package com.projects.shop.domain.entities.baseEntities.roles;

import com.projects.shop.domain.entities.baseEntities.BaseUuidEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "roles")
public class Role extends BaseUuidEntity implements GrantedAuthority {

    private String authority;
    private Set<User> users;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }


    @Override
    @Column(name = "authority", nullable = false, updatable = false)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Access(AccessType.PROPERTY)//Important: app doesn't work without it
    @ElementCollection(targetClass = Role.class)
    @ManyToMany(mappedBy = "authorities",
            targetEntity = User.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
