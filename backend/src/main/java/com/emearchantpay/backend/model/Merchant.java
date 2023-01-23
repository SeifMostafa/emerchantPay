package com.emearchantpay.backend.model;

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
public class Merchant {

   @Column
   @Id
   Long id;
    @Column
    String name,description;
    String email;
    @Column
    @NonNull
    boolean status;
    @Column
    int total_transaction_sum;
}
