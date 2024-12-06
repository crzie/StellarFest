package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.Guest;
import models.Invitation;
import models.User;
import models.Vendor;
import models.VendorProduct;
import utils.AuthUser;
import utils.Response;

public class VendorController {
	public Response<Void> acceptInvitation(String eventId) {
		User currentUser = AuthUser.get();
		
		if(currentUser.getUserRole().equals("Vendor")) {
			return ((Vendor)currentUser).acceptInvitation(eventId);
		}
		return Response.error("Error accepting invitation: user is not a vendor");
	}
	
	public static Response<List<Event>> viewAcceptedEvents(String email) {
		return Vendor.viewAcceptedEvents(email);
	}
	
	public Response<Void> manageVendor(String description, String product) {
		User currentUser = AuthUser.get();
		Response<Void> checkResponse = checkManageVendorInput(description, product);
		
		if(!checkResponse.isSuccess) {
			return Response.error(checkResponse.message);
		}
		if(!currentUser.getUserRole().equals("Vendor")) {
			return Response.error("Error managing vendor: user is not a vendor");
		}
		return ((Vendor)currentUser).manageVendor(description, product);
	}
	
	public static Response<VendorProduct> getProduct(String vendorId) {
		return Vendor.getProduct(vendorId);
	}
	
	public static Response<Void> checkManageVendorInput(String description, String product) {
		return Vendor.checkManageVendorInput(description, product);
	}
}
