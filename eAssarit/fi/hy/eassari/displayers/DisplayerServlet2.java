package fi.hy.eassari.displayers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import fi.hy.eassari.showtask.trainer.Task;
import fi.hy.eassari.showtask.trainer.Tasktype;
import fi.hy.eassari.showtask.trainer.TrainerServlet;
import fi.hy.eassari.showtask.trainer.CacheException;
import fi.hy.eassari.showtask.trainer.TrainerParameters;
import fi.hy.eassari.showtask.trainer.DisplayerInterface;



/**
 * Servlet which accepts task parameters from HTTP requests and activates
 * corresponding task displayer components to generate task presentations
 * for the users.
 *
 * @author  Olli-Pekka Ruuskanen
 * @version 2.5.2004
 */

public class DisplayerServlet2 extends TrainerServlet {

	String  userid      = null;
	String  language    = null;
	String  courseid    = null;
	String  moduleid    = null;
	int     seqNo       = 0;
	String  taskid      = null;
	String  css         ="http://www.cs.helsinki.fi/group/assari/css/assari.css";
	String  fullTID     = null;
	String  errorName   = null;
	Task    tsk         = null;
	ServletOutputStream out;



	/**
	 * Method to initialize cache used by the servlet.
	 *
	 * @param  config				File which contains servlet initialization parameters.
	 * @throws ServletException 	If servlet initalization parameters cannot be read from ServletConfig context.
	 */

   public void init (ServletConfig config) throws ServletException {
		super.init(config);
   }




	/**
	 * Method to receive task display requests from web page, and to check that
	 * requestsd task exists either in the desired language or in system's default 
	 * anguage. Requested language is used primarily and default language secondarily.
	 *
	 * @param   req	HTTPServletRequest
	 * @param	res HTTPServletResponse
	 * @throws	ServletException 	If there are problems with servlet operations.
	 * @throws 	IOException			If there are problems with output operations to the user's web browser.
	 *
	 */

	/*
	 * Customer's original design to provide the following parameters from
	 * the calling web page is used:
	 *
	 * lang = language used to display the task.
	 * sid	= studentID.
	 * cid	= courseID. Course is composed of different modules. 	COMPULSORY.
	 * mid	= moduleID. Module is composed of different sequences. 	COMPULSORY.
	 * tno	= sequence number. Sequence is a location of a task.	COMPULSORY.
	 * par	= task parameters.
	 */

	public void doPost (HttpServletRequest req, HttpServletResponse res)
						throws ServletException, IOException {


		String  ems           = null;
		String  errorMessage  = null;
		boolean langDefined   = false;
		TrainerParameters trp = new TrainerParameters(req);

		// Get data from the trp-object to class variables:
		userid       = trp.getStudentID();
		language     = trp.getLanguage();
		courseid     = trp.getCourseID();
		moduleid     = trp.getModuleID();
		seqNo        = trp.getTaskNo();
		fullTID      = trp.getFullTaskID();

		res.setContentType ("text/html");
		out = res.getOutputStream();

		// Check that the data received from the form contains all the compulsory fields.
		// If not, generate an error message:
		if (trp.isFaulty()) {
			try {
				ems = taskBase.getAttribute("E",trp.getParameterErrors(),"MESSAGE",language);
			}
			catch (CacheException cex) {
				ems = cex.getMessage();
			}
			out.println("FATAL ERROR: " + fatalErrorNotification(fullTID, ems));
			return;
		}

		// If compulsory data given, continue:
		try {

			// Get the task which is attached to the specific course's specific module's
			// specific sequence (courseid + moduleid + sequence is a key to a certain task):
			taskid = taskBase.getTaskID  (courseid, moduleid, seqNo);
			tsk    = taskBase.taskById   (seqNo, courseid, moduleid);

			// If no such task exists:
			if (tsk == null) {
				errorName = "TASKUNKNOWN";

				// Show error message in requested language, if defined. Otherwise
				// show error message in english:
				errorMessage = taskBase.getAttribute("E",errorName,"MESSAGE",language);
				if (errorMessage == null)
					out.println (fatalErrorNotification(fullTID, taskBase.getAttribute("E",errorName,"MESSAGE","EN")));
				else
					out.println (fatalErrorNotification(fullTID, taskBase.getAttribute("E",errorName,"MESSAGE",language)));
				return;
			}

			// If the task exists, but the requested language option for it has not been
			// defined, inform user and show the task in default language if defined in it.
			// If not, cannot show the task at all:
			else {
				// Check if task has been defined in desired language:
				langDefined = taskBase.languageDefined(taskid, language);
				if (!langDefined) {
					// If task hasn't been defined in desired language, check if is has been defined in english:
					langDefined = taskBase.languageDefined(taskid, "EN");
					if (!langDefined)
						errorName = "NOLANGUAGE";
					else
						errorName = "LANGUAGEUNKNOWN";
					// Show appropriate error message in requested language, if defined.
					// Otherwise show it in english:
					errorMessage = taskBase.getAttribute("E",errorName,"MESSAGE",language);
					out.println ("<HTML>");
					out.println ("<HEAD>");
					out.println ("<link href=" + css + " rel=\"stylesheet\" type=\"text/css\">");
					out.println ("</HEAD>");
					out.println ("<BODY>");
					out.println ("<BR><BR><BR><BR><BR><BR><BR><BR>");
					out.println ("<CENTER>");
					out.println ("<FONT COLOR=\"red\">");
					out.println ("<STRONG>");
					if (errorMessage == null)
						out.println (fatalErrorNotification(fullTID, taskBase.getAttribute("E",errorName,"MESSAGE","EN")));
					else
						out.println (errorMessage);
					out.println ("</STRONG>");
					out.println ("</FONT>");
					if (langDefined) {
						language = "EN";
						out.println ("<FORM ACTION=\"DisplayerServlet2\" Method=\"get\">");
						out.println ("<BR><BR>");
						out.println ("<INPUT TYPE=\"submit\" VALUE=\"  OK  \">");
						out.println ("</FORM>");
					}
					out.println ("</CENTER>");
					out.println ("</BODY>");
					out.println ("</HTML>");
				}
				else {
					doGet (req, res);
				}
			}
		}
		catch (Exception ce) {
			out.println ("FATAL ERROR: " + ce.getMessage());
		}
	}



	/**
	 * Method to get actual task display from task specific displayer and
	 * to show it to the user.
	 *
	 * @param   req	HTTPServletRequest
	 * @param	res HTTPServletResponse
	 * @throws	ServletException 	If there are problems with servlet operations.
	 * @throws 	IOException			If there are problems with output operations to the user's web browser.
	 */


	public void doGet (HttpServletRequest req, HttpServletResponse res)
						   throws ServletException, IOException {

		String   tasktype     = null;
		String   hidden       = null;
		String   page         = null;
		String   taskbody     = null;
		String[] defaults     = null;
		StringBuffer output   = new StringBuffer();

		res.setContentType ("text/html");
		out = res.getOutputStream();

		try {

			// Instantiate correct displayer:
			DisplayerInterface disp = tsk.getDisplayer(language);
			Tasktype tType = tsk.getTasktype();
			tasktype       = tType.getTypeName();

			// If there is no displayer for the task type, generate
			// an error message:
			if (disp == null) {
				errorName = "NODISPLAYER";
				out.println (fatalErrorNotification(fullTID, taskBase.getAttribute("E",errorName,"MESSAGE",language)));
			}
			else {
				// now everything is ready to build the page
				// connect the Displayer to the cache
				disp.registerCache(taskBase);
				disp.init(taskid, language, null);

				// Provide the analyser servlet with all the necessary data in hidden
				// fields in addition to the actual answer, which is provided via a table:
				hidden = "<input type=\"hidden\" name=\"sid\"      value=\"" + userid   + "\">" +
						 "<input type=\"hidden\" name=\"cid\"      value=\"" + courseid + "\">" +
						 "<input type=\"hidden\" name=\"mid\"      value=\"" + moduleid + "\">" +
						 "<input type=\"hidden\" name=\"tno\"      value=\"" + seqNo    + "\">" +
						 "<input type=\"hidden\" name=\"taskid\"   value=\"" + taskid   + "\">" +
						 "<input type=\"hidden\" name=\"lang\"     value=\"" + language + "\">"+
						"<input type=\"hidden\" name=\"fullPath\"     value=\"" + new String(req.getContextPath()+ "/eAssari/displayers") + "\">";
						
				// Ordering task page must be generated in one go, because JavaScript code
				// is added to the header section. Other task types can be generated in a
				// modular way from common and type specific parts:
				if (tasktype.equals("orderingtask"))
					page = disp.getSetting (null, null, hidden, true);
				else {
					//output.append(pageHeader(fullTID, disp.getScript(), tsk.getStyle()));
					//output.append(pageHeader(tasktype.substring(0, tasktype.length()-4), disp.getScript(), css));
					output.append(pageHeader(fullTID, disp.getScript(), css));
					output.append(bodyBegin());
					taskbody = disp.getSetting(null, null, hidden, true);
					output.append(taskbody);
					output.append(bodyEnd());
					output.append(footer());
					page = output.toString();
				}
				out.println (page);
			}
		}
		catch (Exception ce) {
			out.println ("FATAL ERROR: " + ce.getMessage());
		}
	}
}

