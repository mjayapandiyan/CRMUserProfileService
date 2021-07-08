package com.crm.app.user.profile.model;

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
@Table(name = "TIMESHEET_DETAILS")
public class TimeSheet {
	@Id
	
	@Column(name="TIME_SHEET_ID")
	private long timeSheetId;
	
	@Column(name="USER_ID")
	private long userId;
	@Column(name="USER_NAME")
	private String username;
	@Column(name="PROJECT_CODE")
	private long projectCode;
	@Column(name="PROJECT_NAME")
	private String projectName;
	@Column(name="UTILIZATION_HOURS")
	private long utilizationHours;
	@Column(name="LEAVE_DATE")
	private String leaveDate;
	@Column(name="LEAVE_REASON")
	private String leave;
	
	@Column(name="TIMESHEET_DATE")
	private String timesheetDate;
}
