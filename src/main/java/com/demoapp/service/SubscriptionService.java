package com.demoapp.service;

import com.demoapp.controller.response.SubscriptionJsonResponse;

public interface SubscriptionService {
    SubscriptionJsonResponse createSubscription(String eventUrl);
}
