package com.emearchantpay.backend.util;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MerchantFactoryTest {
    @InjectMocks
    MerchantFactory merchantFactory;

}
