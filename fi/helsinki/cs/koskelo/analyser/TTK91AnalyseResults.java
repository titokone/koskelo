package fi.helsinki.cs.koskelo.analyser;

public class TTK91AnalyseResults{

    // Peruskriteerit
    private Boolean registersCriteria;
    private Boolean memoryCriteria;
    private Boolean screenOutputCriteria;
    private Boolean fileOutputCriteria;
    private Boolean requiredCommandsCriteria;
    private Boolean forbiddenCommandsCriteria;
    private Boolean acceptedSizeCriteria;
    private Boolean memoryReferencesCriteria;
    
    // laatukriteerit
    private Boolean registersQualityCriteria;
    private Boolean memoryQualityCriteria;
    private Boolean screenOutputQualityCriteria;
    private Boolean fileOutputQualityCriteria;
    private Boolean requiredCommandsQualityCriteria;
    private Boolean forbiddenCommandsQualityCriteria;
    private Boolean optimalSizeCriteria;
    private Boolean memoryReferencesQualityCriteria;

    // Statistiikkatiedot
    
    private int[] registers;
    private int[] memory;
    private int[] crt;
    private int[] file;
    private int memoryReferences;
    private int stackSize;
    private int codeSegmentSize;
    private int dataSegmentSize;
    private int executedCommands;
    
    // Analysointivirheet
    private boolean errorOccured;
    private String errorMessage;
    
    
    /** Luo TTK91AnalyseResults olion, jonka kaikki Booleanmuuttujat on
     * asetettu falseksi. False tarkoittaa, etteiv�t kyseisen ryhm�n
     * kriteerit t�yty, ja true vastaavasti ett� kriteerit t�yttyv�t.
     * N�iden arvoa voi muuttaa sopivalla set-metodilla.
     */
	
    public TTK91AnalyseResults() {

	registersCriteria = null;
	memoryCriteria = null;
	screenOutputCriteria = null;
	fileOutputCriteria = null;
	requiredCommandsCriteria = null;
	forbiddenCommandsCriteria = null;
	acceptedSizeCriteria = null;
	memoryReferencesCriteria = null;

	// laatukriteerit
	registersQualityCriteria = null;
	memoryQualityCriteria = null;
	screenOutputQualityCriteria = null;
	fileOutputQualityCriteria = null;
	requiredCommandsQualityCriteria = null;
	forbiddenCommandsQualityCriteria = null;
	optimalSizeCriteria = null;
	memoryReferencesQualityCriteria = null;

	registers = null;
	memory = null;
	crt = null;
	file = null;
	memoryReferences = -1;
	stackSize = -1;
	codeSegmentSize = -1;
	dataSegmentSize = -1;
	executedCommands = -1;

	errorOccured = false;
	errorMessage = "";
    }
    /** Asetetaan rekisterikriteereihin liittyv� Boolean. */
    public void setRegisters(boolean b) {
	this.registersCriteria = new Boolean(b);
    }

    /** Asetetaan muistikriteereihin liittyv� Boolean. */
    public void setMemory(boolean b) {
	this.memoryCriteria = new Boolean(b);
    }

    /** Asetetaan n�ytt�tulostekriteereihin liittyv� Boolean. */
    public void setScreenOutput(boolean b) {
	this.screenOutputCriteria = new Boolean(b);
    }
    /** Asetetaan tiedostotulostekriteereihin liittyv� Boolean. */
    public void setFileOutput(boolean b) {
	this.fileOutputCriteria = new Boolean(b);
    }
    /** Asetetaan vaadittuihin konek�skyihin liittyv� Boolean. */
    public void setRequiredCommands(boolean b) {
	this.requiredCommandsCriteria = new Boolean(b);
    }
    /** Asetetaan kiellettyihin konek�skyihin liittyv� Boolean. */
    public void setForbiddenCommands(boolean b) {
	this.forbiddenCommandsCriteria = new Boolean(b);
    }

    /** Asetetaan hyv�ksytt�v��n kokoon liittyv� Boolean. */
    public void setAcceptedSize(boolean b) {
	this.acceptedSizeCriteria = new Boolean(b);
    }

    /** Asetetaan muistiviitteiden m��r��n liittyv� Boolean. */
    public void setMemoryReferences(boolean b) {
	this.memoryReferencesCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin rekisterikriteereihin liittyv� Boolean. */
    public void setRegistersQuality(boolean b) {
	this.registersQualityCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin muistikriteereihin liittyv� Boolean. */
    public void setMemoryQuality(boolean b) {
	this.memoryQualityCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin n�ytt�tuloste kriteereihin liittyv� Boolean. */
    public void setScreenOutputQuality(boolean b) {
	this.screenOutputQualityCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin tiedostotuloste kriteereihin liittyv� Boolean. */
    public void setFileOutputQuality(boolean b) {
	this.fileOutputQualityCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin vaadittuihin konek�skyihin liittyv� Boolean. */
    public void setRequiredCommandsQuality(boolean b) {
	this.requiredCommandsQualityCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin kiellettyihin konek�skyihin liittyv� Boolean. */
    public void setForbiddenCommandsQuality(boolean b) {
	this.forbiddenCommandsQualityCriteria = new Boolean(b);
    }

    /** Asetetaan optimikokoon  liittyv� Boolean. */

    public void setOptimalSize(boolean b) {
	this.optimalSizeCriteria = new Boolean(b);
    }

    /** Asetetaan laadulliseen muistiviitekriteeriin liittyv� Boolean. T�llaista
     * muistiviitett� ei voi m��ritell� teht�v�nannossa, mutta palaute voidaan
     */

    public void setMemoryReferencesQuality(boolean b) {
	this.memoryReferencesQualityCriteria = new Boolean(b);
    }

    public void setRegisterValues(int[] registers) {
	this.registers = registers;
    }

    public void setMemoryValues(int[] memory) {
	this.memory = memory;
    }

    public void setCrt(int[] crt) {
	this.crt = crt;
    }

    public void setFile(int[] file) {
	this.file = file;
    }

    public void setMemoryReferenceCount(int memoryReferences) {
	this.memoryReferences = memoryReferences;
    }

    public void setStackSize(int stackSize) {
	this.stackSize = stackSize;
    }

    public void setCodeSegmentSize(int codeSegmentSize) {
	this.codeSegmentSize = codeSegmentSize;
    }

    public void setDataSegmentSize(int dataSegmentSize) {
	this.dataSegmentSize = dataSegmentSize;
    }

    public void setExecutedCommandsCount(int executedCommands) {
	this.executedCommands = executedCommands;
    }

    public void addErrorMessage(String message) {
	this.errorOccured = true;
	this.errorMessage += message;
    }

    /** Rekisterikriteerien t�yttyminen.*/
    public Boolean getRegisters() {
	return this.registersCriteria;
    }
    /** Muistikriteerien t�yttyminen. */
    public Boolean getMemory() {
	return this.memoryCriteria;
    }

    /** N�ytt�tulostekriteerien t�yttyminen. */
    public Boolean getScreenOutput() {
	return this.screenOutputCriteria;
    }
    /** Tiedostotulostekriteerien t�yttyminen.*/
    public Boolean getFileOutput() {
	return this.fileOutputCriteria;
    }
    /** Vaadittujen konek�skyjen esiintyminen.*/
    public Boolean getRequiredCommands() {
	return this.requiredCommandsCriteria;
    }
    /** Kiellettyjen konek�skyjen esiintyminen. */
    public Boolean getForbiddenCommands() {
	return this.forbiddenCommandsCriteria;
    }

    /** Hyv�ksytyn koon t�yttyminen. */
    public Boolean getAcceptedSize() {
	return this.acceptedSizeCriteria;
    }

    /** Vaaditun muistiviitem��r�n t�yttymienn.*/
    public Boolean getMemoryReferences() {
	return this.memoryReferencesCriteria;
    }

    /** Rekisterien laatukriteerien t�yttyminen. */
    public Boolean getRegistersQuality() {
	return this.registersQualityCriteria;
    }

    /** Laadullisten muistikriteerien t�yttyminen. */
    public Boolean getMemoryQuality() {
	return this.memoryQualityCriteria;
    }

    /** Laadullisten n�ytt�tulosteiden t�yttyminen. */
    public Boolean getScreenOutputQuality() {
	return this.screenOutputQualityCriteria;
    }
    /** Laadullisten tiedostotulosteiden t�yttyminen. */

    public Boolean getFileOutputQuality() {
	return this.fileOutputQualityCriteria;
    }

    /** Laadullisten vaadittujen konek�skyjen t�yttyminen.*/
    public Boolean getRequiredCommandsQuality() {
	return this.requiredCommandsQualityCriteria;
    }
    /** Laadullisten kiellettyjen konek�skyjen t�yttyminen.*/

    public Boolean getForbiddenCommandsQuality() {
	return this.forbiddenCommandsQualityCriteria;
    }
    /** Optimikoon saavuttaminen. */

    public Boolean getOptimalSize() {
	return this.optimalSizeCriteria;
    }

    /** Mahdollinen laadullinen muistiiviitepalaute. */
    public Boolean getMemoryReferencesQuality() {
	return this.memoryReferencesQualityCriteria;
    }
	
    public int[] getRegisterValues() {
	return this.registers;
    }

    public int[] getMemoryValues() {
	return this.memory;
    }

    public int[] getCrt() {
	return this.crt;
    }
	
    public int[] getFile() {
	return this.file;
    }

    public int getMemoryReferenceCount() {
	return this.memoryReferences;
    }

    public int getStackSize() {
	return this.stackSize;
    }

    public int getCodeSegmentSize() {
	return this.codeSegmentSize;
    }

    public int getDataSegmentSize() {
	return this.dataSegmentSize;
    }

    public int getExecutedCommandsCount() {
	return this.executedCommands;
    }

    public boolean errors() {
	return this.errorOccured;
    }

    public String getErrorMessage() {
	return this.errorMessage;
    }
}
