
package fi.helsinki.cs.koskelo.common;

/** TTK91 teht�vien parsimiseen k�ytett�vi� yhteisi� metodeja */

public class TTK91ParserUtils { 

	/** Tarkistaa onko annettu String m��ritelty TTK91-konekielen
	 * k�skyksi.
	 * @param command TTK-91 k�skyn String-esitys
	 * @return Onko annettu k�sky validi TTK-91 k�sky
	 */

	static public boolean validateTTK91Command(String command) {

		command.toUpperCase();

		if( 
				command.equals("LOAD") || 
				command.equals("STORE") ||
				command.equals("IN") ||
				command.equals("OUT") ||
				command.equals("ADD") ||
				command.equals("SUB") ||
				command.equals("MUL") ||
				command.equals("DIV") ||
				command.equals("MOD") ||
				command.equals("AND") ||
				command.equals("OR")  ||
				command.equals("XOR") ||
				command.equals("SHL") ||
				command.equals("SHR") ||
				command.equals("COMP") ||
				command.equals("JUMP") ||
				command.equals("JNEG") ||
				command.equals("JZER") ||
				command.equals("JPOS") ||
				command.equals("JNNEG") ||
				command.equals("JNZER") ||
				command.equals("JNPOS") ||
				command.equals("JEQU") ||
				command.equals("JGRE") ||
				command.equals("JLES") ||
				command.equals("JNLES") ||
				command.equals("JNEQU") ||
				command.equals("JNGRE") ||
				command.equals("PUSH") ||
				command.equals("POP") ||
				command.equals("CALL") ||
				command.equals("EXIT") ||
				command.equals("SVC") ||
				command.equals("NOP") ||
				command.equals("NOT") ||
				command.equals("SHRA") ||
				command.equals("DC") ||
				command.equals("DS") ||
				command.equals("EQU")
				
				) {
				
					return true;
				
				} else {
				
					return false;
				
				}
	
	}
}
