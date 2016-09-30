package com.demoapp.service;

import com.demoapp.controller.SubscriptionController;
import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.model.subscription.SubscriptionEvent;
import com.demoapp.repository.SubscriptionEventRepository;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionController.class);

    @Autowired
    SubscriptionEventRepository subscriptionEventRepository;

    @Override
    public SubscriptionJsonResponse createSubscription(String eventUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(eventUrl).openConnection();
            connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON_VALUE);
            connection.setRequestProperty(ACCEPT, APPLICATION_JSON_VALUE);
            connection.setRequestMethod(GET.name());
            InputStream response = connection.getInputStream();
            JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));
            SubscriptionEvent subscriptionCreate = new Gson().fromJson(reader, SubscriptionEvent.class);
            LOGGER.log(Level.INFO, "Subscription Event - Create: " + subscriptionCreate);
            SubscriptionEvent savedSubscriptionEvent = subscriptionEventRepository.save(subscriptionCreate);
            return SubscriptionJsonResponse.getSuccessResponse(String.valueOf(savedSubscriptionEvent.getId()));
        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARN, "URL: " + eventUrl + " is malformed.", e);
        } catch (IOException e) {
            LOGGER.log(Level.WARN, "Could not open connection to eventUrl: " + eventUrl, e);
        }
        return SubscriptionJsonResponse.getFailureResponse(SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL, SubscriptionJsonResponse.ERROR_CODE_GENERAL);
    }
}