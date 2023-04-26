package org.bfsi.transaction.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class KafkaModel {

    private Long accountId;

    private String accountNumber;

    private BigDecimal balance;

    private String userId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private String transactionId;
}
