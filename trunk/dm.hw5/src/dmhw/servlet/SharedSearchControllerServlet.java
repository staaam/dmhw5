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
import java.net.URL;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import dmhw.model.*;
import dmhw.search.MBSearch;
import dmhw.search.MBSearchService;
import dmhw.search.MBSearchServiceLocator;


/**
 * Controller class used to add users to the system
 *
 */
 public class SharedSearchControllerServlet extends ControllerServlet {
 	
	
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 5292911530212301687L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] keywords = request.getParameter("keywords").split(" ");
		Integer rank = Utils.toInteger(request.getParameter("rank"));

		Integer sd = Utils.toInteger(request.getParameter("sd"));
		Integer sm = Utils.toInteger(request.getParameter("sm"));
		Integer sy = Utils.toInteger(request.getParameter("sy"));
		Integer sH = Utils.toInteger(request.getParameter("sH"));
		Integer sM = Utils.toInteger(request.getParameter("sM"));
		long time = new Date(sy-1900, sm, sd,sH, sM).getTime();

		String[] endpoints = request.getParameterValues("endpoints");

		response.setContentType("text/xml");
	    PrintWriter out = response.getWriter();
	    out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
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