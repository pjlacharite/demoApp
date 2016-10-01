package com.demoapp.service;

import com.demoapp.controller.response.SubscriptionJsonResponse;

public interface SubscriptionChangeService {
    SubscriptionJsonResponse changeSubscription(String eventUrl);
}
