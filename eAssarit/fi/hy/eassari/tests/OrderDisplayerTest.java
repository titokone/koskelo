package fi.hy.eassari.tests;

import java.sql.*;
import junit.framework.TestCase;
import fi.hy.eassari.displayers.OrderDisplayer;
import fi.hy.eassari.showtask.trainer.TaskBase;
import fi.hy.eassari.showtask.trainer.CacheException;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.DisplayerInterface;


/**
 * Class to test that order tasks are generated correctly.
 *  
 * @author  Olli-Pekka Ruuskanen
 * @version 7.5.2004
 */

public class OrderDisplayerTest extends TestCase {

		
	final String dbDriver       = "oracle.jdbc.OracleDriver";
	final String dbServer       = "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
	final String dbUser         = "assari";     	
	final String dbPassword     = "opetus"; 
	String             taskbody = "";
	StringBuffer       out      = new StringBuffer();
	DisplayerInterface disp     = new OrderDisplayer();
	AttributeCache     cache    = new TaskBase(dbDriver, dbServer, dbUser, dbPassword);
		
		
		
		
	public OrderDisplayerTest(String arg0) {
		super(arg0);
	}		
		
	
	
		
	/*
	 * Method to test that the displayer displays a correct set of objetcs for a task.
	 */

	public void testGetSetting() throws CacheException, SQLException {
		
		String expected = null;
		String returned = null;
		
		disp.init("TEST04", "EN", null);
		disp.registerCache(cache);
		
		// Check that correct number of options are returned:
		int attrCount = cache.attribCount ("TEST04", "object", "EN");
		assertTrue (attrCount == 3);
		
		// Check that correct objects (= option texts) are returned:
		for (int index = 1; index <= attrCount; index++) {
			switch (index) {
				case 1: expected = "later";
						break;
				case 2: expected = "see";
						break;
				case 3: expected = "you";
						break;
			}
			returned = cache.getAttribute ("T", "TEST04", "object" + index, "EN");
			assertTrue (expected.equals(returned));		
		}
	} 
}

