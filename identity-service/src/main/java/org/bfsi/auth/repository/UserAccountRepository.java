package org.bfsi.auth.repository;

import org.bfsi.auth.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    Optional<UserAccount> findByUserName(String userName);
}
