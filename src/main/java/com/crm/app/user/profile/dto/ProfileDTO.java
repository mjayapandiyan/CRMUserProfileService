package com.crm.app.user.profile.dto;

import java.util.Map;

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
	private String phone;
	private String state;
	private String url;
	private String dob;
	private long profileId;
	private long companyId;
	private String companyName;
	private String managerName;
	private long managerId;
	private Map<String,String> address;
	private ProjectDTO project;
}
