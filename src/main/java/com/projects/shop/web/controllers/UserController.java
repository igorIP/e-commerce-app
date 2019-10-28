package com.projects.shop.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView loginView() {
        return super.view("register");
    }

    @PostMapping("/register")
    public ModelAndView login() {
        return new ModelAndView("home");
    }
}
