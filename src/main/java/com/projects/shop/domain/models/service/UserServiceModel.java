package com.projects.shop.domain.models.service;

import com.projects.shop.domain.entities.baseEntities.roles.Role;

import java.util.HashSet;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel {

    private String username;
    private String email;
    private String password;
    private Set<RoleServiceModel> authorities;

    protected UserServiceModel() {
        authorities = new HashSet<>();

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}
