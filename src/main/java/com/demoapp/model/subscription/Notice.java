package com.demoapp.model.subscription;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Notice implements Serializable{

    public transient static final String NOTICE_DEACTIVATED = "DEACTIVATED";
    public transient static final String NOTICE_REACTIVATED = "REACTIVATED";
    public transient static final String NOTICE_CLOSED = "CLOSED";
    public transient static final String NOTICE_UPCOMING_INVOICE = "UPCOMING_INVOICE";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;

    public String getType() {
        return type;
    }
}
