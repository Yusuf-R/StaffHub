package com.naviroq.staffhub.controller;

import org.springframework.stereotype.Controller; // this is for returning HTML
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController; // this is for return json object

@Controller
public class StaffController {

    @GetMapping("/staff")
    public String login() {
        return "all-staff";
    }

    @GetMapping("/staff/new")
    public String dashboard() {
        return "add-staff";
    }
}