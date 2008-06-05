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
				simpleErrRespone(response, "Username should not be empty");
				return;
			}
			if (UserManager.exists(username)) {
				simpleErrRespone(response, "Username already registered");
				return;
			}
			if (Utils.isNullOrEmpty(password)) {
				simpleErrRespone(response, "Password should not be empty");
				return;
			}
			if (Utils.isNullOrEmpty(type)) {
				simpleErrRespone(response, "Type should not be empty");
				return;
			}
			if (type.length() > 15) {
				simpleErrRespone(response, "Type too long. Should be no more than 15 characters");
				return;
			}
			if (rank == null || rank.intValue() < 0 || rank.intValue() > 10) {
				simpleErrRespone(response, "Rank should be a number between 1 and 10");
				return;
			}		
	
			User user = new User(username, password, type, rank, guest);
			UserManager.addUser(user);
			simpleOkRespone(response, "Registered successfully");
		} catch (Exception e) {
			internalError(request, response, e);
		}
	}   	  	    
}