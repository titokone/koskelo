package fi.hy.eassari.taskdefinition.util.datastructures;

/**
 * A Data Transfer Object for language information.
 * @author Vesa-Matti Mäkinen
 */
public class LanguageDTO {
	
	private String languageId;
	private String languageName;
	
	/**
	 * Creates a new LanguageDTO containing the given language.
	 * @param languageId the String id of the language eg. "EN" 
	 * @param languageName the name of the language eg. "english"
	 */
	public LanguageDTO(String languageId, String languageName) {
		this.languageId = languageId;
		this.languageName = languageName;
	}
	/**
	 * @return the id of this language.
	 */
	public String getLanguageId() {
		return languageId;
	}

	/**
	 * @return the name of this language.
	 */
	public String getLanguageName() {
		return languageName;
	}

	/**
	 * @param string the id to be set for this language - eg. "EN"
	 */
	public void setLanguageId(String string) {
		languageId = string;
	}

	/**
	 * @param string the name to be set for this language - eg. "english"
	 */
	public void setLanguageName(String string) {
		languageName = string;
	}
	/**
	 * Compares to language objects according to their id's and returns true if they are the same.
	 * @return true if the languages compared have the same id.
	 */
	public boolean equals(Object obj) {
		LanguageDTO otherLanguage = (LanguageDTO)obj;
		return this.getLanguageId().equals(otherLanguage.getLanguageId()); 
	}
}
