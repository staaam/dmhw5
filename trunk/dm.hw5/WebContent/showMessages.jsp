<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Messages</title>
</head>
<body>
<c:forEach var="message" items="${messages}">
	<table width="100%">
		<tr><td>title:</td><td>${message.title}</td></tr>
		<tr><td>author:</td><td>${message.author}</td></tr>
		<tr><td>type:</td><td>${message.type}</td></tr>
		<tr><td>rank:</td><td>${message.rank}</td></tr>
		<tr><td>startTime:</td><td>${message.startTime}</td></tr>
		<tr><td>endTime:</td><td>${message.endTime}</td></tr>
		<tr><td>body:</td><td>${message.body}</td></tr>
	</table>
</c:forEach>
</body>
</html>