package com.crm.app.user.profile.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "COMPANY_INFO")
public class Company {
	
	@Id
	@Column(name = "COMPANY_ID")
	private long companyId;
	@Column(name = "COMPANY_NAME")
	private String companyName;
	private String website;
	@Column(name = "REGISTRATION_NO")
	private long registrationNo;
	@Column(name = "REGISTRATION_DATE")
	private Timestamp registrationDate;
	@Column(name = "GST_PERCENTAGE")
	private String gstPercentage;
	@Column(name = "GST_EFFECTIVE_DATE")
	private Timestamp gstEffectiveFrom;
	@Column(name = "GST_NO")
	private String gstNo;
	

}
