package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.Invitation;
import utils.Response;

//mengakses ke model Invitation
public class InvitationController {
	public static Response<Void> sendInvitation(String email, String eventId) {
		return Invitation.sendInvitation(email, eventId);
	}
	
	public static Response<Void> acceptInvitation(String userId, String eventId) {
		return Invitation.acceptInvitation(userId, eventId);
	}
	
	public static Response<List<Event>> getInvitations(String email) {
		return Invitation.getInvitations(email);
	}
}
