package fi.helsinki.cs.koskelo.analyser;

import fi.helsinki.cs.koskelo.common.TTK91TaskOptions;
import fi.helsinki.cs.koskelo.common.TTK91TaskCriteria;
import fi.helsinki.cs.koskelo.common.TTK91Constant;

import fi.hu.cs.titokone.MemoryLine;
import fi.hu.cs.titokone.RandomAccessMemory;

import fi.hu.cs.ttk91.TTK91Memory;
import fi.hu.cs.ttk91.TTK91Cpu;

import java.util.HashMap;


public class TTK91RealAnalyser {

    private TTK91AnalyseData analyseData;
    private TTK91TaskOptions taskOptions;
    private String answer;

    public TTK91RealAnalyser(TTK91AnalyseData analyseData) {
	this.analyseData = analyseData;
	this.taskOptions = analyseData.getTaskOptions();
	this.answer = analyseData.getAnswer();
    }

    public TTK91AnalyseResults analyse() {

	TTK91AnalyseResults results = new TTK91AnalyseResults();

	// analysoi seuraavat osiot
	// -'generalAnalysis'
	generalAnalysis(results);
	// -'analyseMemory'
	analyseMemory(results);
	// -'analyseRegisters'
	analyseRegisters(results);
	// -'analyseOutput'
	analyseScreenOutput(results);
	analyseFileOutput(results);
	// - statistiikat
	setStatistics(results);

	return results;
    }

    private void generalAnalysis(TTK91AnalyseResults results) {
	System.err.println("******************** GENERAL_ANALYSIS ALKAA *******************");

	//  -Suoritettujen konekäskyjen määrä (oikeellisuus)
	int pubInputSize = this.analyseData.getCommandAmount();
	int hiddenInputSize = this.analyseData.getHiddenCommandAmount();
	int sizeLimit = this.taskOptions.getAcceptedSize();
	if (sizeLimit > 0) {
	    results.setAcceptedSize( (pubInputSize <= sizeLimit) && 
				     (hiddenInputSize <= sizeLimit) );
	    System.err.println("pubsize: "+pubInputSize+" hidsize: "+hiddenInputSize+" sizeLimit: "+sizeLimit);
	    System.err.println("Vertailun tulos: "+ ((pubInputSize <= sizeLimit) && (hiddenInputSize <= sizeLimit)));
	}
	//	  -Ihannekoko (laatu)
	int optimalSize = this.taskOptions.getOptimalSize();
	if (optimalSize > 0) {
	    results.setOptimalSize( (pubInputSize <= optimalSize) &&
				    (hiddenInputSize <= optimalSize) );
	}

	//	  -Muistiviitteiden määrä
	TTK91TaskCriteria memRefCriteria = this.taskOptions.getMemRefCriteria();
	if (memRefCriteria != null) {
	    int memRefs = this.analyseData.getMemoryReferences();
	    int hiddenMemRefs = this.analyseData.getHiddenMemoryReferences();
	    boolean memRefsOk = 
		TTK91AnalyserExtraUtils.compare(memRefs, 
						memRefCriteria.getComparator(),
						memRefCriteria.getSecondComparable());
	    if (hiddenMemRefs > 0 && memRefsOk) {
		memRefsOk = 
		    TTK91AnalyserExtraUtils.compare(hiddenMemRefs,
						    memRefCriteria.getComparator(),
						    memRefCriteria.getSecondComparable());
	    }
	    results.setMemoryReferences(memRefsOk);
	}

	//      -Vaaditut käskyt
	String[] requiredCommands = this.taskOptions.getRequiredCommands();
	if (requiredCommands != null) {
	         
	    Boolean requiredQualityCommand = null;
	    Boolean requiredCommand = null;

	    for (int i = 0; i < requiredCommands.length; ++i) {
		
		String[] cmd = requiredCommands[i].split(",");
		if (cmd.length > 1) { // -> laadullinen
		    requiredQualityCommand = 
			TTK91AnalyserExtraUtils.isCommandFound(this.answer, cmd[1]);
		}
		else { // -> oikeellisuus
		    requiredCommand = 
			TTK91AnalyserExtraUtils.isCommandFound(this.answer, cmd[0]);
		    if (!requiredCommand.booleanValue()) {
			break; // vaatimalla vaadittua käskyä ei löytynyt -> lopetetaan
		    }
		}
	    }

	    if (requiredCommand != null) {
		results.setRequiredCommands(requiredCommand.booleanValue());
	    }
	    if (requiredQualityCommand != null) {
		results.setRequiredCommandsQuality(requiredQualityCommand.booleanValue());
	    }
	}

	//      -Kielletyt käskyt
	String[] forbiddenCommands = this.taskOptions.getForbiddenCommands();

	//	for (int i=0; i < forbiddenCommands

	if (forbiddenCommands != null) {
	         
	    Boolean forbiddenQualityCommand = null;
	    Boolean forbiddenCommand = null;

	    for (int i = 0; i < forbiddenCommands.length; ++i) {
		
		String[] cmd = forbiddenCommands[i].split(",");
		if (cmd.length > 1) { // -> laadullinen
		    forbiddenQualityCommand = 
			TTK91AnalyserExtraUtils.commandNotFound(this.answer, cmd[1]); // true jos käskyä ei löydy
		}
		else { // -> oikeellisuus
		    forbiddenCommand = 
			TTK91AnalyserExtraUtils.commandNotFound(this.answer, cmd[0]); 
		    System.err.println("CommandNotFound palautti: "+forbiddenCommand);
		    if (!forbiddenCommand.booleanValue()) {
			break; // kielletty käsky löytyi -> lopetetaan
		    }
		}

	    }

	    if (forbiddenCommand != null) {
		results.setForbiddenCommands(forbiddenCommand.booleanValue());
	    }
	    if (forbiddenQualityCommand != null) {
		results.setForbiddenCommandsQuality(forbiddenQualityCommand.booleanValue());
	    }
	    
	}
    }//generalAnalysis



    /**
     * Tutkitaan muistipaikkojen ja muuttujien sisältöön liittyvät
     * kriteerit Toimintaperiaate: kaydaan muistipaikkoihin ja
     * muuttujiin liittyvia kriteereja lapi, kunnes joko 1) kaikki on
     * kayty lapi tai 2) seka jokin oikeellisuus- etta jokin
     * laatukriteeri on pettanyt. 
     *
     * FIXME: kuvaus uusiksi
     */

    private void analyseMemory(TTK91AnalyseResults results) {
	
	TTK91TaskCriteria[] memcrits = this.taskOptions.getMemoryCriterias();
	int compareMethod = this.taskOptions.getCompareMethod();


	if (memcrits == null) {
	    return;  // jos muistikriteerit null, palataan suoraan (jätetään resultsiin arvot nulleiksi)
	}

	TTK91Memory studentPubMem = this.analyseData.getStudentMemoryPublic();
	TTK91Memory teacherPubMem= this.analyseData.getTeacherMemoryPublic();
	TTK91Memory studentHiddenMem = this.analyseData.getStudentMemoryHidden();
	TTK91Memory teacherHiddenMem = this.analyseData.getTeacherMemoryHidden();
	int analyseSwitcher = this.analyseData.analyseSwitcher();

	/* tutkitaan "helpot" virhetilanteet pois; opiskelijan
	   ratkaisun muistia ei ole käytössä, tai jos pitäisi
	   verrata malliratkaisuun, eikä sitä ole käytettävissä
	*/

	switch (analyseSwitcher) {
	case TTK91Constant.COMPARE_TO_STATIC_PUBLIC:
	    {
		// täällä riittää yksi ainoa muisti
		if (studentPubMem == null) {
		    results.addErrorMessage("analyseMemory: virhe "+
					    "parametreissa - rikkinäinen"+
					    " tehtävä tai bugi koodissa.");
		    return;
		}
		HashMap studentPubSymbolTable = 
		    studentPubMem.getSymbolTable();
		MemoryLine[] studentPubMemlines = 
		    ((RandomAccessMemory)studentPubMem).getMemoryLines();
		
		boolean critvalue = true;
		boolean qualitycritvalue = true;
		boolean nonQualityCritFound = false;
		boolean qualityCritFound = false;
		
		for (int i=0; i < memcrits.length; ++i) {
		    if (!critvalue) {
			break; // oikeellisuus poks, ei syytä jatkaa
		    }
		    TTK91TaskCriteria crit = memcrits[i];
		    boolean isqualitycrit = crit.getQuality();
		    if (isqualitycrit && !qualitycritvalue) { 
			continue; // tutkittavana laatukriteeri, mutta jokin laatukriteeri on jo poksahtanut
		    }
		    if (isqualitycrit) {
			qualityCritFound = true;
			qualitycritvalue = 
			    TTK91AnalyserExtraUtils.
			    checkStaticMemoryCriteria(crit, 
						      studentPubSymbolTable, 
						      studentPubMemlines);
		    }
		    else {
			nonQualityCritFound = true;
			critvalue = 
			    TTK91AnalyserExtraUtils.
			    checkStaticMemoryCriteria(crit, 
						      studentPubSymbolTable, 
						      studentPubMemlines);
		    }
		}
		if (nonQualityCritFound) {
		    results.setMemory(critvalue);
		}
		if (qualityCritFound) {
		    results.setMemoryQuality(qualitycritvalue);
		}
	    }
	    break;
	
	case TTK91Constant.COMPARE_TO_STATIC_HIDDEN:
	    {
		// täällä tarvitaan kaksi muistia
		if ( (studentPubMem == null) || 
		     (studentHiddenMem == null) ) {
		    results.addErrorMessage("analyseMemory: virhe "+
					    "parametreissa - rikkinäinen"+
					    " tehtävä tai bugi koodissa.");
		    return;
		}
		HashMap studentPubSymbolTable = 
		    studentPubMem.getSymbolTable();
		HashMap studentHiddenSymbolTable = 
		    studentHiddenMem.getSymbolTable();
		MemoryLine[] studentPubMemlines = 
		    ((RandomAccessMemory)studentPubMem).getMemoryLines();
		MemoryLine[] studentHiddenMemlines = 
		    ((RandomAccessMemory)studentHiddenMem).getMemoryLines();
		
		boolean critvalue = true;
		boolean qualitycritvalue = true;
		boolean nonQualityCritFound = false;
		boolean qualityCritFound = false;
		
		for (int i=0; i < memcrits.length; ++i) {
		    if (!critvalue) {
			break; // oikeellisuus poks, ei syytä jatkaa
		    }
		    TTK91TaskCriteria crit = memcrits[i];
		    boolean isqualitycrit = crit.getQuality();
		    if (isqualitycrit && !qualitycritvalue) { 
			continue; // tutkittavana laatukriteeri, mutta jokin laatukriteeri on jo poksahtanut
		    }
		    if (isqualitycrit) {
			qualityCritFound = true;
			qualitycritvalue = 
			    TTK91AnalyserExtraUtils.
			    checkStaticMemoryCriteria(crit, 
						      studentPubSymbolTable, 
						      studentHiddenSymbolTable, 
						      studentPubMemlines, 
						      studentHiddenMemlines);
		    }
		    else {
			nonQualityCritFound = true;
			critvalue = 
			    TTK91AnalyserExtraUtils.
			    checkStaticMemoryCriteria(crit, 
						      studentPubSymbolTable, 
						      studentHiddenSymbolTable, 
						      studentPubMemlines, 
						      studentHiddenMemlines);
		    }
		}
		if (nonQualityCritFound) {
		    results.setMemory(critvalue);
		}
		if (qualityCritFound) {
		    results.setMemoryQuality(qualitycritvalue);
		}
	    }
	    break;
	
	case TTK91Constant.COMPARE_TO_SIMULATED_PUBLIC:
	    {
		// täälläkin tarvitaan kaksi muistia
		if ( (studentPubMem == null) || 
		     (teacherPubMem == null) ) {
		    results.addErrorMessage("analyseMemory: virhe "+
					    "parametreissa - rikkinäinen"+
					    " tehtävä tai bugi koodissa.");
		    return;
		}
		HashMap studentPubSymbolTable = 
		    studentPubMem.getSymbolTable();
		HashMap teacherPubSymbolTable = 
		    teacherPubMem.getSymbolTable();
		MemoryLine[] studentPubMemlines = 
		    ((RandomAccessMemory)studentPubMem).getMemoryLines();
		MemoryLine[] teacherPubMemlines = 
		    ((RandomAccessMemory)teacherPubMem).getMemoryLines();
		
		boolean critvalue = true;
		boolean qualitycritvalue = true;
		boolean nonQualityCritFound = false;
		boolean qualityCritFound = false;
		
		for (int i=0; i < memcrits.length; ++i) {
		    if (!critvalue) {
			break; // oikeellisuus poks, ei syytä jatkaa
		    }
		    TTK91TaskCriteria crit = memcrits[i];
		    boolean isqualitycrit = crit.getQuality();
		    if (isqualitycrit && !qualitycritvalue) { 
			continue; // tutkittavana laatukriteeri, mutta jokin laatukriteeri on jo poksahtanut
		    }
		    if (isqualitycrit) {
			qualityCritFound = true;
			qualitycritvalue = 
			    TTK91AnalyserExtraUtils.
			    checkSimulatedMemoryCriteria(crit, 
							 studentPubSymbolTable, 
							 teacherPubSymbolTable, 
							 studentPubMemlines, 
							 teacherPubMemlines);
		    }
		    else {
			nonQualityCritFound = true;
			critvalue = 
			    TTK91AnalyserExtraUtils.
			    checkSimulatedMemoryCriteria(crit, 
							 studentPubSymbolTable, 
							 teacherPubSymbolTable, 
							 studentPubMemlines, 
							 teacherPubMemlines);
		    }
		}
		
		if (nonQualityCritFound) {
		    results.setMemory(critvalue);
		}
		if (qualityCritFound) {
		    results.setMemoryQuality(qualitycritvalue);
		}
	    }
	    break;
	
	case TTK91Constant.COMPARE_TO_SIMULATED_HIDDEN:
	    {
		// täällä tarvitaan "kaikki neljä" muistia
		if ( (studentPubMem == null) || 
		     (teacherPubMem == null) ||
		     (studentHiddenMem == null) ||
		     (teacherHiddenMem == null) ) {
		    results.addErrorMessage("analyseMemory: virhe "+
					    "parametreissa - rikkinäinen"+
					    " tehtävä tai bugi koodissa.");
		    return;
		}
		HashMap studentPubSymbolTable = 
		    studentPubMem.getSymbolTable();
		HashMap studentHiddenSymbolTable = 
		    studentHiddenMem.getSymbolTable();
		HashMap teacherPubSymbolTable = 
		    teacherPubMem.getSymbolTable();
		HashMap teacherHiddenSymbolTable = 
		    teacherHiddenMem.getSymbolTable();
		MemoryLine[] studentPubMemlines = 
		    ((RandomAccessMemory)studentPubMem).getMemoryLines();
		MemoryLine[] studentHiddenMemlines = 
		    ((RandomAccessMemory)studentHiddenMem).getMemoryLines();
		MemoryLine[] teacherPubMemlines = 
		    ((RandomAccessMemory)teacherPubMem).getMemoryLines();
		MemoryLine[] teacherHiddenMemlines = 
		    ((RandomAccessMemory)teacherHiddenMem).getMemoryLines();
		
		boolean critvalue = true;
		boolean qualitycritvalue = true;
		boolean nonQualityCritFound = false;
		boolean qualityCritFound = false;
		
		for (int i=0; i < memcrits.length; ++i) {
		    if (!critvalue) {
			break; // oikeellisuus poks, ei syytä jatkaa
		    }
		    TTK91TaskCriteria crit = memcrits[i];
		    boolean isqualitycrit = crit.getQuality();
		    if (isqualitycrit && !qualitycritvalue) { 
			continue; // tutkittavana laatukriteeri, mutta jokin laatukriteeri on jo poksahtanut
		    }
		    
		    if (isqualitycrit) {
			qualityCritFound = true;
			qualitycritvalue = 
			    TTK91AnalyserExtraUtils.
			    checkSimulatedMemoryCriteria(crit, 
							 studentPubSymbolTable,
							 studentHiddenSymbolTable,
							 teacherPubSymbolTable,
							 teacherHiddenSymbolTable,
							 studentPubMemlines,
							 studentHiddenMemlines,
							 teacherPubMemlines,
							 teacherHiddenMemlines);
		    }
		    else {
			nonQualityCritFound = true;
			critvalue = 
			    TTK91AnalyserExtraUtils.
			    checkSimulatedMemoryCriteria(crit, 
							 studentPubSymbolTable,
							 studentHiddenSymbolTable,
							 teacherPubSymbolTable,
							 teacherHiddenSymbolTable,
							 studentPubMemlines,
							 studentHiddenMemlines,
							 teacherPubMemlines,
							 teacherHiddenMemlines);
		    }
		}
		
		if (nonQualityCritFound) {
		    results.setMemory(critvalue);
		}
		if (qualityCritFound) {
		    results.setMemoryQuality(qualitycritvalue);
		}
	    }
	    break;
	    
	default:
	    results.addErrorMessage("analyseMemory: virhe parametreissa - rikkinäinen tehtävä tai bugi koodissa.");
	    return;
	}
    }

    /**
     * Apumetodi rekistereiden sisältöön liittyvien kriteerien analysointiin
     * 
     */
    
    private void analyseRegisters(TTK91AnalyseResults results) {

	TTK91TaskCriteria[] regCrit = this.taskOptions.getRegisterCriterias();
	
	if (regCrit == null) {
	    return; // jos ei rekisterikriteerejä, ei ole analysoitavaakaan
	}
	
	TTK91Cpu studentCPU = this.analyseData.getStudentCpuPublic();
	TTK91Cpu teacherCPU = this.analyseData.getTeacherCpuPublic();
	TTK91Cpu studentHiddenCPU = this.analyseData.getStudentCpuHidden();
	TTK91Cpu teacherHiddenCPU = this.analyseData.getTeacherCpuHidden();

	boolean regCritOk = true;
	boolean regCritQualityOk = true;
	boolean critFound = false;
	boolean qualityFound = false;

	for (int i = 0; i < regCrit.length; ++i) {
	    TTK91TaskCriteria crit = regCrit[i];
	    if (crit.getQuality()) {
		if (!regCritQualityOk) {
		    continue; // valmiiksi poks
		}
		qualityFound = true;
		regCritQualityOk = TTK91AnalyserExtraUtils.checkRegisterCriteria(crit, studentCPU, teacherCPU);
		if ( (studentHiddenCPU != null) &&
		     (teacherHiddenCPU != null) &&
		     (regCritQualityOk) ) {
		    regCritQualityOk = TTK91AnalyserExtraUtils.checkRegisterCriteria(crit, studentHiddenCPU, teacherHiddenCPU);
		}
	    }
	    else {
		critFound = true;
		regCritOk = TTK91AnalyserExtraUtils.checkRegisterCriteria(crit, studentCPU, teacherCPU);
		if ( (studentHiddenCPU != null) &&
		     (teacherHiddenCPU != null) && 
		     (regCritOk) ) {
		    regCritOk = TTK91AnalyserExtraUtils.checkRegisterCriteria(crit, studentHiddenCPU, teacherHiddenCPU);
		}
		if (!regCritOk) {
		    break; // kaik' on mänt'
		}
	    }
	}
	if (critFound) {
	    results.setRegisters(regCritOk);
	}
	if (regCritOk && qualityFound) {
	    results.setRegistersQuality(regCritQualityOk);
	}
    } // analyseRegisters

    /**
     * Apumetodi näyttötulosteisiin (crt) liittyvien kriteerien analysointiin 
     *
     */

    private void analyseScreenOutput(TTK91AnalyseResults results) {

	TTK91TaskCriteria[] screenOutCrit = this.taskOptions.getScreenOutputCriterias();
	if (screenOutCrit == null) {
	    return; // ei kriteerejä, ei analysoitavaa
	}
	
	String studentPublicScreenOutput = this.analyseData.getStudentScreenOutputPublic();
	String studentHiddenScreenOutput = this.analyseData.getStudentScreenOutputHidden();
	String teacherPublicScreenOutput = this.analyseData.getTeacherScreenOutputPublic();
	String teacherHiddenScreenOutput = this.analyseData.getTeacherScreenOutputHidden();

	int[] studPubScreenOut = TTK91AnalyserExtraUtils.parseOutputArrays(studentPublicScreenOutput);
	int[] studHidScreenOut = TTK91AnalyserExtraUtils.parseOutputArrays(studentHiddenScreenOutput);
	int[] tchrPubScreenOut = TTK91AnalyserExtraUtils.parseOutputArrays(teacherPublicScreenOutput);
	int[] tchrHidScreenOut = TTK91AnalyserExtraUtils.parseOutputArrays(teacherHiddenScreenOutput);

	boolean outputCritOk = true;
	boolean outputQualityOk = true;
	boolean critFound = false;
	boolean qualityFound = false;

	for (int i = 0; i < screenOutCrit.length; ++i) {
	    TTK91TaskCriteria crit = screenOutCrit[i];
	    if (crit.getQuality()) {
		if (!outputQualityOk) {
		    continue; // valmiiksi poks
		}
		qualityFound = true;
		outputQualityOk = TTK91AnalyserExtraUtils.checkOutputCriteria(crit, studPubScreenOut, tchrPubScreenOut);
		if ( (studHidScreenOut != null) &&
		     (tchrHidScreenOut != null) &&
		     (outputQualityOk) ) {
		    outputQualityOk = TTK91AnalyserExtraUtils.checkOutputCriteria(crit, studHidScreenOut, tchrHidScreenOut);
		}
	    }
	    else {
		critFound = true;
		outputCritOk = TTK91AnalyserExtraUtils.checkOutputCriteria(crit, studPubScreenOut, tchrPubScreenOut);
		if ( (studHidScreenOut != null) &&
		     (tchrHidScreenOut != null) &&
		     (outputCritOk) ) {
		    outputCritOk = TTK91AnalyserExtraUtils.checkOutputCriteria(crit, studHidScreenOut, tchrHidScreenOut);
		}
		if (!outputCritOk) {
		    break; // kaik' on mänt'
		}
	    }
	}
	if (critFound) {
	    results.setScreenOutput(outputCritOk);
	}
	if (outputCritOk && qualityFound) {
	    results.setScreenOutputQuality(outputQualityOk);
	}
    }

    /**
     * Apumetodi tiedostotulosteisiin liittyiven kriteerien analysointiin
     *
     */

    private void analyseFileOutput(TTK91AnalyseResults results) {

	TTK91TaskCriteria[] fileOutCrit = this.taskOptions.getFileOutputCriterias();
	if (fileOutCrit == null) {
	    return; // ei kriteerejä, ei analysoitavaa
	}

	String studentPublicFileOutput = this.analyseData.getStudentFileOutputPublic();
	String studentHiddenFileOutput = this.analyseData.getStudentFileOutputHidden();
	String teacherPublicFileOutput = this.analyseData.getTeacherFileOutputPublic();
	String teacherHiddenFileOutput = this.analyseData.getTeacherFileOutputHidden();

	int[] studPubFileOut = TTK91AnalyserExtraUtils.parseOutputArrays(studentPublicFileOutput);
	int[] studHidFileOut = TTK91AnalyserExtraUtils.parseOutputArrays(studentHiddenFileOutput);
	int[] tchrPubFileOut = TTK91AnalyserExtraUtils.parseOutputArrays(teacherPublicFileOutput);
	int[] tchrHidFileOut = TTK91AnalyserExtraUtils.parseOutputArrays(teacherHiddenFileOutput);

	boolean outputCritOk = true;
	boolean outputQualityOk = true;
	boolean critFound = false;
	boolean qualityFound = false;

	for (int i = 0; i < fileOutCrit.length; ++i) {
	    TTK91TaskCriteria crit = fileOutCrit[i];
	    if (crit.getQuality()) {
		if (!outputQualityOk) {
		    continue; // valmiiksi poks
		}
		qualityFound = true;
		outputQualityOk = TTK91AnalyserExtraUtils.checkOutputCriteria(crit, studPubFileOut, tchrPubFileOut);
		if ( (studHidFileOut != null) &&
		     (tchrHidFileOut != null) &&
		     (outputQualityOk) ) {
		    outputQualityOk = TTK91AnalyserExtraUtils.checkOutputCriteria(crit, studHidFileOut, tchrHidFileOut);
		}
	    }
	    else {
		critFound = true;
		outputCritOk = TTK91AnalyserExtraUtils.checkOutputCriteria(crit, studPubFileOut, tchrPubFileOut);
		if ( (studHidFileOut != null) &&
		     (tchrHidFileOut != null) &&
		     (outputCritOk) ) {
		    outputCritOk = TTK91AnalyserExtraUtils.checkOutputCriteria(crit, studHidFileOut, tchrHidFileOut);
		}
		if (!outputCritOk) {
		    break; // kaik' on mänt'
		}
	    }
	}
	if (critFound) {
	    results.setFileOutput(outputCritOk);
	}
	if (outputCritOk && qualityFound) {
	    results.setFileOutputQuality(outputQualityOk);
	}
    }

    private void setStatistics(TTK91AnalyseResults results) {

	// FIXME: seuraavat puuttuvat. Tuleeko niitä edes? Rekisterit voisi olla kiva näkyvissä. Samoin ainakin crt-outputit...
	// int[] registers
	// int[] memory
	// int[] crt
	// int[] file
	
	results.setMemoryReferenceCount(analyseData.getCommandAmount());
	results.setStackSize(analyseData.getStackSize());
	results.setCodeSegmentSize(analyseData.getCodeSegmentSize());
	results.setDataSegmentSize(analyseData.getDataSegmentSize());
	results.setExecutedCommandsCount(analyseData.getCommandAmount());
    }
}
