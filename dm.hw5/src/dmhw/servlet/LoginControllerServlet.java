package dmhw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

//import javax.jcr.RepositoryException;
//import javax.jcr.SimpleCredentials;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;

public class LoginControllerServlet extends ControllerServlet {
	 private static final long serialVersionUID = -6191646376561086611L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
				
			if(UserManager.login(username,password)) {
				request.getSession().setAttribute("auth", new Boolean(true));
				request.getSession().setAttribute("username", username);
				
//				RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher(Pages.index);
//			    requestDispatcher.forward(request, response);
				simpleOkRespone(response);
			} else {
				simpleErrRespone(response, "Login failed");
//				this.responseMessage(request, response, "Authentication Failed", "Username or password is incorrect. Please recheck the password and try to log in again", "go back to login page", Pages.index);
			}

		} catch (Exception e) {
			internalError(request, response, e);
		}
	}

}