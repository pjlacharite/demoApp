package com.demoapp.service;

import com.demoapp.model.subscription.Account;

public interface AccountService{
    Account findByAccountIdentifier(String accountIdentifier);
    Account save(Account account);
    Account update(Account account);
}