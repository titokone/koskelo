/*
 * Created on 28.3.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.showtask.trainer;

/**
 * A Class for analysing order tasks
 * @author Mikko Lukkari
 *
 */
public class OrderAnalyser extends CommonAnalyser{

  final static String ECODE="E";
  final static String TCODE="T";
  final static int PARAMETER_ERROR=2;
  
  AttributeCache cache;
  String myName;
  ParameterString initP;
  String feedbackText = "";
  String feedbackSummaryPos ="";
  String feedbackSummaryNeg ="";

  public OrderAnalyser(){
	taskID = null;
	cache  = null;
	myName = "OrderAnalyser";
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
  /** analyses order tasks
	* 
	* @param String[] answer answer given by student
	* @param String params
	*
	* @throws CacheException If there are problems using the system cache.
	*/  
  public Feedback analyse(String[] answer, String params)throws CacheException{
  	
	String currentOption=null;
	Feedback fb = new Feedback();
	float percent = 0;
	int percentInt = 0;
	int correctAnswers = 0;
	int numberOfOptions = 0;
	int vc = 0;
  	
	//	check that the answer is not empty
	if (answer==null || answer[1]==null || answer[1].length()==0) {
	  fb = new Feedback(1,0,null,null,cache.getAttribute(TCODE,"NOANSWER","MESSAGE", language));
	  return fb;
	}
	//	find out number of objects in exercise
	try{
	  vc = cache.attribCount(taskID, "object", language);
	}
	catch (Exception ex) {
      fb = new Feedback(PARAMETER_ERROR,
			            cache.getAttribute(ECODE,"ATTRIBUTETYPEERROR","MESSAGE",language) + 
						myName+ ' '+ taskID+'.'+currentOption);
	  return fb;		
	}
	feedbackText +="<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\">"
	+"<tr align=\"center\">" 
	+"<td class=\"tableHeader\">&nbsp;</td>"
	+"<td class=\"tableHeader\">"+cache.getAttribute("A", "orderanalyser", "objectlabel", language)+"</td>"
	+"<td class=\"tableHeader\">"+cache.getAttribute("A", "orderanalyser", "feedbacklabel", language)+"</td>"
	+"</tr>";
    // compare the answer with check values
	// generate feedback for matching value
	String [] fback=new String[answer.length+1];
	
	if (vc>0) {
	  try {
	    //int valuesToCheck = Integer.parseInt(vc);
		// loop checks all options
		for (int i=1; i<=vc; i++) {
		  currentOption = "placeofobject"+i;
		  //get right answer to this option
		  String checkValue = cache.getAttribute(TCODE,taskID,currentOption,language);
		  
		  if (checkValue!=null){
		    try{
		      int checkValueInt = Integer.parseInt(checkValue);
			  int userValue = Integer.parseInt(answer[i]);
			  // right place
			  if (userValue == checkValueInt){
			    correctAnswers++;
			  	currentOption = "correctfeedback"+i;
				fback[userValue]="<tr><td><strong>"+userValue+".</strong></td><td>"
			  	+cache.getAttribute(TCODE, taskID, "object"+i, language)
			  	+"</td>"+"<td align=\"center\" class=\"positivefeedback\">"
			  	+cache.getAttribute(TCODE, taskID, currentOption, language)+"</td></tr>";
			  	continue;
			  }
			  // too early
			  if (userValue < checkValueInt){			  
			    currentOption = "tooearlyfeedback"+i;
				fback[userValue]="<tr><td><strong>"+userValue+".</strong></td><td>"
				+cache.getAttribute(TCODE, taskID, "object"+i, language)
				+"</td>"+"<td align=\"center\" class=\"negativefeedback\">"
				+cache.getAttribute(TCODE, taskID, currentOption, language)+"</td></tr>";
				continue;			  	
		      }
		      // too late
			  if (userValue > checkValueInt){			  
			    currentOption = "toolatefeedback"+i;
				fback[userValue]="<tr><td><strong>"+userValue+".</strong></td><td>"
				+cache.getAttribute(TCODE, taskID, "object"+i, language)
				+"</td>"+"<td align=\"center\" class=\"negativefeedback\">"
				+cache.getAttribute(TCODE, taskID, currentOption, language)+"</td></tr>";
				continue;
			  }
		    }

		    //error in format of placeofobject
			catch (NumberFormatException ex) {
			  fb = new Feedback(PARAMETER_ERROR,
					  cache.getAttribute(TCODE,"ATTRIBUTETYPEERROR","MESSAGE",language) + 
					  myName+ ' '+ taskID+'.'+currentOption);
			  return fb;
			}
		  } 
		}
		for (int index=1; index<=vc; index++) {
				feedbackText+=fback[index];

		}
		feedbackText +="  </table></body></html>";
		percent = (correctAnswers*100);
		percent/= vc;
		percentInt = Math.round(percent);
	  }
	  //error in format of number of opitons
	  catch (NumberFormatException ex) {
	    fb = new Feedback(PARAMETER_ERROR,
		         cache.getAttribute(TCODE,"ATTRIBUTETYPEERROR","MESSAGE",language) + 
				 myName+ ' '+ taskID+'.'+currentOption);
	    return fb;
	  }	
	 
	}
	//	number of options == 0
	else {
	  fb= new Feedback(PARAMETER_ERROR,
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
	*/
  public void registerCache( AttributeCache c) {
	cache = c;
  }
  /** gives initialisation partameters
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
	* @return language
	*/
   public String getLanguage(){
	 return language; 
   }
}