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

/** Servletti TTK91-teht‰v‰nannon syntaktisen oikeellisuuden tarkistamiseen.
 * Servletti lukee Static/FillIn/DynamicTTK91Composer.jsp:n formin sis‰llˆn
 * ja tarkistaa kustakin kent‰st‰ niiden sis‰llˆst‰ oikean muodon.
 *
 * Tulevaisuuden jatkokehityksess‰ saattaa olla j‰rkev‰‰ erottaa servletti
 * kahteen osaan siten, ett‰ feedbackill‰ on oma servlettins‰, ettei koodin
 * m‰‰r‰ t‰ss‰ tiedostossa kasva j‰rjettˆm‰n suureksi.
 *
 * @Author Eeva Nevalainen
 * @Version 0.1
 */

public class TTK91SyntaxChecker extends HttpServlet {

	/**
	 * Oletuskohde, jonne palataan jos jokin kriteereist‰ on v‰‰rin.
	 */
	//	private String staticResponse ;
	/**
	 * Kieliasetus. Assarin defaultasetus on ilmeisesti englanti, mutta
	 * t‰m‰ ylikirjoitetaan settingissist‰ saaduilla asetuksilla.
	 */
	//	private String lang = "EN"; // default in assari.
	/**
	 * Http-pyyntˆ.
	 */
	//	private HttpServletRequest req;
	/**
	 * http-vastaus.
	 */
	//	private HttpServletResponse res;
	/*
	 * @see Session
	 */
	//	private HttpSession session;
	/**
	 * Assarin oma sessityyppins‰. T‰‰lt‰ saadaan asetustietoa, kuten
	 * kieli, joka asetetaan langiin.
	 */
//	private TeacherSession settings;
	/**
	 * Events-luokan kokonaisluku. T‰st‰ voidaan p‰‰tell‰ mink‰tyyppist‰
	 * teht‰v‰‰ ollaan luomassa. Luku l‰hetet‰‰n eteenp‰in
	 * taskDefinitionControllerille.
	 */
//	private int event;
	/**
	 * Cache, josta saadaan sivujen merkkijonot. Assarin toteuttama,
	 * lˆtyy fi.hy.eassari.showtask.trainer -pakkauksesta.
	 */
	private TaskBase cache;
	/**
	 * Assarin tietorakenne. T‰t‰ tarvitaan palautteiden palauttamiseen
	 * sivulle, kun teht‰v‰‰ halutaan editoida.
	 */
//	private TaskDTO task;
	/** Boolean, joka kertoo ollaanko luomassa uutta teht‰v‰‰ vai
	 * editoimassa jo vanhaa. Jos ollaan editoimassa jo olemassaolevaa
	 * teht‰v‰‰, halutaan palauttaa annetut palautteet lomakkeeseen,
	 * jotta k‰ytt‰j‰n on mukavampi muuttaa niit‰.
	 */
	//	private boolean editTask = false;
	/** Boolean kuvaamassa onko kyseess‰ FillIn_TTK91 tyyppinen teht‰v‰.
	 * Muutetaanko intiksi jolloin saadaan toteutettua t‰h‰n samaan myˆs
	 * dynaaminen teht‰v‰?
	 */
	//	private boolean fillIn = false;

	// FIXME n‰ikˆ?
	//	private ServletConfig config;
	//FIXME: PASKAA KOODIA

	/**
	 * Yliajettu servletin alustusmetodi. Periaatteessa lukee
	 * konfiguraatiotiedoston ja luo uuden ilmentym‰n TaskBasesta.
	 *
	 * @param config Palvelinohjelmisto automaattisesti osaa antaa
	 * oikeat parametrit alustaessaan servletti‰. CHECK!!
	 */
	public void init (ServletConfig config) throws ServletException  {

		//	this.config = config;
		super.init(config);

		if (cache == null) {
			// Only created by first servlet to call
			String conFile = config
				.getServletContext()
				.getInitParameter("confile");
			conFile = config
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
	 * @param req Http-pyyntˆ, jonka asiakkaan selain l‰hett‰‰.
	 * @param res http-vastaus asiakkaan selaimelle.
	 */
	protected void doGet(
			HttpServletRequest req,
			HttpServletResponse res
			) throws ServletException, java.io.IOException {

		doPost(req, res);
	}// doGet

	/** Toteuttaa keskeisen osan luokan toiminnoista, eli tarkistaa
	 * syˆtettyjen kriteerien syntaksin ja generoi uuden sivun, jolla
	 * voi syˆtt‰‰ palautteen. Jos jonkin kriteerin syntaksi on
	 * virheellinen, sivu palaa takaisin edelliselle sivulle ja antaa
	 * virheilmoituksen.
	 *
	 * @param req HTTP-pyyntˆ joko Static- FillIn tai Dynamic
	 * TTK91Composerilta
	 * @param res HTTP-vastaus asiakkaalle. Ilman virhetilanteita
	 * lomake teht‰v‰n ratkaisun palautteen ker‰‰miseen
	 */

	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse res
			) throws ServletException, java.io.IOException{

		HttpSession session = req.getSession(false);

		// Tarkistetaan onko sessio vanhentunut
		if(session == null) {
			//sessio on vanhentunut, joten ohjataan k‰ytt‰j‰
			//sis‰‰nkirjautumissivulle eik‰ tehd‰ muuta
			req.getRequestDispatcher(
					"/jsp/login.jsp"
					).forward(req,res);
			return;
		}//if

		String exampleCode; // Malliratkaisu
		String taskDescription; // tarviiko tarkistaa?
		String[] requiredCommands; // JUMP
		String[] forbiddenCommands; // EQU

		int compareMethod; // 0 = static, 1 = simuloitu
		int maxCommands;
		int acceptedSize; // 200 rivi‰
		int optimalSize; // 10 rivi‰
		int[] publicInput;
		int[] hiddenInput; // 1,2,3,5,2...
		TTK91TaskCriteria memoryReferences;
		TTK91TaskCriteria[] registerCriteria; // R2 > 1
		TTK91TaskCriteria[] memoryCriteria; // Mikko < Ville
		TTK91TaskCriteria[] screenOutput; // (1,3) (2,4) (4,3)
		TTK91TaskCriteria[] fileOutput; // (1,3)(2,4)(4,3)

		TTK91TaskOptions taskOptions = new TTK91TaskOptions();
		String lang = "EN";
		String staticResponse= "/jsp/StaticTTK91Composer.jsp";
		int event = -1;
		TeacherSession settings;
		TaskDTO task;

		
		
		boolean fillIn = false;
		boolean editTask = false;
		// N‰m‰ muuttujat ovat edellisen sivun inputin
		// lukemista varten parametrista req.
		// Jokainen arvo tarkistetaan siten ett‰ katsotaan onko
		// sen tyyppi ja syntaksi oikein.

		// Mink‰ tyyppinen pyyntˆ on, editointi vai uusi
		String reqEvent = req.getParameter(
				"event"
				);

		// suoritettavien konek‰skyjen maksimim‰‰r‰
		String reqMaxCommands = req.getParameter(
				"maxCommands"
				);
		// malliratkaisu
		String reqExampleCode = req.getParameter(
				"exampleCode"
				);

		// teht‰v‰nanto
		String reqTaskDescription = req.getParameter(
				"taskDescription"
				);

		// julkiset syˆtteet
		String reqPublicInput = req.getParameter(
				"publicInput"
				);

		// piilotetut syˆtteet
		String reqHiddenInput = req.getParameter(
				"hiddenInput"
				);

		// verrataanko simulaatioon
		String reqCompareMethod = req.getParameter(
				"compareMethod"
				);

		// ratkaisun hyv‰ksymiskoko
		String reqAcceptedSize = req.getParameter(
				"acceptedSize"
				);
		// ratkaisun suosituskoko
		String reqOptimalSize = req.getParameter(
				"optimalSize"
				);
		// kielletyt konek‰skyt
		String reqRequiredCommands = req.getParameter(
				"requiredCommands"
				);
		// vaaditut konek‰skyt
		String reqForbiddenCommands = req.getParameter(
				"forbiddenCommands"
				);

		// pyydetyt rekisterien arvot
		String reqRegisterCriteria = req.getParameter(
				"registerValues"
				);

		//muistipaikkojen arvot
		String reqMemoryCriteria = req.getParameter(
				"memoryValues"
				);
		// Muistiviittaukset
		String reqMemoryReferences = req.getParameter(
				"memoryReferences"
				);

		// n‰ytˆn tulosteet
		String reqScreenOutput = req.getParameter(
				"screenOutput"
				);

		// tulosteet tiedostoon
		String reqFileOutput = req.getParameter(
				"fileOutput"
				);


		// Asetustiedot. mm. kieliasetukset, jota
		// k‰ytet‰‰n sivulla p‰‰tt‰m‰‰n mink‰ kieliset
		// merkkijonot tulostetaan palautteenker‰‰missivulle.

		settings = (TeacherSession)
			session.getAttribute(
					"fi.hy.taskdefinition."+
					"util.datastructures.TeacherSession"
					);


		session.removeAttribute("TTK91ERROR");

		// Asetuksia ei v‰ltt‰m‰tt‰ ole
		if (settings != null){
			// Asetuksista lˆytyy mahdollisesti korvaava
			// kieli aikaisemmin asetetulle oletuskielelle
			// EN
			lang = settings.getSelectedLanguageId();
		}

		// Yritet‰‰n parsia kokonaisluku eventist‰.


		try { // event
			event = parsePostInt(reqEvent);
		} catch (Exception e) {
			System.out.println(
					"Error while retrieving event-id: "+
					e
					);
		}

		// p‰‰tet‰‰n mit‰ meid‰n haluttiiin tekev‰n. Jos
		// fillin, asetetaan samantien myˆs kohdeosoite oikein.
		// Defaulttina oletetaan, ett‰ tehd‰‰n uutta staattista
		// teht‰v‰‰
		//

		task = (TaskDTO)
			session.getAttribute(
					"fi.hy.taskdefinition."+
					"util.datastructures.TaskDTO"
					);

		if (task != null) {
			editTask = true;
		} else {
			editTask = false;
		}

		if(event == Events.STATIC_TTK91_SUBMIT){

			fillIn = false;
			// donothing
			staticResponse = "/jsp/StaticTTK91Composer.jsp";

		} else if(event == Events.FILLIN_TTK91_SUBMIT) {

			fillIn = true;
			staticResponse = "/jsp/FillInTTK91Composer.jsp";

		} else if( event == Events.SUBMIT_TASK) {
			//donothing

		}//else



		try { // catch CacheException kun haetaan virheilmotusta tietokannasta

			try {
				// maxCommands ei voi olla null, sill‰
				// maxCommandsia k‰ytet‰‰n looppiin juuttumisen
				// est‰miseksi kun suoritetaan titokoneella
				// opiskelijan ohjelmaa

				if(validParam(reqMaxCommands)) {
					maxCommands = parsePostInt(reqMaxCommands);
				} else {
					maxCommands = 10000;
				}

				taskOptions.setMaxCommands(maxCommands);

			} catch (Exception e) {

				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91maxcommandssyntaxerror",
							lang
							),
						req,
						res,
						session


					   );
				return;

			}// catch

			try { // exampleCode eli ohjelman malliratkaisu
				// TODO dynaamisen koodin syntaksin tarkistaminen
				// TODO dynaamisen koodin vaatiminen

				exampleCode = reqExampleCode;

				if(validParam(reqExampleCode)){
					if(fillIn) {
						fillInValidate(exampleCode);
					}

					taskOptions.setExampleCode(exampleCode);


				} else if( fillIn) {
					throw new Exception("Missing examplecode");
				}
			} catch (Exception e) {

				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91examplecodesyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;

			}

			try { // taskDescription, eli teht‰v‰nanto
				// TODO dynaamisen teht‰v‰n syntaksin tarkistaminen
				// TODO dynaamisen teht‰v‰n vertailu mallikoodiin

				taskDescription = reqTaskDescription;
				taskOptions.setTaskDescription(
						taskDescription
						);

			} catch (Exception e) {
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91taskdescriptionsyntaxerror",
							lang
							),
						req,
						res,
						session


					   );
				return;
			}

			try { // publicInput, eli julkiset syˆtteet

				if(validParam(reqPublicInput)) {
					publicInput = parseInputString(reqPublicInput);
					taskOptions.setPublicInput(publicInput);
				}

			} catch (Exception e) {
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91publicinputsyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;
			}

			try { // hiddenInput, eli piilotetut syˆtteet

				if(validParam(reqHiddenInput)) {
					hiddenInput = parseInputString(reqHiddenInput);
					taskOptions.setHiddenInput(hiddenInput);
				}

			} catch (Exception e) {
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91hiddeninputsyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;
			}

			try { // compareMethod, vertailutapa.
				// compareMethod ilmaisee verrataanko titokoneen
				// simuloituun vai t‰ysin staattiseen lopputilaan.
				// J‰lkimm‰isess‰ tapauksessa titokoneella ei
				// simuloida mahdollisesti annettua malliratkaisua.

				compareMethod = parsePostInt(reqCompareMethod);

				if(
						compareMethod == TTK91Constant.COMPARE_TO_SIMULATED &&
						!validParam(exampleCode)
				  ) {


					// Vaaditaan esimerkkikoodi, jos
					// vaadittiin vertailua simuloituun
					// malliratkaisuun

					returnError(staticResponse,
							cache.getAttribute(
								"D",
								"ttk91syntaxchecker",
								"ttk91missingexamplecodeerror",
								lang
								),
							req,
							res,
							session

						   );
					return;
				}
				taskOptions.setCompareMethod(compareMethod);

			} catch (Exception e) {
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91comparemethodsyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;
			}

			try { //  acceptedSize eli maksimipituus ohjelmalle,
				// joka viel‰ kuitenkin hyv‰ksyt‰‰n

				if(validParam(reqAcceptedSize)) {
					acceptedSize = parsePostInt(reqAcceptedSize);
					taskOptions.setAcceptedSize(acceptedSize);
				}
			} catch (Exception e) {
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91acceptedsizesyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;
			}


			try { // optimalSize eli ratkaisun optimipituus
				if(validParam(reqOptimalSize)) {
					optimalSize = parsePostInt(reqOptimalSize);
					taskOptions.setOptimalSize(optimalSize);
				}
			} catch (Exception e) {
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91optimalsizesyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;

			}

			try { // memoryReferences. Odotetaan vertailu
				//operaattoria ja numeroa.
				//Tallenetaan TTK91Criteriana siten,
				//ett‰ ensimm‰isen‰ vertailuoperaattorina
				//on merkkijono MEMORYREFERENCES

				if(validParam(reqMemoryReferences)) {

					String tmp = "MEMORYREFERENCES"+
						reqMemoryReferences;

					memoryReferences = new TTK91TaskCriteria(
							tmp
							);
					parsePostInt(memoryReferences.getSecondComparable());

					// tarkistetaam ett‰ vertailun oikea puoli on
					// kokonaisluku. T‰st‰ lent‰‰ poikkeus, jos
					// niin ei ole, joten p‰‰dyt‰‰n virheilmotukseen.
					// Analyser olettaa ett‰ t‰m‰ tarkastus on tehty.
					//parsePostInt(memoryReferences.getSecondComparable());

					taskOptions.setMemRefCriteria(
							memoryReferences
							);
				}
			} catch (Exception e) {
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91memoryreferencessyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;
			}

			try { // requiredCommands eli ohjelmassa vaaditut
				//TTK91-kielen konek‰skyt.
				//Kustakin k‰skyst‰ tarkistetaan onko se vaaditunkalta
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
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91requiredcommandssyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;
			}

			try { // forbiddenCommands eli ohjelmassa kielletyt
				//k‰skyt
				if(validParam(reqForbiddenCommands)) {
					forbiddenCommands = validTTK91Commands(
							reqForbiddenCommands
							);
					taskOptions.setForbiddenCommands(
							forbiddenCommands
							);
				}
			} catch (Exception e) {
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91forbiddencommandssyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;
			}



			try { // memoryCriteria eli muistipaikkoihin
				//liittyv‰t vertailukriteerit.
				//Kukin on muotoa (A>B); tai (L, A<B);
				//Tyyppi‰ TTK91TaskCriteria
				if(validParam(reqMemoryCriteria)) {
					memoryCriteria = parseCriteriaString(
							reqMemoryCriteria

							);

					if(compareMethod == TTK91Constant.COMPARE_TO_STATIC){

						checkCriteriasForInts(memoryCriteria);

					}

					taskOptions.setMemoryCriterias(
							memoryCriteria
							);
				}
			} catch (Exception e) {
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91memoryvaluesyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;
			}

			try { // registerCriteria eli titokoneen rekistereihin
				//liittyv‰t vertailukriteerit.
				//Tyyppi‰ TTK91TaskCriteria.
				if(validParam(reqRegisterCriteria)) {
					registerCriteria = parseCriteriaString(
							reqRegisterCriteria
							);

					if(compareMethod == TTK91Constant.COMPARE_TO_STATIC){

						checkCriteriasForInts(registerCriteria);

					}

					taskOptions.setRegisterCriterias(
							registerCriteria
							);
				}
			} catch (Exception e) {
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91registervaluesyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;
			}

			try { // screenOutput eli opiskelijan
				//ohjelman tulosteet n‰ytˆlle. Lukupareja, joista
				//ensimm‰inen ilmaisee monesko tuloste ja toinen
				//ilmaisee mit‰ tulostettu.
				if(validParam(reqScreenOutput)) {
					screenOutput = parseOutputString(
							reqScreenOutput
							);
					taskOptions.setScreenOutputCriterias(
							screenOutput
							);
				}
			} catch (Exception e) {
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91screenoutputsyntaxerror",
							lang
							),
						req,
						res,
						session

					   );
				return;
			}

			try { // fileOutput eli opiskelijan ohjelman tulosteet
				// tiedostoon. Titokoneen virtuaalitiedosto t‰ss‰
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
				returnError(staticResponse,
						cache.getAttribute(
							"D",
							"ttk91syntaxchecker",
							"ttk91fileoutputsyntaxerror",
							lang
							),
						req,
						res,
						session
					   );
				return;

			}

		} catch (CacheException ce) {
			returnError(staticResponse,
					"Error while retrieving error message" + ce.getMessage(),
					req,
					res,
					session);
		}

		// Asetetaan taskOptions sessioon, jotta
		// taskdefinitioncontrollerissa se saadaan annettua
		// eteenp‰in TTK91TaskParserille. TaskParser muokkaa
		// siit‰ tietokantaan tallennettavan otuksen.
		session.setAttribute(
				"fi.helsinki.cs.koskelo.common."+
				"TTK91TaskOptions",
				taskOptions
				);

		res.setContentType ("text/html");
		ServletOutputStream out = res.getOutputStream();

		try{
			if(cache != null) {
				out.print(
						feedbackForm(
							lang, 
							task, 
							editTask,
							event,
							req.getParameter("taskid"),
							req.getParameter("taskname")
							)
						);

			} else {
				// init() failed
				out.print("Didn't get any cache");
			}
		} catch (CacheException e) {
			// jotain meni p‰in hemmmtti‰ tultamisen
			// aikana
			out.print("CacheException");
		}

	} // doPost


	/**
	 * Apumetodi virheen palauttamiseen. Piilottaa osan toisteisesta
	 * koodista. Target on k‰yt‰nnˆss‰ aina StaticResponse, mutta
	 * t‰ss‰ ollaan varauduttu mahdolliseen muuhunkin tarpeeseen.
	 * @param error Merkkijono, jonka sivu, jolle palataaan voi
	 * tulostaa k‰ytt‰j‰lle selityksen‰ virheest‰.
	 */

	private void returnError(String target, String error, HttpServletRequest req, HttpServletResponse res, HttpSession session)
		throws ServletException, java.io.IOException{

			req.setAttribute("fi.hy.eassari.showtask.trainer.TaskBase", cache);
			session.setAttribute("TTK91ERROR", error);
			req.getRequestDispatcher(target).
				forward(req, res);
		}

	/** Heitt‰‰ poikkeuksen jos code-string ei ole validi
	 * FillIn esimerkkiohjelma.
	 * Vaatimukset sopivuudelle ovat, ett‰ merkkijonossa esiintyy [ ensin
	 * ja sitten ].
	 * Molempia saa esiinty‰ tasan yksi.
	 * @param code fillIn teht‰v‰n esimerkkikoodi.
	 */
	private void fillInValidate(String code)
		throws Exception {

			if( 
					(code.indexOf("[")> -1) && 
					(code.indexOf("[") < code.indexOf("]"))&&
					(code.indexOf("[") == code.lastIndexOf("[")) &&
					(code.indexOf("]") == code.lastIndexOf("]"))
			  ){
				return;
			
			} else {
				throw new Exception("Invalid fillin");
			}
		}


	/** Apumetodi ohjelman saamien syˆtteiden parsimiseen. Lukee
	 * merkkijonon muotoa 2,3,4 jne
	 * @param input K‰ytt‰j‰n antama syˆtemerkkijono
	 */

	private int[] parseInputString(String input)
		throws Exception {

			// Kokonaisluvussa saa ilmeisesti olla piste.
			// Syˆtteess‰mme ei kuitenkaan saa olla, joten
			// heitet‰‰n poikkeus, jolloin doPost tiet‰‰
			// ett‰ merkkijono oli virheellinen.
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


	/** Lukee k‰ytt‰j‰n antaman vaaditun outputin ja muuntaa
	 * sen muotoon int[][]. J‰lkimm‰isess‰ taulukossa on aina
	 * tasan kaksi lukua, ensimm‰inen luku ilmaisemassa tulosteen
	 * j‰rjestyslukua ja toinen ilmaisemassa tulosteen sis‰ltˆ‰.
	 *
	 * Ylim‰‰r‰iset annettavat numerot yksien sulkujen sis‰ll‰
	 * j‰tet‰‰n huomiotta.
	 */
	private TTK91TaskCriteria[] parseOutputString(String output)
		throws InvalidTTK91CriteriaException, Exception{

			String[] splitted = output.split(";");
			TTK91TaskCriteria[] outputRet = new TTK91TaskCriteria[splitted.length];

			for(int i = 0; i < splitted.length; i++){
				outputRet[i] = new TTK91TaskCriteria(splitted[i], false);
				parsePostInt(outputRet[i].getFirstComparable());
				parsePostInt(outputRet[i].getSecondComparable());
			}


			return outputRet;
		}
	/** Apumetodi, jolla tarkistetaan syˆtteist‰ ovatko ne pilkulla
	 * toisistaan erotettuja TTK91-k‰skyj‰.
	 */
	private String[] validTTK91Commands(
			String commandString
			) throws Exception {

		//Esimerkki stringi: "(L, JUMP); (LOAD);"

		//H‰vitet‰‰n kaikki whitespacet
		String cleanString = commandString.replaceAll("\\s","");
		//"(L,JUMP);(LOAD)";

		String[] tmp = cleanString.split(";");
		//tmp1_0["(L,JUMP) "] ja tmp1_1["(LOAD)"]

		//Siivotaan sulutus
		for(int i = 0; i < tmp.length; i++) {

			tmp[i] = tmp[i].replaceAll("\\(","");
			//tmp1["L,JUMP) "] ja tmp1_1["LOAD)"]

			tmp[i] = tmp[i].replaceAll("\\)","");
			//tmp1["L,JUMP"] ja  tmp1_1["LOAD"]

		}// for

		for(int i = 0; i < tmp.length; i++) {

			String[] tmp2 = tmp[i].split(",");
			//tmp2_0["L"] JA tmp2_1["JUMP"]

			if(tmp2.length > 1) { // ekassa osassa varmaan L ja toisessa ADD

				if(tmp2[0].equalsIgnoreCase("L")) { // ok, ekassa L

					if( fi.helsinki.cs.koskelo.common.TTK91ParserUtils.
							validateTTK91Command(tmp2[1]) ) {
						// Oikea k‰sky oli toisessa alkiossa

					} else {

						//Ei ollut TTK91-k‰sky
						throw new Exception("Invalid TTK-91 command");

					} // if-else

				} else {

					// hups, eka ei ollutkaan L
					throw new Exception("Invalid quality part");

				}//else

			} else { // ei pilkkua joiten pit‰isi olla pelkk‰ k‰sky
				if(fi.helsinki.cs.koskelo.common.TTK91ParserUtils.
						validateTTK91Command(tmp[i])
				  ) {
					// ok!
				} else {
					throw new Exception("Invalid TTK-91 command");
				} // if-else

			}
		}// for

		// p‰‰stiin t‰nne joten eka splittaus tehd‰‰n uusiksi koska
		// merkkijonoja muutettiin v‰lill‰. Ja koska p‰‰stiin t‰nne
		// siit‰ ei seurannut mit‰‰n kamalaa. 
		// Trimmataan viel‰ ylim‰‰r‰inen
		// whitespace pois.

		//H‰vitet‰‰n kaikki whitespacet
		cleanString  = commandString.replaceAll("\\s","");

		tmp = cleanString.split(";");

		for( int i = 0; i < tmp.length; i++) {
			tmp[i] = tmp[i]+";";
		}

		return tmp;

	}// validTTK91Commands

	/**Apumetodi, jolla tarkistetaan ett‰ staattisen vertailun tapauksessa
	 * vertailukohteet muisti- ja rekisterikriteereiss‰ ovat kokonaislukuja.
	 * Heitt‰‰ poikkeuksen, jos n‰in ei ole, jolloin p‰‰dyt‰‰n virheellisen
	 * syˆtteen seurauksena olevaan catchiin.
	 */

	private void checkCriteriasForInts(TTK91TaskCriteria[] criterias) 
		throws Exception{

			for( int i = 0; i < criterias.length; i++) {
				parsePostInt(criterias[i].getSecondComparable());
			}
		}

	/**
	 * Apumetodi, jolla parsitaan muistipaikka 
	 * ja rekisterikriteerej‰ kuvaavat
	 * merkkijonot.
	 *
	 *@return taulukkoesitys annetun merkkijonon kriteereist‰.
	 */
	private TTK91TaskCriteria[] parseCriteriaString(String criteriaString)
		throws InvalidTTK91CriteriaException{

			String[] tmp = criteriaString.split(";");

			TTK91TaskCriteria[] criterias = new TTK91TaskCriteria[
				tmp.length
				];

			for(int i = 0; i < tmp.length; i++) {
				criterias[i] = new TTK91TaskCriteria(
						tmp[i]
						);
			} // for

			return criterias;

		}//parseCriteriaString



	/** Apumetodi joka muuttaa booleaniksi sen, ett‰ kent‰ss‰
	 * on muutakin kuin tyj‰‰.
	 */
	
	private boolean validParam(String s){
		return (s != null && !(s.trim()).equals(""));
	}//validParam


	/** Apumetodi, joka muodostaa palautelomakkeen.
	 *
	 * T‰m‰ voisi olla joskus ihan hyv‰ idea siirt‰‰ omaksi sivukseen.
	 *
	 * @return Palautelomakesivu
	 */
	private String feedbackForm(
			String lang, 
			TaskDTO task, 
			boolean editTask,
			int event,
			String taskid,
			String taskname
			) throws CacheException{

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
				event+
				"\" />"
				);
		// hidden input. Yuk.
		page = page.concat(
				" <input name=\"taskid\""+
				" type=\"hidden\" id=\"taskid\""+
				" value =\""+
				taskid+
				"\" />"
				);

		page = page.concat(
				" <input name=\"taskname\""+
				" type=\"hidden\" id=\"taskname\""+
				" value =\""+
				taskname+
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
			"</tr>" 
			);
		// k‰skyjen lukum‰‰r‰
		page = page.concat(
				"<tr><td>  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"acceptedSizeHeader",
					lang
					) +
				"</p>\n</td></tr><tr>"+
				feedbackBox("acceptedSize", false, task, editTask) +
				"</tr>
				);
		// optimikoko
		page = page.concat(
				"<tr><td>  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"optimalSizeHeader",
					lang
					) +
				"</p>\n</td></tr><tr>"+
				feedbackBox("optimalSize", false, task, editTask) +
				"</tr>"
				);
		// vaaditut k‰skyt
		page = page.concat(
				"<tr><td>  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"requiredCommandsHeader",
					lang
					) +
				"</p>\n</td></tr><tr>"+
				feedbackBox("requiredCommands", true, task, editTask)+
				"</tr>
				);
		// kielletyt k‰skyt
		page = page.concat(
				"<tr><td>  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"forbiddenCommandsHeader",
					lang
					) +
				"</p>\n</td></tr><tr>"+
				feedbackBox("forbiddenCommands", true, task, editTask) +
				"</tr>"
				);
		// rekisterikriteerit
		page = page.concat(
				"<tr><td>  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"registerValuesHeader",
					lang
					) +
				"</p></td></tr><tr>\n"+
				feedbackBox("register", true, task, editTask)+
				"</tr>"
				);
		// muistikriteerit
		page = page.concat(
				"<tr><td>  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"memoryValuesHeader",
					lang
					) +
				"</p></td></tr>\n<tr>"+
				feedbackBox("memory", true, task, editTask)+
				"</tr>"
				);

		// muistiviitteet
		page = page.concat(
				"<tr><td><p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"memoryReferencesHeader",
					lang
					) +
				"</p>\n</td></tr><tr>"+
				feedbackBox("memoryReferences", false, task, editTask)+
				"</tr>"
				);
		// n‰yttˆtulosteet
		page = page.concat(
				"<tr><td>  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"screenOutputHeader",
					lang
					) +
				"</p>\n</td></tr><tr>"+
				feedbackBox("screenOutput", true, task, editTask)+
				"</tr>"
				);
		// tiedostotulosteet
		page = page.concat(
				"<tr><td>  <p>"+
				cache.getAttribute(
					"D",
					"staticttk91taskcomposer",
					"fileOutputHeader",
					lang
					) +
				"</p>\n</td></tr><tr>"+
				feedbackBox("fileOutput", true, task, editTask)+
				"</tr></table>"
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
	private String feedbackBox(String name, boolean quality, TaskDTO task, boolean editTask) {


		// if edit, lis‰t‰‰n vanha teksti.

		String tmp = "";

		String feedback = "";

		tmp = tmp + "<p>\n"+
			"<td><textarea cols=\"40\" rows=\"5\" "+
			"name=\""+
			name+
			"FeedbackPositive\">";

		if(editTask) {
			feedback =  (String)task.get(name + "FeedbackPositive");
			if (feedback != null && !feedback.equals("") && !feedback.equals("null")) {
				tmp = tmp + feedback;
			}
			feedback = "";
		}

		tmp = tmp +	"</textarea>\n</td>";
		tmp = tmp +	"<td><textarea "+
			"cols=\"40\" rows=\"5\" "+
			"name="+
			"\""+
			name+
			"FeedbackNegative\">";

		if(editTask) {
			feedback =  (String)task.get(name + "FeedbackNegative");
			if (feedback != null && !feedback.equals("") && !feedback.equals("null")) {
				tmp = tmp + feedback;
			}
			feedback = "";

		}

		tmp = tmp + "</textarea>\n</td>";

		if(quality){
			tmp = tmp + "<td><textarea cols=\"40\" rows=\"5\" name="+
				"\""+
				name+
				"QualityFeedback\">";
			if(editTask){
				feedback =  (String)task.get(name + "QualityFeedback");
				if (feedback != null && !feedback.equals("") && !feedback.equals("null")) {
					tmp = tmp + feedback;
				}
				feedback = "";


			}
			tmp = tmp +	"</textarea>\n</td>";
		}

		tmp = tmp + "</p>\n";

		return tmp;
	}// feedbackBox


	private int parsePostInt(String s) throws Exception {

		return ((new Integer(s)).intValue());

	}// parsePostInt

} //class
