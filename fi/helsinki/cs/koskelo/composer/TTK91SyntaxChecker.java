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

/** Servletti TTK91-tehtävänannon syntaktisen oikeellisuuden tarkistamiseen.
 * Servletti lukee Static/FillIn/DynamicTTK91Composer.jsp:n formin sisällön
 * ja tarkistaa kustakin kentästä niiden sisällöstä oikean muodon.
 *
 * Tulevaisuuden jatkokehityksessä saattaa olla järkevää erottaa servletti
 * kahteen osaan siten, että feedbackillä on oma servlettinsä, ettei koodin
 * määrä tässä tiedostossa kasva järjettömän suureksi.
 *
 * @Author Eeva Nevalainen
 * @Version 0.1
 */

public class TTK91SyntaxChecker extends HttpServlet {

	/**
	 * Oletuskohde, jonne palataan jos jokin kriteereistä on väärin.
	 */
	private String staticResponse = "/jsp/StaticTTK91Composer.jsp";
	/** 
	 * Kieliasetus. Assarin defaultasetus on ilmeisesti englanti, mutta
	 * tämä ylikirjoitetaan settingissistä saaduilla asetuksilla.
	 */
	private String lang = "EN"; // default in assari.
	/**
	 * Http-pyyntö.
	 */
	private HttpServletRequest req;
	/**
	 * http-vastaus.
	 */
	private HttpServletResponse res;
	/*
	 * @see Session
	 */
	private HttpSession session;
	/** 
	 * Assarin oma sessityyppinsä. Täältä saadaan asetustietoa, kuten
	 * kieli, joka asetetaan langiin.
	 */
	private TeacherSession settings;
	/** 
	 * Events-luokan kokonaisluku. Tästä voidaan päätellä minkätyyppistä
	 * tehtävää ollaan luomassa. Luku lähetetään eteenpäin
	 * taskDefinitionControllerille.
	 */
	private int event;
	/**
	 * Cache, josta saadaan sivujen merkkijonot. Assarin toteuttama,
	 * lötyy fi.hy.eassari.showtask.trainer -pakkauksesta.
	 */
	private TaskBase cache;
	/**
	 * Assarin tietorakenne. Tätä tarvitaan palautteiden palauttamiseen
	 * sivulle, kun tehtävää halutaan editoida.
	 */
	private TaskDTO task;
	/** Boolean, joka kertoo ollaanko luomassa uutta tehtävää vai 
	 * editoimassa jo vanhaa. Jos ollaan editoimassa jo olemassaolevaa
	 * tehtävää, halutaan palauttaa annetut palautteet lomakkeeseen,
	 * jotta käyttäjän on mukavampi muuttaa niitä.
	 */
	private boolean editTask = false;
	/** Boolean kuvaamassa onko kyseessä FillIn_TTK91 tyyppinen tehtävä.
	 * Muutetaanko intiksi jolloin saadaan toteutettua tähän samaan myös
	 * dynaaminen tehtävä?
	 */
	private boolean fillIn = false;

	// FIXME näikö?
	private ServletConfig config;
	//FIXME: PASKAA KOODIA
	
	/**
	 * Yliajettu servletin alustusmetodi. Periaatteessa lukee
	 * konfiguraatiotiedoston ja luo uuden ilmentymän TaskBasesta.
	 *
	 * @param config Palvelinohjelmisto automaattisesti osaa antaa
	 * oikeat parametrit alustaessaan servlettiä. CHECK!!
	 */
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
	 * @param req Http-pyyntö, jonka asiakkaan selain lähettää.
	 * @param res http-vastaus asiakkaan selaimelle.
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
	 *
	 * @param req HTTP-pyyntö joko Static- FillIn tai Dynamic 
	 * TTK91Composerilta
	 * @param res HTTP-vastaus asiakkaalle. Ilman virhetilanteita
	 * lomake tehtävän ratkaisun palautteen keräämiseen
	 */

	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse res
			) throws ServletException, java.io.IOException {
		// TODO kunnollinen javadoc näistä muuttujista

		this.req = req;
		this.res = res;
		this.session = this.req.getSession(false);

		// Tarkistetaan onko sessio vanhentunut
		if(this.session == null) { 
			//sessio on vanhentunut, joten ohjataan käyttäjä
			//sisäänkirjautumissivulle eikä tehdä muuta
			req.getRequestDispatcher(
					"/jsp/login.jsp"
					).forward(req,res);
			return;
		}//if

		String exampleCode; //TODO tarkista koodin kääntyminen
		String taskDescription; // tarviiko tarkistaa?
		String[] requiredCommands; // JUMP
		String[] forbiddenCommands; // EQU
		int compareMethod; // 0 = static, 1 = simuloitu
		int maxCommands;
		int acceptedSize; // 200 riviä
		int optimalSize; // 10 riviä
		int[][] screenOutput; // (1,3) (2,4) (4,3)
		int[][] fileOutput; // (1,3)(2,4)(4,3)
		int[] publicInput;
		int[] hiddenInput; // 1,2,3,5,2...
		TTK91TaskCriteria memoryReferences;
		TTK91TaskCriteria[] registerCriteria; // R2 > 1
		TTK91TaskCriteria[] memoryCriteria; // Mikko < Ville

		TTK91TaskOptions taskOptions = new TTK91TaskOptions();

		// Nämä muuttujat ovat edellisen sivun inputin
		// lukemista varten parametrista req.
		// Jokainen arvo tarkistetaan siten että katsotaan onko
		// sen tyyppi ja syntaksi oikein.

		// Minkä tyyppinen pyyntö on, editointi vai uusi
		String reqEvent = this.req.getParameter(
				"event"
				);

		// suoritettavien konekäskyjen maksimimäärä
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

		// ratkaisun hyväksymiskoko
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

		// näytön tulosteet
		String reqScreenOutput = this.req.getParameter(
				"screenOutput"
				);

		// tulosteet tiedostoon
		String reqFileOutput = this.req.getParameter(
				"fileOutput"
				);


		// Asetustiedot. mm. kieliasetukset, jota 
		// käytetään sivulla päättämään minkä kieliset
		// merkkijonot tulostetaan palautteenkeräämissivulle.

		settings = (TeacherSession)
			session.getAttribute(
					"fi.hy.taskdefinition."+
					"util.datastructures.TeacherSession"
					);
		
		// Asetuksia ei välttämättä ole
		if (settings != null){
			// Asetuksista löytyy mahdollisesti korvaava
			// kieli aikaisemmin asetetulle oletuskielelle 
			// EN
			lang = settings.getSelectedLanguageId();
		}

		// Yritetään parsia kokonaisluku eventistä.

		try { // event
			event = parsePostInt(reqEvent);
		} catch (Exception e) {
			System.out.println(
					"Error while retrieving event-id: "+
					e
					);
		}

		// päätetään mitä meidän haluttiiin tekevän. Jos
		// fillin, asetetaan samantien myös kohdeosoite oikein.
		// Defaulttina oletetaan, että tehdään uutta staattista
		// tehtävää
		if(event == Events.STATIC_TTK91_EDIT){
			
			// staattisen tehtävän editointi
			
			editTask = true;
			task = (TaskDTO)
				this.session.getAttribute(
						"fi.hy.taskdefinition."+
						"util.datastructures.TaskDTO"
						);
		} else if(event == Events.FILLIN_TTK91_COMPOSE) {
		
			// uusi FILL_IN-tehtävä
			
			fillIn = true;
			staticResponse = "/jsp/FillInTTK91Composer.jsp";
		}

		try {
			// maxCommands ei voi olla null, sillä
			// maxCommandsia käytetään looppiin juuttumisen
			// estämiseksi kun suoritetaan titokoneella
			// opiskelijan ohjelmaa

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

		try { // exampleCode eli ohjelman malliratkaisu
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

		try { // taskDescription, eli tehtävänanto
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

		try { // publicInput, eli julkiset syötteet

			if(validParam(reqPublicInput)) {
				publicInput = parseInputString(reqPublicInput);
				taskOptions.setPublicInput(publicInput);
			}

		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { // hiddenInput, eli piilotetut syötteet

			if(validParam(reqHiddenInput)) {
				hiddenInput = parseInputString(reqHiddenInput);
				taskOptions.setHiddenInput(hiddenInput);
			}

		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { // compareMethod, vertailutapa.
			// compareMethod ilmaisee verrataanko titokoneen
			// simuloituun vai täysin staattiseen lopputilaan.
			// Jälkimmäisessä tapauksessa titokoneella ei
			// simuloida mahdollisesti annettua malliratkaisua.
			// TODO jos verrataan simuloituun, pakko olla malli-
			// ratkaisu
			// FIXME: not like this

			if(validParam(reqCompareMethod)) {
				compareMethod = 1;
			}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { //  acceptedSize eli maksimipituus ohjelmalle,
			// joka vielä kuitenkin hyväksytään
			
			if(validParam(reqAcceptedSize)) {
				acceptedSize = parsePostInt(reqAcceptedSize);
				taskOptions.setAcceptedSize(acceptedSize);
			}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}


		try { // optimalSize eli ratkaisun optimipituus
			if(validParam(reqOptimalSize)) {
				optimalSize = parsePostInt(reqOptimalSize);
				taskOptions.setOptimalSize(optimalSize);
			}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;

		}

		try { // memoryReferences. Odotetaan vertailu
			//operaattoria ja numeroa. 
			//Tallenetaan TTK91Criteriana siten,
			//että ensimmäisenä vertailuoperaattorina
			//on merkkijono MEMORYREFERENCES
			
			if(validParam(reqMemoryReferences)) {
				String tmp = "MEMORYREFERENCES"+
					reqMemoryReferences;
				memoryReferences = new TTK91TaskCriteria(
						tmp
						);
				taskOptions.setMemRefCriteria(
						memoryReferences
						);
			}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { // requiredCommands eli ohjelmassa vaaditut
			//TTK91-kielen konekäskyt.
			//Kustakin käskystä tarkistetaan onko se vaaditunkalta
			//tainen merkkijono.

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

		try { // forbiddenCommands eli ohjelmassa kielletyt
			//käskyt
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



		try { // memoryCriteria eli muistipaikkoihin
			//liittyvät vertailukriteerit.
			//Kukin on muotoa (A>B); tai (L, A<B);
			//Tyyppiä TTK91TaskCriteria
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

		try { // registerCriteria eli titokoneen rekistereihin
			//liittyvät vertailukriteerit. 
			//Tyyppiä TTK91TaskCriteria.
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

		try { // screenOutput eli opiskelijan 
			//ohjelman tulosteet näytölle. Lukupareja, joista
			//ensimmäinen ilmaisee monesko tuloste ja toinen
			//ilmaisee mitä tulostettu.
			if(validParam(reqScreenOutput)) {
				screenOutput = parseOutputString(
						reqScreenOutput
						);
					taskOptions.setScreenOutputCriterias(
							screenOutput
							);
			}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;
		}

		try { // fileOutput eli opiskelijan ohjelman tulosteet
			// tiedostoon. Titokoneen virtuaalitiedosto tässä
			// tapauksessa.
			if(validParam(reqFileOutput)) {
				fileOutput = parseOutputString(
						reqFileOutput
						);
					taskOptions.setFileOutputCriterias(
							fileOutput
							);
			}
		} catch (Exception e) {
			returnError(this.staticResponse, "foo");
			return;

		}

		// Asetetaan taskOptions sessioon, jotta
		// taskdefinitioncontrollerissa se saadaan annettua
		// eteenpäin TTK91TaskParserille. TaskParser muokkaa
		// siitä tietokantaan tallennettavan otuksen.
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
				// init() failed
				out.print("Didn't get any cache");
			}
		} catch (CacheException e) {
			// jotain meni päin hemmmttiä tultamisen
			// aikana
			out.print("CacheException");
		}

	} // doPost


	/**
	 * Apumetodi virheen palauttamiseen. Piilottaa osan toisteisesta
	 * koodista. Target on käytännössä aina StaticResponse, mutta
	 * tässä ollaan varauduttu mahdolliseen muuhunkin tarpeeseen.
	 * @param error Merkkijono, jonka sivu, jolle palataaan voi
	 * tulostaa käyttäjälle selityksenä virheestä.
	 */
	
	private void returnError(String target, String error) 
		throws ServletException, java.io.IOException{

			this.session.setAttribute("TTK91ERROR", error);
			this.req.getRequestDispatcher(target).
				forward(this.req, this.res);
		}

	/** Heittää poikkeuksen jos code-string ei ole validi 
	 * FillIn esimerkkiohjelma.
	 * Vaatimukset sopivuudelle ovat, että merkkijonossa esiintyy [ ensin 
	 * ja sitten ].
	 * Molempia saa esiintyä tasan yksi.
	 * @param code fillIn tehtävän esimerkkikoodi.
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

	
	/** Apumetodi ohjelman saamien syötteiden parsimiseen. Lukee
	 * merkkijonon muotoa 2,3,4 jne
	 * @param input Käyttäjän antama syötemerkkijono
	 */
	
	private int[] parseInputString(String input)
		throws Exception {

			// Kokonaisluvussa saa ilmeisesti olla piste.
			// Syötteessämme ei kuitenkaan saa olla, joten
			// heitetään poikkeus, jolloin doPost tietää
			// että merkkijono oli virheellinen.
			if(input.indexOf(".") > -1) {
				throw new Exception("Not an integer");
			}

			String[] tmp = input.split(",");
			int[] retInput;
			retInput = new int[tmp.length];

			for(int i = 0; i < tmp.length; i++) {
				retInput[i] = parsePostInt(tmp[i].trim());
			} // for

			return retInput;


		} // parseInputString


	/** Lukee käyttäjän antaman vaaditun outputin ja muuntaa
	 * sen muotoon int[][]. Jälkimmäisessä taulukossa on aina
	 * tasan kaksi lukua, ensimmäinen luku ilmaisemassa tulosteen
	 * järjestyslukua ja toinen ilmaisemassa tulosteen sisältöä.
	 *
	 * Ylimääräiset annettavat numerot yksien sulkujen sisällä 
	 * jätetään huomiotta.
	 */
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
/** Apumetodi, jolla tarkistetaan syötteistä ovatko ne pilkulla
 * toisistaan erotettuja TTK91-käskyjä.
 */
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

/**
 * Apumetodi, jolla parsitaan muistipaikka ja rekisteri kriteerejä kuvaavat
 * merkkijonot. 
 * 
 *@return taulukkoesitys annetun merkkijonon kriteereistä.
 */
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


	/** Apumetodi, joka muodostaa palautelomakkeen.
	 *
	 * Tämä voisi olla joskus ihan hyvä idea siirtää omaksi sivukseen.
	 *
	 * @return Palautelomakesivu
	 */
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
		// käskyjen lukumäärä
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
		// vaaditut käskyt
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
		// kielletyt käskyt
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
		// näyttötulosteet
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


	/**
	 * Palautesivun palautelaatikot tulostava metodi
	 */
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
