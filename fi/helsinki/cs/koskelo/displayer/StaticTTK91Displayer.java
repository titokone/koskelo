/**
 * Luokka, joka huolehtii staattisten teht�vien
 * html-koodin tuottamisesta.
 */

package fi.helsinki.cs.koskelo.displayer;

import fi.hy.eassari.showtask.trainer.CacheException;


public class StaticTTK91Displayer extends CommonDisplayer{
    
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
    * @throws CacheException Jos tulee ongelmia attribuuttien 
    *                        hakemisessa AttributeCachesta
    */
		public String getSetting(String [] initVal, 
														 String params, 
														 String hiddens,  
														 boolean allowTry) throws CacheException{
       
				/**
				 * Servletin nimi, joka k�ynnistet��nn lomakkeen
				 * t�ytt�misen j�lkeen.
				 */
				String targetServlet = "Answer2.do2";
       
				// StrinBufferiin ker�t��n palautettava html-sivu
	
				StringBuffer setting = new StringBuffer();
	   
				hiddens += ("<input type=\"hidden\" name=\"tasktype\"" 
										+"value=\"staticttk91task\">");

				// Haetaan teht�v�nanto, sy�tteet ja kieliriippuvainen sy�teteksti.
	
				String taskDescription = 
						cache.getAttribute("T", taskid, "taskDescription", language);
				String input = 
						cache.getAttribute("T", taskid, "publicInput", language);

				//FIXME: Inputtext pit�isi oikeasti hakea tietokannasta
				String inputText ="Sy�tteet"; 

				//String inputText = 
				//cache.getAttribute("D", "staticttk91displayer", 
				//"input_text", language);
	
				// Lomake alkaa
	
				setting.append("<form action=" + targetServlet + 
											 " method=\"post\" name=\"staticttk91task\"" +
                       "id=\"staticttk91task\">");
	
       
				// Lis�t��n teht�v�nanto ja sy�tteet

				setting.append(getHTMLElementTask(taskDescription));
				setting.append(getHTMLElementInput(inputText, input));

				/** 
				 * Lis�t��n vastauslaatikko. Jos parametri initVal on null,
				 * n�ytet��n opiskelijalle tyhj� teht�v�laatikko,
				 * muutoin opiskelijan vastaus laitetaan laatikkoon.
				 */

				if (initVal == null) {
						setting.append(getHTMLElementAnswerBox());
				} else {
						setting.append(getHTMLElementAnswerBox(initVal));
				}

				// Lis�t��nn analyserin tarvitsemat piilokent�t lomakkeeseen
	
				setting.append(hiddens);
	
				// Jos teht�v�n l�hetys sallitaan, luodaan l�het�-nappi
	
				if (allowTry) {
						setting.append(super.getButton());
				}
       

				// Lomake loppuu

				setting.append("</form>");
				return new String(setting);

		}//getSetting
    
		/**
		 * Palauttaa teht�v�nannon html-muodossa.
		 *
		 * @param  task teht�v�nanto 
		 * @return teht�v�nanto html-muodossa
		 */

    private String getHTMLElementTask(String task){
	
				return new String("<p class=\"assignment\"><strong>" 
													+task +"</strong></p>");
    }//getHTMLElementTask

		/**
		 * Palauttaa sy�tteet html-muodossa.
		 *
		 * @param inputText kieliriippuvainen sy�teteksti esim. suomeksi sy�tteet 
		 *        -> englanniksi input jne.
		 * @param input varsinaiset sy�tteet                     
		 * @return sy�tteet html-muodossa
		 */

    private String getHTMLElementInput(String inputText, String input){
	
				return new String("<p class=\"input\"><strong>" 
													+inputText +": " +input +"</strong></p>");
    }//getHTMLELementInput
    
		/**
		 * Palauttaa tyhj�n vastauslaatikon html-muodossa.
		 *
		 * @return vastauslaatikko
		 */

    private String getHTMLElementAnswerBox(){
	
				return new String("<textarea name=\"textfield\" cols=\"50\""+ 
													"rows=\"20\"></textarea>\"");
    }//getHTMLElementAnswerBox
      
		/**
		 * Palauttaa vastauslaatikon, joka sis�lt�� vastauksen
		 *
		 * @param  answer vastaus 
		 * @return täytetyn vastauslaatikon html-muodossa
		 */
 
    private String getHTMLElementAnswerBox(String[] answer){
	
				StringBuffer answerbox = new StringBuffer();

				answerbox.append("<textarea name=\"textfield\" cols=\"50\""+ 
												 "rows=\"20\">");

				for(int i = 0; i < answer.length; i++){
						answerbox.append(answer[i] +"\n");
				}
	
				answerbox.append("</textarea>");
				return new String(answerbox);
		}//getHTMLElementAnswerBox
}//class
