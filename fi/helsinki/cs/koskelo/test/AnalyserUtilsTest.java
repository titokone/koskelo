package fi.helsinki.cs.koskelo.test;

import java.util.*;
import junit.framework.*;
import fi.helsinki.cs.koskelo.analyser.*;
import fi.helsinki.cs.koskelo.common.*;
import java.sql.SQLException;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CacheException;

public class AnalyserUtilsTest extends TestCase {

	public void testGetTTK91TaskOptions() {
		TestCache cache = new TestCache();
		TTK91TaskOptions taskOptions = new TTK91TaskOptions();
		try{
		TTK91AnalyserUtils anaUtils = new TTK91AnalyserUtils(cache, "100", "FI");
		taskOptions = anaUtils.getTTK91TaskOptions();
		}catch(Exception e){fail("taskOptions");}
		
		// Int-arvoisten testit

		Assert.assertEquals(taskOptions.getCompareMethod(),1);
		Assert.assertEquals(taskOptions.getMaxCommands(),10);
		Assert.assertEquals(taskOptions.getAcceptedSize(),100);
		Assert.assertEquals(taskOptions.getOptimalSize(),50);

		// String testi

		Assert.assertEquals(taskOptions.getExampleCode(),
												"LOAD R1, =0;LOAD R2, =2;");

		// Int[] testit

		int[] test1 = taskOptions.getPublicInput();
		

		Assert.assertEquals(test1[0],1);
		Assert.assertEquals(test1[1],2);
		
	 
		int[] test2 = taskOptions.getHiddenInput();
		Assert.assertEquals(test2[0],4);
		Assert.assertEquals(test2[1],5);
		
		// String[] testit

		String[] test3 = taskOptions.getRequiredCommands();

		Assert.assertEquals(test3[0],"LOAD");
		Assert.assertEquals(test3[1],"STORE");

		String[] test4 = taskOptions.getForbiddenCommands();
			Assert.assertEquals(test4[0],"JUMP");
			Assert.assertEquals(test4[1],"NOT");

		// TTK91TaskCriteria[] testit

		TTK91TaskCriteria[] test5 = taskOptions.getScreenOutputCriterias();
		Assert.assertEquals(test5[0].toString(),"(1,100);");
		Assert.assertEquals(test5[1].toString(),"(2,200);");

		TTK91TaskCriteria[] test6 = taskOptions.getFileOutputCriterias();
		Assert.assertEquals(test6[0].toString(),"(2,200);");
		Assert.assertEquals(test6[1].toString(),"(3,300);");

		TTK91TaskCriteria[] test7 = taskOptions.getMemoryCriterias();
	 
		Assert.assertEquals(test7[0].toString(),"(1==100);");
		Assert.assertEquals(test7[1].toString(),"(2==200);");

		TTK91TaskCriteria[] test8 = taskOptions.getRegisterCriterias();
		Assert.assertEquals(test8[0].toString(),"(R1==2);");
		Assert.assertEquals(test8[1].toString(),"(R2==3);");

	  TTK91TaskCriteria test9 = taskOptions.getMemRefCriteria();
		Assert.assertEquals(test9.toString(),"(MemoryReference10<50);"); 
		
	}

	private class TestCache implements AttributeCache{

		public String getAttribute (String objType, 
																String objID, 
																String attributename, 
																String language) throws CacheException{
	
			if (attributename.equals("acceptedSizeHeader")) {
	    return("Hyväksymisen yläraja");
		}
		if (attributename.equals("requiredCommandsHeader")) {
	    return("Ohjelmassa vaaditut käskyt");
		}
		if (attributename.equals("acceptedSizeFeedbackPositive")) {
	    return("Ohjelma ei ollut liian pitkä.");
		}  
		if (attributename.equals("requiredCommandsFeedbackPositive")) {
	    return("Käytit kaikkia vaadittuja käskyjä.");
		}  
		if (attributename.equals("acceptedSizeQualityFeedback")) {
	    return("Ohjelmasi oli juuri oikean kokoinen.");
		}  
		
		if (attributename.equals("compareMethod")) {
	    return("1");
		}
		
		if (attributename.equals("maxCommands")) {
	    return("10");
		}
		if (attributename.equals("acceptedSize")) {
	    return("100");
		}
		if (attributename.equals("optimalSize")) {
	    return("50");
		}
		if (attributename.equals("exampleCode")) {
	    return("LOAD R1, =0;LOAD R2, =2;");
		}
		if (attributename.equals("publicInput")) {
	
	    return("1,2");
		}
		if (attributename.equals("hiddenInput")) {
	    return("4,5");
		}
		if (attributename.equals("requiredCommands")) {
	   
			return("LOAD;STORE;");
		}
			if (attributename.equals("forbiddenCommands")) {
				return("JUMP;NOT;");
		}
		if (attributename.equals("screenOutput")) {
	    return("(1,100);(2,200);");
		}
		if (attributename.equals("fileOutput")) {
	    return("(2,200);(3,300);");
		}
		if (attributename.equals("memoryValues")) {
			
				return("(1=100);(2=200);");
		}
		if (attributename.equals("registerValues")) {
	    return("(R1=2);(R2=3);");
		}

		if (attributename.equals("memoryReferences")) {
	    return("10<50");
		}
		if (attributename.equals("registervaluesLabel")) {
	    return("Rekisterien arvot");
		}
		if (attributename.equals("statisticsLabel")) {
	    return("Statistiikka");
		}
		if (attributename.equals("memoryReference")) {
	    return("Muistiviitteitä yhteensä");
		}	
		if (attributename.equals("stackSize")) {
	    return("Pinon suurin koko");
		}
		if (attributename.equals("codeSegment")) {
	    return("Koodisegmentin koko");
		}
		if (attributename.equals("dataSegment")) {
	    return("Datasegmentin koko");
		}	
		if (attributename.equals("executedCommands")) {
	    return("Suoritettuja käskyjä");
		}
		if (attributename.equals("outputLabel")) {
	    return("Tulosteet");
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
