package com.emearchantpay.backend.model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.context.annotation.Scope;

@Table
@Data
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Merchant {

   @Column
   @Id
   @CsvBindByName
   Long id;
    @Column
    @CsvBindByName
    String name,description,email;

    @Column
    @NonNull
    @CsvBindByName
    boolean status;
    @Column
    @CsvBindByName
    int total_transaction_sum;
}
