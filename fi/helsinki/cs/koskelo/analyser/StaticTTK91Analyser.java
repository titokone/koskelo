package fi.helsinki.cs.koskelo.analyser;

import fi.hu.cs.ttk91.TTK91CompileSource;
import fi.hu.cs.ttk91.TTK91Cpu;
import fi.hu.cs.ttk91.TTK91Memory;
import fi.hu.cs.ttk91.TTK91Exception;
import fi.hu.cs.ttk91.TTK91Application;
import fi.hu.cs.ttk91.TTK91Core;

import fi.hu.cs.titokone.Source;
import fi.hu.cs.titokone.Control;
import fi.hu.cs.titokone.Processor;
import fi.hu.cs.titokone.RandomAccessMemory;
import fi.hu.cs.titokone.MemoryLine;

import fi.hy.eassari.showtask.trainer.Feedback;
import fi.hy.eassari.showtask.trainer.ParameterString;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CommonAnalyser;
import fi.hy.eassari.showtask.trainer.CacheException;

import fi.helsinki.cs.koskelo.common.TTK91TaskOptions;
import fi.helsinki.cs.koskelo.common.TTK91Constant;
import fi.helsinki.cs.koskelo.common.TTK91TaskCriteria;
import fi.helsinki.cs.koskelo.common.InvalidTTK91CriteriaException;

import java.util.regex.Pattern;
import java.util.HashMap;

/**
 * Luokka staattisten TTK-91 -teht‰vien vastauksien tarkastamiseen
 * @author Lauri Liuhto
 *  
 */


// FIXME FIXME FIXME FIXME FIXME FIXME FIXME FIXME 
//
// laadullisia kriteereja ei otettu huomioon ainakaan generalAnalysiksessa! / Lauri
//
// FIXME FIXME FIXME FIXME FIXME FIXME FIXME FIXME 

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
  private String[] exampleCode;                // haetaan taskoptionsista - malliratkaisun koodi
  private TTK91Application studentApplication; // opiskelijan vastaus
  private TTK91Application teacherApplication; // malliratkaisu
  private TTK91AnalyseResults results; //Uusi luokka.
  private TTK91FeedbackComposer fbcomposer;
  private Feedback feedback;
  private TTK91AnalyserUtils utils;
  private int analyseMethod;
  private String studentPublicScreenOut;
  private String studentHiddenScreenOut;
  private String teacherPublicScreenOut;
  private String teacherHiddenScreenOut;
  private String studentPublicFileOut;
  private String studentHiddenFileOut;
  private String teacherPublicFileOut;
  private String teacherHiddenFileOut;

  

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
    this.utils = null;
    this.studentPublicScreenOut = null;
    this.studentHiddenScreenOut = null;
    this.teacherPublicScreenOut = null;
    this.teacherHiddenScreenOut = null;
    this.studentPublicFileOut = null;
    this.studentHiddenFileOut = null;
    this.teacherPublicFileOut = null;
    this.teacherHiddenFileOut = null;
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
    //	this.initP = new ParameterString(initparams);
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

    this.utils = new TTK91AnalyserUtils(this.cache, this.taskID, this.language); // Rumaa, mutta pakko tehd‰ vasta t‰‰ll‰, jotta registerCache ajettu...

    try {
	    this.taskOptions = this.utils.getTTK91TaskOptions();
    }
    catch (CacheException ce) {
	    return new Feedback(TTK91Common.FATAL_ERROR, 
                          "*TTK91Analyser.getTTK91TaskOptions()->"+
                          "CacheException: "+ce.getMessage());
    }
    catch (InvalidTTK91CriteriaException ie) {
	    return new Feedback(TTK91Common.FATAL_ERROR, 
                          "*TTK91Analyser.getTTK91TaskOptions()->"+
                          "InvalidTTK91CriteriaException: "+ie.getMessage());
    }	    
    
    if (this.taskOptions == null) {
      return new Feedback(TTK91Common.FATAL_ERROR, "this.taskOptions on null,"+
                          "vastauksen tarkastamista ei voi tehd‰");
    }

    this.analyseMethod = taskOptions.getCompareMethod();

    if (analyseMethod > 0) {
      this.exampleCode = fetchExampleCode();

      Feedback getTeacherAppFeedback =
        getTeacherApplication(exampleCode); // k‰‰nnet‰‰n mahdollinen malliratkaisu 
      if (getTeacherAppFeedback != null) {
        return getTeacherAppFeedback; // malliratkaisu ei k‰‰nny, joten
        // tuupataan palaute eteenp‰in
      }
    }

    Feedback getStudentAppFeedback =
      getStudentApplication(answer); // k‰‰nnet‰‰n opiskelijan ratkaisu
    if (getStudentAppFeedback != null) {
      return getStudentAppFeedback; // opiskelijan ratkaisu ei k‰‰nny,
                                   // josta tuupataan palaute
                                   // eteenp‰in
    }

    boolean runnedOK = false;
    runnedOK = run(); // varsinainen ratkaisu(je)n simulointi
    if (!runnedOK) { // jos true, ajo(t) meniv‰t ok -> jatketaan. Virheist‰ generoidaan palaute ja lopetetaan.
	    return feedback;
    }

    //Seuraavat metodit asettavat TTK91AnalyseResultsiin tulokset

    generalAnalysis(answer); // "valmis" -- tarkistettava, varmaan lˆytyy luurankoja viel‰ [LL] (FIXME!)
    System.err.println("************* GENERAL_ANALYSIS PƒƒTTYI *************** ");
    RandomAccessMemory studentMemory = (RandomAccessMemory) controlPublicInputStudent.getMemory();
	
    RandomAccessMemory teacherMemory = null;
    if (controlPublicInputTeacher != null) {
	    teacherMemory = (RandomAccessMemory) controlPublicInputTeacher.getMemory(); 
    }

    // FIXME: ilmeisesti pit‰isi myˆs mahdollisesti analysoida nuo hidden-arvoilla...
    Feedback memFeedback = 
	    analyseMemory(taskOptions.getMemoryCriterias(), 
                    results,
                    studentMemory,
                    teacherMemory,
                    taskOptions.getCompareMethod() ); // Lauri "valmis" ? -- ei tod ;( (FIXME!)
    System.err.println("Tultiin analyseMemoryst‰ pojjes, ja paluuarvo != null:" +( memFeedback != null));
    if (memFeedback != null) {
	    return memFeedback; // muistianalysoinnissa tuli vastaan virhetilanne, josta tiedot palauteoliossa. Ei jatketa pidemm‰lle.
    }
	
    // FIXME == T‰m‰ varmaan pit‰isi ajaa kahdesti - tai jotain. Eli
    // toinen ajokerta hiddeninputeilla? Toisaalta, jos toimii
    // hiddeninputeilla, niin toiminee varmaan myˆs n‰kyvill‰... Joku
    // fiksumpi saa sanoa mit‰ tehd‰‰n... / Lauri == FIXME!
    Feedback regFeedback = 
      analyseRegisters(taskOptions.getRegisterCriterias(),
                       results, 
                       controlPublicInputStudent, 
                       controlPublicInputTeacher );

    if (regFeedback != null) {
      return regFeedback; // rekisterianalysoinnissa tuli vastaan
                          // virhetilanne, josta tiedot
                          // palauteoliossa. Ei jatketa pidemm‰lle.
    }

    //analyseOutput()

    Feedback outputFeedback = 
      analyseOutput();
    
    if (outputFeedback != null) {
      return outputFeedback; // tulosteiden analysoinnissa tuli
                             // vastaan virhetilanne, josta tiedot
                             // palauteoliossa. Ei jatketa pidemm‰lle.
    }
    
    //Aseta statistiikat resultsiin TKK91Memorysta ja CPU:sta
    
    try {
	    System.err.println("Hyp‰ttiin palautepulautuksen alkuun, try-lohkoon.");
	    feedback = 
        TTK91FeedbackComposer.formFeedback(results, 
                                           taskOptions.getTaskFeedback(), 
                                           cache, 
                                           taskID, 
                                           language);
	    System.err.println("Palaute pulautettu");
    }
    catch (CacheException e) {
	    System.err.println("T‰nne ei toivomma mukaan tulla... Elikk‰s k‰ts");
	    feedback = 
        TTK91FeedbackComposer.formFeedback("Error while retrieving "+
                                           "error message :( "+
                                           e.getMessage() );
    }
    System.err.println("Seuraavaksi varsinainen palautteen pulautus eAssarin Answer2:lle");
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
   * Apumetodi, joka noutaa malliratkaisun taskoptionsista T‰m‰ metodi
   * kannattanee peitt‰‰ StaticTTK91Analyserin periviss‰ luokissa,
   * ainakin fillin/dynamic -analysaattoreissa!
   * @return esimerkkikoodi
   */
  
  private String[] fetchExampleCode() {
    String[] exampleCode = new String[1];
    if (this.taskOptions != null) {
      exampleCode[0] = this.taskOptions.getExampleCode();
      return exampleCode;
    }
    else {
      return null;
    }
  } // fetchExampleCode

    /**
     * Apumetodi, jolla kaivetaan opiskelijan ratkaisun l‰hdekoodi
     * vastauksesta ja k‰‰nnet‰‰n siit‰ TTK91Application
     * @param answer
     */

  private Feedback getStudentApplication(String[] answer) {

    TTK91CompileSource src = parseSourceFromAnswer(answer);

    if (src == null) {
      return new Feedback(TTK91Constant.FATAL_ERROR, "Ratkaisua ei pystytty"+
                          " muuntamaan TTK91CompileSource-muotoon");
    }//if


    TTK91Application app = null;

    if (controlCompiler == null) {
	    controlCompiler = new Control(null, null);
    }

    try {
	    app = controlCompiler.compile(src);
    } catch (TTK91Exception e) {
      return new Feedback(TTK91Constant.FATAL_ERROR, 
                          "K‰‰nnˆsvirhe: "+e.getMessage());
    }//catch

    this.studentApplication = app;
    return null; // kaikki meni hyvin
  }//getStudentApplication


  /**
   * Apumetodi, jolla kaivetaan malliratkaisun l‰hdekoodi vastauksesta
   * ja k‰‰nnet‰‰n siit‰ TTK91Application
   * @param answer
   */

  private Feedback getTeacherApplication(String[] answer) {


    TTK91CompileSource src = parseSourceFromAnswer(answer);

    if (src == null) {
      
	    return new Feedback(TTK91Constant.FATAL_ERROR, "Malliratkaisua ei "+
                          "pystytty muuntamaan TTK91CompileSource-muotoon");
    }//if

    TTK91Application app = null;

    if (controlCompiler == null) {
	    controlCompiler = new Control(null, null);
    }
	
    try {
	    app = controlCompiler.compile(src);
    } catch (TTK91Exception e) {
      return new Feedback(TTK91Constant.FATAL_ERROR,
                          "Virheellinen malliratkaisu - k‰‰nnˆsvirhe: "+
                          e.getMessage());
    } catch (NullPointerException ne) { // FIXME: onko t‰m‰ oikeasti tarpeellinen?
	    throw new RuntimeException("*TTK91Analyser.getTeacherApplication():"+ne.getMessage());
    }//catch
	

    this.teacherApplication = app;

    return null; // kaikki meni hyvin
  }//getTTK91Application

  /**
   * Apumetodi, joka suorittaa varsinaisen l‰hdekoodin kaivamisen
   * vastauksesta
   * @param answer
   */

  private TTK91CompileSource parseSourceFromAnswer(String[] answer) {
    if (answer != null) {
	    String ans = answer[0];
	    ans = ans + "\n SVC SP, =HALT;";
	    return (TTK91CompileSource) new Source(answer[0]);
	    // FIXME: toimiiko tosiaan n‰in helposti?
    }
    else {
	    return null;
    }
  } // parseSourceFromAnswer

    //    private void getTTK91TaskOptions() {

	//TOMPPA
	//this.taskOptions = blah;

  //    }//TTK91TaskOptions

  /**
   * Apumetodi, suorittaa varsinaiset Titokone-simulaatiot
   */
  private boolean run() { // false jos virheit‰, true muuten

    int[] publicInputTable = taskOptions.getPublicInput();
    int[] hiddenInputTable = taskOptions.getHiddenInput();

    String publicInput = null; 
    String hiddenInput = null;

    int steps = taskOptions.getMaxCommands();
    int compareMethod = -1;
	
    compareMethod = taskOptions.getCompareMethod();

    System.err.println("run() alku: compareMethod: "+compareMethod);

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
      studentPublicScreenOut = studentApplication.readCrt();
      studentPublicFileOut = studentApplication.readStdOut();
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
        teacherPublicScreenOut = teacherApplication.readCrt();
        teacherPublicFileOut = teacherApplication.readStdOut();
	    }
	    catch (TTK91Exception e) {
        feedback = TTK91FeedbackComposer.formFeedback("Virhe malliratkaisussa: "+
                                                      e.getMessage());
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
        studentHiddenScreenOut = studentApplication.readCrt();
        studentHiddenFileOut = studentApplication.readStdOut();
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
          teacherHiddenScreenOut = teacherApplication.readCrt();
          teacherHiddenFileOut = teacherApplication.readStdOut();
        }
        catch (TTK91Exception e) {
          feedback = 
            TTK91FeedbackComposer.formFeedback("Virhe malliratkaisussa: "+
                                               e.getMessage());
          return false;
        }
	    }
    } 
	
    return true;

  }//run

  /**
   * Yksityinen apumetodi, joka suorittaa yleisanalyysin.
   * - konek‰skyjen maksimim‰‰r‰ (oikeellisuus)
   * - konek‰skyjen ihannem‰‰r‰  (laatu)
   * - muistiviitteiden m‰‰r‰
   * - vaaditut k‰skyt
   * - kielletyt k‰skyt
   */
  private void generalAnalysis(String[] answer) { // Laurin heini‰ - "valmis"
    System.err.println("******************** GENERAL_ANALYSIS ALKAA *******************");
    //  -Suoritettujen konek‰skyjen m‰‰r‰ (oikeellisuus)
    TTK91Cpu cpu = controlPublicInputStudent.getCpu();
    System.err.println("giveCommAmount(): "+((Processor)cpu).giveCommAmount());
    int size = ((Processor)cpu).giveCommAmount(); // FIXME: UGLY hack (rajapintaongelma, IMO [LL])
    int sizeLimit = taskOptions.getAcceptedSize();
    results.setAcceptedSize(size < sizeLimit);
    System.err.println("size:†"+size+" sizeLimit: "+sizeLimit);

    //	  -Ihannekoko (laatu)
    results.setOptimalSize(size <= taskOptions.getOptimalSize());


    //	  -Muistiviitteiden m‰‰r‰
    TTK91TaskCriteria memRefCriteria = taskOptions.getMemRefCriteria();
    if (memRefCriteria != null) {
	    TTK91Memory mem = controlPublicInputStudent.getMemory();
	    int memrefs = ((RandomAccessMemory)mem).getMemoryReferences();  // FIXME: UGLY hack (rajapintaongelma, IMO [LL])
	    boolean memrefsok = 
        checkMemRefCriteria(memrefs, 
                            memRefCriteria.getComparator(),
                            memRefCriteria.getSecondComparable());
	    results.setMemoryReferences(memrefsok);
    }
    //      -Vaaditut k‰skyt
    String[] requiredCommands = taskOptions.getRequiredCommands();
    if (requiredCommands != null) {
      System.err.println("L‰hdet‰‰n tutkimaan vaadittuja k‰skyj‰");

      boolean qualityCritFound = false;
      boolean requiredCritFound = false;
      boolean requiredCommandFound = true;
      boolean requiredQualityCommandFound = true;

      for (int i = 0; i < requiredCommands.length; ++i) {
        String[] needle = requiredCommands[i].split(",");
        if (needle.length > 1) {
          qualityCritFound = true;
          if (requiredQualityCommandFound) { // jos false, on jo rikki
            requiredQualityCommandFound = isCommandFound(answer[0], needle[1]); // Laatukriteeriss‰ komento taulukon toisessa alkiossa
          }
        }
        else {
          requiredCritFound = true;
          if (requiredCommandFound) { // jos false, on jo rikki
            requiredCommandFound = isCommandFound(answer[0], needle[0]); // Oikeellisuuskriteeriss‰ komento taulukon ensimm‰isess‰ alkiossa
          }
        }
        if (!requiredCommandFound && !requiredQualityCommandFound) {
          break; // jos molemmat jo rikki -> ei kelpaa
        }
            
        if (requiredCritFound) {
          results.setRequiredCommands(requiredCommandFound);
        }
        if (qualityCritFound) {
          results.setRequiredCommandsQuality(requiredQualityCommandFound);
        }
      }
    }

    //      -Kielletyt k‰skyt
    String[] forbiddenCommands = taskOptions.getForbiddenCommands();
    if (forbiddenCommands != null) {
      System.err.println("L‰hdet‰‰n tutkimaan kiellettyj‰ k‰skyj‰");
      
	    boolean qualityCritFound = false;
	    boolean forbiddenCritFound = false;
	    boolean forbiddenCommandFound = false;
	    boolean forbiddenQualityCommandFound = false;
      
      for (int i = 0; i < forbiddenCommands.length; ++i) {
        String[] needle = forbiddenCommands[i].split(",");
        if (needle.length > 1) {
          qualityCritFound = true;
          if (!forbiddenQualityCommandFound) { // jos true, on jo rikki
            forbiddenQualityCommandFound = isCommandFound(answer[0], needle[1]); // Laatukriteeriss‰ komento taulukon toisessa alkiossa
          }
        }
        else {
          forbiddenCritFound = true;
          if (!forbiddenCommandFound) { // jos true, on jo rikki
            forbiddenCommandFound = isCommandFound(answer[0], needle[0]); // Oikeellisuuskriteeriss‰ komento taulukon ensimm‰isess‰ alkiossa
          }
        }
        if (forbiddenCommandFound && forbiddenQualityCommandFound) {
          break; // jos molemmat jo rikki -> ei kelpaa
        }
        
        if (forbiddenCritFound) {
          results.setForbiddenCommands(!forbiddenCommandFound);
        }
        if (qualityCritFound) {
          results.setForbiddenCommandsQuality(!forbiddenQualityCommandFound); 
        }
        results.setForbiddenCommands(!forbiddenCommandFound);
      }
    }
  }//generalAnalysis


  /**
   * Tutkitaan muistipaikkojen ja muuttujien sis‰ltˆˆn liittyv‰t
   * kriteerit Toimintaperiaate: kaydaan muistipaikkoihin ja
   * muuttujiin liittyvia kriteereja lapi, kunnes joko 1) kaikki on
   * kayty lapi tai 2) seka jokin oikeellisuus- etta jokin
   * laatukriteeri on pettanyt. 
   *
   * FIXME: kuvaus uusiksi
   * @return Feedback palauttaa null, jos kaikki menee hyvin. Virhetilanteessa palautetaan virhett‰ kuvaava Feedback.
   */
  private Feedback analyseMemory(TTK91TaskCriteria[] memcrits, 
                                 TTK91AnalyseResults results, 
                                 RandomAccessMemory studentMem, 
                                 RandomAccessMemory teacherMem, 
                                 int compareMethod) {
    System.err.println("analyseMemoryn alku, compareMethod: "+compareMethod);
	
    if (memcrits == null) {
	    return null;  // jos muistikriteerit null, palataan suoraan (j‰tet‰‰n resultsiin arvot nulleiksi)
    }
    else {
	    
	    /* tutkitaan "helpot" virhetilanteet pois; opiskelijan
	       ratkaisun muistia ei ole k‰ytˆss‰, tai jos pit‰isi
	       verrata malliratkaisuun, eik‰ sit‰ ole k‰ytett‰viss‰
	    */
	    if ( ( (compareMethod == TTK91Constant.COMPARE_TO_SIMULATED) &&
             (teacherMem == null) ) || 
           (studentMem == null) ) {
        return new Feedback(TTK91Constant.FATAL_ERROR, "<br>analyseMemory: virhe parametreissa - rikkin‰inen teht‰v‰ tai bugi koodissa<br><br>");
	    } // if 	    


	    HashMap studentSymbolTable = studentMem.getSymbolTable();
	    System.err.println("analyseMemory: studentSymbolTable.size(): "+studentSymbolTable.size());
	    if (studentSymbolTable == null) {
        return new Feedback(TTK91Constant.FATAL_ERROR,
                            "<br>analyseMemory: opiskelijan ratkaisun"+
                            " symbolitaulua ei saatu - rikkin‰inen "+
                            "teht‰v‰ tai bugi koodissa<br><br>");
	    }
		
	    HashMap teacherSymbolTable = null;
	    if (compareMethod == TTK91Constant.COMPARE_TO_SIMULATED) {
        teacherSymbolTable = teacherMem.getSymbolTable();
        if (teacherSymbolTable == null) {
          return new Feedback(TTK91Constant.FATAL_ERROR,
                              "<br>analyseMemory: malliratkaisun "+
                              "symbolitaulua ei saatu - rikkin‰inen"+
                              " teht‰v‰ tai bugi koodissa<br><br>");
        }
	    }
	    
	    MemoryLine[] studentMemlines = studentMem.getMemoryLines();
	    if (studentMemlines == null) {
        return new Feedback(TTK91Constant.FATAL_ERROR,
                            "<br>analyseMemory: opiskelijan ratkaisun"+
                            " muistia ei saatu - rikkin‰inen teht‰v‰ "+
                            "tai bugi koodissa<br><br>");
	    }

	    MemoryLine[] teacherMemlines = null;
	    if (compareMethod == TTK91Constant.COMPARE_TO_SIMULATED) {
        teacherMemlines = teacherMem.getMemoryLines();
        if (teacherMemlines == null) {
          return new Feedback(TTK91Constant.FATAL_ERROR, 
                              "<br>analyseMemory: simuloidun "+
                              "malliratkaisun muistia ei saatu"+
                              " - rikkin‰inen teht‰v‰ tai bugi"+
                              " koodissa<br><br>");
        }
	    }
	    
	    boolean critvalue = true;
	    boolean qualitycritvalue = true;

	    for (int i=0; i < memcrits.length; ++i) {
        System.err.println("for-luupissa n‰in monetta kertaa: "+i);
        if (!critvalue) {
          break;                                     // Oikeellisuuskriteeri poksahtanut, ei syyta jatkaa.
        }

        TTK91TaskCriteria crit = memcrits[i];
        boolean isqualitycrit = crit.getQuality();

        if (isqualitycrit && !qualitycritvalue) { // tutkittavana laatukriteeri, mutta jokin laatukriteeri on jo poksahtanut
          continue;
        }

        String studentMemSymbol = crit.getFirstComparable();
        String compareMemSymbol = crit.getSecondComparable();
        int comparator = memcrits[i].getComparator();

        if (isqualitycrit) {
          qualitycritvalue = 
            checkMemoryCriteria(studentMemlines, 
                                teacherMemlines, 
                                studentSymbolTable,
                                teacherSymbolTable,
                                studentMemSymbol,
                                compareMemSymbol,
                                comparator,
                                compareMethod);
        } // if -- isqualitycrit
        else {
          System.err.println("Arvottiin jotta kyseess‰ on oikeellisuuskriteeri");
          System.err.println("Ja kriteeri on siis: "+crit);
          System.err.println("Ja compareMethod: "+compareMethod);
          critvalue = 
            checkMemoryCriteria(studentMemlines, 
                                teacherMemlines,
                                studentSymbolTable,
                                teacherSymbolTable,
                                studentMemSymbol,
                                compareMemSymbol,
                                comparator,
                                compareMethod);
          System.err.println("palattiin t‰nne, ja crivaluen pit‰isi olla sama kuin edell‰ comparen tulos, eli: "+critvalue);
        } // else -- !isqualitycrit
	    } // for
	    System.err.println("tultiin for-luupista ulos, ja critvalue on: "+critvalue);
	    results.setMemory(critvalue);
	    results.setMemoryQuality(qualitycritvalue);

    } // else
    System.err.println("Seuraavana onkin null-palautuksen vuoro...");
    return null; // ok-paluu, vaikka v‰h‰n hassulta kuulostaakin.
  } //analyseMemory

  /**
   * Apumetodi rekistereiden sis‰ltˆˆn liittyvien kriteerien analysointiin
   * 
   */
  private Feedback analyseRegisters(TTK91TaskCriteria[] regCrit,
                                    TTK91AnalyseResults results,
                                    TTK91Core controlPublicInputStudent,
                                    TTK91Core controlPublicInputTeacher) {

    
    if (regCrit == null) {
      return null; // jos ei rekisterikriteerej‰, ei ole analysoitavaakaan
    }

    TTK91Cpu studentCPU = null;
    TTK91Cpu teacherCPU = null;
    
    if (controlPublicInputStudent != null) {
      studentCPU = controlPublicInputStudent.getCpu();
    }
    else {
      return new Feedback(TTK91Constant.FATAL_ERROR, "Malliratkaisun "+
                          " suorituksessa k‰ytetty simulaattori kadonnut");
    }

    if (controlPublicInputTeacher != null) {
      teacherCPU = controlPublicInputTeacher.getCpu();
    }
    
    TTK91TaskCriteria crit;
    boolean studentOk = true;
    boolean teacherOk = true;
    boolean qualityOk = true;
    boolean critOk = true;
    boolean qualityCritFound = false;
    boolean realCritFound = false;
    
    for (int i=0; i < regCrit.length; ++i) {
      crit = regCrit[i];
      boolean isQuality = crit.getQuality();
      String regName = crit.getFirstComparable();
      String comp = crit.getSecondComparable();
      int comparator = crit.getComparator();
      int regValue = -1;
      
      if (isQuality && !qualityOk) {
        continue; // laatukriteeri, ja se on jo valmiiksi rikki
      }
      
      try {
        regValue = Integer.parseInt(comp);
      }
      catch (NumberFormatException e) {
        return new Feedback(TTK91Constant.FATAL_ERROR, "Rikkin‰inen kriteeri,"+
                            " String->int -muunnos ep‰onnistui: "+
                            e.getMessage());
      }
      
      if (isQuality) {
        qualityCritFound = true;
        regName = regName.substring(regName.indexOf(","), regName.length());
      }
      else {
        realCritFound = true;
      }
      int regNumber = -1;
      
      try {
        regNumber = Integer.parseInt(regName.substring(regName.indexOf("R")+1, 
                                                       regName.length()));
      }
      catch (NumberFormatException e) {
        return new Feedback(TTK91Constant.FATAL_ERROR, "Rikkin‰inen kriteeri,"+
                            " String->int -muunnos ep‰onnistui: "+
                            e.getMessage());
      }

      int regIndex = -1;

      switch (regNumber) {
        
      case 1:
        regIndex = TTK91Cpu.REG_R1;
        break;
      case 2:
        regIndex = TTK91Cpu.REG_R2;
        break;
      case 3:
        regIndex = TTK91Cpu.REG_R3;
        break;
      case 4:
        regIndex = TTK91Cpu.REG_R4;
        break;
      case 5:
        regIndex = TTK91Cpu.REG_R5;
        break;
      case 6:
        regIndex = TTK91Cpu.REG_R6;
        break;
      case 7:
        regIndex = TTK91Cpu.REG_R7;
        break;
      default:
        return new Feedback(TTK91Constant.FATAL_ERROR, "Virheellinen "+
                            "rekisteri: "+regName);
      }
      // nyt regIndex sis‰lt‰‰ indeksin, jolla saadaan varsinainen
      // rekisterin sis‰ltˆ selville
      // regValue-muuttujassa on vertauskriteerin‰ oleva arvo
      
      int studentRegValue = -1;
      int teacherRegValue = -1;

      studentRegValue = studentCPU.getValueOf(regIndex);
      studentOk = compare(studentRegValue, comparator, regValue);

      if (teacherCPU != null) {
        teacherRegValue = teacherCPU.getValueOf(regIndex);
        teacherOk = compare(teacherRegValue, comparator, regValue);
      }
      
      if (isQuality) {
        qualityOk = (studentOk && teacherOk);
      }
      else {
        critOk = (studentOk && teacherOk);
      }
      if (!critOk) {
        break; // oikeellisuus s‰rkyi, ei syyt‰ jatkaa
      }
    } // for
    
    if (realCritFound) {
      results.setRegisters(critOk);
    }
    if (qualityCritFound) {
      results.setRegistersQuality(qualityOk);
    }

    return null; // kaikki ok

    //results.setBLAAH(boolean)
  } //analyseRegisters

  /**
   * Apumetodi tulostekriteerien analysointiin
   *
   * @return virhetilanteessa palauteolio
   */
   
  private Feedback analyseOutput(TTK91TaskCriteria[] outCrits,
                                 String[] publicOutputs,
				 String[] hiddenOutputs,
				 boolean analysingCrt, 
				 Boolean analyseResult) {
      
//     TTK91TaskCriteria[] screenOut;
//     TTK91TaskCriteria[] fileOut;
    
//     if (taskOptions != null) {
//       screenOut = taskOptions.getScreenOutputCriterias();
//       fileOut = taskOptions.getFileOutputCriterias();
//     }
//     else {
//       return new Feedback(TTK91Constant.FATAL_ERROR, "analyseOutput(): "+
//                           "taskoptions null");
//     }

//     if ((screenOut == null) && (fileOut == null)) {
//       return null; // ei kriteerej‰, palataan
//     }

      if (outCrits == null) {
	  return null; // ei kriteerej‰, palataan
      }
      if (publicOutputs == null || analyseResult == null) {
	  return new Feedback(TTK91Constant.FATAL_ERROR, "analyseOutput(): "+
			      "Virhe tulosteiden analysoinnissa");
      }
      String separator = System.getProperty("line.separator", "\n");
    
      // outputtien parsinta

      String publicOutArr = null;
      String hiddenOutArr = null;

      int[] publicOutInt = null;
      int[] hiddenOutInt = null;

      if (publicOutputs != null) {
	  publicOutArr = publicOutputs.split(separator);
	  publicOutInt = new int[publicOutArr.length];
	  Feedback fb = 
	      parseOutputArrays(publicOutArr, publicOutInt);
	  if (fb != null) {
	      return fb;
	  }
      }

      if (hiddenOutputs != null) {
	  hiddenOutArr = hiddenOutputs.split(separator);
	  hiddenOutInt = new int[hiddenOutArr.length];
	  Feedback fb = 
	      parseOutputArrays(hiddenOutArr, hiddenOutInt);
	  if (fb != null) {
	      return fb;
	  }
      }

      for (int i=0; i < outCrits.length; ++i) {
	  TTK91TaskCriteria crit = outCrits[i];
	  String[] 

     //EN NYT MUISTA MIKƒ TAI MISTƒ [HT]
     //getCrt() [EN]
     // N‰yt‰ mulle mist‰ lˆytyy getCrt() muualta kuin kommenteista ;( [LL]
     // eli ainakin mun mielest‰ ne l‰htee irti metodilla read[Crt|File]()
     //NƒYT÷N TULOSTEET
     //TIEDOSTON TULOSTEET

    //results.setBLAAH(boolean)

    return null; // kaikki ok

  }//analyseOutput


  /**
   * Apumetodi String-muodossa olevan numerotaulukon muuntoon int-taulukoksi
   * L‰hdetaulukon ja kohdetaulukon tulee olla saman kokoiset.
   * @param from l‰hdetaulukko
   * @param to kohdetaulukko
   * @return virhetilanteessa Feedback-olio, joka kuvaa virhett‰, null muuten
   * 
   */

  private Feedback parseOutputArrays(String[] from, int[] to) {
    if ( (from == null) || (to == null) || (from.length != to.length) ) {
      return new Feedback(TTK91Constant.FATAL_ERROR, "parseOutputArrays: "+
                          "Analyserin sis‰inen virhe, parametrivirhe");
    }
    
    for (int i=0; i < from.length; i++) {
      try {
        to[i] = Integer.parseInt(from[i]);
      }
      catch (NumberFormatException e) {
        return new Feedback(TTK91Constant.FATAL_ERROR, "parseOutputArrays: "+
                            "String->int muunnos ep‰onnistui");
      }
    } // for

    return null; // kaikki ok
  } // parseOutputArrays


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


  /**
   * Tarkistaa muistiviitekriteerin
   * @param memrefs
   * @param comparator
   * @param secondcomparable
   */
  private boolean checkMemRefCriteria(int memrefs, int comparator, String secondcomparable) {
    System.err.println("MUISTIVIITTEITƒ: memrefs:"+memrefs+" comparator:"+comparator+"secondcomparable:"+secondcomparable);
    boolean ret = false;
    int checkAgainstMe = -1;
    try {
	    checkAgainstMe = Integer.parseInt(secondcomparable);
    }
    catch (NumberFormatException e) {
	    // rikkin‰inen kriteeri - ei pit‰isi tapahtua
	    throw new RuntimeException("TTK91Analyser.checkMemRefCriteria(): Broken criteria, String to int conversion failed");
    }
	
    ret = compare(memrefs, comparator, checkAgainstMe);
	
    return ret;
  } // checkMemRefCriteria
    
    
    /**
     * Tarkistaa lˆytyykˆ vastauksesta toisen parametrin osoittama k‰sky
     * @param answer
     * @param cmd
     */
  private boolean isCommandFound(String answer, String cmd) {

    System.err.println("Saavuttiin isCommandFound()iin");

    if ((answer != null) && (cmd != null)) {
      System.err.println("P‰‰stiin sis‰‰n varsinaiseen isCommandFound()iin, eli alkuehdot kunnossa");
      
      String haystack = answer.toLowerCase();
      String needle = cmd.toLowerCase();
      
      needle = needle.substring(needle.indexOf("(")+1); // jos avaava sulje lˆytyy, poistetaan
      int temp = needle.indexOf(")");
      if (temp > 0) {
        needle = needle.substring(0, temp); // poistetaan mahdollinen loppusulje
      }
      temp = needle.indexOf(";");
      if (temp > 0) {
        needle = needle.substring(0, temp); // poistetaan mahdollinen ';'
      }

      System.err.println("needle: |"+needle+"|");
      System.err.println("valmiina ajamaan matchays...");
      
      temp = answer.indexOf(needle);
      System.err.println("matchayksen tulos (intin‰), eli palautetaan (temp >= 0): "+temp);
      return (temp >= 0);
    }
    System.err.println("isCommandFound: answer != null: "+
                       (answer != null)+", cmd != null: "+(cmd != null));
    return false; // jotain meni pieleen, mutta ei nyt osata kertoa sit‰ tarkemmin...
  } // isCommandFound

    
    /**
     * Apumetodi muistipaikkaan tai muuttujaan liittyvan kriteerin
     * tarkastamiseen
     */
  private boolean checkMemoryCriteria(MemoryLine[] studentMemLines, 
                                      MemoryLine[] compareMemLines,
                                      HashMap studentSymbolTable, 
                                      HashMap compareSymbolTable, 
                                      String studentMemSymbol, 
                                      String compareMemSymbol, 
                                      int comparator, 
                                      int compareMethod) {

    System.err.println("compareMethod: "+compareMethod);

    /* kriteerin ensimm‰inen "muistipaikka" (studentMemSymbol) on
	   *aina* ep‰suora - on sis‰ltˆn‰ sitten muistipaikan nimi tai
	   osoite. Kriteerin toinen "muistipaikka" (compareMemSymbol)
	   taas voi olla ep‰suoraa tai suoraa k‰sittely‰. Ep‰suora
	   k‰sittely jos ja vain jos vertaillaan mallivastaukseen,
	   ilman mallivastausvertailua kriteerin toista muistipaikkaa
	   k‰sitell‰‰n suoraan int-arvona. */
	
    boolean secondIsDirect = true; 
    boolean studentMemSymbolAsInt = true;
    boolean compareMemSymbolAsInt = true;
	
    int studentMemValue = -1;
    int studentMemAddress = -1;
    int compareMemValue = -1;
    int compareMemAddress = -1;

    for (int i=0; i<10; ++i) {
	    System.err.println("MemLine "+i+":"+studentMemLines[i].getBinary());
    }

    System.err.println("studentMemSymbol: "+studentMemSymbol);
	
    try {
	    studentMemAddress = Integer.parseInt(studentMemSymbol);
    }
    catch (NumberFormatException e) {
	    System.err.println("T‰m‰n ei pit‰isi tulostua");
	    studentMemSymbolAsInt = false;
    }

    int index = -1;
    MemoryLine memline = null;
    if (!studentMemSymbolAsInt) { // ei int-arvoa == oletetaan ett‰ kyseess‰ muistipaikan nimi
	    Integer indexI = null;
	    indexI = (Integer) studentSymbolTable.get(studentMemSymbol);
	    java.util.Set keySet = studentSymbolTable.keySet();
	    java.util.Iterator keyIt = keySet.iterator();
	    int i=0;
	    System.err.println("studentSymbolTablen koko: "+studentSymbolTable.size());
	    while (keyIt.hasNext())
        System.err.println((String) keyIt.next());

	    if (indexI != null) {
        index = indexI.intValue();
        if ( (index >= 0) && (index < studentMemLines.length) ) {
          memline = studentMemLines[index];
        } // tuonkin ehdon pett‰miseen voisi ehk‰ varautua. Titokone k‰yt‰nnˆss‰ pit‰nee huolen, ettei huonosti k‰y.
        studentMemValue = memline.getBinary();
	    }
	    else {
        // FIXME: k‰sittele; kriteeri kosahtaa, koska symbolitaulusta ei moista alkiota lˆytynyt
        System.err.println("T‰nne kapsahdettiin");
        return false;
	    }
    }
    else {
	    System.err.println("Tullaan else-haaraan rivill‰ 698"+"ja studentMemAddress: "+studentMemAddress+" ja studentMemLines.length on: "+studentMemLines.length);
	    if ( (studentMemAddress >= 0) && 
           (studentMemAddress < studentMemLines.length) ) {
        System.err.println("Joten p‰‰dyt‰‰n t‰nne (701), ja poimitaan muistirivi...");
        memline = studentMemLines[studentMemAddress];
        studentMemValue = memline.getBinary();
        System.err.println("Muistipaikan arvo on siis: "+studentMemValue);
	    }
	    else {
        // FIXME: k‰sittele; kriteerin pit‰isi kosahtaa, koska saatu muistiosoitteen arvo ei pysy muistin rajojen sis‰puolella.
	    }
    }

    // T‰ss‰ vaiheessa on t‰m‰n kriteerin opiskelijan ratkaisun vertailtavan muistipaikan sis‰ltˆ kaivettu esiin

    System.err.println("compareMethod (rivi 713): "+compareMethod);
    if (compareMethod == TTK91Constant.COMPARE_TO_STATIC) {
	    System.err.println("Eli compare_to_static");
	    try { 
        compareMemValue = Integer.parseInt(compareMemSymbol);
	    }
	    catch (NumberFormatException e) {
        System.err.println("Catched! (731)");
        // FIXME: syntaxchecker on feilannut, pit‰isi olla kelvollinen intti
	    }
    }
    else { // eli compareMethod == TTK91Constant.COMPARE_TO_SIMULATED
	    try {
        compareMemAddress = Integer.parseInt(compareMemSymbol);
	    }
	    catch (NumberFormatException e) {
        compareMemSymbolAsInt = false;
	    }
	    
	    int index2 = -1;
	    MemoryLine memline2 = null;
	    if (!compareMemSymbolAsInt) { // ei int-arvoa == oletetaan ett‰ kyseess‰ muistipaikan nimi
        Integer indexI = null;
        indexI = (Integer) compareSymbolTable.get(compareMemSymbol);
        if (indexI != null) {
          index2 = indexI.intValue();
          if ( (index2 >= 0) && (index2 < compareMemLines.length) ) {
            memline2 = compareMemLines[index2];
          } // tuonkin ehdon pett‰miseen voisi ehk‰ varautua. Titokone k‰yt‰nnˆss‰ pit‰nee huolen, ettei huonosti k‰y.
        }
        else {
          // FIXME: k‰sittele; kriteeri kosahtaa, koska symbolitaulusta ei moista alkiota lˆytynyt
        }
        compareMemValue = memline2.getBinary();
	    }
	    else {
        if ( (compareMemAddress >= 0) && 
             (compareMemAddress < compareMemLines.length) ) {
          memline2 = compareMemLines[compareMemAddress];
          compareMemValue = memline2.getBinary();
        }
        else {
          // FIXME: k‰sittele; kriteerin pit‰isi kosahtaa, koska saatu muistiosoitteen arvo ei pysy muistin rajojen sis‰puolella.
        }
	    }
    } // else
    System.err.println("Ennen compare()-kutsua"+"ja valuet:"+studentMemValue+","+comparator+","+compareMemValue);
    boolean testi = compare(studentMemValue, comparator, compareMemValue);
    System.err.println("comparen tulos: "+testi);
    return testi;
    //	return compare(studentMemValue, comparator, compareMemValue);

  } // checkMemoryCriteria


    /**
     * Apumetodi, palauttaa first <op> second, miss‰ <op> sis‰lt‰‰
     * normaalit aritmeettiset vertailuoperaatiot
     * @param first - ensimm‰inen vertailtava
     * @param comparator - vertailuoperaattori, m‰‰ritykset TTK91Constant-luokassa
     * @param second - toinen vertailtava
     */
  private boolean compare(int first, int comparator, int second) {
	
    boolean ret = false;
	
    switch (comparator) { 
	    
    case TTK91Constant.LESS:
	    ret = (first < second);
	    break;
    case TTK91Constant.LESSEQ:
	    ret = (first <= second);
	    break;
    case TTK91Constant.GREATER: 
	    ret = (first > second);
	    break;
    case TTK91Constant.GREATEREQ: 
	    ret = (first >= second);
	    break;
    case TTK91Constant.EQUAL: 
	    ret = (first == second);
	    break;
    case TTK91Constant.NOTEQUAL: 
	    ret = (first != second);
	    break;
    default:
	    // tanne ei pitaisi paasta koskaan
    }
	
    return ret;
	
  } // compare
    
} // StaticTTK91Analyser
