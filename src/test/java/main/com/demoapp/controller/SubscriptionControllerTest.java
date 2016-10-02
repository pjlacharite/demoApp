package main.com.demoapp.controller;

import com.demoapp.controller.SubscriptionController;
import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.service.subscription.SubscriptionChangeService;
import com.demoapp.service.subscription.SubscriptionCreationService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class SubscriptionControllerTest {
    protected MockMvc getMockMvc(boolean success){
        SubscriptionJsonResponse subscriptionJsonResponse;
        if (success) {
            subscriptionJsonResponse = SubscriptionJsonResponse.getSuccessResponse("new-account-identifier");
        }else {
            subscriptionJsonResponse = SubscriptionJsonResponse.getFailureResponse(
                    SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL,
                    SubscriptionJsonResponse.ERROR_CODE_UNKNOWN_ERROR);
        }
        SubscriptionCreationService subscriptionCreationService = eventUrl -> subscriptionJsonResponse;
        SubscriptionChangeService subscriptionChangeService  = eventUrl -> subscriptionJsonResponse;
        SubscriptionController subscriptionController = new SubscriptionController(subscriptionCreationService, subscriptionChangeService);
        return MockMvcBuilders.standaloneSetup(subscriptionController).build();
    }

    protected String getAppDirectJsonResponseExample(){
        return "{\n" +
                "    \"success\": \"true\",\n" +
                "    \"accountIdentifier\": \"new-account-identifier\"\n" +
                "}";
    }
}
