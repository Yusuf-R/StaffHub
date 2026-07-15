package com.naviroq.staffhub.controller;

import org.springframework.stereotype.Controller; // this is for returning HTML
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController; // this is for return json object

@Controller
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}