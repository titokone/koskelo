<%@ page import="fi.hy.eassari.taskdefinition.util.datastructures.*" %>
<%@ page import="fi.hy.eassari.taskdefinition.util.*" %>
<%@ page import="fi.hy.eassari.showtask.trainer.TaskBase" %>
<%@ page import="java.util.*" %>

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

if (event == Events.ORDERING_OPTIONS_EDIT || event == Events.ORDERING_OPTIONS_COMPOSE_ADD_OPTIONS) 
	editTask = true;
task = (TaskDTO)request.getSession(false).getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO"); 

int numberOfObjects = 0;
int emptyRows = 10;

if (task != null) {
	String value = null;
	do {
		value = (String)task.get("object"+(numberOfObjects+1));
		if (value != null) 
			++numberOfObjects;
	} while (value != null);
}
%>
<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link href="http://www.cs.helsinki.fi/group/assari/css/assari.css" rel="stylesheet" type="text/css" />
<script language="JavaScript">
function addObjects() {
	document.ordering1.event.value = <%=Events.ORDERING_OPTIONS_COMPOSE_ADD_OPTIONS%>
	document.ordering1.submit()
}
</script>
</head>

<body class="body">

<form action="<%=request.getContextPath()%>/eAssari/taskDefinition/controller" method="post" name="ordering1" id="ordering1">

    <input name="event" type="hidden" id="event" value="<%=Events.ORDERING_OPTIONS_SUBMIT%>" />
    <input name="taskid" type="hidden" id="taskid" value="<%=(task == null ? "" : ""+task.getTaskId())%>" />
    <input name="taskname" type="hidden" id="taskname" value="<%=(task == null ? "" : ""+task.getTaskName())%>" />
    <input name="numberofoptions" type="hidden" id="numberofoptions" value="<%=numberOfObjects+emptyRows%>" />

  <p class="header"><%=cache.getAttribute("D", "orderingtaskdef", "headline", lang)%> 
  </p>
  <table border="0" cellpadding="5" cellspacing="0" class="helpnote">
    <tr> 
      <td><p><%=cache.getAttribute("D", "orderingtaskdef", "infobox", lang)%></p>
        </td>
    </tr>
  </table>
  <br />
  <strong><%=cache.getAttribute("D", "generaltaskdef", "assignmentlabel", lang)+": "%></strong>
  <%=(task==null ? "" : task.get("task"))%>
  <br /><br />  
  <table width="100%" border="0" cellspacing="0" cellpadding="3">
    <tr> 
      <td align="center" class="tableHeader">&nbsp;</td>
      <td class="tableHeader"><%=cache.getAttribute("D", "orderingtaskdef", "objectorderlabel", lang)%></td>
      <td colspan="3" class="tableHeader"> <%=cache.getAttribute("D", "orderingtaskdef", "feedbacklabel", lang)%></td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td align="center" class="highlight1"><%=cache.getAttribute("D", "orderingtaskdef", "correctlabel", lang)%></td>
      <td class="center"><%=cache.getAttribute("D", "orderingtaskdef", "earlylabel", lang)%></td>
      <td align="center" class="highlight1"><%=cache.getAttribute("D", "orderingtaskdef", "latelabel", lang)%></td>
    </tr>
<%    
  	for (int i=1; i<=numberOfObjects+emptyRows; ++i) { 
		String object = "";
		String correctFeedback = "";
		String tooEarlyFeedback = "";
		String tooLateFeedback = "";		

		if (task!=null && i<=numberOfObjects) { // get values from data structure
			object = (String)task.get("object"+i);
			correctFeedback = (String)task.get("correctfeedback"+i);
			tooEarlyFeedback = (String)task.get("tooearlyfeedback"+i);
			tooLateFeedback = (String)task.get("toolatefeedback"+i);
		}

		// print the contents of current option

		out.println("");
	    out.println("<tr>");
		out.println("	<td><strong>"+i+".</strong></td>");		
		out.println("	<td><input name='object"+i+"' type='text' class='input' id='object"+i+"' value='"+object+"' size='30' maxlength='2000' /></td>");		
		out.println("	<td align='center' class='highlight1'><input name='correctfeedback"+i+"' type='text' value='"+correctFeedback+"' class='input' id='correctfeedback"+i+"' size='30' maxlength='2000' /></td>");
		out.println("	<td align='center'><input name='tooearlyfeedback"+i+"' type='text' value='"+tooEarlyFeedback+"' class='input' id='tooearlyfeedback"+i+"' size='40' maxlength='2000' /></td>");
		out.println("	<td align='center' class='highlight1'><input name='toolatefeedback"+i+"' type='text' value='"+tooLateFeedback+"' class='input' id='toolatefeedback"+i+"' size='30' maxlength='2000' /></td>");
		out.println("</tr>");		
	}	
%>		    

    <tr> 
      <td>&nbsp;</td>
      <td>
    	<a href="javascript: addObjects();">
	      <strong><%=cache.getAttribute("D", "orderingtaskdef", "addobjectslink", lang)%></strong>
	    </a>
	  </td>
      <td align="center" class="highlight1">&nbsp;</td>
      <td class="center">&nbsp;</td>
      <td align="center" class="highlight1">&nbsp;</td>
    </tr>
  </table>
  <p>&nbsp;</p>
  <p> 
    <input name="Submit" type="submit" class="button" value="<%=cache.getAttribute("D", "orderingtaskdef", "submitbutton", lang)%>" />
  </p>
  </form>
<p>&nbsp; </p>
</body>
</html>
