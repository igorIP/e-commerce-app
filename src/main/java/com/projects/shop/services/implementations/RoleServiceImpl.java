package com.projects.shop.services.implementations;

import com.projects.shop.domain.entities.baseEntities.roles.Role;
import com.projects.shop.domain.models.service.RoleServiceModel;
import com.projects.shop.repository.api.RoleRepository;
import com.projects.shop.services.api.RoleService;
import org.hibernate.boot.SessionFactoryBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Autowired
    public RoleServiceImpl(final RoleRepository roleRepository,
                           final ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    /**
     * empty users_roles table only for the first user:
     *             em.getTransaction().commit();
     *             em.close();
     *             - doesn't recognize the mapping role -> user, because until em.commit i have nothing in roles table
     *             - doesn't saves the role entities but doesn't save my First user roles in users_roles table
     *
     * Fix:
     *         ->  em.flush();
     *         ->  em.clear();
     *             em.getTransaction().commit();
     *             em.close();
     *             - because changes on one or more entities get written to the DB the moment you call flush() on the EntityManager instance
     *             - but changes become "visible" only after the transaction has been properly executed by the container
     * */
    @Override
    public void seedRolesInDb() {
        if (roleRepository.count() == 0) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(new Role("ROLE_ROOT"));
            em.persist((new Role("ROLE_ADMIN")));
            em.persist((new Role("ROLE_MODERATOR")));
            em.persist((new Role("ROLE_USER")));

            em.flush();
            em.clear();
            em.getTransaction().commit();
            em.close();
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
                .map(modelMapper.map(roleRepository.findByAuthority(authority).orElseThrow(), Role.class),
                        RoleServiceModel.class);
    }
}
