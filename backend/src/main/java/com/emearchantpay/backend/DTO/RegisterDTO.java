package com.emearchantpay.backend.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Data
@Setter
@Getter
public class RegisterDTO {
    @NonNull
    private String name,phone;
    @NonNull
    private int balance;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
