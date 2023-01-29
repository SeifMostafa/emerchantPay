package com.emearchantpay.backend.util;

import com.emearchantpay.backend.DTO.MerchantDTO;
import com.emearchantpay.backend.model.Merchant;
import com.emearchantpay.backend.model.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Component
public class MerchantFactory {
    public List<Merchant> getMerchants(InputStream inputStream) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            CsvToBean<Merchant> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Merchant.class)
                    .withIgnoreLeadingWhiteSpace(true).withSeparator(',')
                    .build();
            List<Merchant> merchants = csvToBean.parse();

           return merchants;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Merchant getMerchant(MerchantDTO merchantDTO) {
        Merchant merchant = Merchant.builder()
                .name(merchantDTO.getName())
                .email(merchantDTO.getEmail())
                .description(merchantDTO.getDescription())
                .total_transaction_sum(merchantDTO.getTotal_transaction_sum())
                .active(merchantDTO.isActive())
                .build();
        return merchant;
    }
}
