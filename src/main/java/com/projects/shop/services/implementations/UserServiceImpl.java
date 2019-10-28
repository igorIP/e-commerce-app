package com.projects.shop.services.implementations;

import com.projects.shop.domain.entities.baseEntities.roles.User;
import com.projects.shop.domain.models.service.UserServiceModel;
import com.projects.shop.repository.api.UserRepository;
import com.projects.shop.services.api.RoleService;
import com.projects.shop.services.api.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final RoleService roleService,
                           final ModelMapper modelMapper,
                           final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        roleService.seedRolesInDb();

        if (userRepository.count() == 0) {
            assignUserRoles(userServiceModel, 0);
        } else {
            userServiceModel.setAuthorities(new HashSet<>());
            userServiceModel.getAuthorities()
                    .add(roleService.findByAuthority("ROLE_USER"));
        }

        User entity = modelMapper.map(userServiceModel, User.class);
        entity.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        return modelMapper.map(
                userRepository.saveAndFlush(entity), UserServiceModel.class);
    }

    @Override
    public void assignUserRoles(UserServiceModel userServiceModel, long numberOfUsers) {
        if (numberOfUsers == 0) {
            userServiceModel.setAuthorities(roleService.findAllRoles());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }


}
