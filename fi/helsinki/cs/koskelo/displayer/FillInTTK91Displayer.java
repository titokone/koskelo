package fi.helsinki.cs.koskelo.displayer;

import fi.hy.eassari.showtask.trainer.CacheException;

/**
 * Luokka, joka huolehtii staattisten teht�vien
 * html-koodin tuottamisesta.
 * @author Tom Bertell
 */

public class FillInTTK91Displayer extends CommonDisplayer{
    
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
													 boolean allowTry) throws CacheException{

		String taskDescription; // Teht�v�nanto
		String input;           // sy�te
		String inputHeader;     // sy�tteen otsikko
		String exampleCode;     // malliratkaisu
		final int ROWS = 12;    // vastauslaatikon korkeus 
		final int COLS = 50;    // vastauslaatikon leveys
		final String TARGETSERVLET = "Answer2.do2"; // k�ynnistett�v� servletti
		StringBuffer setting = new StringBuffer();  // ker�t��n html-sivu
	   
		hiddens += ("<input type=\"hidden\" name=\"tasktype\"" 
								+"value=\"fillinttk91task\">");
		/*
		 * Haetaan teht�v�nanto, sy�tteet, sy�tteen otsikko ja malliratkaisu.
		 */
		taskDescription = 
			cache.getAttribute("T", taskid, "taskDescription", language);
		input = 
			cache.getAttribute("T", taskid, "publicInput", language);
		inputHeader = 
			cache.getAttribute("D", "fillinttk91taskdisplayer", 
												 "inputHeader", language);
		exampleCode =
			cache.getAttribute("T", taskid, "exampleCode", language);
	
		/*
		 * Lomake alkaa
		 */
		setting.append("<form action=" + TARGETSERVLET + 
									 " method=\"post\" name=\"fillinttk91task\"" +
									 "id=\"fillinttk91task\">");
	
    /*   
		 * Lis�t��n lomakkeeseen teht�v�nanto ja sy�tteet
		 */
		setting.append(TTK91DisplayerUtils.getHTMLElementTask(taskDescription));
		setting.append(TTK91DisplayerUtils.getHTMLElementInput(inputHeader, input));

		/* 
		 * Lis�t��n ennen vastauslaatikkoa malliratkaisun osa, joka on 
		 * ennen aukkoa ja vastauslaatikon j�lkeen osa, joka on aukon j�lkeen. 
		 * Aukon paikalle laitetaan vastauslaatikko. Jos parametri initVal on null,
		 * luodaan tyhj� vastauslaatikko, muutoin opiskelijan vastaus 
		 * laitetaan laatikkoon.
		 */

		setting.append(getHTMLElementBeforeEmpty(exampleCode, COLS));

		if (initVal == null) {
			setting.append(TTK91DisplayerUtils.getHTMLElementAnswerBox(ROWS, COLS));
		} else {
			setting.append(TTK91DisplayerUtils.getHTMLElementAnswerBox(initVal, 
																																 ROWS, COLS));
		}
		
		setting.append(getHTMLElementAfterEmpty(exampleCode, COLS));
		
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
    
	/**
	 * Luo sen osan mallikoodista, joka tulee ennen aukkoa.
	 *
	 * @param exampleCode mallivastaus
	 * @param cols tekstikent�n leveys
	 * @return elementti html-muodossa
	 */  
	private String getHTMLElementBeforeEmpty(String exampleCode, int cols){
		
		String beginSeparator = "[[";
		StringBuffer result = new StringBuffer();

		result.append("<textarea cols =\"" +cols +"\" readonly>");
		int index = exampleCode.indexOf(beginSeparator);
		result.append(exampleCode.substring(0,index));
		result.append("</textarea><br>");

		return new String(result);
	}//getHTMLElementBeforeEmpty
	
	/**
	 * Luo sen osan mallikoodista, joka tulee aukkon j�lkeen.
	 *
	 * @param exampleCode mallivastaus
	 * @param cols tekstikent�n leveys
	 * @return elementti html-muodossa
	 */  
	private String getHTMLElementAfterEmpty(String exampleCode, int cols){

		String endSeparator = "]]";
		StringBuffer result = new StringBuffer();

		result.append("<textarea cols =\"" +cols +"\" readonly>");
		int index = exampleCode.indexOf(endSeparator);
		result.append(exampleCode.substring(index+2));
		result.append("</textarea><br>");

		return new String(result);
	}//getHTMLElementAfterEmpty

}//class
