
package fi.helsinki.cs.koskelo.common;

/** TTK91 teht‰vien parsimiseen k‰ytett‰vi‰ yhteisi‰ metodeja */

public class TTK91ParserUtils { 

	/** Tarkistaa onko annettu String m‰‰ritelty TTK91-konekielen
	 * k‰skyksi. Jos TTK-91 kieleen m‰‰ritell‰‰n uusia k‰skyj‰
	 * syksyn 2004 j‰lkeen, on ne k‰sin lis‰tt‰v‰ t‰h‰n luokkaan
	 * tai muutettava t‰m‰ metodi, jotta automaattinen teht‰vien
	 * tarkistus ja luonti edelleen toimii.
	 * @param command TTK-91 k‰skyn String-esitys
	 * @return Onko annettu k‰sky validi TTK-91 k‰sky
	 */

	static public boolean validateTTK91Command(String command) {

		if( 
				command.equalsIgnoreCase("LOAD") || 
				command.equalsIgnoreCase("STORE") ||
				command.equalsIgnoreCase("IN") ||
				command.equalsIgnoreCase("OUT") ||
				command.equalsIgnoreCase("ADD") ||
				command.equalsIgnoreCase("SUB") ||
				command.equalsIgnoreCase("MUL") ||
				command.equalsIgnoreCase("DIV") ||
				command.equalsIgnoreCase("MOD") ||
				command.equalsIgnoreCase("AND") ||
				command.equalsIgnoreCase("OR")  ||
				command.equalsIgnoreCase("XOR") ||
				command.equalsIgnoreCase("SHL") ||
				command.equalsIgnoreCase("SHR") ||
				command.equalsIgnoreCase("COMP") ||
				command.equalsIgnoreCase("JUMP") ||
				command.equalsIgnoreCase("JNEG") ||
				command.equalsIgnoreCase("JZER") ||
				command.equalsIgnoreCase("JPOS") ||
				command.equalsIgnoreCase("JNNEG") ||
				command.equalsIgnoreCase("JNZER") ||
				command.equalsIgnoreCase("JNPOS") ||
				command.equalsIgnoreCase("JEQU") ||
				command.equalsIgnoreCase("JGRE") ||
				command.equalsIgnoreCase("JLES") ||
				command.equalsIgnoreCase("JNLES") ||
				command.equalsIgnoreCase("JNEQU") ||
				command.equalsIgnoreCase("JNGRE") ||
				command.equalsIgnoreCase("PUSH") ||
				command.equalsIgnoreCase("POP") ||
				command.equalsIgnoreCase("CALL") ||
				command.equalsIgnoreCase("EXIT") ||
				command.equalsIgnoreCase("SVC") ||
				command.equalsIgnoreCase("NOP") ||
				command.equalsIgnoreCase("NOT") ||
				command.equalsIgnoreCase("SHRA") ||
				command.equalsIgnoreCase("DC") ||
				command.equalsIgnoreCase("DS") ||
				command.equalsIgnoreCase("EQU")
				
				) {
				
					return true;
				
				} else {
				
					return false;
				
				}
	
	}
}
