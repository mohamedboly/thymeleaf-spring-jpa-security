package com.numeriquepro.thymeleaf_spring_jpa_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin-only")
    public String adminOnly() {

        return "admin-only";
    }
    @GetMapping("/admin-and-animateur")
    public String adminAndAnimateur() {

        return "admin-and-animateur";
    }

    @GetMapping("/admin-or-animateur")
    public String adminOrAnimateur() {

        return "admin-or-animateur";
    }
}
