package fi.helsinki.cs.koskelo.analyser;

import fi.hu.cs.ttk91.TTK91CompileSource;
import fi.hu.cs.ttk91.TTK91Cpu;
import fi.hu.cs.ttk91.TTK91Memory;
import fi.hu.cs.ttk91.TTK91Exception;
import fi.hu.cs.ttk91.TTK91Application;
import fi.hu.cs.ttk91.TTK91Core;

import fi.hu.cs.titokone.Source;
import fi.hu.cs.titokone.Control;

import fi.hy.eassari.showtask.trainer.Feedback;
import fi.hy.eassari.showtask.trainer.ParameterString;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CommonAnalyser;


/**
 * Luokka staattisten TTK-91 -teht‰vien vastauksien tarkastamiseen
 * @author Lauri Liuhto
 *  
 */

public class StaticTTK91Analyser extends CommonAnalyser {

  AttributeCache cache;
  String taskID;
  String language;
  ParameterString initP;
  private TTK91Core core;

  /**
   * Konstruktori, joka luo uuden alustamattoman
   * StaticTTK91Analyserin, alustettava init-metodilla.
   *
   */

  public StaticTTK91Analyser() {
    this.cache = null;
    this.taskID = null;
    this.language = "FI";
    this.initP = null;
    this.core = null;
  } // StaticTTK91Analyser()


    /**
     * Konstruktori, joka luo alustetun StaticTTK91Analyserin,
     * cache-m‰‰rittely puuttuu. Teht‰v‰ registerCache-metodilla.
     * @param taskid teht‰v‰tunnus
     * @param language kielikoodi
     * @param initparams FIXME: kuvaus
     *
     */

  public StaticTTK91Analyser(String taskid, String language, String initparams) {
    this.taskID = taskid;
    this.language = language;
    this.initP = new ParameterString(initparams);
    this.core = new Control(null, null);
  } // StaticTTK91Analyser(String taskid, String language, String initparams)


    /**
     * Alustaa alustamattoman StaticTTK91Analyserin
     * @param taskid teht‰v‰tunnus
     * @param language kielikoodi
     * @param initparams FIXME: kuvaus
     */

  public void init(String taskid, String language, String initparams) {
    this.taskID = taskid;
    this.language = language;
    this.initP = new ParameterString(initparams);
    this.core = new Control(null, null);
  } // init


    /**
     * Analysoi vastauksen Titokoneella
     * @param answer TTK91-kielinen l‰hdekoodi
     * @param params 
     * @return palaute
     */

  public Feedback analyse(String[] answer, String params) { // FIXME:
                                                            // varsinainen
                                                            // toiminnallisuus
                                                            // kesken

    TTK91CompileSource src = StaticTTK91Analyser.parseSourceFromAnswer(answer);
    if (src == null) {
      return new Feedback(); // FIXME: oikeanlainen palaute kun sorsa
                             // ei suostu menem‰‰n edes
                             // TTK91CompileSource-muotoon - voiko
                             // n‰in edes k‰yd‰?
    }

    TTK91Application app;

    try {
      app = core.compile(src);
    }
    catch (TTK91Exception e) {
      return new Feedback(); // FIXME: oikeanlainen palaute, kun
                             // k‰‰nnˆs ep‰onnistuu
    }

    try {
      core.run(app, 5); // FIXME: maksimikierrosten m‰‰r‰
                        // taskoptionsista (tai jostain muualta)
    }
    catch (TTK91Exception e) {
      //	System.err.println("Ajonaikainen virhe"+e.getMessage());
      return new Feedback(); // FIXME: oikeanlainen palaute, kun
                             // suoritus ep‰onnistuu
    }

    TTK91Memory mem = core.getMemory();
    TTK91Cpu cpu = core.getCpu();


    return new Feedback();
  } // analyse


    /**
     * Ilmoitetaan StaticTTK91Analyserille tietokanta-cache
     * @param AttributeCache
     */

  public void registerCache(AttributeCache c) {
    this.cache = c;
  } // registerCache


    /**
     * Apumetodi, jolla kaivetaan l‰hdekoodi vastauksesta
     * @param answer
     */

  private static TTK91CompileSource parseSourceFromAnswer(String[] answer) {
    if (answer != null) { 
      return (TTK91CompileSource) new Source(answer[0]); // FIXME: toimiiko tosiaan n‰in helposti?
    }
    else {
      return null;
    }
  } // parseSourceFromAnswer

} // StaticTTK91Analyser
