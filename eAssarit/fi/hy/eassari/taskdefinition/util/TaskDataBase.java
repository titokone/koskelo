/*
 * Created on Mar 24, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.taskdefinition.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import fi.hy.eassari.taskdefinition.exception.TaskProcessingException;
import fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO;

/**
 * This class is used for database operations in the task definition part of the 
 * software.
 * 
 * @author Sami Termonen
 */
public class TaskDataBase extends DatabaseBase {
	/**
	 * Default constructor for database superclass
	 */
	public TaskDataBase(){
		super();
	}

	/**
	 * Initiliazes db parameters with the given values.
	 * @param dbDriver
	 * @param dbUrl
	 * @param dbUser
	 * @param dbPassword
	 */
	public TaskDataBase(String dbDriver, String dbUrl, String dbUser, String dbPassword){
		super(dbDriver, dbUrl, dbUser, dbPassword);
	}	

	/**
	 * Implementation of abstract method of DatabaseBase. Loads task data from the database.
	 * @param taskId taskID
	 * @param language language
	 * @return TaskDTO object
	 */
	public TaskDTO loadData(String taskId, String language) throws TaskProcessingException{
		TaskDTO dto = new TaskDTO();	
		//ChoiceTaskDTO sourceDto = (ChoiceTaskDTO)srcDto;

		Connection con = null;
		Statement stmt = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		int i = 0;
		String key = null;
		String value = null;
		
		dto.setTaskId(taskId);
		dto.setLanguage(language);
						
		try{
			con = getConnection();

			rs1 = DBOperationsManager.getGeneralTaskData(con, String.valueOf(taskId));

			if(rs1.next()){			
				dto.setAuthor(rs1.getString("author"));
				dto.setDateCreated(rs1.getDate("datecreated"));
				if(rs1.getString("taskname") == null){
					dto.setTaskName("");	
				}
				else{
					dto.setTaskName(rs1.getString("taskname"));
				}
				//dto.setLanguage(rs1.getString("language"));
				//dto.setTask(rs1.getString("task"));
				dto.setTaskType(rs1.getString("tasktype"));
				dto.setCutOffValue(rs1.getInt("cutoffvalue"));
				dto.setNumberOfTries(rs1.getInt("numberoftries_def"));
				dto.setImmediateFeedback(rs1.getString("shouldevaluate_def"));
				dto.setShouldRegisterTry(rs1.getString("shouldregistertry_def"));
				dto.setShouldStoreAnswer(rs1.getString("shouldstoreanswer_def"));
				dto.setShouldKnowStudent(rs1.getString("shouldknowstudent_def"));
			}

			rs2 = DBOperationsManager.getAttributesByLanguage(con, String.valueOf(taskId), language);

			while(rs2.next()){
				key = rs2.getString("attributename"); 
				value = rs2.getString("attributevalue");
				
				if(value == null){
					value = "";
				}

				dto.set(key, value);
			}
			
			rs2.close();

			rs2 = DBOperationsManager.getAttributesByLanguage(con, String.valueOf(taskId), "ALL");

			while(rs2.next()){	
				key = rs2.getString("attributename"); 
				value = rs2.getString("attributevalue");

				if(value == null){
					value = "";
				}
				
				dto.set(key, value);
			}
						
			rs2.close();
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
				
		return dto;
	}

	 /**
	  * Saves data to the database.
	  * @param dataToSave data to save to the database
	  * @return int
	  */
	public int saveData(TaskDTO dataToSave) throws TaskProcessingException{
		Connection con = null;
		Statement stmt = null;	
		int i = 0;
		boolean isUpdateTask = false;
		
		String taskId = dataToSave.getTaskId();
		
		if(taskId == null || taskId.equals("")){
			//taskId = "" + getNextTaskId();
			taskId = "" + getNextTaskIdOracle();
			dataToSave.setTaskId(taskId);
		}
		else{
			if(dataToSave.isSaveAsNew()){
				dataToSave.setTaskId("" + getNextTaskIdOracle());
			}
			else if(dataToSave.isSaveAsNewLanguage()){
				isUpdateTask = true;
			}
			else {
				i = updateData(dataToSave);
				return i;
			}
		}

		try{
			con = getConnection();
			
			con.setAutoCommit(false);
			
			stmt = con.createStatement();
			String sql = null;
			
			if(isUpdateTask){
				sql = DBOperationsManager.makeGeneralTaskDataUpdateLine(dataToSave.getTaskId(), String.valueOf(dataToSave.getNumberOfTries()),
				dataToSave.getShouldStoreAnswer(),
				dataToSave.getShouldRegisterTry(), dataToSave.getShouldKnowStudent(), dataToSave.getImmediateFeedback(), String.valueOf(dataToSave.getCutOffValue()));
			}
			else{
				sql = DBOperationsManager.makeGeneralTaskDataLine(dataToSave.getTaskId(), dataToSave.getTaskName(), 
				dataToSave.getAuthor(), dataToSave.getTaskType(), null, String.valueOf(dataToSave.getNumberOfTries()), dataToSave.getShouldStoreAnswer(),
				dataToSave.getShouldRegisterTry(), dataToSave.getShouldKnowStudent(), "Y", String.valueOf(dataToSave.getCutOffValue()));
			}
									
			i = stmt.executeUpdate(sql);			

			HashMap map = dataToSave.getOptions();
			Set set = map.keySet();
			Iterator it = set.iterator();
			String key = null;
			String value = null;
			
			DBOperationsManager.deleteAttributesByObjectIDAndLanguage(con, dataToSave.getTaskId(), dataToSave.getLanguage());
			
			while(it.hasNext()){
				key = (String)it.next();
				value = (String)map.get(key);
				
				sql = DBOperationsManager.makeAttributeLine("T", dataToSave.getTaskId(), key, dataToSave.getLanguage(), "C", value);		
				stmt.executeUpdate(sql);
			}
			
			con.commit();
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
		
		return i;
	}

	/**
	 * Updates task data to the database.
	 * @param dataToUpdate task data to update.
	 * @param deleteOldData indicates whether the old task data needs to be deleted
	 * @return int number of task updated.
	 */
	public int updateData(TaskDTO dataToUpdate) throws TaskProcessingException{
		Connection con = null;
		Statement stmt = null;
		String taskId = dataToUpdate.getTaskId();
		int i = 0;
		
		try{
			con = getConnection();
			con.setAutoCommit(false);
			
			stmt = con.createStatement();

			String sql = null;

			sql = DBOperationsManager.makeGeneralTaskDataUpdateLine(dataToUpdate.getTaskId(), String.valueOf(dataToUpdate.getNumberOfTries()),
			dataToUpdate.getShouldStoreAnswer(),
			dataToUpdate.getShouldRegisterTry(), dataToUpdate.getShouldKnowStudent(), dataToUpdate.getImmediateFeedback(), String.valueOf(dataToUpdate.getCutOffValue()));
									
			i = stmt.executeUpdate(sql);			

			DBOperationsManager.deleteAttributesByObjectIDAndLanguage(con, dataToUpdate.getTaskId(), dataToUpdate.getLanguage());
			
			HashMap map = dataToUpdate.getOptions();
			Set set = map.keySet();
			Iterator it = set.iterator();
			String key = null;
			String value = null;
			
			while(it.hasNext()){
				key = (String)it.next();
				value = (String)map.get(key);
				
				sql = DBOperationsManager.makeAttributeLine("T", dataToUpdate.getTaskId(), key, dataToUpdate.getLanguage(), "C", value);		
				stmt.executeUpdate(sql);
			}

			con.commit();
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
	
		return i;
	}

	public void deleteTaskData(String taskId) throws TaskProcessingException{
		Connection con = null;
		Statement stmt = null;
		
		try{
			con = getConnection();
			DBOperationsManager.deleteTaskData(con, taskId);
		}
		catch(Throwable throwable){
			throw new TaskProcessingException("General failure in database classes. " + throwable);
		}
		finally{
			closeConnection(stmt, con);
		}		
	}
	
	/**
	 * Gets the next available taskid. This is done so that from the task table max value
	 * of the taskid is retrieved.
	 * 
	 * @return long new taskid
	 */
	private long getNextTaskId() throws TaskProcessingException{
		long id = 1;
		Connection con = null;
		Statement stmt = null;
		String taskid = null;
		
		try{
			String sql = "select max(taskid) from task";
			con = getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				taskid = rs.getString(1);
				id = Long.parseLong(taskid);
				id++;		
			}
		}
		catch(SQLException se){		
			throw new TaskProcessingException("Database connection failure. " + se);
		}
		catch(Throwable throwable){
			throw new TaskProcessingException("General failure in database classes. " + throwable);
		}
		
		return id;
	}

	/**
	 * Gets the next available taskid. This is oracle specific method.
	 * 
	 * @return long new taskid
	 */	
	private long getNextTaskIdOracle() throws TaskProcessingException{
		long id = 1;
		Connection con = null;
		Statement stmt = null;
		String taskid = null;
		
		try{
			String sql = "select taskid_seq.nextval from sys.dual";
			con = getConnection();
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				taskid = rs.getString(1);
				id = Long.parseLong(taskid);
				id++;		
			}
		}
		catch(SQLException se){		
			throw new TaskProcessingException("Database connection failure. " + se);
		}
		catch(Throwable throwable){
			throw new TaskProcessingException("General failure in database classes. " + throwable);
		}
		
		return id;
	}
}
