
package fi.hy.eassari.taskdefinition.util;


import javax.servlet.http.*;
/**
 * A helper class for reading http-post request parameters.
 * @author Vesa-Matti Mäkinen
 */
public class PostParameterParser {

//	variables for commonly used post-parameters
	int event;
	String taskid;
	String taskName;
	String taskType;
	String language;
	boolean saveAsNew;
	boolean saveAsNewLanguage;

	HttpServletRequest request;

/**
 * Creates a new PostParameterParser-class and reads values to local variables.
 * @param req the HttpRequest to be used for value retrieval.
 */
	public PostParameterParser(HttpServletRequest req) {
		this.request = req;
	
		// set required variables
		this.event = getIntParameter("event");
		this.taskid = getStringParameter("taskid");
		this.taskName = getStringParameter("taskname");
	    this.taskType = getStringParameter("tasktype");
		this.language = getStringParameter("language");
		this.saveAsNew = getBooleanParameter("saveasnew");
		this.saveAsNewLanguage = getBooleanParameter("saveasnewlanguage");				
	}

/**
 * Returns a post parameter as an int variable. 
 * @param fieldName name of the post parameter to be read.
 * @return int value of the parameter. 0 if the parameter is not a valid number or doesn't exist.
 */
	public int getIntParameter(String fieldName) {
		int tmp;
		try {
		  tmp = Integer.parseInt(this.request.getParameter(fieldName));
		}
		catch (NumberFormatException e) {
		  System.out.println("PostParameterParser-getIntParameter(String): parameter '"+fieldName+"' was not a number.");
		  tmp = 0;
		}
		return tmp;
	}
/**
 * Returns a post parameter as a String variable. 
 * @param fieldName name of the post parameter to be read.
 * @return String value of the parameter. "" if the parameter doesn't exist.
 */
	public String getStringParameter(String fieldName) {
		String tmp = this.request.getParameter(fieldName);
		if (tmp == null) {
  		  //System.out.println("PostParameterParser-getStringParameter(String): parameter '"+fieldName+"' was null.");
		  tmp = "";
		}
		return tmp;
	}	
/**
 * Returns a post parameter as a boolean variable. The parameter to be read has to be an int. 
 * @param fieldName name of the post parameter to be read.
 * @return boolean value of the parameter. If the value is > 0, returns true, otherwise returns false.
 */	
	public boolean getBooleanParameter(String fieldName) {
		int tmp;
		try {
		  tmp = Integer.parseInt(this.request.getParameter(fieldName));
		}
		catch (NumberFormatException e) {
		  System.out.println("PostParameterParser-getBooleanParameter(String): parameter '"+fieldName+"' was not a number.");
		  tmp = 0;
		}
		return tmp>0;
	}

/**
 * Returns a sequence of int parameters as an array. 
 * Parameters have to be named as fieldName1, fieldName2, ..., fieldNameN (N=numberOfItems). 
 * @param fieldName name of the post parameter to be read.
 * @return int array of size numberOfItems+1. Values start from index 1. Index 0 is not used.
 */
	public int[] getIntArrayParameter(String fieldName, int numberOfItems) {
		int[] returnData = new int[numberOfItems+1];
		for (int i=1; i<=numberOfItems; ++i) {
			returnData[i] = this.getIntParameter(fieldName+""+i);
		}
		return returnData;
	}	
	
/**
 * Returns a sequence of String parameters as an array. 
 * Parameters have to be named as fieldName1, fieldName2, ..., fieldNameN (N=numberOfItems). 
 * @param fieldName name of the post parameter to be read.
 * @return String array of size numberOfItems+1. Values start from index 1. Index 0 is not used.
 */	
	public String[] getStringArrayParameter(String fieldName, int numberOfItems) {
		String[] returnData = new String[numberOfItems+1];
		for (int i=1; i<=numberOfItems; ++i) {
			returnData[i] = this.getStringParameter(fieldName+""+i);
		}
		return returnData;
	}	

/**
 * Returns a sequence of boolean parameters as an array. 
 * Parameters have to be named as fieldName1, fieldName2, ..., fieldNameN (N=numberOfItems). 
 * @param fieldName name of the post parameter to be read.
 * @return boolean array of size numberOfItems+1. Values start from index 1. Index 0 is not used.
 */
	public boolean[] getBooleanArrayParameter(String fieldName, int numberOfItems) {
		boolean[] returnData = new boolean[numberOfItems+1];
		for (int i=1; i<=numberOfItems; ++i) {
			returnData[i] = this.getBooleanParameter(fieldName+""+i);
		}
		return returnData;
	}	

	/**
	 * @return request-parameter "event" as an int.
	 */
	public int getEvent() {
		return event;
	}
	/**
	 * @return request-parameter "language" as a String.
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @return request-parameter "taskid" as a String.
	 */
	public String getTaskid() {
		return taskid;
	}
	/** 
	 * @return request-parameter "taskname" as a String.
	 */
	public String getTaskName() {
		return taskName;
	}
	/** 
	 * @return request-parameter "tasktype" as a String.
	 */
	public String getTaskType() {
		return taskType;
	}
	/** 
	 * @return request-parameter "saveasnew" as a boolean.
	 */
	public boolean isSaveasnew() {
		return saveAsNew;
	}
	/** 
	 * @return request-parameter "saveasnewlanguage" as a boolean.
	 */
	public boolean isSaveasnewlanguage() {
		return saveAsNewLanguage;
	}
}
