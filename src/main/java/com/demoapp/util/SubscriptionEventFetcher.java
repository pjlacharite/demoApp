package com.demoapp.util;

import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.exception.SubscriptionEventException;
import com.demoapp.model.subscription.SubscriptionEvent;
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

public class SubscriptionEventFetcher {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionEventFetcher.class);
    private String eventUrl;
    private String consumerKey;
    private String secret;

    public SubscriptionEventFetcher(String eventUrl, String consumerKey, String secret) {
        this.eventUrl = eventUrl;
        this.consumerKey = consumerKey;
        this.secret = secret;
    }

    public SubscriptionEvent fetchSubscriptionJsonResponse() throws SubscriptionEventException {
        String errorCode = SubscriptionJsonResponse.ERROR_CODE_UNKNOWN_ERROR;
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes, UrlValidator.ALLOW_LOCAL_URLS);
        if (!urlValidator.isValid(eventUrl)) {
            LOGGER.log(Level.WARN, "URL: " + eventUrl + " is not a valid URL.");
            throw new SubscriptionEventException(SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL, errorCode);
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(eventUrl).openConnection();
            connection.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON_VALUE);
            connection.setRequestProperty(ACCEPT, APPLICATION_JSON_VALUE);
            connection.setRequestMethod(GET.name());
            OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, secret);
            consumer.sign(connection);
            switch (connection.getResponseCode()) {
                case HttpURLConnection.HTTP_OK:
                    LOGGER.log(Level.INFO, "Request to EventUrl " + eventUrl + " returned " + HttpURLConnection.HTTP_OK);
                    InputStream response = connection.getInputStream();
                    JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));
                    return new Gson().fromJson(reader, SubscriptionEvent.class);
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    LOGGER.log(Level.INFO, "Request to EventUrl " + eventUrl + " returned " + HttpURLConnection.HTTP_UNAUTHORIZED);
                    errorCode = SubscriptionJsonResponse.ERROR_CODE_UNAUTHORIZED;
                    break;
                case HttpURLConnection.HTTP_FORBIDDEN:
                    LOGGER.log(Level.INFO, "Request to EventUrl " + eventUrl + " returned " + HttpURLConnection.HTTP_FORBIDDEN);
                    errorCode = SubscriptionJsonResponse.ERROR_CODE_FORBIDDEN;
                    break;
                case HttpURLConnection.HTTP_UNAVAILABLE:
                    LOGGER.log(Level.INFO, "Request to EventUrl " + eventUrl + " returned " + HttpURLConnection.HTTP_UNAVAILABLE);
                    errorCode = SubscriptionJsonResponse.ERROR_CODE_TRANSPORT_ERROR;
                    break;
                default:
                    LOGGER.log(Level.INFO, "Request to EventUrl " + eventUrl + " returned " + connection.getResponseCode());
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
        throw new SubscriptionEventException(SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL, errorCode);
    }
}
