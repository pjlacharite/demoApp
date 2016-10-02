package com.demoapp.service.subscription;

import com.demoapp.controller.response.SubscriptionJsonResponse;

public interface SubscriptionChangeService {
    SubscriptionJsonResponse changeSubscription(String eventUrl);
}
