package main.com.demoapp.controller;

import com.demoapp.Application;
import com.demoapp.controller.SubscriptionController;
import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.service.SubscriptionService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SubscriptionControllerTest {

    @Test
    public void testSubscriptionCreateSuccess() throws Exception {
        MockMvc mockMvc = getMockMvc(true);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/subscription/create")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .param("eventUrl", "http://appdirect/event/12345");
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"success\": \"true\",\n" +
                        "    \"accountIdentifier\": \"new-account-identifier\"\n" +
                        "}"));
    }

    @Test
    public void testSubscriptionCreateBadRequest() throws Exception {
        MockMvc mockMvc = getMockMvc(false);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/subscription/create")
                        .accept(MediaType.APPLICATION_JSON_UTF8);
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSubscriptionCreateFailure() throws Exception {
        SubscriptionJsonResponse subscriptionJsonResponse = SubscriptionJsonResponse.getFailureResponse(
                SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL,
                SubscriptionJsonResponse.ERROR_CODE_GENERAL);
        MockMvc mockMvc = getMockMvc(false);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/subscription/create")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .param("eventUrl", "http://appdirect/event/incorrectevent");
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(subscriptionJsonResponse)));
    }

    private MockMvc getMockMvc(boolean success){
        SubscriptionJsonResponse subscriptionJsonResponse;
        if (success) {
            subscriptionJsonResponse = SubscriptionJsonResponse.getSuccessResponse("new-account-identifier");
        }else {
            subscriptionJsonResponse = SubscriptionJsonResponse.getFailureResponse(
                    SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL,
                    SubscriptionJsonResponse.ERROR_CODE_GENERAL);
        }
        SubscriptionService subscriptionService = eventUrl -> subscriptionJsonResponse;
        SubscriptionController subscriptionController = new SubscriptionController(subscriptionService);
        return MockMvcBuilders.standaloneSetup(subscriptionController).build();
    }

    private String getAppDirectJsonResponseExample(){
        return "{\n" +
                "    \"success\": \"true\",\n" +
                "    \"accountIdentifier\": \"new-account-identifier\"\n" +
                "}";
    }
}