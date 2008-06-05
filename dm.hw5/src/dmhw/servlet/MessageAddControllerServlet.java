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
				simpleErrRespone(response, "Guests are not allowed to modify content");
				return;
			}
	
			String title = request.getParameter("title");
			String type = request.getParameter("type");
			
			if (Utils.isNullOrEmpty(type)) {
				simpleErrRespone(response, "Type should be non empty string");
				return;
			}
			if (type.length() > 15) {
				simpleErrRespone(response, "Type length should be less or equal that 15");
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
			
			simpleOkRespone(response, "Your message has been posted successfully");
		} catch (Exception e) {
			internalError(request, response, e);
		}
	}   	  	    
}