public class TTK91TaskCriteria{

	// Vakioita konstruktoria varten, m‰‰rittelev‰t vertailut
	public final int LESS = 0;
	public final int LESSEQ = 1;
	public final int GREATER = 2;
	public final int GREATEREQ = 3;
	public final int EQUAL = 4;
	public final int NOTEQUAL = 5;

	// Olion sis‰iset muuttujat
	
	private int comparison;
	private String firstComparable;
        private String secondComparableB; //T‰nne kommentti
	


	/** Returns an empty TTK91Criteria object for wich
	 * all options are still unset. Use setComparison, 
	 * setFirstComparable and setSecondComparable to set them.
	 */
	
	public TTK91TaskCriteria() {

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

	}// TTK91TaskCriteria(String criteria)

	public TTK91TaskCriteria(
			String firstComparable,
			int comparator,
			String secondComparable
			) {
	
		this.firstComparable = firstComparable;
		this.secondComparable = secondComparable;
		
		//FIXME check for validity of the value
		this.comparator = comparator;
	}
	
	/** Returns a String representation of TTK91Criteria
	 *
	 * @return Standardized string representation of 
	 * a TTK91Criteria. This string given to the constructor
	 * would parse into exactly similar criteria object.
	 */
	
	public String toString() {

		return "Unimplemented";

	}//toString

	/**
	 * Internal method used for trying to parse a TTK91 criteria
	 * object. Given a string representation in specified form,
	 * tries to extract firstComparable, secondComparable and
	 * comprarison.
	 */
	
	private void parseCriteria( String criteria ) {

	}//parseCriteria
	
}// class
