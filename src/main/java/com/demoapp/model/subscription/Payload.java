package com.demoapp.model.subscription;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Payload implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "company_fk")
    private Company company;

    @Transient
    private Order order;

    @Transient
    private Account account;

    private Notice notice;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
