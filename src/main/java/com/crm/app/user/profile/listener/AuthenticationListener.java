package com.crm.app.user.profile.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import com.crm.app.user.profile.service.UserService;
import com.crm.app.user.profile.util.CustomUserDetails;
import com.crm.app.user.profile.util.UserUtil;


@Component
public class AuthenticationListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserUtil usrUtil;
	
	  @Override
	  public void onApplicationEvent(final AuthenticationSuccessEvent event){
		  
	        CustomUserDetails userDetails = (CustomUserDetails) event.getAuthentication().getPrincipal();
	        userService.updateLoginTime(usrUtil.getCurrentTimeStamp(), userDetails.getEmailId());
	  }

	}