package common.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
    private Object target;
    public MyInvocationHandler(final Object target) {
        this.target = target;
    }
    
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		  return method.invoke(target,args);
	}
   }
