package fi.helsinki.cs.koskelo.test;

import java.util.*;
import junit.framework.*;
import fi.helsinki.cs.koskelo.common.*;
import fi.helsinki.cs.koskelo.composer.*;

public class CriteriaTest extends TestCase {
	private TTK91TaskCriteria a;
	private TTK91TaskCriteria b;
	private TTK91TaskCriteria c;
	private TTK91TaskCriteria d;
	private TTK91TaskCriteria e;
	private TTK91TaskCriteria f;
	private TTK91TaskCriteria g;
	
	public void setUp() throws InvalidTTK91CriteriaException{
		a = new TTK91TaskCriteria("(A < B);");
		b = new TTK91TaskCriteria("(A > B);");
		c = new TTK91TaskCriteria("(A <= B);");
		d = new TTK91TaskCriteria("(A >= B);");
		e = new TTK91TaskCriteria("(A != B);");
		f = new TTK91TaskCriteria("(A == B);");
		g = new TTK91TaskCriteria();
	}
	
	public void testLessContructors() {

		Assert.assertTrue(
				(b.getFirstComparable())
				.equals(
					a.getFirstComparable()
					)
				);
		Assert.assertTrue(
				(b.getSecondComparable())
				.equals(
					a.getSecondComparable()
					)
				);
		Assert.assertFalse(
				(b.getComparator())== a.getComparator()
				);
	
	}


	public void testSetTests() throws Exception{
	System.out.println("Foo");	
		g.setComparator(e.LESS);
		g.setFirstComparable("A");
		g.setSecondComparable("B");

		try {
			g.setFirstComparable("");
			fail("Ei tyhj‰‰ merkkijonoa)");
		} catch (Exception e) {
			// t‰nne ei tulla
		}
			try {
			g.setFirstComparable(null);
			fail("Ei tyhj‰‰ merkkijonoa)");
		} catch (Exception e) {
			// t‰nne ei tulla
		}
			try {
			g.setFirstComparable("(((");
			fail("Ei tyhj‰‰ merkkijonoa)");
		} catch (Exception e) {
			// t‰nne ei tulla
		}
		g.setComparator(e.LESS);
		g.setFirstComparable("A");
		g.setSecondComparable("B");
	
		Assert.assertTrue(
				(g.getFirstComparable())
				.equals(
					a.getFirstComparable()
					)
				);
		Assert.assertTrue(
				(g.getSecondComparable())
				.equals(
					a.getSecondComparable()
					)
				);
		Assert.assertTrue(
				(g.getComparator())== a.getComparator()
				);

	}


	public void testComparatorTests() {
	
		Assert.assertFalse(a.getComparator() == b.getComparator());
		Assert.assertFalse(a.getComparator() == c.getComparator());
		Assert.assertFalse(a.getComparator() == d.getComparator());
		Assert.assertFalse(a.getComparator() == e.getComparator());
		Assert.assertFalse(a.getComparator() == f.getComparator());
		Assert.assertFalse(b.getComparator() == c.getComparator());
		Assert.assertFalse(b.getComparator() == d.getComparator());
		Assert.assertFalse(b.getComparator() == e.getComparator());
		Assert.assertFalse(b.getComparator() == f.getComparator());
		Assert.assertFalse(c.getComparator() == d.getComparator());
		Assert.assertFalse(c.getComparator() == e.getComparator());
		Assert.assertFalse(c.getComparator() == f.getComparator());
		Assert.assertFalse(d.getComparator() == e.getComparator());
		Assert.assertFalse(d.getComparator() == f.getComparator());
		Assert.assertFalse(e.getComparator() == f.getComparator());
	}

/*	public static void main(String args[]) throws Exception{
		new TTK91TaskCriteria("Foo;");
	}
*/
	
}
