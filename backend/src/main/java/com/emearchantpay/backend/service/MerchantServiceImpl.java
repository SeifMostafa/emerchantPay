package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.repository.MerchantRepository;
import com.emearchantpay.backend.repository.TransactionRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.service.MerchantServiceImpl;
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
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService{
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void create(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    @Override
    public boolean delete(Long id) {
        List<Transaction> allByMerchant = transactionRepository.findAllByMerchant(merchantRepository.findById(id).get());
        if (allByMerchant.isEmpty())
             merchantRepository.deleteById(id);
        else return false;

        return true;
    }
}
