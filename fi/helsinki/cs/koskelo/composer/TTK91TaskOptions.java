import java.util.Vector;

public class TTK91TaskOptions {

    /*
    private Vector registercriterias;
    private Vector memoryCriterias;
    private Vector screenOutputCriterias;
    private Vector fileOutputCriterias;
    private Vector ;
    */
    
    /**
     *
     */

    public TTK91TaskOptions {
    }


    /**
     * Lisää rekisterin kriteerin rekisteri-taulukon viimeiseksi.
     */
    public void addRegisterCriteria(TTK91TaskCriteria crit) {
    } // addRegisterCriteria

    /**
     * Lisää muistipaikan tai muuttujan kriteerin muisti-taulukon viimeiseksi.
     */
    public void addMemoryCriteria(TTK91TaskCriteria crit) {
    } // addMemoryCriteria

    /**
     * Lisää näytön tulosteeseen liittyvän kriteerin crt-taulukon viimeiseksi.
     */
    public void addScreenOutputCriteria(TTK91TaskCriteria crit) {
    } // addScreenOutputCriteria

    /**
     * Lisää tiedostoon tulostamiseen liittyvän kriteerin file-taulukon viimeiseksi.
     */
    public void addFileOutputCriteria(TTK91TaskCriteria crit) {
    } // addFileOutputCriteria

    /**
     * Asettaa mallivastauksen.
     */
    public void setExampleCode(String code) {
    } // setExampleCode

    /**
     * Asettaa tehtävänannon.
     */
    public void setTaskDescription(String description) {
    } // setTaskDescription

    /**
     * Asettaa public-taulukkoon opiskelijalle näytettävät syötteet.
     */
    public void setPublicInput(int[] input) {
    } // setPublicInput

    /**
     * Asettaa hidden-taulukkoon salaiset testisyötteet.
     */
    public void setHiddenInput(int[] input) {
    } // setHiddenInput

    /**
     * Asettaa arvon miten tehtävän oikeellisuus tutkitaan.
     */
    public void setCompareMethod(int method) {
    } // setCompareMethod

    /**
     * Asettaa rajan suoritettavien komentojen määrälle.
     */
    public void setMaxCommands(int value) {
    } // setMaxCommands

    /**
     * Asettaa hyväksymisrajan ohjelman koolle käskyissä mitattuna.
     */
    public void setAcceptedSize(int size) {
    } // setAcceptedSize

    /**
     * Asettaa ohjelman ihannekoon käskyissä mitattuna.
     */
    public void setOptimalSize(int size) {
    } // setOptimalSize

    /**
     * Asettaa ohjelmassa vaaditut komennot.
     */
    public void setRequiredCommands(String[] commands) {
    } // setRequiredCommands

    /**
     * Asettaa ohjelmassa ehdottmasti kielletyt komennot.
     */
    public void setForbiddenCommands(String[] commands) {
    } // setForbiddenCommands

    /**
     * Hakee kaikki rekistereihin liittyvät kriteerit.
     */
    public TTK91TaskCriteria[] getRegisterCriterias() {
    } // getRegisterCriterias

    /**
     * Hakee kaikki muistiin liittyvät kriteerit.
     */
    public TTK91TaskCriteria[] getMemoryCriterias() {
    } // getMemoryCriterias

    /**
     * Hakee kaikki näytön tulosteisiin liittyvät kriteerit.
     */
    public TTK91TaskCriteria[] getScreenOutputCriterias() {
    } // getScreenOutputCriterias

    /**
     * Hakee kaikki tiedoston tulosteisiin liittyvät kriteerit.
     */
    public TTK91TaskCriteria[] getFileOutputCriterias() {
    } // getFileOutputCriterias

    /**
     * Hakee mallivastauksen.
     */
    public String getExampleCode() {
    } // getExampleCode

    /**
     * Hakee tehtävänannon.
     */
    public String getTaskDescription() {
    } // getTaskDescription

    /**
     * Hakee opiskelijalle näytettävät syötteet.
     */
    public int[] getPublicInput() {
    } // getPublicInput

    /**
     * Hakee salaiset testisyötteet.
     */
    public int[] getHiddenInput() {
    } // getHiddenInput

    /**
     * Hakee vertailutavan jolla tehtävä tarkastetaan.
     */
    public int getCompareMethod() {
    } // getCompareMethod

    /**
     * Hakee luvun joka ilmoittaa määrän komentoja jota korkeintaan ajetaan titokoneella.
     */
    public int getMaxCommands() {
    } // getMaxCommands

    /**
     * Hakee luvun joka ilmoittaa monta komentoa koodissa saa enintään esiintyä.
     */
    public getAcceptedSize() {
    } // getAcceptedSize

    /**
     * Hakee luvun joka ilmoittaa mikä olisi ihannekoko ohjelmalle komentojen määrällä mitattuna.
     */
    public int getOptimalSize() {
    } // getOptimalSize

    /**
     * Hakee ohjelmassa vaaditut komennot.
     */
    public String[] getRequiredCommands() {
    } // getRequiredCommands

    /**
     * Hakee ohjelmassa kielletyt komennot.
     */
    public String[] getForbiddenCommands() {
    } // getForbiddenCommands


} // public class TTK91TaskOptions
