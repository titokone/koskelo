/*
 * Created on 20.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.taskdefinition.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fi.hy.eassari.taskdefinition.exception.TaskProcessingException;

/**
 * A class for checking if a user has permission to access a task.
 * 
 * @author tjvander
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Authorization {
	

	/**
	 * Checks if the user has permission to access the task.
	 * 
	 * @param userId Id of the user trying to access a task.
	 * @param taskId Id of the task being accessed.
	 * @return TRUE if the user has permission to acces the task, FALSE otherwise.
	 */
	public static boolean hasPermissions(String userId, String taskId){

		DatabaseBase dbase=new TaskDataBase();
		
		boolean permission=false;
		String query="select taskname from task where taskid='"+taskId+"' and author='"+userId+"'";
		try{
			Connection con=dbase.getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(query);
			if(rs.next())
				permission=true;
		}
		catch(SQLException ex){
			return false;
		}
		catch(TaskProcessingException tpe){
		}
		
		return permission;
	}


}


