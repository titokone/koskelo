package fi.hy.eassari.displayers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import fi.hy.eassari.showtask.trainer.AttributeCache;



/**
 * Servlet which accepts task parameters from HTTP pages and activates
 * corresponding task displayer component to generate required task
 * presentation for the user.
 * 
 * @author  Olli-Pekka Ruuskanen
 * @version 15.4.2004
 */

public class ParamTestServlet extends HttpServlet {


	protected static AttributeCache cache = null;
	
	public void init (ServletConfig config) throws ServletException {
		
		super.init(config);
	}


	public void doGet (HttpServletRequest req, HttpServletResponse res)
					   throws ServletException, IOException {

		doPost (req, res);
	}



	public void doPost (HttpServletRequest req, HttpServletResponse res)
						throws ServletException, IOException {

		StringBuffer output = new StringBuffer();
		
		res.setContentType ("text/html");
		ServletOutputStream out;
		out = res.getOutputStream();
	
		output.append ("<html>");
		output.append ("<head>");
		output.append ("</head>");
		output.append ("<body>");
		out.println   ("PARAMETERS GIVEN IN THE PREVIOUS TASK ARE:<BR><BR>");
		output.append ("<table border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");
		output.append ("<tr><td> TASKID: </td><td>");
		output.append (req.getParameter("taskid"));
		output.append ("</td></tr>");
		output.append ("<tr><td> LANGUAGE: </td><td>");
		output.append (req.getParameter("language"));
		output.append ("</td></tr>");
		output.append ("<tr><td> STUDENT: </td><td>");
		output.append (req.getParameter("studentid"));
		output.append ("</td></tr>");
		if (req.getParameter("form1.name").equals("blankfill")) {
			output.append("<tr><td> BLANKFILL </td></tr>");
		}
		else	
		if (req.getParameter("form1.name").equals("option")) {
			output.append("<tr><td> OPTION </td></tr>");
		}
		else
		if (req.getParameter("form1.name").equals("order")) {
			output.append("<tr><td> ORDER </td></tr>");
		}
		else ;
		output.append ("</table>");
		output.append ("</body>");
		output.append ("</html>");	
		out.println(output.toString());	
	 }
}
	
