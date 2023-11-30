package a_Introductory;

public class Fibonacci {
	public int fib(int n) {
		switch (n) {
			case 0: return 0;
			case 1: return 1;
			default: return (fib(n - 1) + fib(n - 2));
			//the bug was that the equation for recursive fibonacci does not include "-1" at the end
		}
	}
}
