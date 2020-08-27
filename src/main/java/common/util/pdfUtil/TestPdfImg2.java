package common.util.pdfUtil;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFilesImpl;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.lowagie.text.BadElementException;
import sun.misc.BASE64Encoder;
import com.itextpdf.text.pdf.codec.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

//import com.itextpdf.text.pdf.codec.Base64;

/**
 *
 * 问题： table里面再嵌套table，设置宽度，就会无效
 *        margin 无效
 *        图片 需替换换 itextpdf 自己的Image
 *        postion 无效
 *
 * 带图片的版本
 * @author snail
 * 2020/8/4 23:44
 */
public class TestPdfImg2 {
    private static final long serialVersionUID = -6706615506446278193L;
    private static final int QR_WIDTH = 300;
    private static final int QR_HEIGHT = 300;
    private static final int MAGIC_NUM1 = 0xFF000000;
    private static final int MAGIC_NUM2 = 0xFFFFFFFF;
    private static float PS_CONST = 2.833F;

    public static void main(String[] args) throws IOException, DocumentException, BadElementException {
        final String qrStr = doQrCode("图片12345");
        final StringBuilder htmlContent = new StringBuilder();
        String htmlStr = initHtmlStr2(null, qrStr);

//        htmlStr =  initHtmlStr(null, qrStr,1,1);

        File htmlFile = null;
        File tempPdfFile = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String filepdf = "";
        try {
            htmlFile = File.createTempFile("pdfHtml", ".html");
            if (htmlFile.exists()) {
                writeStringToFile(htmlFile, htmlStr, "UTF-8");
            }
            String pdfTempPAth = htmlFile.getAbsolutePath().replaceAll("html","pdf");
            htmlToPdfWithoutImg(htmlFile.getAbsolutePath(), pdfTempPAth);
            tempPdfFile = new File(pdfTempPAth);
            if (tempPdfFile.exists()){
                //追加图片到pdf 由html转换pdf时不支持margin,以及绝对定位等css样式。无法实现生成pdf二维码底边和标题水平
                PdfReader reader = new PdfReader(pdfTempPAth);
                filepdf = "C:\\Users\\snail\\Desktop\\"+tempPdfFile.getName()+"AES862.pdf";
                outputStream = new FileOutputStream(filepdf);

                PdfStamper stamp = new PdfStamper(reader,  outputStream);
                PdfContentByte overContent = stamp.getOverContent(1);
                com.itextpdf.text.Image image = null;
                image = convertBase64ToImg(qrStr);
                //图片缩放比例
                image.scalePercent(23);
                //左边距、底边距
                image.setAbsolutePosition(750,485);
                overContent.addImage(image);
                overContent.stroke();
                stamp.close();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (htmlFile!=null && htmlFile.exists()){
//                htmlFile.delete();
                System.out.println(htmlFile.getAbsolutePath());
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (tempPdfFile!=null && tempPdfFile.exists()){
                tempPdfFile.delete();
            }
        }
    }

    private static Image convertBase64ToImg(String content) {
        Image  img ;
        final String base64Data = content.substring(content.indexOf(",") + 1);
        try {
            img = Image.getInstance(Base64.decode(base64Data));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
     return img;
    }


    public static Map<String, Object> getPdfMsg(String filePath) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            // 获取PDF共有几页
            PdfReader pdfReader = new PdfReader(new FileInputStream(filePath));
            int pages = pdfReader.getNumberOfPages();
            // System.err.println(pages);
            map.put("pageSize", pages);
            // 获取PDF 的宽高
            PdfReader pdfreader = new PdfReader(filePath);
            Document document = new Document(pdfreader.getPageSize(pages));
            float widths = document.getPageSize().getWidth();
            // 获取页面高度
            float heights = document.getPageSize().getHeight();
            // System.out.println("widths = " + widths + ", heights = " + heights);
            map.put("width", widths);
            map.put("height", heights);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * initPdf.
     *
     * @param htmlPath
     *            htmlPath
     * @param pdfPath
     *            pdfPath
     */
    private static void htmlToPdfWithoutImg(final String htmlPath, final String pdfPath) {
        try {
            final Rectangle pageSize = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
            pageSize.rotate();
            final float mt = 28F;
            final float ml = 40F;
            final float mr = 12F;
            final float mb = 20F;
            final Document doc = new Document(pageSize, ml * PS_CONST, mr * PS_CONST, mt * PS_CONST, mb * PS_CONST);
            final FileOutputStream out = new FileOutputStream(pdfPath);
            final PdfWriter writer = PdfWriter.getInstance(doc, out);
            doc.open();
            final XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
            helper.parseXHtml(writer, doc, new FileInputStream(htmlPath), StandardCharsets.UTF_8);
            doc.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String initHtmlStr2(final Map<String, String> map, final String qrStr) {
            final StringBuilder sb = new StringBuilder();
        sb.append("<html><head><style type='text/css'>@page {size: 842px*595px}table td ");
        sb.append("{text-align: center;width: 15%;height: 58px;font-size: 14px;}</style></head>");
        sb.append("<body style='font-family:SimSun;'><div style=\"margin-top:95px;\">");
        sb.append("<div style=\"display: inline;  float:right;\">");

        // 替换html中图片（虽然现在用不到）
        sb.append("<img src=\"" + qrStr + "\" style=\"width:100px; width:100px; margin-top:-80px;\"></img></div><div>");
        sb.append("<p style=\"text-align: center; font-size:20px; font-weight:bold\">国网内蒙古东部电力有限公司粘贴单</p></div></div>");

        // table
        sb.append("<table border=\"0.5px\" width=\"100%\" height=\"660px\" cellspacing=\"0\">");
        sb.append("<div  style=\"width:100%;\"><div style=\"width:45%; float:left; height:20px;\">");
        sb.append("<p style=\"text-align:left;\">单位:国网内蒙古东部电力有限公司</p></div>");

        sb.append("<div style=\"width:33.3%; float:left;  height:20px;\"><p style=\"text-align:center;\">" + " " + "</p>");
        sb.append("</div><div style=\"float:right;  height:20px;\"><p style=\"text-align:right; \">单位:元</p></div></div>\n");

        // 表格 单据编号
        sb.append("<tr><th rowspan=\"9\" style=\"width:70%;\"></th>");
        sb.append("<td style=\"width:15%; height:58px;\">单据编号:</td><td style=\"width:15%;\"></td></tr>");

        //
        sb.append("<tr><td>费用类形:</td><td>" + " "  + "</td></tr><tr><td>附件张数:</td><td>" + "X "
                + "</td></tr><tr><td>部门:</td><td>" + " " + "</td></tr>");
        sb.append("<tr><td>金额:</td><td>" + " "  + "</td></tr><tr><td>经办人:</td><td>" + " "
                + "</td></tr><tr><td>证明人:</td><td></td></tr>");
        //
        sb.append("<tr><td></td><td></td></tr><tr><td> </td><td> </td></tr></table></body></html>");
            System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * html.
     * @param map  内容
     * @param qrStr qrCode
     * @param weight 宽
     * @param height 高
     * @return str
     */
    private static String initHtmlStr(final Map<String, String> map, final String qrStr, final double weight, final double height) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<html>\n" +
                "\t<head>\n" +
                "\t\t<style type='text/css'>\n" +
                "\t\t\t@page {\n" +
                "\t\t\t\tsize: 842px*595px\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\ttable td {\n" +
                "\t\t\t\ttext-align: center;\n" +
                "\t\t\t\twidth: 15%;\n" +
                "\t\t\t\theight: 56px;\n" +
                "\t\t\t\tfont-size: 14px;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\tspan {\n" +
                "\n" +
                "\t\t\t\tbackground-color: #ccc;\n" +
                "\n" +
                "\t\t\t\tdisplay: -moz-inline-box;\n" +
                "\n" +
                "\t\t\t\tdisplay: inline-block;\n" +
                "\t\t\t}\n" +
                "\t\t</style>\n" +
                "\t</head>\n" +
                "\t<body style='font-family:SimSun;'>\n" +
                "\t\t<div style=\"margin-top:95px;\">\n" +
                "\t\t\t<div style=\"display: inline;  float:right;\"><img src=\"AAA.JPG\" style=\"width:100px; width:100px; margin-top:-80px;\"></img></div>\n" +
                "\t\t\t<div>\n" +
                "\t\t\t\t<p style=\"text-align: center; font-size:20px; font-weight:bold\">国网内蒙古东部电力有限公司粘贴单</p>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t\t<div style=\"width:100%;\">\n" +
                "\t\t\t<!-- <div style=\"width:45%; float:left;\">\n" +
                "\t\t\t\t单位:国网内蒙古东部电力有限公司</p>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<div style=\"width:33.3%; float:left;\">\n" +
                "\t\t\t\t2\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<div style=\"float:right;\">\n" +
                "\t\t\t\t单位:元\n" +
                "\t\t\t</div> -->\n" +
                "\t\t\t<span style=\"float:left; \twidth: 47%;\">\n" +
                "\t\t\t\t<p>\n" +
                "\t\t\t\t\t单位:国网内蒙古东部电力有限公司\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t</span>\n" +
                "\t\t\t<span style=\"float:left; width:33.3%;\">\n" +
                "\t\t\t\t<p>\n" +
                "\t\t\t\t\t2020-09-08\n" +
                "\t\t\t\t</p>\n" +
                "\t\t\t</span>\n" +
                "\t\t\t<span style=\"float:right;\">\n" +
                "\t\t\t<p style=\"text-align: right;\">\n" +
                "\t\t\t\t单位:元\n" +
                "\t\t\t</p>\n" +
                "\t\t\t</span>\n" +
                "\t\t</div>\n" +
                "\n" +
                "\t\t<table border=\"1px\" width=\"100%\" height=\"650px\" cellspacing=\"0\">\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<th rowspan=\"9\" style=\"width:70%;\"></th>\n" +
                "\t\t\t\t<td style=\"width:15%; height:56px;\">单据编号:</td>\n" +
                "\t\t\t\t<td style=\"width:15%;\"></td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>费用类形:</td>\n" +
                "\t\t\t\t<td>2</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>附件张数:</td>\n" +
                "\t\t\t\t<td>3</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>部门:</td>\n" +
                "\t\t\t\t<td>4</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>金额:</td>\n" +
                "\t\t\t\t<td>2</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>经办人:</td>\n" +
                "\t\t\t\t<td>2</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>证明人:</td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td> </td>\n" +
                "\t\t\t\t<td> </td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</table>\n" +
                "\t</body>\n" +
                "</html>\n");
//   "+qrStr+"
        String ss = sb.toString();
        return sb.toString();
    }

    /**
     * qrcode.
     *
     * @param content content
     * @return file
     */
    private static String doQrCode(final String content) {
        String  encodeStr = "";
        BufferedImage bufferImage = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] data = null;
        try {
            bufferImage = createImg(content);
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferImage, "jpg", byteArrayOutputStream);
            inputStream  = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            data = new byte[inputStream.available()];
            inputStream.read(data);
            BASE64Encoder encoder = new BASE64Encoder();
            //前端解析需要带上前缀
            encodeStr = "data:image/png;base64,"+ encoder.encode(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream!=null){
                try{
                   inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (byteArrayOutputStream != null){
                try{
                  byteArrayOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
    }
        return encodeStr.trim();
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
    private static void htmlToPdf(final String htmlPath, final String pdfPath ,String qrStr) throws IOException, DocumentException {
        Document doc;
        //横向
        Rectangle pageSize = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
        pageSize.rotate();
        float mt = 30F;
        float ml = 43F;
        float mr = 15F;
        float mb = 25F;
        doc = new Document(pageSize, ml * PS_CONST, mr * PS_CONST, mt * PS_CONST, mb * PS_CONST);

        final FileOutputStream outp = new FileOutputStream(pdfPath);
        final PdfWriter writer = PdfWriter.getInstance(doc, outp);
        doc.open();
        XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
//        helper.parseXHtml(writer, doc, new FileInputStream(localPath), XMLWorkerHelper.class.getResourceAsStream("/default.css"), StandardCharsets.UTF_8);
        InputStream in = new FileInputStream(htmlPath);
        InputStream inCssFile =  XMLWorkerHelper.class.getResourceAsStream("/default.css");
        CssFilesImpl cssFiles = new CssFilesImpl();
        if (inCssFile != null) {
            cssFiles.add(XMLWorkerHelper.getCSS(inCssFile));
        } else {
//            cssFiles.add(XMLWorkerHelper.getDefaultCSS());
        }
        StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
        HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(new XMLWorkerFontProvider()));
        TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
//        tagProcessorFactory.removeProcessor("img");
//        tagProcessorFactory.addProcessor(new ImageTagProcessor() ,new String[] {
//                "img"});
//        tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
//        tagProcessorFactory.addProcessor(new ZrgImageTagProcessor(), HTML.Tag.IMG);
        hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
        HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(doc, writer));
        Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
        XMLWorker worker = new XMLWorker(pipeline, true);
        XMLParser p = new XMLParser(true, worker, StandardCharsets.UTF_8);
        p.parse(in, StandardCharsets.UTF_8);

        doc.close();
       }



    public static String getXmlString() {
        String xmlString="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\"/>\n" +
                "    <title>Hello World</title>\n" +
                "\t<style>\n" +
                "\t  table.table-separate th{\n" +
                "    font-weight:bold;\n" +
                "    font-size:14px;\n" +
                "    border-top:1px solid #F3EDE9 !important;\n" +
                "  }\n" +
                "  table.table-separate td{\n" +
                "    padding: 13px 0;\n" +
                "    font-weight:100;\n" +
                "  }\n" +
                "  .table-separate td.tit{\n" +
                "    background-color: #f4f9fe;\n" +
                "    font-weight:normal;\n" +
                "    padding:22px 0;\n" +
                "    width:15%;\n" +
                "  }\n" +
                "  .table-separate td.cont{\n" +
                "    text-align: left;\n" +
                "    padding:16px 22px;\n" +
                "    width:85%;\n" +
                "    line-height:175%;\n" +
                "  }\n" +
                "  .table-separate.no-border th{\n" +
                "    border:none;\n" +
                "    text-align: left;\n" +
                "  }\n" +
                "  .table-separate.no-border td{\n" +
                "    text-align: left;\n" +
                "    border:none;\n" +
                "  }\n" +
                "\t@page {\n" +
                "\tsize:210mm 297mm;//纸张大小A4\n" +
                "\tmargin: 0.25in;\n" +
                "\t-fs-flow-bottom: \"footer\";\n" +
                "\t-fs-flow-left: \"left\";\n" +
                "\t-fs-flow-right: \"right\";\n" +
                "\tborder: thin solid black;\n" +
                "\tpadding: 1em;\n" +
                "\t}\n" +
                "\t#footer {\n" +
                "\tfont-size: 90%; font-style: italic;\n" +
                "\tposition: absolute; top: 0; left: 0;\n" +
                "\t-fs-move-to-flow: \"footer\";\n" +
                "\t}\n" +
                "\t#pagenumber:before {\n" +
                "\tcontent: counter(page);\n" +
                "\t}\n" +
                "\t#pagecount:before {content: counter(pages);\n" +
                "\t}\n" +
                "\ttable {\n" +
                "\t\t\tborder-collapse: collapse;\n" +
                "\t\t\ttable-layout: fixed;\n" +
                "\t\t\tword-break:break-all;\n" +
                "\t\t\tfont-size: 10px;\n" +
                "\t\t\twidth: 100%;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t}\n" +
                "\ttd {\n" +
                "\t\tword-break:break-all;\n" +
                "\t\tword-wrap : break-word;\n" +
                "\t}\n" +
                "\t</style>\n" +
                "\t</head>\n" +
                "<body style = \"font-family: SimSun;\">\n" +
                "<div id=\"footer\" style=\"\">  Page <span id=\"pagenumber\"/> of <span id=\"pagecount\"/> </div>\n" +
                "<div id=\"main\">\n" +
                "    <div style=\"max-width:600px;margin:0 auto;padding:10px;\">\n" +
                "        <div style=\"text-align: center; padding: 5mm 0;\">\n" +
                "            <div style=\"font-weight: bold; font-size: 30px;\"> HI Fudi&amp;More</div>\n" +
                "            <div> THANK YOU FOR SHOPPING WITH Fudi&amp;More!</div>\n" +
                "        </div>\n" +
                "        <div style=\"border: 1px solid black; background-color: #f8f8f8; padding: 4mm;\">\n" +
                "            <div style=\"font-size: 17px; font-weight: bold; border-bottom: 1px solid black; padding-bottom: 5mm;\"> ORDER DETAILS</div>\n" +
                "            <div style=\"padding-top: 10px;\">\n" +
                "                <div><strong>Order:&nbsp;</strong>D-8C2Y Placed on 29/09/2019 10:04</div>\n" +
                "                <div><strong>Carrier:&nbsp;</strong>Delivery</div>\n" +
                "                <div><strong>Payment:&nbsp;</strong>Cash Payment</div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div style=\"margin-top: 4mm;\">\n" +
                "            <table class=\"table-separate\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:600px;margin:0 auto;padding:10px;\">\n" +
                "                <thead>\n" +
                "                <tr style=\"text-align: center; height: 40px;\">\n" +
                "                    <th style=\"width: 90px; background-color: #f8f8f8; border-top: 1px solid black; border-left: 1px solid black; border-right: 1px solid black;\">\n" +
                "                        Reference\n" +
                "                    </th>\n" +
                "                    <th colspan=\"2\" style=\"background-color: #f8f8f8; border-top: 1px solid black; border-right: 1px solid black;\">Product</th>\n" +
                "                    <th style=\"width: 110px; background-color: #f8f8f8; border-top: 1px solid black; border-right: 1px solid black;\">Unit price</th>\n" +
                "                    <th style=\"width: 80px; background-color: #f8f8f8; border-top: 1px solid black; border-right: 1px solid black;\">Quantity</th>\n" +
                "                    <th style=\"width: 90px; background-color: #f8f8f8; border-top: 1px solid black; border-right: 1px solid black;\">Total price</th>\n" +
                "                </tr>\n" +
                "                </thead>\n" +
                "                <tbody>\n" +
                "                <tr style=\"text-align: center; \">\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black;\">\n" +
                "                        Main\n" +
                "                    </td>\n" +
                "                    <td colspan=\"2\"\n" +
                "                        style=\"border-top: 1px solid black; border-bottom:1px solid black; border-right: 1px solid black; text-align: left; padding: 010px;\">\n" +
                "                        SweetSour Chicken\n" +
                "                    </td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 7.00</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">1</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 7.00</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; \">\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black;\">\n" +
                "                        Main\n" +
                "                    </td>\n" +
                "                    <td colspan=\"2\"\n" +
                "                        style=\"border-top: 1px solid black; border-bottom:1px solid black; border-right: 1px solid black; text-align: left; padding: 010px;\">\n" +
                "                        Black Bean Stir Fry\n" +
                "                    </td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 9.00</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">1</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 9.00</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; \">\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black;\">\n" +
                "                        Pizzas\n" +
                "                    </td>\n" +
                "                    <td colspan=\"2\"\n" +
                "                        style=\"border-top: 1px solid black; border-bottom:1px solid black; border-right: 1px solid black; text-align: left; padding: 010px;\">\n" +
                "                        Test Design Your Own 8\" Pizza\n" +
                "                    </td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 6.00</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">1</td>\n" +
                "                    <td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro; 6.00</td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "                <tfoot>\n" +
                "                <tr style=\"text-align: center; height: 8mm;\">\n" +
                "                    <td colspan=\"5\"\n" +
                "                        style=\"text-align: right; width: 90px; background-color: #f8f8f8; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 0 10px;\">\n" +
                "                        Item:\n" +
                "                    </td>\n" +
                "                    <td style=\"background-color: #f8f8f8; border-bottom: 1px solid black; border-right: 1px solid black;\">3</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; height: 8mm;\">\n" +
                "                    <td colspan=\"5\"\n" +
                "                        style=\"text-align: right; width: 90px; background-color: #f8f8f8; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 0 10px;\">\n" +
                "                        Subtotal:\n" +
                "                    </td>\n" +
                "                    <td style=\"background-color: #f8f8f8; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro;24.00</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; height: 8mm;\">\n" +
                "                    <td colspan=\"5\"\n" +
                "                        style=\"text-align: right; width: 90px; background-color: #f8f8f8; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 0 10px;\">\n" +
                "                        Deliver Fee:\n" +
                "                    </td>\n" +
                "                    <td style=\"background-color: #f8f8f8; border-bottom: 1px solid black; border-right: 1px solid black;\">+&euro;2.00</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; height: 8mm;\">\n" +
                "                    <td colspan=\"5\"\n" +
                "                        style=\"text-align: right; width: 90px; background-color: #f8f8f8; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 0 10px;\">\n" +
                "                        Discount:\n" +
                "                    </td>\n" +
                "                    <td style=\"background-color: #f8f8f8; border-bottom: 1px solid black; border-right: 1px solid black;\">-&euro;0.00</td>\n" +
                "                </tr>\n" +
                "                <tr style=\"text-align: center; height: 8mm;\">\n" +
                "                    <td colspan=\"5\"\n" +
                "                        style=\"text-align: right; width: 90px; background-color: #f8f8f8; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 0 10px;\">\n" +
                "                        Total:\n" +
                "                    </td>\n" +
                "                    <td style=\"background-color: #f8f8f8; border-bottom: 1px solid black; border-right: 1px solid black;\">&euro;24.00</td>\n" +
                "                </tr>\n" +
                "                </tfoot>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "        <div>\n" +
                "            <div style=\"border: 1px solid black; background-color: #f8f8f8; padding:5mm; margin-top: 5mm;\">\n" +
                "                <div style=\"font-size: 17px; font-weight: bold; border-bottom: 1px solid black; padding-bottom: 15px;\"> DELIVERY ADDRESS</div>\n" +
                "                <div style=\"padding-top: 10px;\">\n" +
                "                    <div><strong>guan</strong> &#9742; <strong>13656690321</strong></div>\n" +
                "                    <div> 1024/ Edenhall,ModelFarmRd,Cork,爱尔兰,A 2048</div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div style=\"font-size: 13px;\"><p>You can review your order and download your invoice from the \"<a target=\"_blank\"\n" +
                "                                                                                                          href=\"http://www.fudiandmore.ie/#/FudiIndex/Order1\">Order\n" +
                "            history</a>\"section of your customer account by clicking \"<a target=\"_blank\" href=\"http://www.fudiandmore.ie/#/FudiIndex/Personalcenter1\">My\n" +
                "            account</a>\" on ourshop.</p></div>\n" +
                "        <hr style=\"border-width: 5px;\"/>\n" +
                "        <div> Fudi,More powered by <a target=\"_blank\" href=\"http://www.fudiandmore.ie\">A2BLiving</a></div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        StringBuffer stringBuffer=new StringBuffer();

        return xmlString;
    }
}