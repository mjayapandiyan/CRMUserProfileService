package com.crm.app.user.profile.dto;

import org.springframework.http.HttpStatus;

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
public class ApiResonseDto {
	
	private byte[] image;
	private String filename;
	private String fileType;
	private String message;
	private String path;
	private HttpStatus statusCode;
}
