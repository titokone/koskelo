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
/** Luo TTK91AnalyseResults olion, jonka kaikki booleanmuuttujat on
 * asetettu falseksi. False tarkoittaa, etteiv�t kyseisen ryhm�n
 * kriteerit t�yty, ja true vastaavasti ett� kriteerit t�yttyv�t.
 * N�iden arvoa voi muuttaa sopivalla set-metodilla.
 */
	
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
/** Asetetaan rekisterikriteereihin liittyv� boolean. */
	public void setRegisters(boolean b) {
		this.registers = b;
	}

/** Asetetaan muistikriteereihin liittyv� boolean. */
	public void setMemory(boolean b) {
		this.memory = b;
	}

/** Asetetaan n�ytt�tulostekriteereihin liittyv� boolean. */
	public void setScreenOutput(boolean b) {
		this.screenOutput = b;
	}
/** Asetetaan tiedostotulostekriteereihin liittyv� boolean. */
	public void setFileOutput(boolean b) {
		this.fileOutput = b;
	}
/** Asetetaan vaadittuihin konek�skyihin liittyv� boolean. */
	public void setRequiredCommands(boolean b) {
		this.requiredCommands = b;
	}
/** Asetetaan kiellettyihin konek�skyihin liittyv� boolean. */
	public void setForbiddenCommands(boolean b) {
		this.forbiddenCommands = b;
	}

/** Asetetaan hyv�ksytt�v��n kokoon liittyv� boolean. */
	public void setAcceptedSize(boolean b) {
		this.acceptedSize = b;
	}

/** Asetetaan muistiviitteiden m��r��n liittyv� boolean. */
	public void setMemoryReferences(boolean b) {
		this.memoryReferences = b;
	}

/** Asetetaan laadullisiin rekisterikriteereihin liittyv� boolean. */
	public void setRegistersQuality(boolean b) {
		this.registersQuality = b;
	}

/** Asetetaan laadullisiin muistikriteereihin liittyv� boolean. */
	public void setMemoryQuality(boolean b) {
		this.memoryQuality = b;
	}

/** Asetetaan laadullisiin n�ytt�tuloste kriteereihin liittyv� boolean. */
	public void setScreenOutputQuality(boolean b) {
		this.screenOutputQuality = b;
	}

/** Asetetaan laadullisiin tiedostotuloste kriteereihin liittyv� boolean. */
	public void setFileOutputQuality(boolean b) {
		this.fileOutputQuality = b;
	}

/** Asetetaan laadullisiin vaadittuihin konek�skyihin liittyv� boolean. */
	public void setRequiredCommandsQuality(boolean b) {
		this.requiredCommandsQuality = b;
	}

/** Asetetaan laadullisiin kiellettyihin konek�skyihin liittyv� boolean. */
	public void setForbiddenCommandsQuality(boolean b) {
		this.forbiddenCommandsQuality = b;
	}

/** Asetetaan optimikokoon  liittyv� boolean. */

	public void setOptimalSize(boolean b) {
		this.optimalSize = b;
	}
	
/** Asetetaan laadulliseen muistiviitekriteeriin liittyv� boolean. T�llaista
 * muistiviitett� ei voi m��ritell� teht�v�nannossa, mutta palaute voidaan
 */
	public void setMemoryReferencesQuality(boolean b) {
		this.memoryReferencesQuality = b;
	}
/** Rekisterikriteerien t�yttyminen.*/
	public boolean getRegisters(boolean b) {
		return this.registers;
	}
/** Muistikriteerien t�yttyminen. */
	public boolean getMemory(boolean b) {
		return this.memory;
	}

/** N�ytt�tulostekriteerien t�yttyminen. */
	public boolean getScreenOutput(boolean b) {
		return this.screenOutput;
	}
	/** Tiedostotulostekriteerien t�yttyminen.*/
	public boolean getFileOutput(boolean b) {
		return this.fileOutput;
	}
	/** Vaadittujen konek�skyjen esiintyminen.*/
	public boolean getRequiredCommands(boolean b) {
		return this.requiredCommands;
	}
	/** Kiellettyjen konek�skyjen esiintyminen. */
	public boolean getForbiddenCommands(boolean b) {
		return this.forbiddenCommands;
	}

	/** Hyv�ksytyn koon t�yttyminen. */
	public boolean getAcceptedSize(boolean b) {
		return this.acceptedSize;
	}

	/** Vaaditun muistiviitem��r�n t�yttymienn.*/
	public boolean getMemoryReferences(boolean b) {
		return this.memoryReferences;
	}

	/** Rekisterien laatukriteerien t�yttyminen. */
	public boolean getRegistersQuality(boolean b) {
		return this.registersQuality;
	}

	/** Laadullisten muistikriteerien t�yttyminen. */
	public boolean getMemoryQuality(boolean b) {
		return this.memoryQuality;
	}

	/** Laadullisten n�ytt�tulosteiden t�yttyminen. */
	public boolean getScreenOutputQuality(boolean b) {
		return this.screenOutputQuality;
	}
	/** Laadullisten tiedostotulosteiden t�yttyminen. */

	public boolean getFileOutputQuality(boolean b) {
		return this.fileOutputQuality;
	}

	/** Laadullisten vaadittujen konek�skyjen t�yttyminen.*/
	public boolean getRequiredCommandsQuality(boolean b) {
		return this.requiredCommandsQuality;
	}
	/** Laadullisten kiellettyjen konek�skyjen t�yttyminen.*/

	public boolean getForbiddenCommandsQuality(boolean b) {
		return this.forbiddenCommandsQuality;
	}
	/** Optimikoon saavuttaminen. */

	public boolean getOptimalSize(boolean b) {
		return this.optimalSize;
	}
	
	/** Mahdollinen laadullinen muistiiviitepalaute. */
	public boolean getMemoryReferencesQuality(boolean b) {
		return this.memoryReferencesQuality;
	}



}
