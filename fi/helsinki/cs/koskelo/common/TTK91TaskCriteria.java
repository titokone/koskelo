//TODO: Kommentit ym. sössö

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


 /** Returns an empty TTK91Criteria object for wich
  * all options are still unset. Use setComparison,
  * setFirstComparable and setSecondComparable to set them.
  */

 public TTK91TaskCriteria() {

  this.comparator = this.INVALID;
  this.firstComparable = null;
  this.secondComparable = null;

 }//TTK91TaskCriteria()

 /**
  * Returns an ready TTK91Criteria object if @param criteria
  * is correct criteria string.
  *
  *@param criteria Standardized string representation of a
  * criteria. Exeption NAMEHERE is thrown if string doesn't
  * represent a correct criteria.
  */

 public TTK91TaskCriteria(String criteria){

  parseCriteria(criteria);

 }// TTK91TaskCriteria(String criteria)



 /**
  */

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



 /**
  */

 public boolean setComparator(int comparator) throws {

  checkComparator(comparator);
  this.comparator = comparator;

 }//setComparator



 /**
  */

 public boolean setFirstComparable(String comparable) {

  this.firstComparable = comparable;

 }//setFirstComparable



 /**
  */

 public boolean setSecondComparable(String comparable) {

  this.secondComparable = comparable;

 }//setSecondComparable




 /** Returns a String representation of TTK91Criteria
  *
  * @return Standardized string representation of
  * a TTK91Criteria. This string given to the constructor
  * would parse into exactly similar criteria object.
  */

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



 /**
  * Internal method used for trying to parse a TTK91 criteria
  * object. Given a string representation in specified form,
  * tries to extract firstComparable, secondComparable and
  * comprarison.
  */

 private void parseCriteria(String criteria) {

  //TODO: kriteerin parsinta

 }//parseCriteria



 /**
  */

 private void checkComparator(int comparator)
              throws InvalidTTK91CriteriaException {

  if(comparator < this.LESS ||
     comparator > this.NOTEQUAL) {

   throw new InvalidTTK91CriteriaException("Invalid comparator");

  }//if

 }//checkComparator



 /**
  */

 private String comparatorSymbol() {

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
  }//switch

  return symbol;

 }//comparatorSymbol

}// class