/**
 * Luokka sis�lt�� TTK91Displayer-luokille yhteisi� apumetodeita.
 * Ne palauttavat yhteisi� html-elementtej�.
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
	 * @return tyhj� vastauslaatikko html-muodossa
	 */

	public static String getHTMLElementAnswerBox(){
	
		return new String("<textarea name=\"answer\" cols=\"50\""+ 
											"rows=\"20\"></textarea>\"<br>");
	}//getHTMLElementAnswerBox
      
	/**
	 * Palauttaa vastauslaatikon, joka sis�lt�� vastauksen
	 *
	 * @param  answer vastaus 
	 * @return t�ytetty vastauslaatikko html-muodossa
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
