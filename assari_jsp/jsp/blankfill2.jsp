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
TaskDTO task = null;

try {
	event = ((Integer)request.getAttribute("event")).intValue();
} catch(Exception e) { System.out.println("Error while retrieving event-id: "+e); }

task = (TaskDTO)request.getSession(false).getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO"); 

int numberOfSpots = 0;

if (task != null) {
	String blankSpot = null;
	do {
		blankSpot = (String)task.get("blank"+(numberOfSpots+1));
		if (blankSpot != null) {
			++numberOfSpots;
		}
	} while (blankSpot != null);
}
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
<form action="<%=request.getContextPath()%>/eAssari/taskDefinition/controller" method="post" name="blankfill2" id="blankfill2">

    <input name="event" type="hidden" id="event" value="<%=Events.BLANKFILL_FEEDBACK_SUBMIT%>" />
    <input name="taskid" type="hidden" id="taskid" value="<%=(task == null ? "" : ""+task.getTaskId())%>" />
    <input name="taskname" type="hidden" id="taskname" value="<%=(task == null ? "" : ""+task.getTaskName())%>" />
    <input name="numberofblankspots" type="hidden" id="numberofblankspots" value="<%=numberOfSpots%>" />

  <p class="header"><%=cache.getAttribute("D", "blankfilltaskdef", "headline2", lang)%></p>
  <table border="0" cellpadding="5" cellspacing="0" class="helpnote">
    <tr> 
      <td><p><%=cache.getAttribute("D", "blankfilltaskdef", "infobox2", lang)%></p>
        </td>
    </tr>
  </table>
  <br />
  <table width="100%" border="0" cellpadding="5" cellspacing="0">
    <tr> 
      <td class="tableHeader">&nbsp;</td>
      <td class="tableHeader"><%=cache.getAttribute("D", "blankfilltaskdef", "blankspotlabel", lang)%></td>
      <td colspan="2" class="tableHeader"><%=cache.getAttribute("D", "blankfilltaskdef", "feedbacklabel", lang)%></td>
    </tr>
    <tr class="subHeader"> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td align="center" class="highlight1"><%=cache.getAttribute("D", "blankfilltaskdef", "correctlabel", lang)%></td>
      <td align="center"><%=cache.getAttribute("D", "blankfilltaskdef", "falselabel", lang)%></td>
    </tr>

<%
  	for (int i=1; i<=numberOfSpots; ++i) { 
	
		if (task == null)
			break;

		String blankSpot = "";
		String textBeforeSpot = "";
		String textAfterSpot = "";
		String positiveFeedback = "";
		String negativeFeedback = "";

		if (task != null) {
			blankSpot = ((String)task.get("blank"+i)).trim();
			textBeforeSpot = (String)task.get("before"+i);
			textAfterSpot = (String)task.get("after"+i);
			positiveFeedback = (String)task.get("positivefeedback"+i);
			negativeFeedback = (String)task.get("negativefeedback"+i);
			
			if(blankSpot == null){
				blankSpot = "";
			}
			
			if(textBeforeSpot == null){
				textBeforeSpot = "";
			}
			
			if(textAfterSpot == null){
				textAfterSpot = "";
			}
			
			if(positiveFeedback == null){
				positiveFeedback = "";
			}
			
			if(negativeFeedback == null){
				negativeFeedback = "";
			}
		}

    	out.println("<tr>"); 
      	out.println("  <td><strong>"+i+".</strong></td>");
      	out.println("  <td class='small'>..."+textBeforeSpot);
        out.println("    <span class='blankspot'>"+blankSpot+"</span> ");
        out.println("  "+textAfterSpot+"...</td>");
		out.println("  <td class='highlight1'><input name='positivefeedback"+i+"' type='text' class='input' id='positivefeedback"+i+"' size='40' maxlength='2000' value='" + positiveFeedback + "'/></td>");
		out.println("  <td><input name='negativefeedback"+i+"' type='text' class='input' id='negativefeedback"+i+"' size='40' maxlength='2000' value='" + negativeFeedback + "'/></td>");
		out.println("</tr>");
	}
%>
  </table>
  <p align="right"> <br />
  <% 
  	if (numberOfSpots > 0)
		out.println("<input name='Submit' type='submit' class='button' value='"+cache.getAttribute("D", "blankfilltaskdef", "submitbutton", lang)+"' />");
  %>  
  </p>
  </form>
</body>
</html>
