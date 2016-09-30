package com.demoapp.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class TestController {
    private static final Logger LOGGER = Logger.getLogger(TestController.class);

    @RequestMapping(value = "/test", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String test() {
        return "{     \"type\": \"SUBSCRIPTION_ORDER\",\n" +
                "      \"marketplace\": {\n" +
                "        \"baseUrl\": \"https://www.acme.com\",\n" +
                "        \"partner\": \"APPDIRECT\"\n" +
                "      },\n" +
                "      \"creator\": {\n" +
                "        \"address\": {\n" +
                "          \"firstName\": \"Sample\",\n" +
                "          \"fullName\": \"Sample Tester\",\n" +
                "          \"lastName\": \"Tester\"\n" +
                "        },\n" +
                "        \"email\": \"sampletester@testco.com\",\n" +
                "        \"firstName\": \"Sample\",\n" +
                "        \"language\": \"en\",\n" +
                "        \"lastName\": \"Tester\",\n" +
                "        \"locale\": \"en_US\",\n" +
                "        \"openId\": \"https://www.acme.com/openid/id/211aa367-f53b-4606-8887-80a381e0ef69\",\n" +
                "        \"uuid\": \"211aa369-f53b-4606-8887-80a361e0ef66\"\n" +
                "      },\n" +
                "      \"payload\": {\n" +
                "        \"company\": {\n" +
                "          \"country\": \"US\",\n" +
                "          \"name\": \"Sample Testing co.\",\n" +
                "          \"uuid\": \"bd58b532-323b-4627-a828-57729489b27b\",\n" +
                "          \"website\": \"www.testerco.com\"\n" +
                "        },\n" +
                "        \"order\": {\n" +
                "          \"editionCode\": \"FREE\",\n" +
                "          \"pricingDuration\": \"MONTHLY\"\n" +
                "        }\n" +
                "      }\n" +
                "  }";
    }
}
