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
		// verrataanko simulaatioon
		String reqCompareMethod = req.getParameter(
				"compare_method"
				);
		
		// miksi tämä on?
		String reqAcceptedSize = req.getParameter(
				"accepted_size"
				); 
		// ratkaisun suosituskoko
		String reqOptimalSize = req.getParameter(
				"optimal_size"
				); 
		// kielletyt konekäskyt
		String reqRequiredCommands = req.getParameter(
				"required_commands"
				);
		// vaaditut konekäskyt
		String reqForbiddenCommands = req.getParameter(
				"forbidden_commands"
				);
		
		// pyydetyt rekisterien arvot
		String reqRegisterValues = req.getParameter(
				"register_values"
				);
		
		//muistipaikkojen arvot
		String reqMemoryValues = req.getParameter(
				"memory_values"
				);
		
		// näytön tulosteet
		String reqScreenOutput = req.getParameter(
				"screen_output"
				); 
		
		// tulosteet tiedostoon
		String reqFileOutput = req.getParameter(
				"file_output"
				); 
		

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


		// TODO nämä omiksi privametodeikseen
		
		try { 
			// maxCommands ei voi olla null
			// TODO konffattava oletusarvo
			
			if(reqMaxCommands != null) {
			
				maxCommands = parsePostInt(reqMaxCommands);
			
			} else {
			
				maxCommands = 10000;
			
			}
			
			taskOptions.setMaxCommands(maxCommands);
		
		} catch (Exception e) { 
			// TODO lisää virheen palauttaminen
			req.getRequestDispatcher("StaticTTK91Composer.jsp")
				.forward(req, resp);
			return;
		}// catch

		try { // exampleCode
			// TODO validin TTK91-koodin tarkistaminen
			// TODO dynaamisen koodin syntaksin tarkistaminen
			// TODO dynaamisen koodin vaatiminen

			exampleCode = reqExampleCode;
			taskOptions.setExampleCode(exampleCode);
		
		} catch (Exception e) {

		}

		try { // taskDescription
			// TODO vaaditaanko?
			// TODO dynaamisen tehtävän syntaksin tarkistaminen
			// TODO dynaamisen tehtävän vertailu mallikoodiin

			taskDescription = reqTaskDescription;
			taskOptions.setTaskDescription(taskDescription);
			
		} catch (Exception e) {

		}

		try { // publicInput
			
			if(reqPublicInput != null) {
				StringTokenizer st = new StringTokenizer(
						reqPublicInput, ","
						);
				Vector tmp = new Vector();

				while(st.hasMoreTokens()){

					tmp.add(st.nextToken());
				}

				publicInput = new int[tmp.size()];


				for(int i = 0; i < tmp.size(); i++) {
				
					publicInput[i] = parsePostInt((String)tmp.get(i));

				}

				taskOptions.setPublicInput(publicInput);
			}
		} catch (Exception e) {
			// TODO lisää virheen palauttaminen
			req.getRequestDispatcher("StaticTTK91Composer.jsp")
				.forward(req, resp);
			return;
		}

		try { // hiddenInput
			if(reqHiddenInput != null) {
				StringTokenizer st = new StringTokenizer(
						reqHiddenInput, ","
						);
				Vector tmp = new Vector();

				while(st.hasMoreTokens()){

					tmp.add(st.nextToken());
				}

				hiddenInput = new int[tmp.size()];


				for(int i = 0; i < tmp.size(); i++) {
					hiddenInput[i] = parsePostInt((String)tmp.get(i));

				}

				taskOptions.setHiddenInput(hiddenInput);
			}
		} catch (Exception e) {
			// TODO lisää virheen palauttaminen
			req.getRequestDispatcher("StaticTTK91Composer.jsp")
				.forward(req, resp);
		}
		try { // compareMethod

			if(reqCompareMethod != null) {
				compareMethod = parsePostInt(reqCompareMethod);
			}

			
		} catch (Exception e) {

		}

		try { //  acceptedSize
			if(reqAcceptedSize != null) {
				acceptedSize = parsePostInt(reqAcceptedSize);
			
				taskOptions.setAcceptedSize(acceptedSize);
			
			}

		} catch (Exception e) {

		}


		try { // optimalSize
			if(reqOptimalSize != null) {
				optimalSize = parsePostInt(reqOptimalSize);
				
				taskOptions.setOptimalSize(optimalSize);
			}


			
		} catch (Exception e) {

		}

		try { // requiredCommands

			if( reqRequiredCommands != null) {

				requiredCommands = reqRequiredCommands.split(",");

				taskOptions.setRequiredCommands(requiredCommands);

			}
			
		} catch (Exception e) {

		}

		try { // forbiddenCommands

			if( reqForbiddenCommands != null) {

				forbiddenCommands = reqForbiddenCommands.split(",");

				taskOptions.setForbiddenCommands(forbiddenCommands);
			}

		} catch (Exception e) {

		}




		session.setAttribute("TTK91TaskOptions", taskOptions);


	} // doPost

	/** Not yet sure what for. 
	*/

	public void init() throws ServletException {


	} // init()


	private int parsePostInt(String s) throws Exception {

		return ((new Integer(s)).intValue());
		
	}
	
	/** Servletin oma sisäinen apumetodi. Tätä hyödynnetään
	 * doPostin parsiessa kriteereitä erilleen saamastaan 
	 * HttpRequestista.
	 */

	private String[] parsePostText(String s) {

	}//parsePostText


} //class
