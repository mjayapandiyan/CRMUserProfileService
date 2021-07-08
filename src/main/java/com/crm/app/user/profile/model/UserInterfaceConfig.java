package com.crm.app.user.profile.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
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
@Table(name = "CONFIG_PARAM")

public class UserInterfaceConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CONFIG_ID")
	private long configId;
	private String path;
	private String component;
	private String value;
	private String createdBy;
	private String modifiedBy;
	private Timestamp createdAt;
	private Timestamp modifiedAt;
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID")
    private Role role;
	
	
}
