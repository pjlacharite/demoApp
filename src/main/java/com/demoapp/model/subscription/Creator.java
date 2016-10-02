package com.demoapp.model.subscription;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Creator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_fk")
    private Address address;
    private String email;
    private String firstName;
    private String language;
    private String locale;
    private String openId;
    private String uuid;

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Creator) {
            Creator creator = (Creator) obj;
            return creator.getId().equals(this.getId());
        }
        return super.equals(obj);
    }
}
