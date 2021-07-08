package com.crm.app.user.profile.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordEncoder {
	
	public static void main(String[] args) {
		String encodedPassword = new BCryptPasswordEncoder().encode("Test@123");
		log.info(encodedPassword);
	}

}
