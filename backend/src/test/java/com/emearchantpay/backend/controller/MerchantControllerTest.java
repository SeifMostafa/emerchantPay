package com.emearchantpay.backend.controller;

import com.emearchantpay.backend.service.MerchantService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class MerchantControllerTest {
    @Autowired
    MerchantService merchantService;

    @Test
    public void testimportMerchantsSuccess(){

    }
    @Test
    public void testimportMerchantsFail(){

    }
    @Test
    public void testgetMerchants(){

    }
    @Test
    public void testEditMerchantSuccess(){

    }
    @Test
    public void testDestroyMerchantSuccess(){

    }
    @Test
    public void testDestroyMerchantFail(){

    }
}
