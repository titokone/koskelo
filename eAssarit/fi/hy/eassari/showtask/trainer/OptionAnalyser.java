/*
 * Created on 25.3.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.showtask.trainer;

/**
 * A Class for analysing option tasks
 * @author Mikko Lukkari
 */
public class OptionAnalyser extends CommonAnalyser{

  final static String ECODE="E";
  final static String TCODE="T";
  final static int PARAMETER_ERROR=2;
  
  AttributeCache cache;
  String myName;
  ParameterString initP;
  float percent = 0;
  int percentInt = 0;
  String feedbackText="";
  String feedbackSummaryPos="";
  String feedbackSummaryNeg="";
	
  public OptionAnalyser(){
  	taskID = null;
  	cache  = null;
  	myName = "OptionAnalyser";
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
  
  /** analyses option type of tasks
   * 
   * @param String[] answer answer given by student
   * @param String params , optional parameters used
   * 
   * @throws CacheException If there are problems using the system cache.
   */
  public Feedback analyse(String[] answer, String params)throws CacheException{
  	
	String currentOption=null;
  	Feedback fb = new Feedback();
  	int correctAnswers = 0;  	
	int vc = 0;
	
    //	check that the answer is not empty
    if (answer==null || answer[1]==null || answer[1].length()==0) {
	  fb = new Feedback(1,0,null, null, cache.getAttribute(TCODE,"NOANSWER","MESSAGE", language));
	  return fb;
    }
    //find out number of options in exercise
	try {	
	  vc = cache.attribCount(taskID, "option", language);
	}
	catch(Exception ex){
	  fb = new Feedback (PARAMETER_ERROR,
			 ex + myName+ ' '+ taskID+'.'+currentOption);
	  return fb;
	}
	feedbackText +="<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\">"
    +"<tr align=\"center\">" 
      +"<td class=\"tableHeader\">&nbsp;</td>"
      +"<td class=\"tableHeader\">"+cache.getAttribute("A", "optionanalyser", "optionlabel", language)+"</td>"
      +"<td class=\"tableHeader\">"+cache.getAttribute("A", "optionanalyser", "feedbacklabel", language)+"</td>"
      +"</tr>";
	// compare the answers with check values
	// generate feedback for all values	 
	if (vc>0) { 
	  //loop checks all options
	  for (int i=1; i<=vc; i++) {
	    //currentOption = "isselected"+i;
	  	//get right answer to this option 
	  	String checkValue = cache.getAttribute(TCODE,taskID,"isselected"+i,language);		  	  	
	  	if (checkValue!=null){
	  	  try{	  	  	
	  	    //right answer
			answer[i]=answer[i].toUpperCase();
			checkValue = checkValue.toUpperCase();		
	  	  	if (answer[i].equals(checkValue)){
	  		 	correctAnswers++;
	  		 	feedbackText +="<tr><td><strong>"+i+".</strong></td><td>"
	  		  	+cache.getAttribute(TCODE, taskID, "option"+i, language)
	  		  	+"</td>"+"<td align=\"center\" class=\"positivefeedback\">"
	  		  	+cache.getAttribute(TCODE, taskID, "positivefeedback"+i, language)+"</td></tr>";
	  	  	}
	  	  	//wrong answer
	  	  	else{		  	  
			  	feedbackText +="<tr><td><strong>"+i+".</strong></td><td>"
   			  	+cache.getAttribute(TCODE, taskID, "option"+i, language)
			  	+"</td>"+"<td align=\"center\" class=\"negativefeedback\">"
			  	+cache.getAttribute(TCODE, taskID, "negativefeedback"+i, language)+"</td></tr>";
	  	  	}
		}	  	  
	  	  //error in value field format
	  	  catch (NumberFormatException ex) {
	  	    fb = new Feedback (PARAMETER_ERROR,
	  		 	  cache.getAttribute(TCODE,"ATTRIBUTETYPEERROR","MESSAGE",language)+ 
	  			  myName+ ' '+ taskID+'.'+currentOption);
	  		return fb;
	  	  }				   		    
		}
	    //checkvalue == null
		else{
		  fb = new Feedback(PARAMETER_ERROR,
				   cache.getAttribute(TCODE,"NOTASKATTRIBUTE","MESSAGE",language)+
				   myName+' '+taskID+'.'+currentOption+" checkvalue :");
		  return fb;
		}
	  }
	  feedbackText +="  </table></body></html>";
	  percent = (correctAnswers*100);
	  percent/= vc;
	  percentInt = Math.round(percent);	  
	}		 
	// number of options == 0
	else {
	  fb = new Feedback(PARAMETER_ERROR,
	          cache.getAttribute(TCODE,"NOTASKATTRIBUTE","MESSAGE",language)+
			  myName+ ' '+ taskID+'.'+"numberofoptions");
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
    * AttributeCache c cache used
    */
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
