package com.crm.app.user.profile.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Column(name = "USER_ID", nullable = false)
	private long userId;
	
	@Column(name = "DIRECTOR_NAME", nullable = false)
	private String directorName;
	@Column(name = "DIRECTOR_ID", nullable = false)
	private long directorId;
	@Column(name = "IS_TERMINATED")
	private String isTerminated;
	@Column(name = "DATE_OF_EXIT")
	private Timestamp dateOfExit;
	@Column(name = "IS_TERMINATION_LETTER_ISSUED")
	private String isTerminationLetterIssued;
	@Column(name = "TERMINATION_INITIATED_BY")
	private String terminationInitiatedBy;
	@Column(name = "TERMINATION_INITIATED_DATE")
	private Timestamp terminationInitiatedDate;

	private String remarks;
	private String companyName;
	private long companyId;
	private String url;
	
    @OneToOne(targetEntity = User.class, mappedBy = "profile")
    private User user;
    
	@OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Project> project;
	
}
