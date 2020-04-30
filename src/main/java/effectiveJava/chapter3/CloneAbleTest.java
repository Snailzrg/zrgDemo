package effectiveJava.chapter3;

import lombok.Data;

/**
 * 一般步骤是（浅复制）：
 *
 * 1. 被复制的类需要实现Clonenable接口（不实现的话在调用clone方法会抛出CloneNotSupportedException异常) 该接口为标记接口(不含任何方法)
 * 2. 覆盖clone()方法，访问修饰符设为public。方法中调用super.clone()方法得到需要的复制对象，（native为本地方法)
 *jdk的集合类clone()方法默认实现为浅拷贝
 * 3.如果想要深拷贝一个对象， 这个对象必须要实现Cloneable接口，重写clone方法，并且在clone方法内部，把该对象引用的其他对象也要clone一份 ， 
 * 这就要求这个成员变量对象必须也要实现Cloneable接口，并且重写clone方法。对于超过两层嵌套的情况，则要所有成员变量都递归的实现clone方法才能实现完全的深拷贝。
 *
 *
 *   // 浅拷贝(使用时两者选其一)
 *     @Override
 *     protected Object clone() throws CloneNotSupportedException {
 *         return super.clone();
 *     }
 *
 *     // 深拷贝(使用时两者选其一)
 *     @Override
 *     protected Object clone() throws CloneNotSupportedException {
 *         Object object = super.clone();
 *         Food food = ((Dog) object).getFood();
 *         ((Dog) object).setFood((Food) food.clone());
 *         return object;
 *     }
 *
 * @author zhouruigang
 * 2019/8/13 17:16
 */
public class CloneAbleTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        School s1 = new School();
        s1.setSchoolName("实验小学");
        s1.setStuNums(100);
        Student stu1 = new Student();
        stu1.setAge(20);
        stu1.setName("zhangsan");
        stu1.setSex(new StringBuffer("男"));
        s1.setStu(stu1);
        System.out.println("s1: " + s1 + " s1的hashcode:" + s1.hashCode() + "  s1中stu1的hashcode:" + s1.getStu().hashCode());
        School s2 = s1.clone();  //调用重写的clone方法，clone出一个新的school---s2
        System.out.println("s2: " + s2 + " s2的hashcode:" + s2.hashCode() + " s2中stu1的hashcode:" + s2.getStu().hashCode());

    }
}

@Data
class Student {
    private String name;   //姓名
    private int age;       //年龄
    private StringBuffer sex;  //性别

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", sex=" + sex + "]";
    }
}

@Data
class School implements Cloneable {
    private String schoolName;   //学校名称
    private int stuNums;         //学校人数
    private Student stu;         //一个学生

    @Override
    protected School clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return (School) super.clone();
    }

    @Override
    public String toString() {
        return "School [schoolName=" + schoolName + ", stuNums=" + stuNums + ", stu=" + stu + "]";
    }
}

