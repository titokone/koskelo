/*
 * Created on 12.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.showtask.trainer;

import junit.framework.TestCase;

/**
 * A Class for testing order analyser with database
 * @author Mikko Lukkari
 */
public class OrderAnalyserTest extends TestCase {

	OrderAnalyser anal = new OrderAnalyser();
	Feedback fb = new Feedback();
	//Instantiate a TaskBase object:
	final String dbDriver   = "oracle.jdbc.OracleDriver";
	final String dbServer   = "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
	final String dbUser     = "assari";     	
	final String dbPassword = "opetus"; 		
	AttributeCache cache = new TaskBase(dbDriver, dbServer, dbUser, dbPassword);
	String param = null;

	/**
	 * Constructor for OrderAnalyserTest.
	 * @param arg0
	 */
	public OrderAnalyserTest(String arg0) {
		super(arg0);
	}

	final public void testOrderAnalyser() {		
	}

	final public void testInit() {
		anal.init("TEST06", "FI", null);
		assertTrue(anal.getTaskID()=="TEST06");
		assertTrue(anal.getLanguage()=="FI");
		assertTrue(anal.getMyName()=="OrderAnalyser");		
	}

	///////////////////////////////////////////////////////////////
	//                     Right order  						 //
	///////////////////////////////////////////////////////////////

	final public void testAnalyseRightOrder() {
		String[]answer = {"nolla","1","2","3","4","5"};
		anal.init("TEST06", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==100);
	}

	///////////////////////////////////////////////////////////////
	//                     Wrong order  						 //
	///////////////////////////////////////////////////////////////

	final public void testAnalyseReverseOrder() {
		String[]answer = {"nolla","5","4","3","2","1"};
		anal.init("TEST06", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==20);
	}

	final public void testAnalyseFirstLast() {
		String[]answer = {"nolla","2","3","4","5","1"};
		anal.init("TEST06", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==0);
	}
	
	final public void testAnalyseLastFirst() {
		String[]answer = {"nolla","5","1","2","3","4"};
		anal.init("TEST06", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==0);
	}

	final public void testRegisterCache() {
	}

}
