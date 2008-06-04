package dmhw.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;

public class UserAddControllerServlet extends ControllerServlet {
	private static final long serialVersionUID = 5292911530212301687L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String type = request.getParameter("type");
			Integer rank = Utils.toInteger(request.getParameter("rank"));
			boolean guest = Utils.isNullOrEmpty(request.getParameter("full_access"));
	
			if (Utils.isNullOrEmpty(username)) {
				this.responseMessage(request, response, "Username is empty", "Username should be non empty string", "Try another username", Pages.newuser);
				return;
			}
			if (UserManager.exists(username)) {
				this.responseMessage(request, response, "Username Exists ", "Username '" + username + "' you have choosen is already registered", "Try another username", Pages.newuser);
				return;
			}
			if (Utils.isNullOrEmpty(password)) {
				this.responseMessage(request, response, "Password is empty", "Password should be non empty string", "Try another password", Pages.newuser);
				return;
			}
			if (Utils.isNullOrEmpty(type)) {
				this.responseMessage(request, response, "Type is empty", "Type should be non empty string", "Try another type", Pages.newuser);
				return;
			}
			if (type.length() > 15) {
				this.responseMessage(request, response, "Type is too long", "Type length should be less or equal that 15", "Try another type", Pages.newuser);
				return;
			}
			if (rank == null || rank.intValue() < 0 || rank.intValue() > 10) {
				this.responseMessage(request, response, "Rank is incorrect", "Rank should be an integer between 1 and 10", "Try another rank", Pages.newuser);
				return;
			}		
	
			User user = new User(username, password, type, rank, guest);
			UserManager.addUser(user);
			this.responseMessage(request, response, "User Registration Successful ", "You have successfully registered", "go back to login page", Pages.index);
		} catch (Exception e) {
			internalError(request, response, e);
		}
	}   	  	    
}