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
     * Lis�� rekisterin kriteerin rekisteri-taulukon viimeiseksi.
     */
    public void addRegisterCriteria(TTK91TaskCriteria crit) {
    } // addRegisterCriteria

    /**
     * Lis�� muistipaikan tai muuttujan kriteerin muisti-taulukon viimeiseksi.
     */
    public void addMemoryCriteria(TTK91TaskCriteria crit) {
    } // addMemoryCriteria

    /**
     * Lis�� n�yt�n tulosteeseen liittyv�n kriteerin crt-taulukon viimeiseksi.
     */
    public void addScreenOutputCriteria(TTK91TaskCriteria crit) {
    } // addScreenOutputCriteria

    /**
     * Lis�� tiedostoon tulostamiseen liittyv�n kriteerin file-taulukon viimeiseksi.
     */
    public void addFileOutputCriteria(TTK91TaskCriteria crit) {
    } // addFileOutputCriteria

    /**
     * Asettaa mallivastauksen.
     */
    public void setExampleCode(String code) {
    } // setExampleCode

    /**
     * Asettaa teht�v�nannon.
     */
    public void setTaskDescription(String description) {
    } // setTaskDescription

    /**
     * Asettaa public-taulukkoon opiskelijalle n�ytett�v�t sy�tteet.
     */
    public void setPublicInput(int[] input) {
    } // setPublicInput

    /**
     * Asettaa hidden-taulukkoon salaiset testisy�tteet.
     */
    public void setHiddenInput(int[] input) {
    } // setHiddenInput

    /**
     * Asettaa arvon miten teht�v�n oikeellisuus tutkitaan.
     */
    public void setCompareMethod(int method) {
    } // setCompareMethod

    /**
     * Asettaa rajan suoritettavien komentojen m��r�lle.
     */
    public void setMaxCommands(int value) {
    } // setMaxCommands

    /**
     * Asettaa hyv�ksymisrajan ohjelman koolle k�skyiss� mitattuna.
     */
    public void setAcceptedSize(int size) {
    } // setAcceptedSize

    /**
     * Asettaa ohjelman ihannekoon k�skyiss� mitattuna.
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
     * Hakee kaikki rekistereihin liittyv�t kriteerit.
     */
    public TTK91TaskCriteria[] getRegisterCriterias() {
    } // getRegisterCriterias

    /**
     * Hakee kaikki muistiin liittyv�t kriteerit.
     */
    public TTK91TaskCriteria[] getMemoryCriterias() {
    } // getMemoryCriterias

    /**
     * Hakee kaikki n�yt�n tulosteisiin liittyv�t kriteerit.
     */
    public TTK91TaskCriteria[] getScreenOutputCriterias() {
    } // getScreenOutputCriterias

    /**
     * Hakee kaikki tiedoston tulosteisiin liittyv�t kriteerit.
     */
    public TTK91TaskCriteria[] getFileOutputCriterias() {
    } // getFileOutputCriterias

    /**
     * Hakee mallivastauksen.
     */
    public String getExampleCode() {
    } // getExampleCode

    /**
     * Hakee teht�v�nannon.
     */
    public String getTaskDescription() {
    } // getTaskDescription

    /**
     * Hakee opiskelijalle n�ytett�v�t sy�tteet.
     */
    public int[] getPublicInput() {
    } // getPublicInput

    /**
     * Hakee salaiset testisy�tteet.
     */
    public int[] getHiddenInput() {
    } // getHiddenInput

    /**
     * Hakee vertailutavan jolla teht�v� tarkastetaan.
     */
    public int getCompareMethod() {
    } // getCompareMethod

    /**
     * Hakee luvun joka ilmoittaa m��r�n komentoja jota korkeintaan ajetaan titokoneella.
     */
    public int getMaxCommands() {
    } // getMaxCommands

    /**
     * Hakee luvun joka ilmoittaa monta komentoa koodissa saa enint��n esiinty�.
     */
    public getAcceptedSize() {
    } // getAcceptedSize

    /**
     * Hakee luvun joka ilmoittaa mik� olisi ihannekoko ohjelmalle komentojen m��r�ll� mitattuna.
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
