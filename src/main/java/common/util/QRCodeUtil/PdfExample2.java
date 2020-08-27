package common.util.QRCodeUtil;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

/**
 * @author snail
 * 2020/8/5 9:28
 */
public class PdfExample2 {

    public static void main(String[] args) throws IOException, DocumentException {
        initCA();
    }


    private static void init(){}





    private static void initCA() throws IOException, DocumentException {
        //页面大小
        Rectangle rect = new Rectangle(PageSize.A4.rotate());
        //页面背景色
        Document document = new Document(rect);
        OutputStream out = new FileOutputStream("initCA2.pdf");
        PdfWriter writer = PdfWriter.getInstance(document, out);
        //页边空白
        document.setMargins(10, 20, 30, 40);
        document.open();
        document.add(new Paragraph("Hello World"));
        //Anchor对象: internal and external links
        Paragraph country = new Paragraph();
        Anchor dest = new Anchor("china", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE));
        dest.setName("CN");
        dest.setReference("http://www.china.com");//external
        country.add(dest);
        country.add(String.format(": %d sites", 10000));
        document.add(country);

//        document.newPage();
        Anchor toUS = new Anchor("Go to first page.", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE));
        toUS.setReference("#CN");//internal
        document.add(toUS);

        Image img = Image.getInstance("C:\\Users\\snail\\Desktop\\3880122.jpg");
        img.setAlignment(Image.LEFT | Image.TEXTWRAP);
        img.setBorder(Image.BOX);
        img.setBorderWidth(10);
        img.setBorderColor(BaseColor.WHITE);
        img.scaleToFit(1000, 72);//大小
        document.add(img);

        //Chapter, Section对象（目录）
//        document.newPage();
        Paragraph title = new Paragraph("Title");
        Chapter chapter = new Chapter(title, 1);

        title = new Paragraph("Section A");
        Section section = chapter.addSection(title);
        section.setBookmarkTitle("bmk");
        section.setIndentation(30);
        section.setBookmarkOpen(false);
        section.setNumberStyle(
                Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);

        Section subsection = section.addSection(new Paragraph("Sub Section A"));
        subsection.setIndentationLeft(20);
        subsection.setNumberDepth(1);
        document.add(chapter);
        document.close();
    }

}
