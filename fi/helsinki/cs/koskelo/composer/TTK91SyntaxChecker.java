package fi.helsinki.cs.koskelo.composer;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;
import java.util.StringTokenizer;
import fi.helsinki.cs.koskelo.common.*;
import fi.hy.eassari.taskdefinition.util.*;
//import fi.hy.eassari.taskdefinition.datastructures.*;
import fi.hy.eassari.showtask.trainer.TaskBase;

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
				"event").trim();

		String reqMaxCommands = this.req.getParameter(
				"maxCommands"
				).trim();
		// malliratkaisu
		String reqExampleCode = this.req.getParameter(
				"exampleCode"
				).trim();
		
		// tehtävänanto
		String reqTaskDescription = this.req.getParameter(
				"taskDescription"
				).trim();
		
		// julkiset syötteet
		String reqPublicInput = this.req.getParameter(
				"publicInput"
				).trim();
		
		// piilotetut syötteet
		String reqHiddenInput = this.req.getParameter(
				"hiddenInput"
				).trim();
		
		// verrataanko simulaatioon
		String reqCompareMethod = this.req.getParameter(
				"compareMethod"
				).trim();
		
		// ratkaisun hyväksymiskoko
		String reqAcceptedSize = this.req.getParameter(
				"acceptedSize"
				).trim();
		// ratkaisun suosituskoko
		String reqOptimalSize = this.req.getParameter(
				"optimalSize"
				).trim();
		// kielletyt konekäskyt
		String reqRequiredCommands = this.req.getParameter(
				"requiredCommands"
				).trim();
		// vaaditut konekäskyt
		String reqForbiddenCommands = this.req.getParameter(
				"forbiddenCommands"
				).trim();

		// pyydetyt rekisterien arvot
		String reqRegisterCriteria = this.req.getParameter(
				"registerCriteria"
				).trim();

		//muistipaikkojen arvot
		String reqMemoryCriteria = this.req.getParameter(
				"memoryCriteria"
				).trim();
		// Muistiviittaukset
		String reqMemoryReferences = this.req.getParameter(
				"memoryReferences"
				).trim();

		// näytön tulosteet
		String reqScreenOutput = this.req.getParameter(
				"screenOutput"
				).trim();

		// tulosteet tiedostoon
		String reqFileOutput = this.req.getParameter(
				"fileOutput"
				).trim();


		// TODO checking of session, but no need for new one

		this.session = this.req.getSession(false);
		try {
			// maxCommands ei voi olla null

			if(validParam(reqMaxCommands)) {
				maxCommands = parsePostInt(reqMaxCommands);
			} else {
				maxCommands = 10000;
			}

			taskOptions.setMaxCommands(maxCommands);

		} catch (Exception e) {
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
			// fixme: not like this
			if(validParam(reqCompareMethod)) {
				compareMethod = 1;
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
			if(validParam(reqMemoryCriteria)) {
				memoryCriteria = parseCriteriaString(
					reqMemoryCriteria
					);
				taskOptions.setMemoryCriterias(
					memoryCriteria
					);
			}
			
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { // registerCriteria
			if(validParam(reqRegisterCriteria)) {
				registerCriteria = parseCriteriaString(
					reqRegisterCriteria
					);
				taskOptions.setRegisterCriterias(
					registerCriteria
					);
			}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;

		}

		try { // screenOutput

			if(validParam(reqScreenOutput)) {
				screenOutput = parseOutputString(
					reqScreenOutput
					);
				taskOptions.setScreenOutput(
					screenOutput
					);
			}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;

		}


		try { // fileOutput
			if(validParam(reqFileOutput)) {
	
				fileOutput = parseOutputString(
					reqFileOutput
					);
				taskOptions.setFileOutput(
					fileOutput
					);
			}
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
					input, ",");

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

	
	/* Parses string of format (1,2);(2,3); etc into int[][] */
	private int[][] parseOutputString(String output) 
		throws Exception{

		String[] splitted1 = output.split(";");
		String[][] splitted2 = new String[splitted1.length][2];
		int[][] outPutTable = new int[splitted1.length][2];
		
		for(int i = 0; i < splitted1.length; i++) {
			splitted1[i] = splitted1[i].replaceAll("(","");
			splitted1[i] = splitted1[i].replaceAll(")","");
			splitted1[i].trim();
			splitted2[i] = splitted1[i].split(",");
			splitted2[i][0].trim();
			splitted2[i][1].trim();
			outPutTable[i][0] = parsePostInt(splitted2[i][0]);
			outPutTable[i][1] = parsePostInt(splitted2[i][1]);
		}
		
		return output;
	}
	
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

			if(fi.helsinki.cs.koskelo.common.TTK91ParserUtils.
					validateTTK91Command(tmp[i])
			  ) {
				// ok!
			} else {
				throw new Exception("Invalid TTK-91 command");
			} // if-else
		}// for

		return tmp;

	}// validTTK91Commands


	private TTK91TaskCriteria[] parseCriteriaString(String criteriaString) 
		throws InvalidTTK91CriteriaException{

		String[] tmp = criteriaString.split(";");
		
		TTK91TaskCriteria[] criterias = new TTK91TaskCriteria[tmp.length];
		
		for(int i = 0; i < tmp.length; i++) {
			criterias[i] = new TTK91TaskCriteria(tmp[i]);
		} // for
		
		return criterias;

	}//parseCriteriaString
	
	private boolean validParam(String s){
		return (s != null && !s.equals(""));
	}//validParam

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

		// hidden input. Yuk.
		page = page.concat(
				" <input name=\"event\""+
				" type=\"hidden\" id=\"event\""+
				" value =\""+
				Events.STATIC_TTK91_SUBMIT+
				"\" />"
				);
		// hidden input. Yuk.
		page = page.concat(
				" <input name=\"taskid\""+
				" type=\"hidden\" id=\"taskid\""+
				" value =\""+
				req.getParameter("taskid")+
				"\" />"
				);

		page = page.concat(
				" <input name=\"taskname\""+
				" type=\"hidden\" id=\"taskname\""+
				" value =\""+
				req.getParameter("taskname")+
				"\" />"
				);
//kriteeriotsakkeet
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
// käskyjen lukumäärä
		page = page.concat(
				"  <p>Hyv&auml;ksytt&auml;v&auml;n"+
				" ratkaisun k&auml;skyjen " +
				"maksimim&auml;&auml;r&auml;  </p>\n"+
				feedbackBox("acceptedSize")
				);
// optimikoko
		page = page.concat(
				"  <p>Ihannekoko  </p>\n"+
				feedbackBox("optimalSize")
				);
// vaaditut käskyt
		page = page.concat(
				"  <p>Ohjelmassa vaaditut k&auml;skyt  </p>\n"+
				feedbackBox("requiredCommands")
				);
// kielletyt käskyt
		page = page.concat(
				"<p>Ohjelmassa kielletyt k&auml;skyt</p>\n" +
				feedbackBox("forbiddenCommands")
				);
// rekisterikriteerit
		page = page.concat(
				"<p>Rekisterien sis&auml;lt&ouml;</p>" +
				feedbackBox("register")
				);
// muistikriteerit
		page = page.concat(
				"  <p>Muistipaikkojen ja muuttujien"+
				"sis&auml;lt&ouml;</p>" +
				feedbackBox("memory")
				);
// muistiviitteet
		page = page.concat(
				"<p>Muistiviitteiden m&auml;&auml;r&auml;</p>" +
				feedbackBox("memoryReferences")
				);
// näyttötulosteet
		page = page.concat(
				"<p>Tulosteet n&auml;yt&ouml;lle</p>" +
				feedbackBox("screenOutput")
				);
// tiedostotulosteet
		page = page.concat(
				"  <p>Tulosteet tiedostoon</p>" +
				feedbackBox("fileOutput")
				);
// submit
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
			"<textarea cols=\"40\" rows=\"5\" name="+
			"\""+
			name+
			"QualityFeedback\">"+
			"</textarea>\n"+
			"</p>\n";
	}// feedbackBox


	private int parsePostInt(String s) throws Exception {

		return ((new Integer(s)).intValue());

	}// parsePostInt

} //class
