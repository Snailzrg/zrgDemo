package other;

import common.util.Patterns;
import common.util.StringUtil;
import sun.misc.BASE64Encoder;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;

/**
 * @author snail
 * 2020/8/27 14:24
 */
public class TMP {
//public  Map errorMAP =new HashMap<>();
//public List<String> errorList = new ArrayList<>();
//    private static StringCharBuffer kvImg;

    public static void main(String[] args) {
        doMdImgToBase64();
    }


    private static void doMdImgToBase64(){
        Map errorMAP =new HashMap<>();
        Map kvImg =new HashMap<>();
        File md = new File("C:\\Users\\snail\\Desktop\\【155期】面试官：你遇到过log4j2线程阻塞的场景吗，如何解决呢？.md");
        InputStream inputStream;
        Set<String> set = new HashSet<>();
        BufferedReader reader = null;
        try {
            inputStream = new FileInputStream(md);
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(md));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                String [] sts = tempString.split("\\(");
                String ss = sts[sts.length-1];
                String [] sts1 = ss.split("\\,");
                // 显示行号
                set.add(sts1[0]);
                System.out.println(tempString);

                Matcher matcher = Patterns.WEB_URL.matcher(tempString);
                if (matcher.find() && !tempString.contains("weixin")){
                    kvImg.put(sts1[0],tempString);
                    //URL 处理 获取图片
                    String baseImg = doImg(tempString,errorMAP);
                    if(StringUtil.isNotEmpty(baseImg)){
                        set.add(baseImg);
                    }
                }
                line++;
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    ignored.printStackTrace();
                }
            }
        }
    }


    public static String doImg(String url,Map errorMAP) {
        //System.out.println("fileName---->"+filePath);
        //创建不同的文件夹目录
        boolean flag = false;
        byte[] data = null;
        String encodeStr = null;
        String filePath = "C:/Users/snail/Desktop/";
//        String url = "https://mmbiz.qpic.cn/mmbiz_png/8KKrHK5ic6XBEWaiblGb3iaicgTuUPXoIgy881Ab5icgxyjoxcPwt4rGeUQcMer4X2TT6miaXiaeAz7CsQRibpAWBziaa8A/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)";
        File file = new File(filePath);
        //判断文件夹是否存在
        if (!file.exists()) {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        BufferedImage bufferImage = null;
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        InputStream inputStream = null;
        try {
            // 建立链接
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            if (!filePath.endsWith("/")) {
                filePath += "/";
            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(filePath + "124WW3.png");
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);
            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            // 读取图片字节数组
            try {
                InputStream in = new FileInputStream(filePath + "1243.png");
                ;
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("抛出异常！！");
            e.printStackTrace();
            errorMAP.put(url,e.getStackTrace());
            flag = true;
        }

        if(flag){
            return null;
        }
        String tmp = null;//new BASE64Encoder().encode("buffer");
        if(StringUtil.isNotEmpty(tmp)){
            tmp= "data:image/png;base64,"+tmp;
        }
        // 对字节数组Base64编码
        return tmp;

    }

        /**
         * URL地址下载图片
         * @param imgUrl
         */
    public static String getImgUrlToBase64(String imgUrl,Map errorMAP) {
        boolean flag = false;
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        HttpURLConnection conn = null;
        byte[] buffer = null;
        try {
            imgUrl.replace("(","");
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
            //区分url是否是资源 下载的图片或者 只是网页；TODO
            // 将内容读取内存中
            buffer = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            buffer = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            errorMAP.put(imgUrl,e.getStackTrace());
            flag = true;
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
        if(flag){
            return null;
        }
        String tmp = new BASE64Encoder().encode(buffer);
        if(StringUtil.isNotEmpty(tmp)){
            tmp= "data:image/png;base64,"+tmp;
        }
        // 对字节数组Base64编码
        return tmp;
    }
}
