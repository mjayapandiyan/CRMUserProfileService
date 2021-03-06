package com.crm.app.user.profile.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

@Component
public class MailDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String firstname;
	private String lastname;
	private String mailId;
	private String message;
	private String createdDate;
	private String accessCode;

}
