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
     * asetettu falseksi. False tarkoittaa, etteiv�t kyseisen ryhm�n
     * kriteerit t�yty, ja true vastaavasti ett� kriteerit t�yttyv�t.
     * N�iden arvoa voi muuttaa sopivalla set-metodilla.
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
    /** Asetetaan rekisterikriteereihin liittyv� Boolean. */
    public void setRegisters(boolean b) {
	this.registers = new Boolean(b);
    }

    /** Asetetaan muistikriteereihin liittyv� Boolean. */
    public void setMemory(boolean b) {
	this.memory = new Boolean(b);
    }

    /** Asetetaan n�ytt�tulostekriteereihin liittyv� Boolean. */
    public void setScreenOutput(boolean b) {
	this.screenOutput = new Boolean(b);
    }
    /** Asetetaan tiedostotulostekriteereihin liittyv� Boolean. */
    public void setFileOutput(boolean b) {
	this.fileOutput = new Boolean(b);
    }
    /** Asetetaan vaadittuihin konek�skyihin liittyv� Boolean. */
    public void setRequiredCommands(boolean b) {
	this.requiredCommands = new Boolean(b);
    }
    /** Asetetaan kiellettyihin konek�skyihin liittyv� Boolean. */
    public void setForbiddenCommands(boolean b) {
	this.forbiddenCommands = new Boolean(b);
    }

    /** Asetetaan hyv�ksytt�v��n kokoon liittyv� Boolean. */
    public void setAcceptedSize(boolean b) {
	this.acceptedSize = new Boolean(b);
    }

    /** Asetetaan muistiviitteiden m��r��n liittyv� Boolean. */
    public void setMemoryReferences(boolean b) {
	this.memoryReferences = new Boolean(b);
    }

    /** Asetetaan laadullisiin rekisterikriteereihin liittyv� Boolean. */
    public void setRegistersQuality(boolean b) {
	this.registersQuality = new Boolean(b);
    }

    /** Asetetaan laadullisiin muistikriteereihin liittyv� Boolean. */
    public void setMemoryQuality(boolean b) {
	this.memoryQuality = new Boolean(b);
    }

    /** Asetetaan laadullisiin n�ytt�tuloste kriteereihin liittyv� Boolean. */
    public void setScreenOutputQuality(boolean b) {
	this.screenOutputQuality = new Boolean(b);
    }

    /** Asetetaan laadullisiin tiedostotuloste kriteereihin liittyv� Boolean. */
    public void setFileOutputQuality(boolean b) {
	this.fileOutputQuality = new Boolean(b);
    }

    /** Asetetaan laadullisiin vaadittuihin konek�skyihin liittyv� Boolean. */
    public void setRequiredCommandsQuality(boolean b) {
	this.requiredCommandsQuality = new Boolean(b);
    }

    /** Asetetaan laadullisiin kiellettyihin konek�skyihin liittyv� Boolean. */
    public void setForbiddenCommandsQuality(boolean b) {
	this.forbiddenCommandsQuality = new Boolean(b);
    }

    /** Asetetaan optimikokoon  liittyv� Boolean. */

    public void setOptimalSize(boolean b) {
	this.optimalSize = new Boolean(b);
    }
	
    /** Asetetaan laadulliseen muistiviitekriteeriin liittyv� Boolean. T�llaista
     * muistiviitett� ei voi m��ritell� teht�v�nannossa, mutta palaute voidaan
     */
    public void setMemoryReferencesQuality(boolean b) {
	this.memoryReferencesQuality = new Boolean(b);
    }
    /** Rekisterikriteerien t�yttyminen.*/
    public Boolean getRegisters() {
	return this.registers;
    }
    /** Muistikriteerien t�yttyminen. */
    public Boolean getMemory() {
	return this.memory;
    }

    /** N�ytt�tulostekriteerien t�yttyminen. */
    public Boolean getScreenOutput() {
	return this.screenOutput;
    }
    /** Tiedostotulostekriteerien t�yttyminen.*/
    public Boolean getFileOutput() {
	return this.fileOutput;
    }
    /** Vaadittujen konek�skyjen esiintyminen.*/
    public Boolean getRequiredCommands() {
	return this.requiredCommands;
    }
    /** Kiellettyjen konek�skyjen esiintyminen. */
    public Boolean getForbiddenCommands() {
	return this.forbiddenCommands;
    }

    /** Hyv�ksytyn koon t�yttyminen. */
    public Boolean getAcceptedSize() {
	return this.acceptedSize;
    }

    /** Vaaditun muistiviitem��r�n t�yttymienn.*/
    public Boolean getMemoryReferences() {
	return this.memoryReferences;
    }

    /** Rekisterien laatukriteerien t�yttyminen. */
    public Boolean getRegistersQuality() {
	return this.registersQuality;
    }

    /** Laadullisten muistikriteerien t�yttyminen. */
    public Boolean getMemoryQuality() {
	return this.memoryQuality;
    }

    /** Laadullisten n�ytt�tulosteiden t�yttyminen. */
    public Boolean getScreenOutputQuality() {
	return this.screenOutputQuality;
    }
    /** Laadullisten tiedostotulosteiden t�yttyminen. */

    public Boolean getFileOutputQuality() {
	return this.fileOutputQuality;
    }

    /** Laadullisten vaadittujen konek�skyjen t�yttyminen.*/
    public Boolean getRequiredCommandsQuality() {
	return this.requiredCommandsQuality;
    }
    /** Laadullisten kiellettyjen konek�skyjen t�yttyminen.*/

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
