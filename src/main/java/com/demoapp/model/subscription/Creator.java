package com.demoapp.model.subscription;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Creator implements Serializable {

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_fk")
    private Address address;
    private String email;
    private String firstName;
    private String language;
    private String locale;
    private String openId;
    @Id
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Creator) {
            Creator creator = (Creator) obj;
            return creator.getUuid().equals(this.getUuid());
        }
        return super.equals(obj);
    }
}
