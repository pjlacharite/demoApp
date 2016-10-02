package com.demoapp.service.subscription;

import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.exception.SubscriptionEventException;
import com.demoapp.model.subscription.Account;
import com.demoapp.model.subscription.SubscriptionEvent;
import com.demoapp.repository.SubscriptionEventRepository;
import com.demoapp.service.AccountServiceImpl;
import com.demoapp.util.SubscriptionEventFetcher;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionChangeServiceImpl implements SubscriptionChangeService {
    private static final Logger LOGGER = Logger.getLogger(SubscriptionChangeServiceImpl.class);
    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;

    @Autowired
    protected AccountServiceImpl accountService;

    @Value("${oauth.consumer-key}")
    private String consumerKey;

    @Value("${oauth.secret}")
    private String secret;

    @Override
    public SubscriptionJsonResponse changeSubscription(String eventUrl) {
        SubscriptionEvent subscriptionChange;
        try {
            subscriptionChange = new SubscriptionEventFetcher(consumerKey, secret).fetchSubscriptionJsonResponse(eventUrl);
            LOGGER.log(Level.INFO, "Subscription Event - Change: " + subscriptionChange);
            Account currentAccount = accountService.findByAccountIdentifier(subscriptionChange.getPayload().getAccount().getAccountIdentifier());
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
}
