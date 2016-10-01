package com.demoapp.service;

import com.demoapp.controller.SubscriptionController;
import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.model.subscription.Creator;
import com.demoapp.model.subscription.SubscriptionEvent;
import com.demoapp.repository.CreatorRepository;
import com.demoapp.repository.SubscriptionEventRepository;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.commons.validator.routines.UrlValidator;
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
    private SubscriptionEventRepository subscriptionEventRepository;
    @Autowired
    private CreatorRepository creatorRepository;

    @Override
    public SubscriptionJsonResponse createSubscription(String eventUrl) {

        String errorCode = SubscriptionJsonResponse.ERROR_CODE_UNKNOWN_ERROR;
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes, UrlValidator.ALLOW_LOCAL_URLS);
        if (!urlValidator.isValid(eventUrl)) {
            LOGGER.log(Level.WARN, "URL: " + eventUrl + " is not a valid URL.");
            return SubscriptionJsonResponse.getFailureResponse(SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL, errorCode);
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(eventUrl).openConnection();
            connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON_VALUE);
            connection.setRequestProperty(ACCEPT, APPLICATION_JSON_VALUE);
            connection.setRequestMethod(GET.name());
            OAuthConsumer consumer = new DefaultOAuthConsumer("Dummy", "secret");
            consumer.sign(connection);
            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:
                    InputStream response = connection.getInputStream();
                    JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));
                    SubscriptionEvent subscriptionCreate = new Gson().fromJson(reader, SubscriptionEvent.class);
                    LOGGER.log(Level.INFO, "Subscription Event - Create: " + subscriptionCreate);
                    if (validateCreateSubscription(subscriptionCreate)) {
                        SubscriptionEvent savedSubscriptionEvent = subscriptionEventRepository.save(subscriptionCreate);
                        return SubscriptionJsonResponse.getSuccessResponse(String.valueOf(savedSubscriptionEvent.getId()));
                    } else {
                        errorCode = SubscriptionJsonResponse.ERROR_CODE_USER_ALREADY_EXISTS;
                        return SubscriptionJsonResponse.getFailureResponse(SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL, errorCode);
                    }


                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    errorCode = SubscriptionJsonResponse.ERROR_CODE_UNAUTHORIZED;
                    break;
                case HttpURLConnection.HTTP_FORBIDDEN:
                    errorCode = SubscriptionJsonResponse.ERROR_CODE_FORBIDDEN;
                    break;
                case HttpURLConnection.HTTP_UNAVAILABLE:
                    errorCode = SubscriptionJsonResponse.ERROR_CODE_TRANSPORT_ERROR;
                    break;
                default:
                    errorCode = SubscriptionJsonResponse.ERROR_CODE_UNKNOWN_ERROR;
            }
        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARN, "URL: " + eventUrl + " is malformed.", e);
            errorCode = SubscriptionJsonResponse.ERROR_CODE_TRANSPORT_ERROR;
        } catch (IOException e) {
            LOGGER.log(Level.WARN, "Could not open connection to eventUrl: " + eventUrl, e);
            errorCode = SubscriptionJsonResponse.ERROR_CODE_TRANSPORT_ERROR;
        } catch (JsonParseException e) {
            LOGGER.log(Level.WARN, "Could not Parse Json to SubscriptionEvent", e);
            errorCode = SubscriptionJsonResponse.ERROR_CODE_INVALID_RESPONSE;
        } catch (OAuthExpectationFailedException e) {
            LOGGER.log(Level.WARN, "oAuth did not meet expectation for eventUrl: " + eventUrl, e);
            errorCode = SubscriptionJsonResponse.ERROR_CODE_UNAUTHORIZED;
        } catch (OAuthMessageSignerException e) {
            LOGGER.log(Level.WARN, "Could not sign request with oAuth signature", e);
            errorCode = SubscriptionJsonResponse.ERROR_CODE_UNAUTHORIZED;
        } catch (OAuthCommunicationException e) {
            LOGGER.log(Level.WARN, "oAuth communication error while requesting: " + eventUrl, e);
            errorCode = SubscriptionJsonResponse.ERROR_CODE_UNAUTHORIZED;
        }
        LOGGER.log(Level.WARN, "Request to eventURL could not be processed because of error: " + errorCode);
        return SubscriptionJsonResponse.getFailureResponse(SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL, errorCode);
    }

    /**
     * Validate that a subscription wasn't already created for this Creator.
     *
     * @param subscriptionEvent
     * @return boolean
     */
    private boolean validateCreateSubscription(SubscriptionEvent subscriptionEvent) {
        Creator creator = creatorRepository.findOne(subscriptionEvent.getCreator().getUuid());
        return (creator == null);
    }
}