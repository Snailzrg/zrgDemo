package effectiveJava.chapter5;

import java.util.List;
import java.util.Set;


/**
 * jdk泛型
 *
 * @author zhouruigang
 * 2019/8/15 17:22
 */
public class FanXinDemo<T extends Number> {

    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//
//        unsafeAdd(list, Integer.valueOf(42));
//        String s = list.get(0);
//
//        System.out.println(getCommonCount(null
//                ,null));

        /**
         * 不可具体化的类型
         *
         */

        //     List<String>[] stringList = new List<String>[1];
        List<?>[] stringList = new List<?>[1];

//        List<Integer>  integerList = List.of(42);

        Object[] objects = stringList;

//        objects[0] = integerList;

//        String s=stringList[0].get(0);



    }

    @SuppressWarnings("unused")
	private static void unsafeAdd(List<Object> list, Object s) {
        list.add(s);
    }

    @SuppressWarnings({ "unused", "rawtypes" })
	private static int getCommonCount(Set s1, Set s2) {
        int count = 0;
        for (Object s : s1) {
            if (s2.contains(s))
                count++;
        }
        return count;
    }

    @SuppressWarnings("unused")
	private static int getCommonCount2(Set<?> s1, Set<?> s2) {
        int count = 0;
        for (Object s : s1) {
            if (s2.contains(s))
                count++;
        }
        return count;
    }


    private static int getCommonCount3(Set<?> ... args) {

        return 0;
    }



}
