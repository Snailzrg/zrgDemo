import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @auther: zhouruigang
 * @date: 2018/11/26 8:38
 */
public class Others {

    @Test
    public void test1() {
        String user = env("ACTIVEMQ_USER", "admin");
        System.out.println(user);
        System.out.println(System.getenv("PATH"));
    }

    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if (rc == null)
            return defaultValue;
        return rc;
    }

    private static final int THREADS_CONUT = 20;

    //普通情况
    public static int count = 0;

    public static void increase() {
        count++;
    }

    //AtomicInteger.incrementAndGet()方法的原子性。
    public static AtomicInteger count2 = new AtomicInteger(0);

    public static void increase2() {
        count2.incrementAndGet();
    }

    /**
     * 测试：多线程 情况下 原子操作
     */
    @Test
    public void testA() {
        Thread[] threads = new Thread[20];
        for (int i = 0; i < THREADS_CONUT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        increase();
                        increase2();
                    }
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(count);
        System.out.println(count2);
    }

    /**
     *
     */
    @Test
    public void test3() {
        int c = Optional.ofNullable("").map(String::length).orElse(-1);
        System.out.println(c);

        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);

        String[] ids = {"aa", "bb", "cc", "dd"};
        String cx = Arrays.stream(ids).reduce("", String::concat);

        System.out.println(cx);

// 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
// 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
// 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
// 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);

        String str = "a,b,c,d,e,f,g";

        List<String> sList = Stream.of(str.split(","))
                .map(elem -> new String(elem))
                .collect(Collectors.toList());
        // System.out.println(sList.size());

        sList.stream().map(r -> r + "zrg").forEach(System.out::println);
    }

    @Test
    public void TestLmd() {
        /**
         * 剔除 B 中 A
         */
        List<String> idsA = Arrays.asList("A", "1", "2");
        List<String> idsB = Arrays.asList("A", "B", "C", "D");

        List<String> idsBB = idsB.stream().filter(id -> (
                !id.equals("B"))).collect(Collectors.toList());

        idsBB.forEach(System.out::println);

        System.out.println(idsBB.size());

    }


}
