package com.emearchantpay.backend.repository;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    public List<Transaction> findAllByMerchant(Merchant merchant);
    public List<Transaction> findAllByCreationTimestampAfter(Timestamp timestamp);
}
