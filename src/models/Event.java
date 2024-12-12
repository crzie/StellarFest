package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Connect;
import utils.Response;

public class Event {
	private String eventId;
	private String eventName;
	private String eventDate;
	private String eventLocation;
	private String eventDescription;
	private String organizerId;
	
	private static Connect db = Connect.getInstance();
	
	public Event(String eventId, String eventName, String eventDate, String eventLocation, String eventDescription,
			String organizerId) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventLocation = eventLocation;
		this.eventDescription = eventDescription;
		this.organizerId = organizerId;
	}

	public static Response<Void> createEvent(String eventName, String date, String location, String description, String organizerId) {
		String eventId = getNextIncrementalId();
		
		PreparedStatement ps = 
				db.prepareStatement(
						"INSERT INTO events(EventId, EventName, EventDate, EventLocation, EventDescription, OrganizerId) "
						+ "VALUES (?, ?, ?, ?, ?, ?)"
					);
		
		if(eventId == null) return Response.error("Create event failed");
		try {
			ps.setString(1, eventId);
			ps.setString(2, eventName);
			ps.setString(3, date);
			ps.setString(4, location);
			ps.setString(5, description);	
			ps.setString(6, organizerId);
			ps.executeUpdate();
			
			return Response.success("Create event success", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Create event failed: " + e.getMessage());
		}
	}
	
	public static Response<List<Event>> viewAllEvents() {
		ResultSet rs = db.executeQuery("SELECT * FROM events");
		ArrayList<Event> events = new ArrayList<>();
		
		if(rs == null) {
			return Response.error("Error fetching event data");
		}
		
		try {
			while(rs.next()) {
				String eventId = rs.getString("EventId");
				String eventName = rs.getString("EventName");
				String eventDate = rs.getString("EventDate");
				String eventLocation = rs.getString("EventLocation");
				String eventDescription = rs.getString("EventDescription");
				String organizerId = rs.getString("OrganizerId");
				
				events.add(new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching events: " + e.getMessage());
		}
		
		return Response.success("Fetch event success", events);
	}
	
	
	public static Response<Event> viewEventDetails(String eventId) {
		ResultSet rs = db.executeQuery(
				String.format("SELECT * FROM events WHERE EventId = '%s'", eventId)
			);
		if (rs == null) return Response.error("Error fetching event details");
		
		try {
			if(rs.next()) {
				String eventName = rs.getString("EventName");
				String eventDate = rs.getString("EventDate");
				String eventLocation = rs.getString("EventLocation");
				String eventDescription = rs.getString("EventDescription");
				String organizerId = rs.getString("OrganizerId");
				
				Event event = new Event(eventId, eventName, eventDate, eventLocation, eventDescription, organizerId);
				return Response.success("Fetch event detail success", event);
			} else {
				return Response.error("Event doesn't exist");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching event details: " + e.getMessage());
		}
	}
	
	private static String getNextIncrementalId() {
		// get largest id in db
		ResultSet rs = db.executeQuery(
				"SELECT EventId "
				+ "FROM events "
				+ "ORDER BY EventId DESC "
				+ "LIMIT 1"
			);
		
		if(rs == null) {
			// if other db error, not due to empty table
			return null;
		}
		
		String nextId;
		try {
			if(rs.next()) {
				String largestId = rs.getString("EventId");
				int nextIdNumber = Integer.valueOf(largestId.substring(2)) + 1;
				nextId = String.format("EV%08d", nextIdNumber);
			} else {
				// if table is empty
				nextId = "EV00000001";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			nextId = null;
		}
		return nextId;
	}
	
	public String getEventId() {
		return eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public String getEventDate() {
		return eventDate;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public String getEventDescription() {
		return eventDescription;
	}
	
	public String getOrganizerId() {
		return organizerId;
	}
}
