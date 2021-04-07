package com.crm.app.user.profile.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PROJECT_INFO")
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
	
	@Column(name = "ASSIGNED_FROM")
	private String assingedFrom;
	@Column(name = "ASSIGNED_TO")
	private String assingedTo;
	@Column(name = "allocation_status")
	private String allocationStatus;
	
	private long allocatedBy;
	private Timestamp allocatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROFILE_ID", nullable = false)
	private Profile profile;
	
}
