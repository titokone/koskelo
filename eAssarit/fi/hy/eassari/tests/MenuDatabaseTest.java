/*
 * Created on 13.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.tests;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;
import fi.hy.eassari.taskdefinition.exception.TaskProcessingException;
import fi.hy.eassari.taskdefinition.util.DBOperationsManager;
import fi.hy.eassari.taskdefinition.util.TaskDataBase;
import fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO;

/**
 * Test class for TaskDataBase.
 * 
 * @author Sami Termonen
 *
 */
public class MenuDatabaseTest extends TestCase {

	TaskDataBase db = new TaskDataBase();
	private String taskId = null;
	
	/**
	 * Constructor for MenuDatabaseTest.
	 * @param arg0
	 */
	public MenuDatabaseTest(String arg0) {
		super(arg0);
	}

	public void testLoadData() throws TaskProcessingException{
		
		
		TaskDTO dto = db.loadData("TEST01", "FI");
		HashMap options = dto.getOptions();

		Set set = options.keySet();
		Iterator it = set.iterator();

		String key = null;
		String value = null;
			
		while(it.hasNext()){
			key = (String)it.next();
			value = (String)options.get(key);
		}
	}

	public void testSaveData() throws TaskProcessingException{
		
		TaskDTO dto=new TaskDTO();
		dto.setTaskName("MenuDatabaseTest");
		dto.setAuthor("Ohtuprojekti Assari");
		dto.setTaskType("optiontask");
		dto.setNumberOfTries(3);
		dto.setShouldStoreAnswer("Y");
		dto.setShouldRegisterTry("Y");
		dto.setShouldKnowStudent("N");
		dto.setImmediateFeedback("Y");
		dto.setCutOffValue(50);
		dto.setLanguage("FI");
		dto.set("task", "Kuka seuraavista ei asu Ankkalinnassa?");
		dto.set("option1", "Mikki Hiiri");
		dto.set("option2", "Batman");
		dto.set("option3", "Aku Ankka");
		dto.set("isselected1", "N");
		dto.set("isselected2", "Y");
		dto.set("isselected3", "N");
		dto.set("positivefeedback1", "Aivan, Mikki asuu Ankkalinnassa");
		dto.set("positivefeedback2", "Aivan, Batman ei asu Ankkalinnassa");
		dto.set("positivefeedback3", "Aivan, Aku asuu Ankkalinnassa");
		dto.set("negativefeedback1", "Tarkista tietosi ja yritä uudelleen");
		dto.set("negativefeedback2", "Taikaviitta hoitaa Batmanin hommat Ankkalinnassa.");
		dto.set("negativefeedback3", "Tarkista tietosi ja yritä uudelleen");
		
		db.saveData(dto);
		
		taskId = dto.getTaskId();
		
		dto=null;
		
		dto=db.loadData(taskId, "FI");
		assertEquals(taskId, dto.getTaskId());
		assertEquals("MenuDatabaseTest", dto.getTaskName());
		assertEquals("Ohtuprojekti Assari", dto.getAuthor());
		assertEquals("optiontask", dto.getTaskType());
		assertEquals(3, dto.getNumberOfTries());
		assertEquals("Y", dto.getShouldStoreAnswer());
		assertEquals("Y", dto.getShouldRegisterTry());
		assertEquals("N", dto.getShouldKnowStudent());
		assertEquals("Y", dto.getImmediateFeedback());
		assertEquals(50, dto.getCutOffValue());
		//assertEquals("FI",dto.getLanguage());
		assertEquals("Kuka seuraavista ei asu Ankkalinnassa?", dto.get("task"));
		assertEquals("Mikki Hiiri", dto.get("option1"));
		assertEquals("Batman", dto.get("option2"));
		assertEquals("Aku Ankka", dto.get("option3"));
		assertEquals("N", dto.get("isselected1"));
		assertEquals("Y", dto.get("isselected2"));
		assertEquals("N", dto.get("isselected3"));
		assertEquals("Aivan, Mikki asuu Ankkalinnassa", dto.get("positivefeedback1"));
		assertEquals("Aivan, Batman ei asu Ankkalinnassa", dto.get("positivefeedback2"));
		assertEquals("Aivan, Aku asuu Ankkalinnassa", dto.get("positivefeedback3"));
		assertEquals("Tarkista tietosi ja yritä uudelleen", dto.get("negativefeedback1"));
		assertEquals("Taikaviitta hoitaa Batmanin hommat Ankkalinnassa.", dto.get("negativefeedback2"));
		assertEquals("Tarkista tietosi ja yritä uudelleen", dto.get("negativefeedback3"));

		Connection con=db.getConnection();
		DBOperationsManager.deleteTaskData(con, taskId);
	}

	public void testUpdateData() throws TaskProcessingException{
		
		TaskDTO dto=new TaskDTO();
		dto.setTaskId("88");
		dto.setTaskName("MenuDatabaseTest");
		dto.setAuthor("Ohtuprojekti Assari");
		dto.setTaskType("multiplechoicetask");
		dto.setNumberOfTries(3);
		dto.setShouldStoreAnswer("N");
		dto.setShouldRegisterTry("Y");
		dto.setShouldKnowStudent("N");
		dto.setImmediateFeedback("Y");
		dto.setCutOffValue(70);
		dto.setLanguage("FI");
		dto.set("task", "Kuka seuraavista ei asu Ankkalinnassa?");
		dto.set("option1", "Hannu Hanhi");
		dto.set("option2", "Teräsmies");
		dto.set("option3", "Aku Ankka");
		dto.set("isselected1", "N");
		dto.set("isselected2", "Y");
		dto.set("isselected3", "N");
		dto.set("positivefeedback1", "Aivan, Hannu asuu Ankkalinnassa");
		dto.set("positivefeedback2", "Aivan, Teräsmies ei asu Ankkalinnassa");
		dto.set("positivefeedback3", "Aivan, Aku asuu Ankkalinnassa");
		dto.set("negativefeedback1", "Tarkista tietosi ja yritä uudelleen");
		dto.set("negativefeedback2", "Teräsmies asuu jossain muualla.");
		dto.set("negativefeedback3", "Lue akkarisi uudelleen.");
		
		db.updateData(dto);
		
		dto=null;
		
		dto=db.loadData("88", "FI");
		assertEquals("88", dto.getTaskId());
		assertEquals("MenuDatabaseTest", dto.getTaskName());
		assertEquals("Ohtuprojekti Assari", dto.getAuthor());
		assertEquals("optiontask", dto.getTaskType());
		assertEquals(3, dto.getNumberOfTries());
		assertEquals("N", dto.getShouldStoreAnswer());
		assertEquals("Y", dto.getShouldRegisterTry());
		assertEquals("N", dto.getShouldKnowStudent());
		assertEquals("Y", dto.getImmediateFeedback());
		assertEquals(70, dto.getCutOffValue());
		//assertEquals("FI",dto.getLanguage());
		assertEquals("Kuka seuraavista ei asu Ankkalinnassa?", dto.get("task"));
		assertEquals("Hannu Hanhi", dto.get("option1"));
		assertEquals("Teräsmies", dto.get("option2"));
		assertEquals("Aku Ankka", dto.get("option3"));
		assertEquals("N", dto.get("isselected1"));
		assertEquals("Y", dto.get("isselected2"));
		assertEquals("N", dto.get("isselected3"));
		assertEquals("Aivan, Hannu asuu Ankkalinnassa", dto.get("positivefeedback1"));
		assertEquals("Aivan, Teräsmies ei asu Ankkalinnassa", dto.get("positivefeedback2"));
		assertEquals("Aivan, Aku asuu Ankkalinnassa", dto.get("positivefeedback3"));
		assertEquals("Tarkista tietosi ja yritä uudelleen", dto.get("negativefeedback1"));
		assertEquals("Teräsmies asuu jossain muualla.", dto.get("negativefeedback2"));
		assertEquals("Lue akkarisi uudelleen.", dto.get("negativefeedback3"));
		
		//Clean up the database
		Connection con=db.getConnection();
		DBOperationsManager.deleteTaskData(con, taskId);
	}
}
