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

@Service
public class SubscriptionChangeServiceImpl extends SubscriptionService implements SubscriptionChangeService {
    private static final Logger LOGGER = Logger.getLogger(SubscriptionChangeServiceImpl.class);
    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;
    @Autowired
    private AccountServiceImpl accountService;

    @Override
    public SubscriptionJsonResponse changeSubscription(String eventUrl) {
        SubscriptionEvent subscriptionChange;
        try {
            subscriptionChange = getSubscriptionEvent(eventUrl);
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
        if (account.isPresent()) {
            return (account.get());
        }
        return null;
    }
}
