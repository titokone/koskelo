/*
 * Created on 1.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.taskdefinition.util;

import java.sql.Connection;

import junit.framework.TestCase;

/**
 * Testclass for DBOperationsManager-class.
 * 
 * @author tjvander
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DBOperationsManagerErrorTest extends TestCase {

	DatabaseBase dbase=new TaskDataBase();
	Connection con=null;
	String tooLong="Helsingin yliopiston tietojenkäsittelytieteenlaitos";
	/**
	 * Constructor for DBOperationsManagerErrorTest.
	 * @param arg0
	 */
	public DBOperationsManagerErrorTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		con=dbase.getConnection();
		assertTrue(tooLong.length()>40);
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		dbase.closeConnection(null, con);
	}

	public void testDeleteTaskData() {
		dbase.closeConnection(null, con);
		assertTrue(-1==DBOperationsManager.deleteTaskData(con,tooLong));

	}

	public void testDeleteTaskData2() {

		assertTrue(-2==DBOperationsManager.deleteTaskData(con,"TEST11"));

	}
	public void testGetGeneralTaskData() {
		
		dbase.closeConnection(null, con);
		assertNull(DBOperationsManager.getGeneralTaskData(con, "TEST11"));
	}

	public void testExecuteSQLUpdate() {
		String line=DBOperationsManager.makeAttributeLine("X", "DBOperationsTest", tooLong, "EN","C", "Junit testvalue");
		assertTrue(!DBOperationsManager.executeSQLUpdate(con, line));	
	}

	public void testDeleteAttribute() {
		
		dbase.closeConnection(null, con);
		assertTrue(!DBOperationsManager.deleteAttribute(con,"X", "DBOperationsTest", "testDeleteAttribute", "EN"));
	}

	public void testGetAttributevalue() {
		
		dbase.closeConnection(null, con);
		assertNull(DBOperationsManager.getAttributevalue(con, "X", "DBOperationsTest", "testDeleteAttribute", "EN"));

	}

	public void testDeleteAttributesByObjectID() {

		dbase.closeConnection(null, con);
		assertTrue(-1==DBOperationsManager.deleteAttributesByObjectID(con, "DBoperationsTest"));
	}

	public void testGetAuthorTasks() {
		
		dbase.closeConnection(null, con);
		assertNull(DBOperationsManager.getAuthorTasks(con, "Teemu Andersson"));
	}

	public void testGetTaskLanguageVersions() {
		
		dbase.closeConnection(null, con);
		assertNull(DBOperationsManager.getTaskLanguageVersions(con, "TASK01"));
	}

	public void testGetAttributesByLanguage() {
		
		dbase.closeConnection(null, con);
		assertNull(DBOperationsManager.getAttributesByLanguage(con,"TASK01","EN"));

	}

}
