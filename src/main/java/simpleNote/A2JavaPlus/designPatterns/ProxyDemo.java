package simpleNote.A2JavaPlus.designPatterns;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 设计模式 -- 代理模式
 *
 *代理可以帮助我们在不修改原有代码对功能进行改动，使你的具有更好的可扩展性。
 * 使用场景：
 * 1：我们调用一个接口的10个方法，只需要对其中一个方法进行修改扩展，或者重新实现。此时可以通过代理实现不修改原有代码进行扩展。
 * 2：通常，我们使用框架，写的普通的类，就额外具有一些特定的功能。是因为框架对写的类生成代理，通过代理对类进行功能扩展
 * 3: 可以控制接口范文权限
 *
 * 代理模式分为：静态代理、动态代理（jdk代理 接口）、Cglib代理(子类代理)
 * 静态代理跟动态代理：需要目标对象有实现接口
 * cglib代理：适用目标对象没有实现接口的代理
 * @author zhouruigang
 * 2019/11/11 15:56
 */
interface ISinger{
     void sing();
}

class Singer implements ISinger {
    @Override
    public void sing() {
        System.out.println("我是歌手，我要唱歌");
    }
}

//静态代理
class SingerProxy implements ISinger{
    private ISinger target;
    public SingerProxy(ISinger singer){
        this.target=singer;
    }
    @Override
    public void sing() {
        System.out.println("演唱开始....");
        target.sing();//静态代理的关键，调用目标对象的方法
        System.out.println("演唱结束....");
    }
}


public class ProxyDemo {

    public static void main(String[] args) {
        //静态
        ISinger targer=new Singer();
        SingerProxy proxy=new SingerProxy(targer);
        proxy.sing();
        System.out.println();

        //动态
        Disinger disinger = new DSinger();
        DynamicProxySinger dynamicProxySinger=new DynamicProxySinger(disinger);
        Disinger disingerProxy = (Disinger) dynamicProxySinger.getProxy();
        disingerProxy.sing();
        disingerProxy.dance();
        System.out.println();

        //cglib
        CglibSinger target=new CglibSinger();
        CglibSinger proxySinger = (CglibSinger) new CglibProxy(target).getProxy();
        proxySinger.sing();
    }
}

//动态代理
 interface Disinger {
     void sing();

     void dance();
}
class DSinger implements Disinger {
    @Override
    public void sing() {
        System.out.println("动态代理实现，我是歌手，我要歌唱");
    }

    @Override
    public void dance() {
        System.out.println("dance..");
    }
}

class DynamicProxySinger implements InvocationHandler {
    private Object target;
    public DynamicProxySinger(Object target){
        this.target=target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start..");
        Object object = method.invoke(target, args);
        System.out.println("end...");
        return object;
    }

    /**
     * 获得代理对象
     * @return
     */
    public Object getProxy(){
        Object object = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        return object;
    }
}


//---------------------------------------------------------------------------------------------//

//cglib代理：依赖spring-core
class CglibSinger {
    public void sing(){
        System.out.println("中国好声音.....");
    }
}

/**
 * cglib实现动态代理依赖spring-core
 * 适用目标对象没有实现接口的情况
 */
class CglibProxy implements MethodInterceptor {
    private  Object target;
    public CglibProxy(Object target){
        this.target=target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("开始时间："+new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(new Date()));
        Object object = method.invoke(target, objects);
        Thread.sleep(2000);
        System.out.println("结束时间："+new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(new Date()));
        return object;
    }

    //获取代理对象
    public  Object getProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        Object object = enhancer.create();
        return object;
    }
}
