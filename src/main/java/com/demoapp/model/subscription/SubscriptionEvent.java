package com.demoapp.model.subscription;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SubscriptionEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "marketplace_fk")
    private Marketplace marketplace;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "creator_fk", referencedColumnName = "uuid")
    private Creator creator;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "payload_fk")
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

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
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