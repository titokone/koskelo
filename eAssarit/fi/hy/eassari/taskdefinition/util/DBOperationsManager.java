/*
 * Created on 24.3.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package fi.hy.eassari.taskdefinition.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A class for managing database operations specific to the project.
 * 
 * @author Teemu Andersson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DBOperationsManager {
	
/**
 * Creates SQL-line containing the information needed to save general task information to the database.
 * 
 * @param taskid Id of the task.
 * @param taskname Name of the task.
 * @param author Name of the tasks creator.
 * @param tasktype Type of the task.
 * @param taskmetadata Metadata concerning the task.
 * @param numberoftries Number of tries allowed for succesfull completion of the task.
 * @param storeanswer Should answers be stored.
 * @param registertry Should tries be registered.
 * @param knowstudent Should the system know the student attempting to do the task.
 * @param evaluate Should the given answer be evaluated immediately.
 * @param cutoffvalue The percentage of correctness that is needed for succesfull completion of the task.
 * @return String containing the SQL line needed to save general task information to the database.
 */
	public static String makeGeneralTaskDataLine(String taskid, String taskname, 
	String author, String tasktype,String taskmetadata, String numberoftries, String storeanswer,
	String registertry, String knowstudent, String evaluate, String cutoffvalue){

		String date=null;

		if(storeanswer == null){
			storeanswer = "N";
		}
		
		if(registertry == null){
			registertry = "N";
		}
		
		if(knowstudent == null){
			knowstudent = "N";
		}
		
		if(evaluate == null){
			evaluate = "N";
		}
		
		if(taskname == null){
			taskname = "";
		}
				
		java.util.Calendar rn= java.util.Calendar.getInstance();
		date=""+rn.get(java.util.Calendar.DAY_OF_MONTH)+".";
		date=date+(rn.get(java.util.Calendar.MONTH)+1)+".";
		date=date+rn.get(java.util.Calendar.YEAR);
	
		return("insert into task values('"+taskid+"','"+
			taskname+"','"+
			author+"',"+
			"to_date('"+date+"','DD.MM.YYYY'),'"+
			tasktype+"','"+
			taskmetadata+"',"+
			numberoftries+",'"+
			storeanswer+"','"+
			registertry+"','"+
			knowstudent+"','"+
			evaluate+"',"+
			cutoffvalue+")");
	}
	
/**
 * Deletes the task with the given taskID from database if the task is not a part of a module.
 * 
 * @param dbcon Database connection.
 * @param taskid Id of he task.
 * @return Number of rows deleted. -1 if SQLException occurs. -2 if the task is a part of a module.
 */
	public static int deleteTaskData(Connection dbcon, String taskid){

		ResultSet rset=null;
		Statement stmt=null;
		int[] counts;
			
		try{
			stmt=dbcon.createStatement();
			rset=stmt.executeQuery("select count(taskid) from taskinmodule where taskid='"+taskid+"'");
			rset.next();
			
			if(rset.getInt(1)!=0)//Checks that the task is not a part of a module.
				return -2;
				
			stmt.close();
			stmt=dbcon.createStatement();
			stmt.addBatch("delete from task where taskid='"+taskid+"'");
			stmt.addBatch("delete from attributevalues where objectid='"+taskid+"'");
			counts=stmt.executeBatch();
		}
		catch(SQLException ex){
			return -1;
		}
		return (counts[0]+counts[1]);
	}

	/**
	 * Deletes the task with the given taskID and language from database if the task is not a part of a module.
	 * 
	 * @param dbcon Database connection.
	 * @param taskid Id of he task.
	 * @param language language which needs to be deleted
	 * @return Number of rows deleted. -1 if SQLException occurs. -2 if the task is a part of a module.
	 */
	public static int deleteTaskDataByLanguage(Connection dbcon, String taskid, String language){

		ResultSet rset=null;
		Statement stmt=null;
		int[] counts;
		
		try{
			stmt=dbcon.createStatement();
			rset=stmt.executeQuery("select count(taskid) from taskinmodule where taskid='"+taskid+"'");
			rset.next();
		
			if(rset.getInt(1)!=0)//Checks that the task is not a part of a module.
				return -2;
			
			stmt.close();
			stmt=dbcon.createStatement();
			stmt.addBatch("delete from task where taskid='"+taskid+"'");
			stmt.addBatch("delete from attributevalues where objectid='"+taskid+"' and language='" + language + "'");
			counts=stmt.executeBatch();
		}
		catch(SQLException ex){
			return -1;
		}
		return (counts[0]+counts[1]);
	}

/**
 * Creates SQL-line containing the information needed to update general task information to the database.
 * 
 * @param taskid Id of the task.
 * @param numberoftries Number of tries allowed for succesfull completion of the task.
 * @param storeanswer Should answers be stored.
 * @param registertry Should tries be registered.
 * @param knowstudent Should the system know the student attempting to do the task.
 * @param evaluate Should the given answer be evaluated immediately.
 * @param cutoffvalue The percentage of correctness that is needed for succesfull completion of the task.
 * @return String containing the SQL line needed to update general task information to the database.
 */
	
	public static String makeGeneralTaskDataUpdateLine(String taskid, 
	 String numberoftries, String storeanswer,
	String registertry, String knowstudent, String evaluate, String cutoffvalue){

		if(storeanswer == null){
			storeanswer = "N";
		}
		
		if(registertry == null){
			registertry = "N";
		}
		
		if(knowstudent == null){
			knowstudent = "N";
		}
		
		if(evaluate == null){
			evaluate = "N";
		}

		return("update task set numberoftries_def="+numberoftries+","+
		"shouldstoreanswer_def='"+storeanswer+"',"+
		"shouldregistertry_def='"+registertry+"',"+
		"shouldknowstudent_def='"+knowstudent+"',"+
		"shouldevaluate_def='"+evaluate+"',"+
		"cutoffvalue="+cutoffvalue+" where taskid='"+taskid+"'");
	}
			
/**
 * Fetches general information concernig the task with the given taskID from database.
 * 
 * @param dbcon Database connection.
 * @param taskid Id of he task.
 * @return ResultSet containing the tasks general information. Null if SQLException occurs.
 */
	public static ResultSet getGeneralTaskData(Connection dbcon, String taskid){

		Statement stmt=null;
		ResultSet rset=null;
		
		try{
			stmt=dbcon.createStatement();
			rset=stmt.executeQuery("select * from task where taskid='"+taskid+"'");
		}
		catch(SQLException ex){
			return rset=null;
		}
		return rset;
	}	
/**
 * Creates SQL-line containing the information needed to save attribute to the database.
 * 
 * @param objecttype Type of object the attribute is attached to.
 * @param objectid Id of the object.
 * @param attributename Name of the attribute.
 * @param language Langugage code of the attribute.
 * @param valuetype Type of value of the attribute.
 * @param attributevalue Value of the attribute
 * @return String containing the SQL line needed to save general attribute to the database.
 */
	public static String makeAttributeLine( String objecttype, String objectid,
	String attributename, String language, String valuetype, String attributevalue ){		
		return("insert into attributevalues values ('"+objecttype+"','"+
			objectid+"','"+
			attributename+"','"+
			language+"','"+
			valuetype+"','"+
			attributevalue+"')" );
	}

/**
 * Creates SQL-line containing the information needed to save attribute to the database.
 * 
 * @param objecttype Type of object the attribute is attached to.
 * @param objectid Id of the object.
 * @param attributename Name of the attribute.
 * @param language Langugage code of the attribute.
 * @param valuetype Type of value of the attribute.
 * @param attributevalue Value of the attribute
 * @return String containing the SQL line needed to save general attribute to the database.
 */
	public static String makeAttributeUpdateLine( String objecttype, String objectid,
	String attributename, String language, String valuetype, String attributevalue ){
	
		return("update attributevalues set attributevalue='"+attributevalue+
		"' where objecttype='"+objecttype+"' AND " +		"objectid='"+objectid+"' AND " +		"attributename='"+attributename+"' AND " +		"language='"+language+"'");
	}

/**
 * Executes the SQL update given as parameter.
 * @param dbcon Connection to the database.
 * @param line The line to e executed.
 * @return TRUE if the operation is succesfull. FALSE if SQLException occurs
 */
	public static boolean executeSQLUpdate(Connection dbcon, String line){
		
		Statement stmt=null;
		
		try{
			stmt=dbcon.createStatement();
			stmt.executeUpdate(line);
		}
		catch(SQLException ex){
			return false;
		}
		return true;
	}
/**
 * Deletes the object with the given parameters from the database.
 * 
 * @param dbcon Database connection.
 * @param objecttype Type of object the attribute is attached to.
 * @param objectid Id of the object.
 * @param attributename Name of the attribute.
 * @param language Language code of the object.
 * @return TRUE if the operation is succesfull. FALSE if SQLException occurs.
 */
	public static boolean deleteAttribute(Connection dbcon, String objecttype, String objectid,
	String attributename, String language){

		Statement stmt=null;
		
		try{
			stmt=dbcon.createStatement();
			stmt.executeUpdate("delete from attributevalues where objecttype='"+objecttype+"' AND "+
			"objectid='"+objectid+"' AND "+
			"attributename='"+attributename+"' AND "+
			"language='"+language+"'");
		}
		catch(SQLException ex){
			return false;
		}
		return true;
	}

/**
 * Fetches the attibute value and the values type for the attribute with the given parameters from the database.
 * 
 * @param dbcon Database connection.
 * @param objecttype Type of the object the attribute is attached to.
 * @param objectid Id of the object.
 * @param attributename Name of the attribute.
 * @param language Language code of the attribute.
 * @return ResultSet containing the attributes value (label attributevalue) and values type (label valuetype).  Null if SQLException occurs.
 */
	public static ResultSet getAttributevalue(Connection dbcon, String objecttype, String objectid,
	String attributename, String language){

		Statement stmt=null;
		ResultSet rset=null;
		
		try{
			stmt=dbcon.createStatement();
			rset=stmt.executeQuery("select valuetype, attributevalue from attributevalues where objecttype='"+objecttype+"' AND "+
			"objectid='"+objectid+"' AND "+
			"attributename='"+attributename+"' AND "+
			"language='"+language+"'");
		}
		catch(SQLException ex){
			return rset=null;
		}
		return rset;
	}
	
/**
 * Deletes all attributes with given objectID from database.
 * 
 * @param dbcon Databse connection.
 * @param objectid Id of the object to have it's attributes removed from the database.
 * @return Number of rows deleted. -1 if SQLException occurs.
 */
	public static int deleteAttributesByObjectID(Connection dbcon, String objectid){
		
		Statement stmt=null;
		int lines=-1;
		try{
			stmt=dbcon.createStatement();
			lines=stmt.executeUpdate("delete from attributevalues where (objectid='"+objectid+"')");
		}
		catch(SQLException ex){
			return -1;
		}
		return lines;
	}

	/**
	 * Deletes all attributes with given objectID from database.
	 * 
	 * @param dbcon Databse connection.
	 * @param objectid Id of the object to have it's attributes removed from the database.
	 * @param language language which attributes needs to be deleted.
	 * @return Number of rows deleted. -1 if SQLException occurs.
	 */
	public static int deleteAttributesByObjectIDAndLanguage(Connection dbcon, String objectid, String language){
		Statement stmt=null;
		int lines=-1;
		try{
			stmt=dbcon.createStatement();
			lines=stmt.executeUpdate("delete from attributevalues where (objectid='"+objectid+"') and language='" + language + "'");
		}
		catch(SQLException ex){
			return -1;
		}
		return lines;
	}

/**
 * Fetches from the database the names and taskid's of all the tasks created by the given author.
 * 
 * @param dbcon Database connection.
 * @param author Name of author.
 * @return ResultSet containing the names of all the tasks by the given author (labeled taskname) and taskids (labeld taskid).  Null if SQLException occurs.
 */
	public static ResultSet getAuthorTasks(Connection dbcon, String author){

		Statement stmt=null;
		ResultSet rset=null;
		
		try{
			stmt=dbcon.createStatement();
			rset=stmt.executeQuery("select taskname, taskid from task where author='"+author+"'");
			
		}
		catch(SQLException ex){
			return null;
		}
		return rset;
	}

/**
 * Fethes from the database the language associated with the taskID. This is done by fethcing the 
 * language codes of the tasks attribute with the attribute name task.
 * 
 * @param dbcon Database connection.
 * @param taskid Id of the task
 * @return ResultSet containing the language codes.  Null if SQLException occurs.
 */	
	public static ResultSet getTaskLanguageVersions(Connection dbcon, String taskid){
		
		Statement stmt=null;
		ResultSet rset=null;
		
		try{
			stmt=dbcon.createStatement();
			rset=stmt.executeQuery("select distinct language from attributevalues where "+
			"objectid='"+taskid+"' AND attributename='task' ORDER BY language");
			
		}
		catch(SQLException ex){
			return rset=null;
		}
		return rset;
	}

/**
 * Fetches from the database all the attributes on the given language for the object with the given objectid.
 * 
 * @param dbcon Database connection.
 * @param objectid Id of the object.
 * @param language Language code for the language that is wanted.
 * @return ResultSet containing all the attributes on the given language for the object.
 */
	public static ResultSet getAttributesByLanguage(Connection dbcon, String objectid,
	String language){

		Statement stmt=null;
		ResultSet rset=null;
		
		try{
			stmt=dbcon.createStatement();
			rset=stmt.executeQuery("select * from attributevalues where objectid='"+objectid+"' "+
			"AND language='"+language+"'");
		}
		catch(SQLException ex){
			return rset=null;
		}
		return rset;
	}

/**
 * Counts the number of tasks the author has in the database. Designed to be used with author name to generate taskid's.
 * 
 * @param author Author of the task.
 * @param dbcon Database connection.
 * @return The number of tasks the author has.
 */
	private static int getTaskCount(String author, Connection dbcon){

		Statement stmt=null;
		int count=0;

		try{
		
			stmt=dbcon.createStatement();
			ResultSet rs=stmt.executeQuery("select count(taskname) from task where author="+author);
			count=rs.getInt(1);
		}
		catch (SQLException ex){
			count=-1;
			return count;
		}
		return count;
	}
	
}// End of class DBOperationsManager
