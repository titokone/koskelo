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


		// N�m� muuttujat ovat edellisen sivun inputin 
		// lukemsita varten parametrista req.
		// Jokainen arvo tarkistetaan siten ett� katsotaan onko
		// sen tyyppi ja syntaksi oikein.
		
		private String maxCommands; // LoopDetector on vaan huono nimi
		private String exampleCode; // malliratkaisu
		private String taskDescription; // teht�v�nantoteksti
		private String publicInput; // julkiset sy�tteet
		private String hiddenInput; // piilotetut sy�tteet
		private String compareMethdod; // verrataanko simulaatioon
		private String acceptedSize; // miksi t�m� on?
		private String optimalSize; // ratkaisun suosituskoko
		private String allowedCommands; // saallitut konek�skyt
		private String registerValues; // pyydetyt rekisterien arvot
		private String memoryValues; // pyydetyt muistipaikkojen arvot
		private String screenOutput; // n�yt�n tulosteet
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
