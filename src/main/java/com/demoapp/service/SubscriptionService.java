package com.demoapp.service;

import com.demoapp.exception.SubscriptionEventException;
import com.demoapp.model.subscription.SubscriptionEvent;
import com.demoapp.util.SubscriptionEventFetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionService {
    @Value("${oauth.consumer-key}")
    private String consumerKey;

    @Value("${oauth.secret}")
    private String secret;

    private SubscriptionEventFetcher subscriptionEventFetcher = new SubscriptionEventFetcher(consumerKey, secret);

    protected SubscriptionEvent getSubscriptionEvent(String eventUrl) throws SubscriptionEventException {
        return subscriptionEventFetcher.fetchSubscriptionJsonResponse(eventUrl);
    }
}
