
/**
 * K��reluokka joka sis�lt�� kaiken teht�v��n liittyv�n palautteen.
 */

package fi.helsinki.cs.koskelo.common;

public class TTK91TaskFeedback {

	private String acceptedSizePositive;
	private String acceptedSizeNegative;
	private String acceptedSizeQuality;

	private String optimalSizePositive;
	private String optimalSizeNegative;
	private String optimalSizeQuality;

	private String requiredComPositive;
	private String requiredComNegative;
	private String requiredComQuality;

	private String forbiddenComPositive;
  private String forbiddenComNegative;
	private String forbiddenComQuality;

	private String registerPositive;
	private String registerNegative;
	private String registerQuality;

	private String memoryPositive;
	private String memoryNegative;
	private String memoryQuality;

	private String memoryRefPositive;
	private String memoryRefNegative;
	private String memoryRefQuality;

	private String screenOutPositive;
	private String screenOutNegative;
	private String screenOutQuality;

	private String fileOutPositive;
	private String fileOutNegative;
	private String fileOutQuality;


	/**
	 * Set-metodit
	 */
	
	/**
	 * Asettaa positiiven, negatiivisen ja laadullisen 
	 * palautteen ohjelman maksimikoosta.
	 *
	 * @param positive kriteeriin liittyv� positiivinen palaute 
	 * @param negative kriteeriin liittyv� negatiivinen palaute
	 * @param quality  kriteeriin liittyv� laadullinen palaute
	 */
	public void setAcceptedSizeFeedback(String positive,
																			String negative,
																			String quality) {
		this.acceptedSizePositive = positive;
		this.acceptedSizeNegative = negative;
		this.acceptedSizeQuality = quality;
	}//setAcceptedSizeFeedback


	/**
	 * Asettaa positiivisen, negatiivisen ja laadullisen  
	 * palautteen ohjelman optimaalisesta koosta.
	 *
	 * @param positive kriteeriin liittyv� positiivinen palaute
	 * @param negative kriteeriin liittyv� negatiivinen palaute
	 * @param quality  kriteeriin liittyv� laadullinen palaute
	 */
	public void setOptimalSizeFeedback(String positive,
																		 String negative,
																		 String quality) {
		this.optimalSizePositive = positive;
		this.optimalSizeNegative = negative;
		this.optimalSizeQuality = quality;
	}//setOptimalSizeFeedback

	/**
	 * Asettaa positiivisen, negatiivisen ja laadullisen 
	 * palautteen ohjelman vaadituista k�skyist�.
	 *
	 * @param positive kriteeriin liittyv� positiivinen palaute
	 * @param negative kriteeriin liittyv� negatiivinen palaute
	 * @param quality  kriteeriin liittyv� laadullinen palaute
	 */
	public void setRequiredCommandsFeedback(String positive,
																					String negative,
																					String quality) {
		this.requiredComPositive = positive;
		this.requiredComNegative = negative;
		this.requiredComQuality = quality;
	}//setRequiredCommandsFeedback

	/**
	 * Asettaa positiivisen, negatiivisen ja laadullisen 
	 * palautteen ohjelman kielletyist� k�skyist�.
	 *
	 * @param positive kriteeriin liittyv� positiivinen palaute
	 * @param negative kriteeriin liittyv� negatiivinen palaute
	 * @param quality  kriteeriin liittyv� laadullinen palaute
	 */
	public void setForbiddenCommandsFeedback(String positive,
																					 String negative,
																					 String quality) {
		this.forbiddenComPositive = positive;
		this.forbiddenComNegative = negative;
		this.forbiddenComQuality = quality;
	}//setForbiddenCommandsFeedback

	/**
	 * Asettaa positiivisen, negatiivisen ja laadullisen 
	 * palautteen rekistereist�.
	 *
	 * @param positive kriteeriin liittyv� positiivinen palaute
	 * @param negative kriteeriin liittyv� negatiivinen palaute
	 * @param quality  kriteeriin liittyv� laadullinen palaute
	 */
	public void setRegisterFeedback(String positive,
																	String negative,
																	String quality) {
		this.registerPositive = positive;
		this.registerNegative = negative;
		this.registerQuality = quality;
	}//setRegisterFeedback

	/**
	 * Asettaa positiivisen, negatiivisen ja laadullisen 
	 * palautteen muistipaikoista ja muuttujista.
	 *
	 * @param positive kriteeriin liittyv� positiivinen palaute
	 * @param negative kriteeriin liittyv� negatiivinen palaute
	 * @param quality  kriteeriin liittyv� laadullinen palaute
	 */
	public void setMemoryFeedback(String positive,
																String negative,
																String quality) {
		this.memoryPositive = positive;
		this.memoryNegative = negative;
		this.memoryQuality = quality;
	}//setMemoryFeedback
	
	/**
	 * Asettaa positiivisen, negatiivisen ja laadullisen 
	 * palautteen muistiviitteiden m��r�st�.
	 *
	 * @param positive kriteeriin liittyv� positiivinen palaute
	 * @param negative kriteeriin liittyv� negatiivinen palaute
	 * @param quality  kriteeriin liittyv� laadullinen palaute
	 */
	public void setMemoryReferencesFeedback(String positive,
																					String negative,
																					String quality) {
		this.memoryRefPositive = positive;
		this.memoryRefNegative = negative;
		this.memoryRefQuality = quality;
	}//setMemoryReferencesFeedback

	/**
	 * Asettaa positiivisen, negatiivisen ja laadullisen 
	 * palautteen n�yt�n tulosteista.
	 *
	 * @param positive kriteeriin liittyv� positiivinen palaute
	 * @param negative kriteeriin liittyv� negatiivinen palaute
	 * @param quality  kriteeriin liittyv� laadullinen palaute
	 */
	public void setScreenOutputFeedback(String positive,
																			String negative,
																			String quality) {
		this.screenOutPositive = positive;
		this.screenOutNegative = negative;
		this.screenOutQuality = quality;
	}//setScreenOutputFeedback

	/**
	 * Asettaa positiivisen, negatiivisen ja laadullisen 
	 * palautteen tiedoston tulosteista.
	 *
	 * @param positive kriteeriin liittyv� positiivinen palaute
	 * @param negative kriteeriin liittyv� negatiivinen palaute
	 * @param quality  kriteeriin liittyv� laadullinen palaute
	 */
	public void setFileOutputFeedback(String positive,
																		String negative,
																		String quality) {
		this.fileOutPositive = positive;
		this.fileOutNegative = negative;
		this.fileOutQuality = quality;
	}//setFileOutputFeedback


	/**
	 * Get-metodit.
	 */


	/**
	 * Antaa positiivisen palautteen maksimikoosta.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getAcceptedSizeFeedbackPositive() {
		return this.acceptedSizePositive;
	}//getMaxSizeFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen maksimikoosta.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getAcceptedSizeFeedbackNegative() {
		return this.acceptedSizeNegative;
	}//getMaxSizeFeedbackNegative

	/**
	 * Antaa laadullisen palautteen maksimikoosta.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getAcceptedSizeFeedbackQuality() {
		return this.acceptedSizeQuality;
	}//getMaxSizeFeedbackQuality

	/**
	 * Antaa positiivisen palautteen optimaalisesta koosta.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getOptimalSizeFeedbackPositive() {
		return this.optimalSizePositive;
	}//getOptimalSizeFeedbackPositive
	
	/**
	 * Antaa negatiivisen palautteen optimaalisesta koosta.
	 *
	 * @return kriteeriin liittyv� palaute
	 */ 
	public String getOptimalSizeFeedbackNegative() {
		return this.optimalSizeNegative;
	}//getOptimalSizeFeedbackNegative

	/**
	 * Antaa laadullisen palautteen optimaalisesta koosta.
	 *
	 * @return kriteeriin liittyv� palaute
	 */ 
	public String getOptimalSizeFeedbackQuality() {
		return this.optimalSizeQuality;
	}//getOptimalSizeFeedbackQuality

	/**
	 * Antaa positiivisen palautteen vaadituista k�skyist�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getRequiredCommandsFeedbackPositive() {
		return this.requiredComPositive;
	}//getRequiredCommandsFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen vaadituista k�skyist�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getRequiredCommandsFeedbackNegative() {
		return this.requiredComNegative;
	}//getRequiredCommandsFeedbackNegative

	/**
	 * Antaa laadullisen palautteen vaadituista k�skyist�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getRequiredCommandsFeedbackQuality() {
		return this.requiredComQuality;
	}//getRequiredCommandsFeedbackQuality

	/**
	 * Antaa positiivisen palautteen kielletyist� k�skyist�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getForbiddenCommandsFeedbackPositive() {
		return this.forbiddenComPositive;
	}//getForbiddenCommandsFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen kielletyist� k�skyist�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getForbiddenCommandsFeedbackNegative() {
		return this.forbiddenComNegative;
	}//getForbiddenCommandsFeedbackNegative

	/**
	 * Antaa laadullisen palautteen kielletyist� k�skyist�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getForbiddenCommandsFeedbackQuality() {
		return this.forbiddenComQuality;
	}//getForbiddenCommandsFeedbackQuality

	/**
	 * Antaa positiivisen palautteen rekistereist�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getRegisterFeedbackPositive() {
		return this.registerPositive;
	}//getRegisterFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen rekistereist�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */ 
	public String getRegisterFeedbackNegative() {
		return this.registerNegative;
	}//getRegisterFeedbackNegative

	/**
	 * Antaa laadullisen palautteen rekistereist�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */ 
	public String getRegisterFeedbackQuality() {
		return this.registerQuality;
	}//getRegisterFeedbackQuality

	/**
	 * Antaa positiivisen palautteen muistipaikoista ja muuttujista.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getMemoryFeedbackPositive() {
		return this.memoryPositive;
	}//getMemoryFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen muistipaikoista ja muuttujista.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getMemoryFeedbackNegative() {
		return this.memoryNegative;
	}//getMemoryFeedbackNegative

	/**
	 * Antaa laadullisen palautteen muistipaikoista ja muuttujista.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getMemoryFeedbackQuality() {
		return this.memoryQuality;
	}//getMemoryFeedbackQuality

	/**
	 * Antaa positiivisen palautteen muistiviitteiden m��r�st�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getMemoryReferencesFeedbackPositive() {
		return this.memoryRefPositive;
	}//getMemoryReferencesFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen muistiviitteiden m��r�st�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getMemoryReferencesFeedbackNegative() {
		return this.memoryRefNegative;
	}//getMemoryReferencesFeedbackNegative

		/**
	 * Antaa laadullisen palautteen muistiviitteiden m��r�st�.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getMemoryReferencesFeedbackQuality() {
		return this.memoryRefQuality;
	}//getMemoryReferencesFeedbackQuality
	
	/**
	 * Antaa positiivisen palautteen n�yt�n tulosteista.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getScreenOutputFeedbackPositive() {
		return this.screenOutPositive;
	}//getScreenOutputFeedbackPositive
	
	/**
	 * Antaa negatiivisen palautteen n�yt�n tulosteista.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getScreenOutputFeedbackNegative() {
		return this.screenOutNegative;
	}//getScreenOutputFeedbackNegative

	/**
	 * Antaa laadullisen palautteen n�yt�n tulosteista.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getScreenOutputFeedbackQuality() {
		return this.screenOutQuality;
	}//getScreenOutputFeedbackQuality

	/**
	 * Antaa positiivisen palautteen tiedoston tulosteista.
	 *
	 * @return kriteeriin liittyv� palaute
	 */
	public String getFileOutputFeedbackPositive() {
		return this.fileOutPositive;
	}//getFileOutputFeedbackPositive

	/**
	 * Antaa negatiivisen palautteen tiedoston tulosteista.
	 *
	 * @return kriteeriin liittyv� palaute
	 */ 
	public String getFileOutputFeedbackNegative() {
		return this.fileOutNegative;
	}//getFileOutputFeedbackNegative
	
	/**
	 * Antaa laadullisen palautteen tiedoston tulosteista.
	 *
	 * @return kriteeriin liittyv� palaute
	 */ 
	public String getFileOutputFeedbackQuality() {
		return this.fileOutQuality;
	}//getFileOutputFeedbackQuality

}//class
