package fi.helsinki.cs.koskelo.analyser;

import fi.helsinki.cs.koskelo.common.*;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.Feedback;
import fi.hy.eassari.showtask.trainer.CacheException;

/**
 * Luo kriteerien perusteella palautteen.
 */

public class TTK91FeedbackComposer{

	public TTK91FeedbackComposer() {
	}


	/**
	 * Metodi, joka muodostaa palautteen.
	 */

	public Feedback formFeedback(TTK91AnalyseResults analyseResults,
															 TTK91TaskFeedback taskFeedback,
															 AttributeCache cache,
															 String taskID,
															 String language) throws CacheException {
		
		StringBuffer feedbackTable = new StringBuffer(); // palaute html-muodossa
		
		String criteriaLabel = ""; // kriteerin otsikko, haetaan cachesta
		String feedbackLabel = ""; // palautteen otsikko, haetaan cachesta
		String qualityLabel = "";  // Laadullisen pal. otsikko, haetaan cachesta

		String criteriaHeader = ""; // käsiteltävän kriteerin nimi
		String feedback = "";       // käsiteltävä palaute
		String quality = "";        // käsiteltävä laadullinen palaute
		int evalution = 100;        // oletetaan, että tehtävä on oikein
		Boolean correct = null;


		criteriaLabel = cache.getAttribute
			("A", "ttk91feedbackcomposer", "criteriaLabel", language);

		feedbackLabel = cache.getAttribute
			("A", "ttk91feedbackcomposer", "criteriaLabel", language);

		qualityLabel = cache.getAttribute
			("A", "ttk91feedbackcomposer", "criteriaLabel", language);


		/**
		 * Luodaan taulukon alkuosa.
		 */

		feedbackTable.append("<table width=\"100%\" border=\"1\" cellspacing=\"0\"" 
												 +"cellpadding=\"3\">"
												 +"<tr align=\"center\">" 
												 +"<td class=\"tableHeader\" align =\"left\"" 
												 +"width=\"20%\">" +criteriaLabel +"</td>"
												 +"<td class=\"tableHeader\" align =\"left\""
												 +"width=\"40%\">"+feedbackLabel +"</td>"
												 +"<td class=\"tableHeader\" align =\"left\""
												 +"width=\"40%\">"+qualityLabel +"</td>"
												 +"</tr>");
	
			
		// Hyväksytty koko

		correct = analyseResults.getAcceptedSize();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"acceptedSizeHeader", language);
			if (correct.booleanValue()) {
				
				feedback = taskFeedback.getAcceptedSizeFeedbackPositive();

			} else {

				feedback = taskFeedback.getAcceptedSizeFeedbackNegative();

				// Jos yksikin kriteeri on väärin, vastausta ei hyväksytä.

				evalution = 0;
			} 

			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}//if

		// Optimaalinen koko

		correct = analyseResults.getOptimalSize();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"optimalSizeHeader", language);
			if (correct.booleanValue()) {
				
				feedback = "";
				quality = taskFeedback.getOptimalSizeFeedbackQuality();
			} 

			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}

		// Muistiin viittaukset

		correct = analyseResults.getMemoryReferences();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"memoryReferencesHeader", language);
			if (correct.booleanValue()) {
			
				feedback = taskFeedback.getMemoryReferencesFeedbackPositive();

			} else {

				feedback = taskFeedback.getMemoryReferencesFeedbackNegative();
				evalution = 0;
			} 
			if ((analyseResults.getMemoryReferencesQuality()).booleanValue()){
				quality = taskFeedback.getMemoryReferencesFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}

		// Vaaditut käskyt
		
		correct = analyseResults.getRequiredCommands();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"requiredCommandsHeader", language);
			if (correct.booleanValue()) {
				
				feedback = taskFeedback.getRequiredCommandsFeedbackPositive();

			} else {

				feedback = taskFeedback.getRequiredCommandsFeedbackNegative();
				evalution = 0;
			} 
			if ((analyseResults.getRequiredCommandsQuality()).booleanValue()){
				quality = taskFeedback.getRequiredCommandsFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}
	

		// Kielletyt käskyt
		
		correct = analyseResults.getForbiddenCommands();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"forbiddenCommandsHeader", language);
			if (correct.booleanValue()) {
			
				feedback = taskFeedback.getForbiddenCommandsFeedbackPositive();

			} else {

				feedback = taskFeedback.getForbiddenCommandsFeedbackNegative();
				evalution = 0;
			} 
			if ((analyseResults.getForbiddenCommandsQuality()).booleanValue()){
				quality = taskFeedback.getForbiddenCommandsFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}
		
			
		// Muisti
		
		correct = analyseResults.getMemory();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"memoryValuesHeader", language);
			if (correct.booleanValue()) {
			
				feedback = taskFeedback.getMemoryFeedbackPositive();

			} else {

				feedback = taskFeedback.getMemoryFeedbackNegative();
				evalution = 0;
			} 
			if ((analyseResults.getMemoryQuality()).booleanValue()){
				quality = taskFeedback.getMemoryFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}

		// Rekisterit
		
		correct = analyseResults.getRegisters();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"registerValuesHeader", language);
			if (correct.booleanValue()) {
			
				feedback = taskFeedback.getRegisterFeedbackPositive();

			} else {

				feedback = taskFeedback.getRegisterFeedbackNegative();
				evalution = 0;
			} 
			if ((analyseResults.getRegistersQuality()).booleanValue()){
				quality = taskFeedback.getRegisterFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}

		
		// Tulosteet näytölle
		
		correct = analyseResults.getScreenOutput();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"screenOutputHeader", language);
			if (correct.booleanValue()) {
			
				feedback = taskFeedback.getScreenOutputFeedbackPositive();

			} else {

				feedback = taskFeedback.getScreenOutputFeedbackNegative();
				evalution = 0;
			} 
			if ((analyseResults.getScreenOutputQuality()).booleanValue()){
				quality = taskFeedback.getScreenOutputFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
	
		}
		// Tulosteet tiedostoon
		
		correct = analyseResults.getFileOutput();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"fileOutputHeader", language);
			if (correct.booleanValue()) {
			
				feedback = taskFeedback.getFileOutputFeedbackPositive();

			} else {

				feedback = taskFeedback.getFileOutputFeedbackNegative();
				evalution = 0;
			} 
			if ((analyseResults.getFileOutputQuality()).booleanValue()){
				quality = taskFeedback.getFileOutputFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}
		// taulukko loppuu
 
		feedbackTable.append("</table><br>");


		/** 
		 * Haetaan tehtävää vastaava yleinen positiivinen 
		 * ja negatiivinen palaute.
		 */

	
		String feedbackSummaryPos = 
			cache.getAttribute("T", taskID, "positivefeedback", language);	

		String feedbackSummaryNeg = 
			cache.getAttribute("T", taskID, "negativefeedback", language);
		

		return new Feedback(0, evalution, feedbackSummaryPos, 
												feedbackSummaryNeg, new String(feedbackTable));
		
	}//formFeedback

	public Feedback formFeedback(String errorMessage) {

		return new Feedback(2, errorMessage);
	}//formFeedback


	private String getHTMLElementFeedbackRow(String criteriaName,
																					 String feedback,
																					 String quality,
																					 boolean correct) {

		String feedbackRow; // palautettava html-taulukon rivi
		
		feedbackRow = ("<tr><td width=\"20\">" +criteriaName +"</td>");

		if (correct) {
			feedbackRow += ("<td class=\"positivefeedback\" width=\"40\">" 
											+feedback +"</td>"); 
										
		} else {
			feedbackRow += ("<td class=\"negativefeedback\" width=\"40\">" 
											+feedback +"</td>");
		}
							 	  
		feedbackRow +=("<td class=\"positivefeedback\" width=\"40\">" 
									 +quality +"</td></tr>");

		return feedbackRow;
	
	}//getHTMLElementFeedbackRow



	public static void main(String [] args){
		FeedbackTestCache cache = new FeedbackTestCache();
		TTK91FeedbackComposer fbcomposer = new TTK91FeedbackComposer();
		TTK91AnalyseResults analyseRe = new TTK91AnalyseResults();
		TTK91TaskFeedback taskFb = new TTK91TaskFeedback();
		analyseRe.setAcceptedSize(false);
		analyseRe.setRequiredCommands(true);
		analyseRe.setRequiredCommandsQuality(true);
		taskFb.setAcceptedSizeFeedback("hyvä","huono", "tosi hyvä");
		taskFb.setRequiredCommandsFeedback("hyvä com","huono com", "tosi hyvä com");
		String [] crit = {"acceptedSize","requiredCommands"};
		boolean [] boo = {true,true};
		
		try{
			Feedback fb = fbcomposer.formFeedback(analyseRe, taskFb,
																						cache, "106", "FI");
			System.out.println(fb.getExtra());
		}
		catch(Exception e){}
		
	}
	
}//class
