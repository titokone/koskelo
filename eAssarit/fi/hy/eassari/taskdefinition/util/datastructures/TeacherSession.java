package fi.hy.eassari.taskdefinition.util.datastructures;

/**
 * A session class containing the teacher's use session data.
 * @author Vesa-Matti Mäkinen
 */
public class TeacherSession {

	private String userId;
	private String selectedLanguageId;
	
	/**
	 * Creates a new TeacherSession object containing the user id and chosen language's id.
	 * @param userId the user id of the current user.
	 * @param selectedLanguageId the use language chosen by the user.
	 */
	public TeacherSession(String userId, String selectedLanguageId) {
		this.userId = userId;
		this.selectedLanguageId = selectedLanguageId;
	}
	
	/**
	 * @return the id of the selected use language.
	 */
	public String getSelectedLanguageId() {
		return selectedLanguageId;
	}
	
	/**
	 * @return the user id of the user.
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * @param string the id of the language to be set.
	 */
	public void setSelectedLanguageId(String string) {
		selectedLanguageId = string;
	}

	/**
	 * @param string the user id to be set.
	 */
	public void setUserId(String string) {
		userId = string;
	}

}
