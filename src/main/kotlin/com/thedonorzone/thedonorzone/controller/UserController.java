package com.thedonorzone.thedonorzone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user")
public class UserController {
    @GetMapping(path = "test")
    public String getString()
    {
        System.out.println("ok");
        return "testok";
    }
}
