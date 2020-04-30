package demo.singlotn1;

import java.io.Serializable;

/**
 *
 * @author zhouruigang
 * 2019/8/10 16:29
 */
public class SingleObject implements Serializable {

    private static final long serialVersionUID = 4220614030499688662L;
    //创建 SingleObject 的一个对象
    private static SingleObject instance = new SingleObject();

    //让构造函数为 private，这样该类就不会被实例化
    private SingleObject(){}

    //获取唯一可用的对象  饿汉式
    public static SingleObject getInstance(){
        return instance;
    }

    public void showMessage(){
        System.out.println("Hello World!");
    }

    /**
     * 解决序列化问题  or 使用 枚举
     * @return
     */
    private Object readResolve() {
        return instance;

    }

}
