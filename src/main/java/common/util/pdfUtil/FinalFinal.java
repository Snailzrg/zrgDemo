package common.util.pdfUtil;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.codec.Base64;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author snail
 * 2020/8/4 23:44
 */
public class FinalFinal {
    private static final long serialVersionUID = -6706615506446278193L;
    private static final int QR_WIDTH = 300;
    private static final int QR_HEIGHT = 300;
    private static final int MAGIC_NUM1 = 0xFF000000;
    private static final int MAGIC_NUM2 = 0xFFFFFFFF;
    private static float psconst = 2.833F;

    public static void main(String[] args) throws IOException {
        final Map<String, String> map = new HashMap<String, String>();
        map.put("所屬部門", "部門");
        final File qrFile = doQrCode("txt");
        final StringBuilder htmlContent = new StringBuilder();
        final String htmlStr = initHtmlStr(map, qrFile.getName(), PageSize.A4.getHeight(), PageSize.A4.getWidth());
        File htmlFile = null;
        File pdfFile = null;
        InputStream inputStream = null;
        OutputStream pdfOut = null;
        final byte[] outByte = null;
        try {
            htmlFile = File.createTempFile("pdfHtml", ".html");
            if (htmlFile.exists()) {
                writeStringToFile(htmlFile, htmlStr, "UTF-8");
            }
            pdfFile = File.createTempFile("mdPdf", ".pdf");

            if (pdfFile.exists()) {
                homotopy(htmlFile.getAbsolutePath(), pdfFile.getAbsolutePath(),htmlStr,qrFile.getAbsolutePath());
                inputStream = new FileInputStream(pdfFile);
            }
            pdfOut = new FileOutputStream(new File("C:\\Users\\snail\\Desktop\\TY.pdf"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }

            if (pdfOut != null) {
                pdfOut.close();
            }
        }

    }

    /**
     * 编解码base64并生成itext图像
     */
    protected FSImage buildImage(String srcAttr, UserAgentCallback uac) throws IOException,
            BadElementException {
        FSImage fiImg=null;
        if (srcAttr.toLowerCase().startsWith("data:image/")) {
            String base64Code= srcAttr.substring(srcAttr.indexOf("base64,") + "base64,".length(),
                    srcAttr.length());
            // 解码
            byte[] decodedBytes = Base64.decode(base64Code);


            fiImg= new ITextFSImage(Image.getInstance(decodedBytes));
        } else {
            fiImg= uac.getImageResource(srcAttr).getImage();
        }
        return fiImg;
    }



    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box, UserAgentCallback uac,
                                                 int cssWidth, int cssHeight) {
        Element e = box.getElement();
        if (e == null) {
            return null;
        }
        String nodeName = e.getNodeName();
        // 找到img标签
        if (nodeName.equals("img")) {
            String attribute = e.getAttribute("src");
            FSImage fsImage;
            try {
                // 生成itext图像
                fsImage = buildImage(attribute, uac);
            } catch (BadElementException e1) {
                fsImage = null;
            } catch (IOException e1) {
                fsImage = null;
            }
            if (fsImage != null) {
                // 对图像进行缩放
                if (cssWidth != -1 || cssHeight != -1) {
                    fsImage.scale(cssWidth, cssHeight);
                }
                return new ITextImageElement(fsImage);
            }
        }
        return null;
    }


    /**
     * html
     *
     * @param map    参数
     * @param name   图片
     * @param weight 页面宽
     * @param height 页面高
     * @return html内容
     */
    private static String initHtmlStr(final Map<String, String> map, final String name, final double weight, final double height) {
        final StringBuilder sb = new StringBuilder();
//        sb.append("<html><head><style type='text/css'>");
//        sb.append("body {width: " + weight + "px;height:" + height + "px;border: 1px #D3D3D3 solid;border-radius: 5px;background: white;box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);}");
        sb.append("<html>\n" +
                "\t<head>\n" +
                "\t\t<style type='text/css'>\n" +
                "\t\t\t@page {\n" +
                "\t\t\t\tsize: 842px*595px\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.wapper {\n" +
                "\t\t\t\tmargin-left: 4.5rem;\n" +
                "\t\t\t\tmargin-top: 3.0rem;\n" +
                "\t\t\t\tmargin-right: 1.5rem;\n" +
                "\t\t\t\tmargin-bottom: 3.0rem;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\ttable td {\n" +
                "\t\t\t\ttext-align: center;\n" +
                "\t\t\t\twidth: 15%;\n" +
                "\t\t\t\theight: 50px;\n" +
                "\t\t\t\tfont-size: 14;\n" +
                "\t\t\t}\n" +
                "\t\t</style>\n" +
                "\t</head>\n" +
                "\t<body style='font-family:SimSun;'>\n" +
                "\t\t<div class=\"wapper\">\n" +
                "\t\t\t<!-- 标题 -->\n" +
                "\t\t\t<div class=\"div_head\" style=\"margin-top: 95px;\">\n" +
                "\t\t\t\t<div>\n" +
                "\t\t\t\t\t<p style=\"text-align: center; font-size: 20px; font-weight:bold\">国网内蒙古东部电力有限公司粘贴单</p>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div style=\"display: inline;  float: right;\">\n" +
                "\t\t\t\t\t<img src=\"./94510527.jpg\" style=\"width: 90px; width: 90px;  margin-top: -95px; \"></img>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<!-- 表头 -->\n" +
                "\t\t\t<div style=\"width:100%; left;\">\n" +
                "\t\t\t\t<div style=\"width: 33%; float: left;\">\n" +
                "\t\t\t\t\t<p style=\"text-align: left;\">单位:国网内蒙古东部电力有限公司</p>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div style=\"width: 33%; float: left;\">\n" +
                "\t\t\t\t\t<p style=\"text-align: center;\">2020年7月8日</p>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div style=\"width: 33%; float: left;\">\n" +
                "\t\t\t\t\t<p style=\"text-align: right; \">单位:元</p>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>\n" +
                "\n" +
                "\t\t\t<table border=\"1\" width=\"100%\" height=\"500px\" cellspacing=\"0\">\n" +
                "\t\t\t\t<!-- <span style=\"text-align:left;\">单位:</span> -->\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<th rowspan=\"9\" style=\"width: 70%;\"></th>\n" +
                "\t\t\t\t\t<td style=\"width: 15%; height: 50px;\">单据编号:</td>\n" +
                "\t\t\t\t\t<td style=\"width: 15%;\">2089</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>费用类形:</td>\n" +
                "\t\t\t\t\t<td>出厂</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>附件张数:</td>\n" +
                "\t\t\t\t\t<td>2</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>部门:</td>\n" +
                "\t\t\t\t\t<td>c测试部门卡卡的卡机款卡的卡卡</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>金额:</td>\n" +
                "\t\t\t\t\t<td>43,123,124</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>经办人:</td>\n" +
                "\t\t\t\t\t<td>昂俊超</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>证明人:</td>\n" +
                "\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td> </td>\n" +
                "\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td> </td>\n" +
                "\t\t\t\t\t<td> </td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</table>\n" +
                "\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>\n");

        return sb.toString();
    }

    /**
     * qrcode.
     *
     * @param content content
     * @return file
     */
    private static File doQrCode(final String content) {
        File img = null;
        BufferedImage bufferImage = null;
        try {
            img = File.createTempFile("qrCode", ".jpg");
            bufferImage = createImg(content);
            ImageIO.write(bufferImage, "JPG", img);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return img;
    }

    /**
     * createImg .
     *
     * @param content content
     * @return image
     */
    @SuppressWarnings("unchecked")
    private static BufferedImage createImg(final String content) {
        final Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = null;
        BufferedImage image = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT,
                    hints);
            final int width = bitMatrix.getWidth();
            final int height = bitMatrix.getHeight();
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? MAGIC_NUM1 : MAGIC_NUM2);
                }
            }
        } catch (WriterException e) {

            e.printStackTrace();
        }
        return image;
    }

    private static void writeStringToFile(final File file, final String data, final String encoding) throws IOException {
        try (OutputStream out = new FileOutputStream(file)) {
            if (encoding == null || "".equalsIgnoreCase(encoding)) {
                out.write(data.getBytes());
            } else {
                out.write(data.getBytes(encoding));
            }
        }
    }

    // 設置A4
    private static void homotopy(final String localPath, final String targetPath ,String html,String inamgpath) throws IOException, com.lowagie.text.DocumentException, DocumentException {
        String pdfJson = (new StringBuilder("{\"pageSize\":\"A4\",\"footer\":\"")).append("第{1}页,共{1}页").append("\",").append("\"landscape\":\"false\",").append("\"margin\":{\"top\":20,\"left\":20,\"right\":20,\"bottom\":20}").append("}").toString();

        Document doc ;//= new Document();
        //横向
        Rectangle pageSize = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
        pageSize.rotate();
        doc = new Document(pageSize, Float.parseFloat("4.5"), Float.parseFloat("1.5"),
                Float.parseFloat("3.0"), Float.parseFloat("3.0"));

        final FileOutputStream outp = new FileOutputStream(targetPath);
        final PdfWriter writer = PdfWriter.getInstance(doc, outp);
        doc.open();



        Image img = Image.getInstance(inamgpath);
        if (img != null)
        {
            img.scaleToFit(540f, 300f);
            doc.add((com.itextpdf.text.Element) img);
        }
        XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
        helper.parseXHtml(writer, doc, new FileInputStream(localPath), StandardCharsets.UTF_8);
        doc.close();
//
//        try {
//            final FileOutputStream outp = new FileOutputStream(targetPath);
//            final PdfWriter writer = PdfWriter.getInstance(doc, outp);
//            doc.open();
//            XMLWorkerHelper.getInstance().parseXHtml(writer, doc, new FileInputStream(localPath),
//                    StandardCharsets.UTF_8);
////            doc.setPageSize(pageSize);
//            doc.close();
//        } catch (DocumentException | IOException e1) {
//            e1.printStackTrace();
//        }

    }



    private void cp(){
//        TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
//        tagProcessorFactory.removeProcessor("img");
//        tagProcessorFactory.addProcessor(new ImageTagProcessor(), new String[] {
//                "img"
//        });
//        com.itextpdf.tool.xml.css.CssFile defaultCss = XMLWorkerHelper.getCSS(is);
//        CssFilesImpl cssFiles = new CssFilesImpl();
//        cssFiles.add(defaultCss);
//        StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
//        HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(myFontProvider));
//        hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
//        HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
//        com.itextpdf.tool.xml.Pipeline pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
//        XMLWorker worker = new XMLWorker(pipeline, true);
//        Charset cset = Charset.forName("UTF-8");
//        XMLParser xmlParser = new XMLParser(true, worker, cset);
//        xmlParser.parse(html, cset);
//        xmlParser.flush();
    }


    // 設置A4---
    private static void homotopyCopy(final String localPath, final String targetPath) throws IOException {
        String pdfJson = (new StringBuilder("{\"pageSize\":\"A4\",\"footer\":\"")).append("第{1}页,共{1}页").append("\",").append("\"landscape\":\"false\",").append("\"margin\":{\"top\":20,\"left\":20,\"right\":20,\"bottom\":20}").append("}").toString();

//        htmlToPdf(localPath, cssFile.getAbsolutePath(), targetPath, pdfJson);
        Document doc ;//= new Document();
        //横向
        Rectangle pageSize = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
        pageSize.rotate();
// doc.setPageSize(pageSize);
//        Document doc;
//        Rectangle ps = PageSize.A4;
//        doc = new Document(ps, Float.parseFloat("4.5"), Float.parseFloat("1.5"),
//                Float.parseFloat("3.0"), Float.parseFloat("3.0"));

        float mt = 20F;
        float ml = 20F;
        float mr = 20F;
        float mb = 20F;
        doc = new Document(pageSize, ml * psconst, mr * psconst, mt * psconst, mb * psconst);
        try {
            final FileOutputStream outp = new FileOutputStream(targetPath);
            final PdfWriter writer = PdfWriter.getInstance(doc, outp);
            doc.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, doc, new FileInputStream(localPath),
                    StandardCharsets.UTF_8);
            doc.setPageSize(pageSize);
            doc.close();
        } catch (DocumentException | IOException e1) {
            e1.printStackTrace();
        }

    }

}
