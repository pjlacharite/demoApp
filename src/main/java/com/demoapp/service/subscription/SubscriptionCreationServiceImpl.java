package com.demoapp.service.subscription;

import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.exception.SubscriptionEventException;
import com.demoapp.model.subscription.Account;
import com.demoapp.model.subscription.SubscriptionEvent;
import com.demoapp.repository.SubscriptionEventRepository;
import com.demoapp.service.AccountService;
import com.demoapp.util.SubscriptionEventFetcher;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubscriptionCreationServiceImpl implements SubscriptionCreationService {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionCreationServiceImpl.class);

    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;
    @Autowired
    private AccountService accountService;

    @Value("${oauth.consumer-key}")
    private String consumerKey;

    @Value("${oauth.secret}")
    private String secret;

    @Override
    public SubscriptionJsonResponse createSubscription(String eventUrl) {
        SubscriptionEvent subscriptionCreate;
        try {
            subscriptionCreate = new SubscriptionEventFetcher(consumerKey, secret).fetchSubscriptionJsonResponse(eventUrl);
            LOGGER.log(Level.INFO, "Subscription Event - Create: " + subscriptionCreate);
            Account account = null;
            if (subscriptionCreate.getPayload().getAccount() != null){
                account = accountService.findByAccountIdentifier(subscriptionCreate.getPayload().getAccount().getAccountIdentifier());
            }
            if (account == null || Account.ACCOUNT_CANCELLED.equals(account.getStatus())) {
                if (account == null){
                    account = new Account(UUID.randomUUID().toString(), Account.ACCOUNT_ACTIVE);
                }else{
                    account.setStatus(Account.ACCOUNT_ACTIVE);
                }
                account.setAccountOrder(subscriptionCreate.getPayload().getOrder());
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
}