package org.bfsi.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.bfsi.account.model.AccountStatus;
import org.bfsi.account.model.AccountType;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "bank_account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(length = 12)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private BigDecimal balance;

    private String userId;

}
