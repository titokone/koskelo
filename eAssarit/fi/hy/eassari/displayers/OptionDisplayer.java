package fi.hy.eassari.displayers;



import java.sql.*;
import fi.hy.eassari.showtask.trainer.CacheException;

/**
 * Class to generate option task presentations.
 *
 * @author  Olli-Pekka Ruuskanen
 * @version 27.4.2004
 */

public class OptionDisplayer extends CommonDisplayer {


	/**
	 * Generates the actual representation of the task in HTML.
	 *
	 * @param  initVal 	contains the default answer.
	 * @param  params  	contains the task specific generated parametervalues.
	 * 				    The format of the string is Displayer specific but xml is recommended.
	 * @param  hiddens 	contains hidden http parameters to be included in the form.
	 * @param  allowTry specifies whether answerbutton should be included or not.
	 * @return String
	 * @throws CacheException If there are problems using the system cache.
	 */

	public String getSetting(String [] initVal, String params, String hiddens,  boolean allowTry)
							throws CacheException {


		String       style         = null;
		String       attrvalue     = null;
		String       buttontext    = null;
		String 		 optionRow     = null;
		String       targetServlet = "Answer2.do2";
		StringBuffer setting       = new StringBuffer();

		//"<form action=\"http://www.helsinki.fi/cgi-bin/dump-all\" method=\"post\" name=\"option\" id=\"option\">" +

		setting.append (

		"<form action=" + targetServlet + " method=\"post\" name=\"option\" id=\"option\">" +
			"<p class=\"assignment\"><strong>");


		setting.append (

				cache.getAttribute("T", taskid, "task", language));

		setting.append (

				"</p>" +

				"<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"helpnote\">"+
					"<tr>"+
						"<td><p>");

		setting.append (

							cache.getAttribute("D", "OptionDisplayer", "helpline", language));

		setting.append (

						"</p></td>" +
					"</tr>" +
				"</table>" +

				"<br />" +

				"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");

		int counter   = 0;
		int attrcount = 0;
		try {
			// First find out, how many answer option there are...
			attrcount = cache.attribCount (taskid, "option", language);
			hiddens=hiddens+("<input type=\"hidden\" name=\"optioncount\" value= " + attrcount + ">");


			// Answer array's first value is at index position 1 whereas defaultvalue
			// array's first value is in position 0:
			int initIndex = 0;
//			if (initVal[0] == null)
				initIndex = 1;

			if (correctOptions (taskid, language) == 1) 
				hiddens=hiddens+("<input type=\"hidden\" name=\"tasktype\" value=\"moptiontask\">");
			else
				hiddens=hiddens+("<input type=\"hidden\" name=\"tasktype\" value= \"optiontask\">");
	
			int correct=correctOptions (taskid, language);

			// ...Then append them one by one to the StringBuffer:
			for (int index = 1; index <= attrcount; index++) {
				if (correct == 1) 
					optionRow = "<tr><td align=\"right\"> <input name=\"option\" type=\"radio\" value=\"" + index + "\"";
				else
					optionRow = "<tr><td align=\"right\"> <input name=\"option" + index + "\" type=\"checkbox\" value=\"Y\"";


				// Get option text:
				attrvalue = cache.getAttribute ("T", taskid, "option" + index, language);
				setting.append (optionRow);

				// Show 1st. option preselected before answering and show the options which 
				// the studend chose - which are indicated in initVal array - after answering:
				if (initVal == null)
					if (index == 1)
						setting.append (
							"checked=\"checked\" />");
					else
						setting.append (" />");
				else
				if (initVal[index].equals("Y"))
					setting.append (
						"checked=\"checked\" />");
				else
					setting.append (" />");

				setting.append (
							"</td>");
				setting.append (
							"<td>" + attrvalue +"</td>" +
							"<td>&nbsp;</td>" +
						"</tr>");
			}

		}
		catch (Exception ex) {;}

		if(allowTry){
			setting.append ("<tr><td></td><td>");
			setting.append (super.getButton());
			setting.append ("</td></tr>");
		}
		setting.append ("</table>");
		setting.append (hiddens);
		setting.append ("</form>");

	return new String(setting);
   }




   /*
	* Private method to get number of correct answers to the task.
	* 
	* @param  taskid   task identification.
	* @param  language language in which the task should be displayed.
	* @return int
	* @throws CacheException If there are problems using the system cache.
	* @throws SQLException   If there are problems using the system cache. 
	*/

   private int correctOptions (String taskid, String language)
							   throws CacheException, SQLException {

		int retValue = 0;

		// Get number of options:
		int count = cache.attribCount(taskid, "isselected", language);

		for (int index = 1; index <= count; index++) {
			// Get options' correctness values ('N' = not selected; 'Y' = selected):
			if (cache.getAttribute ("T", taskid, "isselected"+index, language).equals("Y"))
				retValue++;
		}
		return retValue;
	}
}
