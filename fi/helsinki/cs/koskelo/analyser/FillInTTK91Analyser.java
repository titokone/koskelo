package fi.helsinki.cs.koskelo.analyser;

import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CacheException;
import fi.hy.eassari.showtask.trainer.Feedback;

import fi.helsinki.cs.koskelo.common.TTK91Constant;

/**
 * Luokka TTK-91 -t‰ydennysteht‰vien vastauksien tarkastamiseen
 * @author Tom Bertell, Koskelo-projekti
 *  
 */

public class FillInTTK91Analyser extends StaticTTK91Analyser {

	private AttributeCache cache; 
  private String taskID;
  private String language;
	private String initparams;


	public FillInTTK91Analyser() {
		this.cache = null;
    this.taskID = null;
    this.language = null;
    this.initparams = null;
	}

	
	/**
	 * Alustaa alustamattoman FillInTTK91Analyserin
	 * @param taskid teht‰v‰tunnus
	 * @param language kielikoodi
	 * @param initparams alustusparametrit
	 */
  public void init(String taskid, String language, String initparams) {
    this.taskID = taskid;
    this.language = language;
		this.initparams = initparams;
		super.init(taskid, language, initparams);
   
  } // init

	/**
	 * Kokoaa opiskelijan vastauksen ja malliratkaisun perusteella 
	 * TTK91-kielisen ohjelman. Kutsuu yl‰luokan StaticTTK91Analyser 
	 * analyse-metodia,joka tekee varsinaisen analysoinnin.
	 * @param answer TTK91-kielinen l‰hdekoodi
	 * @param params valinnaisia parametreja
	 * @return palaute
	 */

	public Feedback analyse(String[] answer, String params) {
		
		// Esimerkkikoodi sis‰lt‰‰ aukon paikan "[" ja "]" merkein erotettuna.

		String[] exampleCode = super.fetchExampleCode();

		if (exampleCode != null) {
			int indexBefore = exampleCode[0].indexOf("[");
			int indexAfter = exampleCode[0].indexOf("]");

			String before = exampleCode[0].substring(0, indexBefore);
			String after = exampleCode[0].substring(indexAfter+1);

			String[] answerAll = {before +answer[0] +after};
		
			return (super.analyse(answerAll, params));
		} else {
			return (new Feedback(TTK91Constant.ERROR, 
													 "Esimerkkikoodia ei saatu haettua."));
		}
	}

	/**
   * Apumetodi, joka noutaa malliratkaisun taskoptionsista. Metodi
   * poistaa malliratkaisusta "[" ja "]" merkit.
   * @return esimerkkikoodi
   */
  
  protected String[] fetchExampleCode() {

		String[] exampleCode = super.fetchExampleCode();
		if (exampleCode == null)
			return null;
	
		int indexBefore = exampleCode[0].indexOf("[");
		int indexAfter = exampleCode[0].indexOf("]");

		String before = exampleCode[0].substring(0, indexBefore);
		String middle = exampleCode[0].substring(indexBefore+1, indexAfter);
		String after = exampleCode[0].substring(indexAfter+1);

		String[] parsedExampleCode = {before +middle +after};

		return parsedExampleCode;
		
	} // fetchExampleCode

	
  public void registerCache(AttributeCache c) {
    this.cache = c;
		super.registerCache(c);
  } // registerCache
	
}//class
