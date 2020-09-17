package simpleNote.A2JavaPlus.classload;

import simpleNote.A2JavaPlus.clsLoad.String;
import sun.misc.Launcher;

import java.net.URL;

/**
 * @author snail
 * 2020/9/17 14:58
 */
public class Do {

    public static void main(java.lang.String[] args) {

    }

    /**
     *
     * java常见设计模式
     *
     * 一 ：设计模式六大原则
     * 1.开放封闭原则：对扩展开放，对修改封闭，意即程序拓展时不要动原有的代码
     * 2.LSP原则：任何基类可以出现的地方，子类一定可以出现
     * 3.依赖倒置原则：使用接口，依赖于抽象而不是具体
     * 4.接口隔离原则：为了解耦，使用多个相互隔离的接口
     * 5.迪米特法则：一个实体应当尽量少地与其他实体之间发生相互作用，使得系统功能模块相对独立。
     * 6.CRP法则：尽量使用合成/聚合的方式，而不是使用继承。
     *
     *
     *
     * 二：分类
     *
     * 1.创建型模式（在创建对象的过程中尽量隐藏创建细节，不直接使用new）
     *
     * 工厂模式（Factory Pattern）
     * 抽象工厂模式（Abstract Factory Pattern）
     * 单例模式（Singleton Pattern）
     * 建造者模式（Builder Pattern）
     * 原型模式（Prototype Pattern)
     * 2.结构型模式（主要关注类和对象的继承、组合）
     *
     * 适配器模式（Adapter Pattern）
     * 桥接模式（Bridge Pattern）
     * 过滤器模式（Filter、Criteria Pattern）
     * 组合模式（Composite Pattern）
     * 装饰器模式（Decorator Pattern）
     * 外观模式（Facade Pattern）
     * 享元模式（Flyweight Pattern）
     * 代理模式（Proxy Pattern）
     * 3.行为型模式（关注对象之间的通信）
     *
     * 责任链模式（Chain of Responsibility Pattern）
     * 命令模式（Command Pattern）
     * 解释器模式（Interpreter Pattern）
     * 迭代器模式（Iterator Pattern）
     * 中介者模式（Mediator Pattern）
     * 备忘录模式（Memento Pattern）
     * 观察者模式（Observer Pattern）
     * 状态模式（State Pattern）
     * 空对象模式（Null Object Pattern）
     * 策略模式（Strategy Pattern）
     * 模板模式（Template Pattern）
     * 访问者模式（Visitor Pattern）
     *
     *
     * 三： 常见的
     *工厂模式
     * 现在要创建一些对象，他们都实现了某个接口或者继承了某个类。我们不在需要的时候使用new操作符，而是把创建的操作让一个“工厂类”完成，我们在需要新对象时只需要把需要的东西的名字以参数形式传递给工厂类就行了，而不用去管怎么创建的。
     *
     * 抽象工厂模式
     * 在工厂模式中，一个工厂类只生产实现某个接口或者继承了某个类的对象，也就是具体工厂生产具体对象，如果建立一个抽象工厂类和若干个具体工厂，每个具体工厂负责产生一类对象，就成了抽象工厂模式。就是一个抽象工厂类可以生产多种类型的对象，具体每种类型的对象怎么生成，要用一个专门的工厂类来决定。
     *
     * 单例模式
     * 一个类，他虽然有构造方法，但是把它设定为private，不能被外界使用。这个类只存在一个实例，保存在这个类自己的一个静态字段里，如果要用这个类的实例属性、实例方法，都通过这个静态字段访问这个唯一的实例来实现。
     *
     * 建造者模式
     * 现在要创建一个很复杂的对象，我们把这个工作分开来做，先定义并实现一个建造者类，在这个类中实现构建这个对象所需要的全部方法。再定义并实现一个导演类，把一个建造者类传给它，让它负责这些方法调用的逻辑次序和对象的组合，然后统一给客户端返回一个生成好的复杂对象。
     *
     * 原型模式
     * 已经有了一个对象了，我们创建对象时直接复制它，不需要新建了。
     *
     *
     * ----常见 -----
     *
     *1) 单例模式。
     *
     * 单例模式是一种常用的软件设计模式。
     *
     * 在它的核心结构中只包含一个被称为单例类的特殊类。通过单例模式可以保证系统中一个类只有一个实例而且该实例易于外界访问，从而方便对实例个数的控制并节约系统资源。
     *
     * 应用场景：如果希望在系统中某个类的对象只能存在一个，单例模式是最好的解决方案。
     *
     * 2) 工厂模式。
     *
     * 工厂模式主要是为创建对象提供了接口。
     *
     * 应用场景如下：
     *
     * a、 在编码时不能预见需要创建哪种类的实例。
     *
     * b、 系统不应依赖于产品类实例如何被创建、组合和表达的细节。
     *
     * 3) 策略模式。
     *
     * 策略模式：定义了算法族，分别封装起来，让它们之间可以互相替换。此模式让算法的变化独立于使用算法的客户。
     *
     * 应用场景如下。
     *
     * a、 一件事情，有很多方案可以实现。
     *
     * b、我可以在任何时候，决定采用哪一种实现。
     *
     * c.、未来可能增加更多的方案。
     *
     * d、 策略模式让方案的变化不会影响到使用方案的客户。
     *
     * 举例业务场景如下。
     *
     * 系统的操作都要有日志记录，通常会把日志记录在数据库里面，方便后续的管理，但是在记录日志到数据库的时候，可能会发生错误，比如暂时连不上数据库了，那就先记录在文件里面。日志写到数据库与文件中是两种算法，但调用方不关心，只负责写就是。
     *
     * 4) 观察者模式。
     *
     * 观察者模式又被称作发布/订阅模式，定义了对象间一对多依赖，当一个对象改变状态时，它的所有依赖者都会收到通知并自动更新。
     *
     * 应用场景如下：
     *
     * a、对一个对象状态的更新，需要其他对象同步更新，而且其他对象的数量动态可变。
     *
     * b、对象仅需要将自己的更新通知给其他对象而不需要知道其他对象的细节。
     *
     * 5) 迭代器模式。
     *
     * 迭代器模式提供一种方法顺序访问一个聚合对象中各个元素，而又不暴露该对象的内部表示。
     *
     * 应用场景如下：
     *
     * 当你需要访问一个聚集对象，而且不管这些对象是什么都需要遍 历的时候，就应该考虑用迭代器模式。其实stl容器就是很好的迭代器模式的例子。
     *
     * 6) 模板方法模式。
     *
     * 模板方法模式定义一个操作中的算法的骨架，将一些步骤延迟到子类中，模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些步骤。
     *
     * 应用场景如下：
     *
     * 对于一些功能，在不同的对象身上展示不同的作用，但是功能的框架是一样的。
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * @see https://www.cnblogs.com/hyfer/p/11059395.html
     * @author zhouruigang
     * 2019/11/11 17:10
     */
    public static interface SjmsInfo {

        /**
         * 代理模式
         *
         */
        void ProxyInfo();

        /***
         *工厂，就是生产产品的地方。
         * 在Java设计模式中使用工厂的概念，那就是生成对象的地方了。
         * 本来直接就能创建的对象为何要增加一个工厂类呢？
         * 这就需要了解工厂方法要解决的是什么问题了，如果只有一个类，我们直接new一个对象完事，这是最简单的；但是如果有多个类呢，而且这些类还需要针对不同的情况来创建不同的对象，这时候就需要工厂了，我们可以在工厂中根据条件来创建具体的对象。
         * 这样一来就将调用方和具体的目标类进行了解耦，调用方根本就不知道需要创建那个对象，它只是提出了条件，然后工厂就可以根据给定的条件来决定创建哪一个对象。
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         *
         */
        void FactoryInfo();



    }

    /**
     * 类加载
     参见类运行加载全过程图可知其中会创建JVM启动器实例sun.misc.Launcher。 sun.misc.Launcher初始化使用了单例模式设计，保证一个JVM虚拟机内只有一个 sun.misc.Launcher实例。 在Launcher构造方法内部，其创建了两个类加载器，分别是 sun.misc.Launcher.ExtClassLoader(扩展类加载器)和sun.misc.Launcher.AppClassLoader(应 用类加载器)。 JVM默认使用Launcher的getClassLoader()方法返回的类加载器AppClassLoader的实例加载我们 的应用程序。
     * @author snail
     * 2020/8/27 11:52
     */
    public static class ClassLoadDemo {

        public static void main(String[] args) {
            System.out.println(String.class.getClassLoader());
            System.out.println(com.sun.crypto.provider.DESKeyFactory.class.getClassLoader().getClass().getName());
            System.out.println(ClassLoadDemo.class.getClassLoader().getClass().getName());
            System.out.println();
            ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
            ClassLoader extClassloader = appClassLoader.getParent();
            ClassLoader bootstrapLoader = extClassloader.getParent();

            System.out.println("thebootstrapLoader:" + bootstrapLoader);
            System.out.println("theextClassloader:" + extClassloader);
            System.out.println("theappClassLoader:" + appClassLoader);

            System.out.println();
            System.out.println("bootstrapLoader加载以下文件：");
            URL[] urls = Launcher.getBootstrapClassPath().getURLs();

            for (int i = 0; i < urls.length; i++) {
                System.out.println(urls[i]);
            }

            System.out.println();
            System.out.println("extClassloader加载以下文件：");
            System.out.println(System.getProperty("java.ext.dirs"));

            System.out.println();
            System.out.println("appClassLoader加载以下文件：");
            System.out.println(System.getProperty("java.class.path"));

        }

    }
}
