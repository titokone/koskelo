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
    private TTK91Application studentApplication; // opiskelijan vastaus
    private TTK91Application teacherApplication; // malliratkaisu
    private TTK91AnalyseResults results; //Uusi luokka.
    private TTK91FeedbackComposer fbcomposer;
    private Feedback feedback;
    private TTK91AnalyserUtils utils;

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
	    throw new RuntimeException("*TTK91Analyser.getTTK91TaskOptions()->CacheException: "+ce.getMessage());
	}
	catch (InvalidTTK91CriteriaException ie) {
	    throw new RuntimeException("*TTK91Analyser.getTTK91TaskOptions()->InvalidTTK91CriteriaException: "+ie.getMessage());
	}	    

	getTeacherApplication(answer); // k‰‰nnet‰‰n mahdollinen malliratkaisu 
	// FIXME: v‰‰r‰ parametri
	getStudentApplication(answer); // k‰‰nnet‰‰n opiskelijan ratkaisu

	boolean runnedOK = false;
	runnedOK = run(); // varsinainen ratkaisu(je)n simulointi
	if (!runnedOK) { // jos true, ajo(t) meniv‰t ok -> jatketaan. Virheist‰ generoidaan palaute ja lopetetaan.
	    return feedback;
	}

	//Seuraavat metodit asettavat TTK91AnalyseResultsiin tulokset

	generalAnalysis(answer); // "valmis"

	analyseMemory(); // Lauri "valmis" ?
	//analyseRegisters()
	//analyseOutput()
    
	//Aseta statistiikat resultsiin TKK91Memorysta ja CPU:sta
    
	try {
	    feedback = 
		TTK91FeedbackComposer.formFeedback(results, 
						   taskOptions.getTaskFeedback(), 
						   cache, 
						   taskID, 
						   language);
	}
	catch (CacheException e) {
	    feedback = 
		TTK91FeedbackComposer.formFeedback("Error while retrieving "+
						   "error message :( "+
						   e.getMessage() );
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

	TTK91CompileSource src = parseSourceFromAnswer(answer);

	if (src == null) {
	    // return new Feedback(); 
	    // FIXME: oikeanlainen palaute kun sorsa
	    // ei suostu menem‰‰n edes
	    // TTK91CompileSource-muotoon - voiko
	    // n‰in edes k‰yd‰?
	}//if


	TTK91Application app = null;

	if (controlCompiler == null) {
	    controlCompiler = new Control(null, null);
	}

	try {
	    app = controlCompiler.compile(src);
	} catch (TTK91Exception e) {
	    //	    // FIXME: oikeanlainen palaute, kun
	    // k‰‰nnˆs ep‰onnistuu
	    throw new RuntimeException("*TTK91Analyser.getStudentApplication: "+e.getMessage());
	}//catch

	this.studentApplication = app;

    }//getStudentApplication


    /**
     * Apumetodi, jolla kaivetaan malliratkaisun l‰hdekoodi vastauksesta
     * ja k‰‰nnet‰‰n siit‰ TTK91Application
     * @param answer
     */

    private void getTeacherApplication(String[] answer) {


	TTK91CompileSource src = parseSourceFromAnswer(answer);

	if (src == null) {
	    //	    return new Feedback(); 
	    // FIXME: oikeanlainen palaute kun sorsa
	    // ei suostu menem‰‰n edes
	    // TTK91CompileSource-muotoon - voiko
	    // n‰in edes k‰yd‰?
	    return;
	}//if

	TTK91Application app = null;

	if (controlCompiler == null) {
	    controlCompiler = new Control(null, null);
	}
	
	try {
	    app = controlCompiler.compile(src);
	} catch (TTK91Exception e) {
	    //	    return new Feedback(); 
	    //	    // FIXME: oikeanlainen palaute, kun
	    // k‰‰nnˆs ep‰onnistuu
	} catch (NullPointerException ne) {
	    throw new RuntimeException("*TTK91Analyser.getTeacherApplication():"+ne.getMessage());
	}//catch
	

	this.teacherApplication = app;

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

	//  -Suoritettujen konek‰skyjen m‰‰r‰ (oikeellisuus)
	TTK91Cpu cpu = controlPublicInputStudent.getCpu();
	int size = ((Processor)cpu).giveCommAmount(); // FIXME: UGLY hack (rajapintaongelma, IMO [LL])
	int sizeLimit = taskOptions.getMaxCommands();
	results.setAcceptedSize(size <= sizeLimit);


	//	  -Ihannekoko (laatu)
	results.setOptimalSize(size <= taskOptions.getOptimalSize());


	//	  -Muistiviitteiden m‰‰r‰
	TTK91TaskCriteria memRefCriteria = taskOptions.getMemRefCriteria();
	if (memRefCriteria == null) {
	    results.setRequiredCommands(true);
	    results.setForbiddenCommands(true);
	} // if
	else {
	    TTK91Memory mem = controlPublicInputStudent.getMemory();
	    int memrefs = ((RandomAccessMemory)mem).getMemoryReferences();  // FIXME: UGLY hack (rajapintaongelma, IMO [LL])
	    boolean memrefsok = 
		checkMemRefCriteria(memrefs, 
				    memRefCriteria.getComparator(),
				    memRefCriteria.getSecondComparable());
	    results.setMemoryReferences(memrefsok);
	
	    //      -Vaaditut k‰skyt
	    boolean requiredCommandFound = 
		isCommandFound(answer, taskOptions.getRequiredCommands());
	
	    results.setRequiredCommands(requiredCommandFound);
	
	    //      -Kielletyt k‰skyt
	    boolean forbiddenCommandFound = 
		isCommandFound(answer, taskOptions.getForbiddenCommands());
	
	    results.setForbiddenCommands(!forbiddenCommandFound);
	} // else
    }//generalAnalysis

    /**
     * Tutkitaan muistipaikkojen ja muuttujien sis‰ltˆˆn liittyv‰t
     * kriteerit Toimintaperiaate: kaydaan muistipaikkoihin ja
     * muuttujiin liittyvia kriteereja lapi, kunnes joko 1) kaikki on
     * kayty lapi tai 2) seka jokin oikeellisuus- etta jokin
     * laatukriteeri on pettanyt. 
     */
    private void analyseMemory() {

	TTK91TaskCriteria[] memcrits = taskOptions.getMemoryCriterias();
	if (memcrits == null) {
	    results.setMemory(true); // ei kriteereja --> oikein
	    results.setMemoryQuality(true); // ei kriteereja --> oikein
	} // if
	else {
	    RandomAccessMemory mem = 
		(RandomAccessMemory) controlPublicInputStudent.getMemory();
	    HashMap symboltable = mem.getSymbolTable();
	    MemoryLine[] memlines = mem.getMemoryLines();
	    
	    boolean critvalue = true;
	    boolean qualitycritvalue = true;

	    for (int i=0; i < memcrits.length; ++i) {
		if (!critvalue && !qualitycritvalue) {
		    break;                                     // Seka oikeellisuus- etta laatukriteerit ovat jo poksahtaneet, ei syyta jatkaa.
		}
		TTK91TaskCriteria crit = memcrits[i];
		boolean isqualitycrit = crit.getQuality();
		if ( (!isqualitycrit && !critvalue) ||         // oikeellisuuskriteeri ja jokin aiempi oikeellisuuskriteeri on jo poksahtanut
		     (isqualitycrit && !qualitycritvalue) ) {  // laatukriteeri ja jokin aiempi laatukriteeri on jo poksahtanut
		    continue;
		}
		String memsymbol = crit.getFirstComparable();
		int memvalue = -1;
		try {
		    memvalue = 
			Integer.parseInt(memcrits[i].getSecondComparable());
		} // try
		catch (NumberFormatException e) {
		    // rikkin‰inen kriteeri - ei pit‰isi tapahtua
		    throw new
			RuntimeException("TTK91Analyser.analyseMemory(): "+
					 "Broken criteria, String to int "+
					 "conversion failed");
		} // catch
		int comparator = memcrits[i].getComparator();
		if (isqualitycrit) {
		    qualitycritvalue = 
			checkMemoryCriteria(memlines, 
					    symboltable,
					    memsymbol,
					    comparator,
					    memvalue);
		} // if -- isqualitycrit
		else {
		    critvalue = 
			checkMemoryCriteria(memlines, 
					    symboltable,
					    memsymbol,
					    comparator,
					    memvalue);
		} // else -- !isqualitycrit
	    } // for
	    
	    results.setMemory(critvalue);
	    results.setMemoryQuality(qualitycritvalue);

	} // else
	
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


    /**
     * Tarkistaa muistiviitekriteerin
     * @param memrefs
     * @param comparator
     * @param secondcomparable
     */
    private boolean checkMemRefCriteria(int memrefs, int comparator, String secondcomparable) {
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
     * Tarkistaa lˆytyykˆ vastauksesta toisen parametrin osoittamia k‰skyj‰
     * @param answer
     * @param cmds
     */
    private boolean isCommandFound(String[] answer, String[] cmds) {
	if ((answer != null) && (answer[0] != null) && (cmds != null)) {
	    String src = answer[0].toLowerCase();
	    for (int i=0; i < cmds.length; ++i) {
		String pat = "\\s"+cmds[0].toLowerCase()+"\\s";
		if ( Pattern.matches(pat, src) ) {
		    // sis‰lt‰‰kˆ src merkkijonoa "whitespace+komento+whitespace" ?
		    return true;
		}
	    }
	}
	return false;
    } // isCommandFound
    
    /**
     * Apumetodi muistipaikkaan tai muuttujaan liittyvan kriteerin
     * tarkastamiseen
     * @param memlines
     * @param symboltable
     * @param memsymbol
     * @param comparator
     * @param memvalue
     */
    private boolean checkMemoryCriteria(MemoryLine[] memlines, 
					HashMap symboltable, 
					String memsymbol, 
					int comparator, 
					int memvalue) {

	int index = -1;
	MemoryLine memline = null;
	Integer indexI = (Integer) symboltable.get(memsymbol);
	
	if (indexI != null) {
	    index = indexI.intValue();
	    if ( (index >= 0) && (index < memlines.length) ) {
		memline = memlines[index];
	    }
	}
	else {
	    // avaimella 'memsymbol' ei lˆytynyt alkiota symbolitaulusta FIXME: k‰sittele
	}
	
	int memlinevalue = memline.getBinary();

	return compare(memlinevalue, comparator, memvalue);
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
