package com.crm.app.user.profile.constants;

public enum RoleNames {
	
	ROLE_ADMIN(1000),
	ROLE_DIRECTOR(2000),
	ROLE_EMPLOYEE(3000),
	ROLE_GUEST(4000);
	
	private final long roleId;  
	
	RoleNames(long roleId){
		this.roleId = roleId;
	}

	public long getRoleId() {
		return roleId;
	}
	
	
	
	/*
	 * admin ("ROLE_ADMIN"), director ("ROLE_DIRECTOR"), employee ("ROLE_EMPLOYEE"),
	 * guest ("ROLE_GUEST");
	 * 
	 * private final String name;
	 * 
	 * private RoleNames(String s) { name = s; }
	 * 
	 * public String getRoleName() { return this.name; }
	 */
}
