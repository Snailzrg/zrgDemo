package deignPatterns.singlotn2;

import java.io.*;

/**
 * TryWithResourceDemo 类实现了 AutoCloseable，因此可用作 try-with-resources 语句的一部分，
 * 如 main() 方法中所示。我们特意添加了一些控制台输出，并在该类的 work() 和 close() 方法中抛出异常。运行该程序将产生以下输出：
 * @author zhouruigang
 * 2019/8/13 14:42
 */
public class TryWithResourceDemo implements AutoCloseable {

    @Override
    public void close() {
        System.out.println(">>> close()");
        throw new RuntimeException("Exception in close()");
    }


    public void work() throws MyException {
        System.out.println(">>> work()");
        throw new MyException("Exception in work()");
    }

    public static void main(String[] args) throws MyException{
//        copy2("A", "b");
//        try (TryWithResourceDemo autoClose = new TryWithResourceDemo()) {
//            autoClose.work();
//        } catch (MyException e) {
//            e.printStackTrace();
//        }

        //demo2 异常被屏蔽了 ！！
        TryWithResourceDemo autoClose = new TryWithResourceDemo();
        try {
            autoClose.work();
        } finally {
            autoClose.close();
        }
    }

    static void copy2(String src, String dst) throws IOException {
        try  {
            InputStream inputStream = new FileInputStream(src);
            OutputStream outputStream = new FileOutputStream(dst);
            byte[] bytes = new byte[1024];
            int n;
            while ((n = inputStream.read(bytes)) >= 0) {
                outputStream.write(bytes, 0, n);
            }
        }catch (Exception e){

        }
    }

    /**
     * Think in java page 28
     * try 和 finally中的代码可能抛出异常
     *
     * @param src
     * @param dst
     * @throws IOException
     */
    static void copy(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream outputStream = new FileOutputStream(dst);
            try {
                byte[] bytes = new byte[1024];
                int n;
                while ((n = in.read(bytes)) >= 0)
                    outputStream.write(bytes, 0, n);

            } finally {
                outputStream.close();
            }
        } finally {
            in.close();
        }

    }
}
class MyException extends Exception {

    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }
}
