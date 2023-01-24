package com.emearchantpay.backend.util;

import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class ArchiveManager {
    @Autowired
    TransactionServiceImpl transactionService;

    @Scheduled(cron = "0 0 0/1 1/1 * *")
    public void archiveTransactions(){
        List<Transaction> transactionList = findTransactionsOneHourAgo();
        for(Transaction transaction:transactionList){
            System.out.println(transaction.toString());
            transactionService.delete(transaction.getUuid());
        }

    }
    private List<Transaction> findTransactionsOneHourAgo(){
        Timestamp oneHourAgoTimestamp = new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000));
        return transactionService.getByCreationTimestampAfter(oneHourAgoTimestamp);
    }
}
