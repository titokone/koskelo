package fi.helsinki.cs.koskelo.test;

import java.util.*;
import junit.framework.*;
import fi.helsinki.cs.koskelo.displayer.*;

public class DisplayerUtilsTest extends TestCase {


	public void testgetHTMLElementTask() {

	Assert.assertEquals(fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.getHTMLElementTask("Tehtävä 1"), "<p class=\"assignment\"><strong>Tehtävä 1</strong></p>");
		
	}

	public void testgetHTMLElementInput() {

	Assert.assertEquals(fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.getHTMLElementInput("Tehtävä 1", "testi 1"), "<p class=\"input\"><strong>Tehtävä 1: testi 1</strong></p>");
	}


	public void testHTMLElementAnswerBox(){
	Assert.assertEquals(
			fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.getHTMLElementAnswerBox(3,4), 
			"<textarea name=\"answer\" cols=\"4\" rows=\"3\"></textarea><br>"
			);
	String[] tmp = {"Foobar"};
	Assert.assertEquals(
			fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.getHTMLElementAnswerBox(tmp,3,4),
			"<textarea name=\"answer\" cols=\"4\" rows=\"3\">Foobar</textarea><br>"
			);

	}
}
