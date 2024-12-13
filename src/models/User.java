package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.AuthUser;
import utils.Connect;
import utils.Response;

public class User {
	protected String userId;
	protected String userEmail;
	protected String username;
	protected String userPassword;
	protected String userRole; 
	
	protected static Connect db = Connect.getInstance();
	
	protected User() {}
	
	protected User(String userId, String userEmail, String username, String userPassword, String userRole) {
		this.userId = userId;
		this.userEmail = userEmail;
		this.username = username;
		this.userPassword = userPassword;
		this.userRole = userRole;
	}
	
	public static Response<Void> register(String email, String name, String password, String role) {
		String userId = getNextIncrementalId();
		
		PreparedStatement ps = 
				db.prepareStatement(
						"INSERT INTO users(UserId, UserEmail, Username, UserPassword, UserRole) "
						+ "VALUES (?, ?, ?, ?, ?)"
					);
		
		if(userId == null) return Response.error("Register failed");
		try {
			ps.setString(1, userId);
			ps.setString(2, email);
			ps.setString(3, name);
			ps.setString(4, password);
			ps.setString(5, role);	
			ps.executeUpdate();
			
			return Response.success("Register success", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Register failed: " + e.getMessage());
		}
	}
	
	public static Response<Void> login(String email, String password) {
		Response<User> userResponse = getUserByEmail(email);
		User user = userResponse.data;
		
		if (!userResponse.isSuccess) {
			return Response.error("Login failed: " + userResponse.message);
		}
		
		if (user == null || !user.userPassword.equals(password)) {
			return Response.error("Invalid credentials");
		}
		
		AuthUser.set(user);
		return Response.success("Login success", null);
	}
	
	public Response<Void> changeProfile(String email, String name, String oldPassword, String newPassword) {
		PreparedStatement ps = 
				db.prepareStatement(
						"UPDATE users "
						+ "SET UserEmail = ?, Username = ?, UserPassword = ? "
						+ "WHERE UserId = ?"
					);
		
		try {
			ps.setString(1, email);
			ps.setString(2, name);
			ps.setString(3, newPassword);
			ps.setString(4, this.userId);
			ps.executeUpdate();
			
			return Response.success("Change profile success", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Change profile failed: " + e.getMessage());
		}
	}
	
	public static Response<User> getUserByEmail(String email) {
		PreparedStatement ps = db.prepareStatement("SELECT * FROM users WHERE UserEmail = ?");
		ResultSet rs;
		
		try {
			ps.setString(1, email);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching user: " + e.getMessage());
		}
		
		try {
			if(rs.next()) {
				String userId = rs.getString("UserId");
				String userEmail = rs.getString("UserEmail");
				String username = rs.getString("Username");
				String userPassword = rs.getString("UserPassword");
				String userRole = rs.getString("UserRole");
				
				User user = new User(userId, userEmail, username, userPassword, userRole);
				return Response.success("Fetch user success", user);
			} else {
				// user not found
				return Response.success("User not found", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("Error fetching user: " + e.getMessage());
		}
	}

	public static Response<User> getUserByUsername(String name) {
		PreparedStatement ps = db.prepareStatement("SELECT * FROM users WHERE UserName = ?");
		ResultSet rs;
		
		try {
			ps.setString(1, name);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching user: " + e.getMessage());
		}

		try {
			if(rs.next()) {
				String userId = rs.getString("UserId");
				String userEmail = rs.getString("UserEmail");
				String username = rs.getString("Username");
				String userPassword = rs.getString("UserPassword");
				String userRole = rs.getString("UserRole");
				
				User user = new User(userId, userEmail, username, userPassword, userRole);
				return Response.success("Fetch user success", user);
			} else {
				// user not found
				return Response.success("User not found", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("Error fetching user: " + e.getMessage());
		}
	}
	
	public static Response<Void> checkRegisterInput(String email, String name, String password) {
		if(email.isEmpty()) {
			return Response.error("Email must be filled");
		}
		if(name.isEmpty()) {
			return Response.error("Username must be filled");
		}
		if(password.isEmpty()) {
			return Response.error("Password must be filled");
		}
		if(password.length() < 5) {
			return Response.error("Password must be more than 5 characters");
		}
				
		return Response.success("", null);
	}
	
	public Response<Void> checkChangeProfileInput(String email, String name, String oldPassword, String newPassword) {
		if(email.isEmpty()) {
			return Response.error("Email must be filled");
		}
		if(name.isEmpty()) {
			return Response.error("Username must be filled");
		}
		if(newPassword.length() < 5) {
			return Response.error("Password must be at least 5 characters");
		}
		if(email.equals(this.userEmail) || name.equals(this.username)) {
			return Response.error("Email and username must be different from the old one");
		}
		if(oldPassword.equals(newPassword)) {
			return Response.error("Password must be different from the old password");
		}
		if(!oldPassword.equals(this.userPassword)) {
			return Response.error("Old Password is wrong");
		}
		
		return Response.success("", null);
	}
	
	private static String getNextIncrementalId() {
		// get largest id in db
		ResultSet rs = db.executeQuery(
				"SELECT UserId "
				+ "FROM users "
				+ "ORDER BY UserId DESC "
				+ "LIMIT 1"
			);
		
		if(rs == null) {
			// if other db error, not due to empty table
			return null;
		}
		
		String nextId;
		try {
			if(rs.next()) {
				String largestId = rs.getString("UserId");
				int nextIdNumber = Integer.valueOf(largestId.substring(2)) + 1;
				nextId = String.format("US%08d", nextIdNumber);
			} else {
				// if table is empty
				nextId = "US00000001";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			nextId = null;
		}
		return nextId;
	}
	
	public static Response<User> getUserById(String param) {
		PreparedStatement ps = db.prepareStatement("SELECT * FROM users WHERE UserId = ?");
		ResultSet rs;
		
		try {
			ps.setString(1, param);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching user: " + e.getMessage());
		}
		
		try {
			if(rs.next()) {
				String userId = rs.getString("UserId");
				String userEmail = rs.getString("UserEmail");
				String username = rs.getString("Username");
				String userPassword = rs.getString("UserPassword");
				String userRole = rs.getString("UserRole");
				
				User user = new User(userId, userEmail, username, userPassword, userRole);
				return Response.success("Fetch user success", user);
			} else {
				// user not found
				return Response.success("User not found", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("Error fetching user: " + e.getMessage());
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getUserRole() {
		return userRole;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}
	
	
}
