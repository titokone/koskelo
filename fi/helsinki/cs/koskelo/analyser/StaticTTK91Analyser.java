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
import fi.hy.eassari.showtask.trainer.CacheException;

import fi.helsinki.cs.koskelo.common.TTK91TaskOptions;

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
    private TTK91Core controlCompiler;           // kääntäjä -> saadaan mahdolliset käännösvirheet nätimmin (kunhan ajetaan malli ensin... 
                                                 // Jos se ei käänny, ei varmaan ole tarvetta kääntää opiskelijankaan ratkaisua...
    private TTK91Core controlPublicInputStudent; // publicinputeilla tai ilman unputteja opiskelijan vastaus
    private TTK91Core controlPublicInputTeacher; // publicinputeilla tai ilman inputteja malliratkaisu jos vertailu on määritelty simuloitavaksi
    private TTK91Core controlHiddenInputStudent; // hiddeninputeilla jos ovat määritelty opiskelijan vastaus
    private TTK91Core controlHiddenInputTeacher; // hiddeninputeilla jos ovat määritelty malliratkaisu jos vertailu on määritelty simuloitavaksi
    private TTK91TaskOptions taskOptions;        // taskoptions
    private TTK91Application studentApplication; // opiskelijan vastaus
    private TTK91Application teacherApplication; // malliratkaisu
    private TTK91AnalyseResults results; //Uusi luokka.
    private TTK91FeedbackComposer fbcomposer;
    private Feedback feedback;

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
	//	this.control = null;  // ei tarvittane enää
	this.results = null;
	this.fbcomposer = new TTK91FeedbackComposer();
	this.feedback = null;
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
	//	this.control = new Control(null, null); // ei tarvittane enää
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

	getTeacherApplication(answer); // käännetään mahdollinen malliratkaisu 
	                               // FIXME: väärä parametri
	getStudentApplication(answer); // käännetään opiskelijan ratkaisu
	getTTK91TaskOptions();

	boolean runnedOK = false;
	runnedOK = run(); // varsinainen ratkaisu(je)n simulointi
	if (!runnedOK) { // jos true, ajo(t) menivät ok -> jatketaan. Virheistä generoidaan palaute ja lopetetaan.
	    return feedback;
	}

	//Seuraavat metodit asettavat TTK91AnalyseResultsiin tulokset
	//GeneralAnalysis() a.k.a. "hilut"
	//AnalyseMemory() Lauri
	//AnalyseRegisters()
	//AnalyseOutput()

	//Aseta statistiikat resultsiin TKK91Memorysta ja CPU:sta

	try {
	    feedback = TTK91FeedbackComposer.formFeedback( results, taskOptions.getTaskFeedback(), cache, taskID, language );
	}
	catch (CacheException e) {
	    // FIXME: mitä tehdään
	}
	
	return feedback;

    } // analyse

    /**
     * Ilmoitetaan StaticTTK91Analyserille tietokanta-cache. 
     * @param AttributeCache
     */

    public void registerCache(AttributeCache c) {
	this.cache = c;
    } // registerCache


    /**
     * Apumetodi, jolla kaivetaan opiskelijan ratkaisun lähdekoodi vastauksesta ja käännetään siitä TTK91Application
     * @param answer
     */

    private void getStudentApplication(String[] answer) {

	TTK91CompileSource src = 
	    StaticTTK91Analyser.parseSourceFromAnswer(answer);

	if (src == null) {
	    // return new Feedback(); 
	    // FIXME: oikeanlainen palaute kun sorsa
	    // ei suostu menemään edes
	    // TTK91CompileSource-muotoon - voiko
	    // näin edes käydä?
	}//if

	TTK91Application app = null;

	try {
	    controlCompiler.compile(src);
	} catch (TTK91Exception e) {
	    //	    return new Feedback(); 
	    //	    // FIXME: oikeanlainen palaute, kun
	    // käännös epäonnistuu
	}//catch

	this.studentApplication = app;

    }//getStudentApplication


    /**
     * Apumetodi, jolla kaivetaan malliratkaisun lähdekoodi vastauksesta ja käännetään siitä TTK91Application
     * @param answer
     */

    private void getTeacherApplication(String[] answer) {


	TTK91CompileSource src = 
	    StaticTTK91Analyser.parseSourceFromAnswer(answer);

	if (src == null) {
	    //	    return new Feedback(); 
	    // FIXME: oikeanlainen palaute kun sorsa
	    // ei suostu menemään edes
	    // TTK91CompileSource-muotoon - voiko
	    // näin edes käydä?
	}//if

	TTK91Application app = null;

	try {
	    controlCompiler.compile(src);
	} catch (TTK91Exception e) {
	    //	    return new Feedback(); 
	    //	    // FIXME: oikeanlainen palaute, kun
	    // käännös epäonnistuu
	}//catch

	this.teacherApplication = app;

    }//getTTK91Application

    /**
     * Apumetodi, joka suorittaa varsinaisen lähdekoodin kaivamisen vastauksesta
     * @param answer
     */

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

    private boolean run() { // false jos virheitä, true muuten

	int[] publicInputTable = taskOptions.getPublicInput();
	int[] hiddenInputTable = taskOptions.getHiddenInput();

	String publicInput = null; 
	String hiddenInput = null;

	int steps = taskOptions.getMaxCommands();
	int compareMethod = taskOptions.getCompareMethod();

	/* Koska titokoneesta metodilla .getCPU() saadaan
	 * vain viite controlin sisäiseen prosessiin käytetään
	 * yhteensä maksissaan neljää controlia, kuitenkin
	 * siten, että kullekin simulointikierrokselle
	 * luodaan oma controlinsa.
	 */
	if(publicInputTable != null) {

	    publicInput = parseInputString(publicInputTable);
	    this.studentApplication.setKbd(publicInput);

	    if(compareMethod == taskOptions.COMPARE_TO_SIMULATED) {
		this.teacherApplication.setKbd(publicInput);
	    }
	}

	this.controlPublicInputStudent = new Control(null, null);

	try {
	    this.controlPublicInputStudent.run(this.studentApplication, steps);
	    // 1. simulointi
	}
	catch (TTK91Exception e) {
	    //FIXME: mitä tehdään -- annetaan palaute; ohjelman suoritus kaatui
	    feedback = TTK91FeedbackComposer.formFeedback(e.getMessage());
	    return false;
	}

	if(compareMethod == taskOptions.COMPARE_TO_SIMULATED) {
	    // 1. simulointi malliratkaisua
	    this.controlPublicInputTeacher = new Control(null, null);
	    try {
		this.controlPublicInputTeacher.run(this.teacherApplication, steps);
	    }
	    catch (TTK91Exception e) {
		// FIXME: mitä tehdään -- annetaan palaute; ohjelman suoritus kaatui - malliratkaisun kirjoittaja on kämmännyt
		feedback = TTK91FeedbackComposer.formFeedback("Virhe malliratkaisussa: "+e.getMessage());
		return false;
	    }
	}


	if (hiddenInputTable != null) {
	    // mahdollinen 2. simulointi opiskelijan ratkaisusta
	    hiddenInput = parseInputString(hiddenInputTable);

	    this.controlHiddenInputStudent = new Control(null, null); // luodaan control vain jos hiddeninput määritelty --> "optimointia"
	    this.studentApplication.setKbd(hiddenInput);

	    try {
		this.controlHiddenInputStudent.run(this.studentApplication, steps);
	    }
	    catch (TTK91Exception e) {
		//FIXME: mitä tehdään -- annetaan palaute; ohjelman suoritus kaatui
		feedback = TTK91FeedbackComposer.formFeedback(e.getMessage());
		return false;
	    }

	    if(compareMethod == taskOptions.COMPARE_TO_SIMULATED) {
		// simuloidaa malliratkaisu
		// 2. simulointi malliratkaisua

		this.controlHiddenInputTeacher = new Control(null, null); // luodaan control vain jos hiddeninput määritelty --> "optimointia"
		this.teacherApplication.setKbd(hiddenInput);
		try {
		    this.controlHiddenInputTeacher.run(this.teacherApplication, steps);
		}
		catch (TTK91Exception e) {
		    // FIXME: mitä tehdään -- annetaan palaute; ohjelman suoritus kaatui - malliratkaisun kirjoittaja on kämmännyt
		    feedback = TTK91FeedbackComposer.formFeedback("Virhe malliratkaisussa: "+e.getMessage());
		    return false;
		}
	    }
	} 
	
	return true;

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
