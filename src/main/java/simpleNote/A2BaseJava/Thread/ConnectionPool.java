package simpleNote.A2BaseJava.Thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * 利用Future模式 实现的数据库连接池
 *
 * @auther: zhouruigang
 * @date: 2018/11/21 16:40
 */
public class ConnectionPool {
    /**
     * 我们用了ConcurrentHashMap，这样就不必把getConnection方法置为synchronized(当然也可以用Lock)，当多个线程同时调用getConnection方法时，性能大幅提升。
     *
     * 貌似很完美了，但是有可能导致多余连接的创建，推演一遍：
     *
     * 某一时刻，同时有3个线程进入getConnection方法，调用pool.containsKey(key)都返回false，然后3个线程各自都创建了连接。虽然ConcurrentHashMap的put方法只会加入其中一个，但还是生成了2个多余的连接。如果是真正的数据库连接，那会造成极大的资源浪费。
     *
     * 所以，我们现在的难点是：如何在多线程访问getConnection方法时，只执行一次createConnection。
     * 结合之前Future模式的实现分析：当3个线程都要创建连接的时候，如果只有一个线程执行createConnection方法创建一个连接，其它2个线程只需要用这个连接就行了。再延伸，把createConnection方法放到一个Callable的call方法里面，然后生成FutureTask。我们只需要让一个线程执行FutureTask的run方法，其它的线程只执行get方法就好了。
     */

    private ConcurrentHashMap<String, FutureTask<Connection>> pool =
            new ConcurrentHashMap<String, FutureTask<Connection>>();

    public Connection getConnection(String key) throws InterruptedException, ExecutionException {
        FutureTask<Connection> connectionTask = pool.get(key);
        if (connectionTask != null) {
            return connectionTask.get();
        } else {
            Callable<Connection> callable = new Callable<Connection>() {
                @Override
                public Connection call() throws Exception {
                    return createConnection();
                }
            };
            FutureTask<Connection> newTask = new FutureTask<Connection>(callable);
            connectionTask = pool.putIfAbsent(key, newTask);
            if (connectionTask == null) {
                connectionTask = newTask;
                connectionTask.run();
            }
            return connectionTask.get();
        }
    }

    public Connection createConnection() {
        return new Connection();
    }

    class Connection {
    }
}

/**
 * 当3个线程同时进入else语句块时，各自都创建了一个FutureTask，但是ConcurrentHashMap只会加入其中一个。第一个线程执行pool.putIfAbsent方法后返回null，然后connectionTask被赋值，接着就执行run方法去创建连接，最后get。后面的线程执行pool.putIfAbsent方法不会返回null，就只会执行get方法。
 *
 * 在并发的环境下，通过FutureTask作为中间转换，成功实现了让某个方法只被一个线程执行。
 */