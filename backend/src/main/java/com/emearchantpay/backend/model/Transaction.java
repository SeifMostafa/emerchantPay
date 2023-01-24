package com.emearchantpay.backend.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;
import java.util.UUID;


@Table
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    UUID uuid;

    @Column
    @Positive
    int amount;

    @Column
    @Enumerated(EnumType.STRING)
    TransactionStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    TransactionType type;

    @Column
    @Email
    String customer_email;
    String customer_phone;

    @JoinColumn(name="reference_id")
    @ManyToOne
    Transaction reference;

    @JoinColumn(name = "merchant_id")
    @ManyToOne
    Merchant merchant;

    @Column
    @CreationTimestamp
    Timestamp creationTimestamp;

}
