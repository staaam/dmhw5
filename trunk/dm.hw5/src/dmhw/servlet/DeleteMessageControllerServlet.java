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
				this.responseMessage(request, response, "No permissions", "Guests are not allowed to modify content", "return", Pages.boardView);
				return;
			}
			
			int msgid = Utils.toInteger(request.getParameter("msgid"));
			
			Message message = MessageManager.getMessage(msgid);
			if (message == null) {
				this.responseMessage(request, response, "Message not exists", "Specified message already not exists", "return", Pages.boardView);
			}
			if (message.getRank() > user.getRank()) {
				this.responseMessage(request, response, "No permissions", "You don't have permissions to remove this message", "return", Pages.boardView);
			}
			if (!message.getAuthor().equals(user.getUsername())) {
				this.responseMessage(request, response, "No permissions", "You didn't wrote this message", "return", Pages.boardView);
			}
			MessageManager.deleteMessage(msgid);
			this.responseMessage(request, response, "Deleted successfully", "The message was deleted successfully", "return", Pages.boardView);
		} catch (Exception e) {
			this.internalError(request, response, e);
		}
	}   	  	    
}