package fi.helsinki.cs.koskelo.analyser;

public class TTK91AnalyseResults{

	// Peruskriteerit
	private boolean registers;
	private boolean memory;
	private boolean screenOutput;
	private boolean fileOutput;
	private boolean requiredCommands;
	private boolean forbiddenCommands;
	private boolean acceptedSize;
	private boolean memoryReferences;

	// laatukriteerit
	private boolean registersQuality;
	private boolean memoryQuality;
	private boolean screenOutputQuality;
	private boolean fileOutputQuality;
	private boolean requiredCommandsQuality;
	private boolean forbiddenCommandsQuality;
	private boolean optimalSize;
	private boolean memoryReferencesQuality;

	// Statistiikkatiedot

	public TTK91AnalyseResults() {

		registers = false;
		memory = false;
		screenOutput = false;
		fileOutput = false;
		requiredCommands = false;
		forbiddenCommands = false;
		acceptedSize = false;
		memoryReferences = false;

		// laatukriteerit
		registersQuality = false;
		memoryQuality = false;
		screenOutputQuality = false;
		fileOutputQuality = false;
		requiredCommandsQuality = false;
		forbiddenCommandsQuality = false;
		optimalSize = false;
		memoryReferencesQuality = false;

	}

	public void setRegisters(boolean b) {
		this.registers = b;
	}

	public void setMemory(boolean b) {
		this.memory = b;
	}

	public void setScreenOutput(boolean b) {
		this.screenOutput = b;
	}
	public void setFileOutput(boolean b) {
		this.fileOutput = b;
	}
	public void setRequiredCommands(boolean b) {
		this.requiredCommands = b;
	}
	public void setForbiddenCommands(boolean b) {
		this.forbiddenCommands = b;
	}

	public void setAcceptedSize(boolean b) {
		this.acceptedSize = b;
	}

	public void setMemoryReferences(boolean b) {
		this.memoryReferences = b;
	}

	public void setRegistersQuality(boolean b) {
		this.registersQuality = b;
	}

	public void setMemoryQuality(boolean b) {
		this.memoryQuality = b;
	}

	public void setScreenOutputQuality(boolean b) {
		this.screenOutputQuality = b;
	}

	public void setFileOutputQuality(boolean b) {
		this.fileOutputQuality = b;
	}

	public void setRequiredCommandsQuality(boolean b) {
		this.requiredCommandsQuality = b;
	}

	public void setForbiddenCommandsQuality(boolean b) {
		this.forbiddenCommandsQuality = b;
	}

	public void setOptimalSize(boolean b) {
		this.optimalSize = b;
	}
	
	public void setMemoryReferencesQuality(boolean b) {
		this.memoryReferencesQuality = b;
	}

	public void getRegisters(boolean b) {
		return this.registers;
	}

	public void getMemory(boolean b) {
		return this.memory;
	}

	public void getScreenOutput(boolean b) {
		return this.screenOutput;
	}
	public void getFileOutput(boolean b) {
		return this.fileOutput;
	}
	public void getRequiredCommands(boolean b) {
		return this.requiredCommands;
	}
	public void getForbiddenCommands(boolean b) {
		return this.forbiddenCommands;
	}

	public void getAcceptedSize(boolean b) {
		return this.acceptedSize;
	}

	public void getMemoryReferences(boolean b) {
		return this.memoryReferences;
	}

	public void getRegistersQuality(boolean b) {
		return this.registersQuality;
	}

	public void getMemoryQuality(boolean b) {
		return this.memoryQuality;
	}

	public void getScreenOutputQuality(boolean b) {
		return this.screenOutputQuality;
	}

	public void getFileOutputQuality(boolean b) {
		return this.fileOutputQuality;
	}

	public void getRequiredCommandsQuality(boolean b) {
		return this.requiredCommandsQuality;
	}

	public void getForbiddenCommandsQuality(boolean b) {
		return this.forbiddenCommandsQuality;
	}

	public void getOptimalSize(boolean b) {
		return this.optimalSize;
	}
	
	public void getMemoryReferencesQuality(boolean b) {
		return this.memoryReferencesQuality;
	}



}
