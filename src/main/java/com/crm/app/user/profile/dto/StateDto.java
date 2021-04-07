package com.crm.app.user.profile.dto;

import java.io.Serializable;

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

public class StateDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String stateName;
	private long stateId;

}
