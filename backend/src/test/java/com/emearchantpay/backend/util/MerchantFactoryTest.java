package com.emearchantpay.backend.util;

import com.emearchantpay.backend.model.Merchant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MerchantFactoryTest {
    @InjectMocks
    MerchantFactory merchantFactory;

    @DisplayName("check if passed wrong formatted file to MerchantFactory")
    @Test
    public void testWrongFormatFileFail() throws Exception {
        File file = new File("src/test/resources/merchantsWithWrongFormat.csv");
        FileInputStream fileInputStream = new FileInputStream(file);

        assertThatThrownBy(() -> merchantFactory.getMerchants(fileInputStream))
                .isInstanceOf(Exception.class);
    }
    @DisplayName("check if passed correct formatted file to MerchantFactory")
    @Test
    public void testCorrectFileSuccess() throws Exception {
        File file = new File("src/test/resources/merchants.csv");
        FileInputStream fileInputStream = new FileInputStream(file);

        List<Merchant> merchants = merchantFactory.getMerchants(fileInputStream);

        Assert.assertTrue(!merchants.isEmpty());
        Assert.assertNotNull(merchants.get(0).getName());
    }
}
