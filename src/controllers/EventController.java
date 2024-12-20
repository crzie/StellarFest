package controllers;

import java.time.LocalDate;

import models.Event;
import models.EventOrganizer;
import utils.Response;

//mengakses ke model Event
public class EventController {
	public static Response<Void> createEvent(String eventName, LocalDate date, String location, String description, String organizerId) {
		return EventOrganizerController.createEvent(eventName, date, location, description, organizerId);
	}
	
	public static Response<Event> viewEventDetails(String eventId) {
		return Event.viewEventDetails(eventId);
	}
}
