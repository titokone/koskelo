/******************************************************************
* @author       Harri Tuomikoski, Koskelo-projekti.
* @version      0.2
******************************************************************/

package fi.helsinki.cs.koskelo.common;
import java.lang.String.*;

public class TTK91TaskCriteria{

 // Vakioita konstruktoria varten, m‰‰rittelev‰t vertailut
//  public final int INVALID = -1; // Alustamaton vertailu.
//  public final int LESS = 0; // <
//  public final int LESSEQ = 1; // <=
//  public final int GREATER = 2; // >
//  public final int GREATEREQ = 3; // >=
//  public final int EQUAL = 4; // =
//  public final int NOTEQUAL = 5; // !=
//  public final int NOTCOMPARABLE = 6; // Tulosteita varten joissa
//                                    // ei ole loogista operaattoria

 // Olion sis‰iset muuttujat

 private int comparator; //Looginen operaattori tai ilmaisu sen puuttumisesta
 private String firstComparable; //Ensimm‰inen vertailtava
 private String secondComparable; //Toinen vertailtava
 private boolean quality; //True = kriteeri on laadullinen

 /******************************************************************
 * Luo tyhj‰n kriteeriolion.
 ******************************************************************/

 public TTK91TaskCriteria() {

  this.comparator = TTK91Constant.INVALID;
  this.firstComparable = null;
  this.secondComparable = null;
  this.quality = false;

 }//TTK91TaskCriteria()



 /******************************************************************
 * Luo kriteerin saamastaan merkkijonoesityksest‰.
 * @throws InvalidTTK91CriteriaException
 * @param criteria TTK91-teht‰v‰n kriteeri muodossa: vertailtava_1 looginen_operaatio vertailtava_2.
 * @param isComparable true ilmoittaa ett‰ kriteeri sis‰lt‰‰ loogisen operaation,
 * false taas ilmoittaa ett‰ kyseess‰ on pari ilman loogista operaatiota.
 * @see fi.helsinki.cs.koskelo.common.InvalidTTK91CriteriaException
 ******************************************************************/

 public TTK91TaskCriteria(String criteria, boolean isComparable)
		throws InvalidTTK91CriteriaException {

  if(isComparable == false) {

   parseIncomparableCriteria(criteria);

  } else {

   parseComparableCriteria(criteria);

  }//else

 }// TTK91TaskCriteria(String criteria)



 /******************************************************************
 * Luo kriteerin saamastaan merkkijonoesityksest‰.
 * @throws InvalidTTK91CriteriaException
 * @param criteria TTK91-teht‰v‰n kriteeri muodossa: vertailtava_1 looginen_operaatio vertailtava_2.
 * @see fi.helsinki.cs.koskelo.common.InvalidTTK91CriteriaException
 ******************************************************************/

 public TTK91TaskCriteria(String criteria)
		throws InvalidTTK91CriteriaException {

     this(criteria, true);

 }// TTK91TaskCriteria(String criteria)

 /******************************************************************
 * Alustaa kriteerin annetuilla parametreilla.
 * @throws InvalidTTK91CriteriaException
 * @param firstComparable TTK91-teht‰v‰n ensimm‰inen vertailtava.
 * @param comparator
 * @see fi.helsinki.cs.koskelo.common.InvalidTTK91CriteriaException
 ******************************************************************/

 /*FIMXE: EI ILMEISESTI TARVITAKKAAN [HT]***************************

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

 *******************************************************************/



 /******************************************************************
 *Asettaa kriteerin vertailuoperaattorin.
 * @param quality true jos kriteeri on laadullinen.
 ******************************************************************/

 public void setQuality(boolean quality) {

  this.quality = quality;

 }//setQuality




/** Asettaa vertailuoperaattorin.
 * @param comparator Yksi luokan julkisista vertailuvakioista. Muiden kohdalla
 * heitet‰‰n poikkeus
 */

 public void setComparator(int comparator)
 				throws InvalidTTK91CriteriaException {

  checkComparator(comparator);
  this.comparator = comparator;

 }//setComparator



 /******************************************************************
 * Asettaa vasemmanpuoleisen vertailtavan. Heitt‰‰ poikkeuksen jos
 * parametrina on null, tyhj‰ tai pelkki‰ sulkuja sis‰lt‰v‰ merkkijono.
 * @param comparable Merkkijonoesitys vertailtavasta.
 ******************************************************************/

 public void setFirstComparable(String comparable)
             throws InvalidTTK91CriteriaException {

     String cleanedString = cleanString(comparable);

     if(cleanedString == null || cleanedString.length() == 0) {

	  throw new InvalidTTK91CriteriaException("Invalid comparator");

     }//if

  this.firstComparable = comparable;

 }//setFirstComparable



 /******************************************************************
 * Asettaa oikeanpuoleisen vertailtavan. Heitt‰‰ poikkeuksen jos
 * parametrina on null, tyhj‰ tai pelkki‰ sulkuja sis‰lt‰v‰ merkkijono.
 * @param comparable Merkkijonoesitys vertailtavasta.
 ******************************************************************/


 public void setSecondComparable(String comparable)
             throws InvalidTTK91CriteriaException {

  String cleanedString = cleanString(comparable);

  if(cleanedString == null || cleanedString.length() == 0) {

      throw new InvalidTTK91CriteriaException("Invalid comparator");

  }//if

  this.secondComparable = comparable;

 }//setSecondComparable



 /******************************************************************
 * Palauttaa kriteerin laadullisuusttypin.
 * @return true jos kriteeri on laadullinen, muuten false.
 ******************************************************************/

 public boolean getQuality() {

	 return this.quality;

 }//getQuality



 /******************************************************************
 * Palauttaa vertailuoperaattorin.
 * @return Vertailuoperaattorin kokonaislukuesitys, yksi m‰‰ritellyist‰
 * julkisista kokonaislukuvakioista.
 ******************************************************************/

 public int getComparator() {

	 return this.comparator;

 }//getComparator

 /******************************************************************
 * Palauttaa vertailuoperaattorin symbolin.
 * @return Vertailuoperaattorin symboli String-oliona.
 ******************************************************************/



 public String getComparatorSymbol()
	 throws InvalidTTK91CriteriaException {

	 return comparatorSymbol();

 }//getComparatorSymbol

 /******************************************************************
 * Palauttaa vasemmanpuoleisen vertailtavan.
 * @return Merkkijono, joka kuvaa vertailtavaa.
 ******************************************************************/

  public String getFirstComparable() {

	 return this.firstComparable;

  }//getFirstComparable



 /******************************************************************
 * Palauttaa oikeanpuoleisen vertailtavan.
 * @return Merkkijono, joka kuvaa vertailtavaa.
 ******************************************************************/

 public String getSecondComparable() {

	 return this.firstComparable;

 }//getSecondComparable



 /******************************************************************
 * Tarkistaa vertailuoperaattorin k‰yv‰n arvon.
 ******************************************************************/

 private void checkComparator(int comparator)
              throws InvalidTTK91CriteriaException {

  if(comparator < TTK91Constant.LESS ||
     comparator > TTK91Constant.NOTCOMPARABLE) {

   throw new InvalidTTK91CriteriaException("Invalid comparator");

  }//if

  return;

 }//checkComparator



 /******************************************************************
 * Parsitaan merkkijonoesityksest‰ TTK91TaskCriteriaolio.
 ******************************************************************/

 private void parseComparableCriteria(String criteria)
			  throws InvalidTTK91CriteriaException {

  String[] result = criteria.split(","); //Erota parametrit yksi ja kaksi
                                         //Saattaa olla vain yksi

  if( result.length == 1 ) { // Jos oli vain yksi parametri

   String cleanParameter = cleanString(result[0]);
   findComparableParameterValues(cleanParameter);

  } else if( result.length == 2 ) { // Kaksi parametria

   //Tutki laatuparametrin syntaksi

   String cleanParameter_1 = cleanString(result[0]);

   if( cleanParameter_1.equals("L") ) {

	setQuality(true);

   } else {

	throw new InvalidTTK91CriteriaException("Invalid quality indicator");

   }//else

   //Tutki loogisen operaation syntaksi
   String cleanParameter_2 = cleanString(result[1]);
   findComparableParameterValues(cleanParameter_2);

  } else { // Laiton m‰‰r‰ parametrej‰

   throw new InvalidTTK91CriteriaException("Invalid amount of parameters");

  }//else

  return;

 }//parseComparableCriteria



 private void parseIncomparableCriteria(String criteria)
                            throws InvalidTTK91CriteriaException {


  String cleanParameter_1 = null;
  String cleanParameter_2 = null;
  String cleanParameter_3 = null;

  String[] result = criteria.split(","); //Erota kolme parametria.
                                         //Saattaa olla vain kaksi
                                         //(jos L puuttuu).

  String parameter; //Parametrien v‰liaikaiseen talletukseen

  if( result.length == 2 ) { // Kaksi parametria lˆytyi

    cleanParameter_1 = cleanString(result[0]);
    cleanParameter_2 = cleanString(result[1]);

    setFirstComparable(cleanParameter_1);
    setSecondComparable(cleanParameter_2);

  } else if( result.length == 3 ) { // Kolme parametria

    //Tutki laatuparametrin syntaksi

    cleanParameter_1 = cleanString(result[0]);

    if( cleanParameter_1.equals("L") ) {

	setQuality(true);

    } else {

	throw new InvalidTTK91CriteriaException("Invalid quality indicator");

    }//else

    //Aseta loput parametrit
    cleanParameter_2 = cleanString(result[1]);
    cleanParameter_3 = cleanString(result[2]);

    setFirstComparable(cleanParameter_2);
    setSecondComparable(cleanParameter_3);

  } else { // Laiton m‰‰r‰ parametrej‰

    throw new InvalidTTK91CriteriaException("Invalid amount of parameters");

  }//else

  //Aseta ilmoitus loogisen operaation puuttumisesta
  setComparator(TTK91Constant.NOTCOMPARABLE);

  return;

 }//parseIncomparableCriteria



 private String cleanString(String dirty) {

  String clean;
  dirty = dirty.replaceAll("\\s", ""); // \s whitespace
  dirty = dirty.replaceAll("\\(", "");
  dirty = dirty.replaceAll("\\)", "");
  dirty = dirty.replaceAll(";", "");

  clean = dirty;

  return clean;

 }//cleanString



 private void findComparableParameterValues(String cleanCriteria)
						throws InvalidTTK91CriteriaException {

  String[] parameters;

  parameters = cleanCriteria.split("<=");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(TTK91Constant.LESSEQ);
   return;
  }//if

  parameters = cleanCriteria.split("=<");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(TTK91Constant.LESSEQ);
   return;
  }//if

   parameters = cleanCriteria.split("<");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(TTK91Constant.LESS);
   return;
  }//if

  parameters = cleanCriteria.split(">=");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(TTK91Constant.GREATEREQ);
   return;
  }//if

  parameters = cleanCriteria.split("=>");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(TTK91Constant.GREATEREQ);
   return;
  }//if

  parameters = cleanCriteria.split(">");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(TTK91Constant.GREATER);
   return;
  }//if

  parameters = cleanCriteria.split("!=");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(TTK91Constant.NOTEQUAL);
   return;
  }//if

  parameters = cleanCriteria.split("=!");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(TTK91Constant.NOTEQUAL);
   return;
  }//if

  parameters = cleanCriteria.split("==");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(TTK91Constant.EQUAL);
   return;
  }//if

  parameters = cleanCriteria.split("=");
  if(parameters.length == 2) {
   setFirstComparable(parameters[0]);
   setSecondComparable(parameters[1]);
   setComparator(TTK91Constant.EQUAL);
   return;
  }//if

  throw new InvalidTTK91CriteriaException("Invalid or missing"+
                                          " logical operation");

 }//findComparableParameterValues



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
	  return null; //Comparator == TTK91Constant.INVALID
  }//catch

  criteria += secondComparable;
  criteria += ");";

  return criteria;

 }//toString



 private String comparatorSymbol()
	 			throws InvalidTTK91CriteriaException {

  String symbol;

  switch(this.comparator) {

   case TTK91Constant.LESS:
         symbol = "<";
         break;
   case TTK91Constant.LESSEQ:
         symbol = "<=";
         break;
   case TTK91Constant.GREATER:
         symbol = ">";
         break;
   case TTK91Constant.GREATEREQ:
         symbol = ">=";
         break;
   case TTK91Constant.EQUAL:
         symbol = "==";
         break;
   case TTK91Constant.NOTEQUAL:
         symbol = "!=";
         break;
  case TTK91Constant.NOTCOMPARABLE:
         symbol = ",";
         break;
  default:
         throw new InvalidTTK91CriteriaException("Invalid comparator");

  }//switch

  return symbol;

 }//comparatorSymbol

}// class
