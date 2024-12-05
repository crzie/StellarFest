package models;

import java.util.ArrayList;
import java.util.List;

public class Invitation {
	private String invitationId;
	private String eventId;
	private String userId;
	private String invitationStatus;
	private String invitationRole;
	
	public Invitation(String invitationId, String eventId, String userId, String invitationStatus,
			String invitationRole) {
		this.invitationId = invitationId;
		this.eventId = eventId;
		this.userId = userId;
		this.invitationStatus = invitationStatus;
		this.invitationRole = invitationRole;
	}

	public void sendInvitation(String email) {
		
	}
	
	public void acceptInvitation(String eventId) {
		
	}
	
	public List<Invitation> getInvitations(String email) {
		return new ArrayList<>();
	}

	public String getUserId() {
		return userId;
	}
	
	
}
