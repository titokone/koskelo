/*
 * Created on 19.4.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fi.hy.eassari.tests;

import junit.framework.TestCase;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;


/**
 * Test class for testing communication between components.
 * 
 * @author Teemu Andersson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SystemTest extends TestCase {



	/**
	 * Constructor for SystemTest.
	 * @param arg0
	 */
	public SystemTest(String arg0) {
		super(arg0);
	}

	public void setUp(){

	}


	public void testBlankfill() throws Exception{
		
		ServletRunner sr = new ServletRunner( "web.xml" );      
		ServletUnitClient client = sr.newClient();       
		WebResponse response=null;
		        

		try {
			response=client.getResponse( "http://localhost/Answer2" ); 
		} catch (Exception e) {
			System.out.println("EXCEPTION :"+e);            
		}
		
		System.out.println(response.getText());
	}
	
	public void testBlankFill() throws Exception{
		
		ServletRunner sr = new ServletRunner( "web.xml" );      
		ServletUnitClient client = sr.newClient(); 
		WebRequest request=null;      
		WebResponse response=null;
		
		try{
			request   = new PostMethodWebRequest("http://localhost/Answer2");
			request.setParameter("cid", "Systemtest100");
			request.setParameter("mid","Systemtest10");
			request.setParameter("tno","1");
			request.setParameter("lang", "EN");
			request.setParameter("option1", "the");
			request.setParameter("tasktype", "blankfilltask");
			request.setParameter("gapcount", "1");
			response=client.getResponse(request);
		} catch(Exception e){
			System.out.println("EXCEPTION :"+e);
		}
		System.out.println(response.getText());
		System.out.println(response.getURL());
	}
	
	public void testBlankFill2() throws Exception{
		
		ServletRunner sr = new ServletRunner( "web.xml" );      
		ServletUnitClient client = sr.newClient(); 
		WebRequest request=null;      
		WebResponse response=null;
		
		try{
			request   = new PostMethodWebRequest("http://localhost/Answer2");
			request.setParameter("cid", "Systemtest100");
			request.setParameter("mid","Systemtest10");
			request.setParameter("tno","1");
			request.setParameter("lang", "FI");
			request.setParameter("option1", "on");
			request.setParameter("tasktype", "blankfilltask");
			request.setParameter("gapcount", "2");
			response=client.getResponse(request);
		} catch(Exception e){
			System.out.println("EXCEPTION :"+e);
		}
		System.out.println(response.getText());
		System.out.println(response.getURL());
	}


	public void testOption() throws Exception{
		
		ServletRunner sr = new ServletRunner( "web.xml" );      
		ServletUnitClient client = sr.newClient(); 
		WebRequest request=null;      
		WebResponse response=null;
		
		try{
			request   = new PostMethodWebRequest("http://localhost/Answer2");
			request.setParameter("cid", "Systemtest100");
			request.setParameter("mid","Systemtest10");
			request.setParameter("lang", "FI");
			request.setParameter("tno","2");
			request.setParameter("tasktype", "optiontask");
			request.setParameter("option1", "1");
			request.setParameter("optioncount", "3");
			response=client.getResponse(request);
		} catch(Exception e){
			System.out.println("EXCEPTION :"+e);
		}
		String body=response.getText();
		System.out.println(response.getText());
		
		assertTrue(body.indexOf("failbox")==-1);
		assertTrue(body.indexOf("okbox")!=-1);
	}

	public void testOption2() throws Exception{
		
		ServletRunner sr = new ServletRunner( "web.xml" );      
		ServletUnitClient client = sr.newClient(); 
		WebRequest request=null;      
		WebResponse response=null;
		
		try{
			request   = new PostMethodWebRequest("http://localhost/Answer2");
			request.setParameter("cid", "Systemtest100");
			request.setParameter("mid","Systemtest10");
			request.setParameter("tno","2");
			request.setParameter("option1", "1");
			request.setParameter("option3", "3");
			request.setParameter("tasktype", "optiontask");
			request.setParameter("optioncount", "3");

			response=client.getResponse(request);
		} catch(Exception e){
			System.out.println("EXCEPTION :"+e);
		}
		String body=response.getText();
		System.out.println(response.getText());
		
		assertTrue(body.indexOf("failbox")==-1);
		assertTrue(body.indexOf("okbox")!=-1);
	}
	
	public void testOption3() throws Exception{
		
		ServletRunner sr = new ServletRunner( "web.xml" );      
		ServletUnitClient client = sr.newClient(); 
		WebRequest request=null;      
		WebResponse response=null;
		
		try{
			request   = new PostMethodWebRequest("http://localhost/Answer2");
			request.setParameter("cid", "Systemtest100");
			request.setParameter("mid","Systemtest10");
			request.setParameter("tno","3");
			request.setParameter("tasktype", "optiontask");
			request.setParameter("option2", "2");
			request.setParameter("option3","3");
			request.setParameter("optioncount", "3");
			response=client.getResponse(request);
		} catch(Exception e){
			System.out.println("EXCEPTION :"+e);
		}
		String body=response.getText();
		System.out.println(response.getText());
		
		assertTrue(body.indexOf("failbox")!=-1);
		assertTrue(body.indexOf("okbox")==-1);
		assertTrue(body.indexOf("Try again")!=-1);
		assertTrue(body.indexOf("Fine solution")==-1);
	}
	
	public void testOrdering() throws Exception{
		
		ServletRunner sr = new ServletRunner( "web.xml" );      
		ServletUnitClient client = sr.newClient(); 
		WebRequest request=null;      
		WebResponse response=null;
		
		try{
			request   = new PostMethodWebRequest("http://localhost/Answer2");
			request.setParameter("cid", "Systemtest100");
			request.setParameter("mid","Systemtest10");
			request.setParameter("tno","4");
			request.setParameter("tasktype", "orderingtask");
			request.setParameter("lang", "FI");
			request.setParameter("posof1", "2");
			request.setParameter("posof2","3");
			request.setParameter("posof3","1");
			request.setParameter("optioncount", "3");
			response=client.getResponse(request);
		} catch(Exception e){
			System.out.println("EXCEPTION :"+e);
		}
		String body=response.getText();
		System.out.println(response.getText());
		
		assertTrue(body.indexOf("failbox")==-1);
		assertTrue(body.indexOf("okbox")!=-1);
		assertTrue(body.indexOf("Hienosti ratkaistu")!=-1);
		assertTrue(body.indexOf("Oikein")!=-1);
		assertTrue(body.indexOf("liian myöhäinen")==-1);
		assertTrue(body.indexOf("liian aikainen")==-1);
	}


	public void testOrdering2() throws Exception{
		
		ServletRunner sr = new ServletRunner( "web.xml" );      
		ServletUnitClient client = sr.newClient(); 
		WebRequest request=null;      
		WebResponse response=null;
		
		try{
			request   = new PostMethodWebRequest("http://localhost/Answer2");
			request.setParameter("cid", "Systemtest100");
			request.setParameter("mid","Systemtest10");
			request.setParameter("tno","4");
			request.setParameter("tasktype", "orderingtask");
			request.setParameter("lang", "EN");
			request.setParameter("posof1", "2");
			request.setParameter("posof2","3");
			request.setParameter("posof3","1");
			request.setParameter("optioncount", "3");
			response=client.getResponse(request);
		} catch(Exception e){
			System.out.println("EXCEPTION :"+e);
		}
		String body=response.getText();
		System.out.println(response.getText());
		
		assertTrue(body.indexOf("failbox")!=-1);
		assertTrue(body.indexOf("okbox")==-1);
		assertTrue(body.indexOf("Try again")!=-1);
		assertTrue(body.indexOf("Fine solution")==-1);
		assertTrue(body.indexOf("Correct2")!=-1);
		assertTrue(body.indexOf("liian myöhäinen")==-1);
		assertTrue(body.indexOf("liian aikainen")==-1);
	}
}