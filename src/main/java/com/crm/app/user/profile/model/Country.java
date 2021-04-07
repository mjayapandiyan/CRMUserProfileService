package com.crm.app.user.profile.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "COUNTRY")
public class Country implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "COUNTRY_ID")
	private long countryId;
	@Column(name = "COUNTRY_NAME", nullable=false)
	private String countryName;
	@Column(name = "COUNTRY_CODE", nullable=false)
	private String countryCode;
	@Column(name = "PHONE_CODE", nullable=false)
	private long phoneCode;
	
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<State> state;
	
}
