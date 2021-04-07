package com.crm.app.user.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class ProfileDTO extends BaseDto {

	private String title;
	private String gender;
	private String qualification;
	private String landmark;
	private String city;
	private String country;
	private String state;
	private String pincode;
	private String url;
	private String dob;
	private long profileId;
	private long companyId;
	private String companyName;
	private String managerName;
	private long managerId;
	
	private ProjectDTO project;
}
