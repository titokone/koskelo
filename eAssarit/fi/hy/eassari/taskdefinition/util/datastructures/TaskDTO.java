/*
 * Created on Mar 23, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.taskdefinition.util.datastructures;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Data transfer object for storing all the task data. The General data have own 
 * getter and setter methods and task specific is stored to an HashMap 
 * 
 * @author Sami Termonen
 */
public class TaskDTO {
	private String taskId = "";
	private String taskName = "";
	private String language = "";
	private String author = "";
	private Date dateCreated = null;
	private String task = "";
	private String taskType = "";
	private String generalPositiveFeedback = "";
	private String generalNegativeFeedback = "";
	private int cutOffValue = 0;
	private String immediateFeedback;
	private String shouldRegisterTry;
	private String shouldStoreAnswer;
	private int numberOfTries = 0;
	private String shouldKnowStudent;
	private boolean saveAsNew = false;
	private boolean saveAsNewLanguage = false;
	private String oldLanguage = "";
	private HashMap options = new HashMap(10, 10);
	private int blankCount = 0;
	private ArrayList list = null;
	
	/**
	 * @return
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @return
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param string
	 */
	public void setTaskId(String i) {
		taskId = i;
	}

	/**
	 * @param string
	 */
	public void setTaskName(String string) {
		taskName = string;
	}

	public Object get(String key){
		return options.get(key);
	}
	
	public void set(String key, Object obj){
		options.put(key, obj);
	}
	/**
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param string
	 */
	public void setLanguage(String string) {
		language = string;
	}
	/**
	 * @return
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return
	 */
	public int getCutOffValue() {
		return cutOffValue;
	}

	/**
	 * @return
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @return
	 */
	public String getImmediateFeedback() {
		return immediateFeedback;
	}

	/**
	 * @return
	 */
	public int getNumberOfTries() {
		return numberOfTries;
	}

	/**
	 * @return
	 */
	public HashMap getOptions() {
		return options;
	}

	/**
	 * @return
	 */
	public String getShouldKnowStudent() {
		return shouldKnowStudent;
	}

	/**
	 * @return
	 */
	public String getShouldRegisterTry() {
		return shouldRegisterTry;
	}

	/**
	 * @return
	 */
	public String getShouldStoreAnswer() {
		return shouldStoreAnswer;
	}

	/**
	 * @return
	 */
	public String getTask() {
		return task;
	}

	/**
	 * @return
	 */
	public String getTaskType() {
		return taskType;
	}

	/**
	 * @param string
	 */
	public void setAuthor(String string) {
		author = string;
	}

	/**
	 * @param i
	 */
	public void setCutOffValue(int i) {
		cutOffValue = i;
	}

	/**
	 * @param date
	 */
	public void setDateCreated(Date date) {
		dateCreated = date;
	}

	/**
	 * @param c
	 */
	public void setImmediateFeedback(String c) {
		immediateFeedback = c;
	}

	/**
	 * @param i
	 */
	public void setNumberOfTries(int i) {
		numberOfTries = i;
	}

	/**
	 * @param map
	 */
	public void setOptions(HashMap map) {
		options = map;
	}

	/**
	 * @param c
	 */
	public void setShouldKnowStudent(String c) {
		shouldKnowStudent = c;
	}

	/**
	 * @param c
	 */
	public void setShouldRegisterTry(String c) {
		shouldRegisterTry = c;
	}

	/**
	 * @param c
	 */
	public void setShouldStoreAnswer(String c) {
		shouldStoreAnswer = c;
	}

	/**
	 * @param string
	 */
	public void setTask(String string) {
		task = string;
	}

	/**
	 * @param string
	 */
	public void setTaskType(String string) {
		taskType = string;
	}

	/**
	 * @return
	 */
	public String getGeneralNegativeFeedback() {
		return generalNegativeFeedback;
	}

	/**
	 * @return
	 */
	public String getGeneralPositiveFeedback() {
		return generalPositiveFeedback;
	}

	/**
	 * @param string
	 */
	public void setGeneralNegativeFeedback(String string) {
		generalNegativeFeedback = string;
	}

	/**
	 * @param string
	 */
	public void setGeneralPositiveFeedback(String string) {
		generalPositiveFeedback = string;
	}

	/**
	 * @return
	 */
	public String getOldLanguage() {
		return oldLanguage;
	}

	/**
	 * @return
	 */
	public boolean isSaveAsNew() {
		return saveAsNew;
	}

	/**
	 * @return
	 */
	public boolean isSaveAsNewLanguage() {
		return saveAsNewLanguage;
	}

	/**
	 * @param string
	 */
	public void setOldLanguage(String string) {
		oldLanguage = string;
	}

	/**
	 * @param b
	 */
	public void setSaveAsNew(boolean b) {
		saveAsNew = b;
	}

	/**
	 * @param b
	 */
	public void setSaveAsNewLanguage(boolean b) {
		saveAsNewLanguage = b;
	}

	/**
	 * Determines a boolean value of a String object
	 * 
	 * @return boolean true if String equals Y, otherwise false 
	 */
	public boolean getBooleanImmediateFeedback() {
		if(immediateFeedback.equalsIgnoreCase("Y")){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Converts String object (1 changes to 'Y' and 0 changes to 'N')
	 * 
	 * @param num String to convert to a boolean value
	 */
	public void setBooleanImmediateFeedback(String num) {
		if(num.equals("1")){
			immediateFeedback = "Y";
		}
		else{
			immediateFeedback = "N";
		}
	}

	/**
	 * Determines a boolean value of a String object
	 * 
	 * @return boolean true if String equals Y, otherwise false 
	 */
	public boolean getBooleanShouldKnowStudent() {
		if(shouldKnowStudent.equalsIgnoreCase("Y")){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Converts String object (1 changes to 'Y' and 0 changes to 'N')
	 * 
	 * @param num String to convert to a boolean value
	 */
	public void setBooleanShouldKnowStudent(String num) {
		if(num.equals("1")){
			shouldKnowStudent = "Y";
		}
		else{
			shouldKnowStudent = "N";
		}
	}

	/**
	 * Determines a boolean value of a String object
	 * 
	 * @return boolean true if String equals Y, otherwise false 
	 */
	public boolean getBooleanShouldRegisterTry() {
		if(shouldRegisterTry.equalsIgnoreCase("Y")){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Converts String object (1 changes to 'Y' and 0 changes to 'N')
	 * 
	 * @param num String to convert to a boolean value
	 */
	public void setBooleanShouldRegisterTry(String num) {
		if(num.equals("1")){
			shouldRegisterTry = "Y";
		}
		else{
			shouldRegisterTry = "N";
		}
	}

	/**
	 * Determines a boolean value of a String object
	 * 
	 * @return boolean true if String equals Y, otherwise false 
	 */
	public boolean getBooleanShouldStoreAnswer() {
		if(shouldStoreAnswer.equalsIgnoreCase("Y")){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Converts String object (1 changes to 'Y' and 0 changes to 'N')
	 * 
	 * @param num String to convert to a boolean value
	 */
	public void setBooleanShouldStoreAnswer(String num) {
		if(num.equals("1")){
			shouldStoreAnswer = "Y";
		}
		else{
			shouldStoreAnswer = "N";
		}
	}

	/**
	 * Removes a parameter from the HashMap
	 * 
	 * @param parameterName parameter name
	 */	
	public void eraseParameter(String parameterName) {
		options.remove(parameterName);		
	}
	
	/**
	 * Removes parameter(s) starting a given number from the HashMap
	 * 
	 * @param parameterName parameter name
	 * @param startFrom a number to start
	 */		
	public void eraseParameterSequence(String parameterName, int startFrom) {
		int index = startFrom;
		while (options.containsKey(parameterName+index)) {
			options.remove(parameterName+index);
			++index;
		}
	}
	
	public String toString(){
		return ("TaskId = " + taskId + 
						   "TaskName = " + taskName +						   "language = " + language);
	}
}
