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
import fi.helsinki.cs.koskelo.common.TTK91TaskOptions;
import fi.helsinki.cs.koskelo.common.TTK91Constant;

/**Kerää tehtävän analysoinnissa tarvittavat tiedot, 
 * ja jakaa ne varsinaiselle analysointikoneistolle. 
 * Luokan ilmentymää luotaessa suoritetaan tarvittavat simuloinnit
 * Titokoneella.
 */

public class TTK91AnalyseData{


	// Controllit joilta saadaan dataa:

	private TTK91Core controlCompiler = null;    
	private TTK91Core controlPublicInputStudent = null;
	// publicinputeilla tai ilman inputteja opiskelijan vastaus
	private TTK91Core controlPublicInputTeacher = null; 
	// publicinputeilla tai ilman inputteja malliratkaisu jos vertailu on määritelty simuloitavaksi
	private TTK91Core controlHiddenInputStudent = null;
	// hiddeninputeilla jos ovat määritelty opiskelijan vastaus
	private TTK91Core controlHiddenInputTeacher = null;
	// hiddeninputeilla jos ovat määritelty malliratkaisu jos vertailu on määritelty simuloitavaksi

	// Tehtävänmäärittelydata
	private TTK91TaskOptions taskOptions = null;        // taskoptions

	// Malliratkaisu

	private String exampleCode = null;                // haetaan taskoptionsista - malliratkaisun koodi
	private String answer = null;

	// Virheilmoitukset

	private boolean errors = false;
	private String studentCompileError = null;
	private String teacherCompileError = null;
	private String studentRunError = null;
	private String teacherRunError = null;

	// Käännetyt koodit
	private TTK91Application studentApplicationPublic = null; // opiskelijan vastaus
	private TTK91Application teacherApplicationPublic = null; // malliratkaisu
	private TTK91Application studentApplicationHidden = null; // opiskelijan vastaus
	private TTK91Application teacherApplicationHidden = null; // malliratkaisu


	// Analysointitapa
	private int compareMethod = -1;


	// Optionsin muuttujia:

	private int steps = 0;
	private String publicInput = null;
	private String hiddenInput = null;


	// Opiskelijan tietokoneen statistiikkamuuttujat

	private int dataSegmentSize;
	private int codeSegmentSize;

	//Analysointia varten

	private int maxStackSize;
	private int usedCommands;
	private int memoryReferences;
	private int hiddenUsedCommands;
	private int hiddenMemoryReferences;


	// Titokoneiden muistit (FIXME, näitä ei taideta tarvita?):

//	private    RandomAccessMemory studentPublicMemory = null; 
//	private    RandomAccessMemory teacherPublicMemory = null; 
//	private    RandomAccessMemory studentHiddenMemory = null; 
//	private    RandomAccessMemory teacherHiddenMemory = null;


	/** Luodaan uusi TTK91AnalyseDAta, jolle annetaan parametreina 
	 * tehtävänperustiedot, opiskelijan vastaus ja malliratkaisu.
	 * Malliratkaisu annetaan erikseen, jotta sitä voideen erikseen
	 * esikäsitellä analyserissa. Konstruktori suorittaa varsinaiset
	 * tarvittavat simuloinnit titokoneessa.
	 *
	 * @param taskOptions Tehtävän kriteerit ja perustiedot
	 * @param answer Opiskelijan vastaus
	 * @param exampleCode Malliratkaisu, jos sellaista on
	 */

	public TTK91AnalyseData(
			TTK91TaskOptions taskOptions,
			String answer, 
			String exampleCode
			) {

		this.taskOptions = taskOptions;
		this.answer = answer;
		this.exampleCode = exampleCode;

		compileTeacherApplication();
		compileStudentApplication();
		getTaskData();

		// compilet ovat voineet päättyä virheeseen
		if(!this.errors) {
			run();
		}

		// ohjelmien ajo on voinut päättyä virheeseen
		if(!this.errors) {
			setStatistics();
		}
	}

	/** Statistiikkojen kaivelua muuttujiin, siivottu yhteen metodiin.
	 */

	private void setStatistics() {

		if(controlPublicInputStudent != null && !errors) {

			// FIXME, tämä on titokoneen rajapinnan puutteen kiertoa!!
			RandomAccessMemory ram = (RandomAccessMemory) 
				controlPublicInputStudent.getMemory();

			Processor cpu = (Processor)
				controlPublicInputStudent.getCpu(); 

			usedCommands = cpu.giveCommAmount();
			maxStackSize = cpu.giveStackMaxSize();
			memoryReferences = ram.getMemoryReferences();
			codeSegmentSize = ram.getCodeAreaSize();
			dataSegmentSize = ram.getDataAreaSize();

			if(controlHiddenInputStudent != null) {

				// FIXME, tämä on titokoneen rajapinnan puutteen kiertoa!!
				ram = (RandomAccessMemory) 
					controlHiddenInputStudent.getMemory();

				cpu = (Processor)
					controlHiddenInputStudent.getCpu(); 

				hiddenUsedCommands = cpu.giveCommAmount();
				hiddenMemoryReferences = ram.getMemoryReferences();

			} 

		}
	}

	/** Malliratkaisun kääntäminen. Malliratkaisua ei käännetä jos malliratkaisu on null.
	 */
	
	private void compileTeacherApplication() {


		// compile teacherapp

		TTK91CompileSource src = null;

		if(exampleCode == null) {
			return; 
		}


		src = (TTK91CompileSource) new Source(exampleCode);

		if (src == null) {
			this.teacherCompileError = "Teacher compile error: Malliratkaisua ei pystytty"+
				" muuntamaan TTK91CompileSource-muotoon";
			this.errors = true;
			return;
		}//if


		TTK91Application app = null;

		if (controlCompiler == null) {
			controlCompiler = new Control(null, null);
		}

		try {
			app = controlCompiler.compile(src);
		} catch (TTK91Exception e) {
			this.teacherCompileError = "Teacher compile error:" + 
				e.getMessage();
			this.errors = true;
			return;
		}//catch

		this.teacherApplicationPublic = app;

		try {
			app = controlCompiler.compile(src);
		} catch (TTK91Exception e) {
			this.teacherCompileError = "Teacher compile error:" + 
				e.getMessage();
			this.errors = true;
			return;
		}//catch

		this.teacherApplicationHidden = app;

		// get the student app and the
		// teacher app and make them into applications

	}

	/** Opiskelijan ohjelman kääntäminen. 
	 * Jos opiskelijan ohjelmaa ei ole, palautetaan virhe.
	 */
	
	private void compileStudentApplication() {


		// compile studentapp

		TTK91CompileSource src = null;

		if (this.answer != null) {
			String ans = this.answer;
			ans = ans + "\n SVC SP, =HALT";
			src = (TTK91CompileSource) new Source(ans);
			// FIXME: toimiiko tosiaan näin helposti?
		} else {
			this.errors = true;
			this.studentCompileError = "Student compile error: Ratkaisu puuttuu";
			return;
		
		}

		if (src == null) {
			this.studentCompileError = "Student compile error: Ratkaisua ei pystytty"+
				" muuntamaan TTK91CompileSource-muotoon";
			this.errors = true;
			return;
		}//if


		TTK91Application app = null;

		if (controlCompiler == null) {
			controlCompiler = new Control(null, null);
		}

		try {
			app = controlCompiler.compile(src);
		} catch (TTK91Exception e) {
			this.studentCompileError = "Student compile error:" + 
				e.getMessage();
			this.errors = true;
			return;
		}//catch

		this.studentApplicationPublic = app;

		try {
			app = controlCompiler.compile(src);
		} catch (TTK91Exception e) {
			this.studentCompileError = "Student compile error:" + 
				e.getMessage();
			this.errors = true;
			return;
		}//catch

		this.studentApplicationHidden = app;

		// get the student app and the
		// teacher app and make them into applications

	}

	/** Piilotettu taskOptionsista erikseen datan hakeminen tänne.
	 */
	
	private void getTaskData() {
		this.publicInput = parseInputString(
				this.taskOptions.getPublicInput()
				);
		this.hiddenInput = parseInputString(
				this.taskOptions.getHiddenInput()
				);

		this.steps = taskOptions.getMaxCommands();
		this.compareMethod = taskOptions.getCompareMethod();
	}


/** Rakennetaan coret analysointia varten. Ei simuloida turhia
 * coreja, jos sellaisia ei kaivata, koska myöskään myöhemmin
 * ei haluta opemuistina muuta kuin null jo simulointia ei ole.
 */
	
	private void run() {


		/* Koska titokoneesta metodilla .getCPU() saadaan
		 * vain viite controlin sisäiseen prosessoriin käytetään
		 * yhteensä maksissaan neljää controlia, kuitenkin
		 * siten, että kullekin simulointikierrokselle
		 * luodaan oma controlinsa. Kannattaa myöskin
		 * huomata että applicationeja on neljä,
		 * sen takia, että outputit saadaan sieltä myöhemmin
		 * ja niiden kaikkien simuloinnit tahdotaan tehdä kerralla.
		 * Myöskin muun applicationista haettavan datan kannalta
		 * mahdollisesti myöhemmin tästä voi olla hyötyä.
		 */

		if(publicInput != null) {
			this.studentApplicationPublic.setKbd(publicInput);
			if (compareMethod == TTK91Constant.COMPARE_TO_SIMULATED) {
			    this.teacherApplicationPublic.setKbd(
								 publicInput
								 );
			}
		}

		this.controlPublicInputStudent = new Control(null, null);

		try {
			this.controlPublicInputStudent.run(
					this.studentApplicationPublic, 
					steps
					);
			// 1. simulointi
		} catch (TTK91Exception e) {
		    this.studentRunError = "Student run error:"+ 
			e.getMessage();
		    this.errors = true;
		    return;
		}

		if (compareMethod == TTK91Constant.COMPARE_TO_SIMULATED) {
			// 1. simulointi malliratkaisua
			this.controlPublicInputTeacher = new Control(null, null);
			
			try {
				this.controlPublicInputTeacher.run(
						this.teacherApplicationPublic,
						steps
						);
			} catch (TTK91Exception e) {
				this.teacherRunError = "Teacher run error:" +
					e.getMessage();
				this.errors = true;
				return;
			}
		}


		if (hiddenInput != null) {
			// mahdollinen 2. simulointi opiskelijan ratkaisusta

			this.controlHiddenInputStudent = new Control(null, null); // luodaan control vain jos hiddeninput määritelty --> "optimointia"
			this.studentApplicationHidden.setKbd(hiddenInput);

			try {
				this.controlHiddenInputStudent.run(
						this.studentApplicationHidden,
						steps
						);

			} catch (TTK91Exception e) {
				this.studentRunError = "Student run error:" + 
					e.getMessage();
				this.errors = true;
				return;
			}

			if(compareMethod == TTK91Constant.COMPARE_TO_SIMULATED) {
				// simuloidaa malliratkaisu
				// 2. simulointi malliratkaisua

				this.controlHiddenInputTeacher = new Control(null, null); // luodaan control vain jos hiddeninput määritelty --> "optimointia"
				this.teacherApplicationHidden.setKbd(
						hiddenInput
						);
				try {
					this.controlHiddenInputTeacher.run(
							this.teacherApplicationHidden,
							steps
							);
				} catch (TTK91Exception e) {
					teacherRunError = "Teacher run error:" +
						e.getMessage();
					this.errors = true;
					return;
				}
			}
		} 

	}



	/** Parsitaan int[] taulukosta titokoneelle soveliaaseen
	 * muotoon input. Periaatteessa sama muoto kuin alunperin kriteereissä.
	 */
	private String parseInputString(int[] inputTable) {

	    if (inputTable == null) {
		return null;
	    }
		String input = "";
		
		for(int i = 0; i < inputTable.length-1; i++) {
			input = input + inputTable[i] +",";
		}
		if (inputTable.length > 0) {
		    input = input + inputTable[inputTable.length-1];
		}

		if(input.equals("")){
			return null;
		} else {
			return input;
		}
	}

	/* Seuraavat getterit ovat datan saamiseksi analyserissa, 
	 * realAnalyserissa jne.*/

	/**
	 * Palauttaa mahdolliset Titokoneen käännösaikaiset virheilmoitukset käännettäessä opiskelijan ratkaisua.
	 */
	public String getStudentCompileError() {
		return this.studentCompileError;
	}

	/** Palauttaa mahdolliset 
	 * Titokoneen käännösaikaiset virheilmoitukset 
	 * käännettäessä malliratkaisua.
	 */
	public String getTeacherCompileError() {
		return this.teacherCompileError;
	}

	/**Palauttaa mahdolliset 
	 * Titokoneen ajonaikaiset 
	 * virheilmoitukset opiskelijan ratkaisua simuloitaessa.
	 */
	
	public String getStudentRunError() {
		return this.studentRunError;
	}

	/**Palauttaa mahdolliset Titokoneen 
	 * ajonaikaiset virheilmoitukset malliratkaisua simuloitaessa.
	 */
	public String getTeacherRunError() {
		return this.teacherRunError;
	}

	/** Näin analyser saa kaikki neljä virheilmoitusta siististi kerralla.
	 * 
	 *Palauttaa kaikki edellämainitut virheilmoitukset String-taulukkona.
	 * Käytännössä vain yksi on käytössä, sillä jos teacher ei käänny,
	 * turha yritää opiskelijaa, jos opiskelija ei käänny, turha mitään on
	 * ajaa, jos opiskelijaa ei voida ajaa, 
	 * turha yrittää saada sitä vertailtavaan
	 * tilaan
	 */
	public String[] getErrorMessages() { //FIXME rumaa!
		String[] errors = new String[4];
		errors[0] = studentCompileError;
		errors[1] = teacherCompileError;
		errors[2] = studentRunError;
		errors[3] = teacherRunError;

		return errors;
	}

	/** Palauttaa true, jos käännös- tai ajonaikaisia virheitä.
	 */
	public boolean errors() {
		return this.errors;
	}
	
	/** Käytetäänkö vaihtoehtoisia, 
	 * opiskelijalle näkymättömiä syötteitä simulaatiossa.
	 */
	public boolean compareToHidden() {
		return (hiddenInput != null);
	}

	/** Palauttaa opiskelijan vastauksen.
	 */
	public String getAnswer() {
		return this.answer;
	}

	/** Palauttaa opiskelijan joko syötteettömän tai 
	 * julkisilla syötteillä simuloidun opiskelijan
	 * ratkaisusta saadun sovelluksen
	 */
	public TTK91Application getStudentAppPublic() {
		return this.studentApplicationPublic;
	}

	/** Palauttaa julkisilla tai ilman syötteitä simuloidun
	 * malliratkaisun sovelluksen
	 */
	public TTK91Application getTeacherAppPublic() {
		return this.teacherApplicationPublic;
	}

	/** Palauttaa piilosyötteillä simuloidun opiskelijan
	 * ratkaisun sovelluksen
	 */
	public TTK91Application getStudentAppHidden() {
		return this.studentApplicationHidden;
	}

	/** Palauttaa piilosyötteillä simuloidun malliratkaisun
	 * sovelluksen
	 */
	
	public TTK91Application getTeacherAppHidden() {
		return this.teacherApplicationHidden;
	}

	/** Palauttaa julkisilla syötteillä tai ilman syötteitä
	 * simuloidun opiskelijan ratkaisun jälkeisessä tilassa
	 * olevan suorittimen.
	 */
	public TTK91Cpu getStudentCpuPublic(){
		return controlPublicInputStudent.getCpu();
	}

	/** Palauttaa piilosyötteillä simuloidun opiskelijan ratkaisun
	 * simuloinnin jälkeisessä tilassa olevan suorittimen.
	 */
	
	public TTK91Cpu getStudentCpuHidden(){

		if(controlHiddenInputStudent != null) {
			return controlHiddenInputStudent.getCpu();
		} else {
			return null;
		}
	}
	/** Palauttaa julkisilla syötteillä tai ilman syötteitä
	 * simuloidun malliratkaisun jälkeisessä tilassa
	 * olevan suorittimen.
	 */

	public TTK91Cpu getTeacherCpuPublic(){
		if(controlPublicInputTeacher != null) {
		return controlPublicInputTeacher.getCpu();
		} else {
			return null;
		}
	}

	/** Palauttaa piilosyötteillä simuloidun malliratkaisun
	 *  simuloinnin jälkeisessä tilassa olevan suorittimen.
	 */
	
	public TTK91Cpu getTeacherCpuHidden(){

		if(controlHiddenInputTeacher != null) {
			return controlHiddenInputTeacher.getCpu();
		} else {
			return null;
		}
	}

	/** Opiskelijan julkistensyötteiden koneen muisti.
	 */
	
	public TTK91Memory getStudentMemoryPublic(){
		return controlPublicInputStudent.getMemory();
	}

	/** Opiskelijan piilosyötteiden koneen muisti
	 */
	
	public TTK91Memory getStudentMemoryHidden(){

		if(controlHiddenInputStudent != null) {
			return controlHiddenInputStudent.getMemory();
		} else {
			return null;
		}
	}

	/** Malliratkaisun julkistensyötteiden koneen muisti
	 */
	
	public TTK91Memory getTeacherMemoryPublic(){
		if(controlPublicInputTeacher != null) {
			return controlPublicInputTeacher.getMemory();
		} else {
			return null;
		}
	}

	/** Malliratkaisun piilosyötteiden koneen muisti
	 */
	public TTK91Memory getTeacherMemoryHidden(){

		if(controlHiddenInputTeacher != null) {
			return controlHiddenInputTeacher.getMemory();
		} else {
			return null;
		}
	}

	/** Palauttaa TTK91TaskOptionsin, eli käytännössä tehtävän
	 * kriteerit. Näitä siis ei erikseen analyserille tarvitse 
	 * pallotella.
	 */

	public TTK91TaskOptions getTaskOptions() {
		return taskOptions;
	}

	/**
	 * Palautusarvona julkisilla syötteillä suoritetun opiskelijan ratkaisun konekäskyjen
	 * lukumäärä.
	 */
	public int getCommandAmount() {
		return  this.usedCommands;
	}

	/** 
	 * Palautusarvona piilosyötteillä suoritetun ohjelman konekäskyjen
	 * lukumäärä.
	 */
	public int getHiddenCommandAmount() {
		return  this.hiddenUsedCommands;
	}

	/**
	 * Palauttaa pinon maksimikoon opiskelijan ratkaisun (julkisilla syötteillä) simuloinnissa.
	 */
	public int getStackSize() {

		return this.maxStackSize;

	}

	/**
	 * Palauttaa muistiviitteiden määrän opiskelijan ratkaisun (julkisilla syötteillä) simuloinnissa.
	 */
	
	public int getMemoryReferences() {
		return this.memoryReferences;
	}

	/**
	 * Palauttaa koodisegmentin koon opiskelijan ratkaisun (julkisilla syötteillä) simuloinnissa.
	 */
	
	public int getHiddenMemoryReferences() {
		return this.hiddenMemoryReferences;
	}

	/**
	 * Palauttaa datasegmentin koon opiskelijan ratkaisun (julkisilla syötteillä) simuloinnissa.
	 */
	
	public int getCodeSegmentSize() {
		return this.codeSegmentSize;
	}

	/**Palauttaa datasegmentin koon opiskelijan ratkaisun (julkisilla syötteillä) simuloinnissa.
	 */
	
	public int getDataSegmentSize() {
		return this.dataSegmentSize;
	}
	/**
	 * Palautusarvo kertoo miten simuloinnit on ajettu. Onko piilosyötteitä
	 * ja simuloitiinko malliratkaisu.
	 */

	public int analyseSwitcher(){

		boolean compareToHidden = compareToHidden();
		boolean compareToStatic = 
			(compareMethod == TTK91Constant.COMPARE_TO_STATIC);
		int compareCase = -1;

		if ( compareToStatic && compareToHidden ) {
			compareCase = TTK91Constant.COMPARE_TO_STATIC_HIDDEN;
		}
		else if ( compareToStatic && (!compareToHidden) ) {
			compareCase = TTK91Constant.COMPARE_TO_STATIC_PUBLIC;
		}
		else if ( (!compareToStatic) && compareToHidden ) {
			compareCase = TTK91Constant.COMPARE_TO_SIMULATED_HIDDEN;
		}
		else if ( (!compareToStatic) && (!compareToHidden) ) {
			compareCase = TTK91Constant.COMPARE_TO_SIMULATED_PUBLIC;
		}
	
		return compareCase;
	
	}

	/**
	 * Palauttavat opiskelijan ratkaisun (student) ja malliratkaisun
	 * (teacher) simuloinnissa syntyneet tulosteet. Eri metodit
	 * näyttötulosteille (Screen eli CRT) ja tiedostotulosteille (File).
	 */
	
    public String getStudentScreenOutputPublic(){
	if (studentApplicationPublic != null) {
	    return studentApplicationPublic.readCrt();
	} else {
	    return null;
	}
    }

    /**
     * Palauttavat opiskelijan ratkaisun (student) ja malliratkaisun
     * (teacher) simuloinnissa syntyneet tulosteet. Eri metodit
     * näyttötulosteille (Screen eli CRT) ja tiedostotulosteille (File).
     */
    public String getStudentScreenOutputHidden(){
	if (studentApplicationHidden != null && controlHiddenInputStudent != null) {
	    return studentApplicationHidden.readCrt();
	} else {
	    return null;
	}
    }
    
    /**
     * Palauttavat opiskelijan ratkaisun (student) ja malliratkaisun
     * (teacher) simuloinnissa syntyneet tulosteet. Eri metodit
     * näyttötulosteille (Screen eli CRT) ja tiedostotulosteille (File).
     */
    public String getTeacherScreenOutputPublic(){
	if (teacherApplicationPublic != null && controlPublicInputStudent != null) {
	    return teacherApplicationPublic.readCrt();
	} else {
	    return null;
	}
    }
    
    /**
     * Palauttavat opiskelijan ratkaisun (student) ja malliratkaisun
     * (teacher) simuloinnissa syntyneet tulosteet. Eri metodit
     * näyttötulosteille (Screen eli CRT) ja tiedostotulosteille (File).
     */
    public String getTeacherScreenOutputHidden(){
	if (teacherApplicationHidden != null && controlHiddenInputTeacher != null) {
	    return teacherApplicationHidden.readCrt();
	} else {
	    return null;
	}
    }

    /**
     * Palauttavat opiskelijan ratkaisun (student) ja malliratkaisun
     * (teacher) simuloinnissa syntyneet tulosteet. Eri metodit
     * näyttötulosteille (Screen eli CRT) ja tiedostotulosteille (File).
     */
    public String getStudentFileOutputPublic(){
	if (studentApplicationPublic != null) {
	    return studentApplicationPublic.readStdOut();
	} else {
	    return null;
	}
    }

    /**
     * Palauttavat opiskelijan ratkaisun (student) ja malliratkaisun
     * (teacher) simuloinnissa syntyneet tulosteet. Eri metodit
     * näyttötulosteille (Screen eli CRT) ja tiedostotulosteille (File).
     */
    public String getStudentFileOutputHidden(){
	if (studentApplicationHidden != null) {
	    return studentApplicationHidden.readStdOut();
	} else {
	    return null;
	}
    }
    
    /**
     * Palauttavat opiskelijan ratkaisun (student) ja malliratkaisun
     * (teacher) simuloinnissa syntyneet tulosteet. Eri metodit
     * näyttötulosteille (Screen eli CRT) ja tiedostotulosteille (File).
     */
    
    public String getTeacherFileOutputPublic(){
	if (teacherApplicationPublic != null) {
	    return teacherApplicationPublic.readStdOut();
	} else {
	    return null;
	}
    }
    
    /**
     * Palauttavat opiskelijan ratkaisun (student) ja malliratkaisun
     * (teacher) simuloinnissa syntyneet tulosteet. Eri metodit
     * näyttötulosteille (Screen eli CRT) ja tiedostotulosteille (File).
     */
    
    public String getTeacherFileOutputHidden(){
	if (teacherApplicationHidden != null) {
	    return teacherApplicationHidden.readStdOut();
	} else {
	    return null;
	}
    }
    
}// class
