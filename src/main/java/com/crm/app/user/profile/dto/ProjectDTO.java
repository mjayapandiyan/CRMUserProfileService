package com.crm.app.user.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ProjectDTO {
	
	private long projectId;
	private String projectName;
	private long clientId;
	private String clientName;
	private String assingedFrom;
	private String assingedTo;
	private String allocationStatus;
}
