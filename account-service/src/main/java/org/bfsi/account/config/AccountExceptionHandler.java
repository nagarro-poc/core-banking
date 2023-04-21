package org.bfsi.account.config;

import org.bfsi.account.exception.AccountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(value = AccountException.class)
    public ResponseEntity<Object> exception(AccountException exception) {

        return new ResponseEntity<>("Exception Occur in Account Service::" + exception, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
