<%@ page import=
 "fi.hy.eassari.taskdefinition.util.datastructures.*"
%>
<%@
 page import="fi.hy.eassari.taskdefinition.util.*"
%>
<%@
 page import="fi.hy.eassari.showtask.trainer.TaskBase"
%>

<%
                    //resolve selected language
String lang = "EN"; //default language is english
                    //should check selected language

TeacherSession settings = (TeacherSession)
                          session.getAttribute(
                          "fi.hy.taskdefinition."+
                          "util.datastructures.TeacherSession");

if (settings != null) {
 lang = settings.getSelectedLanguageId();
}//if

int event = -1;
TaskBase cache = (TaskBase)
                 request.getAttribute(
                  "fi.hy.eassari.showtask.trainer.TaskBase"
                 );

boolean editTask = false;
TaskDTO task = null;

try {
 event = ( (Integer)request.getAttribute("event") ).intValue();
} catch(Exception e) {
 System.out.println("Error while retrieving event-id: "+e);
}//catch

if (event == Events.STATIC_TTK91_EDIT) {
 editTask = true;
 task = (TaskDTO)
 request.getSession(false).
 getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO");
}

String syntaxError = (String)session.getAttribute("TTK91ERROR");

%>

<html>
<head>
<title>Staattisen teht�v�n m��rittely</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<?xml version="1.0" encoding="iso-8859-1"?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<body bgcolor="#FFFFFF">
<h1>Staattisen teht�v�n luonti</h1>
<form method="post" action="
 <%=request.getContextPath()%>/koskelo/composer/TTK91SyntaxChecker" name="StaticTTK91Composer">

 <input name="event" type="hidden" id="event"
  value="<%=Events.STATIC_TTK91_SUBMIT%>" />
  
 <input name="taskid" type="hidden" id="taskid"
  value="<%=( task == null ? "" : "" + task.getTaskId() )%>" />
  
 <input name="taskname" type="hidden" id="taskname"
  value="<%=( task == null ? "" : "" + task.getTaskName() )%>" />

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
      "taskDescriptionHeader", lang)%>
   <br>

  <textarea name="taskDescription" cols="100" rows="4"><%
   if(syntaxError != null) {
    out.print(request.getAttribute("taskDescription"));
   }//if
  %></textarea>

  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "publicInputHeader", lang)%>
  <br>
    <input type="text" name="publicInput" size="50"
     value="<%
      if(syntaxError != null) {
       out.print( request.getAttribute("publicInput") );
      }//if
     %>"
    >
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "hiddenInputHeader", lang)%>
   <br>
   <input type="text" name="hiddenInput" size="50"
    value="<%
     if(syntaxError != null) {
      out.print( request.getAttribute("hiddenInput") );
     }//if
    %>"
   >
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "exampleCodeHeader", lang)%>
   <br>
   <textarea name="exampleCode" cols="50" rows="15"><%
     if(syntaxError != null) {
      out.print( request.getAttribute("exampleCode") );
     }//if
    %></textarea>
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "maxCommandsHeader", lang)%>
   <input type="text" name="maxCommands" size="10" maxlength="4"
    value="<%
     if(syntaxError != null) {
      out.print( request.getAttribute("maxCommands") );
     }//if
    %>"
   >

   <br>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "acceptedSizeHeader", lang)%> 
   <input type="text" name="acceptedSize" size="10" maxlength="4"
    value="<%
      if(syntaxError != null) {
       out.print( request.getAttribute("acceptedSize") );
      }//if
     %>"
   >
   <br>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "optimalSizeHeader", lang)%>
   <input type="text" name="optimalSize" size="10" maxlength="4"
    value="<%
      if(syntaxError != null) {
       out.print( request.getAttribute("taskDescription") );
      }//if
     %>"
   >
  </p>

  <p>
    <input type="radio" name="compareMethod" value="radiobutton">
    <%=cache.getAttribute("D", "staticttk91taskcomposer",
     "compareMethod1Header", lang)%>
  </p>
  
  <p> 
    <input type="radio" name="compareMethod" value="radiobutton" checked>
    <%=cache.getAttribute("D", "staticttk91taskcomposer",
     "compareMethod2Header", lang)%>
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
     "requiredCommandsHeader", lang)%>
   <br>
   <textarea name="requiredCommands" rows="4" cols="50"><%
     if(syntaxError != null) {
      out.print( request.getAttribute("requiredCommands") );
     }//if
    %></textarea>
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "forbiddenCommandsHeader", lang)%>
  <br>
   <textarea name="forbiddenCommands" rows="4" cols="50"><%
     if(syntaxError != null) {
      out.print( request.getAttribute("forbiddenCommands") );
     }//if
    %></textarea>
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "registerValuesHeader", lang)%>
   <br>
    <textarea name="registerValues" cols="50" rows="10"><%
      if(syntaxError != null) {
       out.print( request.getAttribute("registerValues") );
      }//if
     %></textarea>
  </p>
  
  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "memoryValuesHeader", lang)%>
   <br>
    <textarea name="memoryValues" cols="50" rows="10"><%
      if(syntaxError != null) {
       out.print( request.getAttribute("memoryValues") );
      }//if
     %></textarea>
    <br>
    <%=cache.getAttribute("D", "staticttk91taskcomposer",
     "memoryReferencesHeader", lang)%>
    <input type="text" name="memoryReferences" size="10" maxlength="8"
     value="<%
       if(syntaxError != null) {
        out.print( request.getAttribute("memoryReferences") );
       }//if
      %>"
    >
  </p>
  
  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "screenOutputHeader", lang)%>
   <br>
   <textarea name="screenOutput" cols="50" rows="5"><%
     if(syntaxError != null) {
      out.print( request.getAttribute("screenOutput") );
     }//if
    %></textarea>
  </p>
  
  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "fileOutputHeader", lang)%>
   <br>
   <textarea name="fileOutput" cols="50" rows="5"><%
     if(syntaxError != null) {
      out.print( request.getAttribute("fileOutput") );
     }//if
    %></textarea>
  </p>
  
    <input type="submit" name="Submit"
     value="<%=cache.getAttribute(
            "D", "staticttk91taskcomposer",
            "submitButtonText", lang)
            %>"
    >
    
</form>
</body>
</html>
