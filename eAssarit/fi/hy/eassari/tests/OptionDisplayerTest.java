package fi.hy.eassari.tests;

import java.sql.*;
import junit.framework.TestCase;
import fi.hy.eassari.displayers.OptionDisplayer;
import fi.hy.eassari.showtask.trainer.TaskBase;
import fi.hy.eassari.showtask.trainer.CacheException;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.DisplayerInterface;


/**
 * Class to test that option tasks are generated correctly.
 * 
 * @author  Olli-Pekka Ruuskanen
 * @version 7.5.2004
 */

public class OptionDisplayerTest extends TestCase  {

	final String dbDriver        = "oracle.jdbc.OracleDriver";
	final String dbServer        = "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
	final String dbUser          = "assari";     	
	final String dbPassword      = "opetus";
	String 			   hidden    = null;
	String             expected  = null;
	String             returned  = null; 
	DisplayerInterface disp      = new OptionDisplayer();
	AttributeCache     cache     = new TaskBase(dbDriver, dbServer, dbUser, dbPassword);
	
	
	
	
	public OptionDisplayerTest(String arg0) {
		super(arg0);
	}
	
	
	
	
	/*
	 * Method to test that the correct options' number is identified correctly.  
	 */
	 
	public void testCorrectOptions() throws CacheException, SQLException {
		
		// No correct answer options at all:
		assertTrue (correctOptions("TEST00", "EN") == 0);
		
		// Only one option is correct:
		assertTrue (correctOptions("TEST01", "EN") == 1);
		
		// More then one option is correct:
		assertTrue (correctOptions("TEST02", "EN") == 2);
	}
	
	
	
	
	/*
	 * Method to test that the displayer displays a correct set of options for a task.
	 */

	public void testGetSetting() throws CacheException, SQLException {
		
		int    attrCount = 0;
		String expected  = null;
		String returned  = null;
		
		disp.init ("TEST01", "EN", null);
		disp.registerCache(cache);
		
		// Check that correct number of options are returned:
		attrCount = cache.attribCount ("TEST01", "option", "EN");
		assertTrue (attrCount == 2);
		
		// Check that correct option values (= option texts) are returned:
		for (int index = 1; index <= attrCount; index++) {
			if (index ==1)
				expected = "BANANA";
			else
				expected = "ROCK";
			returned = cache.getAttribute ("T", "TEST01", "option" + index, "EN");
			assertTrue (expected.equals(returned));		
		}
	}
	
	
	

	/*
	 * Private method copied from OptionDisplayer to test its' functionality.
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
	
