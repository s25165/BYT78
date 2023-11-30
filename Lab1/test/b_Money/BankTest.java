package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank",SweBank.getName());
		assertEquals("Nordea",Nordea.getName());
		assertEquals("DanskeBank",DanskeBank.getName());

	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK,SweBank.getCurrency());
		assertEquals(SEK,Nordea.getCurrency());
		assertEquals(DKK,DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		assertThrows(AccountExistsException.class,()-> SweBank.openAccount("Ulrika"));

		//since the test passes the error is not thrown -> the account does not exist before creation
		try {
			SweBank.openAccount("TEST");
		}catch(AccountExistsException e) {
			fail("Account exists");
		}
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.deposit("TEST",new Money(100,DKK)));

		//since the test passes the error is not thrown -> the account to deposit to exists
		try {
			SweBank.deposit("Ulrika",new Money(100,DKK));
		}catch(AccountDoesNotExistException e) {
			fail("Account exists");
		}
	}
	//here test fails because the withdraw method used "account.deposit" instead of "account.withdraw"
	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		//exception is thrown because account doesn't exist -> the test passes
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.withdraw("TEST",new Money(100,DKK)));

		//since the test passes the error is not thrown -> the account to withdraw exists
		try {
			SweBank.withdraw("Ulrika",new Money(100,DKK));
		}catch(AccountDoesNotExistException e) {
			fail("Account exists");
		}

	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		//all accounts are empty
		assertEquals(0,SweBank.getBalance("Ulrika"),0.001);

		SweBank.deposit("Ulrika",new Money(10000,SEK));

		//10000 = 100.00 so the test passes
		assertEquals(100,SweBank.getBalance("Ulrika"),0.001);
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		//TEST account does not exist in Nordea so the error is thrown and the test passes
		assertThrows(AccountDoesNotExistException.class, () -> SweBank.transfer("Ulrika",Nordea,"TEST",new Money(10000,SEK)));

		//first check both accounts are empty
		assertEquals(0,SweBank.getBalance("Ulrika"),0.001);
		assertEquals(0,Nordea.getBalance("Bob"),0.001);

		//transfer money from one to another
		SweBank.transfer("Ulrika",Nordea,"Bob",new Money(10000,SEK));
		//use second transfer method
		SweBank.transfer("Ulrika","Bob",new Money(10000,SEK));

		//check that new balance in both accounts changed
		assertEquals(100,Nordea.getBalance("Bob"),0.001);

		//this test fails because in the function "transfer" goes from account to the same account
		assertEquals(100,SweBank.getBalance("Bob"),0.001);

		//after two transfers balance is -200
		assertEquals(-200,SweBank.getBalance("Ulrika"),0.001);
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		//since the original function does not throw AccountDoesNotExistException
		//and the account "TEST" does not exist the NullPointerException is raised
		assertThrows(NullPointerException.class, () -> SweBank.addTimedPayment("TEST","1",2,2,new Money(10000, SEK),Nordea,"Bob"));

		//add the payment and make the system "tick" aka register the payment
		SweBank.addTimedPayment("Ulrika","1",2,1,new Money(10000, SEK),Nordea,"Bob");
		SweBank.tick();

		//check that the payment went through
		assertEquals(100,Nordea.getBalance("Bob"),0.001);
		//make another payment
		SweBank.addTimedPayment("Ulrika","2",2,1,new Money(10000, SEK),Nordea,"Bob");
		//remove the payment and move the system by one tick
		SweBank.removeTimedPayment("Ulrika","2");
		SweBank.tick();

		//check that the amount in the account did not change
		assertEquals(100,Nordea.getBalance("Bob"),0.001);

	}
}
