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
import fi.helsinki.cs.koskelo.common.TTK91Constant;
import fi.helsinki.cs.koskelo.common.TTK91TaskCriteria;

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
    //	this.control = null;  // ei tarvittane en‰‰
    this.results = null;
    this.fbcomposer = new TTK91FeedbackComposer();
    this.feedback = null;
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
    // FIXME: v‰‰r‰ parametri
    getStudentApplication(answer); // k‰‰nnet‰‰n opiskelijan ratkaisu
    getTTK91TaskOptions();

    boolean runnedOK = false;
    runnedOK = run(); // varsinainen ratkaisu(je)n simulointi
    if (!runnedOK) { // jos true, ajo(t) meniv‰t ok -> jatketaan. Virheist‰ generoidaan palaute ja lopetetaan.
	    return feedback;
    }

    //Seuraavat metodit asettavat TTK91AnalyseResultsiin tulokset

    //generalAnalysis() a.k.a. "hilut"
    //analyseMemory() Lauri
    //analyseRegisters()
    //analyseOutput()

    //Aseta statistiikat resultsiin TKK91Memorysta ja CPU:sta

    try {
	    feedback = TTK91FeedbackComposer.formFeedback( results, taskOptions.getTaskFeedback(), cache, taskID, language );
    }
    catch (CacheException e) {
	    feedback = TTK91FeedbackComposer.formFeedback("Error while retrieving error message :( "+e.getMessage() );
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

  private boolean run() { // false jos virheit‰, true muuten

    int[] publicInputTable = taskOptions.getPublicInput();
    int[] hiddenInputTable = taskOptions.getHiddenInput();

    String publicInput = null; 
    String hiddenInput = null;

    int steps = taskOptions.getMaxCommands();
    int compareMethod = taskOptions.getCompareMethod();

    /* Koska titokoneesta metodilla .getCPU() saadaan
     * vain viite controlin sis‰iseen prosessiin k‰ytet‰‰n
     * yhteens‰ maksissaan nelj‰‰ controlia, kuitenkin
     * siten, ett‰ kullekin simulointikierrokselle
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
        feedback = TTK91FeedbackComposer.formFeedback("Virhe malliratkaisussa: "+e.getMessage());
        return false;
	    }
    }


    if (hiddenInputTable != null) {
	    // mahdollinen 2. simulointi opiskelijan ratkaisusta
	    hiddenInput = parseInputString(hiddenInputTable);

	    this.controlHiddenInputStudent = new Control(null, null); // luodaan control vain jos hiddeninput m‰‰ritelty --> "optimointia"
	    this.studentApplication.setKbd(hiddenInput);

	    try {
        this.controlHiddenInputStudent.run(this.studentApplication, steps);
	    }
	    catch (TTK91Exception e) {
        feedback = TTK91FeedbackComposer.formFeedback(e.getMessage());
        return false;
	    }

	    if(compareMethod == taskOptions.COMPARE_TO_SIMULATED) {
        // simuloidaa malliratkaisu
        // 2. simulointi malliratkaisua

        this.controlHiddenInputTeacher = new Control(null, null); // luodaan control vain jos hiddeninput m‰‰ritelty --> "optimointia"
        this.teacherApplication.setKbd(hiddenInput);
        try {
          this.controlHiddenInputTeacher.run(this.teacherApplication, steps);
        }
        catch (TTK91Exception e) {
          feedback = TTK91FeedbackComposer.formFeedback("Virhe malliratkaisussa: "+e.getMessage());
          return false;
        }
	    }
    } 
	
    return true;

  }//run


  private void generalAnalysis() { // Laurin heini‰ FIXME: kesken

    /* Kaikille seuraaville kenties 
     */
	
    //  -Suoritettujen konek‰skyjen m‰‰r‰ (oikeellisuus)

    TTK91Cpu cpu = controlPublicInputStudent.getCPU();
    int size = cpu.giveCommAmount();
    int sizeLimit = taskOptions.getMaxCommands();
    results.setAcceptedSize(size <= sizeLimit);

    //	  -Ihannekoko (laatu)
    results.setOptimalSize(size <= taskOptions.getOptimalSize());

    //	  -Muistiviitteiden m‰‰r‰

    TTK91Memory mem = controlPublicInputStudent.getMemory();
    int memrefs = mem.getMemoryReferences();
    TTK91TaskCriteria memRefCriteria = taskOptions.getMemRefCriteria();
    boolean memrefsok = checkMemRefCriteria(memrefs, memRefCriteria.getComparator(), memRefCriteria.getSecondComparable());
    results.setMemoryReferences(memrefsok);

    /*
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

  private boolean checkMemRefCriteria(int memrefs, int comparator, String secondcomparable) {
    int checkAgainstMe = -1;
    try {
	    checkAgainstMe = Integer.parseInt(secondcomparable);
    }
    catch (NumberFormatException e) {
	    // rikkin‰inen kriteeri - ei pit‰isi tapahtua
	    throw new RuntimeException("TTK91Analyser.checkMemRefCriteria(): Broken criteria, String to int conversion failed");
    }
    switch (comparator) { 
	    
    case TTK91Constant.LESS:
	return (memrefs < checkAgainstMe);
	break;
    case TTK91Constant.LESSEQ:
	return (memrefs <= checkAgainstMe);
	break;
    case TTK91Constant.GREATER: 
	return (memrefs > checkAgainstMe);
	break;
    case TTK91Constant.GREATEREQ: 
	return (memrefs >= checkAgainstMe);
	break;
    case TTK91Constant.EQUAL: 
	return (memrefs == checkAgainstMe);
	break;
    case TTK91Constant.NOTEQUAL: 
	return (memrefs != checkAgainstMe);
	break;
    default:
	return false; // tanne ei pitaisi paasta koskaan
    }
  } // checkMemRefCriteria

} // StaticTTK91Analyser
