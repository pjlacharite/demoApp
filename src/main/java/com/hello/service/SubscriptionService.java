package com.hello.service;

import com.hello.controller.response.SubscriptionJsonResponse;

public interface SubscriptionService {
    SubscriptionJsonResponse createSubscription(String eventUrl);
}
