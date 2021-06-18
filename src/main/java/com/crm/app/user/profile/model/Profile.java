package com.crm.app.user.profile.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "USER_PROFILE")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_id_sequence")
	@SequenceGenerator(name = "user_profile_id_sequence", allocationSize = 1, initialValue = 1000, sequenceName = "USER_PROFILE_ID_SEQ")

	@Column(name = "PROFILE_ID")
	private long id;
	
	@Column(name = "user_Id", nullable = false)
	private long userId;
	
	@Column(name = "director_Name", nullable = false)
	private String directorName;
	@Column(name = "director_Id", nullable = false)
	private long directorId;
	@Column(name = "is_Terminated")
	private String isTerminated;
	@Column(name = "date_Of_Exit")
	private Timestamp dateOfExit;
	@Column(name = "isTermination_Letter_Issued")
	private String isTerminationLetterIssued;
	@Column(name = "termination_Initiated_By")
	private String terminationInitiatedBy;
	@Column(name = "termination_Initiated_Date")
	private Timestamp terminationInitiatedDate;

	private String remarks;
	private String companyName;
	private long companyId;
	private String url;
	
	@Column(name = "assigned_from")
	private String assignedfrom;
	@Column(name = "assigned_to")
	private String assignedTo;
	@Column(name = "allocation_status")
	private String allocationStatus;
	@Column(name = "allocated_by")
	private long allocatedBy;
	@Column(name = "allocated_date")
	private Timestamp allocatedDate;
	
	@OneToOne(targetEntity = User.class, mappedBy = "profile")
    private User user;
	
	@Column(name = "project_Id",nullable = false)
	private long projectId;
	
}
