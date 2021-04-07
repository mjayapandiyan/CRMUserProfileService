package com.crm.app.user.profile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserInputException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String message;
}
