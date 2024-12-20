package utils;

import models.User;

public class AuthUser {
	// save current user (user that currently logged in)
	private static User user;
	
	private AuthUser() {};
	
	public static User get() {
		return user;
	}
	
	public static void set(User u) {
		user = u;
	}
	
	// for log out
	public static void clear() {
		user = null;
	}
}
