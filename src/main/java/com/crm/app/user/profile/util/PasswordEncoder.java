package com.crm.app.user.profile.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
	
	@Autowired
	private static BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		String encodedPassword = new BCryptPasswordEncoder().encode("Test@123");
		System.out.println(encodedPassword);
	}

}
