
/*
 * TrainerParameters.java
 *
 * Created on 30. tammikuuta 2004, 8:38
 */

package fi.hy.eassari.showtask.trainer;
/**
 *
 * @author  laine
 * @version
 */

import javax.servlet.*;
import javax.servlet.http.*;

public class TrainerParameters extends Object {

   String parameterErrors;
   String language="EN";       //optional
   String courseID;            //must
   String moduleID;            //must
   String taskSNo;             //must
   int    taskNo;              //must
   String studentID;           //optional
   String answer[];            //optional
   String params;              //optional
   String merkkijono = null;


	/** Creates new TrainerParameters */
	public TrainerParameters(HttpServletRequest req) {

		// get the parameters from request
		parameterErrors = null;
		if (req.getParameter("lang") != null)          	// use default if not specified
		   language = req.getParameter("lang");
		studentID   = req.getParameter("sid");    		// may be omitted
		taskSNo     = req.getParameter("tno");      	// must
		courseID    = req.getParameter("cid");          // must
		moduleID    = req.getParameter("mid");          // must
		params      = req.getParameter("par");
		answer      = req.getParameterValues("answer");

		// check
		if (courseID==null) {
		   parameterErrors="NOCOURSEID";
		   return;
		}
		if (moduleID==null) {
		   parameterErrors="NOCONTEXTID";
		   return;
		}
		if (taskSNo==null) {
		   parameterErrors="NOTASKID";
		   return;
		}
		try {
		   taskNo= Integer.parseInt(taskSNo);
		}
		catch (NumberFormatException nfe) {
		   parameterErrors="TASKIDERROR";
		   return;
		}
	}

	public String getFullTaskID() {
		return courseID+'.'+moduleID+'.'+taskSNo;
	}

	public int getTaskNo() {
		return taskNo;
	}

	public String getCourseID() {
		return courseID;
	}

	public String getModuleID() {
		return moduleID;
	}

	public String getStudentID() {
		return studentID;
	}

	public boolean studentUnknown() {
		return studentID==null;
	}

	public String [] getAnswer() {
		return answer;
	}

	public String getParams() {
		return params;
	}

	public String getLanguage() {
		return language;
	}

	public String getParameterErrors() {
		return parameterErrors;
	}

	public boolean isFaulty() {
		return parameterErrors!=null;
	}

	public String getHiddens() {
	   StringBuffer hiddens =new StringBuffer();
	   hiddens.append("<input type=\"HIDDEN\" name=\"lang\" value=\"");
	   hiddens.append(language);
	   hiddens.append("\">");
	   if (studentID!=null) {
		  hiddens.append("<input type=\"HIDDEN\" name=\"sid\" value=\"");
		  hiddens.append(studentID);
		  hiddens.append("\">");
	   }
	   hiddens.append("<input type=\"HIDDEN\" name=\"cid\" value=\"");
	   hiddens.append(courseID);
	   hiddens.append("\">");
	   hiddens.append("<input type=\"HIDDEN\" name=\"mid\" value=\"");
	   hiddens.append(moduleID);
	   hiddens.append("\">");
	   hiddens.append("<input type=\"HIDDEN\" name=\"tno\" value=\"");
	   hiddens.append(taskSNo);
	   hiddens.append("\">");
	   return hiddens.toString();
   }

}
