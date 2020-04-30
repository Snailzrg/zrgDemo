package study.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouruigang
 * 2019/3/29 13:55
 */
public class Zplay {

    public static void main(String[] args) {


//
//        List<Person> list = map.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey()))
//                .map(e -> new Person(e.getKey(), e.getValue())).collect(Collectors.toList());
//        List<Person> list = map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue))
//                .map(e -> new Person(e.getKey(), e.getValue())).collect(Collectors.toList());
//        List<Person> list = map.entrySet().stream().sorted(Map.Entry.comparingByKey())
//                .map(e -> new Person(e.getKey(), e.getValue())).collect(Collectors.toList());



        List<String> idss= new ArrayList<>();
        List<String> ids1= Arrays.asList("1","2","3","4");
        List<String> ids2= Arrays.asList("A","2","B","C");
        idss.addAll(ids1);
        idss.addAll(ids2);


      //  idss.stream().distinct().map(i->);



//        Arrays.asList(arr2).forEach(o -> { if (sb.length() > 0) {sb.append(link);} sb.append(start + o + end);});//forEach函数实现内部迭代
//        System.out.println(sb);
//        //map collect
//        String str1_1 = Arrays.stream(arr2).map(i -> start+i.toString()+end).

       // String str = List.stream().map(Restriction::getValue).collect(Collectors.joining(","));
//
   //     System.out.println(str);

        /****
         *
         * boxed 只能用于 int 类型数组
         *
         */


      //  String[] arr = {"1","2","3","4"};
//        int[] arr = {1,2,3,4};
//
//        String str1 = Arrays.stream(arr).boxed().map(i -> i.toString()) //必须将普通数组 boxed才能 在 map 里面 toString
//                .collect(Collectors.joining(""));
//        System.out.println(str1);
//
//        String str2 = Arrays.stream(arr).boxed().map(i -> i.toString()).reduce("", String::concat);
//        System.out.println(str2);
//
//        String str3 = Arrays.stream(arr).boxed().map(Object :: toString).reduce("", String::concat); // 方法引用Object：：toString
//        System.out.println(str3);

    }
}
