//TODO: Kommentit ym. sössö
import java.lang.String.*;

package fi.helsinki.cs.koskelo.common;

public class TTK91TaskCriteria{

 // Vakioita konstruktoria varten, määrittelevät vertailut
 public final int INVALID = -1; // Alustamaton vertailu.
 public final int LESS = 0; // <
 public final int LESSEQ = 1; // <=
 public final int GREATER = 2; // >
 public final int GREATEREQ = 3; // >=
 public final int EQUAL = 4; // =
 public final int NOTEQUAL = 5; // !=

 // Olion sisäiset muuttujat

 private int comparator; //Looginen operaattori
 private String firstComparable; //Ensimmäinen vertailtava
 private String secondComparable; //Toinen vertailtava
 private boolean quality; //True = kriteeri on laadullinen



 public TTK91TaskCriteria() {

  this.comparator = this.INVALID;
  this.firstComparable = null;
  this.secondComparable = null;

 }//TTK91TaskCriteria()



 public TTK91TaskCriteria(String criteria){

  parseCriteria(criteria);

 }// TTK91TaskCriteria(String criteria)



 public TTK91TaskCriteria(
		String firstComparable,
		int comparator,
		String secondComparable
		) {

  this.firstComparable = firstComparable;
  this.secondComparable = secondComparable;

  checkComparator(comparator);
  this.comparator = comparator;

  }//else

 }//TTK91TaskCriteria



 public void setQuality(boolean quality) {

  this.quality = quality;

 }//setQuality



 public void setComparator(int comparator)
 				throws InvalidTTK91CriteriaException {

  checkComparator(comparator);
  this.comparator = comparator;

 }//setComparator



 public void setFirstComparable(String comparable) {

  this.firstComparable = comparable;

 }//setFirstComparable



 public void setSecondComparable(String comparable) {

  this.secondComparable = comparable;

 }//setSecondComparable



 private void checkComparator(int comparator)
              throws InvalidTTK91CriteriaException {

  if(comparator < this.LESS ||
     comparator > this.NOTEQUAL) {

   throw new InvalidTTK91CriteriaException("Invalid comparator");

  }//if

  return;

 }//checkComparator



 private void parseCriteria(String criteria)
			  throws InvalidTTK91CriteriaException {

  String[] result = criteria.split(","); //Erota parametrit yksi ja kaksi
                                         //Saattaa olla vain yksi

  String parameter; //Parametrien väliaikaiseen talletukseen

  if(result.length() == 1) { // Jos oli vain yksi parametri

   String cleanCriteria = cleanString(result[0]);
   findSecondParameterValues(cleanCriteria);

  } else if(result.length() == 2) { // Kaksi parametria

   //Tutki laatuparametrin syntaksi
   String cleanCriteriaPart1 = cleanString(result[0]);
   if(cleanCriteriaPart1.equals("L") {
	setQuality(true);
   } else {
	throw new InvalidTTK91CriteriaException("Invalid quality indicator");
   }//else

   //Tutki loogisen operaation syntaksi
   String cleanCriteriaPart2 = cleanString(result[1]);
   findSecondParameterValues(cleanCriteriaPart2);

  } else { // Laiton määrä parametrejä

   throw new InvalidTTK91CriteriaException("Invalid amount of parameters");

  }//else

  return;

 }//parseCriteria



 private String cleanString(String dirty) {

  String clean;
  clean = dirty.replace(' ', '');
  clean = dirty.replace('(', '');
  clean = dirty.replace(')', '');
  clean = dirty.replace(';', '');
  return clean;

 }//cleanString



 private void findSecondParameterValues(String cleanCriteria) {

  parameters = cleanCriteria.split("<");
  if(parameters.length() == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.LESS);
   return;
  }//if

  parameters = cleanCriteria.split("<=");
  if(parameters.length() == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.LESSEQ);
   return;
  }//if

  parameters = cleanCriteria.split("=<");
  if(parameters.length() == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.LESSEQ);
   return;
  }//if

  parameters = cleanCriteria.split(">");
  if(parameters.length() == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.GREATER);
   return;
  }//if

  parameters = cleanCriteria.split(">=");
  if(parameters.length() == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.GREATEREQ);
   return;
  }//if

  parameters = cleanCriteria.split("=>");
  if(parameters.length() == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.GREATEREQ);
   return;
  }//if

  parameters = cleanCriteria.split("=");
  if(parameters.length() == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.EQUAL);
   return;
  }//if

  parameters = cleanCriteria.split("!=");
  if(parameters.length() == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.NOTEQUAL);
   return;
  }//if

  parameters = cleanCriteria.split("=!");
  if(parameters.length() == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.NOTEQUAL);
   return;
  }//if

  throw new InvalidTTK91CriteriaException("Invalid or missing"+
                                          " logical operation");

 }//findSecondParameterValues



 public String toString() {

  if(comparator == this.INVALID) {

   throw new InvalidTTK91CriteriaException("Empty criteria");

  }//if

  String criteria;
  criteria = "(";

  if(this.quality == true) {
   criteria += "L,";
  }//if

  criteria += firstComparable;
  criteria += comparatorSymbol();
  criteria += secondComparable;
  criteria += ");";

  return criteria;

 }//toString



 private String comparatorSymbol() {
	 			throws InvalidTTK91CriteriaException {

  String symbol;

  switch(this.comparator) {

   case this.LESS:
         symbol = "<";
         break;
   case this.LESSEQ:
         symbol = "<=";
         break;
   case this.GREATER:
         symbol = ">";
         break;
   case this.GREATEREQ:
         symbol = ">=";
         break;
   case this.EQUAL:
         symbol = "=";
         break;
   case this.NOTEQUAL:
         symbol = "!=";
         break;
   default this.INVALID:
         throw new InvalidTTK91CriteriaException("Invalid comparator");

  }//switch

  return symbol;

 }//comparatorSymbol

}// class
