package com.demoapp.service;

import com.demoapp.model.subscription.Account;
import com.demoapp.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    @Override
    public Optional<Account> findByAccountIdentifier(String accountIdentifier) {
        return accountRepository.findByAccountIdentifier(accountIdentifier);
    }


}
