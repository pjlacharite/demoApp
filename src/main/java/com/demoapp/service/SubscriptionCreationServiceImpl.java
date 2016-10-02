package com.demoapp.service;

import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.exception.SubscriptionEventException;
import com.demoapp.model.subscription.Account;
import com.demoapp.model.subscription.SubscriptionEvent;
import com.demoapp.repository.SubscriptionEventRepository;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionCreationServiceImpl extends SubscriptionService implements SubscriptionCreationService {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionCreationServiceImpl.class);

    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;
    @Autowired
    private AccountService accountService;

    public SubscriptionCreationServiceImpl(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    public SubscriptionJsonResponse createSubscription(String eventUrl) {
        SubscriptionEvent subscriptionCreate;
        try {
            subscriptionCreate = getSubscriptionEvent(eventUrl);
            LOGGER.log(Level.INFO, "Subscription Event - Create: " + subscriptionCreate);
            if (validateCreateSubscription(subscriptionCreate)) {
                Account account = new Account(UUID.randomUUID().toString(), Account.ACCOUNT_ACTIVE);
                accountService.save(account);
                subscriptionEventRepository.save(subscriptionCreate);
                return SubscriptionJsonResponse.getSuccessResponse(account.getAccountIdentifier());
            } else {
                return SubscriptionJsonResponse.getFailureResponse(SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL, SubscriptionJsonResponse.ERROR_CODE_USER_ALREADY_EXISTS);
            }
        } catch (SubscriptionEventException e) {
            LOGGER.log(Level.WARN, "Could not process Create Subscription Event", e);
            return SubscriptionJsonResponse.getFailureResponse(e.getErrorMessage(), e.getErrorCode());
        }
    }


    /**
     * Validate that an account doesn't already exists.
     *
     * @param subscriptionEvent SuscriptionEvent retrieved from eventUrl
     * @return boolean
     */
    private boolean validateCreateSubscription(SubscriptionEvent subscriptionEvent) {
        if (subscriptionEvent.getPayload().getAccount() != null) {
            System.out.println("AccountService: " + accountService);
            Optional<Account> account = accountService.findByAccountIdentifier(subscriptionEvent.getPayload().getAccount().getAccountIdentifier());
            return (!account.isPresent());
        }
        return true;
    }
}