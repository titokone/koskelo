package fi.helsinki.cs.koskelo.common;

import java.util.Vector;

/**
 * Kääreluokka tehtävätiedoille
 * @author Lauri Liuhto
 */
public class TTK91TaskOptions {

	public final static int COMPARE_TO_SIMULATED = 0; // FIXME syntaxCheckeriin nämä kanssa käyttöön ja kai jsp-sivuille?
	public final static int COMPARE_TO_STATIC = 1;
  private final static int MAX_COMMANDS = 10000; //FIXME: kai tämä
                                                 //oletusarvo
                                                 //määritellään
                                                 //jossain muualla?
						 //[EN] ei, mutta
						 //jos verkkosivuilla jättää
						 //kentän tyhjäksi asettaa
						 //syntaxChecker saman arvon
    
  private Vector regcrits;
  private Vector memcrits;
  private Vector screencrits;
  private Vector filecrits;

  private String examplecode;
  private String taskdescription;

  private int[] publicinput;
  private int[] hiddeninput;

  private TTK91TaskCriteria[] screenoutput;
  private TTK91TaskCriteria[] fileoutput;
  
  private int comparemethod;
  private int maxcommands;
  private int acceptedsize;
  private int optimalsize;
  private TTK91TaskCriteria memrefcriteria;

  private String[] reqcmds;
  private String[] forbiddencmds;

  private TTK91TaskFeedback taskfeedback;

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
    this.publicinput = null;
    this.hiddeninput = null;
    this.screenoutput = null;
    this.fileoutput = null;
    this.comparemethod = 0;
    this.maxcommands = MAX_COMMANDS; // FIXME: kai tämä oletus määritellään jossain muualla?
    this.acceptedsize = 0;
    this.optimalsize = 0;
    this.memrefcriteria = null;
    this.reqcmds = null;
    this.forbiddencmds = null;
    this.taskfeedback = null;
  }

  /**
   * Yksityinen apumetodi kriteereiden lisäämiseen 
   * @param crit taulukko, jonka sisältö lisätään viitteenä saatuun säiliöön
   * @param critcollection viite säiliöön, johon kriteerit tallennetaan
   */
  private static void addCriterias(TTK91TaskCriteria crit[], Vector critcollection) {
    if (crit != null) {
	    for (int i=0; i<crit.length; ++i) {
        critcollection.add(crit[i]);
	    }
    }
  }

  /**
   * Lisää rekisterin kriteerit kokoelmaan.
   * @param crit Kokoelmaan lisättävä kriteeritaulukko
   */
  public void setRegisterCriterias(TTK91TaskCriteria[] crit) {
    this.regcrits = new Vector();
    TTK91TaskOptions.addCriterias(crit, this.regcrits);
  } // addRegisterCriteria(TTK91TaskCriteria[] crit)
  
  
  /**
   * Lisää rekisterikriteerin kokoelman viimeiseksi.
   * @param crit Kokoelmaan lisättävä kriteeri
   */
  public void addRegisterCriteria(TTK91TaskCriteria crit) {
    if (crit != null) {
	    this.regcrits.add(crit);
    }
  } // addRegisterCriteria


  /**
   * Lisää muistipaikka- tai muuttujakriteerit kokoelmaan.
   * @param crit Kokoelmaan lisättävä kriteeritaulukko
   */
  public void setMemoryCriterias(TTK91TaskCriteria[] crit) {
    this.memcrits = new Vector();
    TTK91TaskOptions.addCriterias(crit, this.memcrits);
  } // addMemoryCriteria(TTK91TaskCriteria[] crit)


  /**
   * Lisää muistipaikan tai muuttujan kriteerin muisti-taulukon viimeiseksi.
   * @param crit Kokoelmaan lisättävä kriteeri
   */
  public void addMemoryCriteria(TTK91TaskCriteria crit) {
    if (crit != null) {
	    this.memcrits.add(crit);
    }
  } // addMemoryCriteria


  /**
   * Lisää näytön tulosteeseen liittyvät kriteerit kokoelmaan.
   * @param crit Kokoelmaan lisättävä kriteeritaulukko
   */
  public void setScreenOutputCriterias(TTK91TaskCriteria[] crit) {
    this.screenoutput = crit;
  } // addScreenOutputCriteria(TTK91TaskCriteria[] crit)


  /**
   * Lisää näytön tulosteeseen liittyvän kriteerin crt-taulukon viimeiseksi.
   * @param crit Kokoelmaan lisättävä kriteeri
   */
  
 /* public void addScreenOutputCriteria(TTK91TaskCriteria crit) {
    if (crit != null) {
	    this.screencrits.add(crit);
    }
  } // addScreenOutputCriteria
*/

  /**
   * Lisää tiedostoon tulostamiseen liittyvät kriteerit kokoelmaan.
   * @param crit Kokoelmaan lisättävä kriteeritaulukko
   */
  public void setFileOutputCriterias(TTK91TaskCriteria[] crit) {
    this.fileoutput = crit;
  } // addFileOutputCriteria(TTK91TaskCriteria[] crit)


  /**
   * Lisää tiedostoon tulostamiseen liittyvän kriteerin file-taulukon viimeiseksi.
   * @param crit Kokoelmaan lisättävä kriteeri
   */
  /*
  public void addFileOutputCriteria(TTK91TaskCriteria crit) {
    if (crit != null) {
	    this.filecrits.add(crit);
    }
  } // addFileOutputCriteria
*/

  /**
   * Asettaa mallivastauksen.
   * @param code Asetettava TTK91-kielinen mallivastaus merkkijonona
   */
  public void setExampleCode(String code) {
    this.examplecode = code;
  } // setExampleCode


  /**
   * Asettaa tehtävänannon.
   * @param description Asetettava tehtävänanto merkkijonona
   */
  public void setTaskDescription(String description) {
    this.taskdescription = description;
  } // setTaskDescription


  /**
   * Asettaa public-taulukkoon opiskelijalle näytettävät syötteet.
   * @param input Asetettavat julkiset syötteet
   */
  public void setPublicInput(int[] input) {
    this.publicinput = input;
  } // setPublicInput


  /**
   * Asettaa hidden-taulukkoon salaiset testisyötteet.
   * @param input Asetettavat salaiset syötteet
   */
  public void setHiddenInput(int[] input) {
    this.hiddeninput = input;
  } // setHiddenInput


  /**
   * Asettaa arvon miten tehtävän oikeellisuus tutkitaan.
   * @param method //FIXME: selitys
   */
  public void setCompareMethod(int method) {
    this.comparemethod = method;
  } // setCompareMethod


  /**
   * Asettaa rajan suoritettavien komentojen määrälle.
   * @param value Suoritettavien TTK91-komentojen maksimimäärä
   */
  public void setMaxCommands(int value) {
    this.maxcommands = value;
  } // setMaxCommands


  /**
   * Asettaa hyväksymisrajan ohjelman koolle käskyissä mitattuna.
   * @param size TTK91-komentojen (hyväksyttävä) maksimimäärä 
   */
  public void setAcceptedSize(int size) {
    this.acceptedsize = size;
  } // setAcceptedSize


  /**
   * Asettaa ohjelman ihannekoon käskyissä mitattuna.
   * @param size TTK91-komentojen ihannemäärä
   */
  public void setOptimalSize(int size) {
    this.optimalsize = size;
  } // setOptimalSize

  /**
   * Asettaa muistiviitteiden maaraan liityvan kriteerin
   * @param memrefs muistiviitekriteeri
   */
  public  void setMemRefCriteria(TTK91TaskCriteria memrefs) {
    this.memrefcriteria = memrefs;
  }

  /**
   * Asettaa ohjelmassa vaaditut komennot.
   * @param commands TTK91-ohjelmassa vaadittavat komennot
   */
  public void setRequiredCommands(String[] commands) {
    this.reqcmds = commands;
  } // setRequiredCommands

  
  /**
   * Asettaa ohjelmassa ehdottomasti kielletyt komennot.
   * @param commands TTK91-ohjelmassa kielletyt komennot
   */
  public void setForbiddenCommands(String[] commands) {
    this.forbiddencmds = commands;
  } // setForbiddenCommands


  /**
   * Asettaa palautetiedot
   * @param feedback Tehtävän palautetiedot // FIXME: kuvaus
   */
  public void setTaskFeedback(TTK91TaskFeedback feedback) {
    this.taskfeedback = feedback;
  }


  /**
   * Hakee kaikki rekistereihin liittyvät kriteerit.
   * @return rekisterikriteerit
   */
  public TTK91TaskCriteria[] getRegisterCriterias() {
    TTK91TaskCriteria[] ret = new TTK91TaskCriteria[regcrits.size()];
    for (int i = 0; i < ret.length; ++i) {
	    ret[i] = (TTK91TaskCriteria) regcrits.get(i);
    }
    return ret;
  } // getRegisterCriterias


  /**
   * Hakee muistipaikkojen käyttöön liittyvät kriteerit.
   * @return muistipaikkakriteerit
   */
  public TTK91TaskCriteria[] getMemoryCriterias() {
    TTK91TaskCriteria[] ret = new TTK91TaskCriteria[memcrits.size()];
    for (int i = 0; i < ret.length; ++i) {
	    ret[i] = (TTK91TaskCriteria) memcrits.get(i);
    }
    return ret;
  } // getMemoryCriterias


  /**
   * Hakee kaikki näytön tulosteisiin liittyvät kriteerit.
   * @return kriteerit TTK91-simulaattorin näyttötulosteille
   */
  public TTK91TaskCriteria[] getScreenOutputCriterias() {
    return this.screenoutput;
  } // getScreenOutputCriterias


  /**
   * Hakee kaikki tiedoston tulosteisiin liittyvät kriteerit.
   * @return kriteerit TTK91-simulaattorin tiedostotulosteille
   */
  public TTK91TaskCriteria[] getFileOutputCriterias() {
    return this.fileoutput;
  } // getFileOutputCriterias


 
  /**
   * Hakee mallivastauksen.
   * @return mallivastaus merkkijonona
   */
  public String getExampleCode() {
    return this.examplecode;
  } // getExampleCode


  /**
   * Hakee tehtävänannon.
   * @return tehtävänanto
   */
  public String getTaskDescription() {
    return this.taskdescription;
  } // getTaskDescription


  /**
   * Hakee opiskelijalle näytettävät syötteet.
   * @return opiskelijalle näytettävät syötteet
   */
  public int[] getPublicInput() {
    return this.publicinput;
  } // getPublicInput


  /**
   * Hakee salaiset testisyötteet.
   * @return salaiset testisyötteet
   */
  public int[] getHiddenInput() {
    return this.hiddeninput;
  } // getHiddenInput


  /**
   * Hakee vertailutavan jolla tehtävä tarkastetaan. // FIXME: kuvaus
   * @return vertailutapa // FIXME: kuvaus
   */
  public int getCompareMethod() {
    return this.comparemethod;
  } // getCompareMethod


  /**
   * Hakee titokoneella suoritettavien käskyjen maksimilukumäärän.
   * @return maksimissaan suoritettavien käskyjen määrä
   */
  public int getMaxCommands() {
    return this.maxcommands;
  } // getMaxCommands


  /**
   * Hakee luvun joka ilmoittaa monta komentoa koodissa saa enintään esiintyä.
   * @return hyväksyttävä TTK91-koodin koko käskyinä
   */
  public int getAcceptedSize() {
    return this.acceptedsize;
  } // getAcceptedSize


  /**
   * Hakee luvun joka ilmoittaa mikä olisi ihannekoko ohjelmalle komentojen määrällä mitattuna.
   * @return ihannekoko TTK91-koodille käskyinä
   */
  public int getOptimalSize() {
    return this.optimalsize;
  } // getOptimalSize

  /**
   * Hakee muistiviitekriteerin
   * @return käytettyjen muistiviittausten määrää tarkasteleva kriteeri
   */
  public TTK91TaskCriteria getMemRefCriteria() {
    return this.memrefcriteria;
  }

  /**
   * Hakee ratkaisussa vaaditut komennot.
   * @return vaaditut komennot
   */
  public String[] getRequiredCommands() {
    return this.reqcmds;
  } // getRequiredCommands


  /**
   * Hakee ratkaisussa kielletyt komennot.
   * @return kielletyt komennot
   */
  public String[] getForbiddenCommands() {
    return this.forbiddencmds;
  } // getForbiddenCommands


  /**
   * Hakee ja palauttaa palautetiedot
   * @return palautetiedot
   */
  public TTK91TaskFeedback getTaskFeedback() {
    return this.taskfeedback;
  }


//   /**
//    * Luo ja palauttaa uuden TTK91TaskOptions-olion 
//    * @param taskstr muoto kuten toString() palauttaa
//    */
//   public TTK91TaskOptions parseFromString(String taskstr) {

//     TTK91TaskOptions ret = new TTK91TaskOptions();
//     String[] tasktokens = taskstr.split(":",-1);

//     // regcrit
//     if (tasktokens[0].length() > 0) {
// 	    String[] regcrit = tasktokens[0].split("|",-1);
// 	    for (int i=0; i < regcrit.length; ++i) {
//         try {
//           ret.addRegisterCriteria(new TTK91TaskCriteria(regcrit[i]));
//         } 
//         catch (InvalidTTK91CriteriaException e) {
//           System.err.println("Kriteerin luonti epäonnistui: "+e.getMessage());
//         }
// 	    }
//     }

//     // memcrit
//     if (tasktokens[1].length() > 0) {
// 	    String[] memcrit = tasktokens[1].split("|",-1);
// 	    for (int i=0; i < memcrit.length; ++i) {
//         try {
//           ret.addMemoryCriteria(new TTK91TaskCriteria(memcrit[i]));
//         }
//         catch (InvalidTTK91CriteriaException e) {
//           System.err.println("Kriteerin luonti epäonnistui: "+e.getMessage());
//         }
// 	    }
//     }

//     // screencrit
//     if (tasktokens[2].length() > 0) {
// 	    String[] screencrit = tasktokens[2].split("|", -1);
// 	    for (int i=0; i < screencrit.length; ++i) {
//         try {
//           ret.addScreenOutputCriteria(new TTK91TaskCriteria(screencrit[i]));
//         }
//         catch (InvalidTTK91CriteriaException e) {
//           System.err.println("Kriteerin luonti epäonnistui: "+e.getMessage());
//         }
// 	    }
//     }

//     // filecrit
//     if (tasktokens[3].length() > 0) {
// 	    String[] filecrit = tasktokens[3].split("|", -1);
// 	    for (int i=0; i < filecrit.length; ++i) {
//         try {
//           ret.addFileOutputCriteria(new TTK91TaskCriteria(filecrit[i]));
//         }
//         catch (InvalidTTK91CriteriaException e) {
//           System.err.println("Kriteerin luonti epäonnistui: "+e.getMessage());
//         }
// 	    }
//     }

//     // examplecode
//     if (tasktokens[4].length() > 1) {
// 	    ret.setExampleCode(tasktokens[4]);
//     }
	
//     // taskdescription
//     if (tasktokens[5].length() > 1) {
// 	    ret.setTaskDescription(tasktokens[5]);
//     }

//     //    FIXME: tämän parseFromStringin ehdot; splitatussa vaiheessa;
//     //    onko tyhjä vai ei? Koko metodin alueella tarkastettavaa!

//     // publicinput
//     String[] strpubinput = tasktokens[6].split("|", -1);
//     if (strpubinput.length != 0) {
// 	    int[] pubinput = new int[strpubinput.length];
// 	    for (int i=0; i < pubinput.length; ++i) {
//         try {
//           pubinput[i] = Integer.parseInt(strpubinput[i]);
//         }
//         catch (NumberFormatException e) {
//           System.err.println("String -> int -muunnos epäonnistui: "+e.getMessage());
//         }
// 	    }
// 	    ret.setPublicInput(pubinput);
//     }

//     // hiddeninput
//     String[] strhiddinput = tasktokens[7].split("|", -1);
//     if (strhiddinput.length != 0) {
// 	    int[] hiddinput = new int[strhiddinput.length];
// 	    for (int i=0; i < hiddinput.length; ++i) {
//         try {
//           hiddinput[i] = Integer.parseInt(strpubinput[i]);
//         }
//         catch (NumberFormatException e) {
//           System.err.println("String -> int -muunnos epäonnistui: "+e.getMessage());
//         }
// 	    }
// 	    ret.setHiddenInput(hiddinput);
//     }
	
//     // comparemethod
//     int cmpmeth = 0;
//     try {
// 	    cmpmeth = Integer.parseInt(tasktokens[8]);
//     }
//     catch (NumberFormatException e) {
// 	    System.err.println("String -> int -muunnos epäonnistui: "+e.getMessage());
//     }
//     ret.setCompareMethod(cmpmeth);

//     // maxcommands
//     int maxcmds = -1;
//     try {
// 	    maxcmds = Integer.parseInt(tasktokens[9]);
//     }
//     catch (NumberFormatException e) {
// 	    System.err.println("String -> int -muunnos epäonnistui: "+e.getMessage());
//     }
//     if (maxcmds >= 0) {
// 	    ret.setMaxCommands(maxcmds);
//     }

//     // acceptedsize
//     int acceptsize = -1;
//     try {
// 	    acceptsize = Integer.parseInt(tasktokens[10]);
//     }
//     catch (NumberFormatException e) {
// 	    System.err.println("String -> int -muunnos epäonnistui: "+e.getMessage());
//     }
//     if (acceptsize >= 0) {
// 	    ret.setAcceptedSize(acceptsize);
//     }
    
//     // optimalsize
//     int optimalsize = -1;
//     try {
// 	    optimalsize = Integer.parseInt(tasktokens[11]);
//     }
//     catch (NumberFormatException e) {
// 	    System.err.println("String -> int -muunnos epäonnistui: "+e.getMessage());
//     }
//     if (optimalsize >= 0) {
// 	    ret.setOptimalSize(optimalsize);
//     }
    
//     // memrefcriteria
//     if (tasktokens[12].length() > 0) {
// 	    try {
//         ret.setMemRefCriteria(new TTK91TaskCriteria(tasktokens[12]));
// 	    }
// 	    catch (InvalidTTK91CriteriaException e) {
//         System.err.println("Kriteerin luonti epäonnistui: "+e.getMessage());
// 	    }
//     }

//     // reqcmds
//     String[] required = tasktokens[13].split("|", -1);
//     ret.setRequiredCommands(required);
    
//     // fbdcmds
//     String[] forbidden = tasktokens[14].split("|", -1);
//     ret.setRequiredCommands(forbidden);
    

//     return ret;
//   }  

//   /**
//    * Apulainen toString()-metodille. 
//    * @param sb StringBuffer, johon kriteerit tulostetaan
//    * @param crits Kriteeritaulukko
//    */
 
//   private static void toStringHelper(StringBuffer sb, TTK91TaskCriteria[] crits) {
//     for (int i = 0; i < crits.length; ++i) {
// 	    sb.append(crits[i]);
// 	    sb.append("|");
//     }
//   } // toStringHelper

//     /**
//      * Apulainen toString()-metodille. 
//      * @param sb StringBuffer, johon syötteet tulostetaan
//      * @param inputs syötetaulukko
//      */

//   private static void toStringHelper(StringBuffer sb, int[] inputs) {
//     if (inputs != null) {
// 	    for (int i = 0; i < inputs.length; ++i) {
//         sb.append(inputs[i]);
//         sb.append("|");
// 	    }
//     }
//   } // toStringHelper

//     /**
//      * Apulainen toString()-metodille. 
//      * @param sb StringBuffer, johon kielletyt/vaaditut käskyt tulostetaan
//      * @param cmds taulukko kielletyille/vaadituille käskyille
//      */

//   private static void toStringHelper(StringBuffer sb, String[] cmds) {
//     if (cmds != null) {
// 	    for (int i = 0; i < cmds.length; ++i) {
//         sb.append(cmds[i]);
//         sb.append("|");
// 	    }
//     }
//   } // toStringHelper


//   private static void toStringHelper(StringBuffer sb, int[][] cmds) {
// 	  if(cmds != null) {
// 		  for(int i = 0; i < cmds.length; i++) {
			  
// 			  // voidaan olettaa että taulukon koko
// 			  // on kaksi, sillä outputteille joille
// 			  // tätä käytetään niiden jälkimmäisessä taulukossa
// 			  // on aina kaksi alkiota. Jos ei ole niin jossain
// 			  // aikaisemmin on tehty virhe.
// 			  // FIXME järkevämpi toteutus kuin int[][] output
// 			  // eille
// 			  sb.append("(");
// 			  sb.append(cmds[i][0]);
// 			  sb.append(",");
// 			  sb.append(cmds[i][1]);
// 			  sb.append(")");
// 			  sb.append("|");
// 		  }
// 	  }
//   }
				  
  
//     /**
//      * Palauttaa merkkijonoesityksen TTK91TaskOptions-oliosta
//      */

//   public String toString() {

//     StringBuffer strbf = new StringBuffer();

//     strbf.append(":");

//     //    Rekisterikriteerit
//     TTK91TaskCriteria[] regs = this.getRegisterCriterias();
//     toStringHelper(strbf, regs);
//     strbf.append(":");

//     //    Muistikriteerit
//     TTK91TaskCriteria[] mems = this.getMemoryCriterias();
//     toStringHelper(strbf, mems);
//     strbf.append(":");

//     //    Näyttötulostuksen kriteerit
   
//     TTK91TaskCriteria[] screens = this.getScreenOutputCriterias();
//     toStringHelper(strbf, screens);
//     strbf.append(":");

//     //    Tiedostotulostuksen kriteerit
//     TTK91TaskCriteria[] files = this.getFileOutputCriterias();
//     toStringHelper(strbf, files);
//     strbf.append(":");
    
//     //    Malliratkaisu
//     String code = getExampleCode();
//     if (code != null) {
// 	    strbf.append(code);
//     }
//     strbf.append(":");
    
//     //    getTaskDescription()
//     String description = getTaskDescription();
//     if (description != null) {
// 	    strbf.append(description);
//     }
//     strbf.append(":");

//     //    getPublicInput()
//     int[] pubinput = getPublicInput();
//     toStringHelper(strbf, pubinput);
//     strbf.append(":");
	
//     //    getHiddenInput()
//     int[] hiddeninput = getHiddenInput();
//     toStringHelper(strbf, hiddeninput);
//     strbf.append(":");

//     //    getCompareMethod()
//     strbf.append(getCompareMethod());
//     strbf.append(":");

//     //    getMaxCommands()
//     strbf.append(getMaxCommands());
//     strbf.append(":");
    
//     //    getAcceptedSize()  
//     strbf.append(getAcceptedSize());
//     strbf.append(":");

//     //    getOptimalSize()
//     strbf.append(getOptimalSize());
//     strbf.append(":");

//     //    getMemRefCriteria()
//     strbf.append(getMemRefCriteria());
//     strbf.append(":");

//     //    getRequiredCommands()
//     String[] req = getRequiredCommands();
//     toStringHelper(strbf, req);
//     strbf.append(":");

//     //    getForbiddenCommands()
//     String[] fbd = getForbiddenCommands();
//     toStringHelper(strbf, fbd);
//     strbf.append(":");

//     return strbf.toString();
//   } // toString()

  //   public static void main(String[] args) {
  //     TTK91TaskOptions testi = new TTK91TaskOptions();
  //     String[] req = {"foo", "bar", "barf"};
  //     String[] fbd = {"argh", "yrgh"};
  
  //     testi.setRequiredCommands(req);
  //     testi.setForbiddenCommands(fbd);
  
  //     System.out.println(testi);
  //     String testtext = testi.toString();
  //     String[] tasktokens = testtext.split(":",-1);
  //     for (int i=0; i<tasktokens.length; ++i) {
  // 	    System.out.print(tasktokens[i]+" * ");
  //     }
	
  //   }
  
} // public class TTK91TaskOptions
