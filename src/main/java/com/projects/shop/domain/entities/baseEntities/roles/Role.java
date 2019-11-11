package com.projects.shop.domain.entities.baseEntities.roles;

import com.projects.shop.domain.entities.baseEntities.BaseUuidEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;


@Entity
@Table(name = "roles")
public class Role extends BaseUuidEntity implements GrantedAuthority {

    private String authority;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }


    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }


    /**
     * Helper Method for setting relationships
     * between role and user
     */
//    public void addUser(User user) {
//        users.add(user);
//        Set<Role> roles = user.getAuthorities();
//        user.setAuthorities(roles);
//    }
}
