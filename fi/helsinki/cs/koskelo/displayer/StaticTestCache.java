package fi.helsinki.cs.koskelo.displayer;

import java.sql.SQLException;

import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.CacheException;

public class StaticTestCache implements AttributeCache{

    public String getAttribute (String objType, 
				String objID, 
				String attributename, 
				String language) throws CacheException{
	
	if (attributename.equals("taskDescription")) {
	    return("Laske yhteen 1,2 ja 3.");
	}
	if (attributename.equals("publicInput")) {
	    return("4, 5, 6");
	}
	if (attributename.equals("inputHeader")) {
	    return("Syötteet");
	}  

	return("");
	    
    }



    public int attribCount(String objectid, 
			   String attributename, 
			   String language) throws CacheException, 
						   SQLException{
	
		return 0;
    }

    public void saveAnswer(String userid, 
			   String courseid, 
			   String moduleid,
			   int seqno, 
			   int trynumber, 
			   int correctness, 
			   String whenanswered, 
			   String answer, 
			   String language) throws CacheException {
    }
	
    public String taskType (String taskid) throws CacheException {
		
	return null;
    }

    public String getTaskID (String courseid, 
			     String moduleid, 
			     int seqno) throws CacheException {
	
		return null;							   }

    public boolean languageDefined (String taskid, 
				    String language) throws CacheException {
		
		return true;
    }
}
