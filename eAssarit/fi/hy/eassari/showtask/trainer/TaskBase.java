package fi.hy.eassari.showtask.trainer;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Hashtable;


/****
 * Taskbase manages the cache of tasks, taskbodies, and their attributes.
 * It also knows the the tasktype specific Analysers and  Displayers and
 * loads them when necessary.
 *
 * @author  Harri Laine (modified by Olli-Pekka Ruuskanen)
 * @version 9.4.2004
 */


public class TaskBase implements AttributeCache {

  //static TaskBase INSTANCE;
  //static DBConnectionBroker taskBasepool;

	Hashtable loadedAttributes;
  //Hashtable loadedAnalysers;
  //Hashtable loadedDisplayers;
	Hashtable loadedTasks;
	Hashtable loadedTasktypes;

	String dbDriver   = null;
	String dbServer   = null;
	String dbLogin    = null;
	String dbPassword = null;
  //String helpURL    = null;

	/**
	*  Creates new TaskBase object, A single program may use many taskbases.
	* 
	*  @param db_Driver   database driver identification.
	*  @param db_Server   database server identification.
	*  @param db_Login    user id.
	*  @param db_Password user's passwrord.
	*/
	
	public TaskBase(String db_Driver, String db_Server, String db_Login,  String db_Password) {

		dbDriver         = db_Driver;
		dbServer         = db_Server;
		dbLogin          = db_Login;
		dbPassword       = db_Password;
		loadedAttributes = new Hashtable(1000);
	  //loadedAnalysers  = new Hashtable(20);
	  //loadedDisplayers = new Hashtable(20);
		loadedTasktypes  = new Hashtable(20);
		loadedTasks      = new Hashtable(100);

	  //int minConns = Integer.parseInt((String) p.get("minConns"));
	  //int maxConns = Integer.parseInt((String) p.get("maxConns"));
	  //String logFileString = (String) p.get("logFileString");
	  //double maxConnTime   = new Double((String)p.get("maxConnTime"))).doubleValue();

	  //initialize repository:
	  //Class.forName(dbDriver);
	  //helpURL  = (String) p.get("helpURL");
	  //dbURL    = (String) p.get("dbURL");
	  //myBroker = new DbConnectionBroker (dbDriver,dbServer,dbLogin,
	  //								   dbPassword, minConns,maxConns,
	  //								   logFileString,maxConnTime);
	}


//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
// Code modifications by OPR.

	/**
	 * Method to determine certain attribute's number in a given language
	 * for a given task. Returns an integer representing the number of those
	 * attributes in the attributevalues table which match the search
	 * criteria. If task contains language independent attributes, their
	 * number is added to the specified language's attribute count.
	 *
	 * @param  objectid      Identifier of a task whose attribute is the
	 * 			 			 target of the count.
	 * @param  attributename String representing the prefix of an attribute
	 * 						 name which is the target of the count.
	 * @param  language      Language version of the attribute. All language
	 * 						 independent attributes are automatically included
	 * 						 in the search.
	 * @return int
	 * @throws CacheException If there are problems with the database.
	 * @throws SQLException   If there are problems with the database.
	 */

	public int attribCount(String objectid, String attributename,
						   String language)
						   throws CacheException, SQLException {

		int languageSpecCount = 0;
		int allLanguagesCount = 0;



		// Some attributes are marked as language specific:
		String languageSpecQuery =
				"select count(attributename) from attributevalues where " +
					"(attributename like '" + attributename + "%' and " +
					"language    = '" + language + "'             and " +
					" objectid   = '" + objectid + "'             and " +
					" objecttype = 'T')";

		// Some attributes are marked as applicable for all languages:
		String allLanguagesQuery =
				"select count(attributename) from attributevalues where " +
					"(attributename like '" + attributename + "%' and " +
					"language    = 'ALL'                          and " +
					" objectid   = '" + objectid + "'             and " +
					" objecttype = 'T')";

		Connection con = getConnection();
		Statement  stm = null;
		ResultSet  rs  = null;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(languageSpecQuery);
			if (rs.next())
				languageSpecCount = rs.getInt(1);
			else
			languageSpecCount = 0;
			// If only language independent attributes are to be counted,
			// don't count them twice:
			if (!language.equals("ALL")) {
				rs = stm.executeQuery(allLanguagesQuery);
				if (rs.next())
					allLanguagesCount = rs.getInt(1);
				else
					allLanguagesCount = 0;
			}
		}
		catch (SQLException ex) {
			  throw new CacheException ("Repository failure: " + ex.getMessage());
		}
		finally {
			  try {
				if (rs!=null)  rs.close();
				if (stm!=null) stm.close();
				con.close();
			  }
			  catch (SQLException exc) {
				throw new SQLException ("Cannot close connection: " + exc.getMessage());
			  }
		}
		// Return the number of language specific and language
		// independent attributes:
		return languageSpecCount + allLanguagesCount;
	}




	/**
	 * Method to save answers to tasks in the database.
	 * 
	 * @param userid		Identifies the user who provided the answer.
	 * @param courseid		Identifies the course into which the task belongs.
	 * @param moduleid		Identifies a module within the course.
	 * @param seqno			Identifies a sequence within the module.
	 * @param trynumber		Identifies the number of this try for the user for this task.
	 * @param correctness   Identifies the grade of the answer.
	 * @param whenanswered  Identifies the date when the answer is entered into the system.
	 * @param answer		The actual answer given for the task.
	 * @param language		Identifies the language version used.
	 * @throws CacheExcption If there are problems with the database.
	 */


	public void saveAnswer(String userid, String courseid, String moduleid,
						   int seqno, int trynumber, int correctness,
						   String whenanswered, String answer, String language)
						   throws CacheException {

		Connection con = null;
		Statement  stm = null;
		ResultSet  rs  = null;
		con = getConnection();
		if (con == null) {
			return;
		}

		// Actual SQL query to save the answer to the database:
		String saveAnswer =
		"insert into storedanswer values "+
			"('" + userid+ "','" +courseid+ "','" +moduleid+ "'," +
			  seqno+ "," + trynumber + "," +correctness+ ", " +
			"to_timestamp('" +whenanswered+ "','DD.MM.YYYY'),'" +
			answer+ "','" +language+ "', null, null)";

		try {
			stm = con.createStatement();
			rs  = stm.executeQuery(saveAnswer);
		}
		catch (SQLException ex) {
			throw new CacheException ("Repository failure: " + ex.getMessage());
		}
		finally {
			try {
				if (rs!=null)  rs.close();
				if (stm!=null) stm.close();
				con.close();
			}
			catch (SQLException sex) {;}
		}
	}
	
	
/*
	NOT USED
		
	/**
	 * Returns tasktype corresponding to a task given as parameter.
	 * 
	 */
/*
	public String taskType (String taskid)
							throws CacheException {

		String     type = null;
		Connection con  = getConnection();
		Statement  stm  = null;
		ResultSet  rs   = null;

		String query =
			"select tasktype from task where " +
				"(taskid = '" + taskid + "')";

		try {
			stm = con.createStatement();
			rs  = stm.executeQuery(query);
			if (rs.next())
				type = rs.getString("tasktype");
		}
		catch (SQLException ex) {
			throw new CacheException ("Repository failure: " + ex.getMessage());
		}
		finally {
			try {
				if (rs!=null)  rs.close();
				if (stm!=null) stm.close();
				con.close();
			}
			catch (SQLException sex) {;}
		}
		return type;
	}
*/



	/**
	 * Returns taskid corresponding to the values provided as parameters.
	 * 
	 * @param  courseid Identifies the course into which the task belongs.
	 * @param  moduleid Identifies a module within the course.
	 * @param  seqno    Identofoes a sequence within the module.
	 * @return String
	 * @throws CacheExcption If there are problems with the database.
	 */

	public String getTaskID (String courseid, String moduleid, int seqno)
							throws CacheException {

		String     taskid = null;
		Connection con    = getConnection();
		Statement  stm    = null;
		ResultSet  rs     = null;

		String query =
			"select taskid from taskinmodule where " +
				"(courseid = '" + courseid + "' and " +
				" moduleid = '" + moduleid + "' and " +
				" seqno    =  " + seqno + ")";

		try {
			stm = con.createStatement();
			rs  = stm.executeQuery(query);
			if (rs.next())
				taskid = rs.getString("taskid");
		}
		catch (SQLException ex) {
			throw new CacheException ("Repository failure: " + ex.getMessage());
		}
		finally {
			try {
				if (rs!=null)  rs.close();
				if (stm!=null) stm.close();
				con.close();
			}
			catch (SQLException sex) {;}
		}
		return taskid;
	}




	/**
	 * Returns a boolean value whether task is defined in given language.
	 * 
	 * @param  taskid   Identifies the task.
	 * @param  language Identifies the language version of the task.
	 * @return boolean
	 * @throws CacheExcption If there are problems with the database.
	 */

	public boolean languageDefined (String taskid, String language)
								    throws CacheException {

		boolean    found   = false;
		Connection con     = getConnection();
		Statement  stm     = null;
		ResultSet  rs      = null;

		String query =

			"select language from attributevalues where " +
				"(objectid = '" + taskid + "' and " +
				" attributename='task')";

		try {
			stm = con.createStatement();
			rs  = stm.executeQuery(query);
			while (rs.next()) {
				String check = rs.getString("language");
				if (check.equals(language))
					found = true;
			}
		}
		catch (SQLException ex) {
			throw new CacheException ("Repository failure: " + ex.getMessage());
		}
		finally {
			try {
				if (rs!=null)  rs.close();
				if (stm!=null) stm.close();
				con.close();
			}
			catch (SQLException sex) {;}
		}
		return found;
	}



//---------------------------------------------------------------------------
//---------------------------------------------------------------------------
// Original code by H.L.


   /****
	* Returns the value of the the specified course, module or task
	* attribute in a requested language. Also language independent values
	* are returned. Returns null if the requested value is not available.
	* If the attribute values in the language specified are not in cache
	* they are loaded.
	* 
	* @param  objType       Identifies the type of the object (Task, Displayer, Analyser, Error).
	* @param  objID         Identifies the object.
	* @param  attributename Identifies the atribute which value is returned by the method.
	* @param  language      Identifies the language version of the attribute.
	* @return String
	* @throws CacheExcption If there are problems with the database.
	*/

	public String getAttribute (String objType, String objID,
								String attributename, String language)
								throws CacheException {

		//are the attribute values in this language already loaded?
		String keyPrefixLang = objType + "." + objID + "." + language;
		String keyPrefixAll  = objType + "." + objID + ".ALL.";
		String key           = keyPrefixLang + "." + attributename;
		String value         = null;
		//StringBuffer v = new StringBuffer();
		//check if the values are loaded
		//check if objects's attributes in current language are in cache
		String loadStatus = (String) loadedAttributes.get(keyPrefixLang);

		if (loadStatus==null) {
			//values are not loaded yet, so load them
			//v.append(keyPrefixLang +" not loaded yet, load all<br>");
			loadStatus = loadAllAttributes(objType,objID, language);
			//v.append(loadStatus);
			//v.append("br>");
		}
		if (loadStatus == "LOADED") {
			//now it is loaded if it exists
			value = (String) loadedAttributes.get(key);
			if (value==null) {
				//there were no value in this language, but there might be
				//one common to all languages, load that:
				//v.append(key + "not found<br>");
				String anyLangKey = keyPrefixAll+attributename;
				//try to find a common value for all languages
				value = (String) loadedAttributes.get(anyLangKey);
			}
			//if (value !=null)
			//	v.append(value);
			return value;
		}
		else {
			throw new CacheException(loadStatus);
		}
	}





	/****
	 * Establishes a database connection according to the parameters
	 * loaded from configuration file.
	 * 
	 * @return Connection
	 * @throws CacheExcption If there are problems with the database.
	 */

	private Connection getConnection () throws CacheException {


		// load database driver if not already loaded
		Connection conn = null;
		try {
		  Class.forName(dbDriver);               // load driver
		//Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			 throw new CacheException ("Couldn't find the driver " +
										dbDriver);
		}
		try {
		   conn = DriverManager.getConnection(dbServer, dbLogin, dbPassword);

		}
		catch (SQLException sex) {
		   throw new CacheException ("Couldn't establish repository " +
									 "connection: " + sex);
		}
		return conn;
   }




   /****
	* Loads object's attribute values in a given language and also values
	* common to all languages.
	* 
	* @param  objType       Identifies the type of the object (Task, Displayer, Analyser, Error).
	* @param  objID         Identifies the object.
	* @param  language      Identifies the language version used.
	* @return String
	* @throws CacheExcption If there are problems with the database.
	*/

	private synchronized String loadAllAttributes
								(String objType, String objID,
								 String language)
								throws CacheException {

		// create connection to taskdb
		String     fb    = null;
		String     query = null;
	  //Feedback   fx    = new Feedback();
		Connection con   = getConnection();

		// retrieve all taskattribues in this language
		if (con == null) {
			fb = "FAILED no connection";
			return fb;
		}
		String attQuery = "select attributename, attributevalue, " +
						  "valuetype, language "+
							"from attributevalues where "+
								"objecttype = '" + objType + "' and " +
								"objectid   = '" + objID + "' and " +
								"(language  = '" + language + "' or " +
								"language = 'ALL')";
		Statement stm = null;
		ResultSet rs  = null;
		boolean attributesExist = false;
		String keyPrefix        = objType + "." + objID + ".";
		String keyPrefixAllLang = objType + "." + objID + ".ALL";
		// check if attribute values common to all languages have already
		// been loaded
		boolean commonLoaded = false;
		String commonL = (String) loadedAttributes.get(keyPrefixAllLang);
		if (commonL != null) {
			commonLoaded = commonL.equals("LOADED");
		}

	   String bkey = keyPrefix + language;

		try {
			stm = con.createStatement();
			rs  = stm.executeQuery(attQuery);
			while (rs.next()) {
				attributesExist = true;
				String aName    = rs.getString("ATTRIBUTENAME");
				String aValue   = rs.getString("ATTRIBUTEVALUE");
				String aLang    = rs.getString("LANGUAGE");
				if (aValue != null){
					String akey = keyPrefix + aLang + "." + aName;
					loadedAttributes.put(akey,aValue);
				}
			}
			//  if (attributesExist) {
			fb = "LOADED";
			// mark language dependent values as loaded
			loadedAttributes.put(bkey,fb);
			if (!commonLoaded)
				loadedAttributes.put(keyPrefixAllLang, fb);
		  //}
		  //else {
		  //   fb = "UNAVAILABLE";
		  //   loadedAttributes.put(bkey,fb);
		  //}
		}
		catch (SQLException ex) {
			fb = "Repository failure :" + ex.getMessage();
			throw new CacheException(fb);
		}
		finally {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();
			}
			catch (SQLException fex) {}
		}
		return fb;
	}


/*

	   public String getScript(String tasktype) {
		   // provides the javascript needed in processing the task form
		   // of task iloId that is of type tasktype

		   return null;
		   else {
			   DisplayerInterface taskDisplayer = null;
			   taskDisplayer =
				   (DisplayerInterface) getPlugin(tasktype, loadedDisplayers,
												  "DISPLAYER");
			   String script = null;
			   if (taskDisplayer != null)
				   taskDisplayer.registerCache(this);
			   net script = taskDisplayer.getScript(language);
			   return script;
		   }
	   }


	   public String getSetting (String taskID, String tasktype,
								 String language){

		   String body = "<b>Task " + taskID + " of type " + tasktype + " " +
						 "in language " + language + " requested</b>";
		   DisplayerInterface taskDisplayer=null;
		   taskDisplayer =
			   (DisplayerInterface) getPlugin(tasktype, loadedDisplayers,
											  "DISPLAYER");
		   String body = null;
		   if (taskDisplayer != null)
			   taskDisplayer.registerCache(this);
		   body = taskDisplayer.getSetting(taskID,language);
		return body;


	  }


	   public Task taskByIdTest (String taskID, String courseID,
								 String contextID) {
		   return new Task(taskID, courseID, contextID, "TEST",
						   1, null, "N", "N", "N", -1);
	   }

*/


	  /**
	   * Returns the module specific and general information about the task.
	   * First reference to the task creates the object and attaches it to
	   * the task pool.
	   * 
	   * @param  courseid Identifies the course into which the task belongs.
	   * @param  moduleid Identifies a module within the course.
	   * @param  seqno    Identofoes a sequence within the module.
       * @throws CacheExcption If there are problems with the database.
	   */


	   public synchronized Task taskById (int seqNo, String courseID,
										  String moduleID)
										  throws CacheException {

		   Task task = null;
		   String fullTaskID = courseID + "." + moduleID + "." + seqNo;

		   // full id consists of courseid, moduleid and task sequence number
		   task = (Task) loadedTasks.get(fullTaskID);

		   if (task == null) {
		   // the task has not been loaded yet
			   String query =
					   "select taskinmodule.taskid, tasktype, numberoftries, " +
					   "shouldstoreanswer, shouldregistertry, " +
					   "shouldknowstudent, shouldevaluate, iscreditable, " +
					   "creditsupto, taskinmodule.cutoffvalue, displayer, " +
					   "displayerinit, analyser, analyserinit, tasktypestyle " +
						   "from module, task, taskinmodule,tasktype where " +
							   "taskinmodule.taskid = task.taskid and " +
							   "module.courseid = taskinmodule.courseid and " +
							   "module.moduleid = taskinmodule.moduleid and " +
							   "tasktype.typename = task.tasktype and " +
							   "taskinmodule.seqno = " + seqNo + " and " +
							   "taskinmodule.courseid = '" + courseID + "' and "+
							   "taskinmodule.moduleid= '" + moduleID + "'";

			   Connection con = getConnection();
			   Statement stm  = null;
			   ResultSet rs   = null;
			   try {
				   stm = con.createStatement();
				   rs  = stm.executeQuery(query);
				   if (rs.next()) {
					   int     tries       = rs.getInt("numberoftries");
					   int     cvalue      = rs.getInt("cutoffvalue");
					   String  tid         = rs.getString("taskid");
					   String  tty         = rs.getString("tasktype");
					   String  shouldreg   = rs.getString("shouldregistertry");
					   String  shouldstore = rs.getString("shouldstoreanswer");
					   String  shouldknowstudent
										   = rs.getString("shouldknowstudent");
					   String  shouldevaluate
										   = rs.getString("shouldevaluate");
					   boolean breg        = false;
					   boolean bstore      = false;
					   boolean bknow       = false;
					   boolean beval       = false;

					   if (shouldreg != null && shouldreg.equals("Y"))
						   breg = true;
					   if (shouldstore != null && shouldstore.equals("Y"))
						   bstore = true;
					   if (shouldknowstudent!=null &&
						   shouldknowstudent.equals("Y"))
						   bknow = true;
					   if (shouldevaluate != null && shouldevaluate.equals("Y"))
						   beval = true;
					   String isC = rs.getString("iscreditable");
					   Timestamp creditsupto = rs.getTimestamp("creditsupto");
					   Tasktype tType = (Tasktype) loadedTasktypes.get(tty);

					   if (tType == null) {
						   // tasktype has not been loaded
						   String displayer     = rs.getString("displayer");
						   String displayerinit = rs.getString("displayerinit");
						   String analyser      = rs.getString("analyser");
						   String analyserinit  = rs.getString("analyserinit");
						   String mStyle        = rs.getString("tasktypestyle");
						   tType = new Tasktype (tty, displayer, displayerinit,
												 analyser, analyserinit,
												 mStyle);
						   loadedTasktypes.put(tty,tType);
					   }
					   task = new Task (tid, courseID, moduleID, tty, seqNo,
										creditsupto, bstore, breg, bknow,
										beval, cvalue, tries, tType);
					   loadedTasks.put(fullTaskID, task);
				   }
				   else {
					   task = null;
				   }
			   }
			   catch (SQLException ex) {
				   throw new CacheException ("Repository failure in TaskBase: " +
											  ex.getMessage() + " for query: \n" + query);
			   }
			   finally {
				   try {
					   if (rs!=null)
						   rs.close();
					   if (stm!=null)
						   stm.close();
					   con.close();
				   }
				   catch (SQLException sex) {}
			   }
		   }
		   return task;
	   }
}