/*
 * Created on 20.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.tests;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;
import fi.hy.eassari.taskdefinition.util.LanguageHandler;
import fi.hy.eassari.taskdefinition.util.datastructures.LanguageDTO;

/**
 * Test class for LanguageHandler-class.
 * 
 * @author tjvander
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LanguageHandlerTest extends TestCase {

	/**
	 * Constructor for LanguageHandlerTest.
	 * @param arg0
	 */
	public LanguageHandlerTest(String arg0) {
		super(arg0);
	}

	public void testGetSystemLanguages() {
		
		Collection col=LanguageHandler.getSystemLanguages();
		Iterator it=col.iterator();
		
		while(it.hasNext()){
			LanguageDTO ld=(LanguageDTO)it.next();
			isNameOk(ld);
			isCodeOk(ld);
		}
	}

	public void testGetTaskLanguages() {
		
		Collection col=LanguageHandler.getTaskLanguages("TEST06");
		
		Iterator it=col.iterator();
		
		while(it.hasNext()){
			LanguageDTO ld=(LanguageDTO)it.next();
			assertTrue("Suomi".equals(ld.getLanguageName())||"English".equals(ld.getLanguageName()));
		}
		

	}

	private static void isNameOk(LanguageDTO ld){
		
		String lid=ld.getLanguageId();
		String lname=ld.getLanguageName();
		
		assertTrue(lid.equals("SWE")||lid.equals("EN")||lid.equals("FI"));
		
		if(lid.equals("SWE"))
			assertTrue(lname.equals("Svenska"));
		if(lid.equals("EN"))
			assertTrue(lname.equals("English"));
		if(lid.equals("FI"))
			assertTrue(lname.equals("Suomi"));
	}
	
	private static void isCodeOk(LanguageDTO ld){
		String lid=ld.getLanguageId();
		String lname=ld.getLanguageName();
		
		assertTrue(lname.equals("Svenska")||lname.equals("Suomi")||lname.equals("English"));
		
		if(lname.equals("Svenska"))
			assertTrue(lid.equals("SWE"));
		if(lname.equals("English"))
			assertTrue(lid.equals("EN"));
		if(lname.equals("Suomi"))
			assertTrue(lid.equals("FI"));
		
	}

}
