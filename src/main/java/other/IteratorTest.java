package other;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhouruigang
 * 2019/8/15 15:22
 */
public class IteratorTest {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "2", "3", "4");
//        for (int i=0;i< list.size();i++){
//
//            list.remove(2);
//        }
//
//        for (String s : list) {
//            if (s.equals("2"))
//                list.remove(s);
//        }
//        list.stream().filter(System.out::println);
//        list = list.stream().filter(s -> {
//            if (!s.equals("2"))
//                return s;
//        }).collect(Collectors.toList());
        @SuppressWarnings("rawtypes")
		Iterator it = list.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            System.out.println(obj);
            if (obj.equals("1")) {
                it.remove();
            }
        }
        list.stream().forEach(System.out::println);

    }

}
