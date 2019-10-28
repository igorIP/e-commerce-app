package com.projects.shop.domain.entities.baseEntities.roles;

import com.projects.shop.domain.entities.baseEntities.BaseUuidEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseUuidEntity implements GrantedAuthority {

    @Column(name = "authority", nullable = false, updatable = false)
    private String authority;

    @ManyToMany(mappedBy = "authorities", targetEntity = User.class, fetch = FetchType.EAGER)
    private Set<User> users;

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
