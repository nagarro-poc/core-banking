package org.bfsi.transaction.service;

import org.bfsi.transaction.entity.TransactionEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface TransactionService {
    public TransactionEntity credit(Long accountId, BigDecimal amount);

    public TransactionEntity debit(Long accountId, BigDecimal amount);
}
