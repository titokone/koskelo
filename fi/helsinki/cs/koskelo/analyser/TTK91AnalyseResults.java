package fi.helsinki.cs.koskelo.analyser;

public class TTK91AnalyseResults{

    // Peruskriteerit
    private Boolean registers;
    private Boolean memory;
    private Boolean screenOutput;
    private Boolean fileOutput;
    private Boolean requiredCommands;
    private Boolean forbiddenCommands;
    private Boolean acceptedSize;
    private Boolean memoryReferences;

    // laatukriteerit
    private Boolean registersQuality;
    private Boolean memoryQuality;
    private Boolean screenOutputQuality;
    private Boolean fileOutputQuality;
    private Boolean requiredCommandsQuality;
    private Boolean forbiddenCommandsQuality;
    private Boolean optimalSize;
    private Boolean memoryReferencesQuality;

    // Statistiikkatiedot
    /** Luo TTK91AnalyseResults olion, jonka kaikki Booleanmuuttujat on
     * asetettu falseksi. False tarkoittaa, etteivät kyseisen ryhmän
     * kriteerit täyty, ja true vastaavasti että kriteerit täyttyvät.
     * Näiden arvoa voi muuttaa sopivalla set-metodilla.
     */
	
    public TTK91AnalyseResults() {

	registers = null;
	memory = null;
	screenOutput = null;
	fileOutput = null;
	requiredCommands = null;
	forbiddenCommands = null;
	acceptedSize = null;
	memoryReferences = null;

	// laatukriteerit
	registersQuality = null;
	memoryQuality = null;
	screenOutputQuality = null;
	fileOutputQuality = null;
	requiredCommandsQuality = null;
	forbiddenCommandsQuality = null;
	optimalSize = null;
	memoryReferencesQuality = null;

    }
    /** Asetetaan rekisterikriteereihin liittyvä Boolean. */
    public void setRegisters(boolean b) {
	this.registers = new Boolean(b);
    }

    /** Asetetaan muistikriteereihin liittyvä Boolean. */
    public void setMemory(boolean b) {
	this.memory = new Boolean(b);
    }

    /** Asetetaan näyttötulostekriteereihin liittyvä Boolean. */
    public void setScreenOutput(boolean b) {
	this.screenOutput = new Boolean(b);
    }
    /** Asetetaan tiedostotulostekriteereihin liittyvä Boolean. */
    public void setFileOutput(boolean b) {
	this.fileOutput = new Boolean(b);
    }
    /** Asetetaan vaadittuihin konekäskyihin liittyvä Boolean. */
    public void setRequiredCommands(boolean b) {
	this.requiredCommands = new Boolean(b);
    }
    /** Asetetaan kiellettyihin konekäskyihin liittyvä Boolean. */
    public void setForbiddenCommands(boolean b) {
	this.forbiddenCommands = new Boolean(b);
    }

    /** Asetetaan hyväksyttävään kokoon liittyvä Boolean. */
    public void setAcceptedSize(boolean b) {
	this.acceptedSize = new Boolean(b);
    }

    /** Asetetaan muistiviitteiden määrään liittyvä Boolean. */
    public void setMemoryReferences(boolean b) {
	this.memoryReferences = new Boolean(b);
    }

    /** Asetetaan laadullisiin rekisterikriteereihin liittyvä Boolean. */
    public void setRegistersQuality(boolean b) {
	this.registersQuality = new Boolean(b);
    }

    /** Asetetaan laadullisiin muistikriteereihin liittyvä Boolean. */
    public void setMemoryQuality(boolean b) {
	this.memoryQuality = new Boolean(b);
    }

    /** Asetetaan laadullisiin näyttötuloste kriteereihin liittyvä Boolean. */
    public void setScreenOutputQuality(boolean b) {
	this.screenOutputQuality = new Boolean(b);
    }

    /** Asetetaan laadullisiin tiedostotuloste kriteereihin liittyvä Boolean. */
    public void setFileOutputQuality(boolean b) {
	this.fileOutputQuality = new Boolean(b);
    }

    /** Asetetaan laadullisiin vaadittuihin konekäskyihin liittyvä Boolean. */
    public void setRequiredCommandsQuality(boolean b) {
	this.requiredCommandsQuality = new Boolean(b);
    }

    /** Asetetaan laadullisiin kiellettyihin konekäskyihin liittyvä Boolean. */
    public void setForbiddenCommandsQuality(boolean b) {
	this.forbiddenCommandsQuality = new Boolean(b);
    }

    /** Asetetaan optimikokoon  liittyvä Boolean. */

    public void setOptimalSize(boolean b) {
	this.optimalSize = new Boolean(b);
    }
	
    /** Asetetaan laadulliseen muistiviitekriteeriin liittyvä Boolean. Tällaista
     * muistiviitettä ei voi määritellä tehtävänannossa, mutta palaute voidaan
     */
    public void setMemoryReferencesQuality(boolean b) {
	this.memoryReferencesQuality = new Boolean(b);
    }
    /** Rekisterikriteerien täyttyminen.*/
    public Boolean getRegisters() {
	return this.registers;
    }
    /** Muistikriteerien täyttyminen. */
    public Boolean getMemory() {
	return this.memory;
    }

    /** Näyttötulostekriteerien täyttyminen. */
    public Boolean getScreenOutput() {
	return this.screenOutput;
    }
    /** Tiedostotulostekriteerien täyttyminen.*/
    public Boolean getFileOutput() {
	return this.fileOutput;
    }
    /** Vaadittujen konekäskyjen esiintyminen.*/
    public Boolean getRequiredCommands() {
	return this.requiredCommands;
    }
    /** Kiellettyjen konekäskyjen esiintyminen. */
    public Boolean getForbiddenCommands() {
	return this.forbiddenCommands;
    }

    /** Hyväksytyn koon täyttyminen. */
    public Boolean getAcceptedSize() {
	return this.acceptedSize;
    }

    /** Vaaditun muistiviitemäärän täyttymienn.*/
    public Boolean getMemoryReferences() {
	return this.memoryReferences;
    }

    /** Rekisterien laatukriteerien täyttyminen. */
    public Boolean getRegistersQuality() {
	return this.registersQuality;
    }

    /** Laadullisten muistikriteerien täyttyminen. */
    public Boolean getMemoryQuality() {
	return this.memoryQuality;
    }

    /** Laadullisten näyttötulosteiden täyttyminen. */
    public Boolean getScreenOutputQuality() {
	return this.screenOutputQuality;
    }
    /** Laadullisten tiedostotulosteiden täyttyminen. */

    public Boolean getFileOutputQuality() {
	return this.fileOutputQuality;
    }

    /** Laadullisten vaadittujen konekäskyjen täyttyminen.*/
    public Boolean getRequiredCommandsQuality() {
	return this.requiredCommandsQuality;
    }
    /** Laadullisten kiellettyjen konekäskyjen täyttyminen.*/

    public Boolean getForbiddenCommandsQuality() {
	return this.forbiddenCommandsQuality;
    }
    /** Optimikoon saavuttaminen. */

    public Boolean getOptimalSize() {
	return this.optimalSize;
    }
	
    /** Mahdollinen laadullinen muistiiviitepalaute. */
    public Boolean getMemoryReferencesQuality() {
	return this.memoryReferencesQuality;
    }
}
