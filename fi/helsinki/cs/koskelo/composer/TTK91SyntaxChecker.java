package fi.helsinki.cs.koskelo.composer;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;
import java.util.StringTokenizer;
import fi.helsinki.cs.koskelo.common.*;

public class TTK91SyntaxChecker extends HttpServlet {

	private String staticResponse = "http://db.cs.helsinki.fi/tomcat/tkt_kos/assari/jsp/StaticTTK91Composer.jsp";
	private HttpServletRequest req;
	private HttpServletResponse res;
	private HttpSession session;
	
	/** Kutsuu doPostia parametreillaan. Yhteensopivuuden vuoksi.
	 *
	 */
	protected void doGet(
			HttpServletRequest req,
			HttpServletResponse res
			) throws ServletException, java.io.IOException {

		doPost(req, res);
	}// doGet

	/** Toteuttaa keskeisen osan luokan toiminnoista, eli tarkistaa
	 * syötettyjen kriteerien syntaksin ja generoi uuden sivun, jolla
	 * voi syöttää palautteen. Jos jonkin kriteerin syntaksi on
	 * virheellinen, sivu palaa takaisin edelliselle sivulle ja antaa
	 * virheilmoituksen.
	 */

	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse res
			) throws ServletException, java.io.IOException {
		// TODO kunnollinen javadoc näistä muuttujista
		// Nämä muuttuja ovat ne jotka tungetaan
		// sitten siihen TaskOptionsiin.
		// Ettei sitten tehdä sitä muunnosta siinä set-metodissa.

		this.req = req;
		this.res = res;
		
		String exampleCode; //TODO tarkista koodin kääntyminen
		String taskDescription; // tarviiko tarkistaa?
		String[] requiredCommands; // JUMP
		String[] forbiddenCommands; // EQU
		int compareMethod; // 0 = static, 1 = simuloitu
		int maxCommands;
		int acceptedSize; // 200 riviä
		int optimalSize; // 10 riviä
		int memoryReferences;
		int[][] screenOutput; // (1,3) (2,4) (4,3)
		int[][] fileOutput; // (1,3)(2,4)(4,3)
		int[] publicInput;
		int[] hiddenInput; // 1,2,3,5,2...
		TTK91TaskCriteria[] registerCriteria; // R2 > 1
		TTK91TaskCriteria[] memoryCriteria; // Mikko < Ville



		TTK91TaskOptions taskOptions = new TTK91TaskOptions();

		// Nämä muuttujat ovat edellisen sivun inputin
		// lukemista varten parametrista req.
		// Jokainen arvo tarkistetaan siten että katsotaan onko
		// sen tyyppi ja syntaksi oikein.

		// suoritettavien konekäskyjen maksimimäärä

		String reqEvent = this.req.getParameter(
				"event");

		String reqMaxCommands = this.req.getParameter(
				"maxCommands"
				);
		// malliratkaisu
		String reqExampleCode = this.req.getParameter(
				"exampleCode"
				);
		// tehtävänanto
		String reqTaskDescription = this.req.getParameter(
				"taskDescription"
				);
		// julkiset syötteet
		String reqPublicInput = this.req.getParameter(
				"publicInput"
				);
		// piilotetut syötteet
		String reqHiddenInput = this.req.getParameter(
				"hiddenInput"
				);
		// verrataanko simulaatioon
		String reqCompareMethod = this.req.getParameter(
				"compareMethod"
				);

		// miksi tämä on?
		String reqAcceptedSize = this.req.getParameter(
				"acceptedSize"
				);
		// ratkaisun suosituskoko
		String reqOptimalSize = this.req.getParameter(
				"optimalSize"
				);
		// kielletyt konekäskyt
		String reqRequiredCommands = this.req.getParameter(
				"requiredCommands"
				);
		// vaaditut konekäskyt
		String reqForbiddenCommands = this.req.getParameter(
				"forbiddenCommands"
				);

		// pyydetyt rekisterien arvot
		String reqRegisterValues = this.req.getParameter(
				"registerValues"
				);

		//muistipaikkojen arvot
		String reqMemoryValues = this.req.getParameter(
				"memoryValues"
				);
		// Muistiviittaukset
		String reqMemoryReferences = this.req.getParameter(
				"memoryReferences"
				);

		// näytön tulosteet
		String reqScreenOutput = this.req.getParameter(
				"screenOutput"
				);

		// tulosteet tiedostoon
		String reqFileOutput = this.req.getParameter(
				"fileOutput"
				);


		// TODO checking of session, but no need for new one

		this.session = this.req.getSession(false);


		// TODO nämä omiksi privametodeikseen

		try {
			// maxCommands ei voi olla null
			// TODO konffattava oletusarvo

			if(validParam(reqMaxCommands)) {
				maxCommands = parsePostInt(reqMaxCommands);
			} else {
				maxCommands = 10000;
			}

			taskOptions.setMaxCommands(maxCommands);

		} catch (Exception e) {
			// TODO lisää virheen palauttaminen
			returnError(this.staticResponse, "foo");
			return;
		}// catch

		try { // exampleCode
			// TODO validin TTK91-koodin tarkistaminen
			// TODO dynaamisen koodin syntaksin tarkistaminen
			// TODO dynaamisen koodin vaatiminen

		if(validParam(reqExampleCode)){
			exampleCode = reqExampleCode;
			taskOptions.setExampleCode(exampleCode);
			}
		} catch (Exception e) {
			
			returnError(this.staticResponse, "foo");
			return;

		}

		try { // taskDescription
			// TODO vaaditaanko?
			// TODO dynaamisen tehtävän syntaksin tarkistaminen
			// TODO dynaamisen tehtävän vertailu mallikoodiin
			
		if(validParam(reqTaskDescription)) {
			taskDescription = reqTaskDescription;
			taskOptions.setTaskDescription(taskDescription);

		}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { // publicInput

			if(validParam(reqPublicInput)) {
				publicInput = parseInputString(reqPublicInput);
				taskOptions.setPublicInput(publicInput);
			}

		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { // hiddenInput

			if(validParam(reqHiddenInput)) {
				hiddenInput = parseInputString(reqHiddenInput);
				taskOptions.setHiddenInput(hiddenInput);
			}

		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { // compareMethod

			if(validParam(reqCompareMethod)) {
				compareMethod = parsePostInt(reqCompareMethod);
			}


		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { //  acceptedSize
			if(validParam(reqAcceptedSize)) {

				acceptedSize = parsePostInt(reqAcceptedSize);
				taskOptions.setAcceptedSize(acceptedSize);

			}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}


		try { // optimalSize
			if(validParam(reqOptimalSize)) {

				optimalSize = parsePostInt(reqOptimalSize);
				taskOptions.setOptimalSize(optimalSize);
			}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;

		}

		try { // requiredCommands

			if(validParam(reqRequiredCommands)) {

				requiredCommands = validTTK91Commands(
						reqRequiredCommands
						);
				taskOptions.setRequiredCommands(
						requiredCommands
						);
			}

		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { // forbiddenCommands

			if(validParam(reqForbiddenCommands)) {

				forbiddenCommands = validTTK91Commands(
						reqForbiddenCommands
						);

				taskOptions.setForbiddenCommands(
						forbiddenCommands
						);
			}

		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}



		try { // memoryCriteria

		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { // registerCriteria

		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;

		}

		try { // screenOutput

		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;

		}


		try { // fileOutput

		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;

		}

		this.session.setAttribute(
				"fi.helsinki.cs.koskelo.common.TTK91TaskOptions",
				taskOptions
				);

		this.res.setContentType ("text/html");
		ServletOutputStream out = this.res.getOutputStream();
		out.print(feedbackForm());


	} // doPost


	private void returnError(String target, String error) 
	throws ServletException, java.io.IOException{
	
		this.session.setAttribute("TTK91ERROR", error);
		this.req.getRequestDispatcher(target).
			forward(this.req, this.res);
	}

	private int[] parseInputString(String input)
		throws Exception {


			StringTokenizer st = new StringTokenizer(
					input, ","
					);

			Vector tmp = new Vector();
			int[] retInput;

			while(st.hasMoreTokens()){

				tmp.add(st.nextToken());
			}// while

			retInput = new int[tmp.size()];

			for(int i = 0; i < tmp.size(); i++) {
				retInput[i] = parsePostInt( (String)tmp.get(i) );
			} // for

			return retInput;

		} // parseInputString


	private String[] validTTK91Commands(
			String commandString
			) throws Exception {

		String[] tmp = commandString.split(",");

		//siivotaan kukin
		// TODO tarvitaanko muitakin?
		for(int i = 0; i < tmp.length; i++) {
			tmp[i].trim();
		} // for

		for(int i = 0; i < tmp.length; i++) {

			if(	fi.helsinki.cs.koskelo.common.TTK91ParserUtils.
					validateTTK91Command(tmp[i])
			  ) {
				// ok!
			} else {
				throw new Exception("Invalid TTK-91 command");
			} // if-else
		}// for

		return tmp;

	}// validTTK91Commands


	private boolean validParam(String s){
		return (s != null && !s.equals(""));
	}
	
	private String feedbackForm() {

		// TODO language!!

		String page = "<html>";

		// head
		page =	page.concat(
				"<head>"+
				"<title>Untitled Document</title>" +
				"<meta http-equiv=\"Content-Type\" "+
				"content=\"text/html; charset=iso-8859-1\">"+
				"</head>"
				);
		// body

		page = page.concat(
				"<body bgcolor=\"#FFFFFF\">"

				);
		// title

		page = page.concat(
				"<h1>Opiskelijalle annettavat palautteet</h1>"
				);

		// form
		// FIXME assuming eassari in ../eassari
		page = page.concat(
				"<form method=\"post\" action=\""+
				"../../eAssari/taskDefinition/controller"+
				"\">\n"
				);

		page = page.concat(
				"  <table width=\"450\" border=\"0\">"+
				"   <tr>"+
				"        <td>" +
				"  <div align=\"center\">"+
				"<b>Kriteeri t&auml;yttyy</b>" +
				"</div>" +
				"</td>" +
				"<td>" +
				"       <div align=\"center\">" +
				"<b>Kriteeri ei t&auml;yty</b>" +
				"</div>" +
				"</td>" +
				"</tr>" +
				"</table>\n"
				);

		page = page.concat(
				"  <p>Hyv&auml;ksytt&auml;v&auml;n"+
				" ratkaisun k&auml;skyjen " +
				"maksimim&auml;&auml;r&auml;  </p>\n"+
				feedbackBox("acceptedSize")
				);

		page = page.concat(
				"  <p>Ihannekoko  </p>\n"+
				feedbackBox("optimalSize")
				);

		page = page.concat(
				"  <p>Ohjelmassa vaaditut k&auml;skyt  </p>\n"+
				feedbackBox("requiredCommands")
				);

		page = page.concat(
				"<p>Ohjelmassa kielletyt k&auml;skyt</p>\n" +
				feedbackBox("forbiddenCommands")
				);

		page = page.concat(
				"<p>Rekisterien sis&auml;lt&ouml;</p>" +
				feedbackBox("register")
				);

		page = page.concat(
				"  <p>Muistipaikkojen ja muuttujien"+
				"sis&auml;lt&ouml;</p>" +
				feedbackBox("memory")
				);

		page = page.concat(
				"<p>Muistiviitteiden m&auml;&auml;r&auml;</p>" +
				feedbackBox("memoryReferences")
				);

		page = page.concat(
				"<p>Tulosteet n&auml;yt&ouml;lle</p>" +
				feedbackBox("screenOutput")
				);

		page = page.concat(
				"  <p>Tulosteet tiedostoon</p>" +
				feedbackBox("fileOutput")
				);

		page = page.concat(
				"  <p>"+
				"<input type=\"submit\""+
				" name=\"Submit\""+
				"value=\"Luo teht&auml;v&auml;\">"+
				"</p>"
				);

		page = page.concat("</form></body></html>");


		return page;
	}// feedbackForm


	private String feedbackBox(String name) {

		return "<p>\n"+
			"<textarea cols=\"40\" rows=\"5\" "+
			"name=\""+
			name+
			"FeedbackPositive\">"+
			"</textarea>\n"+
			"<textarea "+
			"cols=\"40\" rows=\"5\" "+
			"name="+
			"\""+
			name+
			"FeedbackNegative\">"+
			"</textarea>\n"+
			"<textarea name="+
			"\""+
			name+
			"QualityFeedback\""+
			"cols=\"40\" rows=\"5\">"+
			"</textarea>\n"+
			"</p>\n";
	}// feedbackBox


	private int parsePostInt(String s) throws Exception {

		return ((new Integer(s)).intValue());

	}// parsePostInt

	/** Servletin oma sisäinen apumetodi. Tätä hyödynnetään
	 * doPostin parsiessa kriteereitä erilleen saamastaan
	 * HttpRequestista.
	 */

	private String[] parsePostText(String s) {

		String[] foo = new String[2];
		foo[0] = "bar";

		return foo;
	}//parsePostText


} //class
