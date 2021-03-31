package com.techasians.doctor.user.system.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class TestResource {
    @GetMapping("/test")
    public String a() {
        return "hello word";
    }
}
