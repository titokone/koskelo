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
 * asetettu falseksi. False tarkoittaa, etteivät kyseisen ryhmän
 * kriteerit täyty, ja true vastaavasti että kriteerit täyttyvät.
 * Näiden arvoa voi muuttaa sopivalla set-metodilla.
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
/** Asetetaan rekisterikriteereihin liittyvä boolean. */
	public void setRegisters(boolean b) {
		this.registers = b;
	}

/** Asetetaan muistikriteereihin liittyvä boolean. */
	public void setMemory(boolean b) {
		this.memory = b;
	}

/** Asetetaan näyttötulostekriteereihin liittyvä boolean. */
	public void setScreenOutput(boolean b) {
		this.screenOutput = b;
	}
/** Asetetaan tiedostotulostekriteereihin liittyvä boolean. */
	public void setFileOutput(boolean b) {
		this.fileOutput = b;
	}
/** Asetetaan vaadittuihin konekäskyihin liittyvä boolean. */
	public void setRequiredCommands(boolean b) {
		this.requiredCommands = b;
	}
/** Asetetaan kiellettyihin konekäskyihin liittyvä boolean. */
	public void setForbiddenCommands(boolean b) {
		this.forbiddenCommands = b;
	}

/** Asetetaan hyväksyttävään kokoon liittyvä boolean. */
	public void setAcceptedSize(boolean b) {
		this.acceptedSize = b;
	}

/** Asetetaan muistiviitteiden määrään liittyvä boolean. */
	public void setMemoryReferences(boolean b) {
		this.memoryReferences = b;
	}

/** Asetetaan laadullisiin rekisterikriteereihin liittyvä boolean. */
	public void setRegistersQuality(boolean b) {
		this.registersQuality = b;
	}

/** Asetetaan laadullisiin muistikriteereihin liittyvä boolean. */
	public void setMemoryQuality(boolean b) {
		this.memoryQuality = b;
	}

/** Asetetaan laadullisiin näyttötuloste kriteereihin liittyvä boolean. */
	public void setScreenOutputQuality(boolean b) {
		this.screenOutputQuality = b;
	}

/** Asetetaan laadullisiin tiedostotuloste kriteereihin liittyvä boolean. */
	public void setFileOutputQuality(boolean b) {
		this.fileOutputQuality = b;
	}

/** Asetetaan laadullisiin vaadittuihin konekäskyihin liittyvä boolean. */
	public void setRequiredCommandsQuality(boolean b) {
		this.requiredCommandsQuality = b;
	}

/** Asetetaan laadullisiin kiellettyihin konekäskyihin liittyvä boolean. */
	public void setForbiddenCommandsQuality(boolean b) {
		this.forbiddenCommandsQuality = b;
	}

/** Asetetaan optimikokoon  liittyvä boolean. */

	public void setOptimalSize(boolean b) {
		this.optimalSize = b;
	}
	
/** Asetetaan laadulliseen muistiviitekriteeriin liittyvä boolean. Tällaista
 * muistiviitettä ei voi määritellä tehtävänannossa, mutta palaute voidaan
 */
	public void setMemoryReferencesQuality(boolean b) {
		this.memoryReferencesQuality = b;
	}
/** Rekisterikriteerien täyttyminen.*/
	public boolean getRegisters(boolean b) {
		return this.registers;
	}
/** Muistikriteerien täyttyminen. */
	public boolean getMemory(boolean b) {
		return this.memory;
	}

/** Näyttötulostekriteerien täyttyminen. */
	public boolean getScreenOutput(boolean b) {
		return this.screenOutput;
	}
	/** Tiedostotulostekriteerien täyttyminen.*/
	public boolean getFileOutput(boolean b) {
		return this.fileOutput;
	}
	/** Vaadittujen konekäskyjen esiintyminen.*/
	public boolean getRequiredCommands(boolean b) {
		return this.requiredCommands;
	}
	/** Kiellettyjen konekäskyjen esiintyminen. */
	public boolean getForbiddenCommands(boolean b) {
		return this.forbiddenCommands;
	}

	/** Hyväksytyn koon täyttyminen. */
	public boolean getAcceptedSize(boolean b) {
		return this.acceptedSize;
	}

	/** Vaaditun muistiviitemäärän täyttymienn.*/
	public boolean getMemoryReferences(boolean b) {
		return this.memoryReferences;
	}

	/** Rekisterien laatukriteerien täyttyminen. */
	public boolean getRegistersQuality(boolean b) {
		return this.registersQuality;
	}

	/** Laadullisten muistikriteerien täyttyminen. */
	public boolean getMemoryQuality(boolean b) {
		return this.memoryQuality;
	}

	/** Laadullisten näyttötulosteiden täyttyminen. */
	public boolean getScreenOutputQuality(boolean b) {
		return this.screenOutputQuality;
	}
	/** Laadullisten tiedostotulosteiden täyttyminen. */

	public boolean getFileOutputQuality(boolean b) {
		return this.fileOutputQuality;
	}

	/** Laadullisten vaadittujen konekäskyjen täyttyminen.*/
	public boolean getRequiredCommandsQuality(boolean b) {
		return this.requiredCommandsQuality;
	}
	/** Laadullisten kiellettyjen konekäskyjen täyttyminen.*/

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
