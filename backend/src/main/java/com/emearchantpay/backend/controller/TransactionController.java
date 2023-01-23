package com.emearchantpay.backend.controller;

import com.emearchantpay.backend.model.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {

    @GetMapping
    public List<Transaction> getTransactions(){
        // TODO
        return new ArrayList<>();
    }

    @PostMapping
    public boolean submitTransaction(Transaction transaction){
        // TODO
        return true;
    }
}
