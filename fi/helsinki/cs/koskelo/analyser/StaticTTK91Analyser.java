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

    private AttributeCache cache; // käytännössä ilmeisesti TaskBase?
    private String taskID;
    private String language;
    private ParameterString initP;
    // käytännön toteutus Control.java
    private TTK91Core controlPublicInputStudent; // publicinputeilla tai ilman unputteja opiskelijan vastaus
    private TTK91Core controlPublicInputTeacher; // publicinputeilla tai ilman inputteja malliratkaisu jos vertailu on määritelty simuloitavaksi
    private TTK91Core controlHiddenInputStudent; // hiddeninputeilla jos ovat määritelty opiskelijan vastaus
    private TTK91Core controlHiddenInputTeacher; // hiddeninputeilla jos ovat määritelty malliratkaisu jos vertailu on määritelty simuloitavaksi
    private TTK91TaskOptions taskOptions;        // taskoptions
    private TTK91Application studentApplication; // opiskelijan vastaus
    private TTK91Application teacherApplication; // malliratkaisu
    private TTK91AnalyseResults results; //Uusi luokka.

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
	this.control = null;
	this.results = null;
    } // StaticTTK91Analyser()


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
	this.control = new Control(null, null);
	this.results = new TTK91AnalyseResults(); //Oletuksena kaikki tulokset false
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
     * Ilmoitetaan StaticTTK91Analyserille tietokanta-cache. 
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

	TTK91CompileSource src = StaticTTK91Analyser
	    .parseSourceFromAnswer(answer);

	if (src == null) {
	    return new Feedback(); 
	    // FIXME: oikeanlainen palaute kun sorsa
	    // ei suostu menemään edes
	    // TTK91CompileSource-muotoon - voiko
	    // näin edes käydä?
	}//if

	TTK91Application app = null;

	try {
	    //	    app = core.compile(src);
	    controlPublicInputStudent.compile(src);
	} catch (TTK91Exception e) {
	    //	    return new Feedback(); 
	    //	    // FIXME: oikeanlainen palaute, kun
	    // käännös epäonnistuu
	}//catch

	this.studentApplication = app; // FIXME: Teacher myös

    }//getTTK91Application

    private static TTK91CompileSource parseSourceFromAnswer(String[] answer) {
	if (answer != null) { 
	    return (TTK91CompileSource) new Source(answer[0]);
	    // FIXME: toimiiko tosiaan näin helposti?
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

	int[] publicInputTable = taskOptions.getPublicInput();
	int[] hiddenInputTable = taskOptions.getHiddenInput();

	String publicInput;
	String hiddenInput;

	int steps = taskOptions.getMaxCommands();
	int compareMethod = taskOptions.getCompareMethod();

	/* Koska titokoneesta metodilla .getCPU() saadaan
	 * vain viite controlin sisäiseen prosessiin käytetään
	 * yhteensä maksissaan neljää controlia, kuitenkin
	 * siten, että kullekin simulointikierrokselle
	 * luodaan oma controlinsa.
	 */
	if(publicInput != null) {

	    publicInput = parseInputString(publicInputTable);
	    this.studentApplication.setKbd(publicInput);

	    if(compareMethod == taskOptions.COMPARE_TO_SIMULATED) {
		this.teacherApplication.setKbd(publicInput);
	    }
	}

	this.controlPublicInputStudent = new Control(null, null);
	this.controlPublicInputStudent.run(this.teacherApplication, steps);
	// 1. simulointi

	if(compareMethod == taskOptions.COMPARE_TO_SIMULATED) {
	    // 1. simulointi malliratkaisua
	    this.controlPublicInputTeacher = new Control(null, null);
	    this.controlPublicInputTeacher.run(this.teacherApplication, steps);
	}


	if(hiddenInput != null) {
	    // mahdollinen 2. simulointi opiskelijan ratkaisusta
	    hiddenInput = parseInputString(publicInputTable);

	    this.controlHiddenInputStudent = new Control(null, null);
	    this.studentApplication.setKbd(publicInput);
	    this.controlHiddenInputStudent.run(this.studentApplication, steps);

	    if(compareMethod == taskOptions.COMPARE_TO_SIMULATED) {
		// simuloidaa malliratkaisu
		// 2. simulointi malliratkaisua

		this.controlHiddenInputTeacher = new Control(null, null);
		this.teacherApplication.setKbd(publicInput);
		this.controlHiddenInputTeacher.run(this.teacherApplication, steps);

	    }
	} 

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

	//results.setBLAAH(boolean)
    } //analyseMemory

    private void analyseRegisters() {

	//REKISTERIT

	//results.setBLAAH(boolean)
    } //analyseRegisters

    private void analyseOutput() {

	//TULOSTEET SAI JOLLAIN TITOKONEEN METODILLA, 
	//EN NYT MUISTA MIKÄ TAI MISTÄ [HT]
	//getCrt() [EN]
	//NÄYTÖN TULOSTEET
	//TIEDOSTON TULOSTEET

	//results.setBLAAH(boolean)
    }//analyseOutput

    /** Tehdään int[]-taulukosta merkkijono 1,2,3,4. Tämä siksi,
     * koska titokoneen setKbd ei osaa lukea int[]-taulua.
     */
    private String parseInputString(int[] inputTable) {

	String input = "";

	for(int i = 0; i < inputTable.length; i++) {
	    input = input + inputTable[i];
	}

	return input;
    }


} // StaticTTK91Analyser
