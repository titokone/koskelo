package fi.helsinki.cs.koskelo.test;

import java.util.*;
import junit.framework.*;
import fi.helsinki.cs.koskelo.displayer.*;

public class DisplayerUtilsTest extends TestCase {


	public void testgetHTMLElementTask() {

	Assert.assertEquals(fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.getHTMLElementTask("Teht�v� 1"), "<p class=\"assignment\"><strong>Teht�v� 1</strong></p>");
		
	}

	public void testgetHTMLElementInput() {

	}


	public void testHTMLElementAnswerBox() throws Exception{

	}

}
