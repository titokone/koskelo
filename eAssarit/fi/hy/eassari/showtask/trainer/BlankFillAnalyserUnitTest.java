/*
 * Created on 26.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.showtask.trainer;

import junit.framework.TestCase;

/**
 * A Class for testing blankfill analyser without database
 * @author Mikko Lukkari
 */
public class BlankFillAnalyserUnitTest extends TestCase {

	BlankFillAnalyser anal = new BlankFillAnalyser();
	Feedback fb = new Feedback();
	//Instantiate a TaskBase object:
	final String dbDriver = "oracle.jdbc.OracleDriver";
	final String dbServer = "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
	final String dbUser = "assari";
	final String dbPassword = "opetus";
	AnalyserTestCache cache = new AnalyserTestCache();
	String param = null;

	/**
	 * Constructor for BlankFillAnalyserUnitTest.
	 * @param arg0
	 */
	public BlankFillAnalyserUnitTest(String arg0) {
		super(arg0);
	}
	protected void setUp() throws Exception {
	}

	final public void testOrderAnalyser() {
	}

	final public void testInit() {
	}

	///////////////////////////////////////////////////////////////
	//                     Correct fills						 //
	///////////////////////////////////////////////////////////////
	final public void testAnalyseCorrectFills() {
		String[] answer = { "nolla", "eka", "toka", "kolmas", "nelj‰s" };
		anal.init("TEST05", "FI", null);

		try {
			anal.registerCache(cache);
			fb = anal.analyse(answer, param);
		} catch (CacheException e) {
		}
		assertTrue(fb.getEvaluation() == 100);
	}
	///////////////////////////////////////////////////////////////
	//                     Wrong fills	    					 //
	///////////////////////////////////////////////////////////////

	final public void testAnalyseEmptyFills() {
		String[] answer = { null, null, null, null, null };
		anal.init("TEST05", "FI", null);

		try {
			anal.registerCache(cache);
			fb = anal.analyse(answer, param);
		} catch (CacheException e) {
		}
		assertTrue(fb.getEvaluation() == 0);
	}

	final public void testAnalyseOneFillWrong() {
		String[] answer = { "nolla", "eka", "toka", "v‰‰r‰", "nelj‰s" };
		anal.init("TEST05", "FI", null);

		try {
			anal.registerCache(cache);
			fb = anal.analyse(answer, param);
		} catch (CacheException e) {
		}
		assertTrue(fb.getEvaluation() == 75);
	}

	final public void testAnalyseOneFillRight() {
		String[] answer = { "nolla", "v‰‰r‰", "toka", "v‰‰r‰", "v‰‰r‰" };
		anal.init("TEST05", "FI", null);

		try {
			anal.registerCache(cache);
			fb = anal.analyse(answer, param);
		} catch (CacheException e) {
		}
		assertTrue(fb.getEvaluation() == 25);
	}

	final public void testAnalyseHalfFillRight() {
		String[] answer = { "nolla", "eka", "toka", "v‰‰r‰", "v‰‰r‰" };
		anal.init("TEST05", "FI", null);

		try {
			anal.registerCache(cache);
			fb = anal.analyse(answer, param);
		} catch (CacheException e) {
		}
		assertTrue(fb.getEvaluation() == 50);
	}

	final public void testAnalyseFirstFillRight() {
		String[] answer = { "nolla", "eka", "v‰‰r‰", "v‰‰r‰", "v‰‰r‰" };
		anal.init("TEST05", "FI", null);

		try {
			anal.registerCache(cache);
			fb = anal.analyse(answer, param);
		} catch (CacheException e) {
		}
		assertTrue(fb.getEvaluation() == 25);
	}

	final public void testAnalyseLastFillRight() {
		String[] answer = { "nolla", "v‰‰r‰", "v‰‰r‰", "v‰‰r‰", "nelj‰s" };
		anal.init("TEST05", "FI", null);

		try {
			anal.registerCache(cache);
			fb = anal.analyse(answer, param);
		} catch (CacheException e) {
		}
		assertTrue(fb.getEvaluation() == 25);
	}

	final public void testAnalyseLastFillWrong() {
		String[] answer = { "nolla", "eka", "toka", "kolmas", "v‰‰r‰" };
		anal.init("TEST05", "FI", null);

		try {
			anal.registerCache(cache);
			fb = anal.analyse(answer, param);
		} catch (CacheException e) {
		}
		assertTrue(fb.getEvaluation() == 75);
	}

	final public void testAnalyseFirstFillWrong() {
		String[] answer = { "nolla", "v‰‰r‰", "toka", "kolmas", "nelj‰s" };
		anal.init("TEST05", "FI", null);

		try {
			anal.registerCache(cache);
			fb = anal.analyse(answer, param);
		} catch (CacheException e) {
		}
		assertTrue(fb.getEvaluation() == 75);
	}

	final public void testAnalyseFirstAndLastFillRight() {
		String[] answer = { "nolla", "eka", "v‰‰r‰", "v‰‰r‰", "nelj‰s" };
		anal.init("TEST05", "FI", null);

		try {
			anal.registerCache(cache);
			fb = anal.analyse(answer, param);
		} catch (CacheException e) {
		}
		assertTrue(fb.getEvaluation() == 50);
	}

	final public void testAnalyseFirstAndLastFillWrong() {
		String[] answer = { "nolla", "v‰‰r‰", "toka", "kolmas", "v‰‰r‰" };
		anal.init("TEST05", "FI", null);

		try {
			anal.registerCache(cache);
			fb = anal.analyse(answer, param);
		} catch (CacheException e) {
		}
		assertTrue(fb.getEvaluation() == 50);
	}

	final public void testRegisterCache() {
	}
}
