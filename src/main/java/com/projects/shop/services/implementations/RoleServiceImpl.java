package com.projects.shop.services.implementations;

import com.projects.shop.domain.entities.baseEntities.roles.Role;
import com.projects.shop.domain.entities.baseEntities.roles.User;
import com.projects.shop.domain.models.service.RoleServiceModel;
import com.projects.shop.domain.models.service.UserServiceModel;
import com.projects.shop.repository.api.RoleRepository;
import com.projects.shop.repository.api.UserRepository;
import com.projects.shop.services.api.RoleService;
import com.projects.shop.services.api.UserService;
import org.hibernate.stat.CollectionStatistics;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(final RoleRepository roleRepository,
                           final ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedRolesInDb() {
        if (roleRepository.count() == 0) {
            roleRepository.saveAndFlush(new Role("ROLE_ROOT"));
            roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
            roleRepository.saveAndFlush(new Role("ROLE_MODERATOR"));
            roleRepository.saveAndFlush(new Role("ROLE_USER"));
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(role -> modelMapper.map(role, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return modelMapper
                .map(modelMapper.map(roleRepository.findByAuthority(authority), Role.class),
                        RoleServiceModel.class);
    }
}
