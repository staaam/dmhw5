package dmhw.servlet;


import java.io.IOException;

//import javax.jcr.RepositoryException;
//import javax.jcr.SimpleCredentials;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;


//import org.apache.jackrabbit.demo.blog.model.UserManager;


 /**
 * Servlet that handles user login 
 */
public class LoginControllerServlet extends ControllerServlet {

	
	 /**
	  * Serial version UID.
	  */
	 private static final long serialVersionUID = -6191646376561086611L;
	
	/**
	 * Handles the POST method requests
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Extract the username and the password from the request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
				
		// Check whether the authentication successful
		if(UserManager.login(username,password)) {
			//UserSession session = SessionManager.getSession(username);
			
			// set the two attributes auth and usernames in the session to keep the authentication state
			request.getSession().setAttribute("auth", new Boolean(true));
			request.getSession().setAttribute("username", username);
			
            // Forward the user to his blog
			RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher(Pages.index);
            requestDispatcher.forward(request, response);
		
        // if authentication fails
		} else {
			this.responseMessage(request, response, 
					"Authentication Failed",
					"Username or password is incorrect. Please recheck the password and try to log in again",
					"go back to login page",
					Pages.index);
		}
	}   	  	    
}