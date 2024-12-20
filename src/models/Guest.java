package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Response;

public class Guest extends User {
	private String acceptedInvitations;
	
	public Guest(String userId, String userEmail, String username, String userPassword) {
		super(userId, userEmail, username, userPassword, "Guest");
	}

	public Response<Void> acceptInvitation(String eventId) {
		return Invitation.acceptInvitation(this.userId, eventId);
	}
	
	// Menampilkan undangan yang sudah diterima berdasarkan email
	public static Response<List<Event>> viewAcceptedEvents(String email) {
		ResultSet rs = db.executeQuery(
				String.format("SELECT i.EventId, EventName, EventDate, EventLocation, EventDescription, OrganizerId "
						+ "FROM events e JOIN invitations i ON e.EventId = i.EventId "
						+ "JOIN users u ON u.UserId = i.UserId "
						+ "WHERE UserEmail = '%s' AND InvitationRole = 'Guest' AND InvitationStatus = 1", email)
			);
		ArrayList<Event> events = new ArrayList<>();
		
		if(rs == null) {
			return Response.error("Error fetching accepted events data");
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
			return Response.error("Error fetching accepted events: " + e.getMessage());
		}
		
		return Response.success("Fetch accepted events success", events);
	}
}
