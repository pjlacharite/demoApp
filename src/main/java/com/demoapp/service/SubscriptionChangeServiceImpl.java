package com.demoapp.service;

import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.exception.SubscriptionEventException;
import com.demoapp.model.subscription.Account;
import com.demoapp.model.subscription.SubscriptionEvent;
import com.demoapp.repository.SubscriptionEventRepository;
import com.demoapp.util.SubscriptionEventFetcher;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Configuration
public class SubscriptionChangeServiceImpl implements SubscriptionChangeService {
    private static final Logger LOGGER = Logger.getLogger(SubscriptionChangeServiceImpl.class);
    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;
    @Autowired
    private AccountServiceImpl accountService;

    @Value("${oauth.consumer-key}")
    private String consumerKey;

    @Value("${oauth.secret}")
    private String secret;

    @Override
    public SubscriptionJsonResponse changeSubscription(String eventUrl) {
        SubscriptionEvent subscriptionChange;
        try {
            subscriptionChange = new SubscriptionEventFetcher(eventUrl, consumerKey, secret).fetchSubscriptionJsonResponse();
            LOGGER.log(Level.INFO, "Subscription Event - Change: " + subscriptionChange);
            Account currentAccount = getAccountIfExists(subscriptionChange);
            if (currentAccount != null) {
                accountService.update(subscriptionChange.getPayload().getAccount());
                subscriptionEventRepository.save(subscriptionChange);
                return SubscriptionJsonResponse.getSuccessResponse(currentAccount.getAccountIdentifier());
            } else {
                return SubscriptionJsonResponse.getFailureResponse(SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL, SubscriptionJsonResponse.ERROR_CODE_ACCOUNT_NOT_FOUND);
            }
        } catch (SubscriptionEventException e) {
            return SubscriptionJsonResponse.getFailureResponse(e.getErrorMessage(), e.getErrorCode());
        }
    }
    /**
     * Validates that an Account exists for the account identifier.
     *
     * @param subscriptionEvent SubscriptionEvent retrieved from eventUrl
     * @return Account
     */
    private Account getAccountIfExists(SubscriptionEvent subscriptionEvent) {
        Optional<Account> account = accountService.findByAccountIdentifier(subscriptionEvent.getPayload().getAccount().getAccountIdentifier());
        return (account.get());
    }
}
