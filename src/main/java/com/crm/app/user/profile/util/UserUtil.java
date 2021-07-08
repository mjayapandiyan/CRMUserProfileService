package com.crm.app.user.profile.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {

	public String getCurrentTimeStamp() {
		SimpleDateFormat sfdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		return sfdate.format(new Date());
	}
}
