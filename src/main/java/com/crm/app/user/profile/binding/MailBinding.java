package com.crm.app.user.profile.binding;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

import com.crm.app.user.profile.constants.UserConstant;

public interface MailBinding {
	
	@Output(UserConstant.SEND_MAIL_NOTIFICATION_CHANNEL)
    MessageChannel triggerEmailNotification();
}
