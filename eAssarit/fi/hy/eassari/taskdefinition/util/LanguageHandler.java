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
import java.util.Collection;
import java.util.LinkedList;

import fi.hy.eassari.taskdefinition.exception.TaskProcessingException;
import fi.hy.eassari.taskdefinition.util.datastructures.LanguageDTO;

/**
 * A class for handling getting language info o system and tasks.
 * 
 * @author tjvander
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LanguageHandler {

	/**
	 * Returns all languages know to system.
	 * @return Collection containing LanguageDTO's containing the codes and names of languages known to system.
	 */
	public static Collection getSystemLanguages(){
		Collection col=new LinkedList();
		
		DatabaseBase dbase=new TaskDataBase();
		

		String query="select * from systemlanguages";
		try{
			Connection con=dbase.getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next())
				col.add(new LanguageDTO(rs.getString("languagecode"), rs.getString("languagename")));
		}
		catch(SQLException ex){
			return new LinkedList();
		}
		catch(TaskProcessingException tpe){
		}
		
		return col;
	}
	
	/**
	 * Returns the languages with which the task has versions saved to database.
	 * 
	 * @param taskId Id of the task for which to get the language info.
	 * @return Collection containing LanguageDTO's containing the codes and names of languages with which the task has versions saved to database.
	 */
	public static Collection getTaskLanguages(String taskId){
		Collection col=new LinkedList();		
		DatabaseBase dbase=new TaskDataBase();
		
	
		try{
			Connection con=dbase.getConnection();
			Connection con2=dbase.getConnection();
			ResultSet rs=DBOperationsManager.getTaskLanguageVersions(con, taskId);
			Statement stmt=con2.createStatement();
			while(rs.next()){
				ResultSet rs2=stmt.executeQuery("select languagename from systemlanguages where" +				" languagecode='"+rs.getString("language")+"'");
				if(rs2.next())
					col.add(new LanguageDTO(rs.getString("language"), rs2.getString("languagename")));
			}
		}
		catch(SQLException ex){
			System.out.println(ex);
			return new LinkedList();
		}
		catch(TaskProcessingException tpe){	
		}
		
		return col;
	}
}
