package org.bfsi.auth.config;

import lombok.AllArgsConstructor;
import org.bfsi.auth.entity.UserAccount;
import org.bfsi.auth.serviceImpl.UserAccountServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserAcountUserDetailsService implements UserDetailsService {

    private UserAccountServiceImpl userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> user = userService.findUserByUserName(username);
        String userName = null, password = null;
        Set<GrantedAuthority> authorities  = null;
        if (user.isPresent())
        {
           userName = user.get().getUserName();
           password = user.get().getPassword();

           //authorities = new ArrayList<GrantedAuthority>();
            // authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities = user.get().getRoles().stream().map(
                    role -> new SimpleGrantedAuthority(role.getName().name()))
                    .collect(Collectors.toSet());

        }
        return new User(userName, password, authorities);
    }
}
