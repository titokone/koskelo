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
	 * Metodi, joka muodostaa palautteen. Tutkii jokaista kriteeri‰ kohden
	 * onko se oikein ja muodostaa palautteen sen mukaisesti. Palaute on
	 * html-muodossa.
	 *
	 * @param analyseResults Analyserin teht‰v‰‰n antama analyysin tulos
	 * @param taskFeedback   teht‰v‰‰n liittyv‰t palautteet
	 * @param cache          haetaan teht‰v‰‰n liittyvi‰ attribuutteja
	 * @param taskID         teht‰v‰n tunnus
	 * @param language       k‰ytetty kieli
	 * @return Feedback-olion, joka sis‰lt‰‰ palautteen
	 * @throws CacheException jos tulee ongelmia attribuuttien 
	 *                        hakemisessa AttributeCachesta
	 */

	public static Feedback formFeedback(TTK91AnalyseResults analyseResults,
															 TTK91TaskFeedback taskFeedback,
															 AttributeCache cache,
															 String taskID,
															 String language) throws CacheException {
		
		StringBuffer feedbackTable = new StringBuffer(); // palaute html-muodossa
		
		String criteriaLabel = ""; // kriteerin otsikko, haetaan cachesta
		String feedbackLabel = ""; // palautteen otsikko, haetaan cachesta
		String qualityLabel = "";  // Laadullisen pal. otsikko, haetaan cachesta

		String criteriaHeader = ""; // k‰sitelt‰v‰n kriteerin nimi
		String feedback = "";       // k‰sitelt‰v‰ palaute
		String quality = "";        // k‰sitelt‰v‰ laadullinen palaute
		int evaluation = 100;        // oletetaan, ett‰ teht‰v‰ on oikein
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
	
		/**
		 * Seuraavassa k‰yd‰‰n l‰pi jokainen kriteerityyppi ja
		 * katsotaan onko kriteerityyppi oikein, v‰‰rin tai onko
		 * sit‰ m‰‰ritelty ollenkaan. Jos kriteeri
		 * on oikein/v‰‰rin, haetaan positiivinen/negatiivinen palaute.
		 * Lis‰ksi tarkistetaan onko kriteerityypin laadullinen osa oikein
		 * ja haetaan tarvittaessa laadullinen palaute. 
		 */

			
		// Hyv‰ksytty koko, kriteeriin ei liity laadullista palautetta

		correct = analyseResults.getAcceptedSize();
		if (correct != null) { // null tarkoittaa, ett‰ kriteerityyppi‰ ei ole

			// haetaan kriiterin kielikohtainen otsikko

			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"acceptedSizeHeader", language);
			if (correct.booleanValue()) { // kriteeri oikein
				
				feedback = taskFeedback.getAcceptedSizeFeedbackPositive();

			} else { // kriteeri v‰‰rin

				feedback = taskFeedback.getAcceptedSizeFeedbackNegative();

				// Jos yksikin kriteeri on v‰‰rin, vastausta ei hyv‰ksyt‰.

				evaluation = 0;
			} 

			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}//if

		// Optimaalinen koko, kriteeriin ei liity laadullista palautetta

		correct = analyseResults.getOptimalSize();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"optimalSizeHeader", language);
			if (correct.booleanValue()) {
				
				feedback = taskFeedback.getOptimalSizeFeedbackPositive();
			} 
			else {
			
				feedback = taskFeedback.getOptimalSizeFeedbackNegative();
			}

			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}//if

		// Muistiin viittaukset, kriteeriin ei liity laadullista palautetta

		correct = analyseResults.getMemoryReferences();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"memoryReferencesHeader", language);
			if (correct.booleanValue()) { 
			
				feedback = taskFeedback.getMemoryReferencesFeedbackPositive();

			} else { 

				feedback = taskFeedback.getMemoryReferencesFeedbackNegative();
				evaluation = 0;
			} 
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}//if

		// Vaaditut k‰skyt
		
		correct = analyseResults.getRequiredCommands();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"requiredCommandsHeader", language);
			if (correct.booleanValue()) {
				
				feedback = taskFeedback.getRequiredCommandsFeedbackPositive();

			} else {

				feedback = taskFeedback.getRequiredCommandsFeedbackNegative();
				evaluation = 0;
			} 
			// tutkitaan onko laadullinen kriteeri oikein
			if ((analyseResults.getRequiredCommandsQuality()).booleanValue()){
				quality = taskFeedback.getRequiredCommandsFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}//if
	

		// Kielletyt k‰skyt
		
		correct = analyseResults.getForbiddenCommands();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"forbiddenCommandsHeader", language);
			if (correct.booleanValue()) {
			
				feedback = taskFeedback.getForbiddenCommandsFeedbackPositive();

			} else {

				feedback = taskFeedback.getForbiddenCommandsFeedbackNegative();
				evaluation = 0;
			} 
			if ((analyseResults.getForbiddenCommandsQuality()).booleanValue()){
				quality = taskFeedback.getForbiddenCommandsFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}//if
		
			
		// Muisti
		
		correct = analyseResults.getMemory();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"memoryValuesHeader", language);
			if (correct.booleanValue()) {
			
				feedback = taskFeedback.getMemoryFeedbackPositive();

			} else {

				feedback = taskFeedback.getMemoryFeedbackNegative();
				evaluation = 0;
			} 
			if ((analyseResults.getMemoryQuality()).booleanValue()){
				quality = taskFeedback.getMemoryFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}//if

		// Rekisterit
		
		correct = analyseResults.getRegisters();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"registerValuesHeader", language);
			if (correct.booleanValue()) {
			
				feedback = taskFeedback.getRegisterFeedbackPositive();

			} else {

				feedback = taskFeedback.getRegisterFeedbackNegative();
				evaluation = 0;
			} 
			if ((analyseResults.getRegistersQuality()).booleanValue()){
				quality = taskFeedback.getRegisterFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}//if

		
		// Tulosteet n‰ytˆlle
		
		correct = analyseResults.getScreenOutput();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"screenOutputHeader", language);
			if (correct.booleanValue()) {
			
				feedback = taskFeedback.getScreenOutputFeedbackPositive();

			} else {

				feedback = taskFeedback.getScreenOutputFeedbackNegative();
				evaluation = 0;
			} 
			if ((analyseResults.getScreenOutputQuality()).booleanValue()){
				quality = taskFeedback.getScreenOutputFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
	
		}//if

		// Tulosteet tiedostoon
		
		correct = analyseResults.getFileOutput();
		if (correct != null) {
			criteriaHeader = cache.getAttribute("D","statictaskcomposer", 
																					"fileOutputHeader", language);
			if (correct.booleanValue()) {
			
				feedback = taskFeedback.getFileOutputFeedbackPositive();

			} else {

				feedback = taskFeedback.getFileOutputFeedbackNegative();
				evaluation = 0;
			} 
			if ((analyseResults.getFileOutputQuality()).booleanValue()){
				quality = taskFeedback.getFileOutputFeedbackQuality();
			} else {
				quality = "";
			}
		
			feedbackTable.append(getHTMLElementFeedbackRow(criteriaHeader, feedback, 
																										 quality, 
																										 correct.booleanValue()));
		}//if

		// taulukko loppuu
 
		feedbackTable.append("</table><br>");

		/**
		 * TODO: Statistiikkan lis‰ys palautteeseen
		 */
		
		

		/** 
		 * Haetaan teht‰v‰‰ vastaava yleinen positiivinen 
		 * ja negatiivinen palaute.
		 */

	
		String feedbackSummaryPos = 
			cache.getAttribute("T", taskID, "positivefeedback", language);	

		String feedbackSummaryNeg = 
			cache.getAttribute("T", taskID, "negativefeedback", language);
		
		/**
		 * Lopuksi luodaan uusi Feedback-olio. Parametreja ovat:
		 * 0 (onnistumisen koodi), evaluation (oikeellisuusprosentti),
		 * feedbackSummary/Pos/Neg (teht‰v‰n positiivinen/negatiivinen palaute),
		 * new String(feedbackTable) (kriteerien palaute).
		 */

		return new Feedback(0, evaluation, feedbackSummaryPos, 
												feedbackSummaryNeg, new String(feedbackTable));
		
	}//formFeedback

	/**
	 * Muodostaa virheilmoituksen.
	 *
	 * @param errorMessage virheilmoitus
	 * @return sis‰lt‰‰ virheilmoituksen
	 */


	public static Feedback formFeedback(String errorMessage) {

		// Virhekoodi 2 on fataalivirhe.

		return new Feedback(2, errorMessage);
	}//formFeedback


	private static String getHTMLElementFeedbackRow(String criteriaName,
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

	/*	

	public static void main(String [] args){
		FeedbackTestCache cache = new FeedbackTestCache();
		TTK91FeedbackComposer fbcomposer = new TTK91FeedbackComposer();
		TTK91AnalyseResults analyseRe = new TTK91AnalyseResults();
		TTK91TaskFeedback taskFb = new TTK91TaskFeedback();
		analyseRe.setAcceptedSize(false);
		analyseRe.setRequiredCommands(true);
		analyseRe.setRequiredCommandsQuality(true);
		taskFb.setAcceptedSizeFeedback("hyv‰","huono", "tosi hyv‰");
		taskFb.setRequiredCommandsFeedback("hyv‰ com","huono com", "tosi hyv‰ com");
		String [] crit = {"acceptedSize","requiredCommands"};
		boolean [] boo = {true,true};
		
		try{
			Feedback fb = fbcomposer.formFeedback(analyseRe, taskFb,
																						cache, "106", "FI");
			System.out.println(fb.getExtra());
		}
		catch(Exception e){}
		
	}
	*/
}//class
