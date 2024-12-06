package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controllers.EventController;
import utils.Response;

public class EventOrganizer extends User {
	private String eventsCreated;
	
	public static Response<Void> createEvent(String eventName, String date, String location, String description, String organizerId) {
		return Event.createEvent(eventName, date, location, description, organizerId);
	} 
	
	public static Response<List<Event>> viewOrganizedEvents(String userId) {
		ResultSet rs = db.executeQuery(
					String.format("SELECT * FROM events WHERE OrganizerId = '%s'", userId)
				);
		if (rs == null) return Response.error("Error fetching events");
		
		ArrayList<Event> events = new ArrayList<>();
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
	
	public static Response<Event> viewOrganizedEventDetails(String eventId) {
		return Event.viewEventDetails(eventId);
	}
	
	public static Response<List<Guest>> getGuests(String eventId) {
		// eventId to exclude guests already added to event
		ResultSet rs = db.executeQuery(
				String.format("SELECT DISTINCT UserId, UserEmail, Username "
						+ "FROM users u JOIN invitations i ON u.UserId = i.UserId "
						+ "WHERE UserRole = 'Guest' AND EventId != %s", 
						eventId
					)
			);
		ArrayList<Guest> guests = new ArrayList<>();
		
		if(rs == null) {
			return Response.error("Error fetching guests");
		}
		
		try {
			while(rs.next()) {
				String userId = rs.getString("UserId");
				String userEmail = rs.getString("UserEmail");
				String username = rs.getString("Username");
				
				guests.add(new Guest(userId, userEmail, username, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching guests: " + e.getMessage());
		}
		
		return Response.success("Fetch guests success", guests);
	}
	
	public static Response<List<Vendor>> getVendors(String eventId) {
		// eventId to exclude vendors already added to event
		ResultSet rs = db.executeQuery(
				String.format("SELECT DISTINCT UserId, UserEmail, Username "
						+ "FROM users u JOIN invitations i ON u.UserId = i.UserId "
						+ "WHERE UserRole = 'Vendor' AND EventId != %s", 
						eventId
					)
			);
		ArrayList<Vendor> vendors = new ArrayList<>();
		
		if(rs == null) {
			return Response.error("Error fetching vendors");
		}
		
		try {
			while(rs.next()) {
				String userId = rs.getString("UserId");
				String userEmail = rs.getString("UserEmail");
				String username = rs.getString("Username");
				
				vendors.add(new Vendor(userId, userEmail, username, null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching vendors: " + e.getMessage());
		}
		
		return Response.success("Fetch vendors success", vendors);
	}
	
	public static Response<List<Guest>> getGuestsByTransactionId(String eventId) {
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
	
	public static Response<List<Vendor>> getVendorsByTransactionId(String eventId) {
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
	
	public static Response<Void> checkCreateEventInput(String eventName, LocalDate date, String location, String description) {
		if(eventName.isEmpty()) {
			return Response.error("Event name must be filled");
		}
		if(date == null) {
			return Response.error("Event date must be filled");
		}
		if(location.isEmpty()) {
			return Response.error("Event location must be filled");
		}
		if(description.isEmpty()) {
			return Response.error("Event description must be filled");
		}
		if(location.length() < 5) {
			return Response.error("Event location must be at least 5 characters");
		}
		if(description.length() < 200) {
			return Response.error("Event description is too long (max 200 characters)");
		}
		if(date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now())) {
			return Response.error("Event date must be at future");
		}
		
		return Response.success("", null);
	}
	
	public static Response<Void> checkAddVendorInput(String vendorId) {
		if(vendorId.isEmpty()) {
			return Response.error("You must choose at least 1 vendor");
		}
		return Response.success("", null);
	}
	
	public static Response<Void> checkAddGuestInput(String guestId) {
		if(guestId.isEmpty()) {
			return Response.error("You must choose at least 1 guest");
		}
		return Response.success("", null);
	}
	
	public static Response<Void> editEventName(String eventId, String eventName) {
		PreparedStatement ps = db.prepareStatement(
				"UPDATE events "
				+ "SET EventName = ? "
				+ "WHERE EventId = ?"
			);
		
		try {
			ps.setString(1, eventName);
			ps.setString(2, eventId);
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected == 0) {
				return Response.error("There is no such event");
			} else {
				return Response.success("Edit event name success", null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error editing event name: " + e.getMessage());
		}
	}
}
