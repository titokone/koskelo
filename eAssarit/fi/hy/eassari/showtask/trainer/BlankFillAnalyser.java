/*
 * Created on 30.3.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.showtask.trainer;

/**
 * A Class for analysing Blankflill tasks
 * @author Mikko Lukkari
 */
public class BlankFillAnalyser extends CommonAnalyser{
	
  final static String ECODE="E";
  final static String TCODE="T";
  final static int PARAMETER_ERROR=2;
  
  AttributeCache cache;
  String myName;
  ParameterString initP;
  float percent = 0;
  int percentInt = 0;
  String feedbackText="";
  String feedbackSummaryPos ="";
  String feedbackSummaryNeg ="";
    
  public BlankFillAnalyser(){
    taskID = null;
	cache  = null;
	myName = "BlankFillAnalyser";
	language="EN";
	initP=null;
  }
  /**
	* initializes the analyser
	* 
	* @param taskID full task identifier (course.module.task)  
	* @param lanquage language used
	* @param initparams initialization parameters, xml, may be null
	*/
  public void init(String taskid, String language, String initparams){
	taskID = taskid;
	this.language = language;
	if (initparams!=null)
	initP= new ParameterString(initparams);	
  }
  /** analyses blankfill tasks
	* 
	* @param String[] answer answer given by student
	* @param String params optional parameters
	* 
	* @throws CacheException If there are problems using the system cache.
	*/
  public Feedback analyse(String[] answer, String params)throws CacheException{
  	
	String currentOption=null;
	Feedback fb = new Feedback();
	int correctAnswers = 0;
	String feedbackText="";
	String returnText ="";
	int vc = 0;
	
	//	check that the answer is not empty
	if (answer==null || answer[1]==null) {
	  fb = new Feedback(1,0,null,null,cache.getAttribute(TCODE,"NOANSWER","MESSAGE", language));
  	  return fb;
	}
	
	//	find out number of options in exercise
	try {	
	  vc = cache.attribCount(taskID, "gap", language);
	}
	catch(Exception ex){
		fb = new Feedback (PARAMETER_ERROR,
			 ex + myName+ ' '+ taskID+'.'+currentOption);
		return fb;
	}
	feedbackText +="<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">"
    +"<tr>" 
      +"<td class=\"tableHeader\">&nbsp;</td>"
      +"<td colspan=\"2\" align=\"center\" class=\"tableHeader\">"+cache.getAttribute("A", "blankfillanalyser", "answerlabel", language)+"</td>"
      +"<td align=\"center\" class=\"tableHeader\">"+cache.getAttribute("A", "blankfillanalyser", "feedbacklabel", language)+"</td>"
    +"</tr>"
    +"<tr>"
      +"<td class=\"subHeader\">&nbsp;</td>"
      +"<td class=\"subHeader\"><strong>"+cache.getAttribute("A", "blankfillanalyser", "correct_answerlabel", language)+"</strong></td>"
      +"<td align=\"center\"><strong>"+cache.getAttribute("A", "blankfillanalyser", "your_answerlabel", language)+"</strong></td>"
      +"<td align=\"center\" class=\"subHeader\">&nbsp;</td>"
    +"</tr>";
	// compare the answers with check values
	// generate feedback for all values

	if (vc>0) {
	  for (int i=1; i<=vc; i++) {
		//natsaako?
		if(answer[i].equals(cache.getAttribute(TCODE, taskID, "gap"+i, language))){
		  feedbackText +="<tr> <td><strong>"+i+".</strong></td>"
      	  +"<td class=\"small\">..."+cache.getAttribute(TCODE, taskID, "before"+i, language)+" "
          +"<span class=\"blankspot\">"+cache.getAttribute(TCODE, taskID, "gap"+i, language)+"</span> "+cache.getAttribute(TCODE, taskID, "after"+i, language)+"...</td>"
          +"<td align=\"center\" class=\"positivefeedback\">"+answer[i]+"</td>"
          +"<td><div align=\"center\" class=\"positivefeedback\">"+cache.getAttribute(TCODE, taskID, "positivefeedback"+i, language)+"</div></td>"
          +"</tr>";
		  correctAnswers++;
		}
		else{
		  try{
			feedbackText +="<tr> <td><strong>"+i+".</strong></td>"
		  	+"<td class=\"small\">..."+cache.getAttribute(TCODE, taskID, "before"+i, language)+" " 
		  	+"<span class=\"blankspot\">"+cache.getAttribute(TCODE, taskID, "gap"+i, language)+"</span> "+cache.getAttribute(TCODE, taskID, "after"+i, language)+"...</td>"
		  	+"<td align=\"center\" class=\"negativefeedback\">"+answer[i]+"</td>"
		  	+"<td><div align=\"center\" class=\"negativefeedback\">"+cache.getAttribute(TCODE, taskID, "negativefeedback"+i, language)+"</div></td>"
		  	+"</tr>";
		  }
		  catch(Exception e){
		    continue;	
		  }		    			
		}
	  }
	  feedbackText +="  </table></body></html>";
	  percent = (correctAnswers*100);
	  percent/= vc;
	  percentInt = Math.round(percent);
	  	 
	}
	//number of options == 0
	else {
	  fb = new Feedback(PARAMETER_ERROR,
			   cache.getAttribute(TCODE,"NOTASKATTRIBUTE","MESSAGE",language)+
			   myName+ ' '+ taskID+'.'+"values to check");
	  return fb;
	}

	//get summary text for feedback
	feedbackSummaryPos = cache.getAttribute(TCODE,taskID,"positivefeedback",language);	
	
	feedbackSummaryNeg = cache.getAttribute(TCODE,taskID,"negativefeedback",language);
	
	//everything ok  
	fb = new Feedback(0,percentInt,feedbackSummaryPos, feedbackSummaryNeg,feedbackText);
	return fb;
  }
  /**
	* Registration of cache to be used in storing attribute values
	* 
	* @param AttributeCache c cache used
	**/
  public void registerCache( AttributeCache c) {
	cache = c;
  }
  
  /** gives initialisation parameters
   * @return initP
   */
  public ParameterString getInitP() {
  	return initP;
  } 

  /** gives name of object
   * @return myName
   */
  public String getMyName() {
	return myName;
  } 
  /** gives tasks id
   * @return taskID
   */
  public String getTaskID(){
  	return taskID; 
  }
  /** gives language used
   * @return taskID
   */
  public String getLanguage(){
	return language; 
  }
}