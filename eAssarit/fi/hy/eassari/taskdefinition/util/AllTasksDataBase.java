/*
 * Created on 6.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.taskdefinition.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import fi.hy.eassari.taskdefinition.exception.TaskProcessingException;
import fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO;

/**
 * Retrieves all tasks for the given author.
 * 
 * @author Sami Termonen
 */
public class AllTasksDataBase extends DatabaseBase{
	/**
	 * Default constructor for database superclass
	 */
	public AllTasksDataBase(){
		super();
	}

	/**
	 * Initiliazes db parameters with the given values.
	 * @param dbDriver
	 * @param dbUrl
	 * @param dbUser
	 * @param dbPassword
	 */
	public AllTasksDataBase(String dbDriver, String dbUrl, String dbUser, String dbPassword){
		super(dbDriver, dbUrl, dbUser, dbPassword);
	}

	/* (non-Javadoc)
	 * @see fi.hy.eassari.taskdefinition.util.DatabaseBase#loadData(fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO)
	 */
	public TaskDTO loadData(String taskId, String language) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fi.hy.eassari.taskdefinition.util.DatabaseBase#saveData(fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO)
	 */
	public int saveData(TaskDTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Gets all author tasks. Used in the start view.
	 * @param author author whose task has to be retrieved.
	 * @param language language of the tasks.
	 * @return Collection of TaskDTO objects.
	 */
	public Collection getAllAuthorTasks(String author, String language) throws TaskProcessingException{
		ArrayList list = new ArrayList();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		int i = 0;
		String key = null;
		String value = null;
		TaskDTO dto = null;
		String taskId = null;
						
		try{
			con = getConnection();

			rs1 = DBOperationsManager.getAuthorTasks(con, author);

			while(rs1.next()){			
				taskId = rs1.getString("taskid");
				
				/*
				if(taskId.equalsIgnoreCase("SYSTEMTEST TASK1") || 
				   taskId.equalsIgnoreCase("SYSTEMTEST TASK2")){
					continue;   	
				}
				*/
				
				rs2 = DBOperationsManager.getGeneralTaskData(con, taskId);
				dto = new TaskDTO();
				
				if(rs2.next()){
					dto.setTaskId(taskId);
					dto.setAuthor(rs2.getString("author"));
					dto.setDateCreated(rs2.getDate("datecreated"));
					
					if(rs2.getString("taskname") == null){
						dto.setTaskName("");
					}
					else{	
						dto.setTaskName(rs2.getString("taskname"));
					}
					
					//dto.setTask(rs2.getString("task"));
					dto.setTaskType(rs2.getString("tasktype"));
					dto.setCutOffValue(rs2.getInt("cutoffvalue"));
					dto.setImmediateFeedback(rs2.getString("shouldevaluate_def"));
					dto.setShouldRegisterTry(rs2.getString("shouldregistertry_def"));
					dto.setShouldStoreAnswer(rs2.getString("shouldstoreanswer_def"));
					dto.setShouldKnowStudent(rs2.getString("shouldknowstudent_def"));

					rs3 = DBOperationsManager.getAttributesByLanguage(con, taskId, language);

					while(rs3.next()){
						key = rs3.getString("attributename"); 
						value = rs3.getString("attributevalue");
						
						if(value == null){
							value = "";
						}
						
						if(key.equalsIgnoreCase("task")){
							dto.set(key, value);
						}
					}
					
					rs3.close();
				}
				
				rs2.close();

				list.add(dto);
			}
				
			rs1.close();
		}
		catch(SQLException se){		
			throw new TaskProcessingException("Database connection failure. " + se);
		}
		catch(Throwable throwable){
			throw new TaskProcessingException("General failure in database classes. " + throwable);
		}
		finally{
			closeConnection(stmt, con);
		}
		// Laita DTO:hon
						
		return list;
	}
	/* (non-Javadoc)
	 * @see fi.hy.eassari.taskdefinition.util.DatabaseBase#updateData(fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO)
	 */
	public int updateData(TaskDTO dataToUpdate) {
		// TODO Auto-generated method stub
		return 0;
	}
}
