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

public class TTK91AnalyseData{


	// Controllit joilta saadaan dataa:

	private TTK91Core controlCompiler = null;    
	private TTK91Core controlPublicInputStudent = null;
	// publicinputeilla tai ilman inputteja opiskelijan vastaus
	private TTK91Core controlPublicInputTeacher = null; 
	// publicinputeilla tai ilman inputteja malliratkaisu jos vertailu on m‰‰ritelty simuloitavaksi
	private TTK91Core controlHiddenInputStudent = null;
	// hiddeninputeilla jos ovat m‰‰ritelty opiskelijan vastaus
	private TTK91Core controlHiddenInputTeacher = null;
	// hiddeninputeilla jos ovat m‰‰ritelty malliratkaisu jos vertailu on m‰‰ritelty simuloitavaksi

	// Teht‰v‰nm‰‰rittelydata
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

	// K‰‰nnetyt koodit
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


	// Titokoneiden muistit (FIXME, n‰it‰ ei taideta tarvita?):

//	private    RandomAccessMemory studentPublicMemory = null; 
//	private    RandomAccessMemory teacherPublicMemory = null; 
//	private    RandomAccessMemory studentHiddenMemory = null; 
//	private    RandomAccessMemory teacherHiddenMemory = null;


	/** Luodaan uusi TTK91AnalyseDAta, jolle annetaan parametreina 
	 * teht‰v‰nperustiedot, opiskelijan vastaus ja malliratkaisu.
	 * Malliratkaisu annetaan erikseen, jotta sit‰ voideen erikseen
	 * esik‰sitell‰ analyserissa.
	 *
	 * @param taskOptions Teht‰v‰n kriteerit ja perustiedot
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

		// compilet ovat voineet p‰‰tty‰ virheeseen
		if(!this.errors) {
			run();
		}

		// ohjelmien ajo on voinut p‰‰tty‰ virheeseen
		if(!this.errors) {
			setStatistics();
		}
	}

	/** Statistiikkojen kaivelua muuttujiin, siivottu yhteen metodiin.
	 */

	private void setStatistics() {

		if(controlPublicInputStudent != null && !errors) {

			// FIXME, t‰m‰ on titokoneen rajapinnan puutteen kiertoa!!
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

				// FIXME, t‰m‰ on titokoneen rajapinnan puutteen kiertoa!!
				ram = (RandomAccessMemory) 
					controlHiddenInputStudent.getMemory();

				cpu = (Processor)
					controlHiddenInputStudent.getCpu(); 

				hiddenUsedCommands = cpu.giveCommAmount();
				hiddenMemoryReferences = ram.getMemoryReferences();

			} 

		}
	}

	/** Malliratkaisun k‰‰nt‰minen. Malliratkaisua ei k‰‰nnet‰ jos malliratkaisu on null.
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

	/** Opiskelijan ohjelman k‰‰nt‰minen. Jos opiskelijan ohjelmaa ei ole, palautetaan virhe.
	 */
	
	private void compileStudentApplication() {


		// compile studentapp

		TTK91CompileSource src = null;

		if (this.answer != null) {
			String ans = this.answer;
			ans = ans + "\n SVC SP, =HALT";
			src = (TTK91CompileSource) new Source(ans);
			// FIXME: toimiiko tosiaan n‰in helposti?
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

	/** Piilotettu taskOptionsista erikseen datan hakeminen t‰nne.
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
 * coreja, jos sellaisia ei kaivata, koska myˆsk‰‰n myˆhemmin
 * ei haluta opemuistina muuta kuin null jo simulointia ei ole.
 */
	
	private void run() {


		/* Koska titokoneesta metodilla .getCPU() saadaan
		 * vain viite controlin sis‰iseen prosessoriin k‰ytet‰‰n
		 * yhteens‰ maksissaan nelj‰‰ controlia, kuitenkin
		 * siten, ett‰ kullekin simulointikierrokselle
		 * luodaan oma controlinsa. Kannattaa myˆskin
		 * huomata ett‰ applicationeja on nelj‰,
		 * sen takia, ett‰ outputit saadaan sielt‰ myˆhemmin
		 * ja niiden kaikkien simuloinnit tahdotaan tehd‰ kerralla.
		 * Myˆskin muun applicationista haettavan datan kannalta
		 * mahdollisesti myˆhemmin t‰st‰ voi olla hyˆty‰.
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

			this.controlHiddenInputStudent = new Control(null, null); // luodaan control vain jos hiddeninput m‰‰ritelty --> "optimointia"
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

				this.controlHiddenInputTeacher = new Control(null, null); // luodaan control vain jos hiddeninput m‰‰ritelty --> "optimointia"
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
	 * muotoon input. Periaatteessa sama muoto kuin alunperin kriteereiss‰.
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

	/** Seuraavat getterit ovat datan saamiseksi analyserissa, realAnalyserissa jne.*/

	public String getStudentCompileError() {
		return this.studentCompileError;
	}

	public String getTeacherCompileError() {
		return this.teacherCompileError;
	}

	public String getStudentRunError() {
		return this.studentRunError;
	}

	public String getTeacherRunError() {
		return this.teacherRunError;
	}

	/** N‰in analyser saa kaikki nelj‰ virheilmoitusta siististi kerralla.
	 * K‰yt‰nnˆss‰ vain yksi on k‰ytˆss‰, sill‰ jos teacher ei k‰‰nny,
	 * turha yrit‰‰ opiskelijaa, jos opiskelija ei k‰‰nny, turha mit‰‰n on
	 * ajaa, jos opiskelijaa ei voida ajaa, turha yritt‰‰ saada sit‰ vertailtavaan
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

	public boolean errors() {
		return this.errors;
	}

	public boolean compareToHidden() {
		return (hiddenInput != null);
	}

	public String getAnswer() {
		return this.answer;
	}

	public TTK91Application getStudentAppPublic() {
		return this.studentApplicationPublic;
	}

	public TTK91Application getTeacherAppPublic() {
		return this.teacherApplicationPublic;
	}

	public TTK91Application getStudentAppHidden() {
		return this.studentApplicationHidden;
	}

	public TTK91Application getTeacherAppHidden() {
		return this.teacherApplicationHidden;
	}

	public TTK91Cpu getStudentCpuPublic(){
		return controlPublicInputStudent.getCpu();
	}
	public TTK91Cpu getStudentCpuHidden(){

		if(controlHiddenInputStudent != null) {
			return controlHiddenInputStudent.getCpu();
		} else {
			return null;
		}
	}

	public TTK91Cpu getTeacherCpuPublic(){
		if(controlPublicInputTeacher != null) {
		return controlPublicInputTeacher.getCpu();
		} else {
			return null;
		}
	}

	public TTK91Cpu getTeacherCpuHidden(){

		if(controlHiddenInputTeacher != null) {
			return controlHiddenInputTeacher.getCpu();
		} else {
			return null;
		}
	}

	public TTK91Memory getStudentMemoryPublic(){
		return controlPublicInputStudent.getMemory();
	}
	public TTK91Memory getStudentMemoryHidden(){

		if(controlHiddenInputStudent != null) {
			return controlHiddenInputStudent.getMemory();
		} else {
			return null;
		}
	}

	public TTK91Memory getTeacherMemoryPublic(){
		if(controlPublicInputTeacher != null) {
			return controlPublicInputTeacher.getMemory();
		} else {
			return null;
		}
	}

	public TTK91Memory getTeacherMemoryHidden(){

		if(controlHiddenInputTeacher != null) {
			return controlHiddenInputTeacher.getMemory();
		} else {
			return null;
		}
	}


	public TTK91TaskOptions getTaskOptions() {
		return taskOptions;
	}

	public int getCommandAmount() {
		return  this.usedCommands;
	}

	public int getHiddenCommandAmount() {
		return  this.hiddenUsedCommands;
	}

	public int getStackSize() {

		return this.maxStackSize;

	}

	public int getMemoryReferences() {
		return this.memoryReferences;
	}


	public int getHiddenMemoryReferences() {
		return this.hiddenMemoryReferences;
	}


	public int getCodeSegmentSize() {
		return this.codeSegmentSize;
	}

	public int getDataSegmentSize() {
		return this.dataSegmentSize;
	}

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

    public String getStudentScreenOutputPublic(){
	if (studentApplicationPublic != null) {
	    return studentApplicationPublic.readCrt();
	} else {
	    return null;
	}
    }

    public String getStudentScreenOutputHidden(){
	if (studentApplicationHidden != null && controlHiddenInputStudent != null) {
	    return studentApplicationHidden.readCrt();
	} else {
	    return null;
	}
    }
    
    public String getTeacherScreenOutputPublic(){
	if (teacherApplicationPublic != null && controlPublicInputStudent != null) {
	    return teacherApplicationPublic.readCrt();
	} else {
	    return null;
	}
    }
    
    public String getTeacherScreenOutputHidden(){
	if (teacherApplicationHidden != null && controlHiddenInputTeacher != null) {
	    return teacherApplicationHidden.readCrt();
	} else {
	    return null;
	}
    }

    public String getStudentFileOutputPublic(){
	if (studentApplicationPublic != null) {
	    return studentApplicationPublic.readStdOut();
	} else {
	    return null;
	}
    }

    public String getStudentFileOutputHidden(){
	if (studentApplicationHidden != null) {
	    return studentApplicationHidden.readStdOut();
	} else {
	    return null;
	}
    }
    
    public String getTeacherFileOutputPublic(){
	if (teacherApplicationPublic != null) {
	    return teacherApplicationPublic.readStdOut();
	} else {
	    return null;
	}
    }
    
    public String getTeacherFileOutputHidden(){
	if (teacherApplicationHidden != null) {
	    return teacherApplicationHidden.readStdOut();
	} else {
	    return null;
	}
    }
    
}// class
