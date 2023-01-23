package com.emearchantpay.backend.util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.List;
import  com.emearchantpay.backend.model.Merchant;
import org.springframework.stereotype.Component;

@Component
public class MerchantFactory {
    public List<Merchant> getMerchants(InputStream inputStream) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            CsvToBean<com.emearchantpay.backend.model.Merchant> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Merchant.class)
                    .withIgnoreLeadingWhiteSpace(true).withSeparator(';')
                    .build();
            List<Merchant> merchants = csvToBean.parse();

           return merchants;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
