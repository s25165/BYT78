package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK",SEK.getName());
		assertEquals("DKK",DKK.getName());
		assertEquals("EUR",EUR.getName());

	}
	
	@Test
	public void testGetRate() {
		assertEquals(0.15,SEK.getRate(),0.001);
		assertEquals(0.20,DKK.getRate(),0.001);
		assertEquals(1.5,EUR.getRate(),0.001);

	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(0.30);
		DKK.setRate(0.35);
		EUR.setRate(0.40);
		assertEquals(0.30,SEK.getRate(),0.001);
		assertEquals(0.35,DKK.getRate(),0.001);
		assertEquals(0.40,EUR.getRate(),0.001);

	}
	
	@Test
	public void testGlobalValue() {
		//let amount in SEK, DKK and EUR = 100.00 or 10000 in int representation
		assertEquals(1500,SEK.universalValue(10000),0.001);
		assertEquals(2000,DKK.universalValue(10000),0.001);
		assertEquals(15000,EUR.universalValue(10000),0.001);

	}
	
	@Test
	public void testValueInThisCurrency() {
		//let the "other" amount equal again to 100.00 or 10000 in int representation
		//1000 = 10.0 so after all calculations 100.0 SEK is 10.0 EUR
		assertEquals(1000,EUR.valueInThisCurrency(10000,SEK),0.001);
		//7500 = 75.0 so after calculations 100.0 SEK is 75.0 DKK
		assertEquals(7500,DKK.valueInThisCurrency(10000,SEK),0.001);
		//100000 = 1000.00 and after calculations 100.0 EUR = 1000.0 SEK
		assertEquals(100000,SEK.valueInThisCurrency(10000,EUR),0.001);
	}

}
