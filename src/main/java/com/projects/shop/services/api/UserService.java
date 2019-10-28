package com.projects.shop.services.api;

import com.projects.shop.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    void assignUserRoles(UserServiceModel userServiceModel, long numberOfUsers);
}
