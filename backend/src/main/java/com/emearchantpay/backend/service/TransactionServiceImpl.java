package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.model.TransactionStatus;
import com.emearchantpay.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    MerchantService merchantService;
    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public boolean create(Transaction transaction,Long merchant_id) {
        Merchant merchant = merchantService.getById(merchant_id);
        if(merchant ==null || !merchant.isActive()) return false;

        transaction.setMerchant(merchant);
        
        if(transaction.getReference()!=null){
            TransactionStatus referencedTransactionStatus = transaction.getReference().getStatus();
            if(referencedTransactionStatus== TransactionStatus.ERROR ||
                    referencedTransactionStatus == TransactionStatus.REVERSED){
                transaction.setStatus(TransactionStatus.ERROR);
            }
        }



        transactionRepository.save(transaction);
        return true;


    }

    @Override
    public void delete(UUID transactin_id) {
        transactionRepository.deleteById(transactin_id);
    }

    @Override
    public List<Transaction> getByCreationTimestampAfter(Timestamp timestamp) {
        return transactionRepository.findAllByCreationTimestampAfter(timestamp);
    }
}
