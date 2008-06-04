package dmhw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;
import dmhw.search.MBSearch;
import dmhw.search.MBSearchService;
import dmhw.search.MBSearchServiceLocator;

public class SharedSearchControllerServlet extends ControllerServlet {
	private static final long serialVersionUID = 5292911530212301687L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] keywords = request.getParameter("keywords").split(" ");
		Integer rank = Utils.toInteger(request.getParameter("rank"));

		long time = Utils.getDate(request, "s").getTime();

		String[] endpoints = request.getParameterValues("endpoints");

		response.setContentType("text/xml");
	    PrintWriter out = response.getWriter();
	    out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	    out.println("<?xml-stylesheet type=\"text/xsl\" href=\"messages.xsl\"?>");
	    out.println("<messages>");
	    int i=1;
		for (String endpoint : endpoints) {
			MBSearchService sss = new MBSearchServiceLocator();
			out.println("<message_list>");
			out.println("<type>"+"endpoint_"+i+"</type>");
			i++;
			try {
				MBSearch ss = sss.getMyService(new URL(endpoint));
				String[] res = ss.search(keywords, rank, time);
				for (String r : res) {
					out.println(r);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.println("</message_list>");
		}
	    out.println("</messages>");
	}   	  	    
}