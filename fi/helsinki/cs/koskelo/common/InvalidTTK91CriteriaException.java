/******************************************************************
* @author     Harri Tuomikoski, Koskelo-projekti.
* @version    0.1
******************************************************************/

package fi.helsinki.cs.koskelo.common;

public class InvalidTTK91CriteriaException extends Exception {

 public InvalidTTK91CriteriaException() {

  super();

 }//InvalidCriteriaException

 /******************************************************************
 * @param Ilmoittaa syyn poikkeukseen.
 * @see java.lang.String
 ******************************************************************/

 public InvalidTTK91CriteriaException(String msg) {

  super(msg);

 }//InvalidCriteriaException()

}//class

