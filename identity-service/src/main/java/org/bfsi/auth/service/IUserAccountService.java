package org.bfsi.auth.service;

import org.bfsi.auth.entity.UserAccount;
import org.bfsi.auth.payload.request.UserRequestDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserAccountService {

    boolean saveUser(UserRequestDto user);

    Optional<UserAccount> findUserByUserName(String userName);

    Set<GrantedAuthority> getRolesByUserName(String userName);

    List<UserAccount> findAllUsers();
}
