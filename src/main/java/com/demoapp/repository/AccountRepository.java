package com.demoapp.repository;

import com.demoapp.model.subscription.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long>{
    Optional<Account> findByAccountIdentifier(String accountIdentifier);
}
