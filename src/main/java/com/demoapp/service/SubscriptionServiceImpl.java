package com.demoapp.service;

import com.demoapp.controller.SubscriptionController;
import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.exception.SubscriptionEventException;
import com.demoapp.model.subscription.Account;
import com.demoapp.model.subscription.Creator;
import com.demoapp.model.subscription.SubscriptionEvent;
import com.demoapp.repository.CreatorRepository;
import com.demoapp.repository.SubscriptionEventRepository;
import com.demoapp.util.SubscriptionEventFetcher;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Configuration
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionController.class);

    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;
    @Autowired
    private CreatorRepository creatorRepository;
    @Autowired
    private AccountServiceImpl accountService;

    @Value("${oauth.consumer-key}")
    private String consumerKey;

    @Value("${oauth.secret}")
    private String secret;

    @Override
    public SubscriptionJsonResponse createSubscription(String eventUrl) {
        SubscriptionEvent subscriptionCreate;
        try {
            subscriptionCreate = new SubscriptionEventFetcher(eventUrl, consumerKey, secret).fetchSubscriptionJsonResponse();
            LOGGER.log(Level.INFO, "Subscription Event - Create: " + subscriptionCreate);
            if (validateCreateSubscription(subscriptionCreate)) {
                Account account = new Account(UUID.randomUUID().toString(), Account.ACCOUNT_ACTIVE);
                subscriptionCreate.getPayload().setAccount(account);
                SubscriptionEvent savedSubscriptionEvent = subscriptionEventRepository.save(subscriptionCreate);
                return SubscriptionJsonResponse.getSuccessResponse(savedSubscriptionEvent.getPayload().getAccount().getAccountIdentifier());
            } else {
                return SubscriptionJsonResponse.getFailureResponse(SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL, SubscriptionJsonResponse.ERROR_CODE_USER_ALREADY_EXISTS);
            }
        } catch (SubscriptionEventException e) {
            LOGGER.log(Level.WARN, "Could not process Create Subscription Event", e);
            return SubscriptionJsonResponse.getFailureResponse(e.getErrorMessage(), e.getErrorCode());
        }
    }

    @Override
    public SubscriptionJsonResponse changeSubscription(String eventUrl) {
        SubscriptionEvent subscriptionChange;
        try {
            subscriptionChange = new SubscriptionEventFetcher(eventUrl, consumerKey, secret).fetchSubscriptionJsonResponse();
            LOGGER.log(Level.INFO, "Subscription Event - Change: " + subscriptionChange);
            if (validateChangeSubscription(subscriptionChange)) {
                SubscriptionEvent savedSubscriptionEvent = subscriptionEventRepository.save(subscriptionChange);
                return SubscriptionJsonResponse.getSuccessResponse(savedSubscriptionEvent.getPayload().getAccount().getAccountIdentifier());
            } else {
                return SubscriptionJsonResponse.getFailureResponse(SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL, SubscriptionJsonResponse.ERROR_CODE_ACCOUNT_NOT_FOUND);
            }
        } catch (SubscriptionEventException e) {
            return SubscriptionJsonResponse.getFailureResponse(e.getErrorMessage(), e.getErrorCode());
        }
    }

    /**
     * Validate that a subscription wasn't already created for this Creator.
     *
     * @param subscriptionEvent SuscriptionEvent retrieved from eventUrl
     * @return boolean
     */
    private boolean validateCreateSubscription(SubscriptionEvent subscriptionEvent) {
        Creator creator = creatorRepository.findOne(subscriptionEvent.getCreator().getUuid());
        return (creator == null);
    }

    /**
     * Validates that an Account exists for the account identifier.
     *
     * @param subscriptionEvent SubscriptionEvent retrieved from eventUrl
     * @return Account
     */
    private boolean validateChangeSubscription(SubscriptionEvent subscriptionEvent) {
        Optional<Account> account = accountService.findByAccountIdentifier(subscriptionEvent.getPayload().getAccount().getAccountIdentifier());
        return (account.isPresent());
    }
}