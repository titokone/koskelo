/*
 * Created on 3.5.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.displayers;

import junit.framework.TestCase;
import fi.hy.eassari.showtask.trainer.DisplayerInterface;

/**
 * A Class for testing order displayer with out database
 * @author Mikko Lukkari
 */
public class OrderDisplayerUnitTest extends TestCase {

		
	final String dbDriver       = "oracle.jdbc.OracleDriver";
	final String dbServer       = "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
	final String dbUser         = "assari";     	
	final String dbPassword     = "opetus"; 
	String             taskbody = "";
	StringBuffer       out      = new StringBuffer();
	DisplayerInterface disp     = new OrderDisplayer();
	DisplayerTestCache cache    = new DisplayerTestCache();
		
		
		
		
	public OrderDisplayerUnitTest(String arg0) {
		super(arg0);
	}		
		
	
	
		
	/*
	 * Method to test that the displayer displays a correct set of objetcs for a task.
	 */

	public void testGetSetting() throws Exception {
		
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

