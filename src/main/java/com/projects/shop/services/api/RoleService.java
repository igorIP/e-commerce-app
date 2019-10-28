package com.projects.shop.services.api;

import com.projects.shop.domain.entities.baseEntities.roles.Role;
import com.projects.shop.domain.models.service.RoleServiceModel;
import com.projects.shop.domain.models.service.UserServiceModel;

import java.util.Set;

public interface RoleService {

    void seedRolesInDb();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);

}
