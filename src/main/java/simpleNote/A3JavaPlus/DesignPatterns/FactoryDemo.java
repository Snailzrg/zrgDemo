package simpleNote.A3JavaPlus.DesignPatterns;
//工厂模式分为工厂方法模式和抽象工厂模式。

/**
 *工厂方法模式：
 * 1. 工厂方法模式分为三种：普通工厂模式，就是建立一个工厂类，对实现了同一接口的一些类进行实例的创建。
 * 2. 多个工厂方法模式，是对普通工厂方法模式的改进，在普通工厂方法模式中，如果传递的字符串出错，则不能正确创建对象，而多个工厂方法模式是提供多个工厂方法，分别创建对象。
 * 3. 静态工厂方法模式，将上面的多个工厂方法模式里的方法置为静态的，不需要创建实例，直接调用即可。
 *
 * @see:  https://blog.csdn.net/yubujian_l/article/details/81455524  , https://www.cnblogs.com/V1haoge/p/10491982.html ,https://www.cnblogs.com/coderdxj/p/9635023.html
 * @author zhouruigang
 * 2019/11/11 17:15
 */
public class FactoryDemo {

    public static void main(String[] args) {
        //简单的
        SimpleFactory factory = new SimpleFactory();
        Person xiaoming = factory.instance("1");
        Person xiaoli = factory.instance("2");
        XiaoLi m = (XiaoLi) xiaoli;
        m.eat();
        XiaoMing l = (XiaoMing) xiaoming;
        l.eat();
    }
}
abstract class Person {
}
class XiaoLi extends Person {
    public void eat() {
        System.out.println("小李吃饭");
    }
}
class XiaoMing extends Person {
    public void eat() {
        System.out.println("小明吃饭");
    }
}
/**
 * 简单工厂
 */
class SimpleFactory {
    public Person instance(String username) {
        if (username.equals("1")) {
            return new XiaoMing();
        } else if (username.equals("2")) {
            return new XiaoLi();
        }
        return null;
    }
}



