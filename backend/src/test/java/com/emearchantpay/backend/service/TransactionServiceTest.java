package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.model.TransactionStatus;
import com.emearchantpay.backend.model.TransactionType;
import com.emearchantpay.backend.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    MerchantService merchantService;
    @Mock
    UserService userService;
    @InjectMocks
    TransactionServiceImpl transactionService;

    Transaction chargeTransaction,reverseTransaction,authorizeTransaction,refundTransaction;
    Transaction approvedTransaction;
    Transaction chargeTransactionWithInactiveMerchant,authorizeTransactionWithReferenceReversedTransaction,authorizeTransactionWithReferenceErrorTransaction;
    Merchant activeMerchant,inactiveMerchant;

    @Before
    public void before(){
        inactiveMerchant = Merchant.builder().name("IKEA").total_transaction_sum(50).active(false).build();
        activeMerchant = Merchant.builder().name("Happy").total_transaction_sum(10).active(true).build();

        chargeTransaction = Transaction.builder()
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(activeMerchant)
                .type(TransactionType.CHARGE)
                .amount(10)
                .build();
        chargeTransactionWithInactiveMerchant = Transaction.builder()
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(inactiveMerchant)
                .type(TransactionType.CHARGE)
                .amount(10)
                .build();
        reverseTransaction = Transaction.builder()
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(activeMerchant)
                .type(TransactionType.REVERSAL)
                .amount(10)
                .build();
        authorizeTransaction = Transaction.builder()
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(activeMerchant)
                .type(TransactionType.AUTHORIZE)
                .amount(10)
                .build();
        refundTransaction = Transaction.builder()
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(activeMerchant)
                .type(TransactionType.REFUND)
                .build();
        Transaction reveresedTransaction = Transaction.builder()
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(activeMerchant)
                .type(TransactionType.AUTHORIZE)
                .status(TransactionStatus.REVERSED)
                .build();
        authorizeTransactionWithReferenceReversedTransaction = Transaction.builder()
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(activeMerchant)
                .type(TransactionType.REFUND)
                .reference(reveresedTransaction)
                .build();
        Transaction errorTransaction = Transaction.builder()
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(activeMerchant)
                .type(TransactionType.AUTHORIZE)
                .status(TransactionStatus.ERROR)
                .build();
        authorizeTransactionWithReferenceErrorTransaction = Transaction.builder()
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(activeMerchant)
                .type(TransactionType.REFUND)
                .reference(errorTransaction)
                .build();
        approvedTransaction = Transaction.builder()
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(activeMerchant)
                .type(TransactionType.AUTHORIZE)
                .status(TransactionStatus.APPROVED)
                .build();
    }
    @DisplayName("Check if tried to submit transaction with inactive merchant")
    @Test
    public void testTransactionCreationWithMerchantInActiveFail(){
        when(merchantService.getById(any())).thenReturn(inactiveMerchant);

        boolean result = transactionService.create(chargeTransactionWithInactiveMerchant,inactiveMerchant.getId());

        verify(transactionRepository,times(0)).save(any());
        Assert.assertFalse(result);
    }
    @DisplayName("Check if tried to submit transaction referenced to REVERSED transaction")
    @Test
    public void testTransactionCreationWithTypeAuthorizeWithReferenceReversedFail(){
        when(merchantService.getById(any())).thenReturn(activeMerchant);
        transactionService.create(authorizeTransactionWithReferenceReversedTransaction,activeMerchant.getId());

        verify(transactionRepository,times(1)).save(any());
        Assert.assertEquals(TransactionStatus.ERROR,authorizeTransactionWithReferenceReversedTransaction.getStatus());
    }
    @DisplayName("Check if tried to submit transaction referenced to ERROR transaction")
    @Test
    public void testTransactionCreationWithTypeAuthorizeWithReferenceErrorFail(){

        when(merchantService.getById(any())).thenReturn(activeMerchant);
        transactionService.create(authorizeTransactionWithReferenceErrorTransaction,activeMerchant.getId());

        verify(transactionRepository,times(1)).save(any());
        Assert.assertEquals(TransactionStatus.ERROR,authorizeTransactionWithReferenceErrorTransaction.getStatus());
    }
    @DisplayName("Check if tried to submit authorize transaction without reference")
    @Test
    public void testTransactionCreationWithTypeAuthorizeWithoutReferenceSuccess(){
        when( merchantService.getById(any())).thenReturn(activeMerchant);
        when(userService.holdAmount(authorizeTransaction.getAmount())).thenReturn(true);

        transactionService.create(authorizeTransaction,activeMerchant.getId());

        verify(transactionRepository,times(1)).save(any());
        Assert.assertEquals(TransactionStatus.APPROVED,authorizeTransaction.getStatus());

    }
    @DisplayName("Check if tried to submit charge transaction without reference")
    @Test
    public void testTransactionCreationWithTypeChargeWithoutReferenceSuccess(){
        when(merchantService.getById(any())).thenReturn(activeMerchant);
        when(userService.charge(chargeTransaction.getAmount())).thenReturn(true);

        transactionService.create(chargeTransaction,activeMerchant.getId());

        verify(transactionRepository,times(1)).save(any());
        Assert.assertEquals(TransactionStatus.APPROVED,chargeTransaction.getStatus());
    }
    @DisplayName("Check if tried to submit charge transaction with reference")
    @Test
    public void testTransactionCreationWithTypeChargeWithReferenceSuccess(){
        chargeTransaction.setReference(approvedTransaction);
        when(merchantService.getById(any())).thenReturn(activeMerchant);
        when(userService.charge(chargeTransaction.getAmount())).thenReturn(true);

        transactionService.create(chargeTransaction,activeMerchant.getId());

        verify(transactionRepository,times(1)).save(any());
        Assert.assertEquals(TransactionStatus.APPROVED,chargeTransaction.getStatus());

        chargeTransaction.setReference(null);
    }
    @DisplayName("Check if tried to submit refund transaction without reference")
    @Test
    public void testTransactionCreationWithTypeRefundWithoutReferenceFail(){
        when(merchantService.getById(any())).thenReturn(activeMerchant);

        transactionService.create(refundTransaction,activeMerchant.getId());

        verify(transactionRepository,times(1)).save(any());
        Assert.assertEquals(TransactionStatus.ERROR,refundTransaction.getStatus());
    }
    @DisplayName("Check if tried to submit refund transaction with reference")
    @Test
    public void testTransactionCreationWithTypeRefundWithReferenceSuccess(){
        refundTransaction.setReference(approvedTransaction);
        when(merchantService.getById(any())).thenReturn(activeMerchant);

        transactionService.create(refundTransaction,activeMerchant.getId());

        verify(transactionRepository,times(2)).save(any());
        Assert.assertEquals(TransactionStatus.APPROVED,refundTransaction.getStatus());
        Assert.assertEquals(TransactionStatus.REFUNDED,refundTransaction.getReference().getStatus());

        refundTransaction.setReference(null);
    }
    @DisplayName("Check if tried to submit reverse transaction with reference")
    @Test
    public void testTransactionCreationWithTypeReversalWithReferenceSuccess(){
        reverseTransaction.setReference(approvedTransaction);
        when(merchantService.getById(any())).thenReturn(activeMerchant);

        transactionService.create(reverseTransaction,activeMerchant.getId());

        verify(transactionRepository,times(2)).save(any());
        Assert.assertEquals(TransactionStatus.APPROVED,reverseTransaction.getStatus());
        Assert.assertEquals(TransactionStatus.REVERSED,reverseTransaction.getReference().getStatus());

        reverseTransaction.setReference(null);
    }
    @DisplayName("Check if tried to submit reverse transaction without reference")
    @Test
    public void testTransactionCreationWithTypeReversalWithoutReferenceFail(){
        when(merchantService.getById(any())).thenReturn(activeMerchant);

        transactionService.create(reverseTransaction,activeMerchant.getId());

        verify(transactionRepository,times(1)).save(any());
        Assert.assertEquals(TransactionStatus.ERROR,reverseTransaction.getStatus());
    }
}