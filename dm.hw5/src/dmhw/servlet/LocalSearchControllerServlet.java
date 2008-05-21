package dmhw.servlet;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.Message;
import dmhw.model.MessageManager;
import dmhw.model.Pages;
import dmhw.model.User;
import dmhw.model.UserManager;
import dmhw.model.Utils;


/**
 * Controller class used to add users to the system
 *
 */
 public class LocalSearchControllerServlet extends ControllerServlet {
 	
	
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 5292911530212301687L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		String[] keywords = request.getParameter("keywords").split(" ");
		String type = request.getParameter("type");
		Integer rank = Utils.toInteger(request.getParameter("rank"));
		
		long time = -1;
		if (!Utils.isNullOrEmpty(request.getParameter("use_time"))) {
			Integer sd = Utils.toInteger(request.getParameter("sd"));
			Integer sm = Utils.toInteger(request.getParameter("sm"));
			Integer sy = Utils.toInteger(request.getParameter("sy"));
			Integer sH = Utils.toInteger(request.getParameter("sH"));
			Integer sM = Utils.toInteger(request.getParameter("sM"));
			time = new Date(sy-1900, sm, sd,sH, sM).getTime();
		}

		ArrayList<Message> messages = MessageManager.searchMessages(keywords, Math.min(user.getRank(), rank), time, type);
		BoardViewControllerServlet.printMessages(response, messages);
	}
 	    
}