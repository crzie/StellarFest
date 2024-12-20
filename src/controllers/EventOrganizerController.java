package controllers;

import java.sql.PreparedStatement;
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

//mengakses ke model Event Organizer
public class EventOrganizerController {
	public static Response<Void> createEvent(String eventName, LocalDate date, String location, String description, String organizerId) {
		// validasi input (seperti name, date, dll)
		Response<Void> checkResponse = checkCreateEventInput(eventName, date, location, description);
		
		// validasi gagal
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
	
	public static Response<List<Guest>> getGuests(String eventId) {
		return EventOrganizer.getGuests(eventId);
	}
	
	public static Response<List<Vendor>> getVendors(String eventId) {
		return EventOrganizer.getVendors(eventId);
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

	public static Response<Void> checkAddVendorInput(String vendorId) {
		return EventOrganizer.checkAddVendorInput(vendorId);
	}
	
	public static Response<Void> checkAddGuestInput(String guestId) {
		return EventOrganizer.checkAddGuestInput(guestId);
	}
	
	public static Response<Void> editEventName(String eventId, String eventName) {
		// check if name isn't empty
		if(eventName.isEmpty()) {
			return Response.error("Event name must be filled");
		}
		return EventOrganizer.editEventName(eventId, eventName);
	}
}
