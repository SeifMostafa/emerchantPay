package com.emearchantpay.backend.controller;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.model.TransactionStatus;
import com.emearchantpay.backend.model.TransactionType;
import com.emearchantpay.backend.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
public class TransactionControllerTest {
    @Autowired
    MockMvc mockMvc;

    List<Transaction> transactions;
    Transaction transaction;
    Merchant merchant;




    @Autowired
    ObjectMapper mapper;

//    @Autowired
//    TransactionRepository transactionRepository;
//    @Autowired
//    MerchantRepository merchantRepository;
//
//    @Autowired
//    TransactionService transactionService;



    @Autowired
    UserServiceImpl userService;

    @Before
    public void setup(){
        transactions = new ArrayList<>();
        transactions.add(Transaction.builder().amount(10)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(Merchant.builder().name("IKEA").active(true).build())
                .type(TransactionType.AUTHORIZE)
                .build());
        transactions.add(Transaction.builder().amount(20)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("plamen@gmail.com")
                .customer_phone("2222222")
                .merchant(Merchant.builder().name("eBag").active(true).build())
                .type(TransactionType.CHARGE)
                .build());
        transactions.add(Transaction.builder().amount(30)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("plamen@gmail.com")
                .customer_phone("2222222")
                .merchant(Merchant.builder().name("eBag").active(true).build())
                .type(TransactionType.REFUND)
                .build());
        transactions.add(Transaction.builder().amount(55)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("plamen@gmail.com")
                .customer_phone("2222222")
                .merchant(Merchant.builder().name("eBag").active(true).build())
                .status(TransactionStatus.ERROR)
                .build());
        transaction = Transaction.builder().amount(100)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("kk@gmail.com")
                .customer_phone("18888888")
                .type(TransactionType.AUTHORIZE)
                .merchant(Merchant.builder().name("WoW").active(true).build())
                .status(TransactionStatus.ERROR)
                .build();
        merchant = Merchant.builder().name("IKEA").id(9l).active(true).build();

    }
    @Test
    public void testPostTransactionSuccess() throws Exception {
        System.out.println(transaction);
        System.out.println(merchant);

        mockMvc.perform(post("/merchant")
                .content(mapper.writeValueAsString(merchant)).secure(false)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

       mockMvc.perform(post("/transactions?merchant_id=9")
               .content(mapper.writeValueAsString(transaction))
               .contentType(MediaType.APPLICATION_JSON)).andReturn();
    }
    @Test
    public void testsubmitTransactionWithReferenceToErrorFail() throws Exception {
        String uri = "/transaction";
        Transaction referenceTransaction = Transaction.builder().amount(10)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(Merchant.builder().name("emerchantPay").build())
                .type(TransactionType.AUTHORIZE)
                .status(TransactionStatus.ERROR)
                .build();
        Transaction transaction = Transaction.builder().amount(10)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(Merchant.builder().name("emerchantPay").build())
                .type(TransactionType.AUTHORIZE)
                .reference(referenceTransaction)
                .build();
//        when(merchantService.getById(9l)).thenReturn(Merchant.builder().name("emerchantPay").build()).getMock();
//        MvcResult mvcResult = mvc.perform(post(uri).content(mapToJson(transaction)).param("merchant_id","9")
//                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//        Boolean b = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Boolean.class);

        System.out.println(transaction);
       // Assert.assertTrue(b);
    }
    @Test
    public void testsubmitTransactionWithReferenceToAuthorisedSuccess(){

    }
    @Test
    public void testsubmitTransactionWithNotEnoughBalanceFail(){

    }
    @Test
    public void testsubmitTransactionWithEnoughBalanceSuccess(){

    }
    @Test
    public void testsubmitTransactionRefundSuccess(){

    }
    @Test
    public void testsubmitTransactionAuthorizeSuccess(){

    }
    @Test
    public void testsubmitTransactionReversalSuccess(){

    }
}
