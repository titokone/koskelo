/*
 * Created on 29.3.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.taskdefinition.util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

/**
 * Testclass for DBOperationsManager-class.
 * 
 * @author Teemu Andersson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DBOperationsManagerTest extends TestCase {

	DatabaseBase dbase=new TaskDataBase();
	Connection con=null;
	/**
	 * Constructor for DBOperationsManagerTest.
	 * @param arg0
	 */
	public DBOperationsManagerTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		con=dbase.getConnection();
	}

	protected void tearDown(){
		dbase.closeConnection(null, con);
	}
	
	public void testMakeGeneralTaskDataLine() {
		
		String line=null;
		
		String date=null;
		
		java.util.Calendar rn= java.util.Calendar.getInstance();
		date=""+rn.get(java.util.Calendar.DAY_OF_MONTH)+".";
		date=date+(rn.get(java.util.Calendar.MONTH)+1)+".";
		date=date+rn.get(java.util.Calendar.YEAR);
		
		line=DBOperationsManager.makeGeneralTaskDataLine("assaritesti1", "testitehtava", "Team Assari (Teemu)", "Menutask","null",
		"3","Y","Y","Y","Y","5");
		
		assertEquals("insert into task values('assaritesti1','"+
					"testitehtava','"+
					"Team Assari (Teemu)',"+
					"to_date('"+date+"','DD.MM.YYYY'),'"+
					"Menutask','"+
					"null',"+
					"3,'"+
					"Y','"+
					"Y','"+
					"Y','"+
					"Y',"+
					"5)", line);
	}

	public void testMakeAttributeLine() {
		
		String line=null;
		
		line=DBOperationsManager.makeAttributeLine("X", "DBOperationsTest", "MakeAttributeLine", "EN","C", "Junit testvalue");
		assertEquals("insert into attributevalues values ('X','DBOperationsTest',"+
		"'MakeAttributeLine','EN','C','Junit testvalue')", line);
	}

	public void testExecuteSQLUpdate(){
		String line=DBOperationsManager.makeAttributeLine("X", "DBOperationsTest", "MakeAttributeLine", "EN","C", "Junit testvalue");
		assertTrue(DBOperationsManager.executeSQLUpdate(con, line));
	}

	public void testGetGeneralTaskData() {
		
		String line=DBOperationsManager.makeGeneralTaskDataLine("assaritesti1", "testitehtava", "Team Assari (Teemu)", "optiontask","ei ole",
		"3","Y","Y","Y","Y","5");
		assertTrue(DBOperationsManager.executeSQLUpdate(con, line));
		
		ResultSet rset=null;
		rset=DBOperationsManager.getGeneralTaskData(con, "assaritesti1");
		assertNotNull(rset);
		
		try{
			rset.next();
			assertEquals("testitehtava",rset.getString("taskname" ));
			assertTrue(rset.getInt("cutoffvalue")==5);
		}
		catch(SQLException ex){
			System.out.println(ex);
			assertTrue(0>1);
			
		}
	}
	
	public void testMakeGeneralTaskDataUpdateLine(){
		
		String line=DBOperationsManager.makeGeneralTaskDataUpdateLine("assaritesti1", 
			 "4", "N","N", "N", "N", "4");
		
		assertEquals("update task set numberoftries_def=4,"+
		"shouldstoreanswer_def='N',"+
		"shouldregistertry_def='N',"+
		"shouldknowstudent_def='N',"+
		"shouldevaluate_def='N',"+
		"cutoffvalue=4 where taskid='assaritesti1'", line);
		
		assertTrue(DBOperationsManager.executeSQLUpdate(con, line));
		ResultSet rset=DBOperationsManager.getGeneralTaskData(con, "assaritesti1");
		assertNotNull(rset);
		try{
			rset.next();
			assertEquals("testitehtava",rset.getString("taskname" ));
			assertTrue(rset.getInt("cutoffvalue")==4);
		}
		catch(SQLException ex){
			System.out.println(ex);
			assertTrue(0>1);
			
		}
	}
	
	public void testDeleteTaskData() {
		
		int lines=0;
		lines=DBOperationsManager.deleteTaskData(con, "assaritesti1");
		assertTrue(lines!=-1);
		assertTrue(lines==1);
		
	}
	


	public void testGetAttributevalue() {
		
		ResultSet rset=null;
		rset=DBOperationsManager.getAttributevalue(con,"X", "DBOperationsTest", "MakeAttributeLine","EN");
		try{
			assertNotNull(rset);
			rset.next();
			assertEquals("Junit testvalue", rset.getString("attributevalue"));
		}
		catch(SQLException ex){
			System.out.println(ex);
			assertTrue(0>1);
		}
	}

	public void testDeleteAttributesByObjectID() {
		
		int lines=-2;
		String line=DBOperationsManager.makeAttributeLine("X", "DBOperationsTest2", "MakeAttributeLine", "EN","C", "Junit testvalue");
		String line2=DBOperationsManager.makeAttributeLine("X", "DBOperationsTest2", "MakeAttributeLine2", "EN","C", "Junit testvalue");
		assertTrue(DBOperationsManager.executeSQLUpdate(con, line));
		assertTrue(DBOperationsManager.executeSQLUpdate(con, line2));
		lines=DBOperationsManager.deleteAttributesByObjectID(con,"DBOperationsTest2");
		assertTrue(lines!=-1);
		assertTrue(lines!=-2);
		assertTrue(lines==2);
	}

	public void testGetTaskLanguageVersions() {
		
		ResultSet rset=null;
		
		rset=DBOperationsManager.getTaskLanguageVersions(con, "TEST01");
		
		try{
			assertNotNull(rset);
			rset.next();
			assertEquals("EN", rset.getString("language"));
			rset.next();
			assertEquals("FI", rset.getString("language"));
		}
		catch(SQLException ex){
			System.out.println(ex);
			assertTrue(0>1);
		}
	}

	public void testGetAttributesByLanguage() {
		
		ResultSet rset=null;
		rset=DBOperationsManager.getAttributesByLanguage(con, "DBOperationsTest", "EN");
		try{
			assertNotNull(rset);
			rset.next();
			assertEquals("EN", rset.getString("language"));
		}
		catch(SQLException ex){
			System.out.println(ex);
			assertTrue(0>1);
		}
	}


	public void testDeleteAttribute() {
		
		String msg=null;
		
		assertTrue(DBOperationsManager.deleteAttribute(con, "X", "DBOperationsTest", "MakeAttributeLine", "EN"));
	}


}
