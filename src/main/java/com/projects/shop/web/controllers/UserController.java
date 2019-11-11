package com.projects.shop.web.controllers;

import com.projects.shop.domain.models.binding.user.UserLoginBindingModel;
import com.projects.shop.domain.models.binding.user.UserRegisterBindingModel;
import com.projects.shop.domain.models.service.UserServiceModel;
import com.projects.shop.services.api.RoleService;
import com.projects.shop.services.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    private static int usersCount = 0;

    public UserController(final UserService userService,
                          final RoleService roleService,
                          final ModelMapper mapper,
                          final BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register() {
        if (usersCount == 0) {
            usersCount++;
        }
        return super.view("register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute("registerModel") UserRegisterBindingModel bindingModel) {
        if (!(bindingModel.getPassword().equals(bindingModel.getConfirmPassword()))) {
            return super.redirect("register");
        } else {
            userService.registerUser(mapper.map(bindingModel, UserServiceModel.class));
            return new ModelAndView("login");
        }
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView loginGet() {
        return view("login");
    }

    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView loginPost(@ModelAttribute("loginModel") UserLoginBindingModel bindingModel,
                                  BindingResult result) {
        UserDetails userDetails = userService.loadUserByUsername(bindingModel.getUsername());
        if (result.hasErrors() || userDetails != null) {
            return view("login");
        } else {
            if (passwordEncoder.matches(userDetails.getPassword(), bindingModel.getPassword())) {
                return view("index");
            }
            return view("index");
        }
    }
}
