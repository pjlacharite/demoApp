package main.com.demoapp.controller;

import com.demoapp.controller.SubscriptionController;
import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.service.subscription.SubscriptionCancelService;
import com.demoapp.service.subscription.SubscriptionChangeService;
import com.demoapp.service.subscription.SubscriptionCreationService;
import com.demoapp.service.subscription.SubscriptionNoticeService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class SubscriptionControllerTest {
    MockMvc getMockMvc(boolean success) {
        SubscriptionJsonResponse subscriptionJsonResponse;
        if (success) {
            subscriptionJsonResponse = SubscriptionJsonResponse.getSuccessResponse("new-account-identifier");
        } else {
            subscriptionJsonResponse = SubscriptionJsonResponse.getFailureResponse(
                    SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL,
                    SubscriptionJsonResponse.ERROR_CODE_UNKNOWN_ERROR);
        }
        SubscriptionCreationService subscriptionCreationService = eventUrl -> subscriptionJsonResponse;
        SubscriptionChangeService subscriptionChangeService = eventUrl -> subscriptionJsonResponse;
        SubscriptionCancelService subscriptionCancelService = eventUrl -> subscriptionJsonResponse;
        SubscriptionNoticeService subscriptionNoticeService = eventUrl -> subscriptionJsonResponse;
        SubscriptionController subscriptionController = new SubscriptionController(
                subscriptionCreationService,
                subscriptionChangeService,
                subscriptionCancelService,
                subscriptionNoticeService);
        return MockMvcBuilders.standaloneSetup(subscriptionController).build();
    }

    String getAppDirectJsonResponseExample() {
        return "{\n" +
                "    \"success\": \"true\",\n" +
                "    \"accountIdentifier\": \"new-account-identifier\"\n" +
                "}";
    }
}
