package fi.helsinki.cs.koskelo.analyser;

import fi.helsinki.cs.koskelo.common.TTK91Constant;
import fi.helsinki.cs.koskelo.common.TTK91TaskCriteria;

import fi.hu.cs.titokone.MemoryLine;

import fi.hu.cs.ttk91.TTK91Cpu;

import java.util.HashMap;

/**
 * Kirjastoluokka analyserien tarpeisiin
 *
 */

public final class TTK91AnalyserExtraUtils {

	private TTK91AnalyserExtraUtils() {} // minustahan ei ilmentymi‰ tehd‰.

	/**
	 * Palauttaa first <op> second, miss‰ <op> sis‰lt‰‰
	 * normaalit aritmeettiset vertailuoperaatiot
	 * @param first - ensimm‰inen vertailtava
	 * @param comparator - vertailuoperaattori, m‰‰ritykset TTK91Constant-luokassa
	 * @param second - toinen vertailtava
	 */

	public static boolean compare(int first, int comparator, String second) {
		try {
			int sec = Integer.parseInt(second);
			return compare(first, comparator, sec);
		}
		catch (NumberFormatException e) {
			return false; // jotain on rikki kriteereiss‰
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
	 * Tarkistaa lˆytyykˆ ensimm‰isest‰ merkkijonosta toisen parametrin osoittama k‰sky.
	 * Ei ole ihan puhdas '(indexOf > 0)', mutta melkein ;)
	 * @param answer
	 * @param cmd
	 * @return null jos ongelmia, Boolean.FALSE jos ei lˆydy, Boolean.TRUE jos lˆytyy.
	 */
	public static Boolean isCommandFound(String answer, String cmd) {

		System.err.println("Saavuttiin isCommandFound()iin");

		if ((answer != null) && (cmd != null)) {
			System.err.println("P‰‰stiin sis‰‰n varsinaiseen"+
					" isCommandFound()iin, eli alkuehdot kunnossa");

			String haystack = answer.toLowerCase();
			String needle = cmd.toLowerCase();

			needle = needle.substring(needle.lastIndexOf("(")+1);
			// jos avaava sulje lˆytyy, poistetaan
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
			System.err.println("matchayksen tulos (intin‰),"+
					"eli palautetaan (temp >= 0): "+temp);
			return new Boolean(temp >= 0);
		}
		System.err.println("isCommandFound: answer != null: "+
				(answer != null)+", cmd != null: "+(cmd != null));
		return null; // jotain meni pieleen, mutta ei nyt osata kertoa sit‰ tarkemmin...
	} // isCommandFound


	/**
	 * Tarkistaa ettei ensimm‰isest‰ merkkijonosta lˆydy 
	 * toisen parametrin osoittamaa merkkijonoa.
	 * Ei ole ihan puhdas '(indexOf > 0)', mutta melkein ;)
	 * @param answer
	 * @param cmd
	 * @return null jos ongelmia, Boolean.TRUE jos ei lˆydy, Boolean.FALSE jos lˆytyy.
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
			HashMap symboltable,
			MemoryLine[] memoryLines
			) {

		String studentsymbol = criteria.getFirstComparable();
		
		int studentMemoryValue;
		int memoryslot = -1;
		MemoryLine memoryLine = null;

		try{

			memoryslot = Integer.parseInt(studentsymbol);

		} catch (NumberFormatException nfe) {

			Integer memoryInteger = (Integer)
				(symboltable.get(studentsymbol));
			
			if(memoryInteger != null) {
				memoryslot = memoryInteger.intValue();
			} else {
				return false;
			}
		}

		if(
				(memoryslot >= 0) && 
				(memoryslot < memoryLines.length)
				){
			memoryLine = memoryLines[memoryslot];
		
		} else {
			return false;
		}
				 

		studentMemoryValue = memoryLine.getBinary();


		return compare(
				studentMemoryValue, 
				criteria.getComparator(), 
				criteria.getSecondComparable()
				);


	
	}

	public static boolean checkStaticMemoryCriteria(
			TTK91TaskCriteria criteria,
			HashMap studentPublicSymbolTable,
			HashMap studentHiddenSymbolTable,
			MemoryLine[] studentPublicMemoryLines,
			MemoryLine[] studentHiddenMemoryLines
			) {

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


	public static boolean checkSimulatedMemoryCriteria(
			TTK91TaskCriteria criteria,
			HashMap studentSymbolTable,
			HashMap teacherSymbolTable,
			MemoryLine[] studentMemoryLines,
			MemoryLine[] teacherMemoryLines
			) {
		
		String studentsymbol = criteria.getFirstComparable();
		String teachersymbol = criteria.getSecondComparable();
		
		int studentMemoryValue;
		int teacherMemoryValue;
		int studentMemoryslot = -1;
		int teacherMemoryslot = -1;
		
		MemoryLine studentMemoryLine = null;
		MemoryLine teacherMemoryLine = null;
		
		try{

			studentMemoryslot = Integer.parseInt(studentsymbol);

			
		} catch (NumberFormatException nfe) {

			Integer memoryInteger = (Integer)
				(studentSymbolTable.get(studentsymbol));

			
			if(memoryInteger != null) {
				studentMemoryslot = memoryInteger.intValue();
			} else {
				return false;
			}
		}

		try{

			teacherMemoryslot = Integer.parseInt(teachersymbol);
			
		} catch (NumberFormatException nfe) {

			Integer memoryInteger = (Integer)
				(teacherSymbolTable.get(teachersymbol));

			
			if(memoryInteger != null) {
				teacherMemoryslot = memoryInteger.intValue();
			} else {
				return false;
			}
		}

		if(
				(studentMemoryslot >= 0) &&
				(teacherMemoryslot >= 0) &&
				(studentMemoryslot < studentMemoryLines.length)&&
				(teacherMemoryslot < teacherMemoryLines.length)
				){
		
			studentMemoryLine = studentMemoryLines[studentMemoryslot];
			teacherMemoryLine = teacherMemoryLines[teacherMemoryslot];
		} else {
			return false;
		}
				 

		studentMemoryValue = studentMemoryLine.getBinary();
		teacherMemoryValue = teacherMemoryLine.getBinary();


		return compare(
				studentMemoryValue, 
				criteria.getComparator(), 
				teacherMemoryValue
				);



	}

	public static boolean checkSimulatedMemoryCriteria(
			TTK91TaskCriteria criteria,
			HashMap studentPublicSymbolTable,
			HashMap studentHiddenSymbolTable,
			HashMap teacherPublicSymbolTable,
			HashMap teacherHiddenSymbolTable,
			MemoryLine[] studentPublicMemoryLines,
			MemoryLine[] studentHiddenMemoryLines,
			MemoryLine[] teacherPublicMemoryLines,
			MemoryLine[] teacherHiddenMemoryLines
			) {
	
		boolean publiccrit = false;
		boolean hiddencrit = true;

		publiccrit = checkSimulatedMemoryCriteria(
				criteria,
				studentPublicSymbolTable,
				teacherPublicSymbolTable,
				studentPublicMemoryLines,
				teacherPublicMemoryLines
				);

		hiddencrit = checkSimulatedMemoryCriteria(
				criteria,
				studentHiddenSymbolTable,
				teacherHiddenSymbolTable,
				studentHiddenMemoryLines,
				teacherHiddenMemoryLines
				);

		return (publiccrit && hiddencrit);

	}

	public static boolean checkRegisterCriteria(
			TTK91TaskCriteria criteria,
			TTK91Cpu studentCpu, 	//not null
			TTK91Cpu compareCpu
			) {

	String studentRegister = criteria.getFirstComparable();
	String compareRegister = criteria.getSecondComparable();

	int studentRegisterValue;
	int compareRegisterValue;
	
	try {
		compareRegisterValue = Integer.parseInt(compareRegister);

	} catch (NumberFormatException nfe) {

		if ( compareCpu != null) {
		
			int registerNumber = getRegisterNumber(compareRegister);
			
			if( registerNumber == -1 ) {
				return false;
			}

			compareRegisterValue = compareCpu.getValueOf(registerNumber);

		} else {
			return false;
		}
	}
	

	int studentRegisterNumber = getRegisterNumber(studentRegister);

	if(studentRegisterNumber == -1) {
		return false;
	}
	
	studentRegisterValue = studentCpu.getValueOf(studentRegisterNumber);
	
	return compare(
			studentRegisterValue,
			criteria.getComparator(),
			compareRegisterValue
			);
	
	}
 
	private static int getRegisterNumber( String register ){

		String compare = register.toUpperCase();
		
		if(compare.equals("TR")) {
			
			return fi.hu.cs.ttk91.TTK91Cpu.CU_TR;
			
		} else if(compare.equals("IR")) {

			return fi.hu.cs.ttk91.TTK91Cpu.CU_IR;

		} else if(compare.equals("PC")) {

			return fi.hu.cs.ttk91.TTK91Cpu.CU_PC;

		} else if(compare.equals("SR")) {

			return fi.hu.cs.ttk91.TTK91Cpu.CU_SR;

		} else if(compare.equals("R0")) {

			return fi.hu.cs.ttk91.TTK91Cpu.REG_R0;

		} else if(compare.equals("R1")) {

			return fi.hu.cs.ttk91.TTK91Cpu.REG_R1;

		} else if(compare.equals("R2")) {

			return fi.hu.cs.ttk91.TTK91Cpu.REG_R2;

		} else if(compare.equals("R3")) {

			return fi.hu.cs.ttk91.TTK91Cpu.REG_R3;

		} else if(compare.equals("R4")) {

			return fi.hu.cs.ttk91.TTK91Cpu.REG_R4;

		} else if(compare.equals("R5")) {

			return fi.hu.cs.ttk91.TTK91Cpu.REG_R5;

		} else if(compare.equals("R6")) {

			return fi.hu.cs.ttk91.TTK91Cpu.REG_R6;

		} else if(compare.equals("R7")) {

			return fi.hu.cs.ttk91.TTK91Cpu.REG_R7;
		
		} else if(compare.equals("SP")) {
		
			return fi.hu.cs.ttk91.TTK91Cpu.REG_SP;

		} else if(compare.equals("FP")) {

			return fi.hu.cs.ttk91.TTK91Cpu.REG_FP;

		} else {

			// ei tunnettu rekisteri.
			return -1;
		}

	}

    public static int[] parseOutputArrays(String from) {
	if (from == null) {
	    return null;
	}
	String lineFeed = System.getProperty("line.separator", "\n");
	String[] outputArray = from.split(lineFeed); // k‰yt‰nnˆss‰ siis stringiss‰ on rivinvaihdolla erotettuna numeroita FIXME: testausraporttiin/yll‰pitodokkariin kommentti; mit‰s jos on kaksimerkkinen rivinvaihto?
	
	int[] to = new int[outputArray.length];
	for (int i=0; i < outputArray.length; ++i) {
	    try {
		to[i] = Integer.parseInt(outputArray[i]);
	    }
	    catch (NumberFormatException e) {
		return null;
	    }
	}
	return to;
    }
    
    public static boolean checkOutputCriteria(TTK91TaskCriteria crit, int[] output, int[] compareOutput) {
	System.err.println("\n********************************\ncheckOutputCriteria not implemented\n************************************");
	return false;
    }

}
