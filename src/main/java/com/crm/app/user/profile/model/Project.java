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
@Table(name = "PROJECTS_DETAILS")
public class Project {
	
	@Id
	@Column(name="PROJECT_ID")
	private long projectId;
	
	@Column(name="PROJECT_NAME")
	private String projectName;
	
	@Column(name="CLIENT_ID")
	private long clientId;
	
	@Column(name="CLIENT_NAME")
	private String clientName;
	
	@Column(name="CONTRACT_SIGNED_DATE")
	private Timestamp contractSignedDate;
	
	@Column(name="CONTRACT_END_DATE")
	private Timestamp contractEndDate;
	
	@Column(name="BILLING_AMOUNT_USD")
	private long billingAmount;
	private String status;
	@Column(name="ALLOCATION_TYPE")
	private String allocationType;
	private Timestamp createdAt;
	private String createdBy;
	
	private String modifiedBy;
	private Timestamp updatedAt;
	
}
