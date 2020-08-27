package common.util.QRCodeUtil;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

/**
 * @author snail
 * 2020/8/5 9:28
 */
public class PdfExample1 {

    public static void main(String[] args) throws IOException, DocumentException {
       // doInitPdf();
//        initPDFbJ();
        initCA();
    }






    private static void doInitPdf() throws IOException, DocumentException {
        //Step 1—Create a Document.
        Document document = new Document();
        //Step 2—Get a PdfWriter instance.
        File pdfOut = File.createTempFile("PdfExample1",".pdf");
        PdfWriter.getInstance(document, new FileOutputStream("createSamplePDF.pdf"));
        //Step 3—Open the Document.
        document.open();
        //Step 4—Add content.
        document.add(new Paragraph("Hello World"));
        //Step 5—Close the Document.
        document.close();
    }


    private static void initPDFbJ() throws FileNotFoundException, DocumentException {
        //页面大小
        Rectangle rect = new Rectangle(PageSize.A4.rotate());
        //页面背景色
        rect.setBackgroundColor(BaseColor.ORANGE);
        Document doc = new Document(rect);
        OutputStream out = new FileOutputStream("initPDFbJ.pdf");
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        //PDF版本(默认1.4)
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
        //文档属性
        doc.addTitle("Title@sample");
        doc.addAuthor("Author@rensanning");
        doc.addSubject("Subject@iText sample");
        doc.addKeywords("Keywords@iText");
        doc.addCreator("Creator@iText");

        //页边空白
        doc.setMargins(10, 20, 30, 40);
        doc.open();
        doc.add(new Paragraph("Hello World"));



        doc.close();
    }

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


    private static void initChunk() throws FileNotFoundException, DocumentException {
        //页面大小
        Rectangle rect = new Rectangle(PageSize.A4.rotate());
        //页面背景色
        rect.setBackgroundColor(BaseColor.ORANGE);
        Document document = new Document(rect);
        OutputStream out = new FileOutputStream("initChunk.pdf");
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Chunk("China"));
        document.add(new Chunk(" "));
        Font font = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);
        Chunk id = new Chunk("chinese", font);
        id.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
        id.setTextRise(6);
        document.add(id);
        document.add(Chunk.NEWLINE);

        document.add(new Chunk("Japan"));
        document.add(new Chunk(" "));
        Font font2 = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);
        Chunk id2 = new Chunk("japanese", font2);
        id2.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
        id2.setTextRise(6);
        id2.setUnderline(0.2f, -2f);
        document.add(id2);
        document.add(Chunk.NEWLINE);

//Phrase对象: a List of Chunks with leading
        document.newPage();
        document.add(new Phrase("Phrase page"));

        Phrase director = new Phrase();
        Chunk name = new Chunk("China");
        name.setUnderline(0.2f, -2f);
        director.add(name);
        director.add(new Chunk(","));
        director.add(new Chunk(" "));
        director.add(new Chunk("chinese"));
        director.setLeading(24);
        document.add(director);

        Phrase director2 = new Phrase();
        Chunk name2 = new Chunk("Japan");
        name2.setUnderline(0.2f, -2f);
        director2.add(name2);
        director2.add(new Chunk(","));
        director2.add(new Chunk(" "));
        director2.add(new Chunk("japanese"));
        director2.setLeading(24);
        document.add(director2);

//Paragraph对象: a Phrase with extra properties and a newline
        document.newPage();
        document.add(new Paragraph("Paragraph page"));

        Paragraph info = new Paragraph();
        info.add(new Chunk("China "));
        info.add(new Chunk("chinese"));
        info.add(Chunk.NEWLINE);
        info.add(new Phrase("Japan "));
        info.add(new Phrase("japanese"));
        document.add(info);

//List对象: a sequence of Paragraphs called ListItem
        document.newPage();
        List list = new List(List.ORDERED);
        for (int i = 0; i < 10; i++) {
            ListItem item = new ListItem(String.format("%s: %d movies",
                    "country" + (i + 1), (i + 1) * 100), new Font(
                    Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE));
            List movielist = new List(List.ORDERED, List.ALPHABETICAL);
            movielist.setLowercase(List.LOWERCASE);
            for (int j = 0; j < 5; j++) {
                ListItem movieitem = new ListItem("Title" + (j + 1));
                List directorlist = new List(List.UNORDERED);
                for (int k = 0; k < 3; k++) {
                    directorlist.add(String.format("%s, %s", "Name1" + (k + 1),
                            "Name2" + (k + 1)));
                }
                movieitem.add(directorlist);
                movielist.add(movieitem);
            }
            item.add(movielist);
            list.add(item);
        }
        document.add(list);
    }
}
