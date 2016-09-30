package com.demoapp.model.subscription;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SubscriptionEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private Marketplace marketplace;
    @Column(columnDefinition = "BINARY(1023)")
    private Creator creator;
    @Column(columnDefinition = "BINARY(1023)")
    private Payload payload;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof SubscriptionEvent) {
            SubscriptionEvent subscriptionEvent = (SubscriptionEvent) obj;
            return subscriptionEvent.getId().equals(this.getId());
        }
        return false;
    }
}