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
    private int memoryReferences;
    private int stackSize;
    private int codeSegmentSize;
    private int dataSegmentSize;
    private int executedCommands;
    
    /** Luo TTK91AnalyseResults olion, jonka kaikki Booleanmuuttujat on
     * asetettu falseksi. False tarkoittaa, etteivät kyseisen ryhmän
     * kriteerit täyty, ja true vastaavasti että kriteerit täyttyvät.
     * Näiden arvoa voi muuttaa sopivalla set-metodilla.
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

	int[] registers = null;
	int[] memory = null;
	int[] crt = null;
	int memoryReferences = -1;
	int stackSize = -1;
	int codeSegmentSize = -1;
	int dataSegmentSize = -1;
	int executedCommands = -1;
    }
    /** Asetetaan rekisterikriteereihin liittyvä Boolean. */
    public void setRegisters(boolean b) {
	    this.registersCriteria = new Boolean(b);
    }

    /** Asetetaan muistikriteereihin liittyvä Boolean. */
    public void setMemory(boolean b) {
	    this.memoryCriteria = new Boolean(b);
    }

    /** Asetetaan näyttötulostekriteereihin liittyvä Boolean. */
    public void setScreenOutput(boolean b) {
	    this.screenOutputCriteria = new Boolean(b);
    }
    /** Asetetaan tiedostotulostekriteereihin liittyvä Boolean. */
    public void setFileOutput(boolean b) {
	    this.fileOutputCriteria = new Boolean(b);
    }
    /** Asetetaan vaadittuihin konekäskyihin liittyvä Boolean. */
    public void setRequiredCommands(boolean b) {
	    this.requiredCommandsCriteria = new Boolean(b);
    }
    /** Asetetaan kiellettyihin konekäskyihin liittyvä Boolean. */
    public void setForbiddenCommands(boolean b) {
	    this.forbiddenCommandsCriteria = new Boolean(b);
    }

    /** Asetetaan hyväksyttävään kokoon liittyvä Boolean. */
    public void setAcceptedSize(boolean b) {
	    this.acceptedSizeCriteria = new Boolean(b);
    }

    /** Asetetaan muistiviitteiden määrään liittyvä Boolean. */
    public void setMemoryReferences(boolean b) {
	    this.memoryReferencesCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin rekisterikriteereihin liittyvä Boolean. */
    public void setRegistersQuality(boolean b) {
	    this.registersQualityCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin muistikriteereihin liittyvä Boolean. */
    public void setMemoryQuality(boolean b) {
	    this.memoryQualityCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin näyttötuloste kriteereihin liittyvä Boolean. */
    public void setScreenOutputQuality(boolean b) {
	    this.screenOutputQualityCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin tiedostotuloste kriteereihin liittyvä Boolean. */
    public void setFileOutputQuality(boolean b) {
	    this.fileOutputQualityCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin vaadittuihin konekäskyihin liittyvä Boolean. */
    public void setRequiredCommandsQuality(boolean b) {
	    this.requiredCommandsQualityCriteria = new Boolean(b);
    }

    /** Asetetaan laadullisiin kiellettyihin konekäskyihin liittyvä Boolean. */
    public void setForbiddenCommandsQuality(boolean b) {
	    this.forbiddenCommandsQualityCriteria = new Boolean(b);
    }

    /** Asetetaan optimikokoon  liittyvä Boolean. */

    public void setOptimalSize(boolean b) {
	    this.optimalSizeCriteria = new Boolean(b);
    }

    /** Asetetaan laadulliseen muistiviitekriteeriin liittyvä Boolean. Tällaista
     * muistiviitettä ei voi määritellä tehtävänannossa, mutta palaute voidaan
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

    public void setExecutedCommandsCount(int excecutedCommands) {
	    this.executedCommands = executedCommands;
    }


    /** Rekisterikriteerien täyttyminen.*/
    public Boolean getRegisters() {
	    return this.registersCriteria;
    }
    /** Muistikriteerien täyttyminen. */
    public Boolean getMemory() {
	    return this.memoryCriteria;
    }

    /** Näyttötulostekriteerien täyttyminen. */
    public Boolean getScreenOutput() {
	    return this.screenOutputCriteria;
    }
    /** Tiedostotulostekriteerien täyttyminen.*/
    public Boolean getFileOutput() {
	    return this.fileOutputCriteria;
    }
    /** Vaadittujen konekäskyjen esiintyminen.*/
    public Boolean getRequiredCommands() {
	    return this.requiredCommandsCriteria;
    }
    /** Kiellettyjen konekäskyjen esiintyminen. */
    public Boolean getForbiddenCommands() {
	    return this.forbiddenCommandsCriteria;
    }

    /** Hyväksytyn koon täyttyminen. */
    public Boolean getAcceptedSize() {
	    return this.acceptedSizeCriteria;
    }

    /** Vaaditun muistiviitemäärän täyttymienn.*/
    public Boolean getMemoryReferences() {
	    return this.memoryReferencesCriteria;
    }

    /** Rekisterien laatukriteerien täyttyminen. */
    public Boolean getRegistersQuality() {
	    return this.registersQualityCriteria;
    }

    /** Laadullisten muistikriteerien täyttyminen. */
    public Boolean getMemoryQuality() {
	    return this.memoryQualityCriteria;
    }

    /** Laadullisten näyttötulosteiden täyttyminen. */
    public Boolean getScreenOutputQuality() {
	    return this.screenOutputQualityCriteria;
    }
    /** Laadullisten tiedostotulosteiden täyttyminen. */

    public Boolean getFileOutputQuality() {
	    return this.fileOutputQualityCriteria;
    }

    /** Laadullisten vaadittujen konekäskyjen täyttyminen.*/
    public Boolean getRequiredCommandsQuality() {
	    return this.requiredCommandsQualityCriteria;
    }
    /** Laadullisten kiellettyjen konekäskyjen täyttyminen.*/

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

    public int getMemoryReferenceCount() {
	   return this.memoryReferences;
    }

    public int setStackSize() {
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


}
