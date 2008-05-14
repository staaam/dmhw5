package dmhw.servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.Pages;
import dmhw.User;
import dmhw.management.UserManager;


/**
 * Controller class which handles the viewing of his/her own blog by user
 */
 public class BoardViewControllerServlet extends ControllerServlet {
  	
	
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
		
		LinkedList<Message> messages = MessageManager.getByUser(user);
		
		request.setAttribute("messages",messages);
		
		// Forward the request to blog entries page
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher(Pages.showMessages);
        requestDispatcher.forward(request, response);
	}   	  	    
}