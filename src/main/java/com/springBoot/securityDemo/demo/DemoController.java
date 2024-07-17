package com.springBoot.securityDemo.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/api")
    public String welcome(){
        return "welcome to unprotected endpoint";
    }
    @GetMapping("/api1")
    public String welcome1(){
        return "welcome to protected endpoint";
    }
}
