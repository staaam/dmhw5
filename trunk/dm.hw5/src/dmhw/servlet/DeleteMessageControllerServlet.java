package dmhw.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;



/**
 * Controller class which handles the viewing of his/her own blog by user
 */
 public class DeleteMessageControllerServlet extends ControllerServlet {
  	
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 4885316149052515878L;
 	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the username of the current session. "username" attribute is set in LoginController when the user log in to the system.
		String username = (String)request.getSession().getAttribute("username");
		
		if (username == null || username.equals("guest")) {
			//set the attributes which are required by user messae page
			this.responseMessage(request, response, 
				"Authentication Required",
				"You have to log in to see your blog page",
				"go back to login page",
				Pages.login);	
            return;
		}
		
		User user = UserManager.getUser(username);
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
	}   	  	    
}