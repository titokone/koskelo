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

if (event == Events.OPTION_TASK_EDIT || event == Events.OPTION_TASK_COMPOSE_ADD_OPTIONS) 
	editTask = true;
task = (TaskDTO)request.getSession(false).getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO"); 
	
int numberOfOptions = 0;
int emptyRows = 10;

if (task != null) {
	String value = null;
	do {
		value = (String)task.get("option"+(numberOfOptions+1));
		if (value != null) 
			++numberOfOptions;
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
function addOptions() {
	document.option.event.value = <%=Events.OPTION_TASK_COMPOSE_ADD_OPTIONS%>
	document.option.submit()
}
</script>
</head>

<body class="body">

<form action="<%=request.getContextPath()%>/eAssari/taskDefinition/controller" method="post" name="option" id="option">

    <input name="event" type="hidden" id="event" value="<%=Events.OPTION_TASK_SUBMIT%>" />
    <input name="taskid" type="hidden" id="taskid" value="<%=(task == null ? "" : ""+task.getTaskId())%>" />
    <input name="taskname" type="hidden" id="taskname" value="<%=(task == null ? "" : ""+task.getTaskName())%>" />
    <input name="numberofoptions" type="hidden" id="numberofoptions" value="<%=numberOfOptions+emptyRows%>" />

  <p class="header"><%=cache.getAttribute("D", "optiontaskdef", "headline", lang)%>
  </p>
  <table border="0" cellpadding="5" cellspacing="0" class="helpnote">
    <tr> 
      <td><p><%=cache.getAttribute("D", "optiontaskdef", "infobox", lang)%></p></td>
    </tr>
  </table><br />
  <strong><%=cache.getAttribute("D", "generaltaskdef", "assignmentlabel", lang)+": "%></strong>
  <%=(task==null ? "" : task.get("task"))%>
  <br /><br />
  <table width="100%" border="0" cellspacing="0" cellpadding="3">
    <tr align="center"> 
      <td class="tableHeader">&nbsp;</td>
      <td class="tableHeader"><%=cache.getAttribute("D", "optiontaskdef", "optionlabel", lang)%></td>
      <td colspan="2" class="tableHeader"><%=cache.getAttribute("D", "optiontaskdef", "correctanswerlabel", lang)%></td>
      <td colspan="2" class="tableHeader"><%=cache.getAttribute("D", "optiontaskdef", "feedbacklabel", lang)%></td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td align="center" class="highlight1"><%=cache.getAttribute("D", "optiontaskdef", "selectedradiobutton", lang)%></td>
      <td class="center"><%=cache.getAttribute("D", "optiontaskdef", "notselectedradiobutton", lang)%></td>
      <td align="center" class="highlight1"><%=cache.getAttribute("D", "optiontaskdef", "correctlabel", lang)%></td>
      <td class="center"><%=cache.getAttribute("D", "optiontaskdef", "falselabel", lang)%></td>
    </tr>
<%    
  	for (int i=1; i<=numberOfOptions+emptyRows; ++i) { 
		String option = "";
		boolean selected = false;
		String positiveFeedback = "";
		String negativeFeedback = "";

		if (task != null && i<=numberOfOptions) { // get values from data structure
			option = (String)task.get("option"+i);

			String tmpSelected = (String)task.get("isselected"+i);
			if (tmpSelected.equals("Y")) 
				selected = true;

			positiveFeedback = (String)task.get("positivefeedback"+i);
			negativeFeedback = (String)task.get("negativefeedback"+i);
		}

		// print the contents of current option
		out.println("");
	    out.println("<tr>");
	    out.println("  <td><strong>"+i+"</strong></td>");
		out.println("  <td> <input name='option"+i+"' type='text' class='input' id='option"+i+"' value='"+option+"' size='30' maxlength='2000' /></td>");
	    out.println("  <td align='center' class='highlight1'> <input type='radio' name='isselected"+i+"' value='Y' "+(selected ? "checked='checked'" : "")+" /></td>");
		out.println("  <td class='center'> <input name='isselected"+i+"' type='radio' value='N' "+(selected ? "" : "checked='checked'")+" /></td>");
	    out.println("  <td align='center' class='highlight1'> <input name='positivefeedback"+i+"' type='text' class='input' id='positivefeedback"+i+"' value='"+positiveFeedback+"' size='40' maxlength='2000' /></td>");
	    out.println("  <td class='center'> <input name='negativefeedback"+i+"' type='text' class='input' id='negativefeedback"+i+"' value='"+negativeFeedback+"'  size='40' maxlength='2000' /></td>");
	    out.println("</tr>");
  	}
%>  
    <tr> 
      <td>&nbsp;</td>
      <td>
      	<a href="javascript: addOptions();">
      		<strong><%=cache.getAttribute("D", "optiontaskdef", "addoptionslink", lang)%></strong>
      	</a></td>
      <td align="center" class="highlight1">&nbsp;</td>
      <td class="center">&nbsp;</td>
      <td align="center" class="highlight1">&nbsp;</td>
      <td class="center">&nbsp;</td>
    </tr>
  </table>
  <p>&nbsp;</p>
  <p> 
    <input name="Submit" type="submit" class="button" value="<%=cache.getAttribute("D", "optiontaskdef", "submitbutton", lang)%>" />
  </p>
  </form>
<p>&nbsp; </p>
</body>
</html>
