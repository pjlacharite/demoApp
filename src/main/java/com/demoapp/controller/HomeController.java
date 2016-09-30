package com.demoapp.controller;

import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @RequestMapping(value = {"/", "/home", "/home/"})
    public String index() {
        return "home";
    }



    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model, OpenIDAuthenticationToken authentication) {
        model.addAttribute("authentication", authentication);
        model.addAttribute("name", name);
        return "hello";
    }

}