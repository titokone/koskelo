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

		// suoritettavien konekäskyjen maksimimäärä
		private String reqMaxCommands = req.getParameter(
				"max_commands"
				);
		// malliratkaisu
		private String reqExampleCode;
		// tehtävänanto
		private String reqTaskDescription;
		// julkiset syötteet
		private String reqPublicInput = req.getParameter(
				"public_input"
				);
		// piilotetut syötteet
		private String reqHiddenInput = req.getParameter(
				"hidden_input"
				);
		private String reqCompareMethod; // verrataanko simulaatioon
		private String reqAcceptedSize; // miksi tämä on?
		private String reqOptimalSize; // ratkaisun suosituskoko
		private String reqCommands; //konekäskyt
		private String reqRegisterValues; // pyydetyt rekisterien arvot
		private String reqMemoryValues; //muistipaikkojen arvot
		private String reqScreenOutput; // näytön tulosteet
		private String reqFileOutput; // tulosteet tiedostoon

		private int maxCommands;
		private String exampleCode; //TODO tarkista koodin kääntyminen
		private Strking taskDescription; // tarviiko tarkistaa?
		private int[] publicInput; 
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
			maxCommands = (Integer.parseint(reqHiddenInput))
				.getValue();

		} catch (Exception e) { 
			// TODO lisää virheen palauttaminen
			req.getRequestDispatcher("StaticTTK91Composer.jsp")
				.forward(req, resp);
			return;
		}// catch

		try { // exampleCode

		} catch (Exception e) {

		}

		try { // taskDescription

		} catch (Exception e) {

		}

		try { // publicInput

			StringTokenizer st = new StringTokenizer(
					reqPublicInput, ","
					);
			Vector tmp = new Vector();

			while(st.hasMoreTokens()){

				tmp.add(Integer.parseInt(st.nextToken()));
			}

			publicInput = new int[tmp.length()];


			for(int i = 0; i < tmp.length(); i++) {
				publicInput[i] = (tmp.get(i)).intValue();

			}

		} catch (Exception e) {
			// TODO lisää virheen palauttaminen
			req.getRequestDispatcher("StaticTTK91Composer.jsp")
				.forward(req, resp);
			return;
		}

		try { // hiddenInput
			StringTokenizer st = new StringTokenizer(
					reqHiddenInput, ","
					);
			Vector tmp = new Vector();

			while(st.hasMoreTokens()){

				tmp.add(Integer.parseInt(st.nextToken()));
			}

			hiddenInput = new int[tmp.length()];


			for(int i = 0; i < tmp.length(); i++) {
				hiddenInput[i] = (tmp.get(i)).intValue();

			}


		} catch (Exception e) {
			// TODO lisää virheen palauttaminen
			req.getRequestDispatcher("StaticTTK91Composer.jsp")
				.forward(req, resp);
			return;

		}

		try { // compareMethod

		} catch (Exception e) {

		}



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
