package com.hello.util;

import com.hello.model.User;
import com.hello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DemoContentLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //Creating users for demo purpose
        User pjlacharite = new User("pjlacharite", "BaSX%#Sst^376quv");
        userRepository.save(pjlacharite);

        User frodobaggins = new User("frodobaggins", "i<3Sam&mypr3ci0us");
        userRepository.save(frodobaggins);
    }
}
