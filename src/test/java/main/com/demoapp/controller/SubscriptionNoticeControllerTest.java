package main.com.demoapp.controller;

import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriptionNoticeControllerTest extends SubscriptionControllerTest {
    @Test
    public void testSubscriptionCreateSuccess() throws Exception {
        MockMvc mockMvc = getMockMvc(true);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/subscription/notice")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .param("eventUrl", "http://appdirect/event/12345");
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(getAppDirectJsonResponseExample()));
    }

    @Test
    public void testSubscriptionCreateBadRequest() throws Exception {
        MockMvc mockMvc = getMockMvc(false);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/subscription/notice")
                        .accept(MediaType.APPLICATION_JSON_UTF8);
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSubscriptionCreateFailure() throws Exception {
        SubscriptionJsonResponse subscriptionJsonResponse = SubscriptionJsonResponse.getFailureResponse(
                SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL,
                SubscriptionJsonResponse.ERROR_CODE_UNKNOWN_ERROR);
        MockMvc mockMvc = getMockMvc(false);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/subscription/notice")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .param("eventUrl", "http://appdirect/event/incorrectevent");
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(subscriptionJsonResponse)));
    }
}