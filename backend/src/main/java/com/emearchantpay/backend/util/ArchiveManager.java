package com.emearchantpay.backend.util;

import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ArchiveManager {
    @Autowired
    TransactionService transactionService;

    @Scheduled(cron = "0 0 0/1 1/1 * *")
    public void archiveTransactions(){

    }
}
