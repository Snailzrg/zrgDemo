package util.poiUtil;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**** * 自己玩一下poi excel
 *
 * Sheet 页
 *  需要 poi 和 poi-ooxml 两个jar包 并且版本必须一致 不然容易报错:....filesystem.FileMagic
 *
 *
 * @auther: zhouruigang
 * @date: 2018/11/16 10:40
 */
public class ExcelUtil_Zrg {
    public static final String excel2003 = "xls";

    /**
     * 根据版本号，获取Excel poi对象
     *
     * @param inputStream
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileName) throws IOException {
        if (fileName == null || "".equals(fileName)) {
            return null;
        }
        Workbook wb;
        if (fileName.endsWith(excel2003)) {
            wb = new HSSFWorkbook(inputStream);
        } else {
            wb = new XSSFWorkbook(inputStream);
        }
        return wb;
    }

    /**
     * 只有一个Sheet 只有一页
     * 创建表格的头部
     */
    public static void createHead(Workbook wb, String title, List<String> params) {
        if (!params.isEmpty()) {
            Sheet sheet = wb.createSheet(title);  // Sheet->页
            Row row = sheet.createRow(0);//合并列
            Cell cell = row.createCell(0);
            cell.setCellValue(title);
            CellRangeAddress region = new CellRangeAddress(0, 0,
                    0, params.size() - 1 > 0 ? params.size() - 1 : 1);
            CellStyle style = wb.createCellStyle();
            sheet.addMergedRegion(region);
            style.setAlignment(HorizontalAlignment.CENTER);
            //字体
            XSSFFont font = (XSSFFont) wb.createFont();
            font.setFontName("宋体");// 设置字体
            font.setFontHeight(12);// 设置字体的大小
            style.setFont(font);// 将字体添加到表格中去
            cell.setCellStyle(style);
            //标题行 数据行从第二行起
            Row row1 = sheet.createRow(1);
            for (int j = 0; j < params.size(); j++) {
                sheet.autoSizeColumn(j);
                Cell cell2 = row1.createCell(j);
                style.setFont(font);// 将字体添加到表格中去
                cell2.setCellStyle(style);
                cell2.setCellValue(params.get(j));
            }
        }
    }

    /**
     * 写入数据 从下标2行开始
     *
     * @param wb
     * @param data
     */
    public static void createDate(Workbook wb, List<List<String>> data) {
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 0; i < data.size(); i++) {
            List<String> oneRow = data.get(i);
            Row row = sheet.createRow(i + 2);
            for (int j = 0; j < oneRow.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(wb.getCellStyleAt(0));
                cell.setCellValue(oneRow.get(j));
            }
        }
    }

    /**
     * 删除指定行
     *
     * @param workbook
     * @param sheetIndex 页
     * @param rowIndex   下标
     * @return
     */
    public static Workbook removeRow(Workbook workbook, int sheetIndex, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
        }
        if (rowIndex == lastRowNum) {
            sheet.removeRow(sheet.getRow(rowIndex));
        }
        return workbook;
    }

    /**
     * 在指定位置插入 数据行/空白行
     *
     * @param workbook
     * @param sheetIndex
     * @param rowIndex
     * @return
     */
    public static Workbook insertBlankRow(Workbook workbook, int sheetIndex, int rowIndex, List<String> params) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        //只操作数据行
        rowIndex += 2;
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex <= lastRowNum) {
            sheet.shiftRows(rowIndex, lastRowNum, 1);
            // 获得上一行的Row对象
            Row preRow = sheet.getRow(rowIndex - 1);
            short rowNum = preRow.getLastCellNum();
            Row curRow = sheet.createRow(rowIndex);
            // 新生成的Row创建与上一个行相同风格的Cell
            for (short i = preRow.getFirstCellNum(); i < rowNum; i++) {
                Cell cell = preRow.getCell(i);
                CellStyle style = cell.getCellStyle();

                Cell cell2 = curRow.createCell(i);
                cell2.setCellValue(params.size() == rowNum ? params.get(i) : "");
                cell2.setCellStyle(style);

            }
            return workbook;
        }
        return null;
    }

    /**
     * 根据sheet(0)作为模板重建workbook
     *
     * @param workbook
     * @param sheetNum
     * @param sheetNames
     * @return
     */
    public static Workbook rebuildWorkbook(Workbook workbook, int sheetNum, String... sheetNames) {
        if (sheetNames.length == sheetNum) {
            for (int i = 0; i < sheetNum; i++) {
                workbook.cloneSheet(0);
                // 生成后面的工作表并指定表名
                workbook.setSheetName(i + 1, sheetNames[i]);
            }
            // 删除第一张工作表
            workbook.removeSheetAt(0);
            return workbook;
        }
        return null;
    }

    /**
     * 指定行数开始读取Excel
     *
     * @param workbook   1
     * @param startRow   开始行
     * @param startCol   开始列
     * @param indexSheet
     * @return
     */
    public static List<List<String>> getExcelString(Workbook workbook, Integer startRow, Integer startCol, int indexSheet) {
        List<List<String>> stringTable = new ArrayList<List<String>>();
        // 获取指定表对象 Excel下页面
        Sheet sheet = workbook.getSheetAt(indexSheet);
        //默认 第二行开始 第一列
        int rowNum = sheet.getLastRowNum();
        if (startRow == null || startRow > rowNum)
            startRow = 2;
        if (startCol == null)
            startCol = 0;
        for (int i = startRow; i <= rowNum; i++) {
            List<String> oneRow = new ArrayList<String>();
            Row row = sheet.getRow(i);
            // 根据当前指针所在行数计算最大列数
            int colNum = row.getLastCellNum();
            for (int j = startCol; j <= colNum; j++) {
                // 确定当前单元格
                Cell cell = row.getCell(j);
                String cellValue = " ";
                if (cell != null) {
                    // 验证每一个单元格的类型
                    cellValue = getCellValue(cell).toString();
                }
                // 生成一行数据
                oneRow.add(cellValue);
            }
            stringTable.add(oneRow);
        }
        return stringTable;
    }

    /***
     * 返回MAP格式数据
     * String 拼接 -
     */
    public static Map<Integer, String> mapExcelDate(Workbook workbook, Integer startRow, Integer startCol, int indexSheet) {
        Map<Integer, String> map = new HashedMap<>();
        Sheet sheet = workbook.getSheetAt(indexSheet);
        //默认 第二行开始 第一列
        int rowNum = sheet.getLastRowNum();
        if (startRow == null || startRow > rowNum)
            startRow = 2;
        if (startCol == null)
            startCol = 0;
        for (int i = startRow; i <= rowNum; i++) {
            Row row = sheet.getRow(i);
            StringBuilder sb = new StringBuilder(i);
            int colNum = row.getLastCellNum();
            for (int j = startCol; j <= colNum; j++) {
                // 确定当前单元格
                Cell cell = row.getCell(j);
                if (cell != null) {
                    // 验证每一个单元格的类型
                    sb.append("-" + getCellValue(cell).toString());
                }
            }
            map.put(new Integer(i), sb.toString());
        }
        return map;
    }

    /***
     * 格式化Cell数据
     * @param cell
     * @return
     */
    private static Object getCellValue(Cell cell) {
        Object obj = " ";
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return obj;
    }

    /**
     * 动态设置cell 的宽度与高度 以及 字体的样式 大小(受cell影响) 计算 cellValue 字符数*像素 设置cell 长宽
     *  字体大小设置
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *  public static Map<Integer,List<String>> getExcelMap(Workbook workbook, Integer startRow, Integer startCol, int indexSheet) {
     *         Map<Integer,List<String>> map = new HashedMap<>();
     *         // 获取指定表对象 Excel下页面
     *         Sheet sheet = workbook.getSheetAt(indexSheet);
     *         //默认 第二行开始 第一列
     *         int rowNum = sheet.getLastRowNum();
     *         if (startRow == null || startRow > rowNum)
     *             startRow = 2;
     *         if (startCol == null)
     *             startCol = 0;
     *         for (int i = startRow; i <= rowNum; i++) {
     *             List<String> oneRow = new ArrayList<String>();
     *             Row row = sheet.getRow(i);
     *             // 根据当前指针所在行数计算最大列数
     *             int colNum = row.getLastCellNum();
     *             for (int j = startCol; j <= colNum; j++) {
     *                 // 确定当前单元格
     *                 Cell cell = row.getCell(j);
     *                 String cellValue = " ";
     *                 if (cell != null) {
     *                     // 验证每一个单元格的类型
     *                     cellValue = getCellValue(cell).toString();
     *                 }
     *                 // 生成一行数据
     *                 oneRow.add(cellValue);
     *             }
     *             stringTable.add(oneRow);
     *         }
     *         return map;
     *     }
     *
     */

    /***
     *  单元格 样式 暂时不设置
     * 设置数据样式
     * @return
     */
    public static void setBodyStyle(Workbook wb) {
        // 创建单元格样式
        CellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置单元格居中对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 创建单元格内容不显示自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = (XSSFFont) wb.createFont();
        font.setFontName("宋体");// 设置字体
        font.setFontHeight(14);// 设置字体的大小
        cellStyle.setFont(font);// 将字体添加到表格中去
        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
    }

    /**
     * 设置单元格字体大小
     */
    private static void setFontSize(Workbook book, Cell cell) {
        Font font = book.createFont();
        font.setFontName("宋体");
        for (short k = 12; k >= 5; k--) {
            font.setFontHeightInPoints(k);
            if (checkCellReasonable(cell, k)) {
                break;
            }
        }
        //解决单元格样式覆盖的问题
        CellStyle cStyle = book.createCellStyle();
        cStyle.cloneStyleFrom(cell.getCellStyle());
        cStyle.setWrapText(true);
        cStyle.setFont(font);
        cell.setCellStyle(cStyle);
    }

    /**
     * 校验单元格中的字体大小是否合理
     */
    private static boolean checkCellReasonable(Cell cell, short fontSize) {
        int sum = cell.getStringCellValue().length();
        double cellWidth = getTotalWidth(cell);
        double fontWidth = (double) fontSize / 72 * 96 * 2;
        double cellHeight = cell.getRow().getHeightInPoints();
        double rows1 = fontWidth * sum / cellWidth;
        double rows2 = cellHeight / fontSize;
        return rows2 >= rows1;
    }

    /**
     * 获取单元格的总宽度（单位：像素）
     */
    private static double getTotalWidth(Cell cell) {
        int x = getColNum(cell.getSheet(), cell.getRowIndex(), cell.getColumnIndex());
        double totalWidthInPixels = 0;
        for (int i = 0; i < x; i++) {
            totalWidthInPixels += cell.getSheet().getColumnWidthInPixels(i + cell.getColumnIndex());
        }
        return totalWidthInPixels;
    }

    /**
     * 获取单元格的列数，如果是合并单元格，就获取总的列数
     */
    private static int getColNum(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        //判断该单元格是否是合并区域的内容
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow && column >= firstColumn && column <= lastColumn) {
                return lastColumn - firstColumn + 1;
            }
        }
        return 1;
    }

    /**
     * 往指定的sheet表中插入数据，插入的方法是提供一组valueMap。int[]是2维数组代表需要插入的数据坐标，从0开始
     *
     * @param workbook
     * @param sheetIndex
     * @param valueMap
     * @return
    //    */
//   public static Workbook insertExcel(Workbook workbook, int sheetIndex, Map<int[], String> valueMap) {
//      Sheet sheet = workbook.getSheetAt(sheetIndex);
//      Iterator<Entry<int[], String>> it = valueMap.entrySet().iterator();
//      while (it.hasNext()) {
//         Entry<int[], String> cellEntry = it.next();
//         int x = cellEntry.getKey()[0];
//         int y = cellEntry.getKey()[1];
//         String value = cellEntry.getValue();
//         Row row = sheet.getRow(y);
//         Cell cell = row.getCell(x);
//         cell.setCellValue(value);
//      }
//      return workbook;
//   }

    /**
     * 设置指定行的行高
     *
     * @param workbook
     * @param rowHight
     * @param sheetIndex
     * @param rowIndex
     * @return
     */
    public static Workbook setRowHeight(Workbook workbook, int rowHight, int sheetIndex, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        Row row = sheet.getRow(rowIndex);
        row.setHeight((short) rowHight);
        return workbook;
    }

    /**
     * 设置列宽
     *
     * @param workbook
     * @param columnWidth
     * @param sheetIndex
     * @param columnIndex
     * @return
     */
    public static Workbook setColumnWidth(Workbook workbook, int columnWidth, int sheetIndex, int columnIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        sheet.setColumnWidth(columnIndex, columnWidth);
        return workbook;
    }

    /**
     *
     switch (cell.getCellType()) {
     case Cell.CELL_TYPE_NUMERIC:
     // 表格中返回的数字类型是科学计数法因此不能直接转换成字符串格式
     cellValue = new BigDecimal(cell.getNumericCellValue()).toPlainString();
     break;
     case Cell.CELL_TYPE_STRING:
     cellValue = cell.getStringCellValue();
     break;
     case Cell.CELL_TYPE_FORMULA:
     cellValue = new BigDecimal(cell.getNumericCellValue()).toPlainString();
     break;
     case Cell.CELL_TYPE_BLANK:
     cellValue = "";
     break;
     case Cell.CELL_TYPE_BOOLEAN:
     cellValue = Boolean.toString(cell.getBooleanCellValue());
     break;
     case Cell.CELL_TYPE_ERROR:
     cellValue = "ERROR";
     break;
     default:
     cellValue = "UNDEFINE";
     }
     */
}
