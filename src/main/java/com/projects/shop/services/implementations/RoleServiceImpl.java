package com.projects.shop.services.implementations;

import com.projects.shop.domain.entities.baseEntities.roles.Role;
import com.projects.shop.domain.models.service.RoleServiceModel;
import com.projects.shop.repository.api.RoleRepository;
import com.projects.shop.services.api.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    private static Integer count;

    @Autowired
    public RoleServiceImpl(final RoleRepository roleRepository,
                           final ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    //TODO: check why i get duplicate saves of each Role in db,
    // hint: cascade.All works, but check -> Transaction Management or annotations on User and Role entities
    @Override
    public void seedRolesInDb() {
        if (count == null) {
            count = 1;
            roleRepository.save(new Role("ROLE_ROOT"));
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_MODERATOR"));
            roleRepository.save(new Role("ROLE_USER"));
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
