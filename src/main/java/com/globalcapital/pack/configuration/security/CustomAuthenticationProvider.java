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

	PasswordEncoding passwordEncoding = new PasswordEncoding();

	public CustomAuthenticationProvider() {

		users = H2DatabaseLuncher.getUsersList();

	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
		Boolean isPassWordMatch = false;
		Boolean isCredentialMatch = false;
		Users ourLoadedUser = new Users();
		Object credentials = authentication.getCredentials();
		if (!(credentials instanceof String)) {
			return null;
		}
		String rawPassword = (credentials.toString());

		for (Users user : users) {

			isPassWordMatch = passwordEncoding.decodePassword(user.getPassword(), rawPassword);
			isCredentialMatch = user.getUserName().equals(name);
			if (isPassWordMatch) {
				ourLoadedUser = user;
			}

		}

		if (isPassWordMatch == false || isCredentialMatch==false) {
			throw new BadCredentialsException("Authentication failed for " + name);
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(ourLoadedUser.getRoleByd().getRoleName()));
		Authentication auth = new UsernamePasswordAuthenticationToken(name, ourLoadedUser.getPassword(),
				grantedAuthorities);
		
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