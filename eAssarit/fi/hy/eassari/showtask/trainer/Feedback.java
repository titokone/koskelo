/*
 * Feedback.java
 *
 * Created on 10. lokakuuta 2003, 8:42
 */

package fi.hy.eassari.showtask.trainer;

/**
 * A Class for feedback delivering to servlet
 * @author  laine modified by Mikko Lukkari
 * @version 
 */
public class Feedback extends java.lang.Object {
   
 
	int errorCode;   // zero if no system errors
	int evaluation;  // task dependent correctness 0 faulty, 100 perfect
					 // use of values between 0 and 100 is analyser dependent
	String msgTextPos;  // feedback message
	String msgTextNeg;  // feedback message
	String extra;    // suplementary feedeback

	/** Creates new Feedback */
    
	/***
	 * creates emty feedback
	 */
	public Feedback () {
		errorCode=0;
		evaluation=0;
		msgTextPos=null;
		msgTextNeg=null;
		extra=null;
	}    
   
	/***
	 * creates feedback for fatal errors
	 * Params:
	 *   eCode = error code (0, 1, 2)
	 *   eMsg  =Error message
	 */
	public Feedback(int eCode, String eMsg) {
		errorCode= eCode;
		evaluation=0;
		msgTextNeg = eMsg;
		extra=null; 
	}
   
	/***
	 * creates general feedback
	 *   eCode = error code (0, 1, 2)
	 *   eval  = point obtained (0-100)
	 *   evalMsgPos = positive evaluation message
	 * 	 evalMsgNeg = negative evaluation message
	 *   evalExtra = additional feedback, for example the result of sql query
	 */
	public Feedback(int eCode, int eval, String evalMsgPos, String evalMsgNeg, String evalExtra) {
		errorCode=eCode;
		evaluation=eval;
		msgTextPos=evalMsgPos;
		msgTextNeg=evalMsgNeg;		
		extra=evalExtra;
	}

   
	public int getErrorCode() {
		return errorCode;
	}    
   
	public int getEvaluation() {
		return evaluation;
	}
 
	public String getMsgTextPos() {
		return msgTextPos;
	}
	
	public String getMsgTextNeg() {
		return msgTextNeg;
	}
    
	public String getExtra() {
		return extra;
	}    
    
	public void setErrorCode(int e) {
	  errorCode=e;
	}  
    
	public void setMsgTextPos(String msg) {
	  msgTextPos = msg;
	} 
	
	public void setMsgTextNeg(String msg) {
	  msgTextNeg = msg;
	}
    
	public boolean causedFatalError () {
	  return errorCode>1;
	}
    
	public boolean wasPassed(int cutoff) {
	   return evaluation>=cutoff;
	}   
	public String toString(){
		return "errorcode:"+errorCode + "\n"+
			   "evaluation:"+evaluation + "\n"+
			   "positive message: "+msgTextPos +"\n"+ 
			   "negative message: "+msgTextNeg +"\n"+
			   "extramessage: "+"\n"+extra;
	}
}
