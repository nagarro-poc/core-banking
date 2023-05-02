package org.bfsi.transaction.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    Long accountId;

    BigDecimal amount;
}
