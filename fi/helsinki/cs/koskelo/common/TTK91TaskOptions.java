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

  private String[] reqcmds;
  private String[] forbiddencmds;

  /**
   * Konstruktori
   */

  public TTK91TaskOptions() {
    this.regcrits = null;
    this.memcrits = null;
    this.screencrits = null;
    this.filecrits = null;
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
    //    this.reqcmds = new String[MAX_REQDENY_CMDS];
    //    this.forbiddencmds = new String[MAX_REQDENY_CMDS];
    this.reqcmds = null;
    this.forbiddencmds = null;
  }

  private static void addCriterias(TTK91TaskCriteria[] crit, Vector critcollection) {
    if (crit != null) {
      if (critcollection == null) {
        critcollection = new Vector();
      }
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
      if (this.regcrits == null) {
        this.regcrits = new Vector();
      }
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
      if (this.memcrits == null) {
        this.memcrits = new Vector();
      }
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
      if (this.screencrits == null) {
        this.screencrits = new Vector();
      }
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
      if (this.filecrits == null) {
        this.filecrits = new Vector();
      }
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
    return (TTK91TaskCriteria[]) this.regcrits.toArray();
  } // getRegisterCriterias


  /**
   * Hakee kaikki muistiin liittyv�t kriteerit.
   */
  public TTK91TaskCriteria[] getMemoryCriterias() {
    return (TTK91TaskCriteria[]) this.regcrits.toArray();
  } // getMemoryCriterias


  /**
   * Hakee kaikki n�yt�n tulosteisiin liittyv�t kriteerit.
   */
  public TTK91TaskCriteria[] getScreenOutputCriterias() {
    return (TTK91TaskCriteria[]) this.regcrits.toArray();
  } // getScreenOutputCriterias


  /**
   * Hakee kaikki tiedoston tulosteisiin liittyv�t kriteerit.
   */
  public TTK91TaskCriteria[] getFileOutputCriterias() {
    return (TTK91TaskCriteria[]) this.regcrits.toArray();
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


} // public class TTK91TaskOptions
