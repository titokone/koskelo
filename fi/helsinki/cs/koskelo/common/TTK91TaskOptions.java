package fi.helsinki.cs.koskelo.common;

import java.util.Vector;

public class TTK91TaskOptions {

    //  private final static int MAX_INPUT = 50;
    private final static int MAX_COMMANDS = 10000;
    //  private final static int MAX_REQDENY_CMDS = 50;
    
    private Vector regcrits;
    private Vector memcrits;
    private Vector screencrits;
    private Vector filecrits;

    private String examplecode;
    private String taskdescription;

    private int[] publicinput;
    private int[] hiddeninput;

    private int comparemethod;
    private int maxcommands;
    private int acceptedsize;
    private int optimalsize;
    private TTK91TaskCriteria memrefcriteria;

    private String[] reqcmds;
    private String[] forbiddencmds;

    /**
     * Konstruktori
     */

    public TTK91TaskOptions() {
	this.regcrits = new Vector();
	this.memcrits = new Vector();
	this.screencrits = new Vector();
	this.filecrits = new Vector();
	this.examplecode = null;
	this.taskdescription = null;
	//    this.publicinput[] = new int[MAX_INPUT];
	//    this.hiddeninput[] = new int[MAX_INPUT];
	this.publicinput = null;
	this.hiddeninput = null;
	this.comparemethod = 0;
	this.maxcommands = MAX_COMMANDS;
	this.acceptedsize = 0;
	this.optimalsize = 0;
	this.memrefcriteria = null;
	//    this.reqcmds = new String[MAX_REQDENY_CMDS];
	//    this.forbiddencmds = new String[MAX_REQDENY_CMDS];
	this.reqcmds = null;
	this.forbiddencmds = null;
    }

    private static void addCriterias(TTK91TaskCriteria crit[], Vector critcollection) {
	if (crit != null) {
	    for (int i=0; i<crit.length; ++i) {
		critcollection.add(crit[i]);
	    }
	}
    }

    /**
     * Lis�� rekisterikriteerin kokoelman viimeiseksi.
     */
    public void addRegisterCriteria(TTK91TaskCriteria crit) {
	if (crit != null) {
	    this.regcrits.add(crit);
	}
    } // addRegisterCriteria


    /**
     * Lis�� rekisterin kriteerit kokoelmaan.
     */
    public void addRegisterCriteria(TTK91TaskCriteria[] crit) {
	TTK91TaskOptions.addCriterias(crit, this.regcrits);
    } // addRegisterCriteria(TTK91TaskCriteria[] crit)
  

    /**
     * Lis�� muistipaikan tai muuttujan kriteerin muisti-taulukon viimeiseksi.
     */
    public void addMemoryCriteria(TTK91TaskCriteria crit) {
	if (crit != null) {
	    this.memcrits.add(crit);
	}
    } // addMemoryCriteria


    /**
     * Lis�� muistipaikka- tai muuttujakriteerit kokoelmaan.
     */
    public void addMemoryCriteria(TTK91TaskCriteria[] crit) {
	TTK91TaskOptions.addCriterias(crit, this.memcrits);
    } // addMemoryCriteria(TTK91TaskCriteria[] crit)


    /**
     * Lis�� n�yt�n tulosteeseen liittyv�n kriteerin crt-taulukon viimeiseksi.
     */
    public void addScreenOutputCriteria(TTK91TaskCriteria crit) {
	if (crit != null) {
	    this.screencrits.add(crit);
	}
    } // addScreenOutputCriteria


    /**
     * Lis�� n�yt�n tulosteeseen liittyv�t kriteerit kokoelmaan.
     */
    public void addScreenOutputCriteria(TTK91TaskCriteria[] crit) {
	TTK91TaskOptions.addCriterias(crit, this.screencrits);
    } // addScreenOutputCriteria(TTK91TaskCriteria[] crit)


    /**
     * Lis�� tiedostoon tulostamiseen liittyv�n kriteerin file-taulukon viimeiseksi.
     */
    public void addFileOutputCriteria(TTK91TaskCriteria crit) {
	if (crit != null) {
	    this.filecrits.add(crit);
	}
    } // addFileOutputCriteria


    /**
     * Lis�� tiedostoon tulostamiseen liittyv�t kriteerit kokoelmaan.
     */
    public void addFileOutputCriteria(TTK91TaskCriteria[] crit) {
	TTK91TaskOptions.addCriterias(crit, this.filecrits);
    } // addFileOutputCriteria(TTK91TaskCriteria[] crit)


    /**
     * Asettaa mallivastauksen.
     */
    public void setExampleCode(String code) {
	this.examplecode = code;
    } // setExampleCode


    /**
     * Asettaa teht�v�nannon.
     */
    public void setTaskDescription(String description) {
	this.taskdescription = description;
    } // setTaskDescription


    /**
     * Asettaa public-taulukkoon opiskelijalle n�ytett�v�t sy�tteet.
     */
    public void setPublicInput(int[] input) {
	this.publicinput = input;
    } // setPublicInput


    /**
     * Asettaa hidden-taulukkoon salaiset testisy�tteet.
     */
    public void setHiddenInput(int[] input) {
	this.hiddeninput = input;
    } // setHiddenInput


    /**
     * Asettaa arvon miten teht�v�n oikeellisuus tutkitaan.
     */
    public void setCompareMethod(int method) {
	this.comparemethod = method;
    } // setCompareMethod


    /**
     * Asettaa rajan suoritettavien komentojen m��r�lle.
     */
    public void setMaxCommands(int value) {
	this.maxcommands = value;
    } // setMaxCommands


    /**
     * Asettaa hyv�ksymisrajan ohjelman koolle k�skyiss� mitattuna.
     */
    public void setAcceptedSize(int size) {
	this.acceptedsize = size;
    } // setAcceptedSize


    /**
     * Asettaa ohjelman ihannekoon k�skyiss� mitattuna.
     */
    public void setOptimalSize(int size) {
	this.optimalsize = size;
    } // setOptimalSize

    /**
     * Asettaa muistiviitteiden maaraan liityvan kriteerin
     * @param memrefs muistiviittekriteeri
     */
    public  void setMemRefCriteria(TTK91TaskCriteria memrefs) {
	this.memrefcriteria = memrefs;
    }

    /**
     * Asettaa ohjelmassa vaaditut komennot.
     */
    public void setRequiredCommands(String[] commands) {
	this.reqcmds = commands;
    } // setRequiredCommands


    /**
     * Asettaa ohjelmassa ehdottmasti kielletyt komennot.
     */
    public void setForbiddenCommands(String[] commands) {
	this.forbiddencmds = commands;
    } // setForbiddenCommands


    /**
     * Hakee kaikki rekistereihin liittyv�t kriteerit.
     */
    public TTK91TaskCriteria[] getRegisterCriterias() {
	TTK91TaskCriteria[] ret = new TTK91TaskCriteria[regcrits.size()];
	for (int i = 0; i < ret.length; ++i) {
	    ret[i] = (TTK91TaskCriteria) regcrits.get(i);
	}
	//      return (TTK91TaskCriteria[]) this.regcrits.toArray();
	return ret;
    } // getRegisterCriterias


    /**
     * Hakee kaikki muistiin liittyv�t kriteerit.
     */
    public TTK91TaskCriteria[] getMemoryCriterias() {
	TTK91TaskCriteria[] ret = new TTK91TaskCriteria[memcrits.size()];
	for (int i = 0; i < ret.length; ++i) {
	    ret[i] = (TTK91TaskCriteria) memcrits.get(i);
	}
	return ret;
	//    return (TTK91TaskCriteria[]) this.memcrits.toArray();
    } // getMemoryCriterias


    /**
     * Hakee kaikki n�yt�n tulosteisiin liittyv�t kriteerit.
     */
    public TTK91TaskCriteria[] getScreenOutputCriterias() {
	TTK91TaskCriteria[] ret = new TTK91TaskCriteria[screencrits.size()];
	for (int i = 0; i < ret.length; ++i) {
	    ret[i] = (TTK91TaskCriteria) screencrits.get(i);
	}
	return ret;
	//    return (TTK91TaskCriteria[]) this.screencrits.toArray();
    } // getScreenOutputCriterias


    /**
     * Hakee kaikki tiedoston tulosteisiin liittyv�t kriteerit.
     */
    public TTK91TaskCriteria[] getFileOutputCriterias() {
	TTK91TaskCriteria[] ret = new TTK91TaskCriteria[filecrits.size()];
	for (int i = 0; i < ret.length; ++i) {
	    ret[i] = (TTK91TaskCriteria) filecrits.get(i);
	}
	return ret;
	//    return (TTK91TaskCriteria[]) this.regcrits.toArray();
    } // getFileOutputCriterias


    /**
     * Hakee mallivastauksen.
     */
    public String getExampleCode() {
	return this.examplecode;
    } // getExampleCode


    /**
     * Hakee teht�v�nannon.
     */
    public String getTaskDescription() {
	return this.taskdescription;
    } // getTaskDescription


    /**
     * Hakee opiskelijalle n�ytett�v�t sy�tteet.
     */
    public int[] getPublicInput() {
	return this.publicinput;
    } // getPublicInput


    /**
     * Hakee salaiset testisy�tteet.
     */
    public int[] getHiddenInput() {
	return this.hiddeninput;
    } // getHiddenInput


    /**
     * Hakee vertailutavan jolla teht�v� tarkastetaan.
     */
    public int getCompareMethod() {
	return this.comparemethod;
    } // getCompareMethod


    /**
     * Hakee luvun joka ilmoittaa m��r�n komentoja jota korkeintaan ajetaan titokoneella.
     */
    public int getMaxCommands() {
	return this.maxcommands;
    } // getMaxCommands


    /**
     * Hakee luvun joka ilmoittaa monta komentoa koodissa saa enint��n esiinty�.
     */
    public int getAcceptedSize() {
	return this.acceptedsize;
    } // getAcceptedSize


    /**
     * Hakee luvun joka ilmoittaa mik� olisi ihannekoko ohjelmalle komentojen m��r�ll� mitattuna.
     */
    public int getOptimalSize() {
	return this.optimalsize;
    } // getOptimalSize

    /**
     * Hakee muistiviitekriteerin
     */
    public TTK91TaskCriteria getMemRefCriteria() {
	return this.memrefcriteria;
    }

    /**
     * Hakee ohjelmassa vaaditut komennot.
     */
    public String[] getRequiredCommands() {
	return this.reqcmds;
    } // getRequiredCommands


    /**
     * Hakee ohjelmassa kielletyt komennot.
     */
    public String[] getForbiddenCommands() {
	return this.forbiddencmds;
    } // getForbiddenCommands


    /**
     * Luo ja palauttaa uuden TTK91TaskOptions-olion 
     * @param taskstr muoto kuten toString() palauttaa
     */
    public TTK91TaskOptions parseFromString(String taskstr) {

	TTK91TaskOptions ret = new TTK91TaskOptions();
	String[] tasktokens = taskstr.split(":",-1);

	// regcrit
	if (tasktokens[0].length() > 0) {
	    String[] regcrit = tasktokens[0].split("|",-1);
	    for (int i=0; i < regcrit.length; ++i) {
		try {
		    ret.addRegisterCriteria(new TTK91TaskCriteria(regcrit[i]));
		} 
		catch (InvalidTTK91CriteriaException e) {
		    System.err.println("Kriteerin luonti ep�onnistui: "+e.getMessage());
		}
	    }
	}

	// memcrit
	if (tasktokens[1].length() > 0) {
	    String[] memcrit = tasktokens[1].split("|",-1);
	    for (int i=0; i < memcrit.length; ++i) {
		try {
		    ret.addMemoryCriteria(new TTK91TaskCriteria(memcrit[i]));
		}
		catch (InvalidTTK91CriteriaException e) {
		    System.err.println("Kriteerin luonti ep�onnistui: "+e.getMessage());
		}
	    }
	}

	// screencrit
	if (tasktokens[2].length() > 0) {
	    String[] screencrit = tasktokens[2].split("|", -1);
	    for (int i=0; i < screencrit.length; ++i) {
		try {
		    ret.addScreenOutputCriteria(new TTK91TaskCriteria(screencrit[i]));
		}
		catch (InvalidTTK91CriteriaException e) {
		    System.err.println("Kriteerin luonti ep�onnistui: "+e.getMessage());
		}
	    }
	}

	// filecrit
	if (tasktokens[3].length() > 0) {
	    String[] filecrit = tasktokens[3].split("|", -1);
	    for (int i=0; i < filecrit.length; ++i) {
		try {
		    ret.addFileOutputCriteria(new TTK91TaskCriteria(filecrit[i]));
		}
		catch (InvalidTTK91CriteriaException e) {
		    System.err.println("Kriteerin luonti ep�onnistui: "+e.getMessage());
		}
	    }
	}

	// examplecode
	if (tasktokens[4].length() > 1) {
	    ret.setExampleCode(tasktokens[4]);
	}
	
	// taskdescription
	if (tasktokens[5].length() > 1) {
	    ret.setTaskDescription(tasktokens[5]);
	}

	//    FIXME: t�m�n parseFromStringin ehdot; splitatussa vaiheessa;
	//    onko tyhj� vai ei? Koko metodin alueella tarkastettavaa!

	// publicinput
	String[] strpubinput = tasktokens[6].split("|", -1);
	if (strpubinput.length != 0) {
	    int[] pubinput = new int[strpubinput.length];
	    for (int i=0; i < pubinput.length; ++i) {
		try {
		    pubinput[i] = Integer.parseInt(strpubinput[i]);
		}
		catch (NumberFormatException e) {
		    System.err.println("String -> int -muunnos ep�onnistui: "+e.getMessage());
		}
	    }
	    ret.setPublicInput(pubinput);
	}

	// hiddeninput
	String[] strhiddinput = tasktokens[7].split("|", -1);
	if (strhiddinput.length != 0) {
	    int[] hiddinput = new int[strhiddinput.length];
	    for (int i=0; i < hiddinput.length; ++i) {
		try {
		    hiddinput[i] = Integer.parseInt(strpubinput[i]);
		}
		catch (NumberFormatException e) {
		    System.err.println("String -> int -muunnos ep�onnistui: "+e.getMessage());
		}
	    }
	    ret.setHiddenInput(hiddinput);
	}
	
	// comparemethod
	int cmpmeth = 0;
	try {
	    cmpmeth = Integer.parseInt(tasktokens[8]);
	}
	catch (NumberFormatException e) {
	    System.err.println("String -> int -muunnos ep�onnistui: "+e.getMessage());
	}
	ret.setCompareMethod(cmpmeth);

	// maxcommands
	int maxcmds = -1;
	try {
	    maxcmds = Integer.parseInt(tasktokens[9]);
	}
	catch (NumberFormatException e) {
	    System.err.println("String -> int -muunnos ep�onnistui: "+e.getMessage());
	}
	if (maxcmds >= 0) {
	    ret.setMaxCommands(maxcmds);
	}

	// acceptedsize
	int acceptsize = -1;
	try {
	    acceptsize = Integer.parseInt(tasktokens[10]);
	}
	catch (NumberFormatException e) {
	    System.err.println("String -> int -muunnos ep�onnistui: "+e.getMessage());
	}
	if (acceptsize >= 0) {
	    ret.setAcceptedSize(acceptsize);
	}
    
	// optimalsize
	int optimalsize = -1;
	try {
	    optimalsize = Integer.parseInt(tasktokens[11]);
	}
	catch (NumberFormatException e) {
	    System.err.println("String -> int -muunnos ep�onnistui: "+e.getMessage());
	}
	if (optimalsize >= 0) {
	    ret.setOptimalSize(optimalsize);
	}
    
	// memrefcriteria
	if (tasktokens[12].length() > 0) {
	    try {
		ret.setMemRefCriteria(new TTK91TaskCriteria(tasktokens[12]));
	    }
	    catch (InvalidTTK91CriteriaException e) {
		System.err.println("Kriteerin luonti ep�onnistui: "+e.getMessage());
	    }
	}

	// reqcmds
	String[] required = tasktokens[13].split("|", -1);
	ret.setRequiredCommands(required);
    
	// fbdcmds
	String[] forbidden = tasktokens[14].split("|", -1);
	ret.setRequiredCommands(forbidden);
    

	return ret;
    }  

    /**
     * Apulainen toString()-metodille. 
     * @param sb StringBuffer, johon kriteerit tulostetaan
     * @param crits Kriteeritaulukko
     */
 
    private static void toStringHelper(StringBuffer sb, TTK91TaskCriteria[] crits) {
	for (int i = 0; i < crits.length; ++i) {
	    sb.append(crits[i]);
	    sb.append("|");
	}
    } // toStringHelper

    /**
     * Apulainen toString()-metodille. 
     * @param sb StringBuffer, johon sy�tteet tulostetaan
     * @param inputs sy�tetaulukko
     */

    private static void toStringHelper(StringBuffer sb, int[] inputs) {
	if (inputs != null) {
	    for (int i = 0; i < inputs.length; ++i) {
		sb.append(inputs[i]);
		sb.append("|");
	    }
	}
    } // toStringHelper

    /**
     * Apulainen toString()-metodille. 
     * @param sb StringBuffer, johon kielletyt/vaaditut k�skyt tulostetaan
     * @param cmds taulukko kielletyille/vaadituille k�skyille
     */

    private static void toStringHelper(StringBuffer sb, String[] cmds) {
	if (cmds != null) {
	    for (int i = 0; i < cmds.length; ++i) {
		sb.append(cmds[i]);
		sb.append("|");
	    }
	}
    } // toStringHelper


    /**
     * Palauttaa merkkijonoesityksen TTK91TaskOptions-oliosta
     */

    public String toString() {

	StringBuffer strbf = new StringBuffer();

	strbf.append(":");

	//    Rekisterikriteerit
	TTK91TaskCriteria[] regs = this.getRegisterCriterias();
	toStringHelper(strbf, regs);
	strbf.append(":");

	//    Muistikriteerit
	TTK91TaskCriteria[] mems = this.getMemoryCriterias();
	toStringHelper(strbf, mems);
	strbf.append(":");

	//    N�ytt�tulostuksen kriteerit
	TTK91TaskCriteria[] screens = this.getScreenOutputCriterias();
	toStringHelper(strbf, screens);
	strbf.append(":");

	//    Tiedostotulostuksen kriteerit
	TTK91TaskCriteria[] files = this.getFileOutputCriterias();
	toStringHelper(strbf, files);
	strbf.append(":");
    
	//    Malliratkaisu
	String code = getExampleCode();
	if (code != null) {
	    strbf.append(code);
	}
	strbf.append(":");
    
	//    getTaskDescription()
	String description = getTaskDescription();
	if (description != null) {
	    strbf.append(description);
	}
	strbf.append(":");

	//    getPublicInput()
	int[] pubinput = getPublicInput();
	toStringHelper(strbf, pubinput);
	strbf.append(":");
	
	//    getHiddenInput()
	int[] hiddeninput = getHiddenInput();
	toStringHelper(strbf, hiddeninput);
	strbf.append(":");

	//    getCompareMethod()
	strbf.append(getCompareMethod());
	strbf.append(":");

	//    getMaxCommands()
	strbf.append(getMaxCommands());
	strbf.append(":");
    
	//    getAcceptedSize()  
	strbf.append(getAcceptedSize());
	strbf.append(":");

	//    getOptimalSize()
	strbf.append(getOptimalSize());
	strbf.append(":");

	//    getMemRefCriteria()
	strbf.append(getMemRefCriteria());
	strbf.append(":");

	//    getRequiredCommands()
	String[] req = getRequiredCommands();
	toStringHelper(strbf, req);
	strbf.append(":");

	//    getForbiddenCommands()
	String[] fbd = getForbiddenCommands();
	toStringHelper(strbf, fbd);
	strbf.append(":");

	return strbf.toString();
    } // toString()

    public static void main(String[] args) {
	TTK91TaskOptions testi = new TTK91TaskOptions();
	String[] req = {"foo", "bar", "barf"};
	String[] fbd = {"argh", "yrgh"};
		
	testi.setRequiredCommands(req);
	testi.setForbiddenCommands(fbd);

	System.out.println(testi);
	String testtext = testi.toString();
	String[] tasktokens = testtext.split(":",-1);
	for (int i=0; i<tasktokens.length; ++i) {
	    System.out.print(tasktokens[i]+" * ");
	}
	
    }

} // public class TTK91TaskOptions
