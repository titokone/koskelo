package fi.helsinki.cs.koskelo.test;

import java.util.*;
//import junit.framework.*;
import fi.helsinki.cs.koskelo.common.*;
import fi.helsinki.cs.koskelo.composer.*;

public class CriteriaTest// extends TestCase {
{


	private TTK91TaskCriteria a;
	private TTK91TaskCriteria b;
	private TTK91TaskCriteria c;
	private TTK91TaskCriteria d;
	private TTK91TaskCriteria e;
	private TTK91TaskCriteria f;
	private TTK91TaskCriteria g;
	
	public void setUp() throws InvalidTTK91CriteriaException{
		TTK91TaskCriteria a = new TTK91TaskCriteria("(A < B);");
		TTK91TaskCriteria b = new TTK91TaskCriteria("(L, A < B);");
		TTK91TaskCriteria c = new TTK91TaskCriteria("(A <= B);");
		TTK91TaskCriteria d = new TTK91TaskCriteria("(L, A <= B);");
		TTK91TaskCriteria e = new TTK91TaskCriteria();
		e.setFirstComparable("A");
		e.setSecondComparable("B");
		e.setComparator(e.LESS);
	}
	
/*	public void testLessContructors() {

		Assert.assertTrue(
				(e.getFirstComparable())
				.equals(
					a.getFirstComparable()
					)
				);
		Assert.assertTrue(
				(e.getSecondComparable())
				.equals(
					a.getSecondComparable()
					)
				);
	
		Assert.assertTrue(!
				(e.getSecondComparable())
				.equals(
					a.getFirstComparable()
					)
				);


	}
*/
	public static void main(String args[]) throws Exception{
		new TTK91TaskCriteria("Foo;");
	}
}
