package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public void viewEventDetails(String eventId) {
		
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
}
