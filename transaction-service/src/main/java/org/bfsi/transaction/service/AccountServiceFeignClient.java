package org.bfsi.transaction.service;

import org.bfsi.transaction.model.AccountEntityModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service",  path = "api/v1/accounts/")
//@FeignClient(value = "AccountServiceFeignClient", url = "${account.service.url}", path = "api/v1/accounts/")
public interface AccountServiceFeignClient {

    @GetMapping(value = "account/{id}")
    public AccountEntityModel getAccountDetails(@PathVariable Long id);

    @PutMapping("account/")
    public AccountEntityModel updateAccountEntity(@RequestBody AccountEntityModel accountEntity);

}