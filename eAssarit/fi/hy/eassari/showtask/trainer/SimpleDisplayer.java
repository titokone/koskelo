package fi.hy.eassari.showtask.trainer;

public class SimpleDisplayer implements DisplayerInterface {

	final static String DCODE="D";
	final static String TCODE="T";
    
    
	AttributeCache cache;
	String myName;
	String taskID;
	String language;
	ParameterString initP;
     
    
	public SimpleDisplayer() {
	   cache=null;
	   myName="SimpleDisplayer";
	   taskID=null;
	   language="EN";
	}
 
	public void init (String taskid, String lang, String initparams) {
		taskID= taskid;
		language= lang;
		initP= new ParameterString(initparams);
	};
    
	/***
	 * loads the URL of the stylesheet attached to the displayer
	 */
	public String getStyle () throws CacheException {
	   String style= cache.getAttribute(DCODE,myName,"STYLESHEET",language);
	   return style;
	}
    
	/****
	 * generates the tasktype specific javascript for processing the task form
	 */
	public String getScript ()  throws CacheException {
	   String script= cache.getAttribute(DCODE, myName, "JAVASCRIPT", language);
	   return script;
	}

	 /****
	 * generates the body of the form bt simply loading it from the database
	 */
	public String getSetting(String [] initVal, String params, String hiddens, boolean allowTry) throws CacheException {
	   StringBuffer st = new StringBuffer();
	   st.append("<form name=\"answerform\" action=\"Answer\">\n");
	   st.append("<table class=\"tasktable\">");
	   st.append("<th  class=\"taskheader\" colspan=\"2\">\n");
	   st.append(taskID+": ");
	   st.append(cache.getAttribute(TCODE, taskID, "NAME", language));
	   st.append("</th></tr>\n");
	   st.append("<tr>\n<td class=\"fname\">");
	   st.append(cache.getAttribute(DCODE, myName, "TASK", language));
	   st.append("</td>\n<td class=\"setting\">");
	   st.append(cache.getAttribute(TCODE,taskID,"SETTING",language));
	   st.append("</td></tr>\n<tr><td class=\"fname\">");
	   st.append(cache.getAttribute(DCODE,myName,"ANSWER",language));
	   st.append("</td>\n<td>");
	   if (initP.nextElementByName("ANSWER")) {
		  String tp= initP.getAttributevalue("TYPE");
		  if (tp!=null && tp.equals("FIELD")) {
			  String arg1=initP.getAttributevalue("SIZE"); 
			  String arg2=initP.getAttributevalue("MAXLENGTH");
			  st.append("<input type=\"text\" name=\"answer\" size=\"");
			  if (arg1!=null) 
				 st.append(arg1);
			  else 
				 st.append("20");
			  st.append("\"");
			  if (arg2!=null) {
				 st.append("maxlength=");
				 st.append(arg2);
				 st.append("\"");
			  }                
			  st.append(" value=\"");
			  if (initVal!=null) 
				  st.append(initVal);
			  st.append("\">\n");
		  }    
		  else {
			  String arg3=initP.getAttributevalue("COLS");
			  String arg4=initP.getAttributevalue("ROWS");
			  st.append("<textarea name=\"answer\" cols=\"");
			  if (arg3!=null)
				 st.append(arg3);
			  else
				 st.append("60");
			  st.append("\" rows=\"");
			  if (arg4!=null)
				 st.append(arg4);
			  else
				 st.append("5");
			  st.append("\">");
			  if (initVal!=null) 
				  st.append(initVal[0]);
			  st.append("</textarea>\n");
		  }                    
	   }   
	   else {
		  st.append("<input type=\"text\" name=\"answer\" size=\"20\" value=\"");
		  if (initVal!=null) 
				  st.append(initVal[0]);
		  st.append("\">\n");
	   }
       
	   st.append("</td></tr>\n");
       
	   if (allowTry) {
		  st.append("<tr><td class=\"buttons\" colspan=\"2\">\n");
		  st.append("<input type=\"submit\" name=\"submit\" value=\"");
		  st.append(cache.getAttribute(DCODE,myName,"SUBMIT",language));
		  st.append("\">\n");
		  st.append("<input type=\"BUTTON\" value=\"");
		  st.append(cache.getAttribute(DCODE,myName,"RESET",language));
		  st.append("\" onClick=\"document.answerform.answer.value='';\">\n");
		  if (hiddens!= null)
			st.append(hiddens);
		  st.append("</td></tr>");
	   }
	   st.append("</table>");
	   st.append("</form>");     
	   return st.toString();
	}   
  
	 /****
	 * generates a link to technical instructions
	 */  
	public String getTechnicalHelp() throws CacheException {
		String helpURL= cache.getAttribute(DCODE,myName,"HOWTOHELP",language);
		String helpText=null;
		String helpLink=null;
		//       String helpImg=null;
		//       String helpTE
		if (helpURL!=null) {
			helpText =cache.getAttribute(DCODE,myName,"HOWTOHELPTEXT",language);
			if (helpText==null) 
			   helpText = (language.equals("FI")?"Ohje":"Help");
			helpLink="<A HREF=\""+helpURL+"\">"+helpText+"</A>";
		}    
		return helpLink;
	}
    
	/****
	 * generates a link to topic specifig help
	 */   
	public String getTopicHelp() throws CacheException {
		String helpURL= cache.getAttribute(TCODE, taskID,"TOPICHELP",language);
		String helpText=null;
		String helpLink=null;
		//       String helpImg=null;
		//       String helpTE
		if (helpURL!=null) {
			helpText =cache.getAttribute(TCODE,taskID,"TOPICHELPTEXT",language);
			if (helpText==null) 
			   helpText = (language.equals("FINNISH")?"Ohje":"Help");
			helpLink="<A HREF=\""+helpURL+"\">"+helpText+"</A>";
		}    
		return helpLink;
	} 

	public String getHelps() throws CacheException{
		return null;
	}

	public void registerCache( AttributeCache c) {
		cache=c;
	}
}

