package deignPatterns.singlotn1;

/**
 * 懒汉式 + 饿汉式
 * @author zhouruigang
 * 2019/8/10 16:32
 */
public class Singleton {
    private static Singleton instance;
    private Singleton (){}

    //线程不安全 因为没有加锁 synchronized   lazy loading 很明显，不要求线程安全，在多线程不能正常工作
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    //线程安全  但加锁会影响效率。
    //getInstance() 的性能对应用程序不是很关键（该方法使用不太频繁）。
    public static synchronized Singleton getInstance2() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }


    //饿汉式--类加载时就初始化，浪费内存。没有加锁，执行效率会提高.它基于 classloader 机制避免了多线程的同步问题
    public static Singleton getInstance3() {
        return instance;
    }


    /**
     * 双检锁/双重校验锁  这种方式采用双锁机制，安全且在多线程情况下能保持高性能
     * @return
     */
    private volatile static Singleton singleton;
    public static Singleton getSingleton4() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

}
