package com.example.kinnibackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeepAliveController {

    @GetMapping("/ping")
    public String ping() {
        return "KKINI server is alive.";
    }
}
