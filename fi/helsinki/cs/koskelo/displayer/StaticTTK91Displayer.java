/**
 * Luokka, joka huolehtii staattisten teht�vien
 * html-koodin tuottamisesta.
 */

package fi.helsinki.cs.koskelo.displayer;

import fi.hy.eassari.showtask.trainer.CacheException;

public class StaticTTK91Displayer extends CommonDisplayer{
    
	/**
	 * Tekee teht�v�n tiedoista html-koodia ja palauttaa 
	 * html-koodin String-oliona. Kutsutaan DisplayerServlet2- ja
	 * Answer2-servleteist�.
	 *
	 * @param  initval teht�v�n vastaus
	 * @param  params  generoidut parametrit
	 * @param  hiddens lomakkeen piilokent�t String-oliona.
	 * @param  allowTry kertoo tuleeko sivun loppuun vastauspainike  
	 * @return teht�v�n esitys html-muodossa
	 * @throws CacheException jos tulee ongelmia attribuuttien 
	 *                        hakemisessa AttributeCachesta
	 */
	public String getSetting(String [] initVal, 
													 String params, 
													 String hiddens,  
													 boolean allowTry) throws CacheException{

		String taskDescription; // teht�v�nanto
		String input;           // sy�tteet
		String inputHeader;     // sy�tteen otsikko
       
		final String TARGETSERVLET = "Answer2.do2"; // kohdeservletin nimi
       
		StringBuffer setting = new StringBuffer(); // ker�t��n palautettava htlm-sivu
	  
		// Lis�t��n piilokentt� teht�v�ntyypille
		
		hiddens += ("<input type=\"hidden\" name=\"tasktype\"" 
								+"value=\"staticttk91task\">");

		// Haetaan teht�v�nanto, sy�tteet ja sy�tteen otsikko.
	
		taskDescription = 
			cache.getAttribute("T", taskid, "taskDescription", language);
		input = 
			cache.getAttribute("T", taskid, "publicInput", language);
		inputHeader = 
			cache.getAttribute("D", "staticttk91taskdisplayer", 
												 "inputHeader", language);
	
		// Lomake alkaa
	
		setting.append("<form action=" + TARGETSERVLET + 
									 " method=\"post\" name=\"staticttk91task\"" +
									 "id=\"staticttk91task\">");
	
       
		// Lis�t��n teht�v�nanto ja sy�tteet

		setting.append(TTK91DisplayerUtils.getHTMLElementTask(taskDescription));
		setting.append(TTK91DisplayerUtils.getHTMLElementInput(inputHeader, input));

		/** 
		 * Lis�t��n vastauslaatikko. Jos parametri initVal on null,
		 * luodann tyhj� teht�v�laatikko, muutoin opiskelijan vastaus 
		 * laitetaan laatikkoon.
		 */

		if (initVal == null) {
			setting.append(TTK91DisplayerUtils.getHTMLElementAnswerBox());
		} else {
			setting.append(TTK91DisplayerUtils.getHTMLElementAnswerBox(initVal));
		}

		// Lis�t��nn analyserin tarvitsemat piilokent�t lomakkeeseen
	
		setting.append(hiddens);
	
		// Jos teht�v�n l�hetys sallitaan, luodaan l�het�-nappi
	
		if (allowTry) {
			setting.append(super.getButton());
		}
       

		// Lomake loppuu

		setting.append("</form>");
		return new String(setting);

	}//getSetting
    
	/**
	 * Palauttaa teht�v�nannon html-muodossa.
	 *
	 * @param  task teht�v�nanto 
	 * @return teht�v�nanto html-muodossa
	 */

	//	private String getHTMLElementTask(String task){
	
	//		return new String("<p class=\"assignment\"><strong>" 
	//									+task +"</strong></p>");
	//}//getHTMLElementTask

	/**
	 * Palauttaa sy�tteet html-muodossa.
	 *
	 * @param inputText kieliriippuvainen sy�teteksti esim. suomeksi sy�tteet 
	 *        -> englanniksi input jne.
	 * @param input varsinaiset sy�tteet                     
	 * @return sy�tteet html-muodossa
	 */

	//private String getHTMLElementInput(String inputHeader, String input){
	
//return new String("<p class=\"input\"><strong>" 
//										+inputHeader +": " +input +"</strong></p>");
//}//getHTMLELementInput
    
	/**
	 * Palauttaa tyhj�n vastauslaatikon html-muodossa.
	 *
	 * @return tyhj� vastauslaatikko html-muodossa
	 */

	//private String getHTMLElementAnswerBox(){
	//
	//	return new String("<textarea name=\"textfield\" cols=\"50\""+ 
	//										"rows=\"20\"></textarea>\"<br>");
	//}//getHTMLElementAnswerBox
      
	/**
	 * Palauttaa vastauslaatikon, joka sis�lt�� vastauksen
	 *
	 * @param  answer vastaus 
	 * @return t�ytetty vastauslaatikko html-muodossa
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
