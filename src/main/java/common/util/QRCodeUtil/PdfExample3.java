package common.util.QRCodeUtil;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author snail
 * 2020/8/5 9:28
 */
public class PdfExample3 {

    public static void main(String[] args) throws IOException, DocumentException, com.lowagie.text.DocumentException {
      String  htmlStr=   initHtmlStr();
        String htmlString="";
        htmlString = htmlStr.toString().replaceAll("\"", "'").replaceAll("<style>", "<style>body{font-family:SimSun;font-size:14px;}");    //注意这里为啥要写这个，主要是替换成这样的字体，如果不设置中文有可能显示不出来。

        OutputStream os = new FileOutputStream(new File("C:\\Users\\snail\\Desktop\\in.pdf"));    //生成PDF文件的路径
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver font = renderer.getFontResolver();
        font.addFont("C:/WINDOWS/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);//添加中文识别，这里是设置的宋体，Linux下要换成对应的字体
        renderer.setDocumentFromString(htmlString.toString());
        renderer.layout();
        renderer.createPDF(os);
        renderer.finishPDF();

    }


    private static String initHtmlStr(){
        StringBuilder strline = new StringBuilder("");
//        File fin = new File("\u202AC:\\Users\\snail\\Desktop\\TY.html");
        try (RandomAccessFile accessFile = new RandomAccessFile(new File("C:\\Users\\snail\\Desktop\\TY.html"), "r");
             FileChannel fcin = accessFile.getChannel();
        ){
            Charset charset = Charset.forName("UTF-8");
            int bufSize = 100000;
            ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);
            String enterStr = "\n";
            byte[] bs = new byte[bufSize];

            StringBuilder strBuf = new StringBuilder("");
            while (fcin.read(rBuffer) != -1) {
                int rSize = rBuffer.position();
                rBuffer.rewind();
                rBuffer.get(bs);
                rBuffer.clear();
                String tempString = new String(bs, 0, rSize,charset);
                tempString = tempString.replaceAll("\r", "");

                int fromIndex = 0;
                int endIndex = 0;
                while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {
                    String line = tempString.substring(fromIndex, endIndex);
                    line = strBuf.toString() + line;
                    strline.append(line.trim());

                    strBuf.delete(0, strBuf.length());
                    fromIndex = endIndex + 1;
                }
                if (rSize > tempString.length()) {
                    strline.append(tempString.substring(fromIndex, tempString.length()));
                    strBuf.append(tempString.substring(fromIndex, tempString.length()));
                } else {
                    strline.append(tempString.substring(fromIndex, rSize));
                    strBuf.append(tempString.substring(fromIndex, rSize));
                }
            }
            System.out.println(strline.toString().replaceAll("\"", "'"));
        } catch (Exception e) {

        }

        return strline.toString();
    }
}
