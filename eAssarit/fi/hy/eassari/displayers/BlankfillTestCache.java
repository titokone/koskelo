/*
 * Created on 13.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.displayers;

import java.sql.SQLException;

import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CacheException;

/**
 * A test class for testing blankfilldisplayer with attributecache interface.
 * 
 * @author tjvander
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BlankfillTestCache implements AttributeCache {

	public String getAttribute (String objType, String objID, String attributename, String language) throws CacheException{
	
		if(language.equals("EN")){
	
		if(attributename.equals("text"))
			return "T[[h]]is t[[e]]x[[t]] [[has]] lots [[]] of [[bl]]nks.";
		if(attributename.equals("helpline"))
			return "Helpline here";
		if(attributename.equals("task"))
			return "Task here";
		if(attributename.equals("submitbutton"))
			return "submitbutton";
		return "Cache attribute here";
	}
	
		if(language.equals("FI")){
	
			if(attributename.equals("text"))
				return "Täs[[s]]ä teks[[t]]is[[s]]ä [[on]] aukkoja.";
			if(attributename.equals("helpline"))
				return "Helppi tähän";
			if(attributename.equals("task"))
				return "Tehtävänanto tähän";
			if(attributename.equals("submitbutton"))
				return "lähetysnappi";
			return "Cache attribuutti tähän";
		}
		
		return "Unknown language";
	}
	
	public int attribCount(String objectid, String attributename, String language) throws CacheException, SQLException{
	
		return 0;
	}
	
	
	public void saveAnswer(String userid, String courseid, String moduleid,
						   int seqno, int trynumber, int correctness, String whenanswered, 
						   String answer, String language) 
						   throws CacheException {
   		
	}
	
	public String taskType (String taskid) throws CacheException {
		
		return null;
	}
	
	public String getTaskID (String courseid, String moduleid, int seqno) throws CacheException {
	
		return null;									
	}
	
	public boolean languageDefined (String taskid, String language) throws CacheException {
		
		return true;
	}
}
