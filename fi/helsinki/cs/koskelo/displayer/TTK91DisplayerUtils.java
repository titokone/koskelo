/**
 * Luokka sis�lt�� TTK91Displayer-luokille yhteisi� apumetodeita.
 * Ne palauttavat yhteisi� html-elementtej�.
 * @author Tom Bertell
 */

package fi.helsinki.cs.koskelo.displayer;

public class TTK91DisplayerUtils {

	/**
	 * Palauttaa teht�v�nannon html-muodossa.
	 *
	 * @param  task teht�v�nanto 
	 * @return teht�v�nanto html-muodossa
	 */

	public static String getHTMLElementTask(String task){
	
		return new String("<p class=\"assignment\"><strong>" 
											+task +"</strong></p>");
	}//getHTMLElementTask

	/**
	 * Palauttaa sy�tteet html-muodossa.
	 *
	 * @param inputText kieliriippuvainen sy�teteksti esim. suomeksi sy�tteet 
	 *        -> englanniksi input jne.
	 * @param input varsinaiset sy�tteet                     
	 * @return sy�tteet html-muodossa
	 */

	public static String getHTMLElementInput(String inputHeader, String input){
	
		return new String("<p class=\"input\"><strong>" 
											+inputHeader +": " +input +"</strong></p>");
	}//getHTMLELementInput
    
	/**
	 * Palauttaa tyhj�n vastauslaatikon html-muodossa.
	 *
	 * @param  rows vastaslaatikon korkeus
	 * @param  cols vastauslaatikon leveys
	 * @return tyhj� vastauslaatikko html-muodossa
	 */

	public static String getHTMLElementAnswerBox(int rows, int cols){
	
		return new String("<textarea name=\"answer\" cols=\"" +cols +"\"" + 
											"rows=\"" +rows +"\"></textarea><br>");
	}//getHTMLElementAnswerBox
      
	/**
	 * Palauttaa vastauslaatikon, joka sis�lt�� vastauksen
	 *
	 * @param  answer vastaus
	 * @param  rows vastaslaatikon korkeus
	 * @param  cols vastauslaatikon leveys
	 * @return t�ytetty vastauslaatikko html-muodossa
	 */
 
	public static String getHTMLElementAnswerBox(String[] answer, int rows, 
																							 int cols){
	
		StringBuffer answerbox = new StringBuffer();

		answerbox.append("<textarea name=\"answer\" cols=\"" +cols +"\""+ 
										 "rows=\"" +rows +"\">");

		answerbox.append(answer[0]);
		answerbox.append("</textarea><br>");
		return new String(answerbox);
	}//getHTMLElementAnswerBox

}//class
