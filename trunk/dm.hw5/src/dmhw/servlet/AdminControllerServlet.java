package dmhw.servlet;

import java.io.IOException;
import java.net.Inet4Address;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;
import dmhw.registration.RegistrationService;
import dmhw.registration.RegistrationServiceService;
import dmhw.registration.RegistrationServiceServiceLocator;

public class AdminControllerServlet extends ControllerServlet {
	private static final long serialVersionUID = -6122606456722720455L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		String action = request.getParameter("action");
		try {
			if ("recreatedb".equals(action)) {
				DB.getInstance().recreate();
				this.simpleOkRespone(response);
				return;
			}
			String url = "http://"+Inet4Address.getLocalHost().getHostAddress()+":8080/dm.hw5/services/MBSearchImpl";
			if ("register".equals(action)) {
				RegistrationServiceService rss = new RegistrationServiceServiceLocator();
				RegistrationService rs = rss.getEndpointsRegistration();
				rs.addEndpoint(url); 
				this.simpleOkRespone(response);
				return;
			}
			if ("unregister".equals(action)) {
				RegistrationServiceService rss = new RegistrationServiceServiceLocator();
				RegistrationService rs = rss.getEndpointsRegistration();
				rs.deleteEndpoint(url); 
				this.simpleOkRespone(response);
				return;
			}
			this.simpleErrRespone(response, "Unknown command " + action);
		}
		catch (Exception e) {
			this.internalError(request, response, e);
		}
	}   	  	      	  	    
}