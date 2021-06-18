package com.crm.app.user.profile.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "USER_DETAILS")

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
	@SequenceGenerator (name = "user_id_sequence", allocationSize = 1, initialValue = 1000, sequenceName = "USER_ID_SEQ")
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "FIRST_NAME",nullable = false)
	private String firstname;
	
	@Column(name = "LAST_NAME",nullable = false)
	private String lastname;
	
	@Column(name = "CONTACT_NO",nullable = false)
	private String contactno;
	
	@Column(name = "EMAIL_ID",nullable = false)
	private String emailId;
	
	@Column(nullable = false)
	private String dob;
	
	@Column(name = "access_code",nullable = false)
	private String accessCode;
	
	@Column(nullable = false)
	private String gender;
	
	private String title;
	@JsonIgnoreProperties
	private String password;
	private Timestamp createdAt;
	private String createdBy;
	
	private String modifiedBy;
	private Timestamp updatedAt;
	private String status;
	private String username;

	@Lob
	@Column(name = "user_image", length = 1000)
	private byte[] image;
	private String filename;
	private String fileType;
	@Column(name = "ROLE_ID",nullable = false)
    private long roleId;
	@Column(name = "LOGIN_DATE", unique = true)
	private String loggedInDate;
	private String qualification;
	private String role;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
   	@JoinColumn(name = "address_id", referencedColumnName = "ADDRESS_ID")
    private Address address;
    
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
   	@JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    private Profile profile;
    
}
