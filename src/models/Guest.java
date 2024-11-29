package models;

public class Guest extends User {
	private String acceptedInvitations;
	
	public Guest(String userId, String userEmail, String username, String userPassword, String userRole) {
		super(userId, userEmail, username, userPassword, userRole);
	}

	public void acceptInvitation(String eventId) {
		
	}
	
	public void viewAcceptedEvents(String email) {
		
	}
}
