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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dmhw.Pages;
import dmhw.User;
import dmhw.UserManager;
import dmhw.Utils;


/**
 * Controller class used to add users to the system
 *
 */
 public class UserAddControllerServlet extends ControllerServlet {
 	
	
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 5292911530212301687L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Extract the details required to add a user from the request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		Integer rank = Utils.toInteger(request.getParameter("rank"));
		
		if (Utils.isNullOrEmpty(username)) {
			this.responseMessage(request, response,
					"Username is empty",
					"Username should be non empty string",
					"Try another username",
					Pages.newuser);
			return;
		}
		
		if (UserManager.exists(username)) {
			this.responseMessage(request, response,
					"Username Exists ",
					"Username '" + username + "' you have choosen is already registered",
					"Try another username",
					Pages.newuser);
			return;
		}
		
		if (Utils.isNullOrEmpty(password)) {
			this.responseMessage(request, response,
					"Password is empty",
					"Password should be non empty string",
					"Try another password",
					Pages.newuser);
			return;
		}
		
		if (Utils.isNullOrEmpty(type)) {
			this.responseMessage(request, response,
					"Type is empty",
					"Type should be non empty string",
					"Try another type",
					Pages.newuser);
			return;
		}
		
		if (rank == null || rank.intValue() < 0 || rank.intValue() > 10) {
			this.responseMessage(request, response,
					"Rank is incorrect",
					"Rank should be an integer between 1 and 10",
					"Try another rank",
					Pages.newuser);
			return;
		}		
		
		User user = new User(username, password, type, rank);
		
		UserManager.addUser(user);
		
		this.responseMessage(request, response,
			"User Registration Successful ",
			"You have successfully registered",
			"go back to login page",
			Pages.index);	
	}   	  	    
}