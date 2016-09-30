package com.demoapp.model.subscription;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Creator implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Address address;
    private String email;
    private String firstName;
    private String language;
    private String locale;
    private String openId;
    private String uuid;
}
