<%@ page import="fi.hy.eassari.taskdefinition.util.datastructures.*" %>
<%@ page import="fi.hy.eassari.taskdefinition.util.*" %>
<%@ page import="fi.hy.eassari.showtask.trainer.TaskBase" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>

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

if (event == Events.EDIT_TASK || event == Events.EDIT_TASK_AS_NEW || event == Events.EDIT_TASK_AS_NEW_LANG) 
	editTask = true;
if (editTask)
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

<form action="<%=request.getContextPath()%>/eAssari/taskDefinition/controller" method="post" name="tasksettings" id="tasksettings">
  <p class="header"><%=cache.getAttribute("D", "generaltaskdef", "tasksettingslabel", lang)%> 
    <input name="event" type="hidden" id="event" value="<%=Events.SUBMIT_TASK%>" />
    <input name="taskid" type="hidden" id="taskid" value="<%=(task == null ? "" : ""+task.getTaskId())%>" />
    <input name="saveasnew" type="hidden" id="saveasnew" value="0" />
    <input name="saveasnewlanguage" type="hidden" id="saveasnewlanguage" value="0" />
  </p>
  <table border="0" cellpadding="3" cellspacing="0" class="bordered">
    <tr class="tableHeader"> 
      <td colspan="2" align="left"><%=cache.getAttribute("D", "generaltaskdef", "generalsettingslabel", lang)%></td>
    </tr>
    <tr>
      <td align="left" valign="top"><%=cache.getAttribute("D", "generaltaskdef", "tasknamelabel", lang)%></td>
      <td><input name="taskname" value="<%=(task == null ? "" : task.getTaskName())%>" type="text" class="input" id="taskname" size="40" maxlength="40" /></td>
    </tr>
    <tr> 
      <td align="left" valign="top"><%=cache.getAttribute("D", "generaltaskdef", "tasktypelabel", lang)%></td>
      <td>
      
<%	if (task == null) { %>
      <select name="tasktype" id="tasktype">
          <option selected="selected" value="<%=TaskTypes.OPTION_TASK%>"><%=cache.getAttribute("D", "teachertaskdef", "optiontasktypelabel", lang)%></option>
          <option value="<%=TaskTypes.BLANKFILL_TASK%>"><%=cache.getAttribute("D", "teachertaskdef", "blankfilltasktypelabel", lang)%></option>
          <option value="<%=TaskTypes.ORDERING_TASK%>"><%=cache.getAttribute("D", "teachertaskdef", "orderingtasktypelabel", lang)%></option>
        </select>
<%	} 
	else {
		out.println(cache.getAttribute("D", "teachertaskdef", task.getTaskType()+"typelabel", lang));
		out.println("<input type='hidden' name='tasktype' value='"+task.getTaskType()+"'>");
	}	
%>        
        </td>
    </tr>
    <tr>
      <td align="left" valign="top"><%=cache.getAttribute("D", "generaltaskdef", "languagelabel", lang)%></td>
      <td><select name='language' id='language'>
<% 
	Collection allLanguages = LanguageHandler.getSystemLanguages();

	if (task != null && event == Events.EDIT_TASK_AS_NEW_LANG) {
		Collection taskLanguages = LanguageHandler.getTaskLanguages(task.getTaskId());
		Iterator taskLangIter = taskLanguages.iterator();

		// Remove all languages that have already been defined
		while (taskLangIter.hasNext()) {
			LanguageDTO language = (LanguageDTO)taskLangIter.next();
			allLanguages.remove(language);
		}
	}
	Iterator langIter = allLanguages.iterator();
	String taskLanguage = "";
	if (task != null)
		taskLanguage = task.getLanguage();
	if (taskLanguage == null)
		taskLanguage = "";
	while (langIter.hasNext()) {
		LanguageDTO language = (LanguageDTO)langIter.next();
		out.println("<option value='"+language.getLanguageId()+"' "+(taskLanguage.equals(language.getLanguageId()) ? "selected='selected'" : "")+">"+language.getLanguageName()+"</option>");
	}
%>
      </select> </td>
    </tr>
    <tr> 
      <td align="left" valign="top"><%=cache.getAttribute("D", "generaltaskdef", "assignmentlabel", lang)%></td>
      <td><p> 
          <textarea name="task" cols="40" rows="5" class="input" id="task"><%=(task == null ? "" : ""+task.get("task"))%></textarea>
        </p></td>
    </tr>
  </table>
  <br />
  <table border="0" cellpadding="3" cellspacing="0" class="bordered">
    <tr class="tableHeader"> 
      <td colspan="2" align="left"><%=cache.getAttribute("D", "generaltaskdef", "taskpolicieslabel", lang)%></td>
    </tr>
<%
boolean requireLogin = false;
boolean registerTry = false;
boolean saveAnswer = false;
boolean immediateFeedback = true;
if (task!=null) {
	requireLogin = task.getBooleanShouldKnowStudent();
	registerTry = task.getBooleanShouldRegisterTry();
	saveAnswer = task.getBooleanShouldStoreAnswer();
	immediateFeedback = task.getBooleanImmediateFeedback();
}
%>
    <tr> 
      <td><input name="loginrequired" <%=(requireLogin ? "checked='checked'" : "")%> type="checkbox" id="loginrequired" value="1" />
        <%=cache.getAttribute("D", "generaltaskdef", "loginlabel", lang)%></td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td><input name="registertry" <%=(registerTry ? "checked='checked'" : "")%> type="checkbox" id="registertry" value="1" />
        <%=cache.getAttribute("D", "generaltaskdef", "registerlabel", lang)%></td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td><input name="saveanswer" <%=(saveAnswer ? "checked='checked'" : "")%> type="checkbox" id="saveanswer" value="1" />
        <%=cache.getAttribute("D", "generaltaskdef", "savelabel", lang)%></td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td align="left"><%=cache.getAttribute("D", "generaltaskdef", "acceptancelimitlabel", lang)%></td>
      <td><select name="cutofvalue" id="cutofvalue">
<%
int limit;
if (task==null)
	limit = 100;
else
	limit = task.getCutOffValue();

String globalPositiveFeedback = null;
String globalNegativeFeedback = null;

if(task != null){
	globalPositiveFeedback = (String)task.get("positivefeedback");
	globalNegativeFeedback = (String)task.get("negativefeedback");
	
	out.println("GlobalPos: " + globalPositiveFeedback + " globalNeg: " + globalNegativeFeedback);
}
%>
          <option value="100" <%=(limit == 100 ? "selected='selected'" : "")%>>100%</option>
          <option value="90" <%=(limit == 90 ? "selected='selected'" : "")%>>90%</option>
          <option value="80" <%=(limit == 80 ? "selected='selected'" : "")%>>80%</option>
          <option value="70" <%=(limit == 70 ? "selected='selected'" : "")%>>70%</option>
          <option value="60" <%=(limit == 60 ? "selected='selected'" : "")%>>60%</option>
          <option value="50" <%=(limit == 50 ? "selected='selected'" : "")%>>50%</option>
          <option value="40" <%=(limit == 40 ? "selected='selected'" : "")%>>40%</option>
          <option value="30" <%=(limit == 30 ? "selected='selected'" : "")%>>30%</option>
          <option value="20" <%=(limit == 20 ? "selected='selected'" : "")%>>20%</option>
          <option value="10" <%=(limit == 10 ? "selected='selected'" : "")%>>10%</option>
          <option value="0" <%=(limit == 0 ? "selected='selected'" : "")%>>0%</option>
        </select></td>
    </tr>
    <tr> 
      <td align="left"><%=cache.getAttribute("D", "generaltaskdef", "feedbacklabel", lang)%> </td>
      <td><p> 
          <input name="immediatefeedback" <%=(immediateFeedback ? "checked='checked'" : "")%> type="radio" value="1" checked="checked" />
          <%=cache.getAttribute("D", "generaltaskdef", "immediateradiobutton", lang)%> <br />
          <input type="radio" <%=(immediateFeedback ? "" : "checked='checked'")%> name="immediatefeedback" value="0" />
          <%=cache.getAttribute("D", "generaltaskdef", "delayedradiobutton", lang)%> </p></td>
    </tr>
    <tr> 
      <td align="left"><%=cache.getAttribute("D", "generaltaskdef", "numberoftrieslabel", lang)%> <br />
        <span class="small"><em><%=cache.getAttribute("D", "generaltaskdef", "numberoftrieslabel2", lang)%></em></span></td>
      <td><input name="numberoftries" value="<%=(task == null ? "" : ""+task.getNumberOfTries())%>" type="text" class="input" id="numberoftries" size="4" maxlength="4" /></td>
    </tr>
  </table>
  <br />
  <table border="0" cellpadding="3" cellspacing="0" class="bordered">
    <tr class="tableHeader"> 
      <td colspan="2" align="left"><strong><%=cache.getAttribute("D", "generaltaskdef", "globalfeedbacklabel", lang)%></strong></td>
    </tr>
    <tr> 
      <td align="left"><%=cache.getAttribute("D", "generaltaskdef", "acceptedlabel", lang)%></td>
      <td><textarea name="positivefeedback" class="input" id="positivefeedback"><%=globalPositiveFeedback == null ? "" : globalPositiveFeedback%></textarea></td>
    </tr>
    <tr> 
      <td align="left"><%=cache.getAttribute("D", "generaltaskdef", "unacceptedlabel", lang)%></td>
      <td><textarea name="negativefeedback" class="input" id="negativefeedback"><%=globalNegativeFeedback == null ? "" : globalNegativeFeedback%></textarea></td>
    </tr>
  </table>
  <p align="left"> 
    <input name="submit" type="submit" class="button" value="<%=cache.getAttribute("D", "generaltaskdef", "submitbutton", lang)%>" />
  </p>
  </form>
</body>
</html>
