package com.crm.app.user.profile.model;

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
@Table(name = "BANK_INFO")
public class Bank {
	
	@Id
	@Column(name = "BANK_ID", nullable = false)
	private long bankId;
	@Column(name = "BANK_NAME", nullable = false)
	private long bankName;
	@Column(name = "IFSC_CODE", nullable = false)
	private String ifscCode;
	private String branch;
	
	 @ManyToOne(fetch = FetchType.LAZY, optional = false)
	 @JoinColumn(name = "COMPANY_ID", referencedColumnName = "COMPANY_ID")
	private Company company;
}
