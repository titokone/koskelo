package fi.hy.eassari.showtask.trainer;
/**
 * Trainer servlet superclass
 */

import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Creates a TaskBase object to be used as the cache for all task related information
 *
 */

public class TrainerServlet extends HttpServlet {


	protected static TaskBase taskBase=null;

	public void init (ServletConfig config) throws ServletException  {
	   super.init(config);
	   if (taskBase == null) {
		 // Only created by first servlet to call
		 String conFile = config.getServletContext().getInitParameter("confile");
		 conFile = config.getServletContext().getRealPath(conFile);
		 	 
		 try {
			Properties p = new Properties();
			p.load(new FileInputStream(conFile));
			String dbDriver   = (String) p.get("dbDriver");
			String dbServer   = (String) p.get("dbServer");
			String dbUser     = (String) p.get("dbUser");
			String dbPassword = (String) p.get("dbPassword");
						
			taskBase = new TaskBase(dbDriver,dbServer,dbUser,dbPassword);
		 }
		 catch (Exception e) {
			 throw new ServletException("Problems with configuration file " + conFile + ": " + e.getMessage());
		 }
	   }
	}


	protected String pageHeader(String titleAttachment, String jscript, String css) {

		StringBuffer header= new StringBuffer();

		header.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0Transitional//EN\"> \n");
		header.append("<html><head>\n");
		header.append("<title>eAssari (1.0) ");
		if (titleAttachment!=null)
			header.append(titleAttachment);
		header.append("</title>\n");
		if (css!=null) {
		   header.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
		   header.append(css);
		   header.append("\">");
		}
		if (jscript!=null) {
		   header.append("<script language=\"JavaScript\">\n");
		   header.append("<!--  hide");
		   header.append(jscript);
		   header.append("// -->\n");
		   header.append("</script>");
		}

		header.append("</head>");
		return header.toString();



	}

	//modifed by Teemu Andersson
	protected String bodyBegin() {
	   //return "<body>\n";
	   return "<body class=\"body\">\n";
	}

	protected String bodyEnd() {
		return "</body>";
	}

	protected String footer() {
		return "</html>";
	}

	protected String errorBox(String msg) {
		StringBuffer sb= new StringBuffer();
		sb.append("<table>");
		sb.append("<tr><th>eAssari error</th></tr>");
		sb.append("<tr><td>");
		sb.append(msg);
		sb.append("</td></table><p></p>");
		return sb.toString();
	}

	protected String fatalErrorNotification(String taskInfo, String msg) {
		StringBuffer page= new StringBuffer();
		page.append(pageHeader("ERROR",null,"../styles/errorpage.css"));
		page.append(bodyBegin());
		page.append(errorBox(msg+"<br>"+taskInfo));
		page.append(bodyEnd());
		page.append(footer());
		return page.toString();
	}
}

