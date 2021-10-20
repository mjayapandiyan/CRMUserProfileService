package com.crm.app.user.profile.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ResetFormDto {
	
	private String mailId;
	private char[] password;
	private char[] accessCode;

}
