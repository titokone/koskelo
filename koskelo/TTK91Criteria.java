public class TTK91Criteria{

	private int comparison;
	private String firstComparable;
	private String secondComparableB;
	


	/** Returns an empty TTK91Criteria object for wich
	 * all options are still unset. Use setComparison, 
	 * setFirstComparable and setSecondComparable to set them.
	 */
	
	public TTK91Criteria() {

	}//TTK91Criteria()

	/**
	 * Returns an ready TTK91Criteria object if @param criteria
	 * is correct criteria string.
	 * 
	 *@param criteria Standardized string representation of a
	 * criteria. Exeption NAMEHERE is thrown if string doesn't
	 * represent a correct criteria.
	 */
	
	public TTK91Criteria( String criteria ){

	}// TTK91Criteria( String criteria )

	
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

	}//patseCriteria
	
}// class
