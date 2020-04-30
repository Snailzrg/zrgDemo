package demo.singlotn1;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 枚举Enum是个抽象类，其实一旦一个类声明为枚举，实际上就是继承了Enum，所以会有（String.class,int.class）的构造器。既然是可以获取到父类Enum的构造器，那你也许会说刚才我的反射是因为自身的类没有无参构造方法才导致的异常，并不能说单例枚举避免了反射攻击。
 * 反射在通过newInstance创建对象时，会检查该类是否ENUM修饰，如果是则抛出异常，反射失败
 * 
 * 由于反射后面
 * 
 *
 * @author zhouruigang
 * 2019/8/12 17:22
 */
public enum EnumSingle implements Serializable {
    INSTANCE;

    public EnumSingle getInstance() {
        return INSTANCE;
    }

    //    private EnumSingleton getInstance(){
//                return INSTANCE;
//           }
    private EnumSingle() {
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException ,Exception{
        menthodA();
    }

    static void menthod() throws Exception{
        EnumSingle singleton1 = EnumSingle.INSTANCE;
        EnumSingle singleton2 = EnumSingle.INSTANCE;
        System.out.println("正常情况下，实例化两个实例是否相同：" + (singleton1 == singleton2));

        Constructor<EnumSingle> constructor = null;
//      constructor = EnumSingle.class.getDeclaredConstructor();

        constructor = EnumSingle.class.getDeclaredConstructor(String.class, int.class);//其父类的构造器
        constructor.setAccessible(true);
        EnumSingle singleton3 = null;
//      constructor.setAccessible(true);
        //           singleton3 = constructor.newInstance();
        singleton3 = constructor.newInstance("testInstance", 66);
        System.out.println(singleton1 + "\n" + singleton2 + "\n" + singleton3);
        System.out.println("通过反射攻击单例模式情况下，实例化两个实例是否相同：" + (singleton1 == singleton3));
    }

    static void menthodA() throws Exception {

        EnumSingle s = EnumSingle.INSTANCE;
        s.setContent("枚举单例序列化");
        System.out.println("枚举序列化前读取其中的内容：" + s.getContent());
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("EnumSingle.obj"));
        oos.writeObject(s);
        oos.flush();
        oos.close();

        FileInputStream fis = new FileInputStream("EnumSingle.obj");
        ObjectInputStream ois = new ObjectInputStream(fis);
        EnumSingle s1 = (EnumSingle) ois.readObject();
        ois.close();
        System.out.println(s + "\n" + s1);
        System.out.println("枚举序列化后读取其中的内容：" + s1.getContent());
        System.out.println("枚举序列化前后两个是否同一个：" + (s == s1));
    }
}