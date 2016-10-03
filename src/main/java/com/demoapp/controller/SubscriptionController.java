package com.demoapp.controller;

import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.service.subscription.SubscriptionCancelService;
import com.demoapp.service.subscription.SubscriptionChangeService;
import com.demoapp.service.subscription.SubscriptionCreationService;
import com.demoapp.service.subscription.SubscriptionNoticeService;
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
    private SubscriptionCreationService subscriptionCreationService;

    @Autowired
    private SubscriptionChangeService subscriptionChangeService;

    @Autowired
    private SubscriptionCancelService subscriptionCancelService;

    @Autowired
    private SubscriptionNoticeService subscriptionNoticeService;

    @Autowired
    public SubscriptionController(SubscriptionCreationService subscriptionCreationService,
                                  SubscriptionChangeService subscriptionChangeService,
                                  SubscriptionCancelService subscriptionCancelService,
                                  SubscriptionNoticeService subscriptionNoticeService) {
        this.subscriptionCreationService = subscriptionCreationService;
        this.subscriptionChangeService = subscriptionChangeService;
        this.subscriptionCancelService = subscriptionCancelService;
        this.subscriptionNoticeService = subscriptionNoticeService;
    }

    @RequestMapping(value = "/create", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionJsonResponse> create(@RequestParam(value = "eventUrl") String eventUrl) {
        LOGGER.log(Level.INFO, "Processing create request: " + eventUrl);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(subscriptionCreationService.createSubscription(eventUrl), httpHeaders, HttpStatus.OK);
    }


    @RequestMapping(value = "/change", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionJsonResponse> change(@RequestParam(value = "eventUrl") String eventUrl) {
        LOGGER.log(Level.INFO, "Processing create request: " + eventUrl);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(subscriptionChangeService.changeSubscription(eventUrl), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/cancel", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionJsonResponse> cancel(@RequestParam(value = "eventUrl") String eventUrl) {
        LOGGER.log(Level.INFO, "Processing create request: " + eventUrl);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(subscriptionCancelService.cancelSubscription(eventUrl), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/notice", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SubscriptionJsonResponse> notice(@RequestParam(value = "eventUrl") String eventUrl) {
        LOGGER.log(Level.INFO, "Processing create request: " + eventUrl);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(subscriptionNoticeService.noticeSubscription(eventUrl), httpHeaders, HttpStatus.OK);
    }
}