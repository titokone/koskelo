package fi.helsinki.cs.koskelo.analyser;

import fi.helsinki.cs.koskelo.common.TTK91TaskCriteria;
import fi.helsinki.cs.koskelo.common.TTK91TaskOptions;
import fi.helsinki.cs.koskelo.common.TTK91TaskFeedback;
import fi.helsinki.cs.koskelo.common.InvalidTTK91CriteriaException;

import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CacheException;

/**
 * Luokka sisältää TTK91Analyser-luokille yhteisiä apumetodeita eAssarin cachen (AttributeCache) käsittelyyn.
 */

public class TTK91AnalyserUtils {

    TTK91TaskOptions taskOptions = new TTK91TaskOptions(); 
    AttributeCache cache;
    String taskid;
    String language;

    /**
     * Konstruktori, jossa välitetään oleelliset parametrit
     * @author Lauri Liuhto
     * @param cache    haetaan attribuutteja
     * @param taskid   käsiteltävän tehtävän tunnus
     * @param language käsiteltävän tehtävän kieli
     */
    public TTK91AnalyserUtils(AttributeCache cache, String taskid, String language) {
	this.cache = cache;
	this.taskid = taskid;
	this.language = language;
    }

    /**
     * Hakee kaikki kriteerit cachesta apumetodeinen avulla ja 
     * lisää ne taskOptions-muuttujaan.
     * @return kaikki kriteerit sisältävän TTK91TaskOptions-luokan ilmentymän
     * @throws CacheException jos cachen kanssa ongelmia
     * @throws InvalidTTK91CriteriaException jos kriteerien parsinta ei onnistu
     */

    //    private void getTTK91TaskOptions() -- muutettu 26.11.2004 / Lauri
    public TTK91TaskOptions getTTK91TaskOptions() 
	throws CacheException, InvalidTTK91CriteriaException {
		
	this.taskOptions.setCompareMethod
	    (getIntFromCache("compareMethod"));
		
	this.taskOptions.setMaxCommands
	    (getIntFromCache("maxCommands"));

	this.taskOptions.setAcceptedSize
	    (getIntFromCache("acceptedSize"));

	this.taskOptions.setOptimalSize
	    (getIntFromCache("optimalSize"));

	this.taskOptions.setExampleCode
	    (getStringFromCache("exampleCode"));

	this.taskOptions.setPublicInput
	    (getIntTableFromCache("publicInput"));

	this.taskOptions.setHiddenInput
	    (getIntTableFromCache("hiddenInput"));

	this.taskOptions.setRequiredCommands
	    (getStringTableFromCache("requiredCommands"));

	this.taskOptions.setForbiddenCommands
	    (getStringTableFromCache("forbiddenCommands"));
	 
	this.taskOptions.setScreenOutputCriterias
	    (getTaskCriteriaFromCache("screenOutput"));

	this.taskOptions.setFileOutputCriterias
	    (getTaskCriteriaFromCache("fileOutput"));
		
	this.taskOptions.setMemoryCriterias
	    (getTaskCriteriaFromCache("memoryValues"));
			
	this.taskOptions.setRegisterCriterias
	    (getTaskCriteriaFromCache("registerValues"));

	this.taskOptions.setTaskFeedback
	    (getTaskFeedbackFromCache());
 
	TTK91TaskCriteria[] temp = 
	    (getTaskCriteriaFromCache("memoryReferences"));

	if (temp == null) { // Lisätty 29.11.2004 / Tom
		this.taskOptions.setMemRefCriteria(null);
	} else {
		this.taskOptions.setMemRefCriteria(temp[0]);
	}

	return this.taskOptions; // Lisätty 26.11.2004 / Lauri

    }//TTK91TaskOptions

    /**
     * Apumetodi, joka hakee cachesta Stringin, jonka muuttaa kokonaisluvuksi.
		 * 
		 * @param cachesta haettavan attribuutin nimi tietokannassa
		 * @return haun tulos kokonaislukuna, jos attribuuttia ei löytynyt
		 *         palautetaan -1
		 * @throws CacheException jos tietokannan käsittelyssä ongelmia
     */

    private int getIntFromCache(String name) throws CacheException {
		
	int result;
		
	// Yritetään muuttaa String -> int, jos ei onnistu palautetaan -1.
		
	try {
	    result = Integer.parseInt
		(cache.getAttribute("T", this.taskid, 
				    name, this.language));
	} catch (NumberFormatException e) {
	    return -1;
	}

	return result;
		
    }//getIntFromCache

    /**
     * Apumetodi, joka hakee cachesta Stringin.
		 *
		 * @param  haettavan attribuutin nimi tietokannassa
		 * @return haun tulos, jos attribuuttia ei löytynyt
		 *         palautetaan ""
		 * @throws CacheException jos tietokannan käsittelyssä ongelmia
     */

    private String getStringFromCache(String name) throws CacheException {
		
	String result = cache.getAttribute("T", this.taskid, 
					   name, this.language);
		
	// Jos tulos on "null"-merkkijono, palautetaan tyhjä-merkkijono.

	if (result == null || result.equals("null")) {  // oli if (result.equals("null")) / Lauri
	    return "";
	} else {
	    return result;
	}
    }//getStringFromCache

    /**
     * Apumetodi hakee cachesta Stringin ja luo sen perusteella int-taulukon.
		 *
		 * @param  haettavan attribuutin nimi tietokannassa
		 * @return haun tulos, jos attribuuttia ei löytynyt
		 *         palautetaan null
		 * @throws CacheException jos tietokannan käsittelyssä ongelmia
     */

    private int[] getIntTableFromCache(String name) throws CacheException{

	String temp = cache.getAttribute("T", this.taskid, 
					 name, this.language);
	if (temp == null || temp.equals("null")) // lisätty tännekin == null / Tom
	    return null;

	String[] result = temp.split(",");
	int[] resultIntTable = new int[result.length];

	/* 
	 * Yritetään muuttaa jokainen taulukon alkio kokonaisluvuksi,
	 * jos ei onnistu palautetaan null.
	 */

	try {
	    for(int i = 0; i < result.length; ++i) {
		resultIntTable[i] = Integer.parseInt(result[i]);
	    }
	} catch (NumberFormatException e) {
	    return null;
	}
	return resultIntTable;
		
    }//getIntTableFromCache

    /**
     * Apumetodi hakee cachesta Stringin ja luo sen perusteella String-taulukon.
		 *
		 * @param  haettavan attribuutin nimi tietokannassa
		 * @return haun tulos, jos attribuuttia ei löytynyt
		 *         palautetaan null
		 * @throws CacheException jos tietokannan käsittelyssä ongelmia
     */

    private String[] getStringTableFromCache(String name) throws CacheException{
		
	String temp = cache.getAttribute("T", this.taskid, 
					 name, this.language);

	// Jos saadaan "null" palautetaan null.

	if (temp == null || temp.equals("null")) // lisätty temp == null / Tom
	    return null;

	String[] result = temp.split(";");

	return result;
    }//getStringTableFromCache
	
    /**
     * Apumetodi hakee Stringin ja luo sen perusteella 
		 * TTK91TaskCriteria-taulukon.
		 *
		 * @param  haettavan attribuutin nimi tietokannassa
		 * @return haun tulos, jos attribuuttia ei löytynyt
		 *         palautetaan null
		 * @throws CacheException jos tietokannan käsittelyssä ongelmia
     */

    private TTK91TaskCriteria[] getTaskCriteriaFromCache(String name) 
	throws CacheException, InvalidTTK91CriteriaException{
		
	String temp = cache.getAttribute("T", this.taskid, 
					 name, this.language);
		
	if (temp == null || temp.equals("null")) 
	    return null;

	// virheilmoitus
	System.err.println("AnalyserUtils.getTaskCriteriaFromCache" +temp); 

	String[] stringResult = temp.split(";");
	TTK91TaskCriteria[] result = new TTK91TaskCriteria[stringResult.length];
		
	/*
	 * Tutkitaan onko haettava kriteeri screenOutput tai fileOutput,
	 * koska kys. kriteereissä ei ole vertailuoperaattoria.
	 * TTK91TaskCriteria:n konstruktorin toinen parametri ilmoittaa
	 * onko kriteerissä vertailuoperaattoria.
	 */
	
	if (name.equals("screenOutput") || name.equals("fileOutput")) {
	    for (int i = 0; i < result.length; ++i) {
		result[i] = new TTK91TaskCriteria(stringResult[i], false);
	    }
	} 
	else if (name.equals("memoryReferences")) {
	    for (int i = 0; i < result.length; ++i) {
		result[i] = new TTK91TaskCriteria("MemoryReference"+stringResult[i], true);
	    }
	}
	else {
	    for (int i = 0; i < result.length; ++i) {
		result[i] = new TTK91TaskCriteria(stringResult[i], true);
	    }
	}
	return result;
    }//getTaskCriteriaFromCache
		
    /**
     * Metodi hakee jokaista kriteeriä vastaavat palautteet ja lisää ne 
     * taskFeedback-olioon. AcceptedSize, OptimalSize ja MemoryReferences
     * eivät saa laadullista palautetta.
		 *
		 * @return sisältää palautteet
		 * @throws CacheException jos tietokannan käsittelyssä ongelmia
     */
	
    private TTK91TaskFeedback getTaskFeedbackFromCache()throws CacheException {

	TTK91TaskFeedback taskFeedback = new TTK91TaskFeedback();

	taskFeedback.setAcceptedSizeFeedback
	    (getStringFromCache("acceptedSizeFeedbackPositive"),
	     getStringFromCache("acceptedSizeFeedbackNegative"),"");
			 			 
	taskFeedback.setOptimalSizeFeedback
	    (getStringFromCache("optimalSizeFeedbackPositive"),
	     getStringFromCache("optimalSizeFeedbackNegative"),"");

	taskFeedback.setRequiredCommandsFeedback
	    (getStringFromCache("requiredCommandsFeedbackPositive"),
	     getStringFromCache("requiredCommandsFeedbackNegative"),
	     getStringFromCache("requiredCommandsQualityFeedback"));

	taskFeedback.setForbiddenCommandsFeedback
	    (getStringFromCache("forbiddenCommandsFeedbackPositive"),
	     getStringFromCache("forbiddenCommandsFeedbackNegative"),
	     getStringFromCache("forbiddenCommandsQualityFeedback"));

	taskFeedback.setRegisterFeedback
	    (getStringFromCache("registerFeedbackPositive"),
	     getStringFromCache("registerFeedbackNegative"),
	     getStringFromCache("registerQualityFeedback"));

	taskFeedback.setMemoryFeedback
	    (getStringFromCache("memoryFeedbackPositive"),
	     getStringFromCache("memoryFeedbackNegative"),
	     getStringFromCache("memoryQualityFeedback"));

	taskFeedback.setMemoryReferencesFeedback
	    (getStringFromCache("memoryReferencesFeedbackPositive"),
	     getStringFromCache("memoryReferencesFeedbackNegative"),"");

	taskFeedback.setScreenOutputFeedback
	    (getStringFromCache("screenOutputFeedbackPositive"),
	     getStringFromCache("screenOutputFeedbackNegative"),
	     getStringFromCache("screenOutputQualityFeedback"));

	taskFeedback.setFileOutputFeedback
	    (getStringFromCache("fileOutputFeedbackPositive"),
	     getStringFromCache("fileOutputFeedbackNegative"),
	     getStringFromCache("fileOutputQualityFeedback"));

	return taskFeedback;
    }//getTaskFeedback



}//class

