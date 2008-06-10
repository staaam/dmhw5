package dmhw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

//import javax.jcr.Repository;
//import javax.jcr.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;

abstract public class ControllerServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{
	
	@Override
	public void init() throws ServletException {
		super.init();
		DB.init(this.getServletContext());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Delegate the work to doPost(HttpServletRequest request, HttpServletResponse response) method
		doPost(request,response);
	} 
	
	protected User getUser(HttpServletRequest request) {
		String username = (String)request.getSession().getAttribute("username");
		try {
			return Utils.isNullOrEmpty(username) ? User.GUEST : UserManager.getUser(username);
		} catch (SQLException e) {
			return User.GUEST;
		}
	}

	protected void frwd(HttpServletRequest request, HttpServletResponse response,
			String fn) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher(fn);
        requestDispatcher.forward(request, response);
	}

	protected void internalError(HttpServletRequest request,HttpServletResponse response, Exception e) {
		e.printStackTrace();
		simpleErrRespone(response, "Error occured while processing request ("+e.getMessage()+")");
	}
	
	protected void simpleErrRespone(HttpServletResponse response, String msg) {
		simpleRespone(response, false, "<msg>" + msg + "</msg>");
	}

	protected void simpleOkRespone(HttpServletResponse response, String msg) {
		simpleRespone(response, true, "<msg>" + msg + "</msg>");
	}

	protected void simpleOkRespone(HttpServletResponse response) {
		simpleRespone(response, true, null);
	}

	protected void simpleRespone(HttpServletResponse response, boolean success, String more) {
		try {
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<response>");
			out.println("<result>" + success + "</result>");
			if (more != null)
				out.println(more);
			out.println("</response>");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	} 
 	  	    

}
