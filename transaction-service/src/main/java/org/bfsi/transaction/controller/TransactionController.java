package org.bfsi.transaction.controller;

import org.bfsi.transaction.entity.TransactionEntity;
import org.bfsi.transaction.model.TransactionDTO;
import org.bfsi.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/transactions/")
public class TransactionController {
    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping("credit")
    public ResponseEntity<TransactionEntity> credit(@RequestBody TransactionDTO transactionDTO) {
        logger.info("In Credit API:" + transactionDTO.toString());
        TransactionEntity list = transactionService.credit(transactionDTO);
        return new ResponseEntity<TransactionEntity>(list, HttpStatus.OK);

    }

    @PostMapping("debit")
    public ResponseEntity<TransactionEntity> debit(@RequestBody TransactionDTO transactionDTO) {
        logger.info("In Debit API:" + transactionDTO.toString());

        TransactionEntity list = transactionService.debit(transactionDTO);
        return new ResponseEntity<TransactionEntity>(list, HttpStatus.OK);

    }
}
