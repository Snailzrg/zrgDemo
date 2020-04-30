package effectiveJava.chapter6;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;



public class RunTests {
	
	
	
	public static void sample() throws ClassNotFoundException{
		int test= 0;
		int passed=0;
		Class<?> cls = Class.forName(Sample.class.getName());
		for(Method me: cls.getDeclaredMethods()){
			if (me.isAnnotationPresent(Test.class)) {
				test++;
				try {
					me.invoke(null);
					passed++;
				} catch (InvocationTargetException e) {

					Throwable ext =e.getCause();
					System.out.println(me+"failed:"+ext);
//					e.printStackTrace();
				} catch (Exception exc) {
					System.out.println("INCALID @Test:"+me
							);
				} 
				
			}
			
		}
		System.out.printf("passd:%d,failed:%d%n",passed,test-passed);
		
	}
	
	
	

	public static void sample2() throws ClassNotFoundException{
		int test= 0;
		int passed=0;
		Class<?> cls = Class.forName(Sample2.class.getName());
		for(Method me: cls.getDeclaredMethods()){
			if (me.isAnnotationPresent(ExceptionTest.class)) {
				test++;
				try {
					me.invoke(null);
					System.out.printf("Test %s failed: no exception%n",me);
				} catch (InvocationTargetException e) {
					Throwable ext =e.getCause();
					int oldPassed =passed;
//					Class<? extends Throwable> exctype=me.getAnnotation(ExceptionTest.class).value();
					Class<? extends Throwable>[] exctype=me.getAnnotation(ExceptionTest.class).value();
					for (Class<? extends Throwable> ex:exctype) {
						if (ex.isInstance(ext)){
							passed++;
							break;
						}
						
					}
					
					if (passed == oldPassed) {
						System.out.printf("Test %s failed:%n",me,ext);
					}
				} catch (Exception exc) {
					System.out.println("INCALID @Test:"+me
							);
				} 
				
			}
			
		}
		System.out.printf("passd:%d,failed:%d%n",passed,test-passed);
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException {

//		sample();
		sample2();
		
	}

}
