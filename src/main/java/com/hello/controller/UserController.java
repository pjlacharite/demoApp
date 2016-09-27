package com.hello.controller;

import com.hello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
