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

TeacherSession settings = null;

settings = (TeacherSession) session.getAttribute(
                            "fi.hy.taskdefinition."+
                            "util.datastructures.TeacherSession");

if (settings != null) {

 lang = settings.getSelectedLanguageId();

}//if

int event = -1;

TaskBase cache = null;
cache = (TaskBase)request.getAttribute(
                  "fi.hy.eassari.showtask.trainer.TaskBase"
                  );

TaskDTO task = null;

String syntaxErrorMsg = null;
boolean syntaxError = false;
String temp; //Parametrin != null v‰litarkistukseen

try {
 event = ( (Integer)request.getAttribute("event") ).intValue();
} catch(Exception e) {
 System.out.println("Error while retrieving event-id: "+e);
}//catch

syntaxErrorMsg = (String)session.getAttribute("TTK91ERROR");

if(syntaxErrorMsg != null) {

 syntaxError = true;

}//if

task = (TaskDTO)  
 request.getSession(false).  
 getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO"); 

out.print("EVENT"+event);

%>

<html>
<head>
<title>T‰ydennysteht‰v‰n m‰‰rittely</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<?xml version="1.0" encoding="iso-8859-1"?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<body bgcolor="#FFFFFF">
<h1>
<%=cache.getAttribute("D", "teachertaskdef",
      "fillinttk91tasktypelabel", lang)
%>
</h1>
<%
 
 if(syntaxError == true) {
  out.print("<h2>" + syntaxErrorMsg + "</h2>");
 }//if

%>

<form method="post" action="
 <%=request.getContextPath()%>/koskelo/composer/TTK91SyntaxChecker" name="FillInTTK91Composer">

 <input name="event" type="hidden" id="event"
  value="<%=Events.FILLIN_TTK91_SUBMIT%>" />
  
 <input name="taskid" type="hidden" id="taskid"
  value="<%=( task == null ? "" : "" + task.getTaskId() )%>" />
  
 <input name="taskname" type="hidden" id="taskname"
  value="<%=( task == null ? "" : "" + task.getTaskName() )%>" />

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
      "taskDescriptionHeader", lang)%>
   <br>

  <textarea name="taskDescription" cols="100" rows="4"><%

   if(syntaxError == true) {

    out.print(request.getParameter("taskDescription"));

   } else if(task != null) {

     temp = (String) task.get("taskDescription");
     if(temp != null) { out.print(temp); }

   }//else

  %></textarea>

  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "publicInputHeader", lang)%>
  <br>
    <input type="text" name="publicInput" size="50"
     value="<%

      if(syntaxError == true) {

       out.print( request.getParameter("publicInput") );

      } else if(task != null) {

       temp = (String) task.get("publicInput");
       if(temp != null) { out.print(temp); } 

      }//else

     %>"
    >
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "hiddenInputHeader", lang)%>
   <br>
   <input type="text" name="hiddenInput" size="50"
    value="<%

     if(syntaxError == true) {

      out.print( request.getParameter("hiddenInput") );

     } else if(task != null) {

      temp = (String) task.get("hiddenInput");
      if(temp != null) { out.print(temp); } 

     }//else

    %>"
   >
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "exampleCodeHeader", lang)%>
   <br>
   <textarea name="exampleCode" cols="50" rows="15"><%

     if(syntaxError == true) {

      out.print( request.getParameter("exampleCode") );

     } else if(task != null) {

      temp = (String) task.get("exampleCode");
      if(temp != null) { out.print(temp); } 

     }//else

    %></textarea>
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "maxCommandsHeader", lang)%>
   <input type="text" name="maxCommands" size="10" maxlength="4"
    value="<%

     if(syntaxError == true) {

      out.print( request.getParameter("maxCommands") );

     } else if(task != null) {

      temp = (String) task.get("maxCommands");
      if(temp != null) { out.print(temp); } 

     }//else

    %>"
   >

   <br>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "acceptedSizeHeader", lang)%> 
   <input type="text" name="acceptedSize" size="10" maxlength="4"
    value="<%

      if(syntaxError == true) {

       out.print( request.getParameter("acceptedSize") );

      } else if(task != null) {

       temp = (String) task.get("acceptedSize");
       if(temp != null) { out.print(temp); } 

      }//else

     %>"
   >
   <br>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "optimalSizeHeader", lang)%>
   <input type="text" name="optimalSize" size="10" maxlength="4"
    value="<%

      if(syntaxError == true) {

       out.print( request.getParameter("optimalSize") );

      } else if(task != null) {

         temp = (String) task.get("optimalSize");
         if(temp != null) { out.print(temp); } 

      }//else

     %>"
   >
  </p>

  <p>
    <input type="radio" name="compareMethod" value="0">
    <%=cache.getAttribute("D", "staticttk91taskcomposer",
     "compareMethod1Header", lang)%>
  </p>
  
  <p> 
    <input type="radio" name="compareMethod" value="1" checked>
    <%=cache.getAttribute("D", "staticttk91taskcomposer",
     "compareMethod2Header", lang)%>
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
     "requiredCommandsHeader", lang)%>
   <br>
   <textarea name="requiredCommands" rows="4" cols="50"><%

     if(syntaxError == true) {

      out.print( request.getParameter("requiredCommands") );

     } else if(task != null) {

      temp = (String) task.get("requiredCommands");
      if(temp != null) { out.print(temp); } 

     }//else

    %></textarea>
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "forbiddenCommandsHeader", lang)%>
  <br>
   <textarea name="forbiddenCommands" rows="4" cols="50"><%

     if(syntaxError == true) {

      out.print( request.getParameter("forbiddenCommands") );

     } else if(task != null) {

      temp = (String) task.get("forbiddenCommands");
      if(temp != null) { out.print(temp); } 

     }//else

    %></textarea>
  </p>

  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "registerValuesHeader", lang)%>
   <br>
    <textarea name="registerValues" cols="50" rows="10"><%

      if(syntaxError == true) {

       out.print( request.getParameter("registerValues") );

      } else if(task != null) {

       temp = (String) task.get("registerValues");
       if(temp != null) { out.print(temp); } 

      }//else

     %></textarea>
  </p>
  
  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "memoryValuesHeader", lang)%>
   <br>
    <textarea name="memoryValues" cols="50" rows="10"><%

      if(syntaxError == true) {

       out.print( request.getParameter("memoryValues") );

      } else if(task != null) {

       temp = (String) task.get("memoryValues");
       if(temp != null) { out.print(temp); } 

      }//else

     %></textarea>

    <br>
    <%=cache.getAttribute("D", "staticttk91taskcomposer",
     "memoryReferencesHeader", lang)%>
    <input type="text" name="memoryReferences" size="10" maxlength="8"
     value="<%

       if(syntaxError == true) {

        out.print( request.getParameter("memoryReferences") );

       } else if(task != null) {

        temp = (String) task.get("memoryReferences");
        if(temp != null) { out.print(temp); } 

       }//else

      %>"
    >
  </p>
  
  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "screenOutputHeader", lang)%>
   <br>
   <textarea name="screenOutput" cols="50" rows="5"><%

     if(syntaxError == true) {

      out.print( request.getParameter("screenOutput") );

     } else if(task != null) {

      temp = (String) task.get("screenOutput");
      if(temp != null) { out.print(temp); } 

     }//else

    %></textarea>
  </p>
  
  <p>
   <%=cache.getAttribute("D", "staticttk91taskcomposer",
    "fileOutputHeader", lang)%>
   <br>
   <textarea name="fileOutput" cols="50" rows="5"><%

     if(syntaxError == true) {

      out.print( request.getParameter("fileOutput") );

     } else if(task != null) {

      temp = (String) task.get("fileOutput");
      if(temp != null) { out.print(temp); } 

     }//else

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
