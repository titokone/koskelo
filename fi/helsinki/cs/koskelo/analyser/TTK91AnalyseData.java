import fi.hu.cs.titokone.Source;
import fi.hu.cs.titokone.Control;
import fi.hu.cs.titokone.Processor;
import fi.hu.cs.titokone.RandomAccessMemory;
import fi.hu.cs.titokone.MemoryLine;
import fi.helsinki.cs.koskelo.common.TTK91TaskOptions;


public class TTK91AnalyserData{


	// Controllit joilta saadaan dataa:

	private TTK91Core controlCompiler;           // k‰‰nt‰j‰ -> saadaan mahdolliset k‰‰nnˆsvirheet n‰timmin (kunhan ajetaan malli ensin... 
	// Jos se ei k‰‰nny, ei varmaan ole tarvetta k‰‰nt‰‰ opiskelijankaan ratkaisua...
	private TTK91Core controlPublicInputStudent; // publicinputeilla tai ilman unputteja opiskelijan vastaus
	private TTK91Core controlPublicInputTeacher; // publicinputeilla tai ilman inputteja malliratkaisu jos vertailu on m‰‰ritelty simuloitavaksi
	private TTK91Core controlHiddenInputStudent; // hiddeninputeilla jos ovat m‰‰ritelty opiskelijan vastaus
	private TTK91Core controlHiddenInputTeacher; // hiddeninputeilla jos ovat m‰‰ritelty malliratkaisu jos vertailu on m‰‰ritelty simuloitavaksi

	// Teht‰v‰nm‰‰rittelydata
	private TTK91TaskOptions taskOptions;        // taskoptions

	// Malliratkaisu
	private String[] exampleCode;                // haetaan taskoptionsista - malliratkaisun koodi

	// K‰‰nnetyt koodit
	private TTK91Application studentApplication; // opiskelijan vastaus
	private TTK91Application teacherApplication; // malliratkaisu

	// Analysointitapa
	private int analyseMethod;


	private String publicInput;
	private String hiddenInput;


	// Opiskelijan ja opettajan ohjelmien n‰yttˆtulosteet
	
	private String studentPublicScreenOut;
	private String studentHiddenScreenOut;
	private String teacherPublicScreenOut;
	private String teacherHiddenScreenOut;

	//Opiskelijan ja opettajan tiedostotulosteet
	
	private String studentPublicFileOut;
	private String studentHiddenFileOut;
	private String teacherPublicFileOut;
	private String teacherHiddenFileOut;


	// Titokoneiden muistit:

	private    RandomAccessMemory studentPublicMemory; 
	private    RandomAccessMemory teacherPublicMemory; 
	private    RandomAccessMemory studentHiddenMemory; 
	private    RandomAccessMemory teacherHiddenMemory;
	
	//		    if (controlPublicInputTeacher != null) {
	//			                teacherMemory = (RandomAccessMemory) controlPublicInputTeacher.getMemory();
	//					    }


	/* public void run( app, app, kbd, kbd ) */ // student, teacher jne
	/* public RandomAccessMemory getStudentPublicMemory() */
	/* public String getStudentScreenOut */
	

	public TTK91AnalyseData(
			String studentapp, 
			String teacherapp, 
			TTK91TaskOptions taskOptions
			) {

		this.taskOptions = taskOptions;
				this.studentApplication = studentapp;
		this.teacherApplication = teacherapp;
	
		getTaskData();
		run();
		setOutput();
	}


	private void getTaskData() {
		this.publicInput = parseInputString(
				this.taskOptions.getPublicInput()
				);
		this.hiddenInput = parseInputString(
				this.taskOptions.getHiddenInput()
				);

		this.steps = taskOptions.getMaxCommands();
		
	}
	

	
	private void run() {
	

		// Aja ohjelmat.
		// Aseta muisti,
		// Aseta rekisterit,
		// Aseta tulosteet.
	
	}

	private String parseInputString(int[] inputTable) {

		String input = "";


		return input;
	}

	/* Tarvitaan viel‰: sopivat getterit */
	
}// class
