package com.demoapp.model.subscription;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Account implements Serializable{
    public static final transient String ACCOUNT_ACTIVE = "ACTIVE";
    public static final transient String ACCOUNT_CANCELLED = "CANCELLED";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String accountIdentifier;
    String status;

    public Account(){
        super();
    }

    public Account(String accountIdentifier, String status){
        this.accountIdentifier = accountIdentifier;
        this.status = status;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account){
            Account account = (Account) obj;
            return account.getAccountIdentifier().equals(this.getAccountIdentifier());
        }
        return super.equals(obj);
    }
}
