package dmhw.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;

public class LogoutControllerServlet extends ControllerServlet {
	private static final long serialVersionUID = -6122606456722720455L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		this.responseMessage(request, response, "Logout", "You have been successfully log out from the system", "go back to login page", Pages.index);	
	}   	  	      	  	    
}