package fi.hy.eassari.showtask.trainer;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// Author Harri Laine
public class Answer extends TrainerServlet {

public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException {
	// Parameter ses informs whether session is required or not
	// Language, student id, taskid and course
	// are known by session is one exists.
	// They may also be delivered as request parameters
	//Session session= req.getSession(false);
	String language= "EN"; 
	String student=  null; 
	String taskId=   null;
	String courseId= null;
	String contextId= null;
    
	//Task tsk= null;
	//Course course = null;
    
	String errorName=null;
	String eMessage=" ";
	String fullTID=null; 
	// HttpSession session= null;
	String ses= null;
	// boolean sessionRequired=true;
	int attempts=0;
    
	//Feedback feedback= null;
	String taskscript =null;
	String taskbody= null;
   // boolean parameterErrors =false;

	// find out the task parameters and check them
  
	//ses = req.getParameter("ses");
	//if (ses==null || ses.equals("NO"))
	//   sessionRequired=false;
	//if (sessionRequired) {  
	   // if session is needed then student and course information is taken from the session 
	//   session= req.getSession(true);    
	//   if (!session.isNew()) {
	//      language= (String) session.getAttribute("lang");
	//      student= (String) session.getAttribute("sid");
	//      taskSNo= (String) session.getAttribute("tno");
	//      courseId = (String) session.getAttribute("cid");
	//      moduleId= (String) sesion.getAttribute("mid");
	//   }
	//   else {
	//      errorCode=ECodes.NOSESSION;
	//      feedback= new Feedback(errorCode, ECodes.getMsg(language, errorCode));
	//   }
	//else {
	   // if sessions are not uses then the parameters should be passes in request
    
	/* get and check the parameters, make an error page if parameters no not match
	 */ 
	TrainerParameters trp= new TrainerParameters(req);

	res.setContentType("text/html");
	PrintWriter out= res.getWriter();
   
	language=trp.getLanguage();
	fullTID= trp.getFullTaskID();
    
	String ems=null;
	if (trp.isFaulty()) {
	  try {  
		  ems= taskBase.getAttribute("E",trp.getParameterErrors(),"MESSAGE",language);
	   }
	  catch (CacheException cex) {
		  ems = cex.getMessage();
	   } 
	  out.println(fatalErrorNotification(fullTID, ems));
	  return;
   }
  // parameters are OK  
   try {   
	  Task tsk= taskBase.taskById(trp.getTaskNo(), trp.getCourseID(), trp.getModuleID());
	  if (tsk==null) {         
		 ems= taskBase.getAttribute("E","TASKUNKNOWN","MESSAGE",language);
		 out.println(fatalErrorNotification(fullTID, ems));
		 return;   
	  }
	  if (trp.studentUnknown() && tsk.shouldKnowStudent()) {
		 ems= taskBase.getAttribute("E","NOSTUDENT","MESSAGE",language); 
		 out.println(fatalErrorNotification(fullTID, ems));
		 return;
	  }
	  // get the analyser
	  boolean wasAnalysed = false;
	  Feedback feedback=null;
	  String[] ans = trp.getAnswer();
	  String params= trp.getParams();
	  Tasktype tpe=tsk.getTasktype();
	  if (tsk.shouldBeAnalysed()) {
		 AnalyserInterface ani= tsk.getAnalyser(language);
		 if (ani==null) {
			ems= taskBase.getAttribute("E","NOANALYSER","MESSAGE",language)+ tpe.getAnalyserClass(); 
			out.println(fatalErrorNotification(fullTID, ems));
			return;
		 }
		 ani.registerCache(taskBase);      
		 feedback= ani.analyse(ans,params);
		 wasAnalysed= true;
		 if (feedback.causedFatalError()) {
			ems= feedback.getMsgTextNeg();
			out.println(fatalErrorNotification(fullTID, ems));
			return;
		 }
	  }   
 /*   if (tsk.shouldRegisterTry()) {
		 attempts= StudentManagement.registerTry(course_id, task_id, student_id, feedback);
	  }        
	  if (tsk.shouldStoreAnswer()) { 
		 errorcode= StudentManagement.storeAnswer(course_id, task_id, student_id, answers, feedback);
	  )
  */   
  /*    if (tsk.shouldAllowRetry(attempts)) {
			 taskscript= tsk.getScript(language);
			 taskbody= tsk.getSetting(language);
	  }    
  */   
         
         
	  // build the page
	  // first show the task
	   DisplayerInterface disp= tsk.getDisplayer(language);
	   if (disp==null) {
		  ems= taskBase.getAttribute("E","NODISPLAYER","MESSAGE",language); 
		  out.println(fatalErrorNotification(fullTID, ems));
		  return;
	   } 
	   disp.registerCache(taskBase);
	   taskbody= disp.getSetting(ans, params, trp.getHiddens(), true);
	   if (taskbody==null) {
		   ems= taskBase.getAttribute("E","DISPLAYFAILED","MESSAGE",language);
		   out.println(fatalErrorNotification(trp.getFullTaskID(), ems));
		   return;
	   }    
	   out.println(pageHeader(fullTID, disp.getScript(), tsk.getStyle()));
	   out.println(bodyBegin());
	   out.println(taskbody);
	      
	   if (wasAnalysed) {
		   feedbackbox(tsk.getCutoffvalue(), out, feedback);
		   String extra = feedback.getExtra();
		   if (extra!=null) 
			   out.println(extra);
	   }    
	   out.println(bodyEnd());
	   out.println(footer());
   }    
   catch (CacheException c) {
	  ems= c.getMessage();
	  out.println(fatalErrorNotification(trp.getFullTaskID(), ems));
	  return;
   }   
}

public void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException {
   doPost(req,res);
}


private void feedbackbox(int coff, PrintWriter out, Feedback fb) {  
	if (fb.wasPassed(coff)) 
	   out.println("<P CLASS=\"okbox\">");
	else   
	   out.println("<P CLASS=\"failbox\">"); 
	out.println(fb.getMsgTextPos());
	out.println("</P>");
}

}

