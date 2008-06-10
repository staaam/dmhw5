<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
<c:choose>
<c:when test="${not empty username}">You're logged in as ${username}</c:when>
<c:otherwise>You're not logged in</c:otherwise>
</c:choose>
<br/>
<a href="newuser.jsp">new user</a><br/>
<a href="login.jsp">login</a><br/>
<a href="newmessage.jsp">new message</a><br/>
<a href="boardview">board view</a><br/>
<a href="boardview?viewtype=html">board view (html)</a><br/>
<a href="logout">logout</a><br/>
<br/>
<a href="localSearch.jsp">Local Search</a><br/>
<a href="sharedSearch.jsp">Shared Search</a><br/>
</body>
</html>