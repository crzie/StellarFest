package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.EventOrganizer;
import models.Guest;
import models.User;
import models.Vendor;
import utils.Response;

public class EventOrganizerController {
	public static Response<Void> createEvent(String eventName, LocalDate date, String location, String description, String organizerId) {
		Response<Void> checkResponse = checkCreateEventInput(eventName, date, location, description);
		if(!checkResponse.isSuccess) {
			return Response.error(checkResponse.message);
		}
		
		return EventOrganizer.createEvent(eventName, date.toString(), location, description, organizerId);
	}
	
	public static Response<List<Event>> viewOrganizedEvents(String userId) {
		return EventOrganizer.viewOrganizedEvents(userId);
	}
	
	public static Response<Event> viewOrganizedEventDetails(String eventId) {
		return EventOrganizer.viewOrganizedEventDetails(eventId);
	}
	
	public List<User> getGuests() {
		return new ArrayList<>();
	}
	
	public List<User> getVendors() {
		return new ArrayList<>();
	}
	
	public static Response<List<Guest>> getGuestsByTransactionId(String eventId) {
		return EventOrganizer.getGuestsByTransactionId(eventId);
	}
	
	public static Response<List<Vendor>> getVendorsByTransactionId(String eventId) {
		return EventOrganizer.getVendorsByTransactionId(eventId);
	}
	
	public static Response<Void> checkCreateEventInput(String eventName, LocalDate date, String location, String description) {
		return EventOrganizer.checkCreateEventInput(eventName, date, location, description);
	}
	
	public boolean checkAddVendorInput(String vendorId) {
		return false;
	}
	
	public boolean checkAddGuestInput(String guestId) {
		return false;
	}
	
	public void editEventName(String eventId, String eventName) {
		
	}
}
