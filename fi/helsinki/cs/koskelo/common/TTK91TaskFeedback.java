
/**
 * Kääreluokka joka sisältää kaiken tehtävään liittyvän palautteen.
 */

package fi.helsinki.cs.koskelo.common;

public class TTK91TaskFeedback {

	private String acceptedSizePos;
	private String acceptedSizeNeg;

	private String optimalSizePos;
	private String optimalSizeNeg;

	private String requiredComPos;
	private String requiredComNeg;

	private String forbiddenComPos;
  private String forbiddenComNeg;

	private String registerPos;
	private String registerNeg;

	private String memoryPos;
	private String memoryNeg;

	private String memoryRefPos;
	private String memoryRefNeg;

	private String screenOutPos;
	private String screenOutNeg;

	private String fileOutPos;
	private String fileOutNeg;
	
	
	/**
	 * Asettaa positiiven palautteen ohjelman maksimikoosta.
	 *
	 * @param positive kriteeriin liittyvä positiivinen palaute 
	 */
	public void setAcceptedSizeFeedbackPositive(String positive) {
		this.acceptedSizePos = positive;
	}//setAcceptedSizeFeedback

	/**
	 * Asettaa negatiivisen palautteen ohjelman maksimikoosta.
	 *
	 * @param negative kriteeriin liittyvä negatiivinen palaute
	 */
	public void setAcceptedSizeFeedbackNegative(String negative) {
		this.acceptedSizeNeg = negative;
	}//setAcceptedSizeFeedbackNegative

	/**
	 * Asettaa positiivisen palautteen ohjelman optimaalisesta koosta.
	 *
	 * @param positive kriteeriin liittyvä positiivinen palaute
	 */
	public void setOptimalSizeFeedbackPositive(String positive) {
		this.optimalSizePos = positive;
	}//setOptimalSizeFeedbackPositive

	/**
	 * Asettaa negatiivisen palautteen ohjelman optimaalisesta koosta.
	 *
	 * @param negative kriteeriin liittyvä negatiivinen palaute
	 */
	public void setOptimalSizeFeedbackNegative(String negative) {
		this.optimalSizeNeg = negative;
	}//setOptimalSizeFeedbackNegative

	/**
	 * Asettaa positiivisen palautteen ohjelman vaadituista käskyistä.
	 *
	 * @param positive kriteeriin liittyvä positiivinen palaute
	 */
	public void setRequiredCommandsFeedbackPositive(String positive) {
		this.requiredComPos = positive;
	}//setRequiredCommandsFeedbackPositive

	/**
	 * Asettaa negatiivisen palautteen ohjelman vaadituista käskyistä.
	 *
	 * @param negative kriteeriin liittyvä negatiivinen palaute
	 */
	public void setRequiredCommandsFeedbackNegative(String negative) {
		this.requiredComNeg = negative;
	}//setRequiredCommandsFeedbackNegative

	/**
	 * Asettaa positiivisen palautteen ohjelman kielletyistä käskyistä.
	 *
	 * @param positive kriteeriin liittyvä positiivinen palaute
	 */
	public void setForbiddenCommandsFeedbackPositive(String positive) {
		this.forbiddenComPos = positive;
	}//setForbiddenCommandsFeedbackPositive

	/**
	 * Asettaa negatiivisen palautteen ohjelman kielletyistä käskyistä.
	 *
	 * @param negative kriteeriin liittyvä negatiivinen palaute
	 */
	public void setForbiddenCommandsFeedbackNegative(String negative) {
		this.forbiddenComNeg = negative;
	}//setForbiddenCommandsFeedbackNegative

	/**
	 * Asettaa positiivisen palautteen rekistereistä.
	 *
	 * @param positive kriteeriin liittyvä positiivinen palaute
	 */
	public void setRegisterFeedbackPositive(String positive) {
		this.registerPos = positive;
	}//setRegisterFeedbackPositive

	/**
	 * Asettaa negatiivisen palautteen rekistereistä.
	 *
	 * @param negative kriteeriin liittyvä negatiivinen palaute
	 */
	public void setRegisterFeedbackNegative(String negative) {
		this.registerNeg = negative;
	}//setRegisterFeedbackNegative

	/**
	 * Asettaa positiivisen palautteen muistipaikoista ja muuttujista.
	 *
	 * @param positive kriteeriin liittyvä positiivinen palaute
	 */
	public void setMemoryFeedbackPositive(String positive) {
		this.memoryPos = positive;
	}//setMemoryFeedbackPositive
	
	/**
	 * Asettaa negatiivisen palautteen muistipaikoista ja muuttujista.
	 *
	 * @param negative kriteeriin liittyvä negatiivinen palaute
	 */
	public void setMemoryFeedbackNegative(String negative) {
		this.memoryNeg = negative;
	}//setMemoryFeedbackNegative
	
	/**
	 * Asettaa positiivisen palautteen muistiviitteiden määrästä.
	 *
	 * @param positive kriteeriin liittyvä positiivinen palaute
	 */
	public void setMemoryReferencesFeedbackPositive(String positive) {
		this.memoryRefPos = positive;
	}//setMemoryReferencesFeedbackPositive

	/**
	 * Asettaa negatiivisen palautteen muistiviitteiden määrästä.
	 *
	 * @param negative kriteeriin liittyvä negatiivinen palaute
	 */
	public void setMemoryReferencesFeedbackNegative(String negative) {
		this.memoryRefNeg = negative;
	}//setMemoryReferencesFeedbackNegative

	/**
	 * Asettaa positiivisen palautteen näytön tulosteista.
	 *
	 * @param positive kriteeriin liittyvä positiivinen palaute
	 */
	public void setScreenOutputFeedbackPositive(String positive) {
		this.screenOutPos = positive;
	}//setScreenOutputFeedbackPositive

	/**
	 * Asettaa negatiivisen palautteen näytön tulosteista.
	 *
	 * @param negative kriteeriin liittyvä negatiivinen palaute
	 */
	public void setScreenOutputFeedbackNegative(String negative) {
		this.screenOutNeg = negative;
	}//setScreenOutputFeedbackNegative

	/**
	 * Asettaa positiivisen palautteen tiedoston tulosteista.
	 *
	 * @param positive kriteeriin liittyvä positiivinen palaute
	 */
	public void setFileOutputFeedbackPositive(String positive) {
		this.fileOutPos = positive;
	}//setFileOutputFeedbackPositive

	/**
	 * Asettaa negatiivisen palautteen tiedoston tulosteista.
	 *
	 * @param negative kriteeriin liittyvä negatiivinen palaute
	 */
	public void setFileOutputFeedbackNegative(String negative) {
		this.fileOutNeg = negative;
	}//setFileOutputFeedbackNegative

	/**
	 * Antaa positiivisen palautteen maksimikoosta.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getAcceptedSizeFeedbackPositive() {
		return this.acceptedSizePos;
	}//getMaxSizeFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen maksimikoosta.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getAcceptedSizeFeedbackNegative() {
		return this.acceptedSizeNeg;
	}//getMaxSizeFeedbackNegative

	/**
	 * Antaa positiivisen palautteen optimaalisesta koosta.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getOptimalSizeFeedbackPositive() {
		return this.optimalSizePos;
	}//getOptimalSizeFeedbackPositive
	
	/**
	 * Antaa negatiivisen palautteen optimaalisesta koosta.
	 *
	 * @return kriteeriin liittyvä palaute
	 */ 
	public String getOptimalSizeFeedbackNegative() {
		return this.optimalSizeNeg;
	}//getOptimalSizeFeedbackNegative

	/**
	 * Antaa positiivisen palautteen vaadituista käskyistä.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getRequiredCommandsFeedbackPositive() {
		return this.requiredComPos;
	}//getRequiredCommandsFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen vaadituista käskyistä.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getRequiredCommandsFeedbackNegative() {
		return this.requiredComNeg;
	}//getRequiredCommandsFeedbackNegative

	/**
	 * Antaa positiivisen palautteen kielletyistä käskyistä.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getForbiddenCommandsFeedbackPositive() {
		return this.forbiddenComPos;
	}//getForbiddenCommandsFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen kielletyistä käskyistä.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getForbiddenCommandsFeedbackNegative() {
		return this.forbiddenComNeg;
	}//getForbiddenCommandsFeedbackNegative

	/**
	 * Antaa positiivisen palautteen rekistereistä.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getRegisterFeedbackPositive() {
		return this.registerPos;
	}//getRegisterFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen rekistereistä.
	 *
	 * @return kriteeriin liittyvä palaute
	 */ 
	public String getRegisterFeedbackNegative() {
		return this.registerNeg;
	}//getRegisterFeedbackNegative

	/**
	 * Antaa positiivisen palautteen muistipaikoista ja muuttujista.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getMemoryFeedbackPositive() {
		return this.memoryPos;
	}//getMemoryFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen muistipaikoista ja muuttujista.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getMemoryFeedbackNegative() {
		return this.memoryNeg;
	}//getMemoryFeedbackNegative

	/**
	 * Antaa positiivisen palautteen muistiviitteiden määrästä.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getMemoryReferencesFeedbackPositive() {
		return this.memoryRefPos;
	}//getMemoryReferencesFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen muistiviitteiden määrästä.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getMemoryReferencesFeedbackNegative() {
		return this.memoryRefNeg;
	}//getMemoryReferencesFeedbackNegative
	
	/**
	 * Antaa positiivisen palautteen näytön tulosteista.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getScreenOutputFeedbackPositive() {
		return this.screenOutPos;
	}//getScreenOutputFeedbackPositive
	
	/**
	 * Antaa negatiivisen palautteen näytön tulosteista.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getScreenOutputFeedbackNegative() {
		return this.screenOutNeg;
	}//getScreenOutputFeedbackNegative

	/**
	 * Antaa positiivisen palautteen tiedoston tulosteista.
	 *
	 * @return kriteeriin liittyvä palaute
	 */
	public String getFileOutputFeedbackPositive() {
		return this.fileOutPos;
	}//getFileOutputFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen tiedoston tulosteista.
	 *
	 * @return kriteeriin liittyvä palaute
	 */ 
	public String getFileOutputFeedbackNegative() {
		return this.fileOutNeg;
	}//getFileOutputFeedbackNegative

}//class
