import javax.servlet.*;
import java.util.Vector;
import java.util.StringTokenizer;
import fi.helsinki.cs.koskelo.common.*;

public class TTK91SyntaxChecker extends javax.servlet.HttpServlet{

	/** Kutsuu doPostia parametreillaan. Yhteensopivuuden vuoksi.
	 *
	 */
	protected void doGet(
			HttpServletRequest req,
			HttpServletResponse resp
			) throws ServletException, java.io.IOException {

		doPost(req, resp);
	}// doGet

	/** Toteuttaa keskeisen osan luokan toiminnoista, eli tarkistaa
	 * sy�tettyjen kriteerien syntaksin ja generoi uuden sivun, jolla
	 * voi sy�tt�� palautteen. Jos jonkin kriteerin syntaksi on 
	 * virheellinen, sivu palaa takaisin edelliselle sivulle ja antaa
	 * virheilmoituksen.
	 */

	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse resp
			) throws ServletException, java.io.IOException {


		TTK91TaskOptions taskOptions = new TTK91TaskOptions();
		
		// N�m� muuttujat ovat edellisen sivun inputin 
		// lukemista varten parametrista req.
		// Jokainen arvo tarkistetaan siten ett� katsotaan onko
		// sen tyyppi ja syntaksi oikein.

		// suoritettavien konek�skyjen maksimim��r�
	
		String reqEvent = req.getParameter(
				"event");
		
		String reqMaxCommands = req.getParameter(
				"maxCommands"
				);
		// malliratkaisu
		String reqExampleCode;
		// teht�v�nanto
		String reqTaskDescription = req.getParameter(
				"taskDescription"
				);
		// julkiset sy�tteet
		String reqPublicInput = req.getParameter(
				"publicInput"
				);
		// piilotetut sy�tteet
		String reqHiddenInput = req.getParameter(
				"hiddenInput"
				);
		// verrataanko simulaatioon
		String reqCompareMethod = req.getParameter(
				"compareMethod"
				);
		
		// miksi t�m� on?
		String reqAcceptedSize = req.getParameter(
				"acceptedSize"
				); 
		// ratkaisun suosituskoko
		String reqOptimalSize = req.getParameter(
				"optimalSize"
				); 
		// kielletyt konek�skyt
		String reqRequiredCommands = req.getParameter(
				"requiredCommands"
				);
		// vaaditut konek�skyt
		String reqForbiddenCommands = req.getParameter(
				"forbiddenCommands"
				);
		
		// pyydetyt rekisterien arvot
		String reqRegisterValues = req.getParameter(
				"registerValues"
				);
		
		//muistipaikkojen arvot
		String reqMemoryValues = req.getParameter(
				"memoryValues"
				);
		// Muistiviittaukset
		String reqMemoryReferences = req.getParameter(
				"memoryReferences"
				);
		
		// n�yt�n tulosteet
		String reqScreenOutput = req.getParameter(
				"screenOutput"
				); 
		
		// tulosteet tiedostoon
		String reqFileOutput = req.getParameter(
				"fileOutput"
				); 
		

		String exampleCode; //TODO tarkista koodin k��ntyminen
		String taskDescription; // tarviiko tarkistaa?
		String[] requiredCommands; // JUMP
		String[] forbiddenCommands; // EQU
		int compareMethod; // 0 = static, 1 = simuloitu
		int maxCommands;
		int acceptedSize; // 200 rivi�
		int optimalSize; // 10 rivi�
		int memoryReferences;
		int[][] screenOutput; // (1,3) (2,4) (4,3)
		int[][] fileOutput; // (1,3)(2,4)(4,3)
		int[] publicInput; 
		int[] hiddenInput; // 1,2,3,5,2...
		TTK91TaskCriteria[] registerCriteria; // R2 > 1
		TTK91TaskCriteria[] memoryCriteria; // Mikko < Ville


		// TODO checking of session, but no need for new one
		
		HttpSession session = req.getSession(false);


		// TODO n�m� omiksi privametodeikseen
		
		try { 
			// maxCommands ei voi olla null
			// TODO konffattava oletusarvo
			
			if(reqMaxCommands != null) {
			
				maxCommands = parsePostInt(reqMaxCommands);
			
			} else {
			
				maxCommands = 10000;
			
			}
			
			taskOptions.setMaxCommands(maxCommands);
		
		} catch (Exception e) { 
			// TODO lis�� virheen palauttaminen
			req.getRequestDispatcher("StaticTTK91Composer.jsp")
				.forward(req, resp);
			return;
		}// catch

		try { // exampleCode
			// TODO validin TTK91-koodin tarkistaminen
			// TODO dynaamisen koodin syntaksin tarkistaminen
			// TODO dynaamisen koodin vaatiminen

			exampleCode = reqExampleCode;
			taskOptions.setExampleCode(exampleCode);
		
		} catch (Exception e) {

		}

		try { // taskDescription
			// TODO vaaditaanko?
			// TODO dynaamisen teht�v�n syntaksin tarkistaminen
			// TODO dynaamisen teht�v�n vertailu mallikoodiin

			taskDescription = reqTaskDescription;
			taskOptions.setTaskDescription(taskDescription);
			
		} catch (Exception e) {

		}

		try { // publicInput
			
			if(reqPublicInput != null) {
				StringTokenizer st = new StringTokenizer(
						reqPublicInput, ","
						);
				Vector tmp = new Vector();

				while(st.hasMoreTokens()){

					tmp.add(st.nextToken());
				}

				publicInput = new int[tmp.size()];


				for(int i = 0; i < tmp.size(); i++) {
				
					publicInput[i] = parsePostInt((String)tmp.get(i));

				}

				taskOptions.setPublicInput(publicInput);
			}
		} catch (Exception e) {
			// TODO lis�� virheen palauttaminen
			req.getRequestDispatcher("StaticTTK91Composer.jsp")
				.forward(req, resp);
			return;
		}

		try { // hiddenInput
			if(reqHiddenInput != null) {
				StringTokenizer st = new StringTokenizer(
						reqHiddenInput, ","
						);
				Vector tmp = new Vector();

				while(st.hasMoreTokens()){

					tmp.add(st.nextToken());
				}

				hiddenInput = new int[tmp.size()];


				for(int i = 0; i < tmp.size(); i++) {
					hiddenInput[i] = parsePostInt((String)tmp.get(i));

				}

				taskOptions.setHiddenInput(hiddenInput);
			}
		} catch (Exception e) {
			// TODO lis�� virheen palauttaminen
			req.getRequestDispatcher("StaticTTK91Composer.jsp")
				.forward(req, resp);
		}
	
		try { // compareMethod

			if(reqCompareMethod != null) {
				compareMethod = parsePostInt(reqCompareMethod);
			}

			
		} catch (Exception e) {

			// TODO virheen palautus.
			// T�lle metodinsa kun se on n�iss�
			// kkaikissa?
		}

		try { //  acceptedSize
			if(reqAcceptedSize != null) {
				acceptedSize = parsePostInt(reqAcceptedSize);
			
				taskOptions.setAcceptedSize(acceptedSize);
			
			}

		} catch (Exception e) {

		}


		try { // optimalSize
			if(reqOptimalSize != null) {
				optimalSize = parsePostInt(reqOptimalSize);
				
				taskOptions.setOptimalSize(optimalSize);
			}


			
		} catch (Exception e) {

		}

		try { // requiredCommands

			if( reqRequiredCommands != null) {

				requiredCommands = reqRequiredCommands.split(
						","
						);

				taskOptions.setRequiredCommands(
						requiredCommands
						);

			}
			
		} catch (Exception e) {

		}

		try { // forbiddenCommands

			if( reqForbiddenCommands != null) {

				forbiddenCommands = reqForbiddenCommands.split(
						","
						);

				taskOptions.setForbiddenCommands(
						forbiddenCommands
						);
			}

		} catch (Exception e) {

		}



		try { // memoryCriteria

		} catch (Exception e) {

		}

		try { // registerCriteria

		} catch (Exception e) {

		}
	
		try { // screenOutput

		} catch (Exception e) {

		}
		
		
		try { // fileOutput

		} catch (Exception e) {

		}
		
		session.setAttribute("TTK91TaskOptions", taskOptions);

		res.setContentType ("text/html");
		ServletOutputStream out = res.getOutputStream();
		out.print(feedbackForm());

		
	} // doPost

	private String feedbackForm() {

		// TODO language!!
		
		String page = "<html>";

		// head
		page.concat(
				"<head>"+
				"<title>Untitled Document</title>" +
				"<meta http-equiv=\"Content-Type\" "+
				"content=\"text/html; charset=iso-8859-1\">"+
				"</head>"
			   );
		// body
		
		page.concat(
				"<body bgcolor=\"#FFFFFF\">"

			   );
		// title
		
		page.concat(
				"<h1>Opiskelijalle annettavat palautteet</h1>"
			   );

		// form
		// FIXME assuming eassari in ../eassari
		page.concat(
				"<form method=\"post\" action=\""+
				"../eassari/taskdefinition/TaskDefintionController"+
				"\">\n"
			   );

		page.concat(
				"  <table width=\"450\" border=\"0\">"+
				"   <tr>"+
				"        <td>" +
				"  <div align=\"center\">"+
				"<b>Kriteeri t&auml;yttyy</b>" +
				"</div>" +
				"</td>" +
				"<td>" +
				"       <div align=\"center\">" +
				"<b>Kriteeri ei t&auml;yty</b>" +
				"</div>" +
				"</td>" +
				"</tr>" +
				"</table>\n"
			   );

		page.concat(
				"  <p>Hyv&auml;ksytt&auml;v&auml;n"+
				" ratkaisun k&auml;skyjen " +
				"maksimim&auml;&auml;r&auml;  </p>\n"+
				feedbackBox("acceptedSize")
			   );

		page.concat(
				"  <p>Ihannekoko  </p>\n"+
				feedbackBox("optimalSize")
			   );

		page.concat(
				"  <p>Ohjelmassa vaaditut k&auml;skyt  </p>\n"+
				feedbackBox("requiredCommands")
			   );

		page.concat(
				"<p>Ohjelmassa kielletyt k&auml;skyt</p>\n" +
				feedbackBox("forbiddenCommands")
				);

		page.concat(
				"<p>Rekisterien sis&auml;lt&ouml;</p>" +
				feedbackBox("register")
			   );
		
		page.concat(
				"  <p>Muistipaikkojen ja muuttujien"+
				"sis&auml;lt&ouml;</p>" +
				feedbackBox("memory")
			   );
		
		page.concat(
				"<p>Muistiviitteiden m&auml;&auml;r&auml;</p>" +
				feedbackBox("memoryReferences")
			   );
		
		page.concat(
				"<p>Tulosteet n&auml;yt&ouml;lle</p>" +
				feedbackBox("screenOutput")
			   );
		
		page.concat(
				"  <p>Tulosteet tiedostoon</p>" +
				feedbackBox("fileOutput")
			   );

		page.concat(
				"  <p>"+
				"<input type=\"submit\""+
				" name=\"Submit\""+
				"value=\"Luo teht&auml;v&auml;\">"+
				"</p>"
			   );

		page.concat("</form></body></html>");
				

		return page;
	}


	private String feedbackBox(String name) {

		return "<p>\n"+
			"<textarea cols=\"40\" rows=\"5\" "+
			"name=\""+
			name+
			"FeedbackPositive\">"+
			"</textarea>\n"+
			"<textarea name="+
			"\""+
			name+
			"FeedbackNegative\""+
			"cols=\"40\" rows=\"5\">"+
			"</textarea>\n"+
			"</p>\n";
	}


	private int parsePostInt(String s) throws Exception {

		return ((new Integer(s)).intValue());

	}

	/** Servletin oma sis�inen apumetodi. T�t� hy�dynnet��n
	 * doPostin parsiessa kriteereit� erilleen saamastaan 
	 * HttpRequestista.
	 */

	private String[] parsePostText(String s) {

	}//parsePostText


} //class
