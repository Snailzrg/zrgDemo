package demo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * sorted浠庡皬鍒板ぇ 灏忎簬 -1 澶т簬 1 绛変簬0
 * @author zhouruigang
 * 2019/8/13 16:27
 */
public class Demo {

    public static void main(String[] args) {
        double s = 1.2;
        double ss = 1.1;

        System.out.println(s == ss);

        System.out.println(Double.compare(s, ss));

        Employee e1 = new Employee("John", 25, 3000, 9922001);
        Employee e2 = new Employee("Ace", 22, 2000, 5924001);
        Employee e3 = new Employee("Keith", 35, 4000, 3924401);
        Employee e4 = new Employee("Keith", 25, 4000, 001);
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        employees.add(e4);
        /** * @SuppressWarnings({"unchecked", "rawtypes"}) * default void sort(Comparator<? super E> c) { * Object[] a = this.toArray(); * Arrays.sort(a, (Comparator) c); * ListIterator<E> i = this.listIterator(); * for (Object e : a) { * i.next(); * i.set((E) e); * } * } * * sort 瀵硅薄鎺ユ敹涓�涓� Comparator 鍑芥暟寮忔帴鍙ｏ紝鍙互浼犲叆涓�涓猯ambda琛ㄨ揪寮� */
//        employees.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
//        Collections.sort(employees, (o1, o2) -> o1.getName().compareTo(o2.getName()));

//        employees.sort(Comparator.comparing((Employee o) -> o.age).thenComparing(Employee::));
        employees.forEach(System.out::println);

    }

//    private static final Comparator<PhoneNumber> COMPARABLE =
//            Comparator.comparingDouble((PhoneNumber pn)->)
}

@Data
class PhoneNumber {
    public double areCode;
    private float prefix;
}

/**
 * [Employee(name=John, age=25, salary=3000.0, mobile=9922001), * Employee(name=Ace, age=22, salary=2000.0, mobile=5924001), * Employee(name=Keith, age=35, salary=4000.0, mobile=3924401)]
 */
@Data
class Employee {
    String name;
    int age;
    double salary;
    long mobile;

    public Employee(String name, int age, double salary, long mobile) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.mobile = mobile;
    }
}