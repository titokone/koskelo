package fi.helsinki.cs.koskelo.analyser;

import fi.helsinki.cs.koskelo.common.*;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.Feedback;
import fi.hy.eassari.showtask.trainer.CacheException;

/**
 * Luokka, joka luo kriteerien perusteella palautteen.
 */

public class TTK91FeedbackComposer{

    public TTK91FeedbackComposer() {
    }


    /**
     * Metodi, joka muodostaa palautteen. Ensin tutkii jokaista kriteeri‰ 
     * kohden onko se oikein ja muodostaa palautteen sen mukaisesti. 
     * Seuraavaksi haetaan rekisterien arvot, statistiikka ja tulosteet, 
     * jotka laitetaan taulukoihin. Palaute on html-taulukoissa.
     *
     * @param analyseResults Analyserin teht‰v‰‰n antama analyysin tulos
     * @param taskFeedback   teht‰v‰‰n liittyv‰t palautteet
     * @param cache          haetaan teht‰v‰‰n liittyvi‰ attribuutteja
     * @param taskID         teht‰v‰n tunnus
     * @param language       k‰ytetty kieli
     * @return Feedback-olion, joka sis‰lt‰‰ palautteen
     * @throws CacheException jos tulee ongelmia attribuuttien 
     *                        hakemisessa cachesta
     */

    public static Feedback formFeedback(TTK91AnalyseResults analyseResults,
					TTK91TaskFeedback taskFeedback,
					AttributeCache cache,
					String taskID,
					String language) throws CacheException {
		
	System.err.println("Tultiin formFeedbackiin");

	StringBuffer feedbackTable = new StringBuffer(); // palaute html-muodossa

	String criteriaHeader = ""; // k‰sitelt‰v‰n kriteerin nimi
	String feedback = "";       // k‰sitelt‰v‰ palaute
	String quality = "";        // k‰sitelt‰v‰ laadullinen palaute
	int evaluation = 100;       // oletetaan aluksi, ett‰ teht‰v‰ on oikein
	Boolean correct = null;     // sis‰lt‰‰ tiedon kriteerin oikeellisuudesta


	String criteriaLabel = cache.getAttribute // kriteerin otsikko
	    ("A", "ttk91feedbackcomposer", "criteriaLabel", language);

	String feedbackLabel = cache.getAttribute // palautteen otsikko
	    ("A", "ttk91feedbackcomposer", "criteriaLabel", language);

	String qualityLabel = cache.getAttribute // laadullisen pal. otsikko
	    ("A", "ttk91feedbackcomposer", "criteriaLabel", language);



	/************************************************
	 * Kriteerikohtaisen palautteen lis‰‰minen
	 ************************************************/
		
	// Luodaan taulukon alkuosa.

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

	    criteriaHeader = cache.getAttribute("D","staticttk91taskcomposer", 
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
	    criteriaHeader = cache.getAttribute("D","staticttk91taskcomposer", 
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
	    criteriaHeader = cache.getAttribute("D","staticttk91taskcomposer", 
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
	    criteriaHeader = cache.getAttribute("D","staticttk91taskcomposer", 
						"requiredCommandsHeader", language);
	    if (correct.booleanValue()) {
				
		feedback = taskFeedback.getRequiredCommandsFeedbackPositive();

	    } else {

		feedback = taskFeedback.getRequiredCommandsFeedbackNegative();
		evaluation = 0;
	    } 
	    // tutkitaan onko laadullinen kriteeri oikein
	    Boolean res = analyseResults.getRequiredCommandsQuality();
	    
	    if (  (res != null) && (res.booleanValue()) ) { 
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
	    criteriaHeader = cache.getAttribute("D","staticttk91taskcomposer", 
						"forbiddenCommandsHeader", language);
	    if (correct.booleanValue()) {
			
		feedback = taskFeedback.getForbiddenCommandsFeedbackPositive();

	    } else {

		feedback = taskFeedback.getForbiddenCommandsFeedbackNegative();
		evaluation = 0;
	    } 
	    Boolean res = analyseResults.getForbiddenCommandsQuality();

	    if ( (res != null) && (res.booleanValue()) ) { 
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
	System.err.println("korrektin arvo, n. rivill‰ 219 on: "+correct.booleanValue());
	if (correct != null) {
	    criteriaHeader = cache.getAttribute("D","staticttk91taskcomposer", 
						"memoryValuesHeader", language);
	    if (correct.booleanValue()) {
			
		feedback = taskFeedback.getMemoryFeedbackPositive();

	    } else {

		feedback = taskFeedback.getMemoryFeedbackNegative();
		evaluation = 0;
	    } 

	    Boolean res = analyseResults.getMemoryQuality();
	    if ( (res != null) && (res.booleanValue()) ) { 
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
	    criteriaHeader = cache.getAttribute("D","staticttk91taskcomposer", 
						"registerValuesHeader", language);
	    if (correct.booleanValue()) {
			
		feedback = taskFeedback.getRegisterFeedbackPositive();

	    } else {

		feedback = taskFeedback.getRegisterFeedbackNegative();
		evaluation = 0;
	    } 

	    Boolean res = analyseResults.getRegistersQuality();
	    if ( (res != null) && (res.booleanValue()) ) { 
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
	    criteriaHeader = cache.getAttribute("D","staticttk91taskcomposer", 
						"screenOutputHeader", language);
	    if (correct.booleanValue()) {
			
		feedback = taskFeedback.getScreenOutputFeedbackPositive();

	    } else {

		feedback = taskFeedback.getScreenOutputFeedbackNegative();
		evaluation = 0;
	    } 

	    Boolean res = analyseResults.getScreenOutputQuality();
	    if ( (res != null) && (res.booleanValue()) ) { 
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
	    criteriaHeader = cache.getAttribute("D","staticttk91taskcomposer", 
						"fileOutputHeader", language);
	    if (correct.booleanValue()) {
			
		feedback = taskFeedback.getFileOutputFeedbackPositive();

	    } else {

		feedback = taskFeedback.getFileOutputFeedbackNegative();
		evaluation = 0;
	    } 
	    
	    Boolean res = analyseResults.getFileOutputQuality();
	    if ( (res != null) && (res.booleanValue()) ) { 
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

	/************************************************
	 * Rekisterien sis‰llˆn lis‰‰minen
	 ************************************************/
		
	String registervaluesLabel = cache.getAttribute // rekisterien otsikko
	    ("A", "ttk91feedbackcomposer", "registersLabel", language);
	
		
	feedbackTable.append("<table width=\"30%\" border=\"1\" cellspacing=\"0\"" 
			     +"cellpadding=\"3\">"
			     +"<tr align=\"center\">" 
			     +"<td class=\"tableHeader\" align =\"left\">" 
			     +registervaluesLabel +"</td>"
			     +"</tr>");
		
	// Haetaan rekisterien arvot int-taulukkoon.

	int[] registers = analyseResults.getRegisterValues();
	if (registers != null) { // FIXME: Tom; fiksattiin eevan kanssa n‰in, koska tuolta voi oikeasti tulla nullia. Eih‰n rikota sun hˆtˆmlej‰? / [LL]
	    for(int i = 0; i < registers.length; ++i) {
		feedbackTable.append("<tr><td>R" +i +": "
				     +registers[i] +"</td>"
				     +"</tr>");
	    }
	}
	feedbackTable.append("</table><br>");

	/************************************************
	 * Statistiikan lis‰‰minen
	 ************************************************/
		
	// Kielikohtaisten otsikkojen hakeminen cachesta

	String statisticsLabel = cache.getAttribute
	    ("A", "ttk91feedbackcomposer", "statisticsLabel", language);
	String memoryReference = cache.getAttribute
	    ("A", "ttk91feedbackcomposer", "memoryReference", language);
	String stackSize = cache.getAttribute
	    ("A", "ttk91feedbackcomposer", "stackSize", language);
	String codeSegment = cache.getAttribute
	    ("A", "ttk91feedbackcomposer", "codeSegment", language);
	String dataSegment = cache.getAttribute
	    ("A", "ttk91feedbackcomposer", "dataSegment", language);
	String executedCommands = cache.getAttribute
	    ("A", "ttk91feedbackcomposer", "executedCommands", language);
		

	feedbackTable.append("<table width=\"30%\" border=\"1\" cellspacing=\"0\"" 
			     +"cellpadding=\"3\">"
			     +"<tr align=\"center\">" 
			     +"<td class=\"tableHeader\" align =\"left\">" 
			     +statisticsLabel +"</td>"
			     +"</tr>");

	feedbackTable.append("<td>" +	memoryReference +": "
			     +analyseResults.getMemoryReferenceCount()
			     +"</tr>");
	feedbackTable.append("<td>" +	stackSize +": "
			     +analyseResults.getStackSize()
			     +"</tr>");
	feedbackTable.append("<td>" +	codeSegment +": "
			     +analyseResults.getCodeSegmentSize()
			     +"</tr>");
	feedbackTable.append("<td>" +	dataSegment +": "
			     +analyseResults.getDataSegmentSize()
			     +"</tr>");	
	feedbackTable.append("<td>" +	executedCommands +": "
			     +analyseResults.getExecutedCommandsCount()
			     +"</tr>");
	feedbackTable.append("</table><br>");

	/************************************************
	 * Tulosteiden (CRT, file) lis‰ys palautteeseen
	 ************************************************/

	String outputLabel = cache.getAttribute
	    ("A", "ttk91feedbackcomposer", "outputLabel", language);

	int[] crt = analyseResults.getCrt();
	int[] file = analyseResults.getFile();

	feedbackTable.append("<table width=\"30%\" border=\"1\" cellspacing=\"0\"" 
			     +"cellpadding=\"3\">"
			     +"<tr align=\"center\">" 
			     +"<td class=\"tableHeader\" align =\"left\">" 
			     +outputLabel +"</td>"
			     +"</tr>");

	if (crt != null) { // FIXME: Tom; t‰m‰ ja seuraava vastaava iffi, fiksattiin eevan kanssa samoin kuin aiemmpi... [LL]
	    for(int i = 0; i < crt.length; ++i) {
		feedbackTable.append("<td>CRT " +(i+1) +":  " +crt[i] +"</td></tr>");
	    }
	}
	if (file != null) {
	    for(int i = 0; i < file.length; ++i) {
		feedbackTable.append("<td>FILE " +(i+1) +": " +file[i] +"</td></tr>");
	    }
	}
	feedbackTable.append("</table><br>");

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
	 * new String(feedbackTable) (palaute).
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

    /**
     * Luo parametrien perusteella yhden html-taulukon rivin.
     *
     * @param criteriaName kriteerin nimi
     * @param feedback     palaute
     * @param quality      mahdollinen laadullinen palaute
     * @param correct      oliko kriteeri oikein
     * @return html-taulukon rivi             
     */

    private static String getHTMLElementFeedbackRow(String criteriaName,
						    String feedback,
						    String quality,
						    boolean correct) {

	String feedbackRow; // palautettava html-taulukon rivi
		
	feedbackRow = ("<tr><td width=\"20\"><strong>" +criteriaName 
		       +"</strong></td>");

	if (correct) { // jos kriteeri oikein
	    feedbackRow += ("<td class=\"positivefeedback\" width=\"40\">" 
			    +feedback +"</td>"); 
										
	} else {       // jos kriteeri v‰‰rin
	    feedbackRow += ("<td class=\"negativefeedback\" width=\"40\">" 
			    +feedback +"</td>");
	}
							 	  
	// Lis‰t‰‰n laadullinen palaute.

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
      int[] temp = {1,2,3,4,5,6,7};
      analyseRe.setRegisterValues(temp);
      analyseRe.setMemoryReferenceCount(20);
      analyseRe.setStackSize(15);
      analyseRe.setDataSegmentSize(50);
      analyseRe.setCodeSegmentSize(5);
      analyseRe.setStackSize(30);
      analyseRe.setCrt(temp);
      analyseRe.setFile(temp);
      analyseRe.setExecutedCommandsCount(100);
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
