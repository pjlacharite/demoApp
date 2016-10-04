package com.demoapp.service.subscription;

import com.demoapp.controller.response.SubscriptionJsonResponse;
import com.demoapp.exception.SubscriptionEventException;
import com.demoapp.model.subscription.Account;
import com.demoapp.model.subscription.Notice;
import com.demoapp.model.subscription.SubscriptionEvent;
import com.demoapp.repository.SubscriptionEventRepository;
import com.demoapp.service.AccountService;
import com.demoapp.util.SubscriptionEventFetcher;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionNoticeServiceImpl implements SubscriptionNoticeService {
    private static final Logger LOGGER = Logger.getLogger(SubscriptionNoticeServiceImpl.class);

    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;
    @Autowired
    private AccountService accountService;

    @Value("${oauth.consumer-key}")
    private String consumerKey;

    @Value("${oauth.secret}")
    private String secret;


    @Override
    public SubscriptionJsonResponse noticeSubscription(String eventUrl) {
        SubscriptionEvent subscriptionNotice;
        try {
            subscriptionNotice = new SubscriptionEventFetcher(consumerKey, secret).fetchSubscriptionJsonResponse(eventUrl);
            LOGGER.log(Level.INFO, "Subscription Event - Notice: " + subscriptionNotice);
            Account currentAccount = accountService.findByAccountIdentifier(subscriptionNotice.getPayload().getAccount().getAccountIdentifier());
            Notice notice = subscriptionNotice.getPayload().getNotice();
            if (currentAccount != null && notice != null) {
                currentAccount = applyNoticeToAccount(notice, currentAccount);
                accountService.update(currentAccount);
                subscriptionEventRepository.save(subscriptionNotice);
                return SubscriptionJsonResponse.getSuccessResponse(currentAccount.getAccountIdentifier());
            } else {
                return SubscriptionJsonResponse.getFailureResponse(SubscriptionJsonResponse.ERROR_MESSAGE_GENERAL, SubscriptionJsonResponse.ERROR_CODE_ACCOUNT_NOT_FOUND);
            }
        } catch (SubscriptionEventException e) {
            return SubscriptionJsonResponse.getFailureResponse(e.getErrorMessage(), e.getErrorCode());
        }
    }

    private Account applyNoticeToAccount(Notice notice, Account account){
        switch(notice.getType()){
            case Notice.NOTICE_CLOSED:
                account.setStatus(Account.ACCOUNT_CANCELLED);
                break;
            case Notice.NOTICE_DEACTIVATED:
                if (Account.ACCOUNT_FREE_TRIAL.equals(account.getStatus())) {
                    account.setStatus(Account.ACCOUNT_FREE_TRIAL_EXPIRED);
                }else{
                    account.setStatus(Account.ACCOUNT_SUSPENDED);
                }
                break;
            case Notice.NOTICE_REACTIVATED:
                account.setStatus(Account.ACCOUNT_ACTIVE);
                break;
            case Notice.NOTICE_UPCOMING_INVOICE:
                //Nothing to do here for now.
                break;
        }
        return account;
    }
}
