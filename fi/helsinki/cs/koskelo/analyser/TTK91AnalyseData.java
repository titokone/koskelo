import fi.hu.cs.titokone.Source;
import fi.hu.cs.titokone.Control;
import fi.hu.cs.titokone.Processor;
import fi.hu.cs.titokone.RandomAccessMemory;
import fi.hu.cs.titokone.MemoryLine;
import fi.helsinki.cs.koskelo.common.TTK91TaskOptions;


public class TTK91AnalyserData{


	// Controllit joilta saadaan dataa:

	private TTK91Core controlCompiler;           // kääntäjä -> saadaan mahdolliset käännösvirheet nätimmin (kunhan ajetaan malli ensin... 
	// Jos se ei käänny, ei varmaan ole tarvetta kääntää opiskelijankaan ratkaisua...
	private TTK91Core controlPublicInputStudent; // publicinputeilla tai ilman unputteja opiskelijan vastaus
	private TTK91Core controlPublicInputTeacher; // publicinputeilla tai ilman inputteja malliratkaisu jos vertailu on määritelty simuloitavaksi
	private TTK91Core controlHiddenInputStudent; // hiddeninputeilla jos ovat määritelty opiskelijan vastaus
	private TTK91Core controlHiddenInputTeacher; // hiddeninputeilla jos ovat määritelty malliratkaisu jos vertailu on määritelty simuloitavaksi

	// Tehtävänmäärittelydata
	private TTK91TaskOptions taskOptions;        // taskoptions

	// Malliratkaisu
	private String[] exampleCode;                // haetaan taskoptionsista - malliratkaisun koodi

	// Käännetyt koodit
	private TTK91Application studentApplication; // opiskelijan vastaus
	private TTK91Application teacherApplication; // malliratkaisu

	// Analysointitapa
	private int analyseMethod;




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

	private    RandomAccessMemory studentMemory; // = (RandomAccessMemory) controlPublicInputStudent.getMemory();
	private    RandomAccessMemory teacherMemory; // = null;
	//		    if (controlPublicInputTeacher != null) {
	//			                teacherMemory = (RandomAccessMemory) controlPublicInputTeacher.getMemory();
	//					    }


	/* public void run( app, app, kbd, kbd ) */ // student, teacher jne
	/* public RandomAccessMemory getStudentPublicMemory() */
	/* public String getStudentScreenOut */
	

	public TTK91AnalyserData(
			String studentapp, 
			String teacherapp, 
			int[] publicInput, 
			int[] hiddenInput
			) {


		// run();
		// setMemories();
		// setOutput();
		// setRegisters();
	}

}// class
