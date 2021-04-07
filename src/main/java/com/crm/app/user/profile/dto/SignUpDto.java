package com.crm.app.user.profile.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

public class SignUpDto {
	
	@NotNull(message="Title cannot be missing or empty")
	private String title;
	@NotNull(message="First name cannot be missing or empty")
	@Size(min=2, max = 50, message="First name must not be less than 2 and more than 50 characters")
	private String firstname;
	
	@NotNull(message="Last name cannot be missing or empty")
	@Size(min=2, max = 50, message="Last name must not be less than 2 and more than 50 characters")
	private String lastname;
	
	@NotNull(message="Contact Number cannot be missing or empty")
	@Size(min=10, max = 10, message="Please enter valid contact number")
	private String contactno;
	
	@Email
	@NotNull(message="Email Id is required")
	private String email;
	@NotNull(message="Please specify the Gender")
	private String gender;
	
	@NotNull(message="Date of Birth is mandatory")
	private String dob;
	private String password;
	private String createdAt;
	private String createdBy;
	private String modifiedBy;
	private String updatedAt;
	private String status;
	private String loggedDate;
	
	@NotNull(message="Please provide your location details")
    private String landmark;
	@NotNull(message="Please select the Country")
    private String country;
	@NotNull(message="Please select the State")
    private String state;
	@NotNull(message="Please select the City")
    private String city;
	@NotNull(message="Please enter the zipcode")
    private String zipcode;
	@NotNull(message="Please choose the qualification")
    private String qualification;
	@NotNull(message="Please choose the role of the associate")
    private String role;

}
