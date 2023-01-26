package com.emearchantpay.backend.controller;

import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(){
        return transactionService.getTransactions();
    }

    @PostMapping(value = "/transactions", consumes={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public boolean submitTransaction(@RequestBody Transaction transaction, @RequestParam long merchant_id){
        return transactionService.create(transaction,merchant_id);
    }
}