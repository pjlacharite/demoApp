package com.demoapp.service.subscription;

import com.demoapp.controller.response.SubscriptionJsonResponse;

public interface SubscriptionCancelService {
    SubscriptionJsonResponse cancelSubscription(String eventUrl);
}
