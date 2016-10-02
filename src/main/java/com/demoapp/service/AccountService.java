package com.demoapp.service;

import com.demoapp.model.subscription.Account;

import java.util.Optional;

public interface AccountService{
    Optional<Account> findByAccountIdentifier(String accountIdentifier);
    Account save(Account account);
    Account update(Account account);
}
