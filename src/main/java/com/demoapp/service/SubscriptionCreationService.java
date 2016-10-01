package com.demoapp.service;

import com.demoapp.controller.response.SubscriptionJsonResponse;

public interface SubscriptionCreationService {
    SubscriptionJsonResponse createSubscription(String eventUrl);
}
