package com.demoapp.model.subscription;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Account implements Serializable{
    public static final transient String ACCOUNT_FREE_TRIAL = "FREE_TRIAL";
    public static final transient String ACCOUNT_FREE_TRIAL_EXPIRED = "FREE_TRIAL_EXPIRED";
    public static final transient String ACCOUNT_ACTIVE = "ACTIVE";
    public static final transient String ACCOUNT_SUSPENDED = "SUSPENDED";
    public static final transient String ACCOUNT_CANCELLED = "CANCELLED";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountIdentifier;
    private String status;
    private Order accountOrder;

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

    public Order getAccountOrder() {
        return accountOrder;
    }

    public void setAccountOrder(Order accountOrder) {
        this.accountOrder = accountOrder;
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
