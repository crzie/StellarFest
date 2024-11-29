package controllers;

import models.User;
import utils.AuthUser;
import utils.Response;

public class UserController {
	public static Response<Void> register(String email, String name, String password, String role) {
		Response<User> emailResponse = getUserByEmail(email);
		Response<User> nameResponse = getUserByUsername(name);
		Response<Void> checkResponse = checkRegisterInput(email, name, password);
		
		if (!checkResponse.isSuccess) {
			return Response.error(checkResponse.message);
		}
		if (!emailResponse.isSuccess) {
			return Response.error(emailResponse.message);
		}
		if (!nameResponse.isSuccess) {
			return Response.error(nameResponse.message);
		}
		if (emailResponse.data != null) {
			return Response.error("Email is already used");
		}
		if (nameResponse.data != null) {
			return Response.error("Username already taken");
		}
		
		return User.register(email, name, password, role);
	}
	
	public static Response<Void> login(String email, String password) {
		if (email.isEmpty() || password.isEmpty()) {
			return Response.error("Email and Password must be filled");
		}
		return User.login(email, password);
	}
	
	public Response<Void> changeProfile(String email, String name, String oldPassword, String newPassword) {
		User currentUser = AuthUser.get();
		Response<User> emailResponse = getUserByEmail(email);
		Response<User> nameResponse = getUserByUsername(name);
		Response<Void> checkResponse = currentUser.checkChangeProfileInput(email, name, oldPassword, newPassword);
		
		if (!checkResponse.isSuccess) {
			return Response.error(checkResponse.message);
		}
		if (!emailResponse.isSuccess) {
			return Response.error(emailResponse.message);
		}
		if (!nameResponse.isSuccess) {
			return Response.error(nameResponse.message);
		}
		if (emailResponse.data != null) {
			return Response.error("Email is already used");
		}
		if (nameResponse.data != null) {
			return Response.error("Username already taken");
		}
		
		return currentUser.changeProfile(email, name, oldPassword, newPassword);
	}
	
	public static Response<User> getUserByEmail(String email) {
		return User.getUserByEmail(email);
	}
	
	public static Response<User> getUserByUsername(String name) {
		return User.getUserByUsername(name);
	}
	
	public static Response<Void> checkRegisterInput(String email, String name, String password) {
		return User.checkRegisterInput(email, name, password);
	}
	
	public Response<Void> checkChangeProfileInput(User user, String email, String name, String oldPassword, String newPassword) {
		return user.checkChangeProfileInput(email, name, oldPassword, newPassword);
	}
}
