package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public boolean login(String email, String password);

    public boolean create(User user,boolean isAdmin);


}
