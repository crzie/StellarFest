package models;

import java.util.ArrayList;
import java.util.List;

public class Invitation {
	private String invitationId;
	private String eventId;
	private String userId;
	private String invitationStatus;
	private String invitationRole;
	
	public void sendInvitation(String email) {
		
	}
	
	public void acceptInvitation(String eventId) {
		
	}
	
	public List<Invitation> getInvitations(String email) {
		return new ArrayList<>();
	}
}
