package fi.hy.eassari.showtask.trainer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//Added by OP because of his Tomcat test environment:
/*
import fi.hy.eassari.showtask.trainer.Task;
import fi.hy.eassari.showtask.trainer.TaskBase;
import fi.hy.eassari.showtask.trainer.Tasktype;
import fi.hy.eassari.showtask.trainer.Feedback;
import fi.hy.eassari.showtask.trainer.TrainerServlet;
import fi.hy.eassari.showtask.trainer.TrainerParameters;
import fi.hy.eassari.showtask.trainer.CacheException;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.DisplayerInterface;
import fi.hy.eassari.showtask.trainer.AnalyserInterface;
*/


/**
 * Modifed version of the Answer-class, used to deliver answers to analysers and show feedback.
 * 
 * @author Harri Laine modified by Teemu Andersson, Olli-Pekka Ruuskanen
 */
public class Answer2 extends TrainerServlet {


	public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException {


		// Parameter ses informs whether session is required or not
		// Language, student id, taskid and course
		// are known by session is one exists.
		// They may also be delivered as request parameters
		// Session session = req.getSession(false);
		String language= "EN";
		String student=  null;
		String taskId=   null;
		String courseId= null;
		String contextId= null;

		// Task   tsk    = null;
		// Course course = null;

		String errorName=null;
		String eMessage=" ";
		String fullTID=null;
		// HttpSession session = null;
		String ses= null;
		// boolean sessionRequired = true;
		int attempts=0;

		//Feedback feedback= null;
		String taskscript =null;
		String taskbody= null;
		// boolean parameterErrors =false;

		// find out the task parameters and check them

		// ses = req.getParameter("ses");
		// if (ses==null || ses.equals("NO"))
		// 		sessionRequired=false;
		// if (sessionRequired) {
		// 		// if session is needed then student and course information is taken from the session
		//   	session= req.getSession(true);
		//   	if (!session.isNew()) {
		//      	language = (String) session.getAttribute("lang");
		//      	student  = (String) session.getAttribute("sid");
		//      	taskSNo  = (String) session.getAttribute("tno");
		//      	courseId = (String) session.getAttribute("cid");
		//      	moduleId = (String) session.getAttribute("mid");
		//   	}
		//   	else {
		//      	errorCode=ECodes.NOSESSION;
		//      	feedback= new Feedback(errorCode, ECodes.getMsg(language, errorCode));
		//   	}
		// }
		// else {
		// 		// if sessions are not used then the parameters should be passed in request.


		// get and check the parameters, make an error page if parameters no not match

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
			Tasktype tpe=tsk.getTasktype();

			//this part modififed by Teemu Andersson
			String tpname=tpe.getTypeName();
			String ans[]=null;

			/*
			// Further modified by Olli-Pekka Ruuskanen because of also modified
			// getOptionValues() method.
			*/
			if(tpname.equals("optiontask")||tpname.equals("orderingtask")||tpname.equals("blankfilltask"))
				ans = getOptionValues(req);
			else
				ans = trp.getAnswer();

			// Modifications by OPR end.


			/* Modified by OPR 30.4.2004.
			// Output ans array to check modified getOptionValues() method's functioning:

			out.println ("OPTIONCOUNT: " + ans[0] + "<HR>");
			out.println ("<table border=\"0\">");
			String countid = null;
			String tyyppi  = req.getParameter("tasktype");
			if (tyyppi.equals("blankfilltask"))
				countid = "gapcount";
			else
				countid = "optioncount";
			for (int index = 1; index <= Integer.parseInt(req.getParameter(countid)); index++) {
				out.println ("<tr><td>ans[" + index + "] = " + ans[index] + "</td></tr>");
			}
			out.println ("</table>");

			Test code by OPR end.

			*/


			String params= trp.getParams();

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

				if (tsk.shouldAllowRetry(attempts)) {
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
			if(tpname.equals("orderingtask")){
				StringBuffer link=new StringBuffer();
				link.append("<a href=\"");
				link.append(req.getParameter("fullPath"));
				link.append("?");
				Enumeration par=req.getParameterNames();
				while(par.hasMoreElements()){
					String name=(String)par.nextElement();
					String value=req.getParameter(name);
					link.append(name+"="+value);
				}
				
				link.append("\">");
				link.append(taskBase.getAttribute("T", "orderingtask", "trylinklabel", language));
				link.append("</a>");
				//link.append(trp.getHiddens());
				taskbody=new String(link);
			}
			else
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
		if (fb.wasPassed(coff)) {
			out.println("<P CLASS=\"okbox\">");
			out.println(fb.getMsgTextPos());
		}
		else {
			out.println("<P CLASS=\"failbox\">");
			out.println(fb.getMsgTextNeg());
		}
		out.println("</P>");
	}


	/*
	 * Modified method by Olli-Pekka Ruuskanen 30.4.2004. Modified by Teemu Andersson. Generates an array represetantion of the
	 * chosen task options. The following arrays are returned for different task types:
	 *
	 * optiontask:			array[index] = "Y" if option nr. index was chosen.
	 * 						array[index] = "N" if option nr. index was not chosen.
	 *
	 * blankfilltask:		array[index] = string, which was inserted into gap nr. index, if any.
	 * 						array[index] = "", if gap nr. index was left blank.
	 *
	 * orderintask:			array[index] = position of object nr. index.
	 */

	private String[] getOptionValues(HttpServletRequest req) {

		int counter     = 1;
		int optioncount = 0;
		String[] array  = null;

		if (req.getParameter("tasktype").equals("moptiontask")) {
			optioncount = Integer.parseInt(req.getParameter("optioncount"));
			String reqAr[]=req.getParameterValues("option");
			array=new String[optioncount+1];
			for(int i=0; i<array.length; i++)
				array[i]="N";
			array[0]=null;
			if(reqAr!=null)
				for(int i=0; i<reqAr.length; i++){
					int index=Integer.parseInt(reqAr[i]);
					array[index]="Y";
				}
		
		}
		else
		if (req.getParameter("tasktype").equals("optiontask")) {
			optioncount = Integer.parseInt(req.getParameter("optioncount"));
			array       = new String[optioncount+1];
			array[0]     = null; // This value is irrelevant.
			while (counter <= optioncount) {
				if (req.getParameter("option"+counter) == null)
					array[counter] = "N";
				else
					array[counter] = "Y";
				counter++;
			}
		}
		else
		if (req.getParameter("tasktype").equals("blankfilltask")) {
			optioncount = Integer.parseInt(req.getParameter("gapcount"));
			array       = new String[optioncount+1];
			array[0]     = null; // This value is irrelevant.
			while (counter <= optioncount) {
				if (req.getParameter("option"+counter) == null)
					array[counter] = "";
				else
					array[counter] = req.getParameter("option"+counter);
				counter++;
			}
		}
		else
			if (req.getParameter("tasktype").equals("orderingtask")) {
				optioncount = Integer.parseInt(req.getParameter("optioncount"));
				array       = new String[optioncount+1];
				array[0]    = optioncount + "";
				while (counter <= optioncount) {
					array[counter] = req.getParameter("posof"+counter);
					counter++;
				}
			}
		return array;
	}
	
}