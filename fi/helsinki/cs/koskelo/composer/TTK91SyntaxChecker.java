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
	 * sy�tettyjen kriteerien syntaksin ja generoi uuden sivun, jolla
	 * voi sy�tt�� palautteen. Jos jonkin kriteerin syntaksi on 
	 * virheellinen, sivu palaa takaisin edelliselle sivulle ja antaa
	 * virheilmoituksen.
	 */

	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse resp
			) throws ServletException, java.io.IOException {


		// N�m� muuttujat ovat edellisen sivun inputin 
		// lukemsita varten parametrista req.
		// Jokainen arvo tarkistetaan siten ett� katsotaan onko
		// sen tyyppi ja syntaksi oikein.
		
		private String reqMaxCommands; // suorit. konek�skyjen lkm
		private String reqExampleCode; // malliratkaisu
		private String reqTaskDescription; // teht�v�nantoteksti
		private String reqPublicInput; // julkiset sy�tteet
		private String reqHiddenInput; // piilotetut sy�tteet
		private String reqCompareMethod; // verrataanko simulaatioon
		private String reqAcceptedSize; // miksi t�m� on?
		private String reqOptimalSize; // ratkaisun suosituskoko
		private String reqCommands; //konek�skyt
		private String reqRegisterValues; // pyydetyt rekisterien arvot
		private String reqMemoryValues; //muistipaikkojen arvot
		private String reqScreenOutput; // n�yt�n tulosteet
		private String reqFileOutput; // tulosteet tiedostoon
		
		private int maxCommands;
		private String ExampleCode; //TODO tarkista koodin k��ntyminen
		private Strking taskDescription; // tarviiko tarkistaa?
		private int[] publicInput; // 1,2,3,4... 
		private int[] hiddenInput; // 1,2,3,5,2...
		private int compareMethod; // 0 = static, 1 = simuloitu
		private int acceptedSize; // 200 rivi�
		private int optimalSize; // 10 rivi�
		private String[] requiredCommands; // JUMP
		private String[] forbiddenCommands; // EQU
		private TTK91TaskCriteria[] registerCriteria; // R2 > 1
		private TTK91TaskCriteria[] memoryCriteria; // Mikko < Ville
		private int[][] screenOutput; // (1,3) (2,4) (4,3)
		private int[][] fileOutput; // (1,3)(2,4)(4,3)

		// TODO yritet��n saada parsittua saatu data
		try { // maxCommands
		
			maxCommands = Integer.parseint(
					req.getParameter(maxCommands);
					);
			
		} catch (Exception e) { //Jokin kentist� oli virheellinen
			// TODO lis�� virheen palauttaminen
			req.getRequestDispatcher("TTK91Composer.jsp")
				.forward(req, resp);
		}// catch

	} // doPost

	/** Not yet sure what for. 
	*/

	public void init() throws ServletException {


	} // init()

	/** Servletin oma sis�inen apumetodi. T�t� hy�dynnet��n
	 * doPostin parsiessa kriteereit� erilleen saamastaan 
	 * HttpRequestista.
	 */
	
	private String[] parsePostText(String s) {

	}//parsePostText

	
} //class
