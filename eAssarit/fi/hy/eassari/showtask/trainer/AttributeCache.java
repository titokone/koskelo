/*
 * AttributeCache.java
 *
 * Created on 8. lokakuuta 2003, 10:04
 */

package fi.hy.eassari.showtask.trainer;
import java.sql.*;

/**
 * This interface defines the methods for getting attribute values from the cache
 * and passing answers to tasks to it.
 * 
 * @author  Harri Laine (Modified by Olli-Pekka Ruuskanen)
 * @version 5.4.2004
 */
public interface AttributeCache {

	/****
	 * provides the value in the specified language for the given attribute
	 * Params:
	 *   objType = type of object (T=task, D= displayer, E=Error, A=analyser, C=Course)
	 *   objID= key used in fetching value from hash map:
	 *          full task id, class name, error name, courseid
	 *   attributename = name of attribute
	 *   language = (FI,EN, language independent values may be obtained with whatever language attribute)
	 */  
    
	public String getAttribute (String objType, String objID, String attributename, String language) throws CacheException;
	
	
	// Methods added by OPR:
	
	/*
	 * Returns the number of language specific and language independent attributes of a certain task
	 * in desired language.
	 */
	
	public int attribCount(String objectid, String attributename, String language) throws CacheException, SQLException;
	
	
	
	/*
	 * Saves answers to tasks to the database.
	 */
	
	public void saveAnswer(String userid, String courseid, String moduleid,
						   int seqno, int trynumber, int correctness, String whenanswered, 
						   String answer, String language) 
						   throws CacheException;
    
/*	NOT USED 
    
	/*
	 * Returns tasktype corresponding to task given as parameter. 
	 */
/*
	public String taskType (String taskid)
							throws CacheException;
*/							
							
	/*
	 * Returns taskid corresponding to a given taskinmodule table's primary key.
	 */
							
	public String getTaskID (String courseid, String moduleid, int seqno)
								throws CacheException;	
								
								
	/*
	 * Returns true if the given task is defined in the given language,
	 * returns otherwise false. 
	 */
	 								
	public boolean languageDefined (String taskid, String language)
									throws CacheException;												

}