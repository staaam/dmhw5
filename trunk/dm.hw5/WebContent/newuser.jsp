<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New User</title>
</head>
<body>
<form name="newuser" action="newuser" method="post">
<table>
<tr><td align="left">Login</td><td><input name="username" type="text"/></td></tr>
<tr><td align="left">Password</td><td><input name="password" type="password"></td></tr>
<tr><td align="left">Confirm</td><td><input name="confirm" type="password"></td></tr>
<tr><td align="left">Type</td><td><input name="type" type="text"></td></tr>
<tr><td align="left">Rank</td><td><select name="rank"><c:forEach begin="1" end="10" var="d"><option value="${d}">${d}</option></c:forEach></select></td></tr>
<tr><td colspan="2"><input name="submit" type="submit" value="newuser"/></td></tr>
</table>
</form>
</body>
</html>