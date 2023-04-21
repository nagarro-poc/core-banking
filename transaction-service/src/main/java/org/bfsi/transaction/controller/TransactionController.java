package org.bfsi.transaction.controller;

import org.bfsi.transaction.entity.TransactionEntity;
import org.bfsi.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/transaction/")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("credit/{accountId}/{amount}")
    public ResponseEntity<TransactionEntity> credit(@PathVariable("accountId") Long accountId,
                                                    @PathVariable("amount") BigDecimal amount) {

        TransactionEntity list = transactionService.credit(accountId, amount);
        return new ResponseEntity<TransactionEntity>(list, HttpStatus.OK);

    }

    @PostMapping("debit/{accountId}/{amount}")
    public ResponseEntity<TransactionEntity> debit(@PathVariable("accountId") Long accountId,
                                                   @PathVariable("amount") BigDecimal amount) {

        TransactionEntity list = transactionService.debit(accountId, amount);
        return new ResponseEntity<TransactionEntity>(list, HttpStatus.OK);

    }
}
