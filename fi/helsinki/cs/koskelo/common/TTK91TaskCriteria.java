/******************************************************************
* @author       Harri Tuomikoski, Koskelo-projekti.
* @version      0.2
******************************************************************/

package fi.helsinki.cs.koskelo.common;
import java.lang.String.*;

public class TTK91TaskCriteria{

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
 * @param criteria TTK91-teht‰v‰n kriteeri muodossa: "(vertailtava_1
 * looginen_operaatio vertailtava_2);" tai "(L, vertailtava_1
 * looginen_operaatio vertailtava_2);".
 * @param isComparable true ilmoittaa ett‰ kriteeri sis‰lt‰‰
 * loogisen operaation, false taas ilmoittaa ett‰ kyseess‰ on pari
 * ilman loogista operaatiota muodossa: "(parametri_1, parameteri_2);"
 * tai "(L, parametri_1, parameteri_2);". L ilmoittaa kriteerin
 * olevan laadullinen.
 * @see fi.helsinki.cs.koskelo.common.TTK91Constant
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
 * @param criteria TTK91-teht‰v‰n kriteeri muodossa: "(vertailtava_1
 * looginen_operaatio vertailtava_2);" tai "(L, vertailtava_1
 * looginen_operaatio vertailtava_2);". L ilmoittaa kriteerin
 * olevan laadullinen.
 * @see fi.helsinki.cs.koskelo.common.TTK91Constant
 ******************************************************************/

 public TTK91TaskCriteria(String criteria)
		throws InvalidTTK91CriteriaException {

     this(criteria, true);

 }// TTK91TaskCriteria(String criteria)



 /******************************************************************
 * Asettaa kriteerin vertailun tyypin; onko laatua vai oikeellisuutta
 * ilmaiseva kriteeri.
 * @param quality true jos kriteeri on laadullinen.
 ******************************************************************/

 public void setQuality(boolean quality) {

  this.quality = quality;

 }//setQuality



 /******************************************************************
 * Asettaa vertailuoperaattorin.
 * @param comparator Yksi luokan TTK91Constant julkisista vertailuvakioista.
 * @throws InvalidTTK91CriteriaException
 * @see fi.helsinki.cs.koskelo.common.TTK91Constant
 ******************************************************************/


 public void setComparator(int comparator)
 				throws InvalidTTK91CriteriaException {

  checkComparator(comparator);
  this.comparator = comparator;

 }//setComparator



 /******************************************************************
 * Asettaa vasemmanpuoleisen vertailtavan. Heitt‰‰ poikkeuksen jos
 * parametrina on null, tyhj‰ tai pelk‰st‰‰n sulkuja tai puolipisteit‰
 * sis‰lt‰v‰ merkkijono.
 * @param comparable Merkkijonoesitys vertailtavasta.
 * @throws InvalidTTK91CriteriaException
 ******************************************************************/

 public void setFirstComparable(String comparable)
             throws InvalidTTK91CriteriaException {

     String cleanedString = cleanString(comparable);

     if(cleanedString == null || cleanedString.length() == 0) {

	  throw new InvalidTTK91CriteriaException("Invalid first comparable");

     }//if

  this.firstComparable = comparable;

 }//setFirstComparable



 /******************************************************************
 * Asettaa oikeanpuoleisen vertailtavan. Heitt‰‰ poikkeuksen jos
 * parametrina on null, tyhj‰ tai pelk‰st‰‰n sulkuja tai puolipisteit‰
 * sis‰lt‰v‰ merkkijono.
 * @param comparable Merkkijonoesitys vertailtavasta.
 * @throws InvalidTTK91CriteriaException
 ******************************************************************/


 public void setSecondComparable(String comparable)
             throws InvalidTTK91CriteriaException {

  String cleanedString = cleanString(comparable);

  if(cleanedString == null || cleanedString.length() == 0) {

      throw new InvalidTTK91CriteriaException("Invalid second comparable");

  }//if

  this.secondComparable = comparable;

 }//setSecondComparable



 /******************************************************************
 * Palauttaa tiedon kriteerin tyypist‰.
 * @return true jos kriteeri on laadullinen, muuten false.
 ******************************************************************/

 public boolean getQuality() {

	 return this.quality;

 }//getQuality



 /******************************************************************
 * Palauttaa vertailuoperaattorin.
 * @return Vertailuoperaattorin kokonaislukuesitys, yksi m‰‰ritellyist‰
 * julkisista kokonaislukuvakioista.
 * @see fi.helsinki.cs.koskelo.common.TTK91Constant
 ******************************************************************/

 public int getComparator() {

	 return this.comparator;

 }//getComparator

 /******************************************************************
 * Palauttaa vertailuoperaattorin symbolin.
 * @return Vertailuoperaattorin symboli String-oliona.
 * @throws InvalidTTK91CriteriaException
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

	 return this.secondComparable;

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
 * Parsitaan loogisen operaation sis‰lt‰v‰st‰ merkkijonoesityksest‰
 * kaikki parametrit erilleen.
 ******************************************************************/

 private void parseComparableCriteria(String criteria)
			  throws InvalidTTK91CriteriaException {

  String[] result = criteria.split(","); //Erota parametrit yksi ja kaksi
                                         //Saattaa olla vain yksi

  if( result.length == 1 ) { // Vain yksi parametri

   String cleanParameter = cleanString(result[0]);
   findComparableParameterValues(cleanParameter);

  } else if( result.length == 2 ) { // Kaksi parametria

   //Tutki laatuparametrin syntaksi

   String cleanParameter_1 = cleanString(result[0]);

   if( cleanParameter_1.equals("L") ) {

	setQuality(true);

   } else { //Laatuparametri puuttui vaikka se piti lˆyty‰.

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



 /******************************************************************
 * Parsitaan merkkijonoesityksest‰ joka ei sis‰ll‰ loogista
 * operaatiota kaikki parametrit erilleen.
 ******************************************************************/

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

    } else { //Laatuparametri puuttui vaikka se piti lˆyty‰.

	throw new InvalidTTK91CriteriaException("Invalid quality indicator");

    }//else

    //Aseta parametrit kaksi ja kolme.
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



 /******************************************************************
 * Etsit‰‰n oikea looginen operaatio ja asetetaan kaikki
 * parametrit luokkamuuttujiin.
 ******************************************************************/

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



 /******************************************************************
 * Palauttaa merkkijonoesityksen jonka muoto riippuu asetetuista
 * parametreist‰.
 * @return Kriteerin merkkijonoesitys muodossa: "(L, param_1 looginen_op param_2");".
 * @return Kriteerin merkkijonoesitys muodossa: "(param_1 looginen_op param_2");".
 * @return Kriteerin merkkijonoesitys muodossa: "(L, param_1, param_2");".
 * @return Kriteerin merkkijonoesitys muodossa: "(param_1, param_2");".
 * @return null jos kriteerin arvoja ei asetettu.
 ******************************************************************/

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



 /******************************************************************
 * Palauttaa merkkijonona kriteerin loogisen operaation. Operaatiot
 * m‰‰ritelty luokassa TTK91Constant.
 ******************************************************************/

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
