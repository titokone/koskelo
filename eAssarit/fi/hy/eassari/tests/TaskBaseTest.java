package fi.hy.eassari.tests;

import java.sql.*;
import java.util.Calendar;
import junit.framework.TestCase;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CacheException;
import fi.hy.eassari.showtask.trainer.TaskBase;

/**
 * Test method class which contains methods to test TaskBase's operation.
 * 
 * @author  Olli-Pekka Ruuskanen
 * @version 13.4.2004
 */

public class TaskBaseTest extends TestCase {


	final String dbDriver   = "oracle.jdbc.OracleDriver";
	final String dbServer   = "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
	final String dbUser     = "assari";     	
	final String dbPassword = "opetus"; 		
	AttributeCache cache    = null;
	



	/**
	 * Constructor for TaskBaseTest.
	 * @param arg0
	 */
	public TaskBaseTest(String arg0) throws CacheException, SQLException {
		super(arg0);
		try{
			this.cache = new TaskBase(dbDriver, dbServer, dbUser, dbPassword);
		}catch (Exception ex) {
			System.out.print ("VIRHE: " + ex.getMessage());
		}
		
		// Restore database to its' initial state before testing:
		restoreDatabase();
	}




	//public void testTaskBase() {
	//}




	/*
	 * Method to test that attribCount() method returns the correct number of 
	 * attributes meeting the search criteria.
	 */

	public void testAttribCount() throws CacheException, SQLException {
		
		int expected0 = 0;
		int expected1 = 1;
		int expected2 = 2;
		int expected3 = 3;
		int expected4 = 2;
		int expected5 = 4;
		int expected6 = 0;
		int expected7 = 0;
		
		// No attributes defined in swedish:
		int result0   = cache.attribCount ("TEST000", "option", "SE");
		
		// Only one attribute defined in finnish:
		int result1   = cache.attribCount ("TEST000", "option", "FI");
		
		// 2 attributes defined in english:
		int result2   = cache.attribCount ("TEST000", "option", "EN");
		
		// 2 attributes defined in finnish and 1 for all languages should
		// return 3 attributes in total:
		int result3   = cache.attribCount ("TEST00",  "option", "FI");
		
		int result4   = cache.attribCount ("TEST01",  "defaultvalue", "FI");
		int result5   = cache.attribCount ("TEST02",  "defaultvalue", "EN");
		int result6   = cache.attribCount ("TEST03",  "defaultvalue", "EN");
		int result7   = cache.attribCount ("TEST04",  "defaultvalue", "FI");
		
		assertTrue (result0 == expected0);
		assertTrue (result1 == expected1);
		assertTrue (result2 == expected2);
		assertTrue (result3 == expected3);
		assertTrue (result4 == expected4);
		assertTrue (result5 == expected5);
		assertTrue (result6 == expected6);
		assertTrue (result7 == expected7);
	}
	
	
	
	
	/*
	 * Method to test that taskType() method returns the correct task type 
	 * name for the task given as a parameter.
	 */
	/*
	public void testTasktype() throws CacheException, SQLException {
		
		String expected0 = null;
		String expected1 = "multiplechoicetask";
		String expected2 = "optiontask";
		String expected3 = "blankfilltask";
		String expected4 = "orderingtask";

		String result0   = cache.taskType ("TEST");
		String result1   = cache.taskType ("TEST01");
		String result2   = cache.taskType ("TEST02");		
		String result3   = cache.taskType ("TEST03");		
		String result4   = cache.taskType ("TEST04");
		
		assertTrue (result0.equals(expected0));
		assertTrue (result1.equals(expected1));
		assertTrue (result2.equals(expected2));
		assertTrue (result3.equals(expected3));
		assertTrue (result4.equals(expected4));
	}
	*/
	
	
	/*
	 * Method to test that getTaskID (String courseid, String moduleid, int seqno)
	 * returns correctly that task's id which is attached to the given parameters.
	 */

	public void testgetTaskID() throws CacheException {
		
		String expected1 = "TEST01";
		String expected2 = null;
		String expected3 = null;
		String expected4 = null;
		
		String result1   = cache.getTaskID("courseX1", "moduleX1", 101);
		String result2   = cache.getTaskID("courseX0", "moduleX1", 101);
		String result3   = cache.getTaskID("courseX1", "moduleX0", 101);
		String result4   = cache.getTaskID("courseX1", "moduleX1", 100);
		
		assertTrue (result1.equals(expected1));
		assertTrue (result2 == null);
		assertTrue (result3 == null);
		assertTrue (result4 == null);
	}
	
	
	
	
	/*
	* Method to test that getLanguage (String taskid, String language)
	* returns correctly true if the given task is defined in the given
	* language and otherwise false.
	*
	*/
	
	public void testlanguageDefined() throws CacheException {
		
		boolean expected1 = true;
		boolean expected2 = true;
		boolean expected3 = false;
		boolean expected4 = false;		// Non-existing taskid should return null;
		
		boolean result1   = cache.languageDefined("TEST01", "EN");
		boolean result2   = cache.languageDefined("TEST01", "FI");
		boolean result3   = cache.languageDefined("TEST01", "SE");
		boolean result4   = cache.languageDefined("TEST",   "FI");
		
		assertTrue (result1 == expected1);
		assertTrue (result2 == expected2);
		assertTrue (result3 == expected3);
		assertTrue (result4 == expected4);
	} 
	
	
	
	
	/*
	 * Method to test that answers are saved correctly in the database. 
	 */

	public void testSaveAnswer() throws CacheException, SQLException {

		String   today        = "";
		Calendar date         = java.util.Calendar.getInstance();
		
		today += date.get(java.util.Calendar.DAY_OF_MONTH) + ".";
		today += date.get(java.util.Calendar.MONTH)        + ".";
		today += date.get(java.util.Calendar.YEAR);          //+ " ";
		//today += date.get(java.util.Calendar.HOUR_OF_DAY)  + ".";
		//today += date.get(java.util.Calendar.MINUTE);		
			
			
		// Initialize return codes for different tests: 
		String retcode1 = null;
		String retcode2 = null;
		String retcode3 = null;
		String retcode4 = null;
		String retcode5 = null;
		String retcode6 = null;
				
		// OK:
		try {
			cache.saveAnswer ("studentX1", "courseX1", "moduleX1", 2, 2, 75, today, 
								 "Test save for Grandma Duck.", "EN");
		
		}
		// Exception should not happen and retcode1 should remain as null:
		catch (CacheException ce) {
			retcode1 = ce.getMessage(); 
		}
		
		// Remove previous entry to ascertain that saving is not language dependent:
		restoreDatabase();

		// OK:
		try {
			cache.saveAnswer ("studentX1", "courseX1", "moduleX1", 2, 2, 75, today, 
								 "Testitallennus tietokantaan Mummo-Ankalle.", "FI");
		
		}
		// Exception should not happen and retcode2 should remain as null:
		catch (CacheException ce) {
			retcode2 = ce.getMessage(); 
		}		

		// CACHE ERROR Repository failure: 
		// ORA-00001: yksikäsitteistä rajoitetta (ASSARI.SYS_C0041533) loukattu
		// i.e. answer with the same trynumber for the same student for the course/module/sequence
		// already saved in the database.
		try {
			cache.saveAnswer ("studentX1", "courseX1", "moduleX1", 2, 2, 75, today, 
								 "Testitallennus tietokantaan Mummo-Ankalle.", "FI");
		}
		// Trying to save same entry again should violate primary key's uniqueness requirement
		//  and cause an exception which sets retcode3 from null to "ORA-00001":
		catch (CacheException ce) {
			retcode3 = "ORA-00001";
		}
		
		// CACHE ERROR Repository failure: 
		// ORA-02291: eheysrajoitetta (ASSARI.SYS_C0041535) on rikottu; pääavainta ei ole löytynyt
		// i.e. given course/module/sequence not found in the database.
		try {
			cache.saveAnswer ("studentX1", "courseX", "moduleX1", 2, 2, 75, today, 
								 "Testitallennus tietokantaan Mummo-Ankalle.", "FI");
		}
		// Trying to save an answer for a non-existing course should violate primary key's existence 
		// requirement and cause an exception which sets retcode4 from null to "ORA-02291":  
		catch (CacheException ce) {
			retcode4 = "ORA-02291";
		}
		
		//CACHE ERROR Repository failure: 
		//ORA-02291: eheysrajoitetta (ASSARI.SYS_C0041535) on rikottu; pääavainta ei ole löytynyt
		// i.e. given student not found in the database. 
		try {
			cache.saveAnswer ("studentX", "courseX", "moduleX1", 2, 2, 75, today, 
								 "Testitallennus tietokantaan Mummo-Ankalle.", "FI");
		}
		// Trying to save an answer for a non-existing student should violate primary key's existence 
		// requirement and cause an exception which sets retcode5 from null to "ORA-02291": 
		catch (CacheException ce) {
			retcode5 = "ORA-02291";
		}

		//CACHE ERROR Repository failure: 
		//ORA-02291: eheysrajoitetta (ASSARI.SYS_C0041535) on rikottu; pääavainta ei ole löytynyt
		// i.e. given language not found for the task. 
		try {
			cache.saveAnswer ("studentX", "courseX", "moduleX1", 2, 2, 75, today, 
								 "Testitallennus tietokantaan Mummo-Ankalle.", "UK");
		}
		// Trying to save an answer in a non-existing language should violate primary key's existence 
		// requirement and cause an exception which sets retcode6 from null to "ORA-02291": 		
		catch (CacheException ce) {
			retcode6 = "ORA-02291";
		}

		assertTrue (retcode1 == null);
		assertTrue (retcode2 == null);
		assertTrue (retcode3.equals("ORA-00001"));
		assertTrue (retcode4.equals("ORA-02291"));
		assertTrue (retcode5.equals("ORA-02291"));		
		assertTrue (retcode6.equals("ORA-02291"));
	}

	
	
	
	
	/**
	 * Method to test that getAttribute() method returns the correct attribute value
	 * in the given language.
	 */

	public void testGetAttribute() throws CacheException, SQLException {
		
		String[] expected1 = new String[1]; expected1[0]= null;
		String[] expected2 = new String[1]; expected2[0]= "vaihtoehto 1";
		String[] expected3 = new String[2]; expected3[0]= "option 1"; expected3[1] = "option 2";	
		String[] expected4 = new String[3]; expected4[0]= "option 1"; expected4[1] = "option 2"; expected4[2] = "vaihtoehto-option 3";
		String[] expected5 = new String[1]; expected5[0]= "RADIO";
		
		String[] result1 = new String[1];
		String[] result2 = new String[1]; 
		String[] result3 = new String[2];
		String[] result4 = new String[3];
		String[] result5 = new String[1];
		
		for (int index = 1; index <= cache.attribCount("TEST000","option", "SE"); index++) {
				result1[index-1] = cache.getAttribute ("T", "TEST000", "option"+index, "SE");
		}
	
		for (int index = 1; index <= cache.attribCount("TEST000", "option", "FI"); index++) {
				result2[index-1] = cache.getAttribute ("T", "TEST000", "option"+index, "FI");
		}
			
		for (int index = 1; index <= cache.attribCount("TEST000", "option", "EN"); index++) {
				result3[index-1] = cache.getAttribute ("T", "TEST000", "option"+index, "EN");
		}
		
		for (int index = 1; index <= cache.attribCount("TEST00", "option", "EN"); index++) {
				result4[index-1] = cache.getAttribute ("T", "TEST00", "option"+index, "EN");
		}
	
		// "RADIO" is an attributevalue defined for all languages, therefore getAttribute() 
		// should return "1" also for norwegian, although there are no attributes defined in
		// norwegian in the database:
		for (int index = 1; index <= cache.attribCount("TEST02", "option", "NO"); index++) {
			result5[index-1] = cache.getAttribute ("T", "TEST02", "option"+index, "NO");		
		}
		
		for (int index = 0; index < cache.attribCount("TEST000", "option", "SE"); index++)
			assertTrue (result1[index].equals(expected1[index]));		
		
		for (int index = 0; index < cache.attribCount("TEST000", "option", "FI"); index++)
			assertTrue (result2[index].equals(expected2[index]));
		
		for (int index = 0; index < cache.attribCount("TEST000", "option", "EN"); index++)
			assertTrue (result3[index].equals(expected3[index]));

		for (int index = 0; index < cache.attribCount("TEST00", "option", "EN"); index++) 
			assertTrue (result4[index].equals(expected4[index]));
		
		for (int index = 0; index < cache.attribCount("TEST02", "option", "NO"); index++) 
			assertTrue (result5[index].equals(expected5[index]));
			
		deleteExtras();
	}
		
	
	
	
	
	/*
	 * Private method to restore database in its' original state to eliminate possible
	 * alterations' effects on test results.
	 */
	
	private void restoreDatabase () throws CacheException, SQLException {
		
		Connection con = getConnection();
		Statement  stm = null; 
		ResultSet  rs  = null;
		try {
			stm = con.createStatement();
			stm.addBatch("delete from task where (taskid='TEST00')");
			stm.addBatch("delete from task where (taskid='TEST000')");
			stm.addBatch("delete from attributevalues where (objectid='TEST00')");
			stm.addBatch("delete from attributevalues where (objectid='TEST000')");
			stm.addBatch("delete from storedanswer where (sid = 'studentX1')");
			stm.addBatch("insert into task values ('TEST00', 'TaskBase test', 'Olli-Pekka Ruuskanen', to_date('13.04.2004','DD.MM.YYYY'),'multiplechoicetask', null, 2, 'N','N','N','Y',70)");
			stm.addBatch("insert into task values ('TEST000', 'TaskBase test', 'Olli-Pekka Ruuskanen',to_date('13.04.2004','DD.MM.YYYY'),'multiplechoicetask', null, 2, 'N','N','N','Y',70)");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST000', 'task',   'EN', 'C','Ask something?')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST000', 'task',   'FI', 'C','Kysy jotain?')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST000', 'option1','EN', 'C','option 1')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST000', 'option1','FI', 'C','vaihtoehto 1')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST000', 'option2','EN', 'C','option 2')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST00',  'task',   'EN', 'C','Ask something?')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST00',  'task',   'FI', 'C','Kysy jotain?')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST00',  'option1','EN', 'C','option 1')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST00',  'option1','FI', 'C','vaihtoehto 1')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST00',  'option2','EN', 'C','option 2')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST00',  'option2','FI', 'C','vaihtoehto 2')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST00',  'option3','ALL','C','vaihtoehto-option 3')");
			stm.addBatch("insert into attributevalues values ( 'T', 'TEST02',  'option5','EN', 'C','RADIO')");
			stm.executeBatch();
			//rs = stm.executeQuery(delete);
			//stm = con.createStatement();
			//rs = stm.executeQuery(insert);
		}
		catch (SQLException ex) {
			  throw new SQLException ("Repository failure: " + ex.getMessage());
		}
		finally {
			try {
				if (rs!=null)  rs.close();
				if (stm!=null) stm.close();
				con.close();
			}
			catch (SQLException exc) {
				throw new SQLException ("Cannot close connection: " + exc.getMessage());
			}
		}
	}
	
	
	
	
	private void deleteExtras () throws CacheException, SQLException {
		
			Connection con = getConnection();
			Statement  stm = null; 
			ResultSet  rs  = null;
			try {
				stm = con.createStatement();
				stm.addBatch("delete from attributevalues where (taskid='TEST02' and language='EN' and attributename='option5')");
				stm.executeBatch();
				//rs = stm.executeQuery(delete);
				//stm = con.createStatement();
				//rs = stm.executeQuery(insert);
			}
			catch (SQLException ex) {
				  throw new SQLException ("Repository failure: " + ex.getMessage());
			}
			finally {
				try {
					if (rs!=null)  rs.close();
					if (stm!=null) stm.close();
					con.close();
				}
				catch (SQLException exc) {
					throw new SQLException ("Cannot close connection: " + exc.getMessage());
				}
			}
	}
		
		
		
	
	/*
	 * Private method to establish a database connection for database restoration. 
	 */
	
	private Connection getConnection () throws CacheException {
		
		// load database driver if not already loaded
		Connection conn = null;
		try { 
		  Class.forName(dbDriver);               // load driver
		//Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) { 
			 throw new CacheException ("Couldn't find the driver " + dbDriver);
		}
		try {
		   conn = DriverManager.getConnection(dbServer, dbUser, dbPassword);
		   
		} 
		catch (SQLException sex) {
		   throw new CacheException ("Couldn't establish repository connection: " + sex);
		}
		return conn;
   }
}
