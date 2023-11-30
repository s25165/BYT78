package a_Introductory;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {
	Point p1, p2, p3;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Point(7, 9);
		p2 = new Point(-3, -30);
		p3 = new Point(-10, 3);
	}

	@Test
	public void testAdd() {
		Point res1 = p1.add(p2);
		Point res2 = p1.add(p3);

		//the bug was that the method tried to compare int with Integer which is an object
		//so to make it right it is just needed to get int value from Integer
		assertEquals(4, res1.x.intValue());
		assertEquals(-21, res1.y.intValue());
		assertEquals(-3, res2.x.intValue());
		assertEquals(12, res2.y.intValue());
		// res2.y instead of twice res2.x
	}

	@Test
	public void testSub() {
		Point res1 = p1.sub(p2);
		Point res2 = p1.sub(p3);

		//the test cases are the same as in the testAdd() but we are testing different function
		//so the correct values are different
		assertEquals(10, res1.x.intValue());
		assertEquals(39, res1.y.intValue());
		assertEquals(17, res2.x.intValue());
		assertEquals(6, res2.y.intValue());
	}

}
