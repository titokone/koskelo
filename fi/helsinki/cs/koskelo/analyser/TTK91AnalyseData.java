import fi.hu.cs.titokone.Source;
import fi.hu.cs.titokone.Control;
import fi.hu.cs.titokone.Processor;
import fi.hu.cs.titokone.RandomAccessMemory;
import fi.hu.cs.titokone.MemoryLine;
import fi.helsinki.cs.koskelo.common.TTK91TaskOptions;


public class TTK91AnalyserData{


	// Controllit joilta saadaan dataa:

	private TTK91Core controlCompiler;    
	// kääntäjä -> saadaan mahdolliset käännösvirheet nätimmin (kunhan ajetaan malli ensin... 
	// Jos se ei käänny, ei varmaan ole tarvetta kääntää opiskelijankaan ratkaisua...
	private TTK91Core controlPublicInputStudent;
	// publicinputeilla tai ilman unputteja opiskelijan vastaus
	private TTK91Core controlPublicInputTeacher; 
	// publicinputeilla tai ilman inputteja malliratkaisu jos vertailu on määritelty simuloitavaksi
	private TTK91Core controlHiddenInputStudent;
	// hiddeninputeilla jos ovat määritelty opiskelijan vastaus
	private TTK91Core controlHiddenInputTeacher;
	// hiddeninputeilla jos ovat määritelty malliratkaisu jos vertailu on määritelty simuloitavaksi

	// Tehtävänmäärittelydata
	private TTK91TaskOptions taskOptions;        // taskoptions

	// Malliratkaisu

	private String exampleCode;                // haetaan taskoptionsista - malliratkaisun koodi
	private String[] answer;
	
	
	// Käännetyt koodit
	private TTK91Application studentApplication; // opiskelijan vastaus
	private TTK91Application teacherApplication; // malliratkaisu

	// Analysointitapa
	private int analyseMethod;


	private String publicInput;
	private String hiddenInput;


	// Opiskelijan ja opettajan ohjelmien näyttötulosteet
	
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
	

	public TTK91AnalyseData(
			String[] answer, 
			TTK91TaskOptions taskOptions
			) {

		this.taskOptions = taskOptions;
		this.answer = answer;

		getApplications;
		getTaskData();
		run();
		setOutput();
	}

	private void getApplications() {


		// get the student app and the
		// teacher app and make them into applications
		
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

	/* Tarvitaan vielä: sopivat getterit */
	
}// class
