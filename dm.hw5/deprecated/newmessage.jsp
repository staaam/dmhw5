<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<form name="newmessage" action="newmessage" method="post">
<table>
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
<tr><td align="left">Body:</td><td><TEXTAREA NAME="body" COLS=40 ROWS=6></TEXTAREA></td></tr>
<tr><td colspan="2"><input name="submit" type="submit" value="Post"/></td></tr>
</table>
</form>
</body>
</html>