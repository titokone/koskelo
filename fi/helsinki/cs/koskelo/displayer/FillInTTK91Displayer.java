package fi.helsinki.cs.koskelo.displayer;

import fi.hy.eassari.showtask.trainer.CacheException;

/**
 * Luokka, joka huolehtii staattisten tehtävien
 * html-koodin tuottamisesta.
 * @author Tom Bertell, Koskelo-projekti
 */

public class FillInTTK91Displayer extends CommonDisplayer{

	/**
	 * Konstruktori, kutsuu yläluokan CommonDisplayerin
	 * konstruktoria.
	 */

	public FillInTTK91Displayer(){
		super();
	}

	final int ROWS = 12;    // vastauslaatikon korkeus 
	final int COLS = 50;    // vastauslaatikon leveys
	final String TARGETSERVLET = "Answer2.do2"; // käynnistettävä servletti
    
	/**
	 * Tekee tehtävän tiedoista html-koodia ja palauttaa 
	 * html-koodin String-oliona. Kutsutaan DisplayerServlet2- ja
	 * Answer2-servleteistä.
	 *
	 * @param  initval tehtävän vastaus
	 * @param  params  generoidut parametrit
	 * @param  hiddens lomakkeen piilokentät String-oliona.
	 * @param  allowTry kertoo tuleeko sivun loppuun vastauspainike  
	 * @return tehtävän esitys html-muodossa
	 * @throws CacheException jos tulee ongelmia attribuuttien 
	 *                        hakemisessa AttributeCachesta
	 */

	public String getSetting(String [] initVal, 
													 String params, 
													 String hiddens,  
													 boolean allowTry) throws CacheException{

		String taskDescription; // Tehtävänanto
		String input;           // syöte
		String inputHeader;     // syötteen otsikko
		String exampleCode;     // malliratkaisu
		StringBuffer setting = new StringBuffer();  // kerätään html-sivu
	  
		// Lisätään piilokenttiin tehtävän tyyppi.
		
		hiddens += ("<input type=\"hidden\" name=\"tasktype\"" 
								+"value=\"fillinttk91task\">");
		/*
		 * Haetaan tehtävänanto, syötteet, syötteen otsikko ja malliratkaisu.
		 */

		taskDescription = 
			cache.getAttribute("T", taskid, "taskDescription", language);
		if (taskDescription == null || taskDescription.equals("null"))
			taskDescription = "";
		input = 
			cache.getAttribute("T", taskid, "publicInput", language);
		if (input == null || input.equals("null"))
			input = "";
		inputHeader = 
			cache.getAttribute("D", "staticttk91taskdisplayer", 
												 "inputHeader", language);
		exampleCode =
			cache.getAttribute("T", taskid, "exampleCode", language);
		if (exampleCode == null || exampleCode.equals("null"))
			exampleCode = "";
	
		/*
		 * Lomake alkaa
		 */

		setting.append("<form action=" + TARGETSERVLET + 
									 " method=\"post\" name=\"fillinttk91task\"" +
									 "id=\"fillinttk91task\">");
	
    /*   
		 * Lisätään lomakkeeseen tehtävänanto ja syötteet
		 */

		setting.append(TTK91DisplayerUtils.getHTMLElementTask(taskDescription));
		setting.append(TTK91DisplayerUtils.getHTMLElementInput(inputHeader, input));

		/* 
		 * Lisätään ennen vastauslaatikkoa malliratkaisun osa, joka on 
		 * ennen aukkoa ja vastauslaatikon jälkeen osa, joka on aukon jälkeen. 
		 * Aukon paikalle laitetaan vastauslaatikko. Jos parametri initVal on null,
		 * luodaan tyhjä vastauslaatikko, muutoin opiskelijan vastaus 
		 * laitetaan laatikkoon.
		 */

		setting.append(getHTMLElementBeforeEmpty(exampleCode));

		if (initVal == null) {
			setting.append(TTK91DisplayerUtils.
										 getHTMLElementAnswerBox(ROWS/2, COLS));
		} else {
			setting.append(TTK91DisplayerUtils.
										 getHTMLElementAnswerBox(initVal, ROWS/2, COLS));
		}
		
		setting.append(getHTMLElementAfterEmpty(exampleCode));
		
		/*
		 * Lisätään analyserin tarvitsemat piilokentät lomakkeeseen
		 */

		setting.append(hiddens);
		
		/*
		 * Jos tehtävän lähetys sallitaan, luodaan lähetä-nappi
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
	 * @return elementti html-muodossa
	 */  

	private String getHTMLElementBeforeEmpty(String exampleCode){
		
		String beginSeparator = "[";
		StringBuffer result = new StringBuffer();
		String temp = "";

		int index = exampleCode.indexOf(beginSeparator);
		temp = exampleCode.substring(0,index);
		
		if(!temp.equals("")){
		result.append("<textarea cols =\"" +this.COLS +"\""
									 +"rows =\"" +this.ROWS +"\" readonly>");
		result.append(temp);
		result.append("</textarea><br>");
		}

		return new String(result);
	}//getHTMLElementBeforeEmpty
	
	/**
	 * Luo sen osan mallikoodista, joka tulee aukkon jälkeen.
	 *
	 * @param exampleCode mallivastaus
	 * @return elementti html-muodossa
	 */  

	private String getHTMLElementAfterEmpty(String exampleCode){

		String endSeparator = "]";
		StringBuffer result = new StringBuffer();
		String temp = "";
		
		int index = exampleCode.indexOf(endSeparator);
		temp = exampleCode.substring(index+1);

		if (!temp.equals("")) { //  Testataan tuleeko aukon jälkeen koodia.
			result.append("<textarea cols =\"" +this.COLS +"\""
										 +"rows =\"" +this.ROWS +"\" readonly>");
			result.append(temp);
			result.append("</textarea><br>");
		}

				return new String(result);
				}//getHTMLElementAfterEmpty

	}//class
