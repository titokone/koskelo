/*
 * Created on 26.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.tests;

import junit.framework.TestCase;
import fi.hy.eassari.taskdefinition.util.BlanksParser;
import fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO;

/**
 * Test class for BlanksParser.
 * 
 * @author Sami Termonen
 *
 */
public class BlanksParserTest extends TestCase {
	private static final String before1 = "";
	private static final String blank1 = "Aukko ";
	private static final String after1 = "alussa ja ";
	private static final String before2 = "alussa ja ";
	private static final String blank2 = "lopussa";
	private static final String after2 = "";
	private static final String beforee1 = "";
	private static final String blanke1 = "Aukko ";
	private static final String aftere1 = "alussa ja ja ";
	private static final String beforee2 = "alussa ja ";
	private static final String blanke2 = "lopussa ";
	private static final String aftere2 = "";
	private static final String beforee3 = "alussa ja ja ";
	private static final String blanke3 = "keskellä.";
	private static final String aftere3 = "";
	private static final String beforeee1 = "TÄmä on ";
	private static final String blankee1 = "aukko ";
	private static final String afteree1 = "tehtävä. Seuraavassa tekstissä on ";
	private static final String beforeee2 = "tehtävä. Seuraavassa tekstissä on ";
	private static final String blankee2 = "muutama ";
	private static final String afteree2 = "aukko.mukaan Nelosen uutisten julkistama ";
	private static final String beforeee3 = " Seuraavassa tekstissä on aukko.";
	private static final String blankee3 = "Keskusrikospoliisin ";
	private static final String afteree3 = "mukaan Nelosen uutisten julkistama Nils ";
	private static final String beforeee4 = "on aukko.mukaan Nelosen uutisten ";
	private static final String blankee4 = "eilen ";
	private static final String afteree4 = "julkistama Nils Gustafssonin vuodelta 1995 ";
	private static final String beforeee5 = "Nelosen uutisten julkistama Nils Gustafssonin ";
	private static final String blankee5 = "haastattelu ";
	private static final String afteree5 = "vuodelta 1995 on tärkeä osa ";
	private static final String beforeee6 = "on tärkeä osa murhatutkimusta. ";
	private static final String blankee6 = "Nauhaa ";
	private static final String afteree6 = "ei ole julkaistu ennen, ";
	private static final String beforeee7 = "ja poliisikin sai sen käsiinsä ";
	private static final String blankee7 = "vasta ";
	private static final String afteree7 = "hiljattain. Nauhalla Bodominjärven kolmoismurhasta ";
	private static final String beforeee8 = "hiljattain. Nauhalla Bodominjärven kolmoismurhasta ";
	private static final String blankee8 = "tutkintavankeudessa ";
	private static final String afteree8 = "oleva Gustafsson kertoo muun muassa,";
	private static final String beforeee9 = "surmayönä nuoret heittivät huulta ja ";
	private static final String blankee9 = "menivät ";
	private static final String afteree9 = "nukkumaan telttaan sulassa LUE LISÄÄ ";
	private static final String beforeee10 = "huulta ja nukkumaan telttaan sulassa ";
	private static final String blankee10 = "sovussa ";
	private static final String afteree10 = "LUE LISÄÄ JA KUUNTELE OSA ";
	private static final String beforeee11 = "LISÄÄ JA KUUNTELE OSA HAASTATTELUSTA ";
	private static final String blankee11 = "Gustafsson ";
	private static final String afteree11 = "sanoo kavereidensa kohtalon paljastuneen vasta ";
	private static final String beforeee12 = "HAASTATTELUSTA sanoo kavereidensa kohtalon paljastuneen ";
	private static final String blankee12 = "hänelle ";
	private static final String afteree12 = "vasta kolmen viikon päästä veriteosta.";

	/**
	 * Constructor for BlanksParserTest.
	 * @param arg0
	 */
	public BlanksParserTest(String arg0) {
		super(arg0);
	}

	public void testParse() {
		BlanksParser parser = new BlanksParser();
		TaskDTO dto = new TaskDTO();

		System.out.println("Aukko alussa. ja lopussa.");

		int i = 1;

		dto.set("text", "[[Aukko]] alussa ja [[lopussa]]");
		dto = parser.parse(dto);

		assertEquals(before1, dto.get("before1"));
		assertEquals(blank1, dto.get("blank1"));
		assertEquals(after1, dto.get("after1"));
		assertEquals(before2, dto.get("before2"));
		assertEquals(blank2, dto.get("blank2"));
		assertEquals(after2, dto.get("after2"));


		i = 1;
				
		System.out.println("Aukko alussa, lopussa ja keskellä.");
				
		dto.set("text", "[[Aukko]] alussa ja [[lopussa]] ja [[keskellä]].");
		
		dto = parser.parse(dto);

		assertEquals(beforee1, dto.get("before1"));
		assertEquals(blanke1, dto.get("blank1"));
		assertEquals(aftere1, dto.get("after1"));
		assertEquals(beforee2, dto.get("before2"));
		assertEquals(blanke2, dto.get("blank2"));
		assertEquals(aftere2, dto.get("after2"));
		assertEquals(beforee3, dto.get("before3"));
		assertEquals(blanke3, dto.get("blank3"));
		assertEquals(aftere3, dto.get("after3"));
				
		i = 1;
		
		System.out.println("Satunnainen tekstinpätkä.");
		
		dto.set("text", "TÄmä on [[aukko]] tehtävä. Seuraavassa tekstissä on [[muutama]] aukko.[[Keskusrikospoliisin]] mukaan Nelosen uutisten [[eilen]] julkistama Nils Gustafssonin [[haastattelu]] vuodelta 1995 on tärkeä osa murhatutkimusta. [[Nauhaa]] ei ole julkaistu ennen, ja poliisikin sai sen käsiinsä [[vasta]] hiljattain." + 
						" Nauhalla Bodominjärven kolmoismurhasta [[tutkintavankeudessa]] oleva Gustafsson kertoo muun muassa, että surmayönä nuoret heittivät huulta ja [[menivät]] nukkumaan telttaan sulassa [[sovussa]] LUE LISÄÄ JA KUUNTELE OSA HAASTATTELUSTA" +
						" [[Gustafsson]] sanoo kavereidensa kohtalon paljastuneen [[hänelle]] vasta kolmen viikon päästä veriteosta. NELONEN ");
		
		dto = parser.parse(dto);

		while(dto.get("blank" + i) != null){
			i++;
		}

		assertEquals(beforeee1, dto.get("before1"));
		assertEquals(blankee1, dto.get("blank1"));
		assertEquals(afteree1, dto.get("after1"));
		assertEquals(beforeee2, dto.get("before2"));
		assertEquals(blankee2, dto.get("blank2"));
		assertEquals(afteree2, dto.get("after2"));
		assertEquals(beforeee3, dto.get("before3"));
		assertEquals(blankee3, dto.get("blank3"));
		assertEquals(afteree3, dto.get("after3"));
		assertEquals(beforeee4, dto.get("before4"));
		assertEquals(blankee4, dto.get("blank4"));
		assertEquals(afteree4, dto.get("after4"));
		assertEquals(beforeee5, dto.get("before5"));
		assertEquals(blankee5, dto.get("blank5"));
		assertEquals(afteree5, dto.get("after5"));
		assertEquals(beforeee6, dto.get("before6"));
		assertEquals(blankee6, dto.get("blank6"));
		assertEquals(afteree6, dto.get("after6"));
		assertEquals(beforeee7, dto.get("before7"));
		assertEquals(blankee7, dto.get("blank7"));
		assertEquals(afteree7, dto.get("after7"));
		assertEquals(beforeee8, dto.get("before8"));
		assertEquals(blankee8, dto.get("blank8"));
		assertEquals(afteree8, dto.get("after8"));
		assertEquals(beforeee9, dto.get("before9"));
		assertEquals(blankee9, dto.get("blank9"));
		assertEquals(afteree9, dto.get("after9"));
		assertEquals(beforeee10, dto.get("before10"));
		assertEquals(blankee10, dto.get("blank10"));
		assertEquals(afteree10, dto.get("after10"));
		assertEquals(beforeee11, dto.get("before11"));
		assertEquals(blankee11, dto.get("blank11"));
		assertEquals(afteree11, dto.get("after11"));
		assertEquals(beforeee12, dto.get("before12"));
		assertEquals(blankee12, dto.get("blank12"));
		assertEquals(afteree12, dto.get("after12"));	
	}
}
