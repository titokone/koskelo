<%@ page import="fi.hy.eassari.taskdefinition.util.datastructures.*" %>
<%@ page import="fi.hy.eassari.taskdefinition.util.*" %>
<%@ page import="fi.hy.eassari.showtask.trainer.TaskBase" %>
<%
// resolve selected language
String lang = "EN"; // default language is english, should check selected language
TeacherSession settings = (TeacherSession)session.getAttribute("fi.hy.taskdefinition.util.datastructures.TeacherSession");
if (settings != null) 
	lang = settings.getSelectedLanguageId();

int event = -1;
TaskBase cache = (TaskBase)request.getAttribute("fi.hy.eassari.showtask.trainer.TaskBase");
boolean editTask = false;
TaskDTO task = null;

try {
	event = ((Integer)request.getAttribute("event")).intValue();
} catch(Exception e) { System.out.println("Error while retrieving event-id: "+e); }

if (event == Events.BLANKFILL_TEXT_EDIT) 
	editTask = true;
task = (TaskDTO)request.getSession(false).getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO"); 
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

<form action="<%=request.getContextPath()%>/eAssari/taskDefinition/controller" method="post" name="blankfill1" id="blankfill1">

    <input name="event" type="hidden" id="event" value="<%=Events.BLANKFILL_TEXT_SUBMIT%>" />
    <input name="taskid" type="hidden" id="taskid" value="<%=(task == null ? "" : ""+task.getTaskId())%>" />
    <input name="taskname" type="hidden" id="taskname" value="<%=(task == null ? "" : ""+task.getTaskName())%>" />

  <p class="header"><%=cache.getAttribute("D", "blankfilltaskdef", "headline", lang)%>
  </p>
  <table border="0" cellpadding="5" cellspacing="0" class="helpnote">
    <tr>
      <td><p><%=cache.getAttribute("D", "blankfilltaskdef", "infobox", lang)%></p>
        </td>
    </tr>
  </table>
  <p> 
    <textarea name="text" cols="100" rows="25" class="input" id="text"><%=(task.get("text") == null ? "" : task.get("text"))%></textarea>
  </p>
  <p align="left"><br />
    <input name="Submit" type="submit" class="button" value="<%=cache.getAttribute("D", "blankfilltaskdef", "submitbutton", lang)%>" />
  </p>
  </form>
</body>
</html>
