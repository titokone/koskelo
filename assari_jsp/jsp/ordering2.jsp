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

task = (TaskDTO)request.getSession(false).getAttribute("fi.hy.taskdefinition.util.datastructures.TaskDTO"); 

int numberOfObjects = 0;

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
<script language="JavaScript" src="http://www.cs.helsinki.fi/group/assari/dynapi/js/dynlayer.js"></script>
<script language="JavaScript" src="http://www.cs.helsinki.fi/group/assari/dynapi/js/mouseevents.js"></script>
<script language="JavaScript" src="http://www.cs.helsinki.fi/group/assari/dynapi/js/drag.js"></script>
<script language="JavaScript">

// layout parameters
var topMargin = 200
var leftMargin = 50
var orderingObjectHeight = 50
var orderingObjectVerticalSpacing = 20


// object handling
var numberOfObjects = <%=numberOfObjects%>
var positions = new Array(numberOfObjects)


function init() {
	// initialize DynLayers
	DynLayerInit()


	// add the draggable layers to the drag object
<%  
	StringBuffer layers = new StringBuffer();
	for (int i=0; i<numberOfObjects; ++i) {
		layers.append("option"+(i+1));
		if (i != (numberOfObjects-1))
			layers.append(", ");
	}
%>
	drag.add(<%=layers.toString()%>)


	drag.onDragStart = dragStart
	drag.onDragMove = dragMove
	drag.onDragEnd = dragEnd
		
	initMouseEvents()


// initialize the array reflecting the order of the objects
<%
	for (int i=0; i<numberOfObjects; ++i) 
		out.println("positions["+i+"] = option"+(i+1));
%>	
// move objects to initial positions and redraw
	moveObjects()
}
function dragStart(x,y) {
	// move all sliding objects to their final positions to avoid collisions
	return false
}
function dragMove(x,y) {
}
function dragEnd(x,y) {
	// reorder and redraw objects
	moveObjects()	
}
// moves all objects to their correct positions according to the positions-array
function moveObjects() {
	// order positions table (ascending order) according to objects y-coordinates
	for (var i = 0; i < numberOfObjects-1; i++) 
		for (var j = i+1; j < numberOfObjects; j++) 
			if (positions[i].y > positions[j].y) {
				var tmp = positions[i]
				positions[i] = positions[j]
				positions[j] = tmp
			}

	// redraw objects to correct positions
	var currentY = topMargin
	for (var i = 0; i<numberOfObjects; i++) { 
		positions[i].moveTo(leftMargin, currentY)
		currentY += orderingObjectHeight + orderingObjectVerticalSpacing
	}
	resolveObjectPositions()
}
function resolveObjectPositions() {
         for (var i=0; i<numberOfObjects; ++i) {
<%
			for (int object=0; object<numberOfObjects; ++object) {
				out.println("if (positions[i].id == 'option"+(object+1)+"Div') {");
                out.println("	document.ordering2.posof"+(object+1)+".value = ''+(i+1)");
                out.println("	continue");
                out.println("}");
            }
%>
         }
}
function getObjectHeight() {
	return orderingObjectHeight
}


</script>
<style type="text/css">
<!--

<%
// Print out the layer divisions representing the orderable objects

	for (int i=0; i<numberOfObjects; ++i) 
		out.println("#option"+(i+1)+"Div {position:absolute; left:0; top:0; height:50}");
%>

->
</style>
<link href="http://www.cs.helsinki.fi/group/assari/css/assari.css" rel="stylesheet" type="text/css" />
</head>


<body onLoad="init()" class="body">
<p class="header"><%=cache.getAttribute("D", "orderingtaskdef", "headline2", lang)%></p>
<table border="0" cellpadding="5" cellspacing="0" class="helpnote">
  <tr> 
    <td><p><%=cache.getAttribute("D", "orderingtaskdef", "infobox2", lang)%></p></td>
  </tr>
</table>
<p align="right">
<form name="ordering2" action="<%=request.getContextPath()%>/eAssari/taskDefinition/controller" method="post">


  <input name="event" type="hidden" id="event" value="<%=Events.ORDERING_ORDER_SUBMIT%>" />
  <input name="taskid" type="hidden" id="taskid" value="<%=(task == null ? "" : ""+task.getTaskId())%>" />
  <input name="taskname" type="hidden" id="taskname" value="<%=(task == null ? "" : ""+task.getTaskName())%>" />
  <input name="numberofoptions" type="hidden" id="numberofoptions" value="<%=numberOfObjects%>" />  

  <strong><%=cache.getAttribute("D", "generaltaskdef", "assignmentlabel", lang)+": "%></strong>
  <%=(task==null ? "" : task.get("task"))%>

<%
// Print out the hidden fields representing the positions

	for (int i=0; i<numberOfObjects; ++i) 
		out.println("<input name='posof"+(i+1)+"' type='hidden' value=''>");

  if (numberOfObjects > 0)
	  out.println("<input name='Submit' type='submit' class='button' value='"+cache.getAttribute("D", "orderingtaskdef", "submitbutton", lang)+"'>");
  	  
%>

</form>  
</p>

<%
// Print out the xhtml-content displaying the orderable objects

	for (int i=0; i<numberOfObjects; ++i) 
		out.println("<div id='option"+(i+1)+"Div' class='orderingblock'><p>"+task.get("object"+(i+1))+"</p></div>");
%>
</body>
</html>
