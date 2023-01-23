package com.emearchantpay.backend.controller;

import com.emearchantpay.backend.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MerchantController {
    @Autowired
    MerchantService merchantService;

    @PostMapping("/merchants")
    public void importMerchants(){

    }
    @DeleteMapping("/merchant")
    public boolean deleteMerchant(@RequestParam(name="id",required = true)Long id){
        return merchantService.delete(id);

    }
}
