/**
 * Luokka sisältää TTK91Displayer-luokille yhteisiä apumetodeita.
 * Ne palauttavat yhteisiä html-elementtejä.
 * @author Tom Bertell
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
	 * @param  rows vastaslaatikon korkeus
	 * @param  cols vastauslaatikon leveys
	 * @return tyhjä vastauslaatikko html-muodossa
	 */

	public static String getHTMLElementAnswerBox(int rows, int cols){
	
		return new String("<textarea name=\"answer\" cols=\"" +cols +"\"" + 
											" rows=\"" +rows +"\"></textarea><br>");
	}//getHTMLElementAnswerBox
      
	/**
	 * Palauttaa vastauslaatikon, joka sisältää vastauksen
	 *
	 * @param  answer vastaus
	 * @param  rows vastaslaatikon korkeus
	 * @param  cols vastauslaatikon leveys
	 * @return täytetty vastauslaatikko html-muodossa
	 */
 
	public static String getHTMLElementAnswerBox(String[] answer, int rows, 
																							 int cols){
	
		StringBuffer answerbox = new StringBuffer();

		answerbox.append("<textarea name=\"answer\" cols=\"" +cols +"\""+ 
										 " rows=\"" +rows +"\">");

		answerbox.append(answer[0]);
		answerbox.append("</textarea><br>");
		return new String(answerbox);
	}//getHTMLElementAnswerBox

}//class
