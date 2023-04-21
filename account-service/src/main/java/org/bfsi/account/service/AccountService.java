package org.bfsi.account.service;

import org.bfsi.account.entity.AccountEntity;
import org.bfsi.account.exception.AccountException;
import org.bfsi.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public List<AccountEntity> getAccountEntities() {

        return accountRepository.findAll();

    }

    public AccountEntity getAccountEntity(Long id) {

        return accountRepository.findById(id).get();

    }

    public AccountEntity createAccountEntity(AccountEntity accountEntity) {
        accountEntity.setAccountNumber(generateRandomAccountNumber());
        return accountRepository.save(accountEntity);

    }

    public AccountEntity updateAccountEntity(AccountEntity accountEntity) {

        AccountEntity updateEntity = accountRepository.findById(accountEntity.getAccountId()).orElseThrow(() -> new AccountException("Account not found with id::" + accountEntity.getAccountId()));

        updateEntity.setAccountStatus(accountEntity.getAccountStatus());
        updateEntity.setBalance(accountEntity.getBalance());
        updateEntity.setAccountType(accountEntity.getAccountType());

        AccountEntity a = accountRepository.save(updateEntity);
        return a;

    }

    public void deleteAccountEntity(Long id) {

        AccountEntity accountEntity = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account not found with id::" + id));
        accountRepository.delete(accountEntity);

    }

    private String generateRandomAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // first not 0 digit
        sb.append(random.nextInt(9) + 1);

        // rest of 11 digits
        for (int i = 0; i < 11; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
