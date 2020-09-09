package simpleNote.A2BaseJava.Reflex;

import common.bean.IBaseService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author snail
 * 2020/8/27 11:46
 */
public class ReflexDemo {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
//        IBaseService
//        Class cls =Class.forName("common.ReflexUse");
//        cls.newInstance();
//
//        final Method method = cls.getMethod("demoStr",String.class);
//        String resValue = method.invoke(method,"参数").toString();
//
//        System.out.println("--"+resValue);



        String value = "5003";
        String[] vls = value.split("\\.");

        System.out.println(vls[vls.length-1]);
    }
}
