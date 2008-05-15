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
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.model.*;



/**
 * Controller class used to add users to the system
 *
 */
 public class MessageAddControllerServlet extends ControllerServlet {
 	
	
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
		
		
		String title = request.getParameter("title");
		String type = request.getParameter("type");
		
		if (Utils.isNullOrEmpty(type)) {
			this.responseMessage(request, response,
					"Type is empty",
					"Type should be non empty string",
					"Try another type",
					Pages.newmessage);
			return;
		}
		if (type.length() > 15) {
			this.responseMessage(request, response,
					"Type is too long",
					"Type length should be less or equal that 15",
					"Try another type",
					Pages.newmessage);
			return;
		}

		Integer rank = Utils.toInteger(request.getParameter("rank"));

		Integer sd = Utils.toInteger(request.getParameter("sd"));
		Integer sm = Utils.toInteger(request.getParameter("sm"));
		Integer sy = Utils.toInteger(request.getParameter("sy"));
		Integer sH = Utils.toInteger(request.getParameter("sH"));
		Integer sM = Utils.toInteger(request.getParameter("sM"));

		Integer ed = Utils.toInteger(request.getParameter("ed"));
		Integer em = Utils.toInteger(request.getParameter("em"));
		Integer ey = Utils.toInteger(request.getParameter("ey"));
		Integer eH = Utils.toInteger(request.getParameter("eH"));
		Integer eM = Utils.toInteger(request.getParameter("eM"));
		
		String body = request.getParameter("body");
		
		// TODO: add checks for correctness!!
		
		Message msg = new Message();
		msg.setTitle(title);
		msg.setType(type);
		msg.setRank(rank);
		msg.setStartTime(new Date(sy-1900, sm, sd,sH, sM));
		msg.setEndTime(new Date(ey-1900, em, ed, eH, eM));
		msg.setAuthor(username);
		msg.setBody(body);
		
		MessageManager.addMessage(msg);
		
		this.responseMessage(request, response,
			"Message Added Successful ",
			"Your have been posted successfully",
			"go back to login page",
			Pages.index);	
	}   	  	    
}