/**
* @author       Harri Tuomikoski, Koskelo-projekti.
* @version      0.1
*/

package fi.helsinki.cs.koskelo.common;

import java.lang.String.*;

public class TTK91TaskCriteria{

 // Vakioita konstruktoria varten, m��rittelev�t vertailut
 public final int INVALID = -1; // Alustamaton vertailu.
 public final int LESS = 0; // <
 public final int LESSEQ = 1; // <=
 public final int GREATER = 2; // >
 public final int GREATEREQ = 3; // >=
 public final int EQUAL = 4; // =
 public final int NOTEQUAL = 5; // !=

 // Olion sis�iset muuttujat

 private int comparator; //Looginen operaattori
 private String firstComparable; //Ensimm�inen vertailtava
 private String secondComparable; //Toinen vertailtava
 private boolean quality; //True = kriteeri on laadullinen

 /**
 * Luo tyhj�n kriteeriolion.
 */

 public TTK91TaskCriteria() {

  this.comparator = this.INVALID;
  this.firstComparable = null;
  this.secondComparable = null;
  this.quality = false;

 }//TTK91TaskCriteria()

 /**
 * Luo kriteerin saamastaan merkkijonoesityksest�. 
 * @throws InvalidTTK91CriteriaException
 * @param criteria TTK91-teht�v�n kriteeri muodossa: vertailtava_1 looginen_operaatio vertailtava_2.
 * @see fi.helsinki.cs.koskelo.common.InvalidTTK91CriteriaException
 */

 public TTK91TaskCriteria(String criteria)
		throws InvalidTTK91CriteriaException {

  parseCriteria(criteria);

 }// TTK91TaskCriteria(String criteria)

 /**
 * Alustaa kriteerin annetuilla parametreilla.
 * @throws InvalidTTK91CriteriaException
 * @param firstComparable TTK91-teht�v�n ensimm�inen vertailtava.
 * @param comparator 
 * @see fi.helsinki.cs.koskelo.common.InvalidTTK91CriteriaException
 */

 public TTK91TaskCriteria(
		String firstComparable,
		int comparator,
		String secondComparable)
		throws InvalidTTK91CriteriaException {

  if(firstComparable == null ||
     secondComparable == null) {

      throw new InvalidTTK91CriteriaException("Invalid comparable");

  }//if

  setFirstComparable(firstComparable);
  setSecondComparable(secondComparable);

  setComparator(comparator);

 }//TTK91TaskCriteria



 public void setQuality(boolean quality) {

  this.quality = quality;

 }//setQuality



 public void setComparator(int comparator)
 				throws InvalidTTK91CriteriaException {

  checkComparator(comparator);
  this.comparator = comparator;

 }//setComparator



 public void setFirstComparable(String comparable)
             throws InvalidTTK91CriteriaException {

     String cleanedString = cleanString(comparable);

     if(cleanedString == null || cleanedString.length() == 0) {
	 throw new InvalidTTK91CriteriaException("Invalid comparator");
     }//if

  this.firstComparable = comparable;

 }//setFirstComparable



 public void setSecondComparable(String comparable)
             throws InvalidTTK91CriteriaException {

  String cleanedString = cleanString(comparable);

  if(cleanedString == null || cleanedString.length() == 0) {
      throw new InvalidTTK91CriteriaException("Invalid comparator");
  }//if

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

  String parameter; //Parametrien v�liaikaiseen talletukseen

  if( result.length == 1 ) { // Jos oli vain yksi parametri

   String cleanCriteria = cleanString(result[0]);
   findSecondParameterValues(cleanCriteria);

  } else if( result.length == 2 ) { // Kaksi parametria

   //Tutki laatuparametrin syntaksi
   String cleanCriteriaPart1 = cleanString(result[0]);
   if( cleanCriteriaPart1.equals("L") ) {
	setQuality(true);
   } else {
	throw new InvalidTTK91CriteriaException("Invalid quality indicator");
   }//else

   //Tutki loogisen operaation syntaksi
   String cleanCriteriaPart2 = cleanString(result[1]);
   findSecondParameterValues(cleanCriteriaPart2);

  } else { // Laiton m��r� parametrej�

   throw new InvalidTTK91CriteriaException("Invalid amount of parameters");

  }//else

  return;

 }//parseCriteria



 private String cleanString(String dirty) {

  String clean;
  clean = dirty.replaceAll("&nbsp", "");
  clean = dirty.replaceAll("(", "");
  clean = dirty.replaceAll(")", "");
  clean = dirty.replaceAll(";", "");
  return clean;

 }//cleanString



 private void findSecondParameterValues(String cleanCriteria)
						throws InvalidTTK91CriteriaException {

  String[] parameters;

  parameters = cleanCriteria.split("<=");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.LESSEQ);
   return;
  }//if

  parameters = cleanCriteria.split("=<");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.LESSEQ);
   return;
  }//if

   parameters = cleanCriteria.split("<");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.LESS);
   return;
  }//if
 
  parameters = cleanCriteria.split(">=");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.GREATEREQ);
   return;
  }//if

  parameters = cleanCriteria.split("=>");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.GREATEREQ);
   return;
  }//if

  parameters = cleanCriteria.split(">");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.GREATER);
   return;
  }//if

  parameters = cleanCriteria.split("!=");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.NOTEQUAL);
   return;
  }//if

  parameters = cleanCriteria.split("=!");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.NOTEQUAL);
   return;
  }//if

  parameters = cleanCriteria.split("=");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(this.EQUAL);
   return;
  }//if

  throw new InvalidTTK91CriteriaException("Invalid or missing"+
                                          " logical operation");

 }//findSecondParameterValues



 public String toString() {

  String criteria;

  criteria = "(";

  if(this.quality == true) {
   criteria += "L,";
  }//if

  criteria += firstComparable;

  try {
   criteria += comparatorSymbol();
  }catch(InvalidTTK91CriteriaException e) {
	  return null; //Comparator == INVALID
  }//catch

  criteria += secondComparable;
  criteria += ");";

  return criteria;

 }//toString



 private String comparatorSymbol()
	 			throws InvalidTTK91CriteriaException {

  String symbol;

  switch(this.comparator) {

   case LESS:
         symbol = "<";
         break;
   case LESSEQ:
         symbol = "<=";
         break;
   case GREATER:
         symbol = ">";
         break;
   case GREATEREQ:
         symbol = ">=";
         break;
   case EQUAL:
         symbol = "=";
         break;
   case NOTEQUAL:
         symbol = "!=";
         break;
   default:
         throw new InvalidTTK91CriteriaException("Invalid comparator");

  }//switch

  return symbol;

 }//comparatorSymbol

}// class
