/*
 * Created on 20.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.tests;

import junit.framework.TestCase;
import fi.hy.eassari.taskdefinition.util.Authorization;

/**
 * Testclass for Authorization-class.
 * 
 * @author tjvander
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AuthorizationTest extends TestCase {

	/**
	 * Constructor for AuthorizationTest.
	 * @param arg0
	 */
	public AuthorizationTest(String arg0) {
		super(arg0);
	}

	public void testHasPermissions() {
		
		assertTrue(Authorization.hasPermissions("Teemu Andersson", "TEST01"));
	}

	public void testHasPermissions2() {
		
		assertFalse(Authorization.hasPermissions("Teemu Andersson", "TEST06"));
	}

}
