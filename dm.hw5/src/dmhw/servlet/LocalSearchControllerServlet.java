package dmhw.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.Message;
import dmhw.model.MessageManager;
import dmhw.model.User;
import dmhw.model.Utils;

public class LocalSearchControllerServlet extends ControllerServlet {
	private static final long serialVersionUID = 5292911530212301687L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
		try {
			User user = getUser(request);
			
			String[] keywords = request.getParameter("keywords").split(" ");
			String type = request.getParameter("type");
			Integer rank = Utils.toInteger(request.getParameter("rank"));
			
			long time = -1;
			if (!Utils.isNullOrEmpty(request.getParameter("use_time"))) {
				time = Utils.getDate(request, "s").getTime();
			}
	
			ArrayList<Message> messages = MessageManager.searchMessages(keywords, Math.min(user.getRank(), rank), time, type);
			BoardViewControllerServlet.printMessages(response, messages, user, getServletContext());
		} catch (Exception e) {
			internalError(request, response, e);
		}
	}
 	    
}