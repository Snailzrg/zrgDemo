package effectiveJava.chapter6;

import org.junit.Test;

public class Sample {

	@Test
	public static void m1() {

	}
	
	public static void m2() {

	}
	@Test
	public static void m3() {
		throw new RuntimeException("boom");
	}
	
	public static void m4() {

	}
	@Test
	public  void m5() {

	}
	
	public static void m6() {

	}
	@Test
	public static  void m7() {
		throw new RuntimeException("crash");
	}
	
	public static void m8() {

	}

}
