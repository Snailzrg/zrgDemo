package simpleNote.A2BaseJava.Thread;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*****
 * Blog：https://www.cnblogs.com/cz123/p/7693064.html
 * Book:葛一名《java的优化..》
 * 详解FutureTask http://www.importnew.com/30531.html
 *
 * Futrue模式: 对于多线程，如果线程A要等待线程B的结果，那么线程A没必要等待B，直到B有结果，可以先拿到一个未来的Future，等B有结果是再取真实的结果。
 *  场景：假如你突然想做饭，但是没有厨具，也没有食材。网上购买厨具比较方便，食材去超市买更放心。
 * 实现分析：在快递员送厨具的期间，我们肯定不会闲着，可以去超市买食材。所以，在主线程里面另起一个子线程去网购厨具。
 * 但是，子线程执行的结果是要返回厨具的，而run方法是没有返回值的。所以，这才是难点，需要好好考虑一下。
 */
public class FutureStudy {

    static class OnlineShopping extends Thread {

        private Chuju chuju;

        @Override
        public void run() {
            System.out.println("开始下单..... ");
            System.out.println("等待快递5000millis");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("快递的厨具送到.....");
            chuju = new Chuju();
        }
    }

    //  用厨具烹饪食材
    static void cook(Chuju chuju, Shicai shicai) {
    }

    // 厨具类
    static class Chuju {
    }

    // 食材类
    static class Shicai {
    }

    /***
     * 传统的方法
     */
    @Test
    public void testMain1() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        OnlineShopping thread = new OnlineShopping();
        thread.start();
        thread.join();  // 保证厨具送到
        // 第二步 去超市购买食材
        Thread.sleep(2000);  // 模拟购买食材时间
        Shicai shicai = new Shicai();
        System.out.println("第二步：食材到位");
        // 第三步 用厨具烹饪食材
        System.out.println("第三步：开始展现厨艺");
        cook(thread.chuju, shicai);
        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");
    }

    /***
     *可以看到，多线程已经失去了意义。在厨具送到期间，我们不能干任何事。对应代码，就是调用join方法阻塞主线程。
     * 有人问了，不阻塞主线程行不行？？？
     * 不行！！！
     * 从代码来看的话，run方法不执行完，属性chuju就没有被赋值，还是null。换句话说，没有厨具，怎么做饭。
     * Java现在的多线程机制，核心方法run是没有返回值的；如果要保存run方法里面的计算结果，必须等待run方法计算完，无论计算过程多么耗时。
     * 面对这种尴尬的处境，程序员就会想：在子线程run方法计算的期间，能不能在主线程里面继续异步执行？？？
     * Where there is a will，there is a way！！！
     * 这种想法的核心就是Future模式，下面先应用一下Java自己实现的Future模式。
     */
//Callable 和 Runable 的区别
//    两者都可用来编写多线程程序；
//            两者都需要调用Thread.start()启动线程；
//    不同点：
//    两者最大的不同点是：实现Callable接口的任务线程能返回执行结果；而实现Runnable接口的任务线程不能返回结果；
//    Callable接口的call()方法允许抛出异常；而Runnable接口的run()方法的异常只能在内部消化，不能继续上抛；
//    注意点：
//    Callable接口支持返回执行结果，此时需要调用FutureTask.get()方法实现，此方法会阻塞主线程直到获取‘将来’结果；当不调用此方法时，主线程不会阻塞！


    @Test
    public void testMain2() throws InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();
        //可以用Lambda表达式代替
        Callable<Chuju> onlineShopping = new Callable<Chuju>() {
            @Override
            public Chuju call() throws Exception {
                System.out.println("第一步：下单");
                System.out.println("第一步：等待送货");
                Thread.sleep(5000);  // 模拟送货时间
                System.out.println("第一步：快递送到");
                return new Chuju();
            }
        };

//        Callable<Chuju> onlineShopping=new  Callable<Chuju>(()->{
//            System.out.println("第一步：下单");
//                System.out.println("第一步：等待送货");
//                Thread.sleep(5000);  // 模拟送货时间
//                System.out.println("第一步：快递送到");
//
//        }).call();

        FutureTask<Chuju> task = new FutureTask<Chuju>(onlineShopping);
        new Thread(task).start();
        // 第二步 去超市购买食材
        Thread.sleep(2000);  // 模拟购买食材时间
        Shicai shicai = new Shicai();
        System.out.println("第二步：食材到位");
        // 第三步 用厨具烹饪食材
        if (!task.isDone()) {  // 判断是否收到货/（是否线程完成）
            System.out.println("第三步：厨具还没到，心情好就等着（可以调用cancel方法取消）");
//            task.cancel(true);
//            System.out.println("取消");
        }
        Chuju chuju = task.get();
        System.out.println("第三步：厨具到位，开始展现厨艺");
        cook(chuju, shicai);

        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");
    }
    /***
     *  Callable接口可以看作是Runnable接口的补充，call方法带有返回值，并且可以抛出异常。
     *  把Callable实例当作参数，生成一个FutureTask的对象，然后把这个对象当作一个Runnable，作为参数另起线程。
     * public class FutureTask<V> implements RunnableFuture<V>
     * public interface RunnableFuture<V> extends Runnable, Future<V>
     *
     这个继承体系中的核心接口是Future。Future的核心思想是：一个方法f，计算过程可能非常耗时，等待f返回，显然不明智。可以在调用f的时候，立马返回一个 Future，可以通过Future这个数据结构去控制方法f的计算过程。
     这里的控制包括：
     get方法：获取计算结果（如果还没计算完，也是必须等待的）
     cancel方法：还没计算完，可以取消计算过程
     isDone方法：判断是否计算完
     isCancelled方法：判断计算是否被取消
     这些接口的设计很完美，FutureTask的实现注定不会简单，后面再说。

     3）在第三步里面，调用了isDone方法查看状态，然后直接调用task.get方法获取厨具，不过这时还没送到，所以还是会等待3秒。对比第一段代码的执行结果，这     里我们节省了2秒。这是因为在快递员送货期间，我们去超市购买食材，这两件事在同一时间段内异步执行。
     通过以上3步，我们就完成了对Java原生Future模式最基本的应用。下面具体分析下FutureTask的实现，先看JDK8的，再比较一下JDK6的实现。
     既然FutureTask也是一个Runnable，那就看看它的run方法
     */

    /***FUTUREtASK 实现的RunAble的run 方法
     *  public void run() {
     *         if (state != NEW ||
     *             !UNSAFE.compareAndSwapObject(this, runnerOffset,
     *                                          null, Thread.currentThread()))
     *             return;
     *         try {
     *             Callable<V> c = callable; ; // 这里的callable是从构造方法里面传人的
     *             if (c != null && state == NEW) {
     *                 V result;
     *                 boolean ran;
     *                 try {
     *                     result = c.call();
     *                     ran = true;
     *                 } catch (Throwable ex) {
     *                     result = null;
     *                     ran = false;
     *                     setException(ex);  // 保存call方法抛出的异常
     *                 }
     *                 if (ran)
     *                     set(result); // 保存call方法的执行结果
     *             }
     *         } finally {
     *             // runner must be non-null until state is settled to
     *             // prevent concurrent calls to run()
     *             runner = null;
     *             // state must be re-read after nulling runner to prevent
     *             // leaked interrupts
     *             int s = state;
     *             if (s >= INTERRUPTING)
     *                 handlePossibleCancellationInterrupt(s);
     *         }
     *     }
     */
    /***
     * 这个get方法里面处理等待线程队列的方式是调用了acquireSharedInterruptibly方法，看过我之前几篇博客文章的读者应该非常熟悉了。其中的等待线程队列、线程挂 起和唤醒等逻辑，这里不再赘述
     *
     *先看try语句块里面的逻辑，发现run方法的主要逻辑就是运行Callable的call方法，然后将保存结果或者异常（用的一个属性result）。这里比较难想到的是，将call方法抛出的异常也保存起来了。
     */



    /**
     * 最后来看看，Future模式衍生出来的更高级的应用。
     * 再上一个场景：我们自己写一个简单的数据库连接池，能够复用数据库连接，并且能在高并发情况下正常工作。
     */





}
