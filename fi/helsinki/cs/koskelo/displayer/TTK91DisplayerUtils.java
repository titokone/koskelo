/**
 * Luokka sisältää TTK91Displayer-luokille yhteisiä apumetodeita.
 * Ne palauttavat yhteisiä html-elementtejä.
 */

package fi.helsinki.cs.koskelo.displayer;

public class TTK91DisplayerUtils {

	/**
	 * Palauttaa tehtävänannon html-muodossa.
	 *
	 * @param  task tehtävänanto 
	 * @return tehtävänanto html-muodossa
	 */

	public static String getHTMLElementTask(String task){
	
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

	public static String getHTMLElementInput(String inputHeader, String input){
	
		return new String("<p class=\"input\"><strong>" 
											+inputHeader +": " +input +"</strong></p>");
	}//getHTMLELementInput
    
	/**
	 * Palauttaa tyhjän vastauslaatikon html-muodossa.
	 *
	 * @return tyhjä vastauslaatikko html-muodossa
	 */

	public static String getHTMLElementAnswerBox(){
	
		return new String("<textarea name=\"textfield\" cols=\"50\""+ 
											"rows=\"20\"></textarea>\"<br>");
	}//getHTMLElementAnswerBox
      
	/**
	 * Palauttaa vastauslaatikon, joka sisältää vastauksen
	 *
	 * @param  answer vastaus 
	 * @return täytetty vastauslaatikko html-muodossa
	 */
 
	public static String getHTMLElementAnswerBox(String[] answer){
	
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
