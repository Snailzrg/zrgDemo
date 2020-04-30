package other;

import java.io.*;

/**
 * 1）一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
 * 2）transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量， 则该类需要实现Serializable接口。
 * 3）被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。
 * 第三点可能有些人很迷惑，因为发现在User类中的username字段前加上static关键字后，程序运行结果依然不变，
 * 即static类型的username也读出来为“Alexia”了，这不与第三点说的矛盾吗？实际上是这样的：第三点确实没错
 * （一个静态变量不管是否被transient修饰，均不能被序列化），反序列化后类中static型变量username的值为当前JVM中对应static变量的值，
 * 这个值是JVM中的不是反序列化得出的，不相信？好吧，下面我来证明：
 * @author zhouruigang
 * 2019/8/13 9:16
 */
public class TransientTest implements Externalizable {

    /**
     * 我们知道在Java中，对象的序列化可以通过实现两种接口来实现，若实现的是Serializable接口，则所有的序列化将会自动进行，若实现的是Externalizable接口，则没有任何东西可以自动序列化，需要在writeExternal方法中进行手工指定所要序列化的变量，这与是否被transient修饰无关。因此第二个例子输出的是变量content初始化的内容，而不是null。
     *
     */
    private transient String content = "是的，我将会被序列化，不管我是否被transient关键字修饰";

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        //   out.writeObject(content);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        //    content = (String) in.readObject();
    }
    /**
     *
     * @throws Exception
     */
    static void externalizableMethod() throws Exception{
        TransientTest et = new TransientTest();
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(
                new File("test")));
        out.writeObject(et);

        ObjectInput in = new ObjectInputStream(new FileInputStream(new File(
                "test")));
        et = (TransientTest) in.readObject();
        System.out.println(et.content);

        out.close();
        in.close();
    }

    public static void main(String[] args) throws Exception {
        externalizableMethod();
    }

    /**
     *
     */
    static  void transientGen(){
        User user = new User();
        user.setUsername("Alexia");
        user.setPasswd("123456");

        System.out.println("read before Serializable: ");
        System.out.println("username: " + user.getUsername());
        System.err.println("password: " + user.getPasswd());

        try {
            ObjectOutputStream os = new ObjectOutputStream(
                    new FileOutputStream("tempFile"));
            os.writeObject(user); // 将User对象写进文件
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //A
//            ObjectInputStream is = new ObjectInputStream(new FileInputStream(
//                    "tempFile"));
//            user = (User) is.readObject(); // 从流中读取User的数据
//            is.close();
//
//            System.out.println("\nread after Serializable: ");
//            System.out.println("username: " + user.getUsername());
//            System.err.println("password: " + user.getPasswd());



            //B 在反序列化之前改变username的值
            user.username = "jmwang";
            user.setUsername("....3330....");

            ObjectInputStream is = new ObjectInputStream(new FileInputStream(
                    "tempFile"));
            user = (User) is.readObject(); // 从流中读取User的数据
            is.close();

            System.out.println("\nread after Serializable: ");
            System.out.println("username2: " + user.getUsername());
            System.err.println("password2: " + user.getPasswd());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

class User implements Serializable {
    private static final long serialVersionUID = 8294180014912103005L;

    public String username;
    private transient String passwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

}
