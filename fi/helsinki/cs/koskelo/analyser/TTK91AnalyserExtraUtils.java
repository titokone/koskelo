package fi.helsinki.cs.koskelo.analyser;

import fi.helsinki.cs.koskelo.common.TTK91Constant;

/**
 * Kirjastoluokka analyserien tarpeisiin
 *
 */

public final class TTK91AnalyserExtraUtils {

	private TTK91AnalyserExtraUtils() {} // minustahan ei ilmentymi� tehd�.

	/**
	 * Palauttaa first <op> second, miss� <op> sis�lt��
	 * normaalit aritmeettiset vertailuoperaatiot
	 * @param first - ensimm�inen vertailtava
	 * @param comparator - vertailuoperaattori, m��ritykset TTK91Constant-luokassa
	 * @param second - toinen vertailtava
	 */

	public static boolean compare(int first, int comparator, String second) {
		try {
			int sec = Integer.parseInt(second);
			return compare(first, comparator, sec);
		}
		catch (NumberFormatException e) {
			return false; // jotain on rikki kriteereiss�
		}
	}

	public static boolean compare(int first, int comparator, int second) {

		boolean ret = false;

		switch (comparator) { 

			case TTK91Constant.LESS:
				ret = (first < second);
				break;
			case TTK91Constant.LESSEQ:
				ret = (first <= second);
				break;
			case TTK91Constant.GREATER: 
				ret = (first > second);
				break;
			case TTK91Constant.GREATEREQ: 
				ret = (first >= second);
				break;
			case TTK91Constant.EQUAL: 
				ret = (first == second);
				break;
			case TTK91Constant.NOTEQUAL: 
				ret = (first != second);
				break;
			default:
				// tanne ei pitaisi paasta koskaan
		}

		return ret;

	} // compare

	/**
	 * Tarkistaa l�ytyyk� ensimm�isest� merkkijonosta toisen parametrin osoittama k�sky.
	 * Ei ole ihan puhdas '(indexOf > 0)', mutta melkein ;)
	 * @param answer
	 * @param cmd
	 * @return null jos ongelmia, Boolean.FALSE jos ei l�ydy, Boolean.TRUE jos l�ytyy.
	 */
	public static Boolean isCommandFound(String answer, String cmd) {

		System.err.println("Saavuttiin isCommandFound()iin");

		if ((answer != null) && (cmd != null)) {
			System.err.println("P��stiin sis��n varsinaiseen"+
					" isCommandFound()iin, eli alkuehdot kunnossa");

			String haystack = answer.toLowerCase();
			String needle = cmd.toLowerCase();

			needle = needle.substring(needle.lastIndexOf("(")+1);
			// jos avaava sulje l�ytyy, poistetaan
			int temp = needle.indexOf(")");
			if (temp > 0) {
				needle = needle.substring(0, temp);
				// poistetaan mahdollinen loppusulje
			}
			temp = needle.indexOf(";");
			if (temp > 0) {
				needle = needle.substring(0, temp); 
				// poistetaan mahdollinen ';'
			}

			System.err.println("needle: |"+needle+"|");
			System.err.println("valmiina ajamaan matchays...");

			temp = answer.indexOf(needle);
			System.err.println("matchayksen tulos (intin�),"+
					"eli palautetaan (temp >= 0): "+temp);
			return new Boolean(temp >= 0);
		}
		System.err.println("isCommandFound: answer != null: "+
				(answer != null)+", cmd != null: "+(cmd != null));
		return null; // jotain meni pieleen, mutta ei nyt osata kertoa sit� tarkemmin...
	} // isCommandFound


	/**
	 * Tarkistaa ettei ensimm�isest� merkkijonosta l�ydy 
	 * toisen parametrin osoittamaa merkkijonoa.
	 * Ei ole ihan puhdas '(indexOf > 0)', mutta melkein ;)
	 * @param answer
	 * @param cmd
	 * @return null jos ongelmia, Boolean.TRUE jos ei l�ydy, Boolean.FALSE jos l�ytyy.
	 */

	public static Boolean commandNotFound(String answer, String cmd) {
		Boolean returnMe = isCommandFound(answer, cmd);
		if (returnMe != null) {
			returnMe = new Boolean(!returnMe.booleanValue());
		}
		return returnMe;
	}

	public static boolean checkStaticMemoryCriteria(
			TTK91TaskCriteria criteria,
			Hashmap symboltable,
			MemoryLine[] memorylines
			) {

		String studentsymbol = criteria.getFirstComparable();
		
		String comparevalue = criteria.getSecondComparable();
		int studentMemoryValue;
		int memoryslot = -1;
		MemoryLine memoryLine = null;

		try{

			memoryslot = Integer.parseInt(studentsymbol);

		} catch (NumberFormatException nfe) {

			Integer memoryInteger = (Integer)
				(studentSymbolTable.get(studentMemSymbol));
			
			if(memoryInteger != null) {
				memoryslot = memoryInteger.intValue();
			} else {
				return false;
			}
		}

		if(
				(memoryslot >= 0) && 
				(memoryslot < memorylines.length())
				){
			memoryLine = memoryLines[memoryslot];
		
		} else {
			return false;
		}
				 

		studentMemoryValue = memoryLine.getBinary();


		return compare(studentMemoryValue, criteria.getComparator(), comparevalue);


	
	}

	public static boolean checkStaticMemoryCriteria(
			TTK91TaskCriteria criteria,
			Hashmap studentPublicSymbolTable,
			Hashmap studentHiddenSymbolTable,
			MemoryLine[] studentPublicMemoryLines,
			MemoryLine[] studentHiddentMemoryLines) {

		boolean publiccrit = false;
		boolean hiddencrit = false;

		publiccrit = checkStaticMemoryCriteria(
				criteria,
				studentPublicSymbolTable,
				studentPublicMemoryLines
				);
		hiddencrit = checkStaticMemoryCriteria(
				criteria,
				studentHiddenSymbolTable,
				studentHiddenMemoryLines
				);

		return (publiccrit && hiddencrit);

	}
				
}
