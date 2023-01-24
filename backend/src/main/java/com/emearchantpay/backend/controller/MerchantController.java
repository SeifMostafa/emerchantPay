package com.emearchantpay.backend.controller;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.service.MerchantService;
import com.emearchantpay.backend.util.MerchantFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MerchantController {
    @Autowired
    MerchantService merchantService;
    @Autowired
    MerchantFactory merchantFactory;

    @DeleteMapping("/merchant")
    public boolean deleteMerchant(@RequestParam(name="id",required = true)Long id){
        return merchantService.delete(id);
    }
    @GetMapping("merchants")
    public List<Merchant> getMerchants(){
        return merchantService.getAll();
    }
    @PostMapping("/merchants")
    public boolean importMerchants(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) return false;
        try{
            List<Merchant> merchants  = merchantFactory.getMerchants(file.getInputStream());
            for(Merchant merchant: merchants)
                merchantService.create(merchant);
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
}