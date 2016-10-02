package main.com.demoapp.util;

import com.demoapp.model.subscription.SubscriptionEvent;
import com.google.gson.Gson;

public class SubscriptionEventUtil {
    public static SubscriptionEvent getValidCreationEvent() {
        Gson gson = new Gson();
        SubscriptionEvent subscriptionEvent = gson.fromJson("{\n" +
                "    \"type\": \"SUBSCRIPTION_CHANGE\",\n" +
                "    \"marketplace\": {\n" +
                "      \"baseUrl\": \"https://www.acme.com\",\n" +
                "      \"partner\": \"APPDIRECT\"\n" +
                "    },\n" +
                "    \"creator\": {\n" +
                "      \"address\": {\n" +
                "        \"city\": \"San Jose\",\n" +
                "        \"country\": \"US\",\n" +
                "        \"firstName\": \"Test\",\n" +
                "        \"fullName\": \"Test User\",\n" +
                "        \"lastName\": \"User\",\n" +
                "        \"state\": \"CA\",\n" +
                "        \"street1\": \"1 Main St\",\n" +
                "        \"zip\": \"95131\"\n" +
                "      },\n" +
                "      \"email\": \"testuser@testco.com\",\n" +
                "      \"firstName\": \"Test\",\n" +
                "      \"language\": \"en\",\n" +
                "      \"lastName\": \"User\",\n" +
                "      \"locale\": \"en_US\",\n" +
                "      \"openId\": \"https://www.acme.com/openid/id/7f59aad1-85cd-4c04-b35b-906ee53acc71\",\n" +
                "      \"uuid\": \"7f59aad1-85cd-4c04-b35b-906ee53acc71\"\n" +
                "    },\n" +
                "    \"payload\": {\n" +
                "      \"account\": {\n" +
                "        \"accountIdentifier\": \"206123\",\n" +
                "        \"status\": \"ACTIVE\"\n" +
                "      },\n" +
                "      \"order\": {\n" +
                "        \"editionCode\": \"DME\",\n" +
                "        \"pricingDuration\": \"DAILY\",\n" +
                "        \"item\": {\n" +
                "          \"quantity\": \"0\",\n" +
                "          \"unit\": \"GIGABYTE\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "}\n" +
                "\n", SubscriptionEvent.class);
        return subscriptionEvent;
    }
}
