package com.globalcapital.pack.configuration.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.globalcapital.database.datasource.H2DatabaseLuncher;
import com.globalcapital.pack.database.entity.Users;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static List<Users> users = new ArrayList();

    public CustomAuthenticationProvider() {
    	
    	users = H2DatabaseLuncher.getUsersList();
        //users.add(new User("erin", "123", "ADMIN"));
        //users.add(new User("mike", "234", "ADMIN"));
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String name = authentication.getName();
        Object credentials = authentication.getCredentials();
        System.out.println("credentials class: " + credentials.getClass());
        if (!(credentials instanceof String)) {
            return null;
        }
        String password = credentials.toString();

        Optional<Users> userOptional = users.stream()
                                           .filter(u -> u.match(name, password))
                                           .findFirst();

        if (!userOptional.isPresent()) {
            throw new BadCredentialsException("Authentication failed for " + name);
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userOptional.get().getRoleByd().getRoleName()));
        Authentication auth = new
                UsernamePasswordAuthenticationToken(name, password, grantedAuthorities);
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private static class User {
        private String username;
        private String password;
        private String role;

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public boolean match(String usernamename, String password) {
            return this.username.equals(username) && this.password.equals(password);
        }
    }
}