package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.Transaction;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public interface TransactionService {
    List<Transaction> getTransactions();
    boolean create(Transaction transaction,Long merchant_id);

    void delete(UUID transactin_id);

    List<Transaction> getByCreationTimestampBefore(Timestamp timestamp);

    List<Transaction> getByMerchant(Merchant merchant);
}
