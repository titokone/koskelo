/*
 * Created on 13.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.tests;

import junit.framework.TestCase;
import fi.hy.eassari.displayers.BlankfillDisplayer;
import fi.hy.eassari.displayers.BlankfillTestCache;
import fi.hy.eassari.showtask.trainer.AttributeCache;
import fi.hy.eassari.showtask.trainer.DisplayerInterface;

/**
 * Testclass for BlankfillDisplayer-class.
 * 
 * @author tjvander
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BlankfillDisplayerTest extends TestCase {
	
	DisplayerInterface disp=new BlankfillDisplayer();
	String taskbody="";
	AttributeCache cache=new BlankfillTestCache();
	String hidden="<input type=hidden name=\"stundet_id\" value=\"007\">";


	/**
	 * Constructor for BlankfillDisplayerTest.
	 * @param arg0
	 */
	
	
	public BlankfillDisplayerTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		
		disp.registerCache(cache);
	}

	public void testGetSettingEn() {
		
		disp.init(null, "EN", null);
		
		try{
			taskbody=disp.getSetting(null, null, hidden, true);
		}catch(Exception ex){}
		
		assertTrue(taskbody.indexOf("blanks")==-1);
		assertTrue(taskbody.indexOf("option1")!=-1);
		assertTrue(taskbody.indexOf("option6")!=-1);
		assertTrue(taskbody.indexOf("submit")!=-1);
		assertTrue(taskbody.indexOf("007")!=-1);
		assertTrue(taskbody.indexOf("[[")==-1);
		assertTrue(taskbody.indexOf("]]")==-1);
	}

	public void testGetSettingEn2() {
		
		disp.init(null, "EN", null);
		
		try{
			taskbody=disp.getSetting(null, null, hidden, false);
		}catch(Exception ex){}
		
		assertTrue(taskbody.indexOf("blanks")==-1);
		assertTrue(taskbody.indexOf("option1")!=-1);
		assertTrue(taskbody.indexOf("option6")!=-1);
		assertTrue(taskbody.indexOf("submit")==-1);
		assertTrue(taskbody.indexOf("007")!=-1);
		assertTrue(taskbody.indexOf("[[")==-1);
		assertTrue(taskbody.indexOf("]]")==-1);
	}
	
	public void testGetSettingFi() {
		
		disp.init(null, "FI", null);
		
		try{
			taskbody=disp.getSetting(null, null, hidden, true);
		}catch(Exception ex){}
		
		assertTrue(taskbody.indexOf("tekstissä")==-1);
		assertTrue(taskbody.indexOf("option1")!=-1);
		assertTrue(taskbody.indexOf("option4")!=-1);
		assertTrue(taskbody.indexOf("lähetysnappi")!=-1);
		assertTrue(taskbody.indexOf("007")!=-1);
		assertTrue(taskbody.indexOf("[[")==-1);
		assertTrue(taskbody.indexOf("]]")==-1);
	}

	public void testGetSettingFi2() {
		
		disp.init(null, "FI", null);
		
		try{
			taskbody=disp.getSetting(null, null, hidden, false);
		}catch(Exception ex){}
		
		
		assertTrue(taskbody.indexOf("option1")!=-1);
		assertTrue(taskbody.indexOf("option4")!=-1);
		assertTrue(taskbody.indexOf("lähetysnappi")==-1);
		assertTrue(taskbody.indexOf("007")!=-1);
		assertTrue(taskbody.indexOf("[[")==-1);
		assertTrue(taskbody.indexOf("]]")==-1);
	}
	
	public void testGetSettingWithParameters(){
		
		disp.init(null, "FI", null);
		
		String[] ans=new String[4];
		ans[0]="eka";
		ans[1]="toka";
		ans[2]="kolmas";
		ans[3]="vika";
		
		try{
			taskbody=disp.getSetting(ans, null, hidden, false);
		}catch(Exception ex){}
		
		
		assertTrue(taskbody.indexOf("option1")!=-1);
		assertTrue(taskbody.indexOf("option4")!=-1);
		assertTrue(taskbody.indexOf("eka")!=-1);
		assertTrue(taskbody.indexOf("toka")!=-1);
		assertTrue(taskbody.indexOf("kolmas")!=-1);
		assertTrue(taskbody.indexOf("vika")!=-1);
		assertTrue(taskbody.indexOf("id=\"option1\" size=\"20\" value=\"eka\"")!=-1);
		assertTrue(taskbody.indexOf("id=\"option4\" size=\"20\" value=\"vika\"")!=-1);
		assertTrue(taskbody.indexOf("lähetysnappi")==-1);
		assertTrue(taskbody.indexOf("007")!=-1);
		assertTrue(taskbody.indexOf("[[")==-1);
		assertTrue(taskbody.indexOf("]]")==-1);
			
	}

}



