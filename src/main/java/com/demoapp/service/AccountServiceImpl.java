package com.demoapp.service;

import com.demoapp.model.subscription.Account;
import com.demoapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<Account> findByAccountIdentifier(String accountIdentifier) {
        return accountRepository.findByAccountIdentifier(accountIdentifier);
    }

    @Override
    public Account update(Account account) {
        Optional<Account> current = accountRepository.findByAccountIdentifier(account.getAccountIdentifier());
        if (current == null) {
            return null;
        }
        Account currentAccount = current.get();
        currentAccount.setStatus(account.getStatus());
        return accountRepository.save(currentAccount);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
