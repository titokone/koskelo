/**
 * Luokka, joka huolehtii staattisten tehtävien
 * html-koodin tuottamisesta.
 */

package fi.helsinki.cs.koskelo.displayer;

import fi.hy.eassari.showtask.trainer.CacheException;

public class StaticTTK91Displayer extends CommonDisplayer{
    
	/**
	 * Tekee tehtävän tiedoista html-koodia ja palauttaa 
	 * html-koodin String-oliona. Kutsutaan DisplayerServlet2- ja
	 * Answer2-servleteistä.
	 *
	 * @param  initval tehtävän vastaus
	 * @param  params  generoidut parametrit
	 * @param  hiddens lomakkeen piilokentät String-oliona.
	 * @param  allowTry kertoo tuleeko sivun loppuun vastauspainike  
	 * @return tehtävän esitys html-muodossa
	 * @throws CacheException jos tulee ongelmia attribuuttien 
	 *                        hakemisessa AttributeCachesta
	 */
	public String getSetting(String [] initVal, 
													 String params, 
													 String hiddens,  
													 boolean allowTry) throws CacheException{

		String taskDescription; // tehtävänanto
		String input;           // syötteet
		String inputHeader;     // syötteen otsikko
       
		final String TARGETSERVLET = "Answer2.do2"; // kohdeservletin nimi
       
		StringBuffer setting = new StringBuffer(); // kerätään palautettava htlm-sivu
	  
		// Lisätään piilokenttä tehtäväntyypille
		
		hiddens += ("<input type=\"hidden\" name=\"tasktype\"" 
								+"value=\"staticttk91task\">");

		// Haetaan tehtävänanto, syötteet ja syötteen otsikko.
	
		taskDescription = 
			cache.getAttribute("T", taskid, "taskDescription", language);
		input = 
			cache.getAttribute("T", taskid, "publicInput", language);
		inputHeader = 
			cache.getAttribute("D", "staticttk91taskdisplayer", 
												 "inputHeader", language);
	
		// Lomake alkaa
	
		setting.append("<form action=" + TARGETSERVLET + 
									 " method=\"post\" name=\"answer\"" +
									 "id=\"answer\">");
	
       
		// Lisätään tehtävänanto ja syötteet

		setting.append(TTK91DisplayerUtils.getHTMLElementTask(taskDescription));
		setting.append(TTK91DisplayerUtils.getHTMLElementInput(inputHeader, input));

		/** 
		 * Lisätään vastauslaatikko. Jos parametri initVal on null,
		 * luodann tyhjä tehtävälaatikko, muutoin opiskelijan vastaus 
		 * laitetaan laatikkoon.
		 */

		if (initVal == null) {
			setting.append(TTK91DisplayerUtils.getHTMLElementAnswerBox());
		} else {
			setting.append(TTK91DisplayerUtils.getHTMLElementAnswerBox(initVal));
		}

		// Lisätäänn analyserin tarvitsemat piilokentät lomakkeeseen
	
		setting.append(hiddens);
	
		// Jos tehtävän lähetys sallitaan, luodaan lähetä-nappi
	
		if (allowTry) {
			setting.append(super.getButton());
		}
       

		// Lomake loppuu

		setting.append("</form>");
		return new String(setting);

	}//getSetting
    
	/**
	 * Palauttaa tehtävänannon html-muodossa.
	 *
	 * @param  task tehtävänanto 
	 * @return tehtävänanto html-muodossa
	 */

	//	private String getHTMLElementTask(String task){
	
	//		return new String("<p class=\"assignment\"><strong>" 
	//									+task +"</strong></p>");
	//}//getHTMLElementTask

	/**
	 * Palauttaa syötteet html-muodossa.
	 *
	 * @param inputText kieliriippuvainen syöteteksti esim. suomeksi syötteet 
	 *        -> englanniksi input jne.
	 * @param input varsinaiset syötteet                     
	 * @return syötteet html-muodossa
	 */

	//private String getHTMLElementInput(String inputHeader, String input){
	
//return new String("<p class=\"input\"><strong>" 
//										+inputHeader +": " +input +"</strong></p>");
//}//getHTMLELementInput
    
	/**
	 * Palauttaa tyhjän vastauslaatikon html-muodossa.
	 *
	 * @return tyhjä vastauslaatikko html-muodossa
	 */

	//private String getHTMLElementAnswerBox(){
	//
	//	return new String("<textarea name=\"textfield\" cols=\"50\""+ 
	//										"rows=\"20\"></textarea>\"<br>");
	//}//getHTMLElementAnswerBox
      
	/**
	 * Palauttaa vastauslaatikon, joka sisältää vastauksen
	 *
	 * @param  answer vastaus 
	 * @return täytetty vastauslaatikko html-muodossa
	 */
 
	//private String getHTMLElementAnswerBox(String[] answer){
	
//StringBuffer answerbox = new StringBuffer();

//	answerbox.append("<textarea name=\"textfield\" cols=\"50\""+ 
//									 "rows=\"20\">");
//
//	for(int i = 0; i < answer.length; i++){
//		answerbox.append(answer[i] +"\n");
//	}
	
//	answerbox.append("</textarea><br>");
//	return new String(answerbox);
//}//getHTMLElementAnswerBox
}//class
