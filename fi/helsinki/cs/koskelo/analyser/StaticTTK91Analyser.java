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

import fi.helsinki.cs.koskelo.common.TTK91TaskOptions;

/**
 * Luokka staattisten TTK-91 -teht‰vien vastauksien tarkastamiseen
 * @author Lauri Liuhto
 *  
 */

public class StaticTTK91Analyser extends CommonAnalyser {

    private AttributeCache cache; // k‰yt‰nnˆss‰ ilmeisesti TaskBase?
    private String taskID;
    private String language;
    private ParameterString initP;
    // k‰yt‰nnˆn toteutus Control.java
    private TTK91Core controlCompiler;           // k‰‰nt‰j‰ -> saadaan mahdolliset k‰‰nnˆsvirheet n‰timmin (kunhan ajetaan malli ensin... 
                                                 // Jos se ei k‰‰nny, ei varmaan ole tarvetta k‰‰nt‰‰ opiskelijankaan ratkaisua...
    private TTK91Core controlPublicInputStudent; // publicinputeilla tai ilman unputteja opiskelijan vastaus
    private TTK91Core controlPublicInputTeacher; // publicinputeilla tai ilman inputteja malliratkaisu jos vertailu on m‰‰ritelty simuloitavaksi
    private TTK91Core controlHiddenInputStudent; // hiddeninputeilla jos ovat m‰‰ritelty opiskelijan vastaus
    private TTK91Core controlHiddenInputTeacher; // hiddeninputeilla jos ovat m‰‰ritelty malliratkaisu jos vertailu on m‰‰ritelty simuloitavaksi
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
	//	this.control = null;  // ei tarvittane en‰‰
	this.results = null;
    } // StaticTTK91Analyser()


    /**
     * Alustaa alustamattoman StaticTTK91Analyserin
     * @param taskid teht‰v‰tunnus
     * @param language kielikoodi
     * @param initparams FIXME: kuvaus
     */

    public void init(String taskid, String language, String initparams) {
	this.taskID = taskid;
	this.language = language;
	this.initP = new ParameterString(initparams);
	//	this.control = new Control(null, null); // ei tarvittane en‰‰
	this.results = new TTK91AnalyseResults(); //Oletuksena kaikki tulokset false
    } // init


    /**
     * Analysoi vastauksen Titokoneella
     * @param answer TTK91-kielinen l‰hdekoodi
     * @param params 
     * @return palaute
     */

    public Feedback analyse(String[] answer, String params) { // FIXME:
	// varsinainen
	// toiminnallisuus
	// kesken

	getTeacherApplication(answer); // k‰‰nnet‰‰n mahdollinen malliratkaisu
	getStudentApplication(answer); // k‰‰nnet‰‰n opiskelijan ratkaisu
	getTTK91TaskOptions();

	//RUN()

	//Seuraavat metodit asettavat TTK91AnalyseResultsiin tulokset
	//GeneralAnalysis() a.k.a. "hilut"
	//AnalyseMemory() Lauri
	//AnalyseRegisters()
	//AnalyseOutput()

	//Aseta statistiikat resultsiin TKK91Memorysta ja CPU:sta

	return TTK91FeedbackComposer.formFeedback( results, taskOptions.getTaskFeedback(), cache, taskID, language );


	/*
	  try {
	  core.run(app, 5); // FIXME: maksimikierrosten m‰‰r‰
	  // taskoptionsista (tai jostain muualta)
	  }
	  catch (TTK91Exception e) {
	  //	System.err.println("Ajonaikainen virhe"+e.getMessage());
	  return new Feedback(); // FIXME: oikeanlainen palaute, kun
	  // suoritus ep‰onnistuu
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
     * Apumetodi, jolla kaivetaan opiskelijan ratkaisun l‰hdekoodi vastauksesta ja k‰‰nnet‰‰n siit‰ TTK91Application
     * @param answer
     */

    private void getStudentApplication(String[] answer) {

	TTK91CompileSource src = 
	    StaticTTK91Analyser.parseSourceFromAnswer(answer);

	if (src == null) {
	    // return new Feedback(); 
	    // FIXME: oikeanlainen palaute kun sorsa
	    // ei suostu menem‰‰n edes
	    // TTK91CompileSource-muotoon - voiko
	    // n‰in edes k‰yd‰?
	}//if

	TTK91Application app = null;

	try {
	    //	    app = core.compile(src);
	    controlCompiler.compile(src);
	} catch (TTK91Exception e) {
	    //	    return new Feedback(); 
	    //	    // FIXME: oikeanlainen palaute, kun
	    // k‰‰nnˆs ep‰onnistuu
	}//catch

	this.studentApplication = app;

    }//getStudentApplication


    /**
     * Apumetodi, jolla kaivetaan malliratkaisun l‰hdekoodi vastauksesta ja k‰‰nnet‰‰n siit‰ TTK91Application
     * @param answer
     */

    private void getTeacherApplication(String[] answer) {


	TTK91CompileSource src = 
	    StaticTTK91Analyser.parseSourceFromAnswer(answer);

	if (src == null) {
	    //	    return new Feedback(); 
	    // FIXME: oikeanlainen palaute kun sorsa
	    // ei suostu menem‰‰n edes
	    // TTK91CompileSource-muotoon - voiko
	    // n‰in edes k‰yd‰?
	}//if

	TTK91Application app = null;

	try {
	    //	    app = core.compile(src);
	    controlCompiler.compile(src);
	} catch (TTK91Exception e) {
	    //	    return new Feedback(); 
	    //	    // FIXME: oikeanlainen palaute, kun
	    // k‰‰nnˆs ep‰onnistuu
	}//catch

	this.teacherApplication = app;

    }//getTTK91Application

    /**
     * Apumetodi, joka suorittaa varsinaisen l‰hdekoodin kaivamisen vastauksesta
     * @param answer
     */

    private static TTK91CompileSource parseSourceFromAnswer(String[] answer) {
	if (answer != null) { 
	    return (TTK91CompileSource) new Source(answer[0]);
	    // FIXME: toimiiko tosiaan n‰in helposti?
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
	 * vain viite controlin sis‰iseen prosessiin k‰ytet‰‰n
	 * yhteens‰ maksissaan nelj‰‰ controlia, kuitenkin
	 * siten, ett‰ kullekin simulointikierrokselle
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
	   -Suoritettujen konek‰skyjen m‰‰r‰ (oikeellisuus)
	   -Ihannekoko (laatu)
	   -Muistiviitteiden m‰‰r‰
	   -Vaaditut k‰skys
	   -Kielletyt k‰skyt
	*/
	//results.setBLAAH(boolean)

    }//generalAnalysis

    private void analyseMemory() {

	/*
	 *MUISTIPAIKKOJEN JA MUUTTUJIEN SISƒLT÷
	 */

	//results.setBLAAH(boolean)
    } //analyseMemory

    private void analyseRegisters() {

	//REKISTERIT

	//results.setBLAAH(boolean)
    } //analyseRegisters

    private void analyseOutput() {

	//TULOSTEET SAI JOLLAIN TITOKONEEN METODILLA, 
	//EN NYT MUISTA MIKƒ TAI MISTƒ [HT]
	//getCrt() [EN]
	//NƒYT÷N TULOSTEET
	//TIEDOSTON TULOSTEET

	//results.setBLAAH(boolean)
    }//analyseOutput

    /** Tehd‰‰n int[]-taulukosta merkkijono 1,2,3,4. T‰m‰ siksi,
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
