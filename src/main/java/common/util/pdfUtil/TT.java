package common.util.pdfUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author snail
 * 2020/8/14 2:45
 */
public class TT {

    public static void main(String[] args) throws IOException {
        final String sk = URLEncoder.encode("中", "UTF-8");

        final String reqJson = URLDecoder.decode("中国", "utf-8");

        String qrStr = "AAA.JPG";
        final StringBuilder sb = new StringBuilder();
//        sb.append("<html>\n" + "\t<head>\n" + "\t\t<style type='text/css'>\n" + "\t\t\t@page {\n"
//                + "\t\t\t\tsize: 842px*595px\n" + "\t\t\t}\n" + "\t\t\ttable td {\n" + "\t\t\t\ttext-align: center;\n"
//                + "\t\t\t\twidth: 15%;\n" + "\t\t\t\theight: 56px;\n" + "\t\t\t\tfont-size: 14;\n" + "\t\t\t}\n"
//                + "\t\t</style>\n" + "\t</head>\n" + "\t<body style='font-family:SimSun;'>\n"
//                + "\t\t<div class=\"wapper\">\n" + "\t\t\t<div class=\"div_head\" style=\"margin-top:95px;\">\n"
//                + "\t\t\t\t<div style=\"display: inline;  float:right;\">\n" + "\t\t\t\t\t<img src=\"" + qrStr
//                + "\" style=\"width:100px; width:100px; margin-top:-80px;\"></img>\n" + "\t\t\t\t</div>\n"
//                + "\t\t\t\t<div>\n"
//                + "\t\t\t\t\t<p style=\"text-align: center; font-size:20px; font-weight:bold\">国网内蒙古东部电力有限公司粘贴单</p>\n"
//                + "\t\t\t\t</div>\n" + "\t\t\t</div>\n"
//                + "\t\t\t<table border=\"0.5px\" width=\"100%\" height=\"650px\" cellspacing=\"0\">\n"
//                + "\t\t\t\t<div  style=\"width:100%;\">\n" + "\t\t\t\t\t<div style=\"width:45%; float:left;\">\n"
//                + "\t\t\t\t\t\t<p style=\"text-align:left;\">单位:国网内蒙古东部电力有限公司</p>\n" + "\t\t\t\t\t</div>\n"
//                + "\t\t\t\t\t<div style=\"width:33.3%; float:left;\">\n"
//                + "\t\t\t\t\t\t<p style=\"text-align:center;\">2020年7月8日</p>\n" + "\t\t\t\t\t</div>\n"
//                + "\t\t\t\t\t<div style=\"float:right;\">\n" + "\t\t\t\t\t\t<p style=\"text-align:right; \">单位:元</p>\n"
//                + "\t\t\t\t\t</div>\n" + "\t\t\t\t</div>\n" + "\t\t\t\t<span style=\"text-align:left;\"></span>\n"
//                + "\t\t\t\t<tr>\n" + "\t\t\t\t\t<th rowspan=\"9\" style=\"width:70%;\"></th>\n"
//                + "\t\t\t\t\t<td style=\"width:15%; height:56px;\">单据编号:</td>\n"
//                + "\t\t\t\t\t<td style=\"width:15%;\">2089</td>\n" + "\t\t\t\t</tr>\n" + "\t\t\t\t<tr>\n"
//                + "\t\t\t\t\t<td>费用类形:</td>\n" + "\t\t\t\t\t<td>出厂</td>\n" + "\t\t\t\t</tr>\n" + "\t\t\t\t<tr>\n"
//                + "\t\t\t\t\t<td>附件张数:</td>\n" + "\t\t\t\t\t<td>2</td>\n" + "\t\t\t\t</tr>\n" + "\t\t\t\t<tr>\n"
//                + "\t\t\t\t\t<td>部门:</td>\n" + "\t\t\t\t\t<td>c测试部门卡卡的卡机款卡的卡卡</td>\n" + "\t\t\t\t</tr>\n"
//                + "\t\t\t\t<tr>\n" + "\t\t\t\t\t<td>金额:</td>\n" + "\t\t\t\t\t<td>43,123,124</td>\n" + "\t\t\t\t</tr>\n"
//                + "\t\t\t\t<tr>\n" + "\t\t\t\t\t<td>经办人:</td>\n" + "\t\t\t\t\t<td>昂俊超</td>\n" + "\t\t\t\t</tr>\n" + "\n"
//                + "\t\t\t\t<tr>\n" + "\t\t\t\t\t<td>证明人:</td>\n" + "\t\t\t\t\t<td></td>\n" + "\t\t\t\t</tr>\n"
//                + "\t\t\t\t<tr>\n" + "\t\t\t\t\t<td> </td>\n" + "\t\t\t\t\t<td></td>\n" + "\t\t\t\t</tr>\n"
//                + "\t\t\t\t<tr>\n" + "\t\t\t\t\t<td> </td>\n" + "\t\t\t\t\t<td> </td>\n" + "\t\t\t\t</tr>\n"
//                + "\t\t\t</table>\n" + "\n" + "\t\t</div>\n" + "\t</body>\n" + "</html>\n");

            sb.append("<html><head><style type='text/css'>@page {size: 842px*595px}table td ");
            sb.append("{text-align: center;width: 15%;height: 56px;font-size: 14px;}</style></head>");
            sb.append("<body style='font-family:SimSun;'><div style=\"margin-top:95px;\">");
            sb.append("<div style=\"display: inline;  float:right;\">");

            // 替换html中图片（虽然现在用不到）
            sb.append("<img src=\"" + qrStr + "\" style=\"width:100px; width:100px; margin-top:-80px;\"></img></div><div>");
            sb.append("<p style=\"text-align: center; font-size:20px; font-weight:bold\">国网内蒙古东部电力有限公司粘贴单</p></div></div>");

            // table
            sb.append("<table border=\"0.5px\" width=\"100%\" height=\"650px\" cellspacing=\"0\">");
            sb.append("<div  style=\"width:100%;\"><div style=\"width:45%; float:left;\">");
            sb.append("<p style=\"text-align:left;\">单位:国网内蒙古东部电力有限公司</p></div>");

            sb.append("<div style=\"width:33.3%; float:left;\"><p style=\"text-align:center;\">" + 2 + "</p>");
            sb.append("</div><div style=\"float:right;\"><p style=\"text-align:right; \">单位:元</p></div></div>\n");

            // 表格 单据编号
            sb.append("<tr><th rowspan=\"9\" style=\"width:70%;\"></th>");
            sb.append("<td style=\"width:15%; height:56px;\">单据编号:</td><td style=\"width:15%;\"></td></tr>");

            //
            sb.append("<tr><td>费用类形:</td><td>" + 2 + "</td></tr><tr><td>附件张数:</td><td>" + 3
                    + "</td></tr><tr><td>部门:</td><td>" + 4 + "</td></tr>");
            sb.append("<tr><td>金额:</td><td>" + 2 + "</td></tr><tr><td>经办人:</td><td>" + 2
                    + "</td></tr><tr><td>证明人:</td><td></td></tr>");
            //
            sb.append("<tr><td></td><td></td></tr><tr><td> </td><td> </td></tr></table></body></html>");
        System.out.println(sb.toString());
        File html =File.createTempFile("HTML",".html");
        writeStringToFile(html,sb.toString(),"utf-8");
        System.out.println(html.getAbsolutePath());
    }


    private static  void writeStringToFile(final File file, final String data, final String encoding) throws IOException {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            if (encoding == null) {
                out.write(data.getBytes());
            } else {
                out.write(data.getBytes(encoding));
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
