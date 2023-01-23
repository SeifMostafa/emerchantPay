package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.repository.MerchantRepository;
import com.emearchantpay.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {
    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public boolean delete(Long id) {
        List<Transaction> allByMerchant = transactionRepository.findAllByMerchant(merchantRepository.findById(id).get());
        if (allByMerchant.isEmpty())
             merchantRepository.deleteById(id);
        else return false;

        return true;
    }
}
