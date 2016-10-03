package com.demoapp.service.subscription;

import com.demoapp.controller.response.SubscriptionJsonResponse;

public interface SubscriptionNoticeService {
    SubscriptionJsonResponse noticeSubscription(String eventUrl);
}
