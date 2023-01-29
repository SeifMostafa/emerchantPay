package com.emearchantpay.backend.DTO;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;


import javax.persistence.Column;
import javax.persistence.Id;

@Data
@ToString
public class MerchantDTO {
    @NonNull
    Long id;
    String name, description, email;
    boolean active;
    int total_transaction_sum;
}
