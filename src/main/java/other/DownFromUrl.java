package other;

import com.sun.org.apache.bcel.internal.generic.NEW;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author snail
 * 2020/8/28 2:51
 */
public class DownFromUrl {
    /**
     * 将网络图片转换成Base64编码字符串
     *
     * @param imgUrl 网络图片Url
     * @return
     */
    public static String getImgUrlToBase64(String imgUrl) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        HttpURLConnection conn = null;
        byte[] buffer = null;
        try {
            URL httpUrl=new URL(imgUrl);
            conn=(HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();
            outputStream = new ByteArrayOutputStream();
            // 将内容读取内存中
            buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            buffer = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    // 关闭inputStream流
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // 关闭outputStream流
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对字节数组Base64编码
        return new BASE64Encoder().encode(buffer);
    }


    public static void main(String[] args){
        String url ="https://mmbiz.qpic.cn/mmbiz_png/8KKrHK5ic6XBEWaiblGb3iaicgTuUPXoIgy8PgcPAJt8x78B7dq0aXo63Uf8GicSP5b41E3jic13owyVkkqlbEuF1hlQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)";
        String encodeStr =  getImgUrlToBase64(url);
        encodeStr = "data:image/png;base64," + encodeStr;
        System.out.println(encodeStr);
    }
    public static void doImg() {
        //System.out.println("fileName---->"+filePath);
        //创建不同的文件夹目录
        byte[] data = null;
        String encodeStr = null;
        String filePath = "C:/Users/snail/Desktop/";
        String url ="https://mmbiz.qpic.cn/mmbiz_png/8KKrHK5ic6XBEWaiblGb3iaicgTuUPXoIgy881Ab5icgxyjoxcPwt4rGeUQcMer4X2TT6miaXiaeAz7CsQRibpAWBziaa8A/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)";
        File file=new File(filePath);
        //判断文件夹是否存在
        if (!file.exists())
        {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        BufferedImage bufferImage = null;
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        InputStream inputStream = null;
        try
        {
            // 建立链接
            URL httpUrl=new URL(url);
            conn=(HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {
                filePath += "/";
            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(filePath+"124WW3.png");
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);
            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while(length != -1)
            {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            // 读取图片字节数组
            try {
                InputStream in = new FileInputStream(filePath+"1243.png");;
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
//            System.out.println(encoder.encode(buf)); // 返回Base64编码过的字节数组字符串
            //前端解析需要带上前缀
            encodeStr = "data:image/png;base64," + encoder.encode(data);

            System.out.println(encodeStr);
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("抛出异常！！");
        }

    }


    private static void doImg(InputStream in1){
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = in1;
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println(encoder.encode(data)); // 返回Base64编码过的字节数组字符串
    }


    //
    private static String doQrCode(final String content) {
        String encodeStr = "";
        BufferedImage bufferImage = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] data = null;
        try {
//            bufferImage = createImg(content);
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferImage, "jpg", byteArrayOutputStream);
            inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            data = new byte[inputStream.available()];
            inputStream.read(data);
            BASE64Encoder encoder = new BASE64Encoder();
            //前端解析需要带上前缀
            encodeStr = "data:image/png;base64," + encoder.encode(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return encodeStr.trim();
    }

}
