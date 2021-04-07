package com.crm.app.user.profile.model;

import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name = "REPORT_DETAILS")
public class Reports {
	
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "report_id_sequence")
	@SequenceGenerator (name = "report_id_sequence", allocationSize = 1, initialValue = 1000, sequenceName = "report_id_sequence")
	@Column(name = "REPORT_ID")
	private long reportId;
	@Column(name = "USER_ID")
	private long userId;
	@Column(name = "COMPANY_ID")
	private long companyId;
	@Column(name = "PROJECT_ID")
	private long projectId;
	@Column(name = "PROJECT_NAME")
	private String projectName;
	@Column(name = "REPORT_TYPE")
	private String reportType;
	@Column(name = "REPORT_NAME")
	private String reportName;
	private Blob attachment;
	@Column(name = "GENERATED_DATE")
	private Timestamp generatedDate;
	@Column(name = "GENERATE_BY")
	private String generateBy;
	
}
