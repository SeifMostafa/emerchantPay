package com.emearchantpay.backend.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MerchantController {

    @PostMapping
    public void importMerchants(){

    }
    @DeleteMapping
    public boolean deleteMerchant(){
        return true;
    }
}
