package fi.helsinki.cs.koskelo.composer;

import java.io.*;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;
import fi.helsinki.cs.koskelo.common.*;
import fi.hy.eassari.taskdefinition.util.*;
import fi.hy.eassari.taskdefinition.util.datastructures.*;
import fi.hy.eassari.showtask.trainer.TaskBase;
import fi.hy.eassari.showtask.trainer.*;

public class TTK91SyntaxChecker extends HttpServlet {

	private String staticResponse = "/jsp/StaticTTK91Composer.jsp";
	private String lang = "EN"; // default in assari.
	private HttpServletRequest req;
	private HttpServletResponse res;
	private HttpSession session;
	private TeacherSession settings;
	private int event;
	private TaskBase cache;
	private TaskDTO task;
	private boolean editTask = false;
	private boolean fillIn = false;

	// FIXME n�ik�?
	private ServletConfig config;
	//FIXME: PASKAA KOODIA
	public void init (ServletConfig config) throws ServletException  {
		
		this.config = config;   
		super.init(this.config);
		
		if (cache == null) {
			// Only created by first servlet to call
			String conFile = this.config
				.getServletContext()
				.getInitParameter("confile");
			conFile = this.config
				.getServletContext()
				.getRealPath(conFile);

			try {
				Properties p = new Properties();
				p.load(new FileInputStream(conFile));
				String dbDriver   = (String)p.get("dbDriver");
				String dbServer   = (String)p.get("dbServer");
				String dbUser     = (String)p.get("dbUser");
				String dbPassword = (String)p.get("dbPassword");

				cache = new TaskBase(
						dbDriver,
						dbServer,
						dbUser,
						dbPassword
						);
			} catch (Exception e) {
				throw new ServletException(
						"Problems with configuration"+
						" file " + 
						conFile + 
						": " + 
						e.getMessage());
			}//catch
		}//if
	}//init

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
	 * sy�tettyjen kriteerien syntaksin ja generoi uuden sivun, jolla
	 * voi sy�tt�� palautteen. Jos jonkin kriteerin syntaksi on
	 * virheellinen, sivu palaa takaisin edelliselle sivulle ja antaa
	 * virheilmoituksen.
	 */

	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse res
			) throws ServletException, java.io.IOException {
		// TODO kunnollinen javadoc n�ist� muuttujista

		this.req = req;
		this.res = res;

		String exampleCode; //TODO tarkista koodin k��ntyminen
		String taskDescription; // tarviiko tarkistaa?
		String[] requiredCommands; // JUMP
		String[] forbiddenCommands; // EQU
		int compareMethod; // 0 = static, 1 = simuloitu
		int maxCommands;
		int acceptedSize; // 200 rivi�
		int optimalSize; // 10 rivi�
		int memoryReferences;
		int[][] screenOutput; // (1,3) (2,4) (4,3)
		int[][] fileOutput; // (1,3)(2,4)(4,3)
		int[] publicInput;
		int[] hiddenInput; // 1,2,3,5,2...
		TTK91TaskCriteria[] registerCriteria; // R2 > 1
		TTK91TaskCriteria[] memoryCriteria; // Mikko < Ville

		TTK91TaskOptions taskOptions = new TTK91TaskOptions();

		// N�m� muuttujat ovat edellisen sivun inputin
		// lukemista varten parametrista req.
		// Jokainen arvo tarkistetaan siten ett� katsotaan onko
		// sen tyyppi ja syntaksi oikein.

		// suoritettavien konek�skyjen maksimim��r�

		String reqEvent = this.req.getParameter(
				"event");

		String reqMaxCommands = this.req.getParameter(
				"maxCommands"
				);
		// malliratkaisu
		String reqExampleCode = this.req.getParameter(
				"exampleCode"
				);

		// teht�v�nanto
		String reqTaskDescription = this.req.getParameter(
				"taskDescription"
				);

		// julkiset sy�tteet
		String reqPublicInput = this.req.getParameter(
				"publicInput"
				);

		// piilotetut sy�tteet
		String reqHiddenInput = this.req.getParameter(
				"hiddenInput"
				);

		// verrataanko simulaatioon
		String reqCompareMethod = this.req.getParameter(
				"compareMethod"
				);

		// ratkaisun hyv�ksymiskoko
		String reqAcceptedSize = this.req.getParameter(
				"acceptedSize"
				);
		// ratkaisun suosituskoko
		String reqOptimalSize = this.req.getParameter(
				"optimalSize"
				);
		// kielletyt konek�skyt
		String reqRequiredCommands = this.req.getParameter(
				"requiredCommands"
				);
		// vaaditut konek�skyt
		String reqForbiddenCommands = this.req.getParameter(
				"forbiddenCommands"
				);

		// pyydetyt rekisterien arvot
		String reqRegisterCriteria = this.req.getParameter(
				"registerCriteria"
				);

		//muistipaikkojen arvot
		String reqMemoryCriteria = this.req.getParameter(
				"memoryCriteria"
				);
		// Muistiviittaukset
		String reqMemoryReferences = this.req.getParameter(
				"memoryReferences"
				);

		// n�yt�n tulosteet
		String reqScreenOutput = this.req.getParameter(
				"screenOutput"
				);

		// tulosteet tiedostoon
		String reqFileOutput = this.req.getParameter(
				"fileOutput"
				);


		// TODO checking of session, but no need for new one

		this.session = this.req.getSession(false);

		if(this.session == null) { // Sessio vanhentunut
			req.getRequestDispatcher(
					"/jsp/login.jsp"
					).forward(req,res);
			return;
		}//if

		settings = (TeacherSession)
			session.getAttribute(
					"fi.hy.taskdefinition."+
					"util.datastructures.TeacherSession"
					);
		if (settings != null){
			lang = settings.getSelectedLanguageId();
		}

		try {
			event = parsePostInt(reqEvent);
		} catch (Exception e) {
			System.out.println(
					"Error while retrieving event-id: "+
					e
					);
		}

		if(event == Events.STATIC_TTK91_EDIT){
			editTask = true;
			task = (TaskDTO)
				this.session.getAttribute(
						"fi.hy.taskdefinition."+
						"util.datastructures.TaskDTO"
						);
		} else if(event == Events.FILLIN_TTK91_COMPOSE) {
			fillIn = true;
			// FIXME
			staticResponse = "/jsp/FillInTTK91Composer.jsp";
		}

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
				if(fillIn) {
					fillInValidate(exampleCode);	
				}

				taskOptions.setExampleCode(exampleCode);


			}
		} catch (Exception e) {

			returnError(this.staticResponse, "foo");
			return;

		}

		try { // taskDescription
			// TODO vaaditaanko?
			// TODO dynaamisen teht�v�n syntaksin tarkistaminen
			// TODO dynaamisen teht�v�n vertailu mallikoodiin

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
			// FIXME: not like this
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
				// FIXME
				//	taskOptions.setScreenOutput(
				//			screenOutput
				//			);
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
				// FIXME
				//	taskOptions.setFileOutput(
				//			fileOutput
				//			);
			}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;

		}

		this.session.setAttribute(
				"fi.helsinki.cs.koskelo.common."+
				"TTK91TaskOptions",
				taskOptions
				);

		this.res.setContentType ("text/html");
		ServletOutputStream out = this.res.getOutputStream();
		try{
			if(cache != null) {
				out.print(feedbackForm());

			} else {
				// FIXME
				out.print("Didn't get any cache");
			}
		} catch (CacheException e) {
			//FIXME
			out.print("CacheException");
		}

	} // doPost


	private void returnError(String target, String error) 
		throws ServletException, java.io.IOException{

			this.session.setAttribute("TTK91ERROR", error);
			this.req.getRequestDispatcher(target).
				forward(this.req, this.res);
		}

	/** Heitt�� poikkeuksen jos code-string ei ole validi FillIn esimerkkiohjelma.
	 * Vaatimukset sopivuudelle ovat, ett� merkkijonossa esiintyy [ ensin 
	 * ja sitten ].
	 * Molempia saa esiinty� tasan yksi.
	 * @param code fillIn teht�v�n esimerkkikoodi.
	 */
	private void fillInValidate(String code) 
		throws Exception {

			if(code.indexOf("[")> -1) {
				if(
						(code.indexOf("[") < code.indexOf("]"))&&
						(code.indexOf("[") == code.lastIndexOf("[")) &&
						(code.indexOf("]") == code.lastIndexOf("]"))
				  ){
					return;
				}
			} else {
				throw new Exception("Invalid fillin");
			}
		}

	private int[] parseInputString(String input)
		throws Exception {


			String[] tmp = input.split(",");
			int[] retInput;
			retInput = new int[tmp.length];

			for(int i = 0; i < tmp.length; i++) {
				retInput[i] = parsePostInt(tmp[i].trim());
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
				outPutTable[i][0] = parsePostInt(
						splitted2[i][0]
						);
				outPutTable[i][1] = parsePostInt(
						splitted2[i][1]
						);
			}

			return outPutTable;
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

			TTK91TaskCriteria[] criterias = new TTK91TaskCriteria[
				tmp.length
				];

			for(int i = 0; i < tmp.length; i++) {
				criterias[i] = new TTK91TaskCriteria(
						tmp[i].trim()
						);
			} // for

			return criterias;

		}//parseCriteriaString

	private boolean validParam(String s){
		return (s != null && !(s.trim()).equals(""));
	}//validParam

	private String feedbackForm() throws CacheException{

		String page = "<html>";

		// head
		page =	page.concat(
				"<head>"+
				"<title>"+
				cache.getAttribute(
					"D",
					"ttk91syntaxchecker",
					"feedbacktitle", 
					lang
					) +
				"</title>" +
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
				"<h1>"+
				cache.getAttribute(
					"D",
					"ttk91syntaxchecker",
					"feedbacktitle", 
					lang
					) +
				"</h1>"
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
				this.req.getParameter("event")+
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
				cache.getAttribute(
					"D",
					"ttk91syntaxchecker",
					"criteriafilledtitle", 
					lang
					) +
				"</div>" +
				"</td>" +
				"<td>" +
				"       <div align=\"center\">" +
				cache.getAttribute(
					"D",
					"ttk91syntaxchecker",
					"criterianotfilledtitle", 
					lang
					) +
				"</div>" +
			"</td>" +
			"<td>" +
			"       <div align=\"center\">" +
			cache.getAttribute(
					"D",
					"ttk91syntaxchecker",
					"criteriaqualitytitle", 
					lang
					) +
			"</div>" +
			"</td>" +
			"</tr>" +
			"</table>\n"
			);
		// k�skyjen lukum��r�
		page = page.concat(
				"  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"acceptedSizeHeader", 
					lang
					) +
				"</p>\n"+
				feedbackBox("acceptedSize")
				);
		// optimikoko
		page = page.concat(
				"  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"optimalSizeHeader", 
					lang
					) +
				"</p>\n"+
				feedbackBox("optimalSize")
				);
		// vaaditut k�skyt
		page = page.concat(
				"  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"requiredCommandsHeader", 
					lang
					) +
				"</p>\n"+
				feedbackBox("requiredCommands")
				);
		// kielletyt k�skyt
		page = page.concat(
				"  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"forbiddenCommandsHeader", 
					lang
					) +
				"</p>\n"+
				feedbackBox("forbiddenCommands")
				);
		// rekisterikriteerit
		page = page.concat(
				"  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"registerValuesHeader", 
					lang
					) +
				"</p>\n"+
				feedbackBox("register")
				);
		// muistikriteerit
		page = page.concat(
				"  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"memoryValuesHeader", 
					lang
					) +
				"</p>\n"+
				feedbackBox("memory")
				);

		// muistiviitteet
		page = page.concat(
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"memoryReferencesHeader", 
					lang
					) +
				"</p>\n"+
				feedbackBox("memoryReferences")
				);
		// n�ytt�tulosteet
		page = page.concat(
				"  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"screenOutputHeader", 
					lang
					) +
				"</p>\n"+
				feedbackBox("screenOutput")
				);
		// tiedostotulosteet
		page = page.concat(
				"  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"fileOutputHeader", 
					lang
					) +
				"</p>\n"+
				feedbackBox("fileOutput")
				);
		// submit
		page = page.concat(
				"  <p>"+
				"<input type=\"submit\""+
				" name=\"Submit\""+
				"value=\""+
				cache.getAttribute(
					"D",
					"ttk91syntaxchecker",
					"submitbutton", 
					lang
					) +

				"\">"+
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
