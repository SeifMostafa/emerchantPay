package com.emearchantpay.backend.util;

import com.emearchantpay.backend.DTO.RegisterDTO;
import com.emearchantpay.backend.model.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
public class UserFactory {
    @Autowired
    PasswordEncoder passwordEncoder;
    public List<User> getUsers(InputStream inputStream) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            CsvToBean<User> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(User.class)
                    .withIgnoreLeadingWhiteSpace(true).withSeparator(',')
                    .build();
            List<User> users = csvToBean.parse();

            return users;
        } catch (Exception ex) {
            throw ex;
        }
    }
    public User getUser(RegisterDTO registerDTO){
        User user = User.builder()
                .name(registerDTO.getName())
                .phone(registerDTO.getPhone())
                .email(registerDTO.getEmail())
                .balance(registerDTO.getBalance())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .build();
        return user;
    }
}
