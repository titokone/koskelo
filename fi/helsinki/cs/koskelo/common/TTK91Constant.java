package fi.helsinki.cs.koskelo.common;

/**
 * Luokka Koskelon käyttämien vakioiden yhtenäiseksi sijaintipaikaksi.
 */

public final class TTK91Constant {

	// common.TTK91TaskOptions/compareMethod:


/** Vertailu simuloituun titokoneen lopputilaan */
	public final static int COMPARE_TO_SIMULATED = 1; 
	/** Vertailu staattisiin arvoihin kriteereissä */
	public final static int COMPARE_TO_STATIC = 0;
	// analyser.TTK91RealAnalyser
	/** Vertailu simuloituun tilaan julkisilla syötteillä */
	public final static int COMPARE_TO_SIMULATED_PUBLIC = 3;
	/** Vertailu simuloituun tilaan piilosyötteillä. Simuloidaan
	 * myös julkisilla siis.
	 */
	public final static int COMPARE_TO_SIMULATED_HIDDEN = 5;

	/** Vertailu staattiseen tilaan julkisilla syötteillä */
	public final static int COMPARE_TO_STATIC_PUBLIC = 7;
	/** Vertailu simuloituun lopputilaan piilosyötteillä. Simuloidaan
	 * myös siis julkisilla
	 */
	public final static int COMPARE_TO_STATIC_HIDDEN = 11;

	/** maksimissaan ajettavien käskyjen määrä; "ikuisen silmukan esto"*/
	public final static int MAX_COMMANDS = 10000; 

	// common.TTK91TaskCriteria
	/** Epäkelpo alustamaton vertailu */
	public static final int INVALID = -1; // Alustamaton vertailu.
	/** &lt */
	public static final int LESS = 0; // <
	/** &lt= */
	public static final int LESSEQ = 1; // <=
	/** &gt */
	public static final int GREATER = 2; // >
	/** &gt= */
	public static final int GREATEREQ = 3; // >=
	/** == */
	public static final int EQUAL = 4; // =
	/** !\= Erisuuri */
	public static final int NOTEQUAL = 5; // !=
	/** , . Ei vertailua */
	public static final int NOTCOMPARABLE = 6; // Tulosteita varten joissa
	// ei ole loogista operaattoria

	// eAssarin virhekoodit
	/** Fataali virhe. eAssari. */
	public static final int FATAL_ERROR = 2;
	/** Virhe. eAssari. */
	public static final int ERROR = 1; 
	/** Ei virhettä. eAssari */
	public static final int NO_ERROR = 0;

}
