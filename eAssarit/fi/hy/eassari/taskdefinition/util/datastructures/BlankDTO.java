/*
 * Created on 26.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.taskdefinition.util.datastructures;

/**
 * This is a dto class for parsing blanks from the text.
 * 
 * @author Sami Termonen
 *
 */
public class BlankDTO {
	private int index = 0;
	private String blank = null;
	
	
	/**
	 * @return
	 */
	public String getBlank() {
		return blank;
	}

	/**
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param string
	 */
	public void setBlank(String string) {
		blank = string;
	}

	/**
	 * @param i
	 */
	public void setIndex(int i) {
		index = i;
	}

}
