package fi.hy.eassari.showtask.trainer;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Author Harri Laine

/*****
*   TaskServlet takes care of task presentation
*   It assumes that the task identity is provided in http call parameters
*   Sudent identity may also be provided as an http call parameter or thru 
*   a sesssion cookie
*   A task servlet and an answer base may also be provided as http call parameters
*/
 
 public class TaskServlet extends TrainerServlet {

    
 
    
   public void init(ServletConfig config) throws ServletException {
	  super.init(config);
   }

   public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException {
	// Parameter ses informs whether session is required or not
	// Language, student id, taskid and course
	// are known by session is one exists.
	// They may also be delivered as request parameters
	// HttpSession session= req.getSession(false);
	 String language= "EN"; 
	 TrainerParameters trp=null;
     
	 String courseId= null;
	 String moduleId= null;
	 String fullTID=null;
	 String errorName=null;
    
    
	// load http call parameters
   trp= new TrainerParameters(req);
   fullTID= trp.getFullTaskID();
   // prepare for output
   
   // the session handling should be included gere 
   // find out the session parameters
   // ses = req.getParameter("ses");
   //i f (ses==null || ses.equals("NO"))
   //     sessionRequired=false;
   // if (sessionRequired) {  
   // if session is needed then student information is taken from the session 
   // session= req.getSession(true);    
   // if (!session.isNew()) {
	  //    language= (String) session.getAttribute("lang");
	  //    student= (String) session.getAttribute("sid"); 
	  //    adjust TrainerParameters
	  //    trp.setStudent(student);
	  //    it might be usefult to store also student state in session 
	  // }
	  // else {
	  //   this servlet does not establish new sessions.
	  //    errorCode="NOSESSION";
	  //    feedback= new Feedback(errorCode, ECodes.getMsg(language, errorCode));
	  // }
      
   res.setContentType("text/html");
   PrintWriter out= res.getWriter();
   
   //out.println(pageHeader("testing",null,null));
   //out.println(bodyBegin(null,"WHITE"));
  
  // parameters to content
   language=trp.getLanguage();
  
   
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
		 errorName= "TASKUNKNOWN";
		 out.println( fatalErrorNotification(fullTID, 
			 taskBase.getAttribute("E",errorName,"MESSAGE",language))); 
	 }
	 else {  
       
	   DisplayerInterface disp= tsk.getDisplayer(language);
	   if (disp==null) {
		 errorName="NODISPLAYER";
		 out.println(fatalErrorNotification(fullTID,
					 taskBase.getAttribute("E",errorName,"MESSAGE",language)));
	   }   
	   else {
		  // how everything is ready to build the page
		  // connect the Displayer to the cache
		  disp.registerCache(taskBase); 
          
		  String taskbody= disp.getSetting(trp.getAnswer(),trp.getParams(), trp.getHiddens(), true);
    
		  if (taskbody!=null) {
			  // print the normal page
			 out.println(pageHeader(fullTID, disp.getScript(), tsk.getStyle()));
			 out.println(bodyBegin());
			 out.println(taskbody);
			 out.println(bodyEnd());
			 out.println(footer());
		  }   
		  else {
			 // page building failed 
			 errorName="DISPLAYFAILED";
			 out.println(fatalErrorNotification(fullTID, 
				  taskBase.getAttribute("E",errorName,"MESSAGE",language)));
		  }
	   }  
	 }   
   } // try
   catch (CacheException c) {
	  ems= c.getMessage();
	  out.println(fatalErrorNotification(fullTID, ems));
   }   
  }    
         
public void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException {
   doPost(req,res);
}


}



