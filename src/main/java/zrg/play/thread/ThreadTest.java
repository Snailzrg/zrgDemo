package zrg.play.thread;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import util.poiUtil.ExcelUtil_Zrg;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;


/*
 *线程池
 */
public class ThreadTest {

    class MyTask implements Runnable {
        private int taskNum;

        public MyTask(int num) {
            this.taskNum = num;

        }

        @Override
        public void run() {
            System.out.print("正在执行task " + taskNum);
            try {
                System.out.println("SLEEP:4000->millis");
                Thread.currentThread().sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task " + taskNum + "执行完毕");
        }
    }

    @Test
    public void testThread1() {
        /***
         *Creates a new {@code ThreadPoolExecutor} with the given initial
         *      * parameters and default thread factory and rejected execution handler.
         *      * It may be more convenient to use one of the {@link Executors} factory
         *      * methods instead of this general purpose constructor.
         *
         *在java doc中，并不提倡我们直接使用ThreadPoolExecutor，而是使用Executors类中提供的几个静态方法来创建线程池：
         * Executors.newCachedThreadPool();        //创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE
         * Executors.newSingleThreadExecutor();   //创建容量为1的缓冲池
         * Executors.newFixedThreadPool(int);    //创建固定容量大小的缓冲池
         *
         * 线程池的正确使用
         *
         * 以下阿里编码规范里面说的一段话：
         * 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风说明：Executors各个方法的弊端：
         * 1）newFixedThreadPool和newSingleThreadExecutor: 主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
         * 2）newCachedThreadPool和newScheduledThreadPool: 主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
         */

        /**
         * 核心线程5 线程池容量10 保持时间200
         *
         *  线程大小 应该小于 队列大小+最大size
         */

        long startTime = System.currentTimeMillis();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(6, 11, 200, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 19; i++) {
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());

        }
        executor.shutdown();
        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");
    }

    /***
     * 不同队列的比较
     */
    class ThreadPoolTest implements Runnable {
        public void run() {
            synchronized (this) {
                try {
                    System.out.println("线程名称：" + Thread.currentThread().getName());
                    Thread.sleep(3000); //休眠是为了让该线程不至于执行完毕后从线程池里释放
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testThreadQueue() throws InterruptedException {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(4); //固定为4的线程队列
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 6, 1, TimeUnit.DAYS, queue);
        for (int i = 0; i < 10; i++) {
            executor.execute(new Thread(new ThreadPoolTest(), "TestThread".concat("" + i)));
            int threadSize = queue.size();
            System.out.println("线程队列大小为-->" + threadSize);
            if (threadSize == 4) {
                queue.put(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("我是新线程，看看能不能搭个车加进去！");
                    }
                });
            }
        }
        executor.shutdown();
    }


    /***
     * 线程池读取Excel文件 List格式
     */
    @Test
    public void testWithExcel() throws Exception {
        InputStream inputStream = new FileInputStream("C:\\Users\\zhouruigang\\Desktop\\新的ygEXCEL3.xlsx");
        Workbook wb = ExcelUtil_Zrg.getWorkbook(inputStream, "新的ygEXCEL3.xlsx");
        List<List<String>> lists = ExcelUtil_Zrg.getExcelString(wb, null, null, 0);

        int threadCount = lists.size() % 200 == 0 ? lists.size() / 200 : lists.size() / 200 + 1;

        /***
         * 此时 队列 核心池数量 总数量 都设置为最大值 后续优化
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(threadCount, threadCount, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        long startTime = System.currentTimeMillis();
        Iterator<List<String>> iterator = lists.iterator();
        int i = 0;
        while (i < threadCount) {
            i++;
            System.out.println("线程数量" + executor.getPoolSize() + "--max线程数量" + threadCount);
//            while(){
//
//
//
//
//
//            }

        }
        executor.shutdown();//关闭 不会立马结束未完成的线程
        System.out.println(System.currentTimeMillis() - startTime + "---milles");
    }

    /***
     * Map 格式
     * @throws Exception
     */
    @Test
    public void testWithMAPExcel() throws Exception {
        InputStream inputStream = new FileInputStream("C:\\Users\\zhouruigang\\Desktop\\新的ygEXCEL3.xlsx");
        Workbook wb = ExcelUtil_Zrg.getWorkbook(inputStream, "新的ygEXCEL3.xlsx");
        Map<Integer, String> map = ExcelUtil_Zrg.mapExcelDate(wb, null, null, 0);
        System.out.println(map.size());
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        int threadCount = map.size() % 200 == 0 ? map.size() / 200 : map.size() / 200 + 1;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(threadCount, threadCount, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (i < threadCount) {
            i++;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String[] values = null;
                    while (map.size() > 0) {
                        if (it.hasNext()) {
                            Map.Entry<Integer, String> entry = it.next();
                            //分割时防止空的行 出错
                            values = (entry.getValue() + "0").split("-");
                            it.remove();
                        }

                    }

                    if (values != null) {
                        /**
                         * 数据处理
                         */

                    }
                }
            });
        }
        executor.shutdown();//关闭 不会立马结束未完成的线程



        System.out.println(System.currentTimeMillis() - startTime + "---milles");
    }























    /**
     * 迭代器的next()方法 只能调用一次 不然会报 NoSuchElementException
     * 测试迭代器报错
     */
    @Test
    public void trstIterator() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "张三");
        map.put(2, "李四");
        map.put(3, "王武");
        List<Map.Entry<Integer, String>> arr = new ArrayList<Map.Entry<Integer, String>>(map.entrySet());

        Iterator<Map.Entry<Integer, String>> it1 = arr.iterator();
        while (it1.hasNext()) {
            Map.Entry<Integer, String> entry = it1.next();

            it1.remove();
        }

    }

    @Test
    public void trstIterator2() {

        List<String> aList = new ArrayList<>();
        aList.add("张三");
        aList.add("李四");
        aList.add("王武");
        aList.add("王武");

        Iterator<String> iterator = aList.iterator();
        while (iterator.hasNext()) {

            String X = iterator.next();

            System.out.println(X);

            iterator.remove();
        }

    }

}
