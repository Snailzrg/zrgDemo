package util.pdfUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 三种Java下生成PDF方式的比较
 *
 * @author snail
 * 2020/7/29 8:46
 */
public class PDFUtil {

    public static void main(String[] args) throws IOException {
//        File file = File.createTempFile("xmlStrPrint","pdf");
//
        try {
            File fl = null;
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
                fl = File.createTempFile("smxttmp", ".txt");
                fos = new FileOutputStream(fl);
                System.out.println(fl.getAbsolutePath());
                out = new ObjectOutputStream(fos);
                out.writeObject("good");
            } finally {
                if (null != out) {
                    out.flush(); // 如果没有这行就会发生严重的貌似JVM bug的超级异常错误，详细见http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6406859
                    out.close();
                }
                if (null != fos) {
                    fos.close();
                }
                if (null != fl) {
                    fl.deleteOnExit(); // 令临时文件在JVM关闭的时候自动删除
                    fl.delete(); // 立刻删除临时文件
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
