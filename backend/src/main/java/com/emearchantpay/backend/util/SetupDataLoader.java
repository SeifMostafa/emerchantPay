package com.emearchantpay.backend.util;

import com.emearchantpay.backend.model.Role;
import com.emearchantpay.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    RoleRepository roleRepository;
    boolean alreadySetup = false;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(alreadySetup) return;
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");
        alreadySetup = true;
    }
    @Transactional
    Role createRoleIfNotFound(String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = Role.builder().name(name).build();
            roleRepository.save(role);
        }
        return role;
    }
}
