/*
 * Created on 8.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.taskdefinition.util;

import java.util.ArrayList;

import fi.hy.eassari.taskdefinition.util.datastructures.BlankDTO;
import fi.hy.eassari.taskdefinition.util.datastructures.TaskDTO;

/**
 * Parses blanks from blank fill task. This class will also form text
 * after and before a blank spot.
 * 
 * @author Sami Termonen
 */
public class BlanksParser {
	private static final int WORDS = 5;
	
	/**
	 * Parses blanks. Uses an helper method for determining word list and blanks. Then
	 * specified amount of words is taken before and after blank.
	 * 
	 * @param dto contains text to parse in a HashMap
 	 * @return TaskDTO the same dto but now HashMap contains keys after, before and blank
	 */
	public TaskDTO parse(TaskDTO dto){		
		String task = (String)dto.get("text");
		ArrayList words = new ArrayList();
		ArrayList blanks = separateWords(words, task);
		//dto.setList(blanks);
		
		int length = blanks.size();
		int blankIndex = 0;
		StringBuffer sb = new StringBuffer();
		String temp = null;
		BlankDTO b = null;
		boolean isBegin = false;
		boolean isEnd = false;
		
		for(int i = 0; i < length; i++){
			b = ((BlankDTO)blanks.get(i));
			blankIndex = b.getIndex();

			dto.set("blank" + (i + 1), b.getBlank());

			isBegin = false;
			isEnd = false;
			
			if(blankIndex == 0){
				dto.set("before" + (i + 1), "");
				isBegin = true;
			}
			else if(blankIndex == words.size() - 1){
				isEnd = true;
			}
						
			if(!isBegin){			
				if(blankIndex - WORDS < 0){
					for(int x = 0; x < blankIndex; x++){
						temp = (String)words.get(x);			
					
						sb.append(temp);
					}
				}
				else if(blankIndex - WORDS >= 0){
					for(int x = blankIndex - WORDS; x < blankIndex; x++){
						temp = (String)words.get(x);			
					
						sb.append(temp);
					}
				}
				
				dto.set("before" + (i + 1), sb.toString());
				sb = new StringBuffer();
			}

			if(!isEnd){
				if((blankIndex + WORDS) > words.size()){
					for(int x = blankIndex; x < words.size(); x++){
						temp = (String)words.get(x);			
					
						sb.append(temp);
					}
				}
				else {			
					for(int x = blankIndex; x < (blankIndex + WORDS); x++){
						temp = (String)words.get(x);			
					
						sb.append(temp);
					}
				}
	
				dto.set("after" + (i + 1), sb.toString());
				sb = new StringBuffer();
			}
		}
		
		return dto;
	}
	
	/**
	 * Helper method for separating words from the given text.
	 * 
	 * @param words ArrayList where words are put
	 * @param task String to parse
	 * @return ArrayList containing blankDTO objects
	 */
	private ArrayList separateWords(ArrayList words, String task){
		int length = task.length();
		StringBuffer sb = new StringBuffer();
		char tmp;
		char tmp2;
		//ArrayList words = new ArrayList();
		String temp = null;
		StringBuffer tempBuf = null;
		ArrayList blanks = new ArrayList();
		
		for(int i = 0; i < length; i++){
			tmp = task.charAt(i);
			
			if(tmp == ' ' || tmp == '.' || tmp == ',' || tmp == ':' || tmp == ';' || i == (length - 1)){
				sb.append(tmp);
				temp = sb.toString();
				
				if(temp.indexOf("[[") != -1){
					tempBuf = new StringBuffer();
					BlankDTO b = new BlankDTO();
					
					for(int x = 0; x < temp.length(); x++){
						tmp2 = temp.charAt(x);
						
						if(tmp2 == '[' || tmp2 == ']'){
							continue;	
						}
						else{
							tempBuf.append(tmp2);
						}
					}
					b.setIndex(words.size());
					b.setBlank(tempBuf.toString());
					blanks.add(b);
				}
				else{		
					words.add(temp);
				}
				
				sb = new StringBuffer(); 
			}
			else{
				sb.append(tmp);
			}
		}
		
		return blanks;		
	}
}
