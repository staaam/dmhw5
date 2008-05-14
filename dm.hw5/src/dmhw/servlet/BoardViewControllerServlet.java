package dmhw.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
				"/jackrabbit-jcr-demo/blog/index.jsp");	
            return;
		}
		
		
//		// Get a ArrayList of blog entries of user
//		ArrayList<BlogEntry> blogList = BlogManager.getByUsername(username,session);
//		
//		String uuid = UserManager.getUUID(username, session);
//		
//		// Set the blogList as a request attribute 
//		request.setAttribute("blogList",blogList);
//		request.setAttribute("ownBlog",true);
//		request.setAttribute("userUUID", uuid);
//		
//		// Forward the request to blog entries page
//        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/listBlogEntries.jsp");
//        requestDispatcher.forward(request, response);
	}   	  	    
}