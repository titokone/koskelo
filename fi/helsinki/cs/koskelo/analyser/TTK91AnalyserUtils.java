package fi.helsinki.cs.koskelo.analyser;

import fi.helsinki.cs.koskelo.common.TTK91TaskCriteria;
import fi.helsinki.cs.koskelo.common.TTK91TaskOptions;
import fi.helsinki.cs.koskelo.common.TTK91TaskFeedback;
import fi.helsinki.cs.koskelo.common.InvalidTTK91CriteriaException;

import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CacheException;

public class TTK91AnalyserUtils {

	/**
	 * Hakee kaikki kriteerit cachesta apumetodeinen avulla ja 
	 * lis‰‰ ne taskOptions-luokkamuuttujaan.
	 *
	 * @throws CacheException jos cachen kanssa ongelmia
	 * @throws InvalidTTK91CriteriaException jos kriteerien parsinta ei onnistu
	 */

	private void getTTK91TaskOptions(TTK91 TaskOptions taskOptions) 
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
	 
		this.taskOptions.setMemRefCriteria(temp[0]);
	
	}//TTK91TaskOptions

	/**
	 * Apumetodi, joka hakee cachesta Stringin, jonka muuttaa kokonaisluvuksi.
	 */

	private int getIntFromCache(String name) throws CacheException {
		
		int result;
		
		// Yritet‰‰n muuttaa String -> int, jos ei onnistu palautetaan -1.
		
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
	 */

	private String getStringFromCache(String name) throws CacheException {
		
		String result = cache.getAttribute("T", this.taskid, 
																			 name, this.language);
		
		// Jos tulos on "null"-merkkijono, palautetaan tyhj‰-merkkijono.

		if (result.equals("null")) { 
			return "";
		} else {
			return result;
		}
	}//getStringFromCache

	/**
	 * Apumetodi hakee cachesta Stringin ja luo sen perusteella int-taulukon.
	 */

	private int[] getIntTableFromCache(String name) throws CacheException{

		String temp = cache.getAttribute("T", this.taskid, 
							 											 name, this.language);
		if (temp.equals("null"))
			return null;

		String[] result = temp.split(",");
		int[] resultIntTable = new int[result.length];

		/* 
		 * Yritet‰‰n muuttaa jokainen taulukon alkio kokonaisluvuksi,
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
	 */

	private String[] getStringTableFromCache(String name) throws CacheException{
		
		String temp = cache.getAttribute("T", this.taskid, 
							 											 name, this.language);

		// Jos saadaan "null" palautetaan "".

		if (temp.equals("null"))
			return null;

		String[] result = temp.split(";");

		return result;
	}//getStringTableFromCache
	
	/**
	 * Apumetodi Hakee Stringin ja luo sen perusteella TTK91TaskCriteria-taulukon.
	 */

	private TTK91TaskCriteria[] getTaskCriteriaFromCache(String name) 
		throws CacheException, InvalidTTK91CriteriaException{
		
		String temp = cache.getAttribute("T", this.taskid, 
							 											 name, this.language);
		
		if (temp.equals("null")) 
			return null;
		
		String[] stringResult = temp.split(";");
		TTK91TaskCriteria[] result = new TTK91TaskCriteria[stringResult.length];
		
		/*
		 * Tutkitaan onko haettava kriteeri screenOutput tai fileOutput,
		 * koska kys. kriteereiss‰ ei ole vertailuoperaattoria.
		 * TTK91TaskCriteria:n konstruktorin toinen parametri ilmoittaa
		 * onko kriteeriss‰ vertailuoperaattoria.
		 */
	
		if (name.equals("screenOutput") || name.equals("fileOutput")) {
			for (int i = 0; i < result.length; ++i) {
				result[i] = new TTK91TaskCriteria(stringResult[i], false);
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
	 * Metodi hakee jokaista kriteeri‰ vastaavat palautteet ja lis‰‰ ne 
	 * taskFeedback-olioon. AcceptedSize, OptimalSize ja MemoryReferences
	 * eiv‰t saa laadullista palautetta.
	 */
	
	private TTK91TaskFeedback getTaskFeedbackFromCache()throws CacheException {

		TTK91TaskFeedback taskFeedback = new TTK91TaskFeedback();

		taskFeedback.setAcceptedSizeFeedback
			(getStringFromCache("acceptedSizeFeedbackPositive"),
			 getStringFromCache("acceptedSizeFeedbackNegative"),"");
			 			 
		taskFeedback.setOptimalSizeFeedback
			(getStringFromCache("optimalSizeFeedbackPositive"),
			 getStringFromCache("FeedbackNegative"),"");

		taskFeedback.setRequiredCommandsFeedback
			(getStringFromCache("requiredCommandsFeedbackPositive"),
			 getStringFromCache("requiredCommandsFeedbackNegative"),
			 getStringFromCache("requiredCommandsFeedbackQuality"));

		taskFeedback.setForbiddenCommandsFeedback
			(getStringFromCache("forbiddenCommandsFeedbackPositive"),
			 getStringFromCache("forbiddenCommandsFeedbackNegative"),
			 getStringFromCache("forbiddenCommandsFeedbackQuality"));

		taskFeedback.setRegisterFeedback
			(getStringFromCache("registerValuesFeedbackPositive"),
			 getStringFromCache("registerValuesFeedbackNegative"),
			 getStringFromCache("registerValuesFeedbackQuality"));

		taskFeedback.setMemoryFeedback
			(getStringFromCache("memoryValuesFeedbackPositive"),
			 getStringFromCache("memoryValuesFeedbackNegative"),
			 getStringFromCache("memoryValuesFeedbackQuality"));

		taskFeedback.setMemoryReferencesFeedback
			(getStringFromCache("memoryReferencesFeedbackPositive"),
			 getStringFromCache("memoryReferencesFeedbackNegative"),"");

		taskFeedback.setScreenOutputFeedback
			(getStringFromCache("screenOutputFeedbackPositive"),
			 getStringFromCache("screenOutputFeedbackNegative"),
			 getStringFromCache("screenOutputFeedbackQuality"));

		taskFeedback.setFileOutputFeedback
			(getStringFromCache("fileOutputFeedbackPositive"),
			 getStringFromCache("fileOutputFeedbackNegative"),
			 getStringFromCache("fileOutputFeedbackQuality"));

		return taskFeedback;
	}//getTaskFeedback



}//class

