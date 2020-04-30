package bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;



class MyLibrary {
    public interface MyCallback {
        void doMyCallback();
    }
    public void mainMethod(MyCallback myCallback) {
        System.out.println("doing MyLibrary mainMethod...");
        myCallback.doMyCallback();
    }
}
class MyHandler implements InvocationHandler{
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("doing MyHandler invoke...");
        return null;
    }
}
public class Start {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException {

	
		Class<?> myLibraryClazz = Class.forName("bean.IBaseService");//类
//        
//        MyHandler myHandler = new MyHandler();//类
//        MyCallback myCallback = (MyCallback)Proxy.newProxyInstance(
//        		Test3.class.getClassLoader(),//类加载器
//        		new Class[] { myCallbackClazz },//接口数组
//        		myHandler//为接口实现的对应具体方法
//        	);//为接口实例化对象
		
		Object instance = Proxy.newProxyInstance(myLibraryClazz.getClassLoader(), new Class[] { myLibraryClazz },
				new MyInvocationHandler("BaseService"));
		 final Method method = instance.getClass().getMethod("sayHello",  String.class,String.class);
	 //   obj=   method.invoke(null,orgId,dxid);
//	       obj=   method.invoke(instance,orgId,dxid);
	       
	       
//        Method method = myLibraryClazz.getDeclaredMethod("sayHello", String.class,String.class);//（类名，参数类型）
        method.invoke(instance, "123","123");//调用方法，（实例化对象，内部接口实现对象）
		
	}
	
}




