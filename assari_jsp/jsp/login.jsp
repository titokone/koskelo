<%@ page import="fi.hy.eassari.taskdefinition.util.*" %>
<%@ page import="fi.hy.eassari.taskdefinition.util.datastructures.*" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>

<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>eAssari login</title>
<link href="styles/editor1.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript">

function setFocus() {
  this.document.login.userid.focus();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link href="http://www.cs.helsinki.fi/group/assari/css/assari.css" rel="stylesheet" type="text/css" />
</head>

<body leftmargin="200" topmargin="50" onload="setFocus()">
<form action="<%=request.getContextPath()%>/eAssari/taskDefinition/controller?event=<%=Events.LOGIN%>" method="post" name="login" class="body" id="login">

  <p class="header">Login</p>
  <table width="400" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td>Username</td>
      <td><input name="userid" type="text" size="25" maxlength="25" /></td>
    </tr>
    <tr>
      <td>Password</td>
      <td><input name="password" type="password" size="25" maxlength="25" /></td>
    </tr>
    <tr>
      <td>Language</td>
      <td><select name='language' id='language'>
<% 	Collection allLanguages = LanguageHandler.getSystemLanguages();
	Iterator langIter = allLanguages.iterator();
	while (langIter.hasNext()) {
		LanguageDTO language = (LanguageDTO)langIter.next();
		out.println("<option value='"+language.getLanguageId()+"'>"+language.getLanguageName()+"</option>");
	}
%>    
	  </select></td>
    </tr>
  </table>
  <p class="header"> 
    <input type="submit" name="Submit" value="Submit" class="button" />
  </p>
  </form>
</body>
</html>
