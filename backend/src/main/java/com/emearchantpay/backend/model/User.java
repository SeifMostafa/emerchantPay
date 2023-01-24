package com.emearchantpay.backend.model;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(name = "users", uniqueConstraints= {@UniqueConstraint(columnNames = {"email"})})
@Data
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @CsvBindByName
    private String email;

    @Column
    @CsvBindByName
    String name,password,phone;

    @Column
    @CsvBindByName
    int balance;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",joinColumns=@JoinColumn(name = "username", referencedColumnName = "email")
            ,inverseJoinColumns =@JoinColumn(name ="role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
