package fi.hy.eassari.tests;

import junit.framework.TestCase;
import fi.hy.eassari.showtask.trainer.Task;
import fi.hy.eassari.showtask.trainer.Tasktype;
import fi.hy.eassari.showtask.trainer.TaskBase;
import fi.hy.eassari.showtask.trainer.CacheException;
import fi.hy.eassari.showtask.trainer.DisplayerInterface;


/**
 * Test method class which contains methods to test DisplayerServlet 2's operations.
 * 
 * @author  Olli-Pekka Ruuskanen
 * @version 7.5.2004
 */

public class DisplayerServlet2Test extends TestCase {

	final    String dbDriver   = "oracle.jdbc.OracleDriver";
	final    String dbServer   = "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
  //final    String dbUser     = "assari";     	
  //final    String dbPassword = "opetus";
	final    String dbUser     = "opruuska";
	final    String dbPassword = "infolab"; 		
	Task     tsk               = null;
	TaskBase taskBase          = new TaskBase(dbDriver, dbServer, dbUser, dbPassword);
	
	
	
	/**
	 * Constructor for DisplayerServlet2Test.
	 * 
	 * @param arg0
	 */
	public DisplayerServlet2Test(String arg0) {
		super(arg0);
	}
	
	
	
	
	/**
	 * Method to test doPost method's operation. 
	 */
	
	public void testDoPost() {
		
		// Initialize return code as null:
		String retcode = null;

		// Option task should be found:
		try {
		tsk = taskBase.taskById (2, "democourse1", "demomodule1");
		}
		catch (CacheException ce) {;}
		retcode = tsk.getTaskID();
		assertTrue (retcode.equals("DEMO_OPR_2"));
	
		retcode = null;
	
		// Blankfill task should be found:
		try {
		tsk = taskBase.taskById (3, "democourse1", "demomodule1");
		}
		catch (CacheException ce) {;}
		retcode = tsk.getTaskID();
		assertTrue (retcode.equals("DEMO_OPR_3"));

		retcode = null;

		// Ordering task should be found:
		try {
		tsk = taskBase.taskById (4, "democourse1", "demomodule1");
		}
		catch (CacheException ce) {;}
		retcode = tsk.getTaskID();
		assertTrue (retcode.equals("DEMO_OPR_4"));
		
		retcode = null;

		// Task should not be found because of wrong sequence number:
		try {
		tsk = taskBase.taskById (0, "democourse1", "demomodule1");
		}
		catch (CacheException ce) {;}
		assertTrue (tsk == null);

		retcode = null;
	
		// Task should not be found because of wrong courseid:
		try {
		tsk = taskBase.taskById (2, "democourse", "demomodule1");
		}
		catch (CacheException ce) {;}
		assertTrue (tsk == null);

		retcode = null;

		// Task should not be found because of wrong moduleid:
		try {
		tsk = taskBase.taskById (2, "democourse1", "demomodule");
		}
		catch (CacheException ce) {;}
		assertTrue (tsk == null);
	}
	
	


	/**
	 * Method to test doGet method's operation. 
	 */
	
	public void testDoGet() {
	
		Tasktype           tType    = null;
		DisplayerInterface disp     = null;
		
		// Task for OptionDisplayer:
		try  {
			tsk   = taskBase.taskById (2, "democourse1", "demomodule1");
			tType = tsk.getTasktype(); 
		}
		catch (CacheException ce) {;}
		assertTrue (tType.getTypeName().equals("optiontask"));
		
		disp     = null;
		
		// Task for BlankfillDisplayer:
		try  {
			tsk   = taskBase.taskById (3, "democourse1", "demomodule1");
			tType = tsk.getTasktype(); 
		}
		catch (CacheException ce) {;}
		assertTrue (tType.getTypeName().equals("blankfilltask"));
		
		disp     = null;
		
		// Task for OrderDisplayer:
		try  {
			tsk   = taskBase.taskById (4, "democourse1", "demomodule1");
			tType = tsk.getTasktype(); 
		}
		catch (CacheException ce) {;}
		assertTrue (tType.getTypeName().equals("orderingtask"));

		disp     = null;

		// There shouldn't be an appropriate displayer for the task:
		try  {
			tsk   = taskBase.taskById (0, "democourse1", "demomodule1");
		}
		catch (CacheException ce) {;}
		assertTrue (tsk == null);
	}
}
