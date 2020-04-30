package demo.singlotn1;

import java.io.*;

/**
 * @author zhouruigang
 * 2019/8/10 16:29
 */
public class start {

    /**
     *
     * 编译器会为枚举类通过静态代码块实例化在枚举中定义的枚举值(MAN和WOMAN)，因为是静态变量所有在内存中只存在一份，反序列化的时候通过Enum.valueOf()方法得到一个key为枚举名称value为静态的Gender类的对象，通过map.get(枚举名称)得到的就是Gender类其中的一个静态成员变量。因为枚举的反序列化得到的对象依然引用的原Gender类静态代码块创建的对象并没有新创见一个对象，因此还是单例的。
     * ---------------------
     * 版权声明：本文为CSDN博主「一直不懂」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/shenchaohao12321/article/details/79521774
     *
     */
        public static void main(String[] args) throws IOException, ClassNotFoundException  {
            SingleObject demo1 =  SingleObject.getInstance();
            SingleObject demo2 =  SingleObject.getInstance();
            System.out.println(demo1 == demo2);

            /**
             * 序列化对单例的破坏 /序列化会通过反射调用无参数的构造方法创建一个新的对象。
             *
             * line 1785--> obj = desc.isInstantiable() ? desc.newInstance() : null;
             * 通过无参构造函数直接创建对象，这也就是说为啥反序列化类不是单例了
             *对于实例控制，枚举类型优于readResolve。
             *
             *
             *
             *
             *
             */
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
            oos.writeObject(SingleObject.getInstance());
            //Read Obj from file
            File file = new File("tempFile");
            ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(file));
            SingleObject newInstance = (SingleObject) ois.readObject();
            //判断是否是同一个对象
            System.out.println(newInstance == SingleObject.getInstance());
        }
}
