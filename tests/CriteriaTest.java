public class CriteriaTest extends TestCase {

	public void testLessContructors() {
		TTK91TaskCriteria a = new TTK91TaskCriteria("(A < B);");
		TTK91TaskCriteria b = new TTK91TaskCriteria("(L, A < B);");
		TTK91TaskCriteria c = new TTK91TaskCriteria("(A <= B);");
		TTK91TaskCriteria d = new TTK91TaskCriteria("(L, A <= B);");
		TTK91TaskCriteria e = new TTK91TaskCriteria();
		e.setFirstComparable("A");
		e.setSecondComparable("B");
		e.setComparator(e.LESS);

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
}
