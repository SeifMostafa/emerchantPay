package com.emearchantpay.backend.controller;

import com.emearchantpay.backend.DTO.LoginDTO;
import com.emearchantpay.backend.DTO.RegisterDTO;
import com.emearchantpay.backend.model.User;
import com.emearchantpay.backend.service.UserService;
import com.emearchantpay.backend.util.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserFactory userFactory;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User logged-in successfully!", HttpStatus.OK);
    }
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
        boolean result = userService.create(userFactory.getUser(registerDTO),false);
        if(!result)
            return new ResponseEntity<>("email is already registered!", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
    @PostMapping("/users")
    public boolean importMerchants(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) return false;
        try{
            List<User> users  = userFactory.getUsers(file.getInputStream());
            for(User user: users)
                userService.create(user,true);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
}
