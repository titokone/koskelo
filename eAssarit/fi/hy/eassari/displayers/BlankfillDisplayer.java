/*
 * Created on 1.4.2004
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.displayers;

import java.util.StringTokenizer;

import fi.hy.eassari.showtask.trainer.CacheException;

/**
 * A class for creating a view for a blankfilltask.
 * @author tjvander
 */


public class BlankfillDisplayer extends CommonDisplayer{
	


	/**
	 * Generates the actual representation of the task in HTML
	 *
	 * @param  initval contains the default answer
	 * @param  params  contains the task specific generated parametervalues - the format of the string
	 *           is Displayer5 specific but xml is recommended
	 * @param  hiddens contains hidden http parameters to be includen in the form
	 * @param  allowTry specifies whether answerbutton should be included or not  
	 */
   public String getSetting(String [] initVal, String params, String hiddens,  boolean allowTry) throws CacheException{
   
   String targetServlet = "Answer2.do2";
   StringBuffer setting=new StringBuffer();
   hiddens += "<input type=\"hidden\" name=\"tasktype\" value=\"blankfilltask\">";
   
   //"<form action=\"http://www.helsinki.fi/cgi-bin/dump-all\" method=\"post\" name=\"blankfill\" id=\"blankfill\">" +
   
   setting.append(
   "<form action=" + targetServlet + " method=\"post\" name=\"blankfill\" id=\"blankfill\">" +
		"<p class=\"assignment\"><strong>");
		setting.append(cache.getAttribute("T", taskid, "task", language));
	setting.append("</strong>\n");
	setting.append("</p>"+
	 "<table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"helpnote\">\n"+
	   "<tr>\n"+
		 "<td><p>\n");
		 
	setting.append(cache.getAttribute("D","BlankfillDisplayer","helpline", language));
	
	setting.append("</p>" +
		"</td>" +
		"</tr>" +
		"</table>" +
		"<p>");
	
	//text start
	
	int counter=0;
	String text=cache.getAttribute("T", taskid, "text", language);
	StringTokenizer tokenizer=new StringTokenizer(text);
	String token;
	
	while(tokenizer.hasMoreTokens()){
		token=tokenizer.nextToken();
		if(checkIfBlank(token)){//Checks if the word is intended to be blank
			counter=insertBlank(token, setting, counter, initVal);// insert a blank instead of the word
		}
		else setting.append(token+" ");
	}
	//text end
	
	hiddens += "<input type=\"hidden\" name=\"gapcount\" value=" + counter + ">";

	setting.append("<br />" +
	"</p>" +
	"<p align=\"right\"><br />" );

	setting.append("</td>" +
		"</tr>" +
		"</table>" +
		"<p>");
	setting.append(hiddens);
	if(allowTry){
		setting.append(super.getButton());
	}
	setting.append("</p></form>" );
   
   return new String(setting);}
   
   private boolean checkIfBlank(String word){
    	
		if(word.indexOf("[[")==-1)
			return false;
		else return true;
    	
	}

	private int insertBlank(String word, StringBuffer buffer, int counter, String [] param){
		
		String operated=word;
		int index=0;
		int count=counter;
		String value="";
		while(operated.indexOf("[[")!=-1){
			index=operated.indexOf("[[");
			if(index>0){
				buffer.append(operated.substring(0,index));
			}
			if(param!=null){
				if(count<param.length)
					value=param[count+1];
				else value="";
			}
			count++;
			buffer.append("<input name=\"option"+count+"\" type=\"text\" class=\"input\" id=\"option"+count+"\" size=\"20\" value=\""+value+"\" />");
			index=operated.indexOf("]]");
			operated=operated.substring(index+2, operated.length());
		}
		
		if(operated.length()>0)
			buffer.append(operated);
		buffer.append(" ");
		return count;
	}
}
