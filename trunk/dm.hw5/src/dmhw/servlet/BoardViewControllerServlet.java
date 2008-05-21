package dmhw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;
import dmhw.search.MBSearch;
import dmhw.search.MBSearchService;
import dmhw.search.MBSearchServiceLocator;



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
		
		ArrayList<Message> messages = MessageManager.getByUser(user);
		
		String viewtype = (String)request.getParameter("viewtype");
		if ("html".equals(viewtype)) {
			request.setAttribute("messages", messages);
	        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher(Pages.showMessages);
	        requestDispatcher.forward(request, response);
			return;
		}
		
		BoardViewControllerServlet.printMessages(response, messages);
	}
	
	public static void printMessages(HttpServletResponse response,
			ArrayList<Message> messages) throws IOException {
		HashMap<String, LinkedList<Message>> map = new HashMap<String, LinkedList<Message>>();
		for (Message m : messages) {
			String t = m.getType();
			if (!map.containsKey(t))
				map.put(t, new LinkedList<Message>());
			map.get(t).add(m);
		}
		
		response.setContentType("text/xml");
	    PrintWriter out = response.getWriter();
	    out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    out.println("<?xml-stylesheet type=\"text/xsl\" href=\"messages.xsl\"?>");
	    out.println("<messages>");
	    for (String t : map.keySet()) {
			out.println("<message_list>");
			out.println("<type>"+t+"</type>");
			for (Message m : map.get(t)) {
				out.println(m.toXML());
			}
			out.println("</message_list>");
	    }
	    out.println("</messages>");
	}   	
}