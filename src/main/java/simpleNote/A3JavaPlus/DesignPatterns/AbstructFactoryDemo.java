package simpleNote.A3JavaPlus.DesignPatterns;

/**
 *
 *抽象工厂模式测试
 *工厂模式：提供一个实例化对象的接口，让子类去决定实例那个具体对象
 *
 * @author zhouruigang
 * 2019/11/11 17:17
 */
public class AbstructFactoryDemo {

    public static void main(String[] args) {
        Afactory penFactory = new PenFactory();
        Pen pen = (Pen) penFactory.instance();
        pen.desc();
        Afactory mobileFactory = new MobilePhoneFactory();
        MobilePhone mobilePhone = (MobilePhone) mobileFactory.instance();
        mobilePhone.desc();
    }
}

abstract class Product {
}
class MobilePhone extends Product {
    public void desc() {
        System.out.println("this is mobilephone");
    }
}
class Pen extends Product {
    public void desc(){
        System.out.println("this is pen");
    }
}
/**
 * 抽象工厂-抽象类 子类再去实现抽象类的各个抽象方法
 *  每个类别创建自己的工厂
 */
abstract class Afactory {
    public abstract Product instance();
}

class MobilePhoneFactory extends  Afactory {
    @Override
    public Product instance() {
        return new MobilePhone();
    }
}
class PenFactory extends Afactory {
    @Override
    public Product instance() {
        return new Pen();
    }
}
