/*
 * Created on 6.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.tests;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;
import fi.hy.eassari.taskdefinition.exception.TaskProcessingException;
import fi.hy.eassari.taskdefinition.util.AllTasksDataBase;
import fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO;

/**
 * Test class for AllTasksDataBase
 * 
 * @author Sami Termonen
 */
public class AllTasksTest extends TestCase {
	private static final String task1 = "TEST01";
	private static final String task2 = "TEST02";
	private static final String task3 = "TEST03";
	private static final String task4 = "TEST04";
	private static final String task5 = "SYSTEMTEST TASK2";
	private static final String task6 = "SYSTEMTEST TASK1";
	private static final String task7 = "23";
	private static final String task8 = "26";
	private static final String task9 = "24";
	private static final String task10 = "28";
	private static final String task11 = "27";
	private static final String task12 = "SYSTEMTEST TASK3";
	private static final String task13 = "43";
	private static final String task14 = "49";
	private static final String task15 = "50";
	private static final String task16 = "59";

	AllTasksDataBase task = new AllTasksDataBase();
	/**
	 * Constructor for AllTasksTest.
	 * @param arg0
	 */
	public AllTasksTest(String arg0) {
		super(arg0);
	}

	public void testGetAllAuthorTasks() throws TaskProcessingException{
		TaskDTO dto = new TaskDTO();
		dto.setAuthor("Teemu Andersson");
		dto.setLanguage("FI");
		
		Collection list = task.getAllAuthorTasks("Teemu Andersson", "FI");
		
		Iterator it = list.iterator();
		String taskId = null;
		int i = 1;
		HashMap map = new HashMap();
		
		while(it.hasNext()){
			TaskDTO dto2 = (TaskDTO)it.next();
			taskId = dto2.getTaskId();
			
			map.put(String.valueOf(i), taskId);	
			i++;
		}
		
		i = 1;
		
		assertEquals(task1, map.get("1"));
		assertEquals(task2, map.get("2"));
		assertEquals(task3, map.get("3"));
		assertEquals(task4, map.get("4"));
		assertEquals(task5, map.get("5"));
		assertEquals(task6, map.get("6"));
		assertEquals(task7, map.get("7"));
		assertEquals(task8, map.get("8"));
		assertEquals(task9, map.get("9"));
		assertEquals(task10, map.get("10"));
		assertEquals(task11, map.get("11"));
		assertEquals(task12, map.get("12"));
		assertEquals(task13, map.get("13"));
		assertEquals(task14, map.get("14"));
		assertEquals(task15, map.get("15"));
		assertEquals(task16, map.get("16"));		
	}

	public void testGetConnection() {
	}

	public void testExecuteQuery() {
	}

	public void testExecuteUpdate() {
	}

	/*
	 * Test for void closeConnection(Statement, Connection)
	 */
	public void testCloseConnectionStatementConnection() {
	}

	/*
	 * Test for void closeConnection(ResultSet, Statement, Connection)
	 */
	public void testCloseConnectionResultSetStatementConnection() {
	}

}
