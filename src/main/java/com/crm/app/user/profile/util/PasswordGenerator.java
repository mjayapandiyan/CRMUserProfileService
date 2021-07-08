package com.crm.app.user.profile.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {
	
	private static final Random RANDOM = new Random();
	
	public char[] generatePassword(){
		 String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
	      String specialCharacters = "!@#$";
	      String numbers = "1234567890";
	      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
	      char[] password = new char[8];
	      password[0] = lowerCaseLetters.charAt(RANDOM.nextInt(lowerCaseLetters.length()));
	      password[1] = capitalCaseLetters.charAt(RANDOM.nextInt(capitalCaseLetters.length()));
	      password[2] = specialCharacters.charAt(RANDOM.nextInt(specialCharacters.length()));
	      password[3] = numbers.charAt(RANDOM.nextInt(numbers.length()));
	   
	      for(int i = 4; i< 8 ; i++) {
	         password[i] = combinedChars.charAt(RANDOM.nextInt(combinedChars.length()));
	      }
	      return password;
	}

}
