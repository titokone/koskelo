package fi.hy.eassari.displayers;


import java.sql.SQLException;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CacheException;

/**
 * "Local" cache to test task displayer servlet's and displayer components'
 * operation in an environment outside TKTL's systems.
 * 
 * @author  Olli-Pekka Ruuskanen (by complementing Teemu Anderson's earlier implementation)
 * @version 15.4.2004
 */


public class DisplayerTestCache implements AttributeCache {
	
	String dbDriver   = "oracle.jdbc.OracleDriver";
	String dbServer   = "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
	String dbLogin    = "assari";
	String dbPassword = "opetus";

	public String getAttribute (String objType, String objID, String attributename, String language) 
								throws CacheException {
	
		// Option task:
		if (objID.equals("TEST01")) {
			if(language.equals("EN")){
				if(attributename.equals("task"))
					return "Which one is edible?";
				if(attributename.equals("helpline"))
					return "Select correct choises for the alternatives below.";
				if(attributename.equals("option1"))
					return "BANANA";
				if(attributename.equals("option2"))
					return "ROCK";					
				if(attributename.equals("isselected1"))
					return "Y";		
				if(attributename.equals("isselected2"))
					return "N";		
				if(attributename.equals("defaultvalue1"))
					return "Y";
				if(attributename.equals("defaultvalue2"))
					return "N";
				if(attributename.equals("submitbutton"))
					return "Submit";
				//return "Cache attribute here";
			}
			if(language.equals("FI")){
				if(attributename.equals("task"))
					return "Kumpi on syötävä?";
				if(attributename.equals("helpline"))
					return "Valitse oikeat vaihtoehdot alla olevista vaihtoehdoista.";
				if(attributename.equals("option1"))
					return "BANAANI";
				if(attributename.equals("option2"))
					return "KIVI";				
				if(attributename.equals("isselected1"))
					return "Y";		
				if(attributename.equals("isselected2"))
					return "N";			
				if(attributename.equals("defaultvalue1"))
					return "N";		
				if(attributename.equals("defaultvalue2"))
					return "Y";																																		
				if(attributename.equals("submitbutton"))
					return "Vastaa";
				//return "Cache attribuutti tähän";
			}
			return "TEST01: Unknown language: " + language;
		}
		
		else
		// Multiple choice task:
		if (objID.equals("TEST02")) {
			if(language.equals("EN")){
				if(attributename.equals("task"))
					return "Which are edible?";
				if(attributename.equals("helpline"))
					return "Select correct choises for the alternatives below.";
				if(attributename.equals("option1"))
					return "BANANA";
				if(attributename.equals("option2"))
					return "ROCK";			
				if(attributename.equals("option3"))
					return "ANANAS";		
				if(attributename.equals("option4"))
					return "RADIO";	
				if(attributename.equals("isselected1"))
					return "Y";		
				if(attributename.equals("isselected2"))
					return "N";																	
				if(attributename.equals("isselected3"))
					return "Y";		
				if(attributename.equals("isselected4"))
					return "N";	
				if(attributename.equals("defaultvalue1"))
					return "Y";		
				if(attributename.equals("defaultvalue2"))
					return "N";	
				if(attributename.equals("defaultvalue3"))
					return "N";		
				if(attributename.equals("defaultvalue4"))
					return "Y";																										
				if(attributename.equals("submitbutton"))
					return "Submit";
				//return "Cache attribute here";
			}
			if(language.equals("FI")){
				if(attributename.equals("task"))
					return "Mitkä ovat syötäviä?";
				if(attributename.equals("helpline"))
					return "Valitse oikeat vaihtoehdot alla olevista vaihtoehdoista.";
				if(attributename.equals("option1"))
					return "BANAANI";
				if(attributename.equals("option2"))
					return "KIVI";
				if(attributename.equals("option3"))
					return "ANANAS";					
				if(attributename.equals("option4"))
					return "RADIO";		
				if(attributename.equals("isselected1"))
					return "Y";		
				if(attributename.equals("isselected2"))
					return "N";																	
				if(attributename.equals("isselected3"))
					return "Y";		
				if(attributename.equals("isselected4"))
					return "N";
				if(attributename.equals("defaultvalue1"))
					return "N";		
				if(attributename.equals("defaultvalue2"))
					return "Y";	
				if(attributename.equals("defaultvalue3"))
					return "Y";		
				if(attributename.equals("defaultvalue4"))
					return "N";																												
				if(attributename.equals("submitbutton"))
					return "Vastaa";
				//return "Cache attribuutti tähän";
			}
			return "TEST02: Unknown language: " + language;
		}	
		
		else
		// Blankfill task:
		if (objID.equals("TEST03")) {
			if(language.equals("EN")){
				if(attributename.equals("text"))
					return "T[[h]]is t[[e]]x[[t]] [[has]] lots [[]] of [[bl]]nks.";
				if(attributename.equals("helpline"))
					return "Helpline here";
				if(attributename.equals("task"))
					return "Task here";
				if(attributename.equals("submitbutton"))
					return "Submit";
				//return "Cache attribute here";
			}
			if(language.equals("FI")){
				if(attributename.equals("text"))
					return "Täs[[s]]ä teks[[t]]is[[s]]ä [[on]] aukkoja.";
				if(attributename.equals("helpline"))
					return "Helppi tähän";
				if(attributename.equals("task"))
					return "Tehtävänanto tähän";
				if(attributename.equals("submitbutton"))
					return "Vastaa";
				//return "Cache attribuutti tähän";
			}		
			return "TEST03: Unknown language: " + language;
		}	
		
		// Ordering task:
		if (objID.equals("TEST04")) {
			if(language.equals("EN")){
				if(attributename.equals("task"))
					return "Put the the objects into the right order.";
				if(attributename.equals("helpline"))
					return "Put the the objects into the right order.";
				if(attributename.equals("object1"))
					return "later";
				if(attributename.equals("object2"))
					return "see";			
				if(attributename.equals("object3"))
					return "you";		
				if(attributename.equals("submitbutton"))
					return "Submit";
				//return "Cache attribute here";
			}
			if(language.equals("FI")){
				if(attributename.equals("task"))
					return "Mitkä ovat syötäviä?";
				if(attributename.equals("helpline"))
					return "Valitse oikeat vaihtoehdot alla olevista vaihtoehdoista.";
				if(attributename.equals("object1"))
					return "myöhemmin";
				if(attributename.equals("object2"))
					return "nähdään";			
				if(attributename.equals("submitbutton"))
					return "Vastaa";
				//return "Cache attribuutti tähän";
			}
			return "TEST04: Unknown language: " + language;
		}	
		if (objID.equals("CommonDisplayer")) {
			if(language.equals("EN")){
				if(attributename.equals("submitbutton")) {
					return "Submit";
				}
				else
					return "";
			}
			if(language.equals("FI")){
				if(attributename.equals("submitbutton")) {
					return "Vastaa";
				}
				else
					return "";
			}
			else
				return "Unknown language: " + language;
		}
		else
		return "Unknown task: " + objID;	
	}
	
	
	
	public int attribCount(String objectid, String attributename, String language) 
			               throws CacheException, SQLException{
	
		if (objectid.equals("TEST01")) {
			if (language.equals("EN"))
				return 2;
			if (language.equals("FI"))
				return 2;
			else
				return 0;
		}
		else
		if (objectid.equals("TEST02")) {
			if (language.equals("EN"))
				return 4;
			if (language.equals("FI"))
				return 4;
			else
				return 0;
		}
		else
		if (objectid.equals("TEST04")) {
			if (language.equals("EN"))
				return 3;
			if (language.equals("FI"))
				return 2;
			else
				return 0;
		}
		else
			return 0;
	}
	
	
	public void saveAnswer(String userid, String courseid, String moduleid,
						   int seqno, int trynumber, int correctness, String whenanswered, 
						   String answer, String language) 
						   throws CacheException {
	}
	

	public String taskType (String taskid) throws CacheException {
		
		if (taskid.equals("TEST01"))
			return "multiplechoicetask";
		if (taskid.equals("TEST02"))
			return "optiontask";
		if (taskid.equals("TEST03"))
			return "blankfilltask";
		if (taskid.equals("TEST04"))
			return "orderingtask";
		return null;
	}
	
	public String getTaskID (String courseid, String moduleid, int seqno) throws CacheException {
	
		return null;									
	}
	
	public boolean languageDefined (String taskid, String language) throws CacheException {
		
		return true;
	}
}
