 /*
 * Created on 23.3.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.taskdefinition.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fi.hy.eassari.taskdefinition.exception.TaskProcessingException;
import fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO;

/**
 * Super class for all database classes. Contains methods for connecting database and
 * executing queries.
 * 
 * @author Sami Termonen, Mikko Lukkari
 */
public abstract class DatabaseBase {
	//public class AssariTesti extends HttpServlet {
 
   	protected String dbDriver="oracle.jdbc.OracleDriver";
   	protected String dbServer= "jdbc:oracle:thin:@bodbacka.cs.helsinki.fi:1521:test";
 
   	protected String dbUser= "assari";     // replace with your db user account
   	protected String dbPassword ="opetus"; // replace with your password

	/**
	 * Default constructor for database superclass
	 */
	public DatabaseBase(){
	}

	/**
	 * Initiliazes db parameters with the given values.
	 * @param dbDriver
	 * @param dbUrl
	 * @param dbUser
	 * @param dbPassword
	 */
	public DatabaseBase(String dbDriver, String dbUrl, String dbUser, String dbPassword){
		this.dbDriver = dbDriver;
		this.dbServer = dbUrl;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;	
	}

	/**
	 * Initializes a connection to a database.
	 *  
	 * @return Connection an Connection object.
	 */
	public Connection getConnection(/*boolean autoCommit*/) throws TaskProcessingException{
		Connection con = createDbConnection(dbDriver, dbServer, dbUser, dbPassword);

		return con;
	}

	/**
	 * Executes a sql statement (query).
	 * 
	 * @param sql statement to execute
	 * @return ResultSet a ResultSet object
	 * @throws SQLException if an error occurs
	 */
	/*
	public ResultSet executeQuery(String sql) throws SQLException{
		Connection con = getConnection();
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		// Voiko tätä tehdä vielä tässä 
		closeConnection(stmt, con);
		
		return rs; 
	}
	*/
	/**
	 * Executes a sql statement (update).
	 * 
	 * @param sql statement to execute
	 * @return ResultSet a ResultSet object
	 * @throws SQLException if an error occurs
	 */	
	/*
	public int executeUpdate(String sql) throws SQLException{
		Connection con = getConnection();
		
		Statement stmt = con.createStatement();
		int i = stmt.executeUpdate(sql); 
		
		// Voiko tätä tehdä vielä tässä
		closeConnection(stmt, con);
		
		return i;		
	}
	*/
	/**
	 * Helper method for creating db connection.
	 * 
	 * @param dbDriver driver to use
	 * @param dbServer server url
	 * @param dbUser username
	 * @param dbPassword password
	 * @return Connection a Connection object
	 * @throws TaskProcessingException if driver is not found or connection failure happens
	 */
	private Connection createDbConnection(
		String dbDriver, String dbServer, String dbUser, String dbPassword)  throws TaskProcessingException{
	
	 // establish a database connection
		try{ 
			Class.forName(dbDriver);               // load driver
		} catch (ClassNotFoundException e) { 
			throw new TaskProcessingException("Driver not found. " + e);
		}
		Connection con=null;
		try {
		   con = DriverManager.getConnection(dbServer,dbUser,dbPassword); 
		} 
		catch (SQLException se) {
			throw new TaskProcessingException("Database connection error. " + se);
		}

		return con;
	}
	
	/**
	 * Closes db connection.
	 * 
	 * @param stmt statement to close
	 * @param con connection to close
	 */
	public void closeConnection(Statement stmt, Connection con) {
	   // close database connection
		try {
			if(stmt != null){
		   		stmt.close();	 
			}
		   
			if(con != null){
				con.close();
			}
	   }
	   catch (SQLException e) {}
	}
	
	/**
	 * Closes db connection.
	 * 
	 * @param rs ResultSet to close
	 * @param stmt Statement to close
	 * @param con Conncetion to close
	 */
	public void closeConnection(ResultSet rs, Statement stmt, Connection con){
		// close database connection
		 try {
			 if(rs != null){
			 	rs.close();	
			 }
			 
			 if(stmt != null){
				 stmt.close();	 
			 }
		   
			 if(con != null){
				 con.close();
			 }
		}
		catch (SQLException e) {}		
	}
	
	/**
	 * Abstract method for loading data from the database. Implemented in subclassess.
	 * @param taskId taskID
	 * @param language language
	 * @return TaskDTO object which contains task data
	 * @throws TaskProcessingException if something unexpected happens
	 */
	public abstract TaskDTO loadData(String taskId, String language) throws TaskProcessingException;
	
	/**
	 * Abstract method for saving data to the database.
	 * @param dto data to save
	 * @return int
	 * @throws TaskProcessingException if something unexpected happens
	 */ 
	public abstract int saveData(TaskDTO dto) throws TaskProcessingException;
}
