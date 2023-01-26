package com.emearchantpay.backend.util;

import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.Transaction;
import com.emearchantpay.backend.model.TransactionType;
import com.emearchantpay.backend.service.TransactionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ArchiveManagerTest {
    @InjectMocks
    ArchiveManager archiveManager;
    @Mock
    TransactionServiceImpl transactionService;

    @Test
    public void testArchiveTransactionsCallDeleteSuccess(){
        Transaction transactionHalfHourAgo = Transaction.builder().amount(10)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()-1000*60*30))
                .customer_email("csseifms@gmail.com")
                .customer_phone("11111111")
                .merchant(Merchant.builder().name("IKEA").active(true).build())
                .type(TransactionType.AUTHORIZE)
                .build();
        when(transactionService.getByCreationTimestampAfter(any()))
                .thenReturn(Collections.singletonList(transactionHalfHourAgo));

        archiveManager.archiveTransactions();

        verify(transactionService,times(1))
                .delete(transactionHalfHourAgo.getUuid());
    }
}
