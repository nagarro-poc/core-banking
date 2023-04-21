package org.bfsi.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/notification/")
public class NotificationController {

    @GetMapping(value = "health")
    public String health() {
        return "OK From Notification Controller";
    }

    @GetMapping("list")
    public ResponseEntity notifyTransactions() {

//        List<TransactionModel> list = transactionService.getTransactionEntities();
//        return new ResponseEntity<List<TransactionModel>>(list, HttpStatus.OK);
        return null;
    }
}
