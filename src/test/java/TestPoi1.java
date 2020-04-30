import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import util.poiUtil.ExcelUtil_Zrg;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @auther: zhouruigang
 * @date: 2018/11/16 10:36
 */
public class TestPoi1 {

    public static void main(String[] args) {

        List<String> x = new ArrayList<>();

    }

    @Test
    public static void testC() {
        try {
            File file = new File("C:\\Users\\zhouruigang\\Desktop\\文明单位导入模板.xlsx");
            FileInputStream in = new FileInputStream(file);
            Workbook wb = ExcelUtil_Zrg.getWorkbook(in, file.getName());
            int sheetCount = wb.getNumberOfSheets();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testD() {
        try {
            File file = new File("C:\\Users\\zhouruigang\\Desktop\\文明单位导入模板2.xlsx");
            InputStream inputStream = new FileInputStream(file);

            Workbook wb = ExcelUtil_Zrg.getWorkbook(inputStream, file.getName());

            List<List<String>> dateLists = ExcelUtil_Zrg.getExcelString(wb, 2, 1, 1);
            dateLists.stream().count();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testE() {
        try {
            Workbook wb = new XSSFWorkbook(); // 定义一个新的工作簿
            Sheet sheet = wb.createSheet("第一个Sheet页");  // 创建第一个Sheet页
            Font font = wb.createFont();
            Row row = sheet.createRow(0);//合并列
            Cell cell = row.createCell(0);
            cell.setCellValue("这个地方是标题");
            CellRangeAddress region = new CellRangeAddress(0, 0, 0, 5);
            CellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setFont(font);// 将字体添加到表格中去
            cell.setCellStyle(style);
            sheet.addMergedRegion(region);

            List<String> params = new ArrayList<>();
            params.add("序号");
            params.add("姓名");
            params.add("学号");

            Row row2 = sheet.createRow(1);
            for (int j = 0; j < params.size(); j++) {
                sheet.autoSizeColumn(j);
                Cell cell2 = row2.createCell(j);
                cell2.setCellValue(params.get(j));
                font.setFontHeightInPoints((short) 12);
                style.setFont(font);
                cell2.setCellStyle(style);
            }

            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\zhouruigang\\Desktop\\新的2.xlsx");
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testF() {
        try {
            Workbook wb = new XSSFWorkbook(); // 定义一个新的工作簿
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\zhouruigang\\Desktop\\新的ygEXCEL3.xlsx");
            List<String> params = new ArrayList<>();
            params.add("序号");
            params.add("姓名");
            params.add("学号");
            params.add("性别");
            params.add("手机");
            params.add("地址");
            ExcelUtil_Zrg.createHead(wb, "这个是一个标题", params);

            List<List<String>> data = new ArrayList<>();
            for (int ix = 0; ix < 220; ix++) {
                List<String> user = new ArrayList<>();
                user.add(String.valueOf(ix + 1));
                user.add("姓名" + ix);
                user.add("学号" + ix);
                user.add("性别" + ix);
                user.add("手机" + ix);
                user.add("地址" + ix);
                data.add(user);
            }
            ExcelUtil_Zrg.createDate(wb, data);

            // ExcelUtil_Zrg.removeRow(wb,0,3);

            ExcelUtil_Zrg.insertBlankRow(wb, 0, 3, new ArrayList<>());
            String[] sheetNames = {"sheet1", "sheet2"};
            ExcelUtil_Zrg.rebuildWorkbook(wb, 2, sheetNames);
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testG() {
        try {
            InputStream inputStream = new FileInputStream("C:\\Users\\zhouruigang\\Desktop\\新的ygEXCEL2-1.xlsx");
            Workbook wb = ExcelUtil_Zrg.getWorkbook(inputStream, "新的ygEXCEL2-1.xlsx");
            List<List<String>> lists = ExcelUtil_Zrg.getExcelString(wb, null, null, 0);

            long startTime=System.currentTimeMillis();

            Iterator<List<String>> iterator = lists.iterator();
            while (iterator.hasNext()) {
                List<String> vo = iterator.next();
                if (vo.isEmpty()){
                    System.out.println("x");

                }
                System.out.println(vo.get(0)+"---"+vo.get(1)+"---"+vo.get(3)+"---size");
                iterator.remove();
            }

            System.out.println(System.currentTimeMillis()-startTime+"---milles");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
