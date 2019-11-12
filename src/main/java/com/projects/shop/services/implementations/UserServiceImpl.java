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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private EntityManagerFactory emf;

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

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEM() {
        return emf.createEntityManager();
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {

        EntityManager entityManager = getEM();
        entityManager.getTransaction().begin();

        if (userRepository.count() == 0) {
            assignUserRoles(userServiceModel, 0);
        } else {
            userServiceModel
                    .getAuthorities()
                    .add(roleService.findByAuthority("ROLE_USER"));
        }

        User entity = modelMapper.map(userServiceModel, User.class);
        entity.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();

        return modelMapper.map(entity, UserServiceModel.class);
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
