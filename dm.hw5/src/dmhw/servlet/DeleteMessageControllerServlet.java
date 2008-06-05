package dmhw.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;

public class DeleteMessageControllerServlet extends ControllerServlet {
	private static final long serialVersionUID = 4885316149052515878L;
 	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = getUser(request);
			if (user.isGuest()) {
				simpleErrRespone(response, "Guests are not allowed to modify content");
				return;
			}
			
			int msgid = Utils.toInteger(request.getParameter("msgid"));
			
			Message message = MessageManager.getMessage(msgid);
			if (message == null) {
				simpleErrRespone(response, "Specified message already not exists");
			}
			if (message.getRank() > user.getRank()) {
				simpleErrRespone(response, "Your rank is not enough to delete this message");
			}
			if (!message.getAuthor().equals(user.getUsername())) {
				simpleErrRespone(response, "You didn't wrote this message");
			}
			MessageManager.deleteMessage(msgid);
			simpleOkRespone(response, "The message was deleted successfully");
		} catch (Exception e) {
			this.internalError(request, response, e);
		}
	}   	  	    
}