import javax.servlet.*;

public class TTK91SyntaxChecker extends javax.servlet.HttpServlet{

	/** Calls doPost with its parameters. Here for compability.
	 *
	 */
	protected void doGet(
			HttpServletRequest req,
			HttpServletResponse resp
			) throws ServletException, java.io.IOException {

		doPost(req, resp);
	}// doGet
	
	/** Does the thing. Decides whether to redirect user back
	 * to give criteria again or forward to give feedback.
	 */

	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse resp
			) throws ServletException, java.io.IOException {


		// Nämä muuttujat ovat edellisen sivun inputin 
		// lukemsita varten parametrista req.
		// Jokainen arvo tarkistetaan siten että katsotaan onko
		// sen tyyppi ja syntaksi oikein.
		
		private String maxCommands; // LoopDetector on vaan huono nimi
		private String exampleCode; // malliratkaisu
		private String taskDescription; // tehtävänantoteksti
		private String publicInput; // julkiset syötteet
		private String hiddenInput; // piilotetut syötteet
		private String compareMethdod; // verrataanko simulaatioon
		private String acceptedSize; // miksi tämä on?
		private String optimalSize; // ratkaisun suosituskoko
		private String allowedCommands; // saallitut konekäskyt
		private String registerValues; // pyydetyt rekisterien arvot
		private String memoryValues; // pyydetyt muistipaikkojen arvot
		private String screenOutput; // näytön tulosteet
		private String fileOutput; // tulosteet tiedostoon
		

		
	
	
	
	} // doPost

	/** Not yet sure what for. Anyhow, overriden instead of 
	 * init(ServletConfig) because java api told to do it this way.
	 * Will be called automatically
	 */
	public void init() throws ServletException {

	
	} // init()

	private String[] parsePostText(String s) {

	}//parsePostText

	
} //class
