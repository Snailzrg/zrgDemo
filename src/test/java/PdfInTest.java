import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author snail
 * 2020/8/6 1:44
 */
public class PdfInTest {







    @Test
    public void testP() throws IOException, DocumentException {
        int MINI_CELL_HEIGHT= 10;
        //取数据
        //A4横向
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File("C:\\Users\\snail\\Desktop\\TY.pdf")));
        // 设置中文字体
        BaseFont bfChinese =
                BaseFont.createFont("C:/Windows/Fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        //字体
        Font titleChinese = new Font(bfChinese, 20, Font.BOLD);
        Font secondtitleChinese = new Font(bfChinese, 12, Font.BOLD);
        Font fontChinese = new Font(bfChinese, 8, Font.NORMAL);

        document.open();
        Paragraph title = new Paragraph("上海飞粟供应链管理有限公司",titleChinese);
        //设置标题格式对齐方式
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph secondTitle = new Paragraph();
        Phrase nbsp = new Phrase("                                         ");
        secondTitle.add(nbsp);
        Phrase shd = new Phrase("送   货   单",secondtitleChinese);
        secondTitle.add(shd);

        secondTitle.add(nbsp);
        Phrase fax = new Phrase("本送货单传真有效",fontChinese);
        secondTitle.add(fax);
        secondTitle.setAlignment(Element.ALIGN_CENTER);
        secondTitle.setSpacingBefore(20f);
        document.add(secondTitle);

        // 设置表格的列宽和列数
        float[] widths = {25f, 30f, 35f, 25f, 25f, 25f, 30f, 30f, 25f, 25f, 25f };
        // 建立一个pdf表格
        PdfPTable table = new PdfPTable(widths);

        table.setSpacingBefore(20f);
        // 设置表格宽度为100%
        table.setWidthPercentage(100);

        PdfPCell cell = null;
        //第一行
        cell = new PdfPCell(new Paragraph("系统单号",fontChinese));
        cell.setMinimumHeight(MINI_CELL_HEIGHT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("waybillVO.getWaybill().getId().toString()",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("发货单号",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("zzz",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("委托方",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("zzz",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("等待通知卸货",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        //第二行
        cell = new PdfPCell(new Paragraph("收货方",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(MINI_CELL_HEIGHT);
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("收货联系电话",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("zzz",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("要求到货时间",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(new SimpleDateFormat("yyyy-MM-dd").format("zzz"),fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("否",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        //第三行
        cell = new PdfPCell(new Paragraph("收货联系人",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(MINI_CELL_HEIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("zzz",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("收货地址",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("zzz",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(7);
        table.addCell(cell);

        //第四行
        cell = new PdfPCell(new Paragraph("货品名称",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(MINI_CELL_HEIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("件数",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("规格型号（公斤/件）",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("重量（吨）",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("包装方式",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("提货时间",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("提货地",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("提货仓库",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("提货联系人",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("提货联系电话",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("备注",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        //第五行
        cell = new PdfPCell(new Paragraph("zzz",fontChinese));
        cell.setMinimumHeight(MINI_CELL_HEIGHT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("zzz",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("zzz",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        String weight = "sss";
//        if (waybillVO.getCargo().getWeight() != null) {
//            weight = waybillVO.getCargo().getWeight().toString();
//        } else if (waybillVO.getCargo().getVolume() != null) {
//            weight = waybillVO.getCargo().getVolume().toString();
//        }
        cell = new PdfPCell(new Paragraph(weight,fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        String packingManner = "其他";
        cell = new PdfPCell(new Paragraph(packingManner,fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(new SimpleDateFormat("yyyy-MM-dd").format(new Date()),fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("其他",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("waybillVO.getWaybill().getFromCompany()",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("waybillVO.getWaybill().getConsignerName()",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("waybillVO.getWaybill().getConsignerPhone()",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("waybillVO.getWaybill().getRemark()",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        //第六行

        PdfPTable inerTable = new PdfPTable(widths);
        inerTable.setWidthPercentage(100);

        PdfPCell inerCell = null;
        inerCell = new PdfPCell(new Paragraph("合计",fontChinese));
        inerCell.setMinimumHeight(MINI_CELL_HEIGHT);
        inerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        inerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        inerCell.setBorder(0);
        inerTable.addCell(inerCell);

        inerCell = new PdfPCell(new Paragraph("argo().getAmouwaybillVO.getCnt() "+ "件",fontChinese));
        inerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        inerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        inerCell.setColspan(3);
        inerCell.setBorderWidth(0);
        inerTable.addCell(inerCell);

        inerCell = new PdfPCell(new Paragraph(weight + "吨",fontChinese));
        inerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        inerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        inerCell.setBorderWidth(0);
        inerTable.addCell(inerCell);

        inerCell = new PdfPCell(new Paragraph("",fontChinese));
        inerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        inerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        inerCell.setColspan(6);
        inerCell.setBorder(0);
        inerTable.addCell(inerCell);

        cell.addElement(inerTable);
        cell.setColspan(11);
        table.addCell(cell);

        //第七行
        cell = new PdfPCell(new Paragraph("代收物项",fontChinese));
        cell.setMinimumHeight(MINI_CELL_HEIGHT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("代收金额",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("",fontChinese));
        cell.setColspan(6);
        table.addCell(cell);

        //第八行
        cell = new PdfPCell(new Paragraph("委托方\n（签字并盖章）",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(MINI_CELL_HEIGHT * 3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("",fontChinese));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("操作备注",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("waybillVO.getWaybill().getRemark()",fontChinese));
        cell.setColspan(6);
        table.addCell(cell);

        //第九行

        //第十行
        cell = new PdfPCell(new Paragraph("收货方\n（签字并盖章）",fontChinese));
        cell.setMinimumHeight(MINI_CELL_HEIGHT * 3);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("",fontChinese));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("收货情况",fontChinese));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("",fontChinese));
        cell.setColspan(6);
        table.addCell(cell);

        document.add(table);

        Paragraph bottomString = new Paragraph();
        Phrase phone = new Phrase("客服电话：4009205366",fontChinese);
        bottomString.add(phone);
        Phrase nbspBottom = new Phrase("                                                                   ");
        bottomString.add(nbspBottom);
        Phrase zd = new Phrase("制单：" + "waybillVO.getStaffAccount().getRealName()",fontChinese);
        bottomString.add(zd);
        Phrase nbspBottom2 = new Phrase("               ");
        bottomString.add(nbspBottom2);
        Phrase printTime = new Phrase("打印时间：" + new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(new Date()),fontChinese);
        bottomString.add(printTime);
        bottomString.setIndentationLeft(70);
        document.add(bottomString);

        document.close();
    }

}
