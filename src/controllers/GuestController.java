package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.Guest;
import models.Invitation;
import models.User;
import utils.AuthUser;
import utils.Response;

public class GuestController {
	public Response<Void> acceptInvitation(String eventId) {
		User currentUser = AuthUser.get();
		
		if(currentUser.getUserRole().equals("Guest")) {
			return ((Guest)currentUser).acceptInvitation(eventId);
		}
		return Response.error("Error accepting invitation: user is not a guest");
	}
	
	public Response<List<Event>> viewAcceptedEvents(String email) {
		return Guest.viewAcceptedEvents(email);
	}
}
