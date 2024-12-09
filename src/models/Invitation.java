package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Connect;
import utils.Response;

public class Invitation {
	private String invitationId;
	private String eventId;
	private String userId;
	private String invitationStatus;
	private String invitationRole;
	
	private static Connect db = Connect.getInstance(); 
	
	public Invitation(String invitationId, String eventId, String userId, String invitationStatus,
			String invitationRole) {
		this.invitationId = invitationId;
		this.eventId = eventId;
		this.userId = userId;
		this.invitationStatus = invitationStatus;
		this.invitationRole = invitationRole;
	}

	public static Response<Void> sendInvitation(String email, String eventId) {
		String invitationId = getNextIncrementalId();
		
		if(invitationId == null) return Response.error("Send invitation failed");
		
		Integer rowsAffected = db.executeUpdate(
				String.format(
					"INSERT INTO invitations (InvitationId, EventId, UserId, InvitationStatus, InvitationRole) "
					+ "SELECT '%s', '%s', UserId, 0, UserRole "
					+ "FROM users "
					+ "WHERE UserEmail = '%s'",
					invitationId, eventId, email
				)
			);
		
		if(rowsAffected == null) {
			return Response.error("Error sending invitation");
		} else if(rowsAffected == 0) {
			return Response.error("There is no such user");
		}
		
		return Response.success("Invitation sent successfully", null);
	}
	
	public static Response<Void> acceptInvitation(String userId, String eventId) {
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
	
	public static Response<List<Invitation>> getInvitations(String email) {
		// invitation status 0 = not accepted
		ResultSet rs = db.executeQuery(
				String.format("SELECT InvitationId, EventId, i.UserId, InvitationRole "
						+ "FROM invitations i JOIN users u ON i.UserId = u.UserId "
						+ "WHERE UserEmail = '%s' AND InvitationStatus = 0", email)
			);
		ArrayList<Invitation> invitations = new ArrayList<>();
		
		if(rs == null) {
			return Response.error("Error fetching invitation data");
		}
		
		try {
			while(rs.next()) {
				String invitationId = rs.getString("InvitationId");
				String eventId = rs.getString("EventId");
				String userId = rs.getString("UserId");
				String invitationRole = rs.getString("InvitationRole");
				
				invitations.add(new Invitation(invitationId, eventId, userId, "Not Accepted", invitationRole));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching invitations: " + e.getMessage());
		}
		
		return Response.success("Fetch invitation success", invitations);
	}
	
	private static String getNextIncrementalId() {
		// get largest id in db
		ResultSet rs = db.executeQuery(
				"SELECT InvitationId "
				+ "FROM invitations "
				+ "ORDER BY InvitationId DESC "
				+ "LIMIT 1"
			);
		
		if(rs == null) {
			// if other db error, not due to empty table
			return null;
		}
		
		String nextId;
		try {
			if(rs.next()) {
				String largestId = rs.getString("InvitationId");
				int nextIdNumber = Integer.valueOf(largestId.substring(2)) + 1;
				nextId = String.format("IN%08d", nextIdNumber);
			} else {
				// if table is empty
				nextId = "IN00000001";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			nextId = null;
		}
		return nextId;
	}

	public String getUserId() {
		return userId;
	}
		
	public String getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(String invitationId) {
		this.invitationId = invitationId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getInvitationStatus() {
		return invitationStatus;
	}

	public void setInvitationStatus(String invitationStatus) {
		this.invitationStatus = invitationStatus;
	}

	public String getInvitationRole() {
		return invitationRole;
	}

	public void setInvitationRole(String invitationRole) {
		this.invitationRole = invitationRole;
	}

	public void print() {
		System.out.println(this.invitationId);
		System.out.println(this.eventId);
		System.out.println(this.invitationStatus);
		System.out.println(this.invitationRole);
		System.out.println();
	}
	
}
