package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Response;

public class Admin extends User {
	public static Response<List<Event>> viewAllEvents() {
		return getAllEvents();
	}
	
	public static Response<Event> viewEventDetails(String eventId) {
		return Event.viewEventDetails(eventId);
	}
	
	public static Response<Void> deleteEvent(String eventId) {
		// event id not written by user, so no prepared statement required
		Integer rowsAffected = db.executeUpdate(
				String.format("DELETE FROM events WHERE EventId = '%s'", eventId)
			);
		
		if(rowsAffected == null) {
			return Response.error("Error deleting event");
		} else if(rowsAffected == 0) {
			return Response.error("There is no such event");
		}
		
		return Response.success("Delete event success", null);
	}
	
	public static Response<Void> deleteUser(String userId) {
		Integer rowsAffected = db.executeUpdate(
				String.format("DELETE FROM users WHERE UserId = '%s'", userId)
			);
		
		if(rowsAffected == null) {
			return Response.error("Error deleting user");
		} else if(rowsAffected == 0) {
			return Response.error("There is no such user");
		}
		
		return Response.success("Delete user success", null);
	}
	
	public static Response<List<User>> getAllUsers() {
		ResultSet rs = db.executeQuery("SELECT * FROM users");
		ArrayList<User> users = new ArrayList<>();
		
		if(rs == null) {
			return Response.error("Error fetching user data");
		}
		
		try {
			while(rs.next()) {
				String userId = rs.getString("UserId");
				String userEmail = rs.getString("UserEmail");
				String username = rs.getString("Username");
				String userRole = rs.getString("UserRole");
				
				users.add(new User(userId, userEmail, username, null, userRole));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching users: " + e.getMessage());
		}
		
		return Response.success("Fetch user success", users);
	}
	
	public static Response<List<Event>> getAllEvents() {
		ResultSet rs = db.executeQuery("SELECT * FROM events");
		ArrayList<Event> events = new ArrayList<>();
		
		if(rs == null) {
			return Response.error("Error fetching event data");
		}
		
		try {
			while(rs.next()) {
				String eventId = rs.getString("EventId");
				String eventName = rs.getString("EventName");
				String eventDate = rs.getString("EventDate");
				String eventLocation = rs.getString("EventLocation");
				String eventDescription = rs.getString("EventDescription");
				String organizerId = rs.getString("OrganizerId");
				
				events.add(new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching events: " + e.getMessage());
		}
		
		return Response.success("Fetch event success", events);
	}
	
	public static Response<List<Guest>> getGuestsByTransaction(String eventId) {
		ResultSet rs = db.executeQuery(
				String.format("SELECT DISTINCT UserId, UserEmail, Username "
						+ "FROM users u JOIN invitations i ON u.UserId = i.UserId "
						+ "WHERE EventId = '%s' AND InvitationRole = 'Guest' AND InvitationStatus = 1", 
						eventId)
			);
		if (rs == null) return Response.error("Error fetching event guests");
		
		ArrayList<Guest> guests = new ArrayList<>();
		try {
			while(rs.next()) {
				String userId = rs.getString("UserId");
				String userEmail = rs.getString("UserEmail");
				String username = rs.getString("Username");
				
				guests.add(new Guest(userId, userEmail, username, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching event guests: " + e.getMessage());
		}
		
		return Response.success("Fetch event guests success", guests);
	}
	
	public static Response<List<Vendor>> getVendorsByTransaction(String eventId) {
		ResultSet rs = db.executeQuery(
				String.format("SELECT DISTINCT UserId, UserEmail, Username "
						+ "FROM users u JOIN invitations i ON u.UserId = i.UserId "
						+ "WHERE EventId = '%s' AND InvitationRole = 'Vendor' AND InvitationStatus = 1",
						eventId)
			);
		if (rs == null) return Response.error("Error fetching event vendors");
		
		ArrayList<Vendor> vendors = new ArrayList<>();
		try {
			while(rs.next()) {
				String userId = rs.getString("UserId");
				String userEmail = rs.getString("UserEmail");
				String username = rs.getString("Username");
				
				vendors.add(new Vendor(userId, userEmail, username, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching event vendors: " + e.getMessage());
		}
		
		return Response.success("Fetch event vendors success", vendors);
	}
}
