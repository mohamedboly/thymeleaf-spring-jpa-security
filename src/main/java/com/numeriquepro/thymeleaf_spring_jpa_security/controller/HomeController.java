package com.numeriquepro.thymeleaf_spring_jpa_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

     @GetMapping({"", "/"})
     public String home() {
         return "index";
     }


    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
