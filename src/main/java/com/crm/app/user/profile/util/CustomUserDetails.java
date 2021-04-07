package com.crm.app.user.profile.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.crm.app.user.profile.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@Component
public class CustomUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private String emailId;
	private long userId;
	private String username;
	@JsonIgnore
	private String password;
	private String firstName;
	private String lastName;
	private String logginTimeStamp;
	private String status;
	private long roleId;
	
	public CustomUserDetails(long userId, String emailId, String username, String password, String firstName, String lastName, String logginTimeStamp,String status, long roleId) {
		this.userId = userId;
		this.emailId = emailId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.logginTimeStamp = logginTimeStamp;
		this.status = status;
		this.roleId = roleId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static CustomUserDetails build(User user) {
		
		return new CustomUserDetails(
				user.getUserId(),
                user.getEmailId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.getLoggedInDate(),
                user.getStatus(),
                user.getRoleId()
        );
	}

	

}
