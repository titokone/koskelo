/*
 * Created on 2.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.displayers;

import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CacheException;
import fi.hy.eassari.showtask.trainer.DisplayerInterface;

/**
 * A common superclass for all displayers.
 * 
 * @author Teemu Andersson
 */
abstract public class CommonDisplayer implements DisplayerInterface {

	AttributeCache cache;
	String taskid;
	String language;
	String initparams;

	/****
	 * initializes the Displayer
	 * 
	 * @param   taskid= full task identifier 
	 * @param   language= language used (FI,EN)
	 * @param   initparams= parameters used for Dispalyer initialization, XML format
	 */
	public void init (String taskid, String language, String initparams){
		this.taskid=taskid;
		this.language=language;
		this.initparams=initparams;
	}
	
	/****
	 * Generates the JavaSripts needed for the user interface
	 */
	public String getScript () throws CacheException {
	return "";}
	
	/***
	 * Registration of cache to be used in storing attribute values
	 * 
	 * @param cache An object implementing the AttributeCache interface.
	 */
	public void registerCache(AttributeCache cache ){
		this.cache=cache;
	}
    
	
	/****
	 * Generates the actual representation of the task in HTML, should be overwritten in classes extending this class.
	 *
	 * @param  initval contains the default answer
	 * @param  params  contains the task specific generated parametervalues - the format of the string
	 *           is Displayer5 specific but xml is recommended
	 * @param  hiddens contains hidden http parameters to be includen in the form
	 * @param  allowTry specifies whether answerbutton should be included or not  
	 */
   public String getSetting(String [] initVal, String params, String hiddens,  boolean allowTry) throws CacheException{
   return "OVERRIDE THIS";
   }
   
   /***
	* generates help 'links'
	*/
	public String getHelps() throws CacheException{
		return"";
	}
/**
 * Generates a submit button.
 * 
 * @return a String containing the html representation of a submitbutton. The button is on the language that the cache is registred with.
 */
	protected String getButton(){
		try{
			return ("<input name=\"Submit\" type=\"submit\" class=\"button\" value=\""+
			cache.getAttribute("D", "CommonDisplayer", "submitbutton", language)+
			"\" />");
		}
		catch(CacheException ex){
			return "CacheException occurred while retrieving button"+ex;
		}
	}
}