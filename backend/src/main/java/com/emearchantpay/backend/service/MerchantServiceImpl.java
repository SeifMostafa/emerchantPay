package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService{
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    TransactionServiceImpl transactionService;

    @Override
    public void create(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    @Override
    public boolean delete(Long id) {
        Merchant merchant = merchantRepository.findById(id).get();
        List<Transaction> existTransactionListByMerchant = transactionService.getByMerchant(merchant);
        if (existTransactionListByMerchant.isEmpty())
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

    @Override
    public void transferMoney(int amount,Merchant merchant) {
        merchant.setTotal_transaction_sum(merchant.getTotal_transaction_sum()+amount);
        merchantRepository.save(merchant);
    }


}
