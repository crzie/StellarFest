package models;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
	public void viewAllEvents() {
		
	}
	
	public void viewEventDetails(String eventId) {
		
	}
	
	public void deleteEvent(String eventId) {
		
	}
	
	public void deleteUser(String userId) {
		
	}
	
	public List<User> getAllUsers() {
		return new ArrayList<>();
	}
	
	public List<Event> getAllEvents() {
		return new ArrayList<>();
	}
	
	public List<User> getGuestsByTransaction(String eventId) {
		return new ArrayList<>();
	}
	
	public List<User> getVendorsByTransaction(String eventId) {
		return new ArrayList<>();
	}
}
