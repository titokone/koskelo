package fi.helsinki.cs.koskelo.analyser;

import fi.helsinki.cs.koskelo.common.TTK91Constant;

/**
 * Kirjastoluokka analyserien tarpeisiin
 *
 */

public final class TTK91AnalyserExtraUtils {
    
    private TTK91AnalyserExtraUtils() {} // minustahan ei ilmentymiä tehdä.

    /**
     * Palauttaa first <op> second, missä <op> sisältää
     * normaalit aritmeettiset vertailuoperaatiot
     * @param first - ensimmäinen vertailtava
     * @param comparator - vertailuoperaattori, määritykset TTK91Constant-luokassa
     * @param second - toinen vertailtava
     */

    public static boolean compare(int first, int comparator, String second) {
	try {
	    int sec = Integer.parseInt(second);
	    return compare(first, comparator, sec);
	}
	catch (NumberFormatException e) {
	    return false; // jotain on rikki kriteereissä
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
     * Tarkistaa löytyykö ensimmäisestä merkkijonosta toisen parametrin osoittama käsky.
     * Ei ole ihan puhdas '(indexOf > 0)', mutta melkein ;)
     * @param answer
     * @param cmd
     * @return null jos ongelmia, Boolean.FALSE jos ei löydy, Boolean.TRUE jos löytyy.
     */
    public static Boolean isCommandFound(String answer, String cmd) {

	System.err.println("Saavuttiin isCommandFound()iin");
	
	if ((answer != null) && (cmd != null)) {
	    System.err.println("Päästiin sisään varsinaiseen isCommandFound()iin, eli alkuehdot kunnossa");
	    
	    String haystack = answer.toLowerCase();
	    String needle = cmd.toLowerCase();
	    
	    needle = needle.substring(needle.lastIndexOf("(")+1); // jos avaava sulje löytyy, poistetaan
	    int temp = needle.indexOf(")");
	    if (temp > 0) {
		needle = needle.substring(0, temp); // poistetaan mahdollinen loppusulje
	    }
	    temp = needle.indexOf(";");
	    if (temp > 0) {
		needle = needle.substring(0, temp); // poistetaan mahdollinen ';'
	    }
	    
	    System.err.println("needle: |"+needle+"|");
	    System.err.println("valmiina ajamaan matchays...");
	    
	    temp = answer.indexOf(needle);
	    System.err.println("matchayksen tulos (intinä), eli palautetaan (temp >= 0): "+temp);
	    return new Boolean(temp >= 0);
	}
	System.err.println("isCommandFound: answer != null: "+
			   (answer != null)+", cmd != null: "+(cmd != null));
	return null; // jotain meni pieleen, mutta ei nyt osata kertoa sitä tarkemmin...
    } // isCommandFound
    

    /**
     * Tarkistaa ettei ensimmäisestä merkkijonosta löydy toisen parametrin osoittamaa merkkijonoa.
     * Ei ole ihan puhdas '(indexOf > 0)', mutta melkein ;)
     * @param answer
     * @param cmd
     * @return null jos ongelmia, Boolean.TRUE jos ei löydy, Boolean.FALSE jos löytyy.
     */

    public static Boolean commandNotFound(String answer, String cmd) {
	Boolean returnMe = isCommandFound(answer, cmd);
	if (returnMe != null) {
	    returnMe = new Boolean(!returnMe.booleanValue());
	}
	return returnMe;
    }
}
