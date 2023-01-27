package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.User;
import com.emearchantpay.backend.repository.RoleRepository;
import com.emearchantpay.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
            user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean holdAmount(int amount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).get();
        if(user.getBalance()<amount) return false;
        user.setOnholdBalance(user.getOnholdBalance()+amount);
        user.setBalance(user.getBalance()-amount);
        userRepository.save(user);
        return true;
    }

    @Override
    public void unholdAmount(int amount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).get();
        user.setOnholdBalance(user.getOnholdBalance()-amount);
        user.setBalance(user.getBalance()+amount);
        userRepository.save(user);
    }

    @Override
    public boolean charge(int amount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).get();
        if(user.getBalance()<amount) return false;
        user.setBalance(user.getBalance()-amount);
        userRepository.save(user);
        return true;
    }

    @Override
    public void refund(int amount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).get();
        user.setBalance(user.getBalance()+amount);
        userRepository.save(user);
    }
}
