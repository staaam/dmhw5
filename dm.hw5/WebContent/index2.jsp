<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import='dmhw.registration.RegistrationService' %>
<%@ page import='dmhw.registration.RegistrationServiceService' %>
<%@ page import='dmhw.registration.RegistrationServiceServiceLocator' %>
<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
	dir="ltr">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Message Board</title>
  	<link href="messages.css" rel="stylesheet" type="text/css" />
	<script src="js/ajaxslt/util.js" type="text/javascript"></script>
	<script src="js/ajaxslt/xmltoken.js" type="text/javascript"></script>
	<script src="js/ajaxslt/dom.js" type="text/javascript"></script>
	<script src="js/ajaxslt/xpath.js" type="text/javascript"></script>
	<script src="js/ajaxslt/xslt.js" type="text/javascript"></script>
	<script src="js/aim.js" type="text/javascript"></script>
	<script language="JavaScript" src="js/mb.js" type="text/javascript"></script>
	<script language="JavaScript">
		var username = "${username}";
	</script>
</head>
<body onload="init()">
<div id="menu">
	<a href="#" class="menu_item" onclick="newMessage()">new message</a>
	<a href="#" class="menu_item" onclick="boardView()">board view</a>
	<a href="#" class="menu_item" onclick="localSearch()">Local Search</a>
	<a href="#" class="menu_item" onclick="sharedSearch()">Shared Search</a>
	<a href="#" class="menu_item" onclick="showPrefs()">Preferences</a>
</div>
<div id="login_status"></div>
<div id="main_top" class="top_d">
	<div id="main_label" class="top_lbl" onclick="toggle('main');">Action</div>
	<div id="main"></div>
</div>
<div id="board_top" class="top_d">
	<div id="board_label" class="top_lbl" onclick="toggle('board');">Board View</div>
	<div id="board"></div>
</div>
<div id="search_top" class="top_d">
	<div id="search_label" class="top_lbl" onclick="toggle('search');">Search Results</div>
	<div id="search"></div>
</div>

<div id="all_divs" style="visibility:hidden">
<div id="prefs">
	<form enctype='multipart/form-data' action='setprefs' method='post'>
	<table>
	<tr><td colspan="2"><div id="uploadStatus"></div></td></tr>
	<tr>
	<td>Custom CSS</td><td><input name='css' type='file' /></td>
	<td>Custom XSL</td><td><input name='xsl' type='file' /></td>
	<td colspan="2"><input type='submit' value='Upload'/></td>
	<!--  onclick="setPrefs(this); return false" -->
	</tr>
	</table>
	</form>
</div>
<div id="login">
	<form>
	<table>
		<tr>
			<td colspan="2"><span id='loginError' class="error"></span></td>
		</tr>
		<tr>
			<td align="left">Login:</td>
			<td><input name="username" type="text" value="" /></td>
		</tr>
		<tr>
			<td align="left">Password:</td>
			<td><input name="password" type="password" value="" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input name="submit" type="submit"
				value="Login" onclick="doLogin(this.form); return false" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><a href="#" onclick="register()">Register now</a></td>
		</tr>
	</table>
	</form>
</div>
<div id="loggedin">
	You're logged in as <span id="user">&nbsp;</span>.
	<a href="#" onclick="doLogout();">Logout</a>
</div>
<div id="newuser">
<form>
<table>
	<tr>
		<td colspan="2"><span id='newuserError' class="error"></span></td>
	</tr>
	<tr>
		<td align="left">Login</td>
		<td><input name="username" type="text" /></td>
	</tr>
	<tr>
		<td align="left">Password</td>
		<td><input name="password" type="password" /></td>
	</tr>
	<tr>
		<td align="left">Confirm</td>
		<td><input name="confirm" type="password" /></td>
	</tr>
	<tr>
		<td align="left">Type</td>
		<td><input name="type" type="text" /></td>
	</tr>
	<tr>
		<td align="left">Rank</td>
		<td><select name="rank">
			<c:forEach begin="1" end="10" var="d">
				<option value="${d}">${d}</option>
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td align="left">Full access</td>
		<td><input type="checkbox" name="fullAccess" value="1"
			checked="checked" /></td>
	</tr>
	<tr>
		<td colspan="2"><input name="submit" type="submit"
			value="Register" onclick="doRegister(this.form); return false" /></td>
	</tr>
</table>
</form>
</div>
<div id="newMessage">
<form>
<table>
	<tr>
		<td colspan="2"><span id='newMessageError' class="error"></span></td>
	</tr>
<tr><td align="left">Title:</td><td><input name="title" type="text"/></td></tr>
<tr><td align="left">Type:</td><td><input name="type" type="text"/></td></tr>
<tr><td align="left">Rank:</td><td><select name="rank"><c:forEach begin="1" end="10" var="d"><option value="${d}">${d}</option></c:forEach></select></td></tr>
<tr><td align="left">StartTime:</td><td>
<select name="sd"><c:forEach begin="1" end="31" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sm"><c:forEach begin="1" end="12" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sy"><c:forEach begin="1970" end="2054" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sH"><c:forEach begin="0" end="23" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sM"><c:forEach begin="0" end="59" var="d"><option value="${d}">${d}</option></c:forEach></select>
</td></tr>
<tr><td align="left">EndTime:</td><td>
<select name="ed"><c:forEach begin="1" end="31" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="em"><c:forEach begin="1" end="12" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="ey"><c:forEach begin="1970" end="2054" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="eH"><c:forEach begin="0" end="23" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="eM"><c:forEach begin="0" end="59" var="d"><option value="${d}">${d}</option></c:forEach></select>
</td></tr>
<tr><td align="left">Body:</td><td><textarea name="body" cols="40" rows="6"></textarea></td></tr>
<tr><td colspan="2"><input name="submit" type="submit" value="Post" onclick="postMessage(this.form); return false"/></td></tr>
</table>
</form>
</div>
<div id="localSearch">
<form>
<table>
	<tr>
		<td colspan="2"><span id='localSearchError' class="error"></span></td>
	</tr>
<tr>
<td>Keywords</td><td><input type="text" name="keywords"/></td>
</tr>
<tr>
<td>Type (can be empty)</td><td><input type="text" name="type"/></td>
</tr>
<tr>
<td>Rank (no more than yours)</td><td><select name="rank"><c:forEach begin="1" end="10" var="d"><option value="${d}">${d}</option></c:forEach></select></td>
</tr>
<tr>
<td>Time</td><td>
<select name="sd"><c:forEach begin="1" end="31" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sm"><c:forEach begin="1" end="12" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sy"><c:forEach begin="1970" end="2054" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sH"><c:forEach begin="0" end="23" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sM"><c:forEach begin="0" end="59" var="d"><option value="${d}">${d}</option></c:forEach></select>
<input type="checkbox" name="use_time" value="1" checked="checked"/>Use time limitation
</td>
</tr>
</table>
<input type="submit" value="Search" onclick="doLocalSearch(this.form); return false"/>
</form>
</div>
<div id="sharedSearch">
<% RegistrationServiceService rss = new RegistrationServiceServiceLocator(); %>
<% RegistrationService rs = rss.getEndpointsRegistration(); %>
<% request.setAttribute("endpoints", rs.getRegisteredEndpoints()); %>
<form>
<c:forEach items="${endpoints}" var="ep">
	<input type="checkbox" name="endpoints" value="${ep}" checked="checked"/>${ep}<br/>
</c:forEach>
<table>
	<tr>
		<td colspan="2"><span id='sharedSearchError' class="error"></span></td>
	</tr>
<tr>
<td>Keywords</td><td><input type="text" name="keywords"/></td>
</tr>
<tr>
<td>Rank</td><td><select name="rank"><c:forEach begin="1" end="10" var="d"><option value="${d}">${d}</option></c:forEach></select></td>
</tr>
<tr>
<td>Time</td><td>
<select name="sd"><c:forEach begin="1" end="31" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sm"><c:forEach begin="1" end="12" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sy"><c:forEach begin="1970" end="2054" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sH"><c:forEach begin="0" end="23" var="d"><option value="${d}">${d}</option></c:forEach></select>
<select name="sM"><c:forEach begin="0" end="59" var="d"><option value="${d}">${d}</option></c:forEach></select>
</td>
</tr>
</table>
<input type="submit" value="Search" onclick="doSharedSearch(this.form); return false"/>
</form>
</div>
<div id="post_ok">
	Your message has been posted successfully
</div>
<div id="reg_ok">
	Registration successfull. You can login now
</div>
<div id="boardView">
</div>
</div>
</body>
</html>