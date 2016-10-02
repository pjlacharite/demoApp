package com.demoapp.service.subscription;

import com.demoapp.controller.response.SubscriptionJsonResponse;

public interface SubscriptionCreationService {
    SubscriptionJsonResponse createSubscription(String eventUrl);
}
