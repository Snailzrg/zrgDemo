package common.util.ImgUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 对图片加密 只是对字节数组 加密 ----
 */
public class TestImg {

    public static void main(String[] args) throws IOException {

        File file =new File("C:\\Users\\zhouruigang\\Desktop\\img123.png");
        if(file.exists()){
            System.out.println(1);
        }

        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream("C:\\Users\\zhouruigang\\Desktop\\img1234.png");

        int b;

        while ((b = fis.read()) != -1) {
            fos.write(b ^ 123);
        }

        fis.close();
        fos.close();
    }

}



