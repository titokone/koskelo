/*
 * Created on 12.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.showtask.trainer;

import junit.framework.TestCase;

/**
 * A Class for testing option analyser without database
 * @author Mikko Lukkari
 */
public class OptionAnalyserUnitTest extends TestCase {

 	OptionAnalyser anal = new OptionAnalyser();
	Feedback fb = new Feedback();
	//Instantiate a TaskBase object:
	final String dbDriver   = "oracle.jdbc.OracleDriver";
	final String dbServer   = "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
	final String dbUser     = "assari";     	
	final String dbPassword = "opetus"; 		
	AnalyserTestCache cache = new AnalyserTestCache();
	String param = null;

	/**
	 * Constructor for OptionAnalyserTest.
	 * @param arg0
	 */
	public OptionAnalyserUnitTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception{
		
	}
	
	final public void testOptionAnalyser() {
		
	}

	final public void testInit() {
		
	}

	///////////////////////////////////////////////////////////////
	//                     Correct cases						 //
	///////////////////////////////////////////////////////////////
	final public void testAnalyseOneCorrect() {
		String[]answer = {"nolla","Y","N"};
		anal.init("TEST01", "EN", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==100);		
	}
	
	final public void testAnalyseManyCorrect() {
		String[]answer = {"nolla","Y","N","Y","N"};
			anal.init("TEST02", "FI", null);

			try{
			  anal.registerCache(cache);
			  fb = anal.analyse(answer,param);  
			}
			catch(CacheException e){}
			assertTrue(fb.getEvaluation()==100);		
	}
	
	final public void testAnalyseNoCorrectNoChosen() {
		String[]answer = {"nolla","n","n","n"};
		anal.init("TEST08", "EN", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==100);		
	}	

	final public void testAnalyseAllCorrect() {
		String[]answer = {"nolla","Y","Y","Y"};
		anal.init("TEST07", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==100);		
	}
	
	final public void testAnalyseHalfCorrect() {
		String[]answer = {"nolla","Y","N","Y","N"};
		anal.init("TEST02", "EN", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==100);		
	}

	///////////////////////////////////////////////////////////////
	//                     Extra options chosen 				 //
	///////////////////////////////////////////////////////////////

	final public void testAnalyseOneRightOneWrong() {
		String[]answer = {"nolla","Y","Y"};
		anal.init("TEST01", "EN", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==50);		
	}

	final public void testAnalyseOneRightAllChosen() {
		String[]answer = {"nolla","Y","Y"};
		anal.init("TEST01", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==50);		
	}
	
	final public void testAnalyseAllRightChosenOneWrongChosen() {
		String[]answer = {"nolla","Y","N","Y","Y"};
			anal.init("TEST02", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==75);		
	}
	
	final public void testAnalyseManyRightManyWrongAllChosen() {
		String[]answer = {"nolla","Y","Y","Y","Y"};
			anal.init("TEST02", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==50);		
	}

	final public void testAnalyseHalfRightOneWrongChosen() {
		String[]answer = {"nolla","Y","Y","Y","N"};
		anal.init("TEST02", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==75);		
	}
	
	final public void testAnalyseHalfRightAllChosen() {
		String[]answer = {"nolla","Y","Y","Y","Y"};
		anal.init("TEST02", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==50);		
	}

	///////////////////////////////////////////////////////////////
	//                     Wrong options chosen 				 //
	///////////////////////////////////////////////////////////////

	final public void testAnalyseOneRightWrongChosen() {
		String[]answer = {"nolla","n","y"};
		anal.init("TEST01", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==0);		
	}

	final public void testAnalyseOneRightAllWrongChosen() {
		String[]answer = {"nolla","N","y"};
		anal.init("TEST01", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==0);		
	}

	final public void testAnalyseManyRightOneWrongChosen() {
		String[]answer = {"nolla","N","N","n","y"};
		anal.init("TEST02", "ALL", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==0);		
	}

	final public void testAnalyseNoRightAllChosen() {
		String[]answer = {"1","1","1","1","1","1"};
		anal.init("TEST02", "ALL", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==0);		
	}
	final public void testAnalyseHalfRightAllWrongChosen() {
		String[]answer = {"nolla","N","y","n","Y"};
		anal.init("TEST02", "ALL", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==0);		
	}

	///////////////////////////////////////////////////////////////
	//                  Right options not chosen 			     //
	///////////////////////////////////////////////////////////////

	final public void testAnalyseOneRightNoChosen() {
		String[]answer = {"nolla","n","n"};
		anal.init("TEST01", "FI", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==50);		
	}

	final public void testAnalyseAllRightNoChosen() {
		String[]answer = {"0","0","0","0","0","0"};
		anal.init("TEST02", "ALL", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==0);		
	}
	
	final public void testAnalyseHalfRightNoChosen() {
		String[]answer = {"nolla","n","n","n","N"};
		anal.init("TEST02", "EN", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==50);		
	}

	final public void testAnalyseAllRightOneRightMissing() {
		String[]answer = {"nolla","Y","Y","n"};
		anal.init("TEST07", "EN", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==67);		
	}

	final public void testAnalyseManyRightOneRightMissing() {
		String[]answer = {"nolla","N","N","Y","N"};
		anal.init("TEST02", "EN", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==75);		
	}

	final public void testAnalyseAllRightOneRightChosen() {
		String[]answer = {"nolla","Y","n","n"};
		anal.init("TEST07", "EN", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==33);		
	}

	final public void testAnalyseManyRightOneRightChosen() {
		String[]answer = {"nolla","Y","N","N","N"};
		anal.init("TEST02", "EN", null);

		try{
		  anal.registerCache(cache);
		  fb = anal.analyse(answer,param);  
		}
		catch(CacheException e){}
		assertTrue(fb.getEvaluation()==75);		
	}

	final public void testRegisterCache() {
	}

}