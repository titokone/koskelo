package fi.helsinki.cs.koskelo.test;

import java.util.*;
import junit.framework.*;
import fi.helsinki.cs.koskelo.analyser.*;
import fi.helsinki.cs.koskelo.common.*;
import java.sql.SQLException;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CacheException;
import fi.hy.eassari.showtask.trainer.Feedback;

public class FeedbackComposerTest extends TestCase {
	
	TestCache cache = new TestCache();
	
	public void testFormFeedback() {

	 
		TTK91FeedbackComposer fbcomposer = new TTK91FeedbackComposer();
		TTK91AnalyseResults analyseRe = new TTK91AnalyseResults();
		TTK91TaskFeedback taskFb = new TTK91TaskFeedback();
		String language = "";
		String taskID = "";

		// Testiparametreja

		analyseRe.setAcceptedSize(false);
		
		analyseRe.setRequiredCommands(true);
		analyseRe.setRequiredCommandsQuality(true);
		int[] reg = {1,2,3,4,5,6,7};
		analyseRe.setRegisterValues(reg);
		int[] crt = {10,20};
		int[] file = {100,200};
		analyseRe.setMemoryReferenceCount(20);
		analyseRe.setStackSize(15);
		analyseRe.setDataSegmentSize(50);
		analyseRe.setCodeSegmentSize(5);
		analyseRe.setCrt(crt);
		analyseRe.setFile(file);
		analyseRe.setExecutedCommandsCount(30);
		taskFb.setAcceptedSizeFeedback("hyvä","huono", "tosi hyvä");
		taskFb.setRequiredCommandsFeedback("hyvä com","huono com", "tosi hyvä com");
	
	
		TTK91FeedbackComposer test = new TTK91FeedbackComposer();
		Feedback testFB = new Feedback();;
		
		try {
			testFB = test.formFeedback(analyseRe, taskFb, cache,
																					taskID, language);
		}
		catch (Exception e){fail("");}

	
		
		Assert.assertEquals(testFB.getExtra(),
												// Palaute
												"<table width=\"100%\" border=\"1\" cellspacing=\"0\"" 
												+"cellpadding=\"3\">"
												+"<tr align=\"center\">" 
												+"<td class=\"tableHeader\" align =\"left\"" 
												+"width=\"20%\">Kriteerit</td>"
												+"<td class=\"tableHeader\" align =\"left\""
												+"width=\"40%\">Palaute</td>"
												+"<td class=\"tableHeader\" align =\"left\""
												+"width=\"40%\">Laadullinen palaute</td>"
												+"</tr>"
												+"<tr><td width=\"20\"><strong>Hyväksytty koko" 
												+"</strong></td>"
												+"<td class=\"negativefeedback\" width=\"40\">" 
												+"huono</td>"
												+"<td class=\"positivefeedback\" width=\"40\">" 
												+"</td></tr>"
												+"<tr><td width=\"20\"><strong>Vaaditut käskyt" 
												+"</strong></td>"
												+"<td class=\"positivefeedback\" width=\"40\">" 
												+"hyvä com</td>"
												+"<td class=\"positivefeedback\" width=\"40\">" 
												+"tosi hyvä com</td></tr></table><br>"
												// Rekisterit
												+"<table width=\"30%\" border=\"1\" cellspacing=\"0\"" 
												+"cellpadding=\"3\">"
												+"<tr align=\"center\">" 
												+"<td class=\"tableHeader\" align =\"left\">" 
												+"Rekisterien sisältö</td>"
												+"</tr>"
												+"<tr><td>R0: 1</td></tr>"
												+"<tr><td>R1: 2</td></tr>"
												+"<tr><td>R2: 3</td></tr>"
												+"<tr><td>R3: 4</td></tr>"
												+"<tr><td>R4: 5</td></tr>"
												+"<tr><td>R5: 6</td></tr>"
												+"<tr><td>R6: 7</td></tr></table><br>"
												// Statistiikka
												+"<table width=\"30%\" border=\"1\" cellspacing=\"0\"" 
												+"cellpadding=\"3\">"
												+"<tr align=\"center\">" 
												+"<td class=\"tableHeader\" align =\"left\">" 
												+"Statistiikka</td>"
												+"</tr>"
												+"<tr><td>Muistiviitteiden määrä: 20</td></tr>"
												+"<tr><td>Pinon koko: 15</td></tr>"
												+"<tr><td>Koodisegmentti: 5</td></tr>"
												+"<tr><td>Datasegmentti: 50</td></tr>"
												+"<tr><td>Suoritettuja käskyjä: 30</td></tr></table><br>"
												// Tulosteet
												+"<table width=\"30%\" border=\"1\" cellspacing=\"0\"" 
												+"cellpadding=\"3\">"
												+"<tr align=\"center\">" 
												+"<td class=\"tableHeader\" align =\"left\">" 
												+"Tulosteet</td>"
												+"</tr>"
												+"<tr><td>CRT 1:  10</td></tr>"
												+"<tr><td>CRT 2:  20</td></tr>"
												+"<tr><td>FILE 1: 100</td></tr>"
												+"<tr><td>FILE 2: 200</td></tr>"
												+"</table><br>");
		
		

	}
	private class TestCache implements AttributeCache{

		public String getAttribute (String objType, 
																String objID, 
																String attributename, 
																String language) throws CacheException{
	
			if (attributename.equals("criteriaLabel")) {
				return("Kriteerit");
			}
			if (attributename.equals("feedbackLabel")) {
				return("Palaute");
			}
			if (attributename.equals("qualityLabel")) {
				return("Laadullinen palaute");
			}  
			if (attributename.equals("submitbutton")) {
				return("Lähetä");
			}  
			
			if (attributename.equals("acceptedSizeHeader")) {
				return("Hyväksytty koko");
			}  
			if (attributename.equals("requiredCommandsHeader")) {
				return("Vaaditut käskyt");
			}  
			
			if (attributename.equals("registervaluesLabel")) {
				return("Rekisterien sisältö");
			}
			if (attributename.equals("statisticsLabel")) {
				return("Statistiikka");
			}
			if (attributename.equals("memoryReference")) {
				return("Muistiviitteiden määrä");
			}
			if (attributename.equals("stackSize")) {
				return("Pinon koko");
			}  
			if (attributename.equals("codeSegment")) {
				return("Koodisegmentti");
			}  
			
			if (attributename.equals("dataSegment")) {
				return("Datasegmentti");
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
