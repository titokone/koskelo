package fi.helsinki.cs.koskelo.test;

import java.util.*;
import junit.framework.*;
import fi.helsinki.cs.koskelo.displayer.*;

public class StaticTTK91DisplayerTest extends TestCase {

	String [] answer = {"LOAD R1, =3; LOAD R2, =2"};
	String hiddens = ("<input type=\"hidden\" name=\"test\"" 
										+"value=\"test\">");
	String testString ="";
	public void testgetSetting() {

		TestStaticDisplayer test = new TestStaticDisplayer();
		try{
			testString = test.getSetting (answer,"",hiddens,false);
			}
		catch (Exception e){}
		Assert.assertEquals
			(testString, 
			 ("<form action=Answer2.do2" + 
				" method=\"post\" name=\"staticttk91task\"" +
				"id=\"staticttk91task\">"
				+fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.
				getHTMLElementTask("Laske yhteen 1,2 ja 3.")
				+fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.
				getHTMLElementInput("Syötteet","4, 5, 6")
				+fi.helsinki.cs.koskelo.displayer.TTK91DisplayerUtils.
				getHTMLElementAnswerBox(answer, 20 ,50)
				+hiddens
				+"<input type=\"hidden\" name=\"tasktype\"" 
				+"value=\"staticttk91task\">"
				+"</form>"));
		
	}
}
