package fi.helsinki.cs.koskelo.analyser;

import fi.hu.cs.ttk91.TTK91CompileSource;
import fi.hu.cs.ttk91.TTK91Cpu;
import fi.hu.cs.ttk91.TTK91Memory;
import fi.hu.cs.ttk91.TTK91Exception;
import fi.hu.cs.ttk91.TTK91Application;
import fi.hu.cs.ttk91.TTK91Core;

import fi.hu.cs.titokone.Source;
import fi.hu.cs.titokone.Control;

import fi.hy.eassari.showtask.trainer.Feedback;
import fi.hy.eassari.showtask.trainer.ParameterString;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CommonAnalyser;


/**
 * Luokka staattisten TTK-91 -tehtävien vastauksien tarkastamiseen
 * @author Lauri Liuhto
 *  
 */

public class StaticTTK91Analyser extends CommonAnalyser {

    private AttributeCache cache;
    private String taskID;
    private String language;
    private ParameterString initP;
    private Control control1;
    private Control control2;
    private Control control3;
    private Control control4;
    private TTK91Core core; 
    private TTK91TaskOptions taskOptions;
    private TTK91Application application;
    private TTK91AnalyseResults results; //Uusi luokka.
                                         //Sisältää boolean muuttujat jokaiselle kriteerille.
                                         //Eeva koodaa
                                         //Huom! Resulttiin kentät statistiikalle!

    /**
     * Konstruktori, joka luo uuden alustamattoman
     * StaticTTK91Analyserin, alustettava init-metodilla.
     *
     */

    public StaticTTK91Analyser() {
	this.cache = null;
	this.taskID = null;
	this.language = "FI";
	this.initP = null;
	this.core = null;
	this.results = null;
    } // StaticTTK91Analyser()


    /**
     * Konstruktori, joka luo alustetun StaticTTK91Analyserin,
     * cache-määrittely puuttuu. Tehtävä registerCache-metodilla.
     * @param taskid tehtävätunnus
     * @param language kielikoodi
     * @param initparams FIXME: kuvaus
     *
     */

    /*XXX: TURHA?
    public StaticTTK91Analyser(String taskid, String language, String initparams) {
	this.taskID = taskid;
	this.language = language;
	this.initP = new ParameterString(initparams);
	this.core = new Control(null, null);
    } // StaticTTK91Analyser(String taskid, String language, String initparams)
    */

    /**
     * Alustaa alustamattoman StaticTTK91Analyserin
     * @param taskid tehtävätunnus
     * @param language kielikoodi
     * @param initparams FIXME: kuvaus
     */

    public void init(String taskid, String language, String initparams) {
	this.taskID = taskid;
	this.language = language;
	this.initP = new ParameterString(initparams);
	this.core = new Control(null, null);
	this.results = new TTK91AnalyseResults; //Oletuksena kaikki tulokset false
    } // init


    /**
     * Analysoi vastauksen Titokoneella
     * @param answer TTK91-kielinen lähdekoodi
     * @param params 
     * @return palaute
     */

    public Feedback analyse(String[] answer, String params) { // FIXME:
	// varsinainen
	// toiminnallisuus
	// kesken

	getTTK91Application();
	getTTK91TaskOptions();

	//RUN()

	//Seuraavat metodit asettavat TTK91AnalyseResultsiin tulokset
	//GeneralAnalysis() a.k.a. "hilut"
	//AnalyseMemory() Lauri
	//AnalyseRegisters()
	//AnalyseOutput()

	//Aseta statistiikat resultsiin TKK91Memorysta ja CPU:sta

	return TTK91FeedbackComposer.formFeedback( results, taskOptions.getTaskFeedback() );


	/*
	try {
	    core.run(app, 5); // FIXME: maksimikierrosten määrä
	    // taskoptionsista (tai jostain muualta)
	}
	catch (TTK91Exception e) {
	    //	System.err.println("Ajonaikainen virhe"+e.getMessage());
	    return new Feedback(); // FIXME: oikeanlainen palaute, kun
	    // suoritus epäonnistuu
	}

	TTK91Memory mem = core.getMemory();
	TTK91Cpu cpu = core.getCpu();


	return new Feedback();
	*/

    } // analyse

    /**
     * Ilmoitetaan StaticTTK91Analyserille tietokanta-cache
     * @param AttributeCache
     */

    public void registerCache(AttributeCache c) {
	this.cache = c;
    } // registerCache


    /**
     * Apumetodi, jolla kaivetaan lähdekoodi vastauksesta
     * @param answer
     */

    private void getTTK91Application(String[] answer) {

	TTK91CompileSource src = StaticTTK91Analyser.parseSourceFromAnswer(answer);

	if (src == null) {
	    return new Feedback(); // FIXME: oikeanlainen palaute kun sorsa
	    // ei suostu menemään edes
	    // TTK91CompileSource-muotoon - voiko
	    // näin edes käydä?
	}//if
	
	TTK91Application app = null;

	try {
	    app = core.compile(src);
	} catch (TTK91Exception e) {
	    //	    return new Feedback(); // FIXME: oikeanlainen palaute, kun
	    // käännös epäonnistuu
	}//catch

	this.application = app;

    }//getTTK91Application

    private static TTK91CompileSource parseSourceFromAnswer(String[] answer) {
	if (answer != null) { 
	    return (TTK91CompileSource) new Source(answer[0]); // FIXME: toimiiko tosiaan näin helposti?
	}
	else {
	    return null;
	}
    } // parseSourceFromAnswer

    private void getTTK91TaskOptions() {

	//TOMPPA
	//this.taskOptions = blah;

    }//TTK91TaskOptions

    private void run() {

	    int[] publicInput = taskOptions.getPublicInput();
	    int[] hiddenInput = taskOptions.getHiddenInput();
	    int compareMethod = taskOptions.getCompareMethod();

	    
	    /* Koska titokoneesta metodilla .getCPU() saadaan
	     * vain viite controlin sisäiseen prosessiin käytetään
	     * yhteensä maksissaan neljää controlia, kuitenkin
	     * siten, että kullekin simulointikierrokselle
	     * luodaan oma controlinsa.
	     */

	    if(publicInput != null) {

		    // 1. simulointi
		    if(compareMethod == taskOptions.COMPARE_TO_SIMULATED) {
			    // simuloidaa malliratkaisu
			    // 1. simulointi malliratkaisua

		    }

	    } else {

		    // JOS titokoneelle erikoisesti pitää antaa syötteet
		    // tämä else on tarpeellinen
		    // 1.simulointi
	    }

	    if(hiddenInput != null {

		    // 2. simulointi
		    /* FIXME näinkö? mihin verrataan jos on kahdet syötteet
		     * määritelty?
		     */
		    if(compareMethod == taskOptions.COMPARE_TO_SIMULATED) {
			    // simuloidaa malliratkaisu

		    	// 2. simulointi malliratkaisusta. FIXME, entä jos static?
		    }

	    } 
	    // ei elseä sillä malliratkaisulle ei tarvita toista
	    // simulaatiota jos ei ole piilotettuja syötteitä

	    
	//suorita simulointi 1-4 kertaa.

    }//run

    private void generalAnalysis() {

	/* Kaikille seuraaville kenties
	  -Suoritettujen konekäskyjen määrä (oikeellisuus)
	  -Ihannekoko (laatu)
	  -Muistiviitteiden määrä
	  -Vaaditut käskys
	  -Kielletyt käskyt
	 */
	//results.setBLAAH(boolean)

    }//generalAnalysis

    private void analyseMemory() {

	/*
	 *MUISTIPAIKKOJEN JA MUUTTUJIEN SISÄLTÖ
	 */

    }analyseMemory

    private void analyseRegisters() {

	//REKISTERIT

    }analyseRegisters
    
    private void analyseOutput() {

	//TULOSTEET SAI JOLLAIN TITOKONEEN METODILLA, EN NYT MUISTA MIKÄ TAI MISTÄ [HT]
	//NÄYTÖN TULOSTEET
	//TIEDOSTON TULOSTEET

    }//analyseOutput

} // StaticTTK91Analyser
