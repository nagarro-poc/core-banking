package org.bfsi.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.bfsi.account.entity.AccountEntity;
import org.bfsi.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/accounts/")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("health")
    public ResponseEntity<String> health() {
        log.info("Info level log message");
        log.debug("Debug level log message");
        log.error("Error level log message");
        return new ResponseEntity("OK", HttpStatus.OK);

    }
    @GetMapping("account")
    public ResponseEntity<List<AccountEntity>> getAccountEntities() {

        List<AccountEntity> accountEntities = accountService.getAccountEntities();
        return new ResponseEntity<List<AccountEntity>>(accountEntities, HttpStatus.OK);

    }

    @GetMapping("account/{id}")
    public ResponseEntity<AccountEntity> getAccountEntity(@PathVariable("id") Long id) {

        AccountEntity accountEntity = accountService.getAccountEntity(id);
        return new ResponseEntity<AccountEntity>(accountEntity, HttpStatus.OK);

    }

    @PostMapping("account")
    public ResponseEntity<AccountEntity> createAccountEntity(@RequestBody AccountEntity accountEntity) {

        AccountEntity b = accountService.createAccountEntity(accountEntity);
        return new ResponseEntity<AccountEntity>(b, HttpStatus.OK);

    }

    @PutMapping("account/")
    public ResponseEntity<AccountEntity> updateAccountEntity(@RequestBody AccountEntity accountEntity) {

        AccountEntity b = accountService.updateAccountEntity(accountEntity);
        return new ResponseEntity<AccountEntity>(b, HttpStatus.OK);

    }

    @DeleteMapping("account/{id}")
    public ResponseEntity<String> deleteAccountEntity(@PathVariable("id") Long id) {

        accountService.deleteAccountEntity(id);
        return ResponseEntity.ok().build();

    }

}
