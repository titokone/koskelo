package fi.helsinki.cs.koskelo.analyser;

// import TTK91CompileSource; // FIXME: T‰m‰ kuuluu oikeasti titokoneen rajapintaluokkaan, nyt ruma kehitysaikainen kludge
// pit‰isi olla tyyliin
// import fi.hu.cs.ttk91.TTK91CompileSource;

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
  } // StaticTTK91Analyser()


    /**
     * Konstruktori, joka luo alustetun StaticTTK91Analyserin,
     * cache-m‰‰rittely puuttuu. Teht‰v‰ registerCache-metodilla.
     * @param taskid 
     * @param language
     * @param initparams
     *
     */

  public StaticTTK91Analyser(String taskid, String language, String initparams) {
    this.taskID = taskid;
    this.language = language;
    this.initP = new ParameterString(initparams);
  } // StaticTTK91Analyser(String taskid, String language, String initparams)


    /**
     * Alustaa alustamattoman StaticTTK91Analyserin
     * @param taskid 
     * @param language
     * @param initparams
     */

  public void init(String taskid, String language, String initparams) {
    this.taskID = taskid;
    this.language = language;
    this.initP = new ParameterString(initparams);
  } // init


    /**
     * Analysoi vastauksen Titokoneella
     * @param answer
     * @param params
     * @return Feedback 
     */

  public Feedback analyse(String[] answer, String params) { // FIXME: varsinainen toiminnallisuus puuttuu
    TTK91CompileSource src; 
    src = StaticTTK91Analyser.parseSourceFromAnswer(answer);
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
