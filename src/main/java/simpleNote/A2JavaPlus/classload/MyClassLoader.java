package simpleNote.A2JavaPlus.classload;


import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义类加载器
 * @author snail
 * 2020/9/16 16:06
 */
public class MyClassLoader {

    static class ZrgClassLoader extends ClassLoader {

        private String classPath;

        public ZrgClassLoader(String classPath){
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws IOException {
            name = name.replaceAll("\\.","/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name +".class");
            int len = fis.available();
            byte [] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }


        protected Class<?> findClass(String name) throws ClassNotFoundException{
            Class<?> cls = null;
            try {
                byte[] data = loadByte(name);
                cls = defineClass(name,data,0,data.length);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassFormatError classFormatError) {
                classFormatError.printStackTrace();
            }
            return cls;
        }

    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        ZrgClassLoader classLoader = new ZrgClassLoader("D:/test");
         Class clazz = classLoader.loadClass("com.tuling.jvm.User1");;
         Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout", null);
        method.invoke(obj, null);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }
}

