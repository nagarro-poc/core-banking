package org.bfsi.notification.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class KafkaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long accountId;

    private String accountNumber;

    private BigDecimal balance;

    private String userId;

    private String transactionType;

    private String transactionId;
}
