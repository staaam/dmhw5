package dmhw.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;

public class AdminControllerServlet extends ControllerServlet {
	private static final long serialVersionUID = -6122606456722720455L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action.equals("recreatedb")) {
			DB.getInstance().recreate();
			return;
		}
	}   	  	      	  	    
}