//package util.pdfUtil;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.tool.xml.XMLWorker;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
//import com.itextpdf.tool.xml.css.CssFilesImpl;
//import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
//import com.itextpdf.tool.xml.html.CssAppliersImpl;
//import com.itextpdf.tool.xml.html.TagProcessorFactory;
//import com.itextpdf.tool.xml.html.Tags;
//import com.itextpdf.tool.xml.parser.XMLParser;
//import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
//import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
//import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
//import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
//import org.apache.poi.ss.usermodel.Footer;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * @author snail
// * 2020/8/4 23:44
// */
//public class TestPdf2 {
//    private static final long serialVersionUID = -6706615506446278193L;
//    private static final int QR_WIDTH = 300;
//    private static final int QR_HEIGHT = 300;
//    private static final int MAGIC_NUM1 = 0xFF000000;
//    private static final int MAGIC_NUM2 = 0xFFFFFFFF;
//    private static float psconst = 2.833F;
//
//    public static void main(String[] args) throws IOException {
//        final Map<String, String> map = new HashMap<String, String>();
//        map.put("所屬部門", "部門");
//        final File qrFile = doQrCode("txt");
//        final StringBuilder htmlContent = new StringBuilder();
//        final String htmlStr = initHtmlStr(map, qrFile.getName(), PageSize.A4.getHeight(), PageSize.A4.getWidth());
//        //html 设置页面宽和高 依据 PageSize.A4.getHeight(), PageSize.A4.getHeight()
//        File htmlFile = null;
//        File pdfFile = null;
//        InputStream inputStream = null;
//        OutputStream pdfOut = null;
//        final byte[] outByte = null;
//        try {
//            htmlFile = File.createTempFile("pdfHtml", ".html");
//            if (htmlFile.exists()) {
//                writeStringToFile(htmlFile, htmlStr, "UTF-8");
//            }
//            pdfFile = File.createTempFile("mdPdf", ".pdf");
//
//            if (pdfFile.exists()) {
//                homotopy(htmlFile.getAbsolutePath(), pdfFile.getAbsolutePath());
//                inputStream = new FileInputStream(pdfFile);
//            }
//            pdfOut = new FileOutputStream(new File("/test.pdf"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                inputStream.close();
//            }
//
//            if (pdfOut != null) {
//                pdfOut.close();
//            }
//        }
//
//    }
//
//    /**
//     * html
//     *
//     * @param map    参数
//     * @param name   图片
//     * @param weight 页面宽
//     * @param height 页面高
//     * @return html内容
//     */
//    private static String initHtmlStr(final Map<String, String> map, final String name, final double weight, final double height) {
//        final StringBuilder sb = new StringBuilder();
//        sb.append("<html><head><style type='text/css'>");
//        sb.append("body {width: " + weight + "px;height:" + height + "px;border: 1px #D3D3D3 solid;border-radius: 5px;background: white;box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);}");
//        sb.append("\ttable,\n" +
//                "\t\t\ttable tr td {\n" +
//                "\t\t\t\tborder: 1px dashed #000;\n" +
//                "\t\t\t\tborder-collapse: collapse\n" +
//                "\t\t\t}\n" +
//                "\t\t\t.title_td {\n" +
//                "\t\t\t\twidth: 95px;\n" +
//                "\t\t\t}\n" +
//                "\t\t</style>\n" +
//                "\t</head>\n" +
//                "\t<body style='font-family:SimSun;'>\n" +
//                "\n" +
//                "\t\t<div class=\"head\">\n" +
//                "\t\t\t<div style=\"font-size: 20px; color:#000000;font-weight:bold;text-align:center\">国网内蒙古东部电力有限公司粘贴单</div>\n" +
//                "\t\t\t<img src=\"94510527.jpg\" style=\"width: 3cm; width: 3cm;\" />\n" +
//                "\t\t</div>\n" +
//                "\n" +
//                "\t\t<table width='900px'>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td colspan='6' style='font-size:20px;color:#000000;font-weight:bold;text-align:center;'>厦门航空有限公司本部</td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td colspan='6' style='font-size:17px;color:#000000;font-weight:bold;text-align:center'>通用报销单（标准）</td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td colspan='6' style='font-size:14px;color:#000000;text-align:center'>编号：1870</td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td colspan='6'></td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td' style='font-size:15px;font-weight:800;background:#DEDDDC'>业务信息</td>\n" +
//                "\t\t\t\t<td colspan='5'></td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td'>经办部门</td>\n" +
//                "\t\t\t\t<td>厦航本部_计划财务部</td>\n" +
//                "\t\t\t\t<td class='title_td'>币种</td>\n" +
//                "\t\t\t\t<td>RMB</td>\n" +
//                "\t\t\t\t<td class='title_td'>合同编号</td>\n" +
//                "\t\t\t\t<td>MFMMCG160625</td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td'>申请总额</td>\n" +
//                "\t\t\t\t<td>999</td>\n" +
//                "\t\t\t\t<td class='title_td'>扣回预付款</td>\n" +
//                "\t\t\t\t<td>0</td>\n" +
//                "\t\t\t\t<td class='title_td'>其他扣款/冲销借款</td>\n" +
//                "\t\t\t\t<td>0</td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td'>预留质保金</td>\n" +
//                "\t\t\t\t<td>0</td>\n" +
//                "\t\t\t\t<td class='title_td'>附件张数(A4纸)</td>\n" +
//                "\t\t\t\t<td>1</td>\n" +
//                "\t\t\t\t<td class='title_td'>专票张数</td>\n" +
//                "\t\t\t\t<td>1</td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td' style='font-size:15px;font-weight:800;background:#DEDDDC'>预算信息</td>\n" +
//                "\t\t\t\t<td colspan='5'></td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td'>申请理由</td>\n" +
//                "\t\t\t\t<td colspan='5'>11</td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td'>业务类型</td>\n" +
//                "\t\t\t\t<td>办公用纸采购支出</td>\n" +
//                "\t\t\t\t<td class='title_td'>预算号</td>\n" +
//                "\t\t\t\t<td>办公用纸采购支出20171020</td>\n" +
//                "\t\t\t\t<td class='title_td'>申请金额</td>\n" +
//                "\t\t\t\t<td></td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td' style='font-size:15px;font-weight:800;background:#DEDDDC'>收款信息</td>\n" +
//                "\t\t\t\t<td colspan='5'></td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td'>支付方式</td>\n" +
//                "\t\t\t\t<td colspan='5'>转账－外单位</td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td'>收款人</td>\n" +
//                "\t\t\t\t<td>厦门市美利捷科技有限公司</td>\n" +
//                "\t\t\t\t<td class='title_td'>收款人帐号</td>\n" +
//                "\t\t\t\t<td>厦门市美利捷科技有限公司</td>\n" +
//                "\t\t\t\t<td class='title_td'>支付金额</td>\n" +
//                "\t\t\t\t<td>888</td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td' style='font-size:15px;font-weight:800;background:#DEDDDC'>审核记录</td>\n" +
//                "\t\t\t\t<td colspan='5'></td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td'>时间</td>\n" +
//                "\t\t\t\t<td class='title_td'>步骤名称</td>\n" +
//                "\t\t\t\t<td class='title_td'>操作者</td>\n" +
//                "\t\t\t\t<td class='title_td'>角色</td>\n" +
//                "\t\t\t\t<td colspan='2'>处理意见</td>\n" +
//                "\t\t\t</tr>\n" +
//                "\t\t\t<tr style='height:30px'>\n" +
//                "\t\t\t\t<td class='title_td'>2017-12-15 17:04:00</td>\n" +
//                "\t\t\t\t<td class='title_td'>新增</td>\n" +
//                "\t\t\t\t<td class='title_td'>卢翔云</td>\n" +
//                "\t\t\t\t<td class='title_td'>财务共享中心.总账管理岗</td>\n" +
//                "\t\t\t\t<td colspan='2'>同意</td>\n" +
//                "\t\t\t</tr>\n" +
//                "\n" +
//                "\t\t</table>\n" +
//                "\t</body>\n" +
//                "</html>\n");
//
//        return sb.toString();
//    }
//
//    /**
//     * qrcode.
//     *
//     * @param content content
//     * @return file
//     */
//    private static File doQrCode(final String content) {
//        File img = null;
//        BufferedImage bufferImage = null;
//        try {
//            img = File.createTempFile("qrCode", ".jpg");
//            bufferImage = createImg(content);
//            ImageIO.write(bufferImage, "JPG", img);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return img;
//    }
//
//    /**
//     * createImg .
//     *
//     * @param content content
//     * @return image
//     */
//    @SuppressWarnings("unchecked")
//    private static BufferedImage createImg(final String content) {
//        final Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//        BitMatrix bitMatrix = null;
//        BufferedImage image = null;
//        try {
//            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT,
//                    hints);
//            final int width = bitMatrix.getWidth();
//            final int height = bitMatrix.getHeight();
//            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            for (int x = 0; x < width; x++) {
//                for (int y = 0; y < height; y++) {
//                    image.setRGB(x, y, bitMatrix.get(x, y) ? MAGIC_NUM1 : MAGIC_NUM2);
//                }
//            }
//        } catch (WriterException e) {
//
//            e.printStackTrace();
//        }
//        return image;
//    }
//
//    private static void writeStringToFile(final File file, final String data, final String encoding) throws IOException {
//        try (OutputStream out = new FileOutputStream(file)) {
//            if (encoding == null || "".equalsIgnoreCase(encoding)) {
//                out.write(data.getBytes());
//            } else {
//                out.write(data.getBytes(encoding));
//            }
//        }
//    }
//
//    // 設置A4
//    private static void homotopy(final String localPath, final String targetPath) throws IOException {
//        String pdfJson = (new StringBuilder("{\"pageSize\":\"A4\",\"footer\":\"")).append("第{1}页,共{1}页").append("\",").append("\"landscape\":\"false\",").append("\"margin\":{\"top\":20,\"left\":20,\"right\":20,\"bottom\":20}").append("}").toString();
//
//        String css = ".a{\n" +
//                "  /*第一个样式内容不加载*/\n" +
//                "}\n" +
//                "\n" +
//                ".border{\n" +
//                "   border-top:1px solid black;border-left:1px solid black;\n" +
//                "}\n" +
//                "\n" +
//                ".border td{\n" +
//                "   border-right:1px solid black;border-bottom:1px solid black;\n" +
//                "}\n" +
//                "\n" +
//                ".noborder{\n" +
//                "    border:0px solid black;border-collapse:collapse;border-spacing:0px;\n" +
//                "}\n" +
//                "\n" +
//                ".fontFamilyHT{\n" +
//                "    font-family:黑体;\n" +
//                "}\n" +
//                ".fontSize12{\n" +
//                "    font-size:12px;\n" +
//                "}\n" +
//                ".fontSize14{\n" +
//                "    font-size:14px;\n" +
//                "}\n" +
//                ".fontSize28{\n" +
//                "    font-size:28px;\n" +
//                "}\n" +
//                ".textAlignCenter{\n" +
//                "    text-align:center;\n" +
//                "}\n" +
//                ".textAlignRight{\n" +
//                "    text-align:right;\n" +
//                "}\n" +
//                ".verticalAlignTop{\n" +
//                "    vertical-align:top;\n" +
//                "}\n" +
//                ".verticalAlignCenter{\n" +
//                "    vertical-align:middle;\n" +
//                "}\n" +
//                ".verticalAlignBottom{\n" +
//                "    vertical-align:bottom;\n" +
//                "}\n" +
//                ".width160{\n" +
//                "    width:160px;\n" +
//                "}\n" +
//                "#width160{\n" +
//                "   width:160px; \n" +
//                "}\n" +
//                ".width60 {\n" +
//                "   width:60px;\n" +
//                "}";
//        File cssFile = File.createTempFile("pdf", ".css");
//        writeStringToFile(cssFile, css, "UTF-8");
//
//        htmlToPdf(localPath, cssFile.getAbsolutePath(), targetPath, pdfJson);
//
//        Document doc = new Document();
//        //横向
//        Rectangle pageSize = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
//        pageSize.rotate();
//// doc.setPageSize(pageSize);
////        Document doc;
////        Rectangle ps = PageSize.A4;
////        doc = new Document(ps, Float.parseFloat("4.5"), Float.parseFloat("1.5"),
////                Float.parseFloat("3.0"), Float.parseFloat("3.0"));
//
//        float mt = 20F;
//        float ml = 20F;
//        float mr = 20F;
//        float mb = 20F;
//        document = new Document(ps, ml * psconst, mr * psconst, mt * psconst, mb * psconst);
//
//        try {
//            final FileOutputStream outp = new FileOutputStream(targetPath);
//            final PdfWriter writer = PdfWriter.getInstance(doc, outp);
//            doc.open();
//            XMLWorkerHelper.getInstance().parseXHtml(writer, doc, new FileInputStream(localPath),
//                    StandardCharsets.UTF_8);
//            doc.setPageSize(pageSize);
//            doc.close();
//        } catch (DocumentException | IOException e1) {
//            e1.printStackTrace();
//        }
//
//    }
//
//    ///
//    public void htmlToPdf(String HTML, String CSS, String DEST, String setJson)
//            throws DocumentException, IOException {
//        Document document;
//        FileOutputStream dest;
//        PdfWriter writer;
//        InputStream is;
//        FileInputStream html;
//        JSONObject oset = JSON.parseObject(setJson);
//        Rectangle ps = PageSize.A4;
//        if (oset.containsKey("pageSize"))
//            ps = PageSize.getRectangle(oset.getString("pageSize"));
//        if (oset.containsKey("customSize")) {
//            JSONObject cs = oset.getJSONObject("customSize");
//            ps = new Rectangle(cs.getFloatValue("width") * psconst, cs.getFloatValue("height") * psconst);
//        }
//        if (oset.containsKey("landscape") && oset.getString("landscape").equals("true"))
//            ps = ps.rotate();
//        float mt = 20F;
//        float ml = 20F;
//        float mr = 20F;
//        float mb = 20F;
//        if (oset.containsKey("margin")) {
//            JSONObject margin = oset.getJSONObject("margin");
//            if (margin.containsKey("left"))
//                ml = Float.valueOf(margin.getString("left")).floatValue();
//            if (margin.containsKey("top"))
//                mt = Float.valueOf(margin.getString("top")).floatValue();
//            if (margin.containsKey("right"))
//                mr = Float.valueOf(margin.getString("right")).floatValue();
//            if (margin.containsKey("bottom"))
//                mb = Float.valueOf(margin.getString("bottom")).floatValue();
//        }
//        document = new Document(ps, ml * psconst, mr * psconst, mt * psconst, mb * psconst);
//        dest = new FileOutputStream(DEST);
//        writer = PdfWriter.getInstance(document, dest);
//        PdfPageEvent event = new PdfPageEvent();
//        String bsn = PDF_PRINT;
//
//        if (oset.containsKey("footer")) {
//            JSONObject pn = oset.getJSONObject("footer");
//            Footer fd = new Footer() {
//            };
//            fd.setBoxSizeName(bsn);
//            if (pn.containsKey("format"))
//                fd.setFooter(pn.getString("format"));
//            if (pn.containsKey("align"))
//                fd.setAlign(pn.getString("align"));
//            else
//                fd.setAlign("center");
//            if (pn.containsKey("text"))
//                fd.setFooterText(pn.getString("text"));
//            if (pn.containsKey("fontFamily"))
//                fd.setFontFamily(pn.getString("fontFamily"));
//            if (pn.containsKey("fontSize"))
//                fd.setFontSize(Float.parseFloat(pn.getString("fontSize")));
//            if (pn.containsKey("splitLine"))
//                fd.setSplitLine(pn.getString("splitLine"));
//            event.addElement(fd);
//        }
//        if (oset.containsKey("fonts")) {
//            JSONObject fonts = oset.getJSONObject("fonts");
//            String val;
//            String ckey;
//            for (Iterator iterator = fonts.entrySet().iterator(); iterator.hasNext(); PdfUtils.addFontFamily(ckey, val)) {
//                Map.Entry entry = (Map.Entry) iterator.next();
//                val = entry.getValue().toString();
//                ckey = (String) entry.getKey();
//            }
//
//        }
//
//        writer.setBoxSize(bsn, ps);
//        writer.setPageEvent(event);
//        is = getClass().getResourceAsStream(CSS);
//        html = new FileInputStream(HTML);
//        document.open();
//        try {
//            EcpXmlWorkerFontProvider myFontProvider = new EcpXmlWorkerFontProvider();
//            TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
//            tagProcessorFactory.removeProcessor("img");
//            tagProcessorFactory.addProcessor(new ImageTagProcessor(), new String[]{
//                    "img"
//            });
//            com.itextpdf.tool.xml.css.CssFile defaultCss = XMLWorkerHelper.getCSS(is);
//            CssFilesImpl cssFiles = new CssFilesImpl();
//            cssFiles.add(defaultCss);
//            StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
//            HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(myFontProvider));
//            hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
//            HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
//
//            com.itextpdf.tool.xml.Pipeline pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
//            XMLWorker worker = new XMLWorker(pipeline, true);
//            Charset cset = Charset.forName("UTF-8");
//            XMLParser xmlParser = new XMLParser(true, worker, cset);
//            xmlParser.parse(html, cset);
//            xmlParser.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            is.close();
//            html.close();
//            document.close();
//            writer.close();
//            dest.close();
//            File fhtml = new File(HTML);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            is.close();
//            html.close();
//            document.close();
//            writer.close();
//            dest.close();
//            File fhtml = new File(HTML);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
