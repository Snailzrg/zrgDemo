package effectiveJava.chapter6;

import org.junit.Test;

public class Sample2 {

	@ExceptionTest(ArithmeticException.class)
	public static void m1() {
		int i= 0;
		i =i/i;

	}
	
	@ExceptionTest(ArithmeticException.class)
	public static void m2() {
		int a[] = new int[10];
		int i = a[11];
	}
	@ExceptionTest(ArithmeticException.class)
	public static void m3() {
	}
	
	
}
