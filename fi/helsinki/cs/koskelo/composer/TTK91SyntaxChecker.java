import javax.servlet.*;
import java.util.Vector;

public class TTK91SyntaxChecker extends javax.servlet.HttpServlet{

	/** Kutsuu doPostia parametreillaan. Yhteensopivuuden vuoksi.
	 *
	 */
	protected void doGet(
			HttpServletRequest req,
			HttpServletResponse resp
			) throws ServletException, java.io.IOException {

		doPost(req, resp);
	}// doGet
	
	/** Toteuttaa keskeisen osan luokan toiminnoista, eli tarkistaa
	 * syötettyjen kriteerien syntaksin ja generoi uuden sivun, jolla
	 * voi syöttää palautteen. Jos jonkin kriteerin syntaksi on 
	 * virheellinen, sivu palaa takaisin edelliselle sivulle ja antaa
	 * virheilmoituksen.
	 */

	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse resp
			) throws ServletException, java.io.IOException {


		// Nämä muuttujat ovat edellisen sivun inputin 
		// lukemsita varten parametrista req.
		// Jokainen arvo tarkistetaan siten että katsotaan onko
		// sen tyyppi ja syntaksi oikein.
		
		private String reqMaxCommands; // suorit. konekäskyjen lkm
		private String reqExampleCode; // malliratkaisu
		private String reqTaskDescription; // tehtävänantoteksti
		private String reqPublicInput; // julkiset syötteet
		private String reqHiddenInput; // piilotetut syötteet
		private String reqCompareMethod; // verrataanko simulaatioon
		private String reqAcceptedSize; // miksi tämä on?
		private String reqOptimalSize; // ratkaisun suosituskoko
		private String reqCommands; //konekäskyt
		private String reqRegisterValues; // pyydetyt rekisterien arvot
		private String reqMemoryValues; //muistipaikkojen arvot
		private String reqScreenOutput; // näytön tulosteet
		private String reqFileOutput; // tulosteet tiedostoon
		
		private int maxCommands;
		private String ExampleCode; //TODO tarkista koodin kääntyminen
		private Strking taskDescription; // tarviiko tarkistaa?
		private int[] publicInput; // 1,2,3,4... 
		private int[] hiddenInput; // 1,2,3,5,2...
		private int compareMethod; // 0 = static, 1 = simuloitu
		private int acceptedSize; // 200 riviä
		private int optimalSize; // 10 riviä
		private String[] requiredCommands; // JUMP
		private String[] forbiddenCommands; // EQU
		private TTK91TaskCriteria[] registerCriteria; // R2 > 1
		private TTK91TaskCriteria[] memoryCriteria; // Mikko < Ville
		private int[][] screenOutput; // (1,3) (2,4) (4,3)
		private int[][] fileOutput; // (1,3)(2,4)(4,3)

		// TODO yritetään saada parsittua saatu data
		try { // maxCommands
		
			maxCommands = Integer.parseint(
					req.getParameter(maxCommands);
					);
			
		} catch (Exception e) { //Jokin kentistä oli virheellinen
			// TODO lisää virheen palauttaminen
			req.getRequestDispatcher("TTK91Composer.jsp")
				.forward(req, resp);
		}// catch

	} // doPost

	/** Not yet sure what for. 
	*/

	public void init() throws ServletException {


	} // init()

	/** Servletin oma sisäinen apumetodi. Tätä hyödynnetään
	 * doPostin parsiessa kriteereitä erilleen saamastaan 
	 * HttpRequestista.
	 */
	
	private String[] parsePostText(String s) {

	}//parsePostText

	
} //class
