package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Response;

public class Vendor extends User {
	private String acceptedInvitations;
	
	public Vendor(String userId, String userEmail, String username, String userPassword) {
		super(userId, userEmail, username, userPassword, "Vendor");
	}
	
	public Response<Void> acceptInvitation(String eventId) {
		return Invitation.acceptInvitation(this.userId, eventId);
	}
	
	public static Response<List<Event>> viewAcceptedEvents(String email) {
		ResultSet rs = db.executeQuery(
				String.format("SELECT EventId, EventName, EventDate, EventLocation, EventDescription, OrganizerId "
						+ "FROM events e JOIN invitations i ON e.EventId = i.EventId "
						+ "JOIN users u ON u.UserId = i.UserId "
						+ "WHERE UserEmail = '%s' AND InvitationRole = 'Vendor' AND InvitationStatus = 1", email)
			);
		ArrayList<Event> events = new ArrayList<>();
		
		if(rs == null) {
			return Response.error("Error fetching accepted events data");
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
			return Response.error("Error fetching accepted events: " + e.getMessage());
		}
		
		return Response.success("Fetch accepted events success", events);
	}
	
	public Response<Void> manageVendor(String description, String product) {
		PreparedStatement ps = 
				db.prepareStatement(
						"INSERT INTO vendorproducts (VendorId, ProductName, ProductDescription) "
						+ "VALUES (?, ?, ?) "
						+ "ON DUPLICATE KEY UPDATE "
						+ "ProductName = VALUES(ProductName), "
						+ "ProductDescription = VALUES(ProductDescription)"
					);
		
		try {
			ps.setString(1, this.userId);
			ps.setString(2, product);
			ps.setString(3, description);
			ps.executeUpdate();
			
			return Response.success("Manage vendor success", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Manage vendor failed: " + e.getMessage());
		}
	}
	
	public static Response<VendorProduct> getProduct(String vendorId) {
		ResultSet rs = db.executeQuery(
				String.format("SELECT ProductName, ProductDescription "
						+ "FROM vendorproducts "
						+ "WHERE VendorId = %s", 
						vendorId)
			);
		
		try {
			if(rs.next()) {
				String name = rs.getString("ProductName");
				String description = rs.getString("ProductDescription");
				
				return Response.success("Fetch product success", new VendorProduct(name, description));
			}
			return Response.success("No product", null);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.error("Error fetching product: " + e.getMessage());
		}
	}
	
	public static Response<Void> checkManageVendorInput(String description, String product) {
		if(description.isEmpty()) {
			return Response.error("Product description must be filled");
		}
		if(product.isEmpty()) {
			return Response.error("Product name must be filled");
		}
		if(description.length() > 200) {
			return Response.error("Product description is maximum 200 characters long");
		}
		
		return Response.success("", null);
	}
}
