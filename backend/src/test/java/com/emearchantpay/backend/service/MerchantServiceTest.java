package com.emearchantpay.backend.service;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.model.TransactionType;
import com.emearchantpay.backend.repository.MerchantRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MerchantServiceTest {
    @Mock
    MerchantRepository merchantRepository;
    @Mock
    TransactionServiceImpl transactionService;
    @InjectMocks
    MerchantServiceImpl merchantService;

    @Test
    public void testCreationSuccess(){
        Merchant merchant = Merchant.builder().name("IKEA").active(true).build();
        when(merchantRepository.save(merchant)).thenReturn(merchant);

        merchantService.create(merchant);

        verify(merchantRepository,times(1)).save(merchant);
    }
    @Test
    public void testDeletionSuccess(){
        Merchant merchant = Merchant.builder().name("IKEA").active(true).build();
        when(transactionService.getByMerchant(merchant)).thenReturn(Collections.EMPTY_LIST);
        when(merchantRepository.findById(merchant.getId())).thenReturn(Optional.of(merchant));

        boolean deleted = merchantService.delete(merchant.getId());

        verify(merchantRepository,times(1)).deleteById(merchant.getId());
        Assert.assertTrue(deleted);
    }
    @Test
    public void testDeletionFailedBecauseThereAreSomeRelatedTransactions(){
        Merchant merchant = Merchant.builder().name("IKEA").active(true).build();
        Transaction transactionHalfHourAgo = Transaction.builder().amount(10)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()-1000*60*30))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(merchant)
                .type(TransactionType.AUTHORIZE)
                .build();

        when(transactionService.getByMerchant(merchant))
                .thenReturn(Collections.singletonList(transactionHalfHourAgo));
        when(merchantRepository.findById(merchant.getId())).thenReturn(Optional.of(merchant));

        boolean deleted = merchantService.delete(merchant.getId());

        verify(merchantRepository,times(0)).deleteById(merchant.getId());
        Assert.assertFalse(deleted);
    }
    @Test
    public void testTransferMoneySuccess(){
        Merchant merchant = Merchant.builder().name("IKEA").total_transaction_sum(50).active(true).build();
        when(merchantRepository.save(merchant)).thenReturn(merchant);
        int balanceBeforeTransfer100 = merchant.getTotal_transaction_sum();

        merchantService.transferMoney(100,merchant);

        verify(merchantRepository,times(1)).save(merchant);
        Assert.assertEquals(100+balanceBeforeTransfer100 , merchant.getTotal_transaction_sum());
    }
    @Test
    public void testTransferMoneyFail(){
        Merchant merchant = Merchant.builder().name("IKEA").total_transaction_sum(50).active(true).build();
        when(merchantRepository.save(merchant)).thenReturn(merchant);
        int balanceBeforeTransfer100 = merchant.getTotal_transaction_sum();

        merchantService.transferMoney(-100,merchant);

        verify(merchantRepository,times(1)).save(merchant);
        Assert.assertNotEquals(balanceBeforeTransfer100+100 , merchant.getTotal_transaction_sum());
    }
}
