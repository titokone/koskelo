/*
 * Created on 20.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.showtask.trainer;

import java.sql.SQLException;

/**
 * A Test cache, used instead of database with unit test cases)
 * @author Mikko Lukkari
 */
public class AnalyserTestCache implements AttributeCache {

	String dbDriver = "oracle.jdbc.OracleDriver";
	String dbServer = "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
	String dbLogin = "assari";
	String dbPassword = "opetus";

	public String getAttribute(
		String objType,
		String objID,
		String attributename,
		String language)
		throws CacheException {

		// Option task:
		if (objID.equals("TEST01")) {
			if (language.equals("EN")) {
				if (attributename.equals("task"))
					return "Which one is edible?";
				if (attributename.equals("helpline"))
					return "Select correct choises for the alternatives below.";
				if (attributename.equals("option1"))
					return "BANANA";
				if (attributename.equals("option2"))
					return "ROCK";
				if (attributename.equals("isselected1"))
					return "Y";
				if (attributename.equals("isselected2"))
					return "N";
				if (attributename.equals("defaultvalue1"))
					return "Y";
				if (attributename.equals("defaultvalue2"))
					return "N";
				if (attributename.equals("submitbutton"))
					return "Submit";
				//return "Cache attribute here";
			}
			if (language.equals("FI")) {
				if (attributename.equals("task"))
					return "Kumpi on syötävä?";
				if (attributename.equals("helpline"))
					return "Valitse oikeat vaihtoehdot alla olevista vaihtoehdoista.";
				if (attributename.equals("option1"))
					return "BANAANI";
				if (attributename.equals("option2"))
					return "KIVI";
				if (attributename.equals("isselected1"))
					return "Y";
				if (attributename.equals("isselected2"))
					return "N";
				if (attributename.equals("defaultvalue1"))
					return "N";
				if (attributename.equals("defaultvalue2"))
					return "Y";
				if (attributename.equals("submitbutton"))
					return "Vastaa";
				//return "Cache attribuutti tähän";
			}
			return "TEST01: Unknown language: " + language;
		} else
			// Multiple choice task:
			if (objID.equals("TEST02")) {
				if (language.equals("EN")) {
					if (attributename.equals("task"))
						return "Which are edible?";
					if (attributename.equals("helpline"))
						return "Select correct choises for the alternatives below.";
					if (attributename.equals("option1"))
						return "BANANA";
					if (attributename.equals("option2"))
						return "ROCK";
					if (attributename.equals("option3"))
						return "ANANAS";
					if (attributename.equals("option4"))
						return "RADIO";
					if (attributename.equals("isselected1"))
						return "Y";
					if (attributename.equals("isselected2"))
						return "N";
					if (attributename.equals("isselected3"))
						return "Y";
					if (attributename.equals("isselected4"))
						return "N";
					if (attributename.equals("defaultvalue1"))
						return "Y";
					if (attributename.equals("defaultvalue2"))
						return "N";
					if (attributename.equals("defaultvalue3"))
						return "N";
					if (attributename.equals("defaultvalue4"))
						return "Y";
					if (attributename.equals("submitbutton"))
						return "Submit";
					//return "Cache attribute here";
				}
				if (language.equals("FI")) {
					if (attributename.equals("task"))
						return "Mitkä ovat syötäviä?";
					if (attributename.equals("helpline"))
						return "Valitse oikeat vaihtoehdot alla olevista vaihtoehdoista.";
					if (attributename.equals("option1"))
						return "BANAANI";
					if (attributename.equals("option2"))
						return "KIVI";
					if (attributename.equals("option3"))
						return "ANANAS";
					if (attributename.equals("option4"))
						return "RADIO";
					if (attributename.equals("isselected1"))
						return "Y";
					if (attributename.equals("isselected2"))
						return "N";
					if (attributename.equals("isselected3"))
						return "Y";
					if (attributename.equals("isselected4"))
						return "N";
					if (attributename.equals("defaultvalue1"))
						return "N";
					if (attributename.equals("defaultvalue2"))
						return "Y";
					if (attributename.equals("defaultvalue3"))
						return "Y";
					if (attributename.equals("defaultvalue4"))
						return "N";
					if (attributename.equals("submitbutton"))
						return "Vastaa";
					if (attributename.equals("negativefeedback1"))
						return "Etkö ole koskaan syönyt banaania?";
					if (attributename.equals("negativefeedback2"))
						return "Oletko joskus syönyt kiviä?";
					if (attributename.equals("negativefeedback3"))
						return "Etkö ole koskaan syönyt ananasta?";
					if (attributename.equals("negativefeedback4"))
						return "Oletko joskus syönyt radion?";
					if (attributename.equals("positivefeedback1"))
						return "Oikeassa olet!";
					if (attributename.equals("positivefeedback2"))
						return "Oikeassa olet!";
					if (attributename.equals("positivefeedback3"))
						return "Oikeassa olet!";
					if (attributename.equals("positivefeedback4"))
						return "Oikeassa olet!";
					//return "Cache attribuutti tähän";
				}
				return "TEST02: Unknown language: " + language;
			} else
				// Blankfill task:
				if (objID.equals("TEST03")) {
					if (language.equals("EN")) {
						if (attributename.equals("text"))
							return "T[[h]]is t[[e]]x[[t]] [[has]] lots [[]] of [[bl]]nks.";
						if (attributename.equals("helpline"))
							return "Helpline here";
						if (attributename.equals("task"))
							return "Task here";
						if (attributename.equals("submitbutton"))
							return "Submit";
						//return "Cache attribute here";
					}
					if (language.equals("FI")) {
						if (attributename.equals("text"))
							return "Täs[[s]]ä teks[[t]]is[[s]]ä [[on]] aukkoja.";
						if (attributename.equals("helpline"))
							return "Helppi tähän";
						if (attributename.equals("task"))
							return "Tehtävänanto tähän";
						if (attributename.equals("submitbutton"))
							return "Vastaa";
						//return "Cache attribuutti tähän";
					}
					return "TEST03: Unknown language: " + language;
				}

		// Ordering task:
		if (objID.equals("TEST04")) {
			if (language.equals("EN")) {
				if (attributename.equals("task"))
					return "Put the the objects into the right order.";
				if (attributename.equals("helpline"))
					return "Put the the objects into the right order.";
				if (attributename.equals("object1"))
					return "later";
				if (attributename.equals("object2"))
					return "see";
				if (attributename.equals("object3"))
					return "you";
				if (attributename.equals("submitbutton"))
					return "Submit";
				//return "Cache attribute here";
			}
			if (language.equals("FI")) {
				if (attributename.equals("task"))
					return "Mitkä ovat syötäviä?";
				if (attributename.equals("helpline"))
					return "Valitse oikeat vaihtoehdot alla olevista vaihtoehdoista.";
				if (attributename.equals("object1"))
					return "myöhemmin";
				if (attributename.equals("object2"))
					return "nähdään";
				if (attributename.equals("submitbutton"))
					return "Vastaa";
				//return "Cache attribuutti tähän";
			}
			return "TEST04: Unknown language: " + language;
		}
		if (objID.equals("TEST05")) {
			if (language.equals("EN")) {
				if (attributename.equals("task"))
					return "Fill in the blanks.";
				if (attributename.equals("helpline"))
					return "Fill in the blanks with missing words.";
				if (attributename.equals("gap1"))
					return "eka";
				if (attributename.equals("gap2"))
					return "toka";
				if (attributename.equals("gap3"))
					return "kolmas";
				if (attributename.equals("gap4"))
					return "neljäs";
				if (attributename.equals("submitbutton"))
					return "Submit";
				//return "Cache attribute here";
			}
			if (language.equals("FI")) {
				if (attributename.equals("task"))
					return "Täytä aukot.";
				if (attributename.equals("helpline"))
					return "Täytä aukot puuttuvilla sanoilla.";
				if (attributename.equals("gap1"))
					return "eka";
				if (attributename.equals("gap2"))
					return "toka";
				if (attributename.equals("gap3"))
					return "kolmas";
				if (attributename.equals("gap4"))
					return "neljäs";
				if (attributename.equals("submitbutton"))
					return "Vastaa";
				//return "Cache attribuutti tähän";
			}
			return "TEST05: Unknown language: " + language;
		}

		if (objID.equals("TEST06")) {
			if (language.equals("EN")) {
				if (attributename.equals("task"))
					return "Put the objects into the right order.";
				if (attributename.equals("helpline"))
					return "Put the objects into the right order.";
				if (attributename.equals("placeofobject1"))
					return "1";
				if (attributename.equals("placeofobject2"))
					return "2";
				if (attributename.equals("placeofobject3"))
					return "3";
				if (attributename.equals("placeofobject4"))
					return "4";
				if (attributename.equals("placeofobject5"))
					return "5";
				if (attributename.equals("submitbutton"))
					return "Submit";
				//return "Cache attribute here";
			}
			if (language.equals("FI")) {
				if (attributename.equals("task"))
					return "Järjestä objektit.";
				if (attributename.equals("helpline"))
					return "Laita objektit oikeaan järjestykseen.";
				if (attributename.equals("placeofobject1"))
					return "1";
				if (attributename.equals("placeofobject2"))
					return "2";
				if (attributename.equals("placeofobject3"))
					return "3";
				if (attributename.equals("placeofobject4"))
					return "4";
				if (attributename.equals("placeofobject5"))
					return "5";
				if (attributename.equals("submitbutton"))
					return "Vastaa";
				//return "Cache attribuutti tähän";
			}
			return "TEST06: Unknown language: " + language;
		}

		if (objID.equals("TEST07")) {
			if (language.equals("EN")) {
				if (attributename.equals("task"))
					return "Which are edible?";
				if (attributename.equals("helpline"))
					return "Choose right options.";
				if (attributename.equals("option1"))
					return "bread";
				if (attributename.equals("option2"))
					return "cheese";
				if (attributename.equals("option3"))
					return "candy";
				if (attributename.equals("submitbutton"))
					return "Submit";
				if (attributename.equals("isselected1"))
					return "Y";
				if (attributename.equals("isselected2"))
					return "Y";
				if (attributename.equals("isselected3"))
					return "Y";
				if (attributename.equals("defaultvalue1"))
					return "N";
				if (attributename.equals("defaultvalue2"))
					return "N";
				if (attributename.equals("defaultvalue3"))
					return "N";
				//return "Cache attribute here";
			}
			if (language.equals("FI")) {
				if (attributename.equals("task"))
					return "Mitkä ovat syötäviä?";
				if (attributename.equals("helpline"))
					return "Valitse oikeat vaihtoehdot alla olevista vaihtoehdoista.";
				if (attributename.equals("option1"))
					return "leipä";
				if (attributename.equals("option2"))
					return "juusto";
				if (attributename.equals("option3"))
					return "karkki";
				if (attributename.equals("isselected1"))
					return "Y";
				if (attributename.equals("isselected2"))
					return "Y";
				if (attributename.equals("isselected3"))
					return "Y";
				if (attributename.equals("defaultvalue1"))
					return "N";
				if (attributename.equals("defaultvalue2"))
					return "N";
				if (attributename.equals("defaultvalue3"))
					return "N";
				if (attributename.equals("submitbutton"))
					return "Vastaa";
				//return "Cache attribuutti tähän";
			}
			return "TEST07: Unknown language: " + language;
		}

		if (objID.equals("TEST08")) {
			if (language.equals("EN")) {
				if (attributename.equals("task"))
					return "Which are edible?";
				if (attributename.equals("helpline"))
					return "Choose right options.";
				if (attributename.equals("option1"))
					return "radio";
				if (attributename.equals("option2"))
					return "tv";
				if (attributename.equals("option3"))
					return "rock";
				if (attributename.equals("submitbutton"))
					return "Submit";
				if (attributename.equals("isselected1"))
					return "N";
				if (attributename.equals("isselected2"))
					return "N";
				if (attributename.equals("isselected3"))
					return "N";
				if (attributename.equals("defaultvalue1"))
					return "Y";
				if (attributename.equals("defaultvalue2"))
					return "Y";
				if (attributename.equals("defaultvalue3"))
					return "Y";
				//return "Cache attribute here";
			}
			if (language.equals("FI")) {
				if (attributename.equals("task"))
					return "Mitkä ovat syötäviä?";
				if (attributename.equals("helpline"))
					return "Valitse oikeat vaihtoehdot alla olevista vaihtoehdoista.";
				if (attributename.equals("option1"))
					return "radio";
				if (attributename.equals("option2"))
					return "tv";
				if (attributename.equals("option3"))
					return "kivi";
				if (attributename.equals("submitbutton"))
					return "Vastaa";
				if (attributename.equals("isselected1"))
					return "N";
				if (attributename.equals("isselected2"))
					return "N";
				if (attributename.equals("isselected3"))
					return "N";
				if (attributename.equals("defaultvalue1"))
					return "Y";
				if (attributename.equals("defaultvalue2"))
					return "Y";
				if (attributename.equals("defaultvalue3"))
					return "Y";
				//return "Cache attribuutti tähän";
			}
			return "TEST08: Unknown language: " + language;
		}

		if (objID.equals("TEST09")) {
			if (language.equals("EN")) {
				if (attributename.equals("task"))
					return "Fill in the blanks.";
				if (attributename.equals("helpline"))
					return "Fill in the blanks with right words.";
				if (attributename.equals("gap1"))
					return "eka";
				if (attributename.equals("gap2"))
					return "toka";
				if (attributename.equals("gap3"))
					return "kolmas";
				if (attributename.equals("submitbutton"))
					return "Submit";
				//return "Cache attribute here";
			}
			if (language.equals("FI")) {
				if (attributename.equals("task"))
					return "Täytä aukot.";
				if (attributename.equals("helpline"))
					return "Täytä aukot puuttuvilla sanoilla.";
				if (attributename.equals("gap1"))
					return "eka";
				if (attributename.equals("gap2"))
					return "toka";
				if (attributename.equals("gap3"))
					return "kolmas";
				if (attributename.equals("submitbutton"))
					return "Vastaa";
				//return "Cache attribuutti tähän";
			}
			return "TEST09: Unknown language: " + language;
		}

		if (objID.equals("CommonDisplayer")) {
			if (language.equals("EN")) {
				if (attributename.equals("submitbutton")) {
					return "Submit";
				} else
					return "";
			}
			if (language.equals("FI")) {
				if (attributename.equals("submitbutton")) {
					return "Vastaa";
				} else
					return "";
			} else
				return "Unknown language: " + language;
		} else
			return "Unknown task: " + objID;
	}

	public int attribCount(
		String objectid,
		String attributename,
		String language)
		throws CacheException, SQLException {

		if (objectid.equals("TEST01")) {
			if (language.equals("EN"))
				return 2;
			if (language.equals("FI"))
				return 2;
			else
				return 0;
		} else if (objectid.equals("TEST02")) {
			if (language.equals("EN"))
				return 4;
			if (language.equals("FI"))
				return 4;
			else
				return 0;
		} else if (objectid.equals("TEST04")) {
			if (language.equals("EN"))
				return 3;
			if (language.equals("FI"))
				return 2;
			else
				return 0;
		} else if (objectid.equals("TEST05")) {
			if (language.equals("EN"))
				return 4;
			if (language.equals("FI"))
				return 4;
			else
				return 0;
		} else if (objectid.equals("TEST06")) {
			if (language.equals("EN"))
				return 5;
			if (language.equals("FI"))
				return 5;
			else
				return 0;
		} else if (objectid.equals("TEST07")) {
			if (language.equals("EN"))
				return 3;
			if (language.equals("FI"))
				return 3;
			else
				return 0;
		} else if (objectid.equals("TEST08")) {
			if (language.equals("EN"))
				return 3;
			if (language.equals("FI"))
				return 3;
			else
				return 0;
		} else if (objectid.equals("TEST09")) {
			if (language.equals("EN"))
				return 3;
			if (language.equals("FI"))
				return 3;
			else
				return 0;
		} else
			return 0;
	}

	public void saveAnswer(
		String userid,
		String courseid,
		String moduleid,
		int seqno,
		int trynumber,
		int correctness,
		String whenanswered,
		String answer,
		String language)
		throws CacheException {
	}

	public String taskType(String taskid) throws CacheException {

		if (taskid.equals("TEST01"))
			return "multiplechoicetask";
		if (taskid.equals("TEST02"))
			return "optiontask";
		if (taskid.equals("TEST03"))
			return "blankfilltask";
		if (taskid.equals("TEST04"))
			return "orderingtask";
		return null;
	}
	
	public String getTaskID (String courseid, String moduleid, int seqno) throws CacheException {
	
		return null;									
	}
	
	public boolean languageDefined (String taskid, String language) throws CacheException {
		
		return true;
	}
}
