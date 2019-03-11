package com.globalcapital.pack.configuration.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoding {

	public String encoder(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(6); // Strength set as 12
		String encodedPassword = encoder.encode(password);

		return encodedPassword;
	}

	public boolean decodePassword(String hashPassword, String rawPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(6);
		return encoder.matches(rawPassword, hashPassword);
	}

}
