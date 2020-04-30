package effectiveJava.chapter5;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhouruigang
 * 2019/8/19 9:34
 */
public class UnionFanXin {

    public static void main(String[] args) {

        Set<String> s1 = new HashSet<String>() {{
            add("XZ13s");
            add("AB21/X");
            add("YYLEX");
            add("AR2D");
        }};

        Set<String> s2 = new HashSet<String>() {{
            add("S2.1");
            add("S2.2/X");
            add("S2.3");
            add("S2.4");
        }};

        Set<String> s3 = union(s1,s2);
        System.out.println(s3);
    }

    /**
     *
     * @param s1
     * @param s2
     * @param <E>�?<E extends Comparable<E>>类型参数�?
     * @return
     */
    public static <E extends Comparable<E>> Set<E> union (Set<E> s1,Set<E>s2){
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }
}
