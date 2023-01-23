package com.emearchantpay.backend.controller;

import com.emearchantpay.backend.model.Transaction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransactionController {

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(){
        // TODO
        return new ArrayList<>();
    }


    @PostMapping(value = "/transaction", consumes={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public boolean submitTransaction(Transaction transaction){
        // TODO
        return true;
    }
}
