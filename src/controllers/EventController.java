package controllers;

import java.time.LocalDate;

import models.Event;
import models.EventOrganizer;
import utils.Response;

public class EventController {
	public static Response<Void> createEvent(String eventName, LocalDate date, String location, String description, String organizerId) {
		return EventOrganizerController.createEvent(eventName, date, location, description, organizerId);
	}
}
