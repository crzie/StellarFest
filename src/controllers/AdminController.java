package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Admin;
import models.Event;
import models.Guest;
import models.User;
import models.Vendor;
import utils.Response;

//mengakses ke model Admin
public class AdminController {
	public static Response<List<Event>> viewAllEvents() {
		return Admin.viewAllEvents();
	}
	
	public static Response<Event> viewEventDetails(String eventId) {
		return Admin.viewEventDetails(eventId);
	}
	
	public static Response<Void> deleteEvent(String eventId) {
		return Admin.deleteEvent(eventId);
	}
	
	public static Response<Void> deleteUser(String userId) {
		return Admin.deleteUser(userId);
	}
	
	public static Response<List<User>> getAllUsers() {
		return Admin.getAllUsers();
	}
	
	public static Response<List<Event>> getAllEvents() {
		return Admin.viewAllEvents();
	}
	
	public static Response<List<Guest>> getGuestsByTransactionID(String eventId) {
		return Admin.getGuestsByTransaction(eventId);
	}
	
	public static Response<List<Vendor>> getVendorsByTransactionID(String eventId) {
		return Admin.getVendorsByTransaction(eventId);
	}
}
