<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Local Search</title>
</head>
<body>
<form action="localsearch" method="POST">
<table>
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
<input type="checkbox" name="use_time" value="1" checked/>Use time limitation
</td>
</tr>
</table>
<input type="submit" value="Search"/>
</form>
</body>
</html>