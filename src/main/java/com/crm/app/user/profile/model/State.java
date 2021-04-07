package com.crm.app.user.profile.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name="STATE")
public class State implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "STATE_ID")
	private long stateId;
	@Column(name = "STATE_NAME", nullable=false)
	private String stateName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	private Country country;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "state")
	private List<City> city;
	
}