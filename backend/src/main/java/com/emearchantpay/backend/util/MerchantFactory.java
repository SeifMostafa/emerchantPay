package com.emearchantpay.backend.util;

import com.emearchantpay.backend.model.Merchant;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

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
