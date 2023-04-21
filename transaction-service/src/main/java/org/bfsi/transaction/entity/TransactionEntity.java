package org.bfsi.transaction.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bfsi.transaction.model.TransactionStatus;
import org.bfsi.transaction.model.TransactionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transaction")
public class TransactionEntity {

    @Id
    private String id;

    //FK
    private Long accountId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private BigDecimal transactionAmount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
}
