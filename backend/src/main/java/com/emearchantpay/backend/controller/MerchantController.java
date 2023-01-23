package com.emearchantpay.backend.controller;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.service.MerchantService;
import com.emearchantpay.backend.util.MerchantFactory;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
public class MerchantController {
    @Autowired
    MerchantService merchantService;

    @Autowired
    MerchantFactory merchantFactory;

    @PostMapping("/merchants")
    public void importMerchants(@RequestParam("file") MultipartFile file, Model model){
        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {
            try{
                List<Merchant> merchants  = merchantFactory.getMerchants(file.getInputStream());

                for(Merchant merchant: merchants)
                    merchantService.create(merchant);

                model.addAttribute("status", true);

            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }
    }

    @DeleteMapping("/merchant")
    public boolean deleteMerchant(@RequestParam(name="id",required = true)Long id){
        return merchantService.delete(id);
    }
}
