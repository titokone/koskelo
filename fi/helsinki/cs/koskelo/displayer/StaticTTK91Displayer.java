/**
 * Luokka, joka huolehtii staattisten teht�vien
 * html-koodin tuottamisesta.
 * @author Tom Bertell
 */

package fi.helsinki.cs.koskelo.displayer;

import fi.hy.eassari.showtask.trainer.CacheException;

public class StaticTTK91Displayer extends CommonDisplayer {

	/**
	 * Konstruktori, joka kutsuu yl�luokan CommonAnalyser
	 * konstruktoria.
	 */

	public StaticTTK91Displayer() {
		super();
	}//StaticTTK91Displayer 
    
	final int COLS = 50;    // vastauslaatikon leveys
	final int ROWS = 20;    // vastauslaatiokon korkeus
	final String TARGETSERVLET = "Answer2.do2"; // kohdeservletin nimi 

	/**
	 * Tekee teht�v�n tiedoista html-koodia ja palauttaa 
	 * html-koodin String-oliona. Kutsutaan DisplayerServlet2- ja
	 * Answer2-servleteist�.
	 *
	 * @param  initval teht�v�n vastaus
	 * @param  params  generoidut parametrit
	 * @param  hiddens lomakkeen piilokent�t String-oliona.
	 * @param  allowTry kertoo tuleeko sivun loppuun vastauspainike  
	 * @return teht�v�n esitys html-muodossa
	 * @throws CacheException jos tulee ongelmia attribuuttien 
	 *                        hakemisessa AttributeCachesta
	 */

	public String getSetting(String [] initVal, 
													 String params, 
													 String hiddens,  
													 boolean allowTry) throws CacheException {

		String taskDescription; // teht�v�nanto
		String input;           // sy�tteet
		String inputHeader;     // sy�tteen otsikko
    StringBuffer setting = new StringBuffer(); // ker�t��n palautettava sivu

		/*
		 * Lis�t��n piilokentt� teht�v�ntyypille
		 */

		hiddens += ("<input type=\"hidden\" name=\"tasktype\"" 
								+"value=\"staticttk91task\">");

		/*
		 * Haetaan teht�v�nanto, sy�tteet ja sy�tteen otsikko.
		 */

		taskDescription = 
			cache.getAttribute("T", taskid, "taskDescription", language);
		input = 
			cache.getAttribute("T", taskid, "publicInput", language);
		inputHeader = 
			cache.getAttribute("D", "staticttk91taskdisplayer", 
												 "inputHeader", language);
		
		/*
		 * Lomake alkaa
		 */

		setting.append("<form action=" + TARGETSERVLET + 
									 " method=\"post\" name=\"staticttk91task\"" +
									 "id=\"staticttk91task\">");
	
    /*   
		 * Lis�t��n teht�v�nanto ja sy�tteet
		 */

		setting.append(TTK91DisplayerUtils.getHTMLElementTask(taskDescription));
		setting.append(TTK91DisplayerUtils.getHTMLElementInput(inputHeader, 
																													 input));

		/* 
		 * Lis�t��n vastauslaatikko. Jos parametri initVal on null,
		 * luodann tyhj� teht�v�laatikko, muutoin opiskelijan vastaus 
		 * laitetaan laatikkoon.
		 */

		if (initVal == null) {
			setting.append(TTK91DisplayerUtils.getHTMLElementAnswerBox(ROWS, COLS));
		} else {
			setting.append(TTK91DisplayerUtils.getHTMLElementAnswerBox(initVal,
																																 ROWS, COLS));
		}
		/*
		 * Lis�t��n analyserin tarvitsemat piilokent�t lomakkeeseen
		 */

		setting.append(hiddens);
		
		/*
		 * Jos teht�v�n l�hetys sallitaan, luodaan l�het�-nappi
		 */

		if (allowTry) {
			setting.append(super.getButton());
		}
       
		/*
		 * Lomake loppuu
		 */

		setting.append("</form>");
		return new String(setting);

	}//getSetting

}//class
