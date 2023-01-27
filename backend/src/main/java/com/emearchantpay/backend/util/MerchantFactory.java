package com.emearchantpay.backend.util;

import com.emearchantpay.backend.model.Merchant;
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
                    .withIgnoreLeadingWhiteSpace(true).withSeparator(';')
                    .build();
            List<Merchant> merchants = csvToBean.parse();

           return merchants;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
