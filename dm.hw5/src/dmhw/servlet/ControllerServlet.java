package dmhw.servlet;


import java.io.IOException;

//import javax.jcr.Repository;
//import javax.jcr.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;

//import org.apache.jackrabbit.servlet.ServletRepository;


/**
 * Controller class which is used as the base class for all controller servlets
 */
abstract public class ControllerServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{
	
	@Override
	public void init() throws ServletException {
		super.init();
		DB.init(this.getServletContext());
	}

	/** 
	 * Method which handles the GET method requests
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Delegate the work to doPost(HttpServletRequest request, HttpServletResponse response) method
		doPost(request,response);
	} 
	
	protected void responseMessage(HttpServletRequest request,HttpServletResponse response, String title, String message, String URLText, String URL) throws ServletException, IOException{
		
		//set the attributes which are required by user messae page
		request.setAttribute("msgTitle", title);
		request.setAttribute("msgBody", message);
		request.setAttribute("urlText", "go back to "+ URLText);
		request.setAttribute("url","." + URL);	
		
		//forward the request to user massage page
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher(Pages.message);
        requestDispatcher.forward(request, response);
		
	}

}
