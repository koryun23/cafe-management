package com.cafe.security;

import com.cafe.entity.user.User;
import com.cafe.entity.user.UserRole;
import com.cafe.service.core.user.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DbUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public DbUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = userService.getByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                username,
                dbUser.getPassword(),
                dbUser.getUserRoleList().stream()
                        .map(UserRole::getUserRoleType)
                        .map(Enum::toString)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }
}
