/**
 * Luokka, joka huolehtii staattisten tehtävien
 * html-koodin tuottamisesta.
 */

package fi.helsinki.cs.koskelo.displayer;

import fi.hy.eassari.showtask.trainer.CacheException;
//import fi.hy.eassari.displayers.*;

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
       
		/**
		 * Servletin nimi, joka käynnistetäänn lomakkeen
		 * täyttämisen jälkeen.
		 */
		String targetServlet = "Answer2.do2";
       
		// StrinBufferiin kerätään palautettava html-sivu
	
		StringBuffer setting = new StringBuffer();
	   
		hiddens += ("<input type=\"hidden\" name=\"tasktype\"" 
								+"value=\"staticttk91task\">");

		// Haetaan tehtävänanto, syötteet ja syötteen otsikko.
	
		String taskDescription = 
			cache.getAttribute("T", taskid, "taskDescription", language);
		String input = 
			cache.getAttribute("T", taskid, "publicInput", language);

		String inputHeader = 
			cache.getAttribute("D", "staticttk91taskdisplayer", 
												 "inputHeader", language);
	
		// Lomake alkaa
	
		setting.append("<form action=" + targetServlet + 
									 " method=\"post\" name=\"staticttk91task\"" +
									 "id=\"staticttk91task\">");
	
       
		// Lisätään tehtävänanto ja syötteet

		setting.append(getHTMLElementTask(taskDescription));
		setting.append(getHTMLElementInput(inputHeader, input));

		/** 
		 * Lisätään vastauslaatikko. Jos parametri initVal on null,
		 * näytetään opiskelijalle tyhjä tehtävälaatikko,
		 * muutoin opiskelijan vastaus laitetaan laatikkoon.
		 */

		if (initVal == null) {
			setting.append(getHTMLElementAnswerBox());
		} else {
			setting.append(getHTMLElementAnswerBox(initVal));
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

	private String getHTMLElementTask(String task){
	
		return new String("<p class=\"assignment\"><strong>" 
											+task +"</strong></p>");
	}//getHTMLElementTask

	/**
	 * Palauttaa syötteet html-muodossa.
	 *
	 * @param inputText kieliriippuvainen syöteteksti esim. suomeksi syötteet 
	 *        -> englanniksi input jne.
	 * @param input varsinaiset syötteet                     
	 * @return syötteet html-muodossa
	 */

	private String getHTMLElementInput(String inputHeader, String input){
	
		return new String("<p class=\"input\"><strong>" 
											+inputHeader +": " +input +"</strong></p>");
	}//getHTMLELementInput
    
	/**
	 * Palauttaa tyhjän vastauslaatikon html-muodossa.
	 *
	 * @return tyhjä vastauslaatikko html-muodossa
	 */

	private String getHTMLElementAnswerBox(){
	
		return new String("<textarea name=\"textfield\" cols=\"50\""+ 
											"rows=\"20\"></textarea>\"<br>");
	}//getHTMLElementAnswerBox
      
	/**
	 * Palauttaa vastauslaatikon, joka sisältää vastauksen
	 *
	 * @param  answer vastaus 
	 * @return täytetty vastauslaatikko html-muodossa
	 */
 
	private String getHTMLElementAnswerBox(String[] answer){
	
		StringBuffer answerbox = new StringBuffer();

		answerbox.append("<textarea name=\"textfield\" cols=\"50\""+ 
										 "rows=\"20\">");

		for(int i = 0; i < answer.length; i++){
			answerbox.append(answer[i] +"\n");
		}
	
		answerbox.append("</textarea><br>");
		return new String(answerbox);
	}//getHTMLElementAnswerBox
}//class
