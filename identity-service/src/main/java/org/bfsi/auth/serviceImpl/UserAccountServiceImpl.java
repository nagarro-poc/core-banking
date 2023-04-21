package org.bfsi.auth.serviceImpl;

import lombok.AllArgsConstructor;
import org.bfsi.auth.entity.Role;
import org.bfsi.auth.entity.UserAccount;
import org.bfsi.auth.payload.request.UserRequestDto;
import org.bfsi.auth.repository.RoleRepository;
import org.bfsi.auth.repository.UserAccountRepository;
import org.bfsi.auth.service.IUserAccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserAccountServiceImpl implements IUserAccountService {

    private UserAccountRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public boolean saveUser(UserRequestDto userDto) {

        UserAccount user = new UserAccount();
        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<String> userRolesRequest = userDto.getRoles();
        Set<Role> roles = new HashSet<>();
        userRolesRequest.forEach( role ->
                {
                    switch(Role.ERole.valueOf(role))
                    {
                        case ROLE_ADMIN:
                            Role adminRole = roleRepository.findByName(Role.ERole.ROLE_ADMIN).get();
                            roles.add(adminRole);

                            break;
                        case ROLE_USER:
                            Role userRole = roleRepository.findByName(Role.ERole.ROLE_USER).get();
                            roles.add(userRole);

                            break;
                    }
                }

        );
        // check role in roles repository
        user.setRoles(roles);


        UserAccount savedUser = userRepository.save(user);

        return (savedUser.getId() > 0);
    }

    @Override
    public Optional<UserAccount> findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Set<GrantedAuthority> getRolesByUserName(String userName) {
        Optional<UserAccount> user = userRepository.findByUserName(userName);
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (user.isPresent()) {
            authorities = user.get().getRoles().stream().map(
                            role -> new SimpleGrantedAuthority(role.getName().name()))
                    .collect(Collectors.toSet());
        }
        return authorities;
    }

    @Override
    public List<UserAccount> findAllUsers() {
        return null;
    }
}
