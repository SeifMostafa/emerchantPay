package com.emearchantpay.backend.model;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @CsvBindByName(column = "status")
    boolean active;
    @Column
    @CsvBindByName
    int total_transaction_sum;
}
