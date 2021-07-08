package com.crm.app.user.profile.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "ROLE_MST")

public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROLE_ID", nullable = false)
	private long roleId;
	
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	@Column(name="ROLE_DESC", length = 60, nullable = false)
	@NotBlank
	private String roleName;
	private String createdBy;
	private String modifiedBy;
	
	@OneToMany(mappedBy = "role", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserInterfaceConfig> config;
	
}
