package dmhw.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;

public class BoardViewControllerServlet extends ControllerServlet {
	private static final long serialVersionUID = 4885316149052515878L;
 	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = getUser(request);
			if (!Utils.isNullOrEmpty(request.getParameter("xsl"))) {
				String fn = getUserXSL(user, getServletContext());
		        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher(fn);
		        requestDispatcher.forward(request, response);
				return;
			}
			
			ArrayList<Message> messages = null;
			try {
				messages = MessageManager.getByUser(user);
			} catch (SQLException e) {
			}
			
			BoardViewControllerServlet.printMessages(response, messages, user, getServletContext());
		} catch (Exception e) {
			internalError(request, response, e);
		}		
	}

	public static void printMessages(HttpServletResponse response,
			ArrayList<Message> messages, User user, ServletContext servletContext) throws IOException {
		HashMap<String, LinkedList<Message>> map = new HashMap<String, LinkedList<Message>>();
		if (messages != null)
			for (Message m : messages) {
				String t = m.getType();
				if (!map.containsKey(t))
					map.put(t, new LinkedList<Message>());
				map.get(t).add(m);
			}
		
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/xml");
	    PrintWriter out = response.getWriter();
	    out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    out.println("<?xml-stylesheet type=\"text/xsl\" href=\""+getUserXSL(user, servletContext)+"\"?>");
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

	private static String getUserXSL(User user, ServletContext sc) {
		String fn = "/custom/"+user.getUsername()+".xsl";
		File f = new File(sc.getRealPath(fn));
		if (!f.exists())
			fn = "/messages.xsl";
		//fn = sc.getContextPath() + fn;
		return fn;
	}   	
}