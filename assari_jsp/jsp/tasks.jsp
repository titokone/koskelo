<%@ page import="fi.hy.eassari.taskdefinition.util.datastructures.*" %>
<%@ page import="fi.hy.eassari.taskdefinition.util.*" %>
<%@ page import="fi.hy.eassari.showtask.trainer.TaskBase" %>
<%@ page import="java.util.*" %>

<%
// resolve selected language
String lang = "EN"; // default language is english
TeacherSession settings = (TeacherSession)session.getAttribute("fi.hy.taskdefinition.util.datastructures.TeacherSession");
if (settings != null) 
	lang = settings.getSelectedLanguageId();

TaskBase cache = (TaskBase)request.getAttribute("fi.hy.eassari.showtask.trainer.TaskBase");
Collection tasks = (Collection)request.getSession(false).getAttribute("java.util.Collection"); 
%>

<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link href="http://www.cs.helsinki.fi/group/assari/css/assari.css" rel="stylesheet" type="text/css" />
</head>

<body class="body">

<p class="header"><%=cache.getAttribute("D", "teachertaskdef", "tasklistlabel", lang)%></p>
<table border="0" cellpadding="5" cellspacing="0" class="bordered">
  <tr class="tableHeader"> 
    <td colspan="7" align="left"><%=cache.getAttribute("D", "teachertaskdef", "createdtaskslabel", lang)%></td>
  </tr>
  <tr class="subHeader"> 
    <td align="left" valign="top" class="bordered"><strong><%=cache.getAttribute("D", "teachertaskdef", "tasknamelabel", lang)%></strong></td>
    <td align="left" valign="top" class="bordered"><strong><%=cache.getAttribute("D", "teachertaskdef", "tasktypelabel", lang)%></strong></td>
    <td align="left" valign="top" class="bordered"><strong><%=cache.getAttribute("D", "teachertaskdef", "languagelabel", lang)%></strong></td>
    <td class="bordered"><%=cache.getAttribute("D", "teachertaskdef", "editlabel", lang)%></td>
    <td class="bordered"><%=cache.getAttribute("D", "teachertaskdef", "editnewlabel", lang)%></td>
    <td class="bordered"><%=cache.getAttribute("D", "teachertaskdef", "editnewlanguagelabel", lang)%></td>
    <td class="bordered"><%=cache.getAttribute("D", "teachertaskdef", "deletelabel", lang)%></td>
  </tr>
<%

	if (tasks != null) {
		Iterator taskIter = tasks.iterator();
		while (taskIter.hasNext()) {
			TaskDTO taskAtIter = (TaskDTO)taskIter.next();

			Collection taskLanguages = LanguageHandler.getTaskLanguages(taskAtIter.getTaskId());
			Iterator langIter = taskLanguages.iterator();
	
			out.println("<tr>");
			out.println("  <form method='post' name='TASK"+taskAtIter.getTaskId()+"' action='"+request.getContextPath()+"/eAssari/taskDefinition/controller'>");
			out.println("  <input type='hidden' name='event' value='"+Events.EDIT_TASK+"'>");
			out.println("  <input type='hidden' name='taskid' value='"+taskAtIter.getTaskId()+"'>");
			out.println("  <td align='left' valign='top' class='bordered'>"+taskAtIter.getTaskName()+"</td>");
	    	out.println("  <td align='left' valign='top' class='bordered'>"+cache.getAttribute("D", "teachertaskdef", taskAtIter.getTaskType()+"typelabel", lang)+"</td>");
	    	out.println("  <td align='left' valign='top' class='bordered'>");
	    	out.println("		<select name='editlanguage'>");
			while (langIter.hasNext()) {
				LanguageDTO language = (LanguageDTO)langIter.next();
				out.println("        <option value='"+language.getLanguageId()+"'>"+language.getLanguageName()+"</option>");
			}	    	
			out.println("		</select></form></td>");	    	

	    	out.println("  <td class='bordered'>");
			out.println("  <div align='center'><strong>");
			out.println("  <a href='javascript: document.TASK"+taskAtIter.getTaskId()+".submit();'>");
			out.println(cache.getAttribute("D", "teachertaskdef", "editlink", lang)+"</a></strong></div></td>");
	
	    	out.println("  <td class='bordered'>");
	    	out.println("  <div align='center'><strong>");
			out.println("  <a href='javascript: document.TASK"+taskAtIter.getTaskId()+".event.value="+Events.EDIT_TASK_AS_NEW+", document.TASK"+taskAtIter.getTaskId()+".submit();' onClick='document.TASK"+taskAtIter.getTaskId()+".event.value=9;'>");
			out.println(cache.getAttribute("D", "teachertaskdef", "editnewlink", lang)+"</a></strong></div></td>");
	
	    	out.println("  <td class='bordered'>");
			out.println("  <div align='center'><strong>");
			out.println("  <a href='javascript: document.TASK"+taskAtIter.getTaskId()+".event.value="+Events.EDIT_TASK_AS_NEW_LANG+", document.TASK"+taskAtIter.getTaskId()+".submit();' onClick='document.TASK"+taskAtIter.getTaskId()+".event.value=9;'>");
			out.println(cache.getAttribute("D", "teachertaskdef", "editnewlanguagelink", lang)+"</a></strong></div></td>");		
	
	    	out.println("  <td class='bordered'>");
			out.println("  <div align='center'><strong>");
			out.println("  <a href='javascript: document.TASK"+taskAtIter.getTaskId()+".event.value="+Events.DELETE_TASK+", document.TASK"+taskAtIter.getTaskId()+".submit();' onClick='document.TASK"+taskAtIter.getTaskId()+".event.value=9;'>");
			out.println(cache.getAttribute("D", "teachertaskdef", "deletelink", lang)+"</a></strong></div></td>");		
	
	  		out.println("</tr>");
		}
	}
%>
  <tr> 
</table>
<p><a href="<%=request.getContextPath()+"/eAssari/taskDefinition/controller?event="+Events.NEW_TASK%>">
<strong><%=cache.getAttribute("D", "teachertaskdef", "createnewtasklink", lang)%></strong></a></p>
</body>
</html>
