import javax.servlet.*;
import java.util.Vector;
import java.util.StringTokenizer;
import fi.helsinki.cs.koskelo.common.*;

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


		TTK91TaskOptions taskOptions = new TTK91TaskOptions();
		
		// Nämä muuttujat ovat edellisen sivun inputin 
		// lukemista varten parametrista req.
		// Jokainen arvo tarkistetaan siten että katsotaan onko
		// sen tyyppi ja syntaksi oikein.

		// suoritettavien konekäskyjen maksimimäärä
		String reqMaxCommands = req.getParameter(
				"max_commands"
				);
		// malliratkaisu
		String reqExampleCode;
		// tehtävänanto
		String reqTaskDescription;
		// julkiset syötteet
		String reqPublicInput = req.getParameter(
				"public_input"
				);
		// piilotetut syötteet
		String reqHiddenInput = req.getParameter(
				"hidden_input"
				);
		String reqCompareMethod; // verrataanko simulaatioon
		String reqAcceptedSize; // miksi tämä on?
		String reqOptimalSize; // ratkaisun suosituskoko
		String reqCommands; //konekäskyt
		String reqRegisterValues; // pyydetyt rekisterien arvot
		String reqMemoryValues; //muistipaikkojen arvot
		String reqScreenOutput; // näytön tulosteet
		String reqFileOutput; // tulosteet tiedostoon

		int maxCommands;
		String exampleCode; //TODO tarkista koodin kääntyminen
		String taskDescription; // tarviiko tarkistaa?
		int[] publicInput; 
		int[] hiddenInput; // 1,2,3,5,2...
		int compareMethod; // 0 = static, 1 = simuloitu
		int acceptedSize; // 200 riviä
		int optimalSize; // 10 riviä
		String[] requiredCommands; // JUMP
		String[] forbiddenCommands; // EQU
		TTK91TaskCriteria[] registerCriteria; // R2 > 1
		TTK91TaskCriteria[] memoryCriteria; // Mikko < Ville
		int[][] screenOutput; // (1,3) (2,4) (4,3)
		int[][] fileOutput; // (1,3)(2,4)(4,3)


		// TODO checking of session, but no need for new one
		
		HttpSession session = req.getSession(false);
		
		// TODO yritetään saada parsittua saatu data
		try { // maxCommands
			maxCommands = (new Integer(reqHiddenInput))
				.intValue();
			taskOptions.setMaxCommands(maxCommands);
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

				tmp.add(new Integer(st.nextToken()));
			}

			publicInput = new int[tmp.size()];


			for(int i = 0; i < tmp.size(); i++) {
				publicInput[i] = ((Integer)tmp.get(i)).intValue();

			}

			taskOptions.setPublicInput(publicInput);

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

				tmp.add(new Integer(st.nextToken()));
			}

			hiddenInput = new int[tmp.size()];


			for(int i = 0; i < tmp.size(); i++) {
				hiddenInput[i] = ((Integer)(tmp.get(i))).intValue();

			}
			
			taskOptions.setHiddenInput(hiddenInput);

		} catch (Exception e) {
			// TODO lisää virheen palauttaminen
			req.getRequestDispatcher("StaticTTK91Composer.jsp")
				.forward(req, resp);
			return;

		}

		try { // compareMethod

		} catch (Exception e) {

		}

		session.setAttribute("TTK91TaskOptions", taskOptions);


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
