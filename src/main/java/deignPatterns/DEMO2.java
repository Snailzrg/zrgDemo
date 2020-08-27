package deignPatterns;

/**
 * @author zhouruigang
 * 2019/8/14 11:50
 */
public class DEMO2 {

    public static void main(String[] args) {

        System.out.println(A.value);
//        System.out.println(A.name);
//
//        A.value="aaa";
//        A.name="aaaName";
        System.out.println(A.sz.length);
//        System.out.println(A.name);
    }
}

class A{

    private static final String name = "nameA";

    public static final String value = "valueA";

    public static final String[] sz= {"a","b"};



}