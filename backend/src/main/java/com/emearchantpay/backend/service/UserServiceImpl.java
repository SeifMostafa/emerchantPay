package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.User;
import com.emearchantpay.backend.repository.RoleRepository;
import com.emearchantpay.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;



    @Override
    public boolean login(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());
        authenticationManager.authenticate(token);
        boolean result = token.isAuthenticated();
        if (result) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        return result;
    }

    @Override
    public boolean create(User user,boolean isAdmin) {
        if (isAdmin) {
            user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_ADMIN")));
        }else{
            user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_ADMIN")));
        }
        userRepository.save(user);
        return true;
    }

}
