package com.emearchantpay.backend.util;

import com.emearchantpay.backend.model.Role;
import com.emearchantpay.backend.model.User;
import com.emearchantpay.backend.repository.RoleRepository;
import com.emearchantpay.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collections;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    boolean alreadySetup = false;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(alreadySetup) return;
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");
        createSuperUserIfNotFound();
        alreadySetup = true;
    }
    @Transactional
    private void createSuperUserIfNotFound() {
        User superUser = User.builder()
                .email("csseifms@gmail.com")
                .password(passwordEncoder.encode("post1234"))
                .roles(Collections.singleton(roleRepository.findByName("USER_ADMIN"))).build();
        userRepository.save(superUser);
    }

    @Transactional
    private void createRoleIfNotFound(String name) {
        roleRepository.save(Role.builder().name(name).build());
    }
}
