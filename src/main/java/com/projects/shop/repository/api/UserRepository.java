package com.projects.shop.repository.api;

import com.projects.shop.domain.entities.baseEntities.roles.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUsername(String username);
}
