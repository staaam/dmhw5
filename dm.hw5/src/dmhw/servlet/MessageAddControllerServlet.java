package dmhw.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;

public class MessageAddControllerServlet extends ControllerServlet {
	private static final long serialVersionUID = 5292911530212301687L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = getUser(request);
			if (user.isGuest()) {
				this.responseMessage(request, response, "No permissions", "Guests are not allowed to modify content", "return", Pages.boardView);
				return;
			}
	
			String title = request.getParameter("title");
			String type = request.getParameter("type");
			
			if (Utils.isNullOrEmpty(type)) {
				this.responseMessage(request, response, "Type is empty", "Type should be non empty string", "Try another type", Pages.newmessage);
				return;
			}
			if (type.length() > 15) {
				this.responseMessage(request, response, "Type is too long", "Type length should be less or equal that 15", "Try another type", Pages.newmessage);
				return;
			}
	
			Integer rank = Utils.toInteger(request.getParameter("rank"));
	
			String body = request.getParameter("body");
			
			// TODO: add checks for correctness!!
			
			Message msg = new Message();
			msg.setTitle(title);
			msg.setType(type);
			msg.setRank(rank);
			msg.setStartTime(Utils.getDate(request, "s"));
			msg.setEndTime(Utils.getDate(request, "e"));
			msg.setAuthor(user.getUsername());
			msg.setBody(body);
			
			MessageManager.addMessage(msg);
			
			this.responseMessage(request, response, "Message Added Successful ", "Your have been posted successfully", "go back to login page", Pages.index);
		} catch (Exception e) {
			internalError(request, response, e);
		}
	}   	  	    
}