package fi.helsinki.cs.koskelo.test;

import java.util.*;
import junit.framework.*;
import fi.helsinki.cs.koskelo.common.*;
import fi.helsinki.cs.koskelo.composer.*;

public class CriteriaTest extends TestCase {
	private TTK91TaskCriteria a;
	private TTK91TaskCriteria b;
	private TTK91TaskCriteria c;
	private TTK91TaskCriteria cb;
	private TTK91TaskCriteria d;
	private TTK91TaskCriteria db;
	private TTK91TaskCriteria e;
  	private TTK91TaskCriteria eb;  
	private TTK91TaskCriteria f;
	private TTK91TaskCriteria fb;
	private TTK91TaskCriteria g;
	private TTK91TaskCriteria al;
	private TTK91TaskCriteria bl;
	private TTK91TaskCriteria cl;
	private TTK91TaskCriteria dl;
	private TTK91TaskCriteria el;
	private TTK91TaskCriteria ebl;
	private TTK91TaskCriteria fl;

   	private TTK91TaskCriteria aa;
	private TTK91TaskCriteria ab;
	private TTK91TaskCriteria ala;
	private TTK91TaskCriteria abl;

	public void setUp() throws InvalidTTK91CriteriaException{
		a = new TTK91TaskCriteria("(A < B);");
		b = new TTK91TaskCriteria("(A > B);");
		c = new TTK91TaskCriteria("(A <= B);");
		d = new TTK91TaskCriteria("(A => B);");
		cb = new TTK91TaskCriteria("(A =< B);");
		db = new TTK91TaskCriteria("(A >= B);");
		e = new TTK91TaskCriteria("(A != B);");
		eb = new TTK91TaskCriteria("(A =! B);");
		f = new TTK91TaskCriteria("(A == B);");
		fb = new TTK91TaskCriteria("(A = B);");
		al = new TTK91TaskCriteria("(L,A < B);");
		bl = new TTK91TaskCriteria("(L,A > B);");
		cl = new TTK91TaskCriteria("(L,A <= B);");
		dl = new TTK91TaskCriteria("(L,A >= B);");
		el = new TTK91TaskCriteria("(L,A != B);");
		ebl = new TTK91TaskCriteria("(L,A =! B);");
		fl = new TTK91TaskCriteria("(L,A == B);");

		aa = new TTK91TaskCriteria( "(A, B);", true );
		ab = new TTK91TaskCriteria( "(A, B);", false );
		ala = new TTK91TaskCriteria( "(L, A, B);", true );
		abl = new TTK91TaskCriteria( "(L, A, B);", false );
	
		g = new TTK91TaskCriteria();
	}

	public void testQuality() {

		
		Assert.assertTrue(al.getQuality());
		Assert.assertTrue(bl.getQuality());
		Assert.assertTrue(cl.getQuality());
		Assert.assertTrue(dl.getQuality());
		Assert.assertTrue(el.getQuality());
		Assert.assertTrue(fl.getQuality());

   		Assert.assertTrue(ala.getQuality());
		Assert.assertTrue(abl.getQuality());

		Assert.assertFalse(a.getQuality());
		Assert.assertFalse(aa.getQuality());

	}

	public void testString() {

		Assert.assertEquals(a.toString(), "(A<B);");
		Assert.assertEquals(b.toString(), "(A>B);");
		Assert.assertEquals(c.toString(), "(A<=B);");
		Assert.assertEquals(d.toString(), "(A>=B);");
		Assert.assertEquals(e.toString(), "(A!=B);");
		Assert.assertEquals(f.toString(), "(A==B);");

		Assert.assertEquals(al.toString(), "(L,A<B);");
		Assert.assertEquals(bl.toString(), "(L,A>B);");
		Assert.assertEquals(cl.toString(), "(L,A<=B);");
		Assert.assertEquals(dl.toString(), "(L,A>=B);");
		Assert.assertEquals(el.toString(), "(L,A!=B);");
		Assert.assertEquals(fl.toString(), "(L,A==B);");

		Assert.assertEquals(aa.toString(), "(A,B);");
		Assert.assertEquals(ab.toString(), "(A,B);");
		Assert.assertEquals(ala.toString(), "(L,A,B);");
		Assert.assertEquals(abl.toString(), "(L,A,B);");

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

		Assert.assertTrue(
				(aa.getFirstComparable())
				.equals(
					ab.getFirstComparable()
				       )
				);

		Assert.assertTrue(
				(ab.getSecondComparable())
				.equals(
					aa.getSecondComparable()
				       )
				);

		Assert.assertFalse(
				(ab.getComparator())== aa.getComparator()
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

		try {
			g.setFirstComparable("    \n\r");
			fail("Ei tyhj‰‰ merkkijonoa)");
		} catch (Exception e) {
			// t‰nne ei tulla
		}

		try {
			g.setFirstComparable(")))\t");
			fail("Ei tyhj‰‰ merkkijonoa)");
		} catch (Exception e) {
			// t‰nne ei tulla
		}	



		g.setComparator(e.LESS);
		g.setFirstComparable("A");
		g.setSecondComparable("B");
		g.setQuality(true);

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



		g.setComparator(aa.NOTCOMPARABLE);
		g.setFirstComparable("A");
		g.setSecondComparable("B");
		g.setQuality(true);

		Assert.assertTrue(
				(g.getFirstComparable())
				.equals(
					aa.getFirstComparable()
				       )
				);
		Assert.assertTrue(
				(g.getSecondComparable())
				.equals(
					aa.getSecondComparable()
				       )
				);
		Assert.assertTrue(
				(g.getComparator())== aa.getComparator()
				);
	}


	public void testComparatorTests() {

		Assert.assertFalse(a.getComparator() == b.getComparator());
		Assert.assertFalse(a.getComparator() == c.getComparator());
		Assert.assertFalse(a.getComparator() == d.getComparator());
		Assert.assertFalse(a.getComparator() == e.getComparator());
		Assert.assertFalse(a.getComparator() == f.getComparator());
		Assert.assertFalse(a.getComparator() == aa.getComparator());

		Assert.assertFalse(b.getComparator() == c.getComparator());
		Assert.assertFalse(b.getComparator() == d.getComparator());
		Assert.assertFalse(b.getComparator() == e.getComparator());
		Assert.assertFalse(b.getComparator() == f.getComparator());
		Assert.assertFalse(b.getComparator() == aa.getComparator());

		Assert.assertFalse(c.getComparator() == d.getComparator());
		Assert.assertFalse(c.getComparator() == e.getComparator());
		Assert.assertFalse(c.getComparator() == f.getComparator());
		Assert.assertFalse(c.getComparator() == aa.getComparator());

		Assert.assertFalse(d.getComparator() == e.getComparator());
		Assert.assertFalse(d.getComparator() == f.getComparator());
		Assert.assertFalse(d.getComparator() == aa.getComparator());

		Assert.assertFalse(e.getComparator() == f.getComparator());
		Assert.assertFalse(e.getComparator() == aa.getComparator());

		Assert.assertFalse(f.getComparator() == aa.getComparator());

		Assert.assertTrue(c.getComparator() == cb.getComparator());
		Assert.assertTrue(d.getComparator() == db.getComparator());
		
		Assert.assertTrue(f.getComparator() == fb.getComparator());

       		Assert.assertFalse(aa.getComparator() == ab.getComparator());

	}

	/*	public static void main(String args[]) throws Exception{
		new TTK91TaskCriteria("Foo;");
		}
		*/

}
