/*
 * Created on 26.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.showtask.trainer;

import junit.framework.TestCase;

/**
 * A Class for testing order analyser without database
 * @author Mikko Lukkari
 */
public class OrderAnalyserUnitTest extends TestCase {

	OrderAnalyser anal = new OrderAnalyser();
	Feedback fb = new Feedback();
	//Instantiate a TaskBase object:
	final String dbDriver = "oracle.jdbc.OracleDriver";
	final String dbServer = "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
	final String dbUser   = "assari";
	final String dbPassword = "opetus";
	AnalyserTestCache cache = new AnalyserTestCache();
	String param = null;

	/**
	 * Constructor for OrderAnalyserUnitTest.
	 * @param arg0
	 */
	public OrderAnalyserUnitTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
	}

	final public void testOrderAnalyser() {
	}

	final public void testInit() {
	}
	///////////////////////////////////////////////////////////////
	//                     Right order							 //
	///////////////////////////////////////////////////////////////
	
	final public void testAnalyseRightOrder() {
		String[]answer = {"nolla","1","2","3","4","5"};
		anal.init("TEST06", "EN", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==100);
	}
	
	///////////////////////////////////////////////////////////////
	//                     Wrong order							 //
	///////////////////////////////////////////////////////////////

	final public void testAnalyseReverseOrder() {
		String[]answer = {"nolla","5","4","3","2","1"};
		anal.init("TEST06", "EN", null);

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