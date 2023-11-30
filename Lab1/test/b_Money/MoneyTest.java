package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(100.00,SEK100.getAmount(),0.00001);
		assertEquals(200.00,SEK200.getAmount(),0.00001);
		assertEquals(0.00,SEK0.getAmount(),0.00001);
		assertEquals(-100.00,SEKn100.getAmount(),0.00001);
		assertEquals(10.00,EUR10.getAmount(),0.00001);
		assertEquals(0.00,EUR0.getAmount(),0.00001);
		assertEquals(20.00,EUR20.getAmount(),0.00001);

	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK,SEK100.getCurrency());
		assertEquals(SEK,SEK200.getCurrency());
		assertEquals(SEK,SEKn100.getCurrency());
		assertEquals(EUR,EUR10.getCurrency());
		assertEquals(EUR,EUR20.getCurrency());
		assertEquals(EUR,EUR0.getCurrency());

	}

	@Test
	public void testToString() {

		assertEquals("100.0 SEK",SEK100.toString());
		assertEquals("200.0 SEK",SEK200.toString());
		assertEquals("-100.0 SEK",SEKn100.toString());
		assertEquals("10.0 EUR",EUR10.toString());
		assertEquals("20.0 EUR",EUR20.toString());
		assertEquals("0.0 EUR",EUR0.toString());
	}

	@Test
	public void testGlobalValue() {
		//expected value for 100 SEK with rate 0.15 is 15.00 but in int representation of the amount it is 1500
		assertEquals(1500,SEK100.universalValue(),0.0001);
		assertEquals(3000,SEK200.universalValue(),0.0001);
		assertEquals(-1500,SEKn100.universalValue(),0.0001);
		assertEquals(1500,EUR10.universalValue(),0.0001);
		assertEquals(3000,EUR20.universalValue(),0.0001);
		assertEquals(0,EUR0.universalValue(),0.0001);
	}

	@Test
	public void testEqualsMoney() {
		assertEquals(true,SEK100.equals(EUR10));
		assertEquals(true,SEK200.equals(EUR20));
		assertFalse("Doesn't equal",SEK100.equals(SEK200));
		assertFalse("Doesn't equal",EUR10.equals(EUR20));

	}

	@Test
	public void testAdd() {
		assertEquals(200,SEK100.add(EUR10).getAmount(),0.001);
		assertEquals(20,EUR10.add(SEK100).getAmount(),0.001);
		assertEquals(30,EUR10.add(EUR20).getAmount(),0.001);

	}

	@Test
	public void testSub() {
		assertEquals(0,SEK100.sub(EUR10).getAmount(),0.001);
		assertEquals(0,EUR10.sub(SEK100).getAmount(),0.001);
		assertEquals(10,EUR20.sub(EUR10).getAmount(),0.001);
	}

	@Test
	public void testIsZero() {
		assertEquals(true,EUR0.isZero());
		assertEquals(false,EUR10.isZero());
		assertEquals(false,SEK100.isZero());
		assertEquals(false,SEKn100.isZero());

	}

	@Test
	public void testNegate() {
		assertEquals(100.0,SEKn100.negate().getAmount(),0.001);
		assertEquals(-10.0,EUR10.negate().getAmount(),0.001);
		assertEquals(0.0,EUR0.negate().getAmount(),0.001);
	}

	@Test
	public void testCompareTo() {
		assertEquals(1,SEK200.compareTo(SEK100));
		assertEquals(0,SEK100.compareTo(EUR10));
		assertEquals(-1,SEKn100.compareTo(EUR10));

	}
}
