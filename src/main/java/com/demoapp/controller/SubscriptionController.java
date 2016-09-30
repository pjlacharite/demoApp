package com.demoapp.controller;

import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.service.SubscriptionService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/subscription")
public class SubscriptionController {
    private static final Logger LOGGER = Logger.getLogger(SubscriptionController.class);

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @RequestMapping(value = "/create", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionJsonResponse> create(@RequestParam(value = "eventUrl") String eventUrl) {
        LOGGER.log(Level.INFO, "Processing create request: " + eventUrl);
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(subscriptionService.createSubscription(eventUrl), httpHeaders, HttpStatus.OK);
    }

}