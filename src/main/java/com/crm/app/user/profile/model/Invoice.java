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
@Table(name = "INVOICE_DETAILS")
public class Invoice {
	@Id
	@Column(name="invoice_Id")
	private long invoiceId;
	@Column(name="company_Id", nullable=false)
	private long companyId;
	@Column(name="company_name", nullable=false)
	private String companyName;
	
	@Column(name="project_Id", nullable=false)
	private long projectId;
	
	@Column(name="invoice_name", nullable=false)
	private String invoiceName;
	
	@Column(name="invoice_type", nullable=false)
	private String invoiceType;
	
	@Column(name="invoice_no", nullable=false)
	private long invoiceNo;
	
	@Column(name="status", nullable=false)
	private String status;
	
	@Column(name="invoice_date")
	private Timestamp createdDate;
	
	@Column(name="period_start_date")
	private Timestamp invoiceStartDate;
	@Column(name="period_end_date")
	private Timestamp invoiceEndDate;
	@Column(name="invoice_mount", nullable=false)
	private double invoiceAmount;
	@Column(name="gst_Amount")
	private double gstAmount;
	@Column(name="gst_Percentage")
	private String gstPercentage;
	private String remarks;
	

}
