package fi.helsinki.cs.koskelo.test;

import java.util.*;
import junit.framework.*;
import fi.helsinki.cs.koskelo.displayer.*;
import java.sql.SQLException;

import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CacheException;

public class StaticTTK91DisplayerTest extends TestCase {

	TestCache cache = new TestCache();
	String [] answer = {"LOAD R1, =3; LOAD R2, =2"};
	String hiddens = ("<input type=\"hidden\" name=\"test\"" 
										+"value=\"test\">");
	String testString ="";
	public void testgetSetting() {

		/*
		 * Testi ilman vastauspainiketta.
		 */

		StaticTTK91Displayer test = new StaticTTK91Displayer();
		test.registerCache(cache);
		try{
			testString = test.getSetting (answer,"",hiddens,false);
		}
		catch (Exception e){System.err.println(e);}
		Assert.assertEquals
			(testString, 
			 ("<form action=Answer2.do2" + 
				" method=\"post\" name=\"staticttk91task\"" +
				"id=\"staticttk91task\">"
				+fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.
				getHTMLElementTask("Laske yhteen 1,2 ja 3.")
				+fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.
				getHTMLElementInput("Syötteet","4, 5, 6")
				+fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.
				getHTMLElementAnswerBox(answer, 20 ,50)
				+hiddens
				+"<input type=\"hidden\" name=\"tasktype\"" 
				+"value=\"staticttk91task\">"
				+"</form>"));

		/*
		 * Testi vastauspainikkeen kanssa.
		 */

		try{
			testString = test.getSetting (answer,"",hiddens,true);
		}
		catch (Exception e){System.err.println(e);}
		
		Assert.assertEquals
			(testString, 
			 ("<form action=Answer2.do2" + 
				" method=\"post\" name=\"staticttk91task\"" +
				"id=\"staticttk91task\">"
				+fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.
				getHTMLElementTask("Laske yhteen 1,2 ja 3.")
				+fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.
				getHTMLElementInput("Syötteet","4, 5, 6")
				+fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.
				getHTMLElementAnswerBox(answer, 20 ,50)
				+hiddens
				+"<input type=\"hidden\" name=\"tasktype\"" 
				+"value=\"staticttk91task\">"
				+"<input name=\"Submit\" type=\"submit\" "
				+"class=\"button\" value=\"Lähetä\" />"
				+"</form>"));

		
	}


	private class TestCache implements AttributeCache{

		public String getAttribute (String objType, 
																String objID, 
																String attributename, 
																String language) throws CacheException{
	
			if (attributename.equals("taskDescription")) {
				return("Laske yhteen 1,2 ja 3.");
			}
			if (attributename.equals("publicInput")) {
				return("4, 5, 6");
			}
			if (attributename.equals("inputHeader")) {
				return("Syötteet");
			}  
			if (attributename.equals("submitbutton")) {
				return("Lähetä");
			}  
	
			return("no match");
	    
		}



		public int attribCount(String objectid, 
													 String attributename, 
													 String language) throws CacheException, 
																									 SQLException{
	
			return 0;
		}

		public void saveAnswer(String userid, 
													 String courseid, 
													 String moduleid,
													 int seqno, 
													 int trynumber, 
													 int correctness, 
													 String whenanswered, 
													 String answer, 
													 String language) throws CacheException {
		}
	
		public String taskType (String taskid) throws CacheException {
		
			return null;
		}

		public String getTaskID (String courseid, 
														 String moduleid, 
														 int seqno) throws CacheException {
	
			return null;							   }

		public boolean languageDefined (String taskid, 
																		String language) throws CacheException {
		
			return true;
		}
	}
}
