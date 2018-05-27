package com.kbmc.model;

import com.kbmc.model.User;

/**
 * UserDetails Model class
 * 
 * @author Zone24X7
 * 
 */
public class UserDetails {
	private User user;
	private String userrole;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserrole() {
		return userrole;
	}

	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}

}
