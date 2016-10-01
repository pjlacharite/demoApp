package com.demoapp.model.subscription;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subscription_order")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String editionCode;
    private String pricingDuration;
}
