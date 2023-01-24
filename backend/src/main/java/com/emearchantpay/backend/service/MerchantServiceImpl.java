package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.repository.MerchantRepository;
import com.emearchantpay.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Merchant getById(Long id) {
        return merchantRepository.findById(id).get();
    }

    @Override
    public List<Merchant> getAll() {
        return merchantRepository.findAll();
    }

}
