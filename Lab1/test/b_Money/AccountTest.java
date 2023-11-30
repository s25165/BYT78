package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		//every test fails because this method throws NullPointerException
		//also after fixing the first problem every test failed because Bank.deposit method had a bug
		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		//firstly key does not exist
		assertFalse("Key exists",testAccount.timedPaymentExists("1"));
		//then it is added
		testAccount.addTimedPayment("1",2,2,new Money(10000,SEK),SweBank,"Alice");
		//and now it exists
		assertTrue(testAccount.timedPaymentExists("1"));
		//and then it is removed
		testAccount.removeTimedPayment("1");
		assertFalse("Key exists",testAccount.timedPaymentExists("1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		//firstly key does not exist
		assertFalse("Key exists",testAccount.timedPaymentExists("1"));
		//then it is added
		testAccount.addTimedPayment("1",2,2,new Money(10000,SEK),SweBank,"Alice");
		//and now it exists
		assertTrue(testAccount.timedPaymentExists("1"));
	}

	@Test
	public void testAddWithdraw() {
		//initial state
		assertEquals(100000.0,testAccount.getBalance().getAmount(),0.001);

		//adding more money
		testAccount.deposit(new Money(10000000, SEK));
		//amount on account is doubled
		assertEquals(200000.0,testAccount.getBalance().getAmount(),0.001);

		//removing the money
		testAccount.withdraw(new Money(10000000, SEK));

		//amount is the same as initially
		assertEquals(100000.0,testAccount.getBalance().getAmount(),0.001);
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(100000.0,testAccount.getBalance().getAmount(),0.001);

	}
}
