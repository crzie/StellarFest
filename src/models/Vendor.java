package models;

public class Vendor extends User {
	private String acceptedInvitations;
	
	public Vendor(String userId, String userEmail, String username, String userPassword, String userRole) {
		super(userId, userEmail, username, userPassword, userRole);
	}
	
	public void acceptInvitation(String eventId) {
		
	}
	
	public void viewAcceptedEvents(String email) {
		
	}
	
	public void manageVendor(String description, String product) {
		
	}
	
	public boolean checkManageVendorInput(String description, String product) {
		return false;
	}
}
