package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Response;

public class Guest extends User {
	private String acceptedInvitations;
	
	public Guest(String userId, String userEmail, String username, String userPassword, String userRole) {
		super(userId, userEmail, username, userPassword, userRole);
	}

	public Response<Void> acceptInvitation(String eventId) {
		Integer rowsAffected = db.executeUpdate(
				String.format("UPDATE invitations SET InvitationStatus = 1 "
						+ "WHERE UserId = '%s' AND EventId = '%s'", 
						userId, eventId)
			);
		
		if(rowsAffected == null) {
			return Response.error("Error accepting invitation");
		} else if(rowsAffected == 0) {
			return Response.error("There is no such invitation");
		}
		
		return Response.success("Invitation accepted", null);
	}
	
	public static Response<List<Invitation>> getInvitations(String userId) {
		// invitation status 0 = not accepted
		ResultSet rs = db.executeQuery(
				String.format("SELECT * FROM invitations "
						+ "WHERE UserId = '%s' AND InvitationRole = 'Guest' AND InvitationStatus = 0", userId)
			);
		ArrayList<Invitation> invitations = new ArrayList<>();
		
		if(rs == null) {
			return Response.error("Error fetching invitation data");
		}
		
		try {
			while(rs.next()) {
				String invitationId = rs.getString("InvitationId");
				String eventId = rs.getString("EventId");
				
				invitations.add(new Invitation(invitationId, eventId, userId, "Not Accepted", "Guest"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching invitations: " + e.getMessage());
		}
		
		return Response.success("Fetch invitation success", invitations);
	}
	
	public static Response<List<Event>> viewAcceptedEvents(String email) {
		ResultSet rs = db.executeQuery(
				String.format("SELECT EventId, EventName, EventDate, EventLocation, EventDescription, OrganizerId "
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
