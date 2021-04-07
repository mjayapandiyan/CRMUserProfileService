package com.crm.app.user.profile.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public abstract class BaseDto {
	
	private long userId;
	private String emailId;
	private String firstname;
	private String lastname;
	private String loginTimeStamp;
	private String status;
	private String role;
	 
	

}
