import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import util.poiUtil.ExcelUtil_Zrg;

import java.io.*;
import java.util.*;

/**
 * @auther: zhouruigang
 * @date: 2018/11/16 10:36
 */
public class TestPoi1 {

    public static void main(String[] args) {

        List<String> x = new ArrayList<>();

    }

    @Test
    public  void testC() {
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
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\zhouruigang\\Desktop\\新的ygEXCEL34.xlsx");
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

    /**
     * 国际化excel替换 [分功能复制英文]
     */
    @Test
    public void testExcelCopy() {
        try {
            InputStream inputStream = new FileInputStream("C:\\Users\\zhouruigang\\Desktop\\国际化工具\\back.xls\\移动审批_english_20200424.xlsx");
            Workbook wb = ExcelUtil_Zrg.getWorkbook(inputStream, "移动审批_english_20200424.xlsx");
            List<List<String>> lists = ExcelUtil_Zrg.getExcelString(wb, null, null, 0);

            inputStream.close();
            // 翻译完的excel
            InputStream englishInputStream = new FileInputStream("C:\\Users\\zhouruigang\\Desktop\\国际化工具\\grmMobile_english.xls");
            Workbook wb2 = ExcelUtil_Zrg.getWorkbook(englishInputStream, "grmMobile_english.xls");
            List<List<String>> lists2 = ExcelUtil_Zrg.getExcelString(wb2, null, null, 0);

            englishInputStream.close();
            lists2.stream().forEach(s -> {
                List<String> used = lists.stream().filter(t2 ->
                            s.get(0).equals(t2.get(0))).findAny().orElse(null);
                if (used != null){
                    String e = used.get(2);
                    s.set(2,e);
                }

            });

            long startTime=System.currentTimeMillis();

            // 重新写入xls文件中
//            ExcelUtil_Zrg.createDate(wb, "");
            Workbook wb3 = new XSSFWorkbook(); // 定义一个新的工作簿
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\zhouruigang\\Desktop\\grmMobile_english.xlsx");

            List<String> params = new ArrayList<>();
            params.add("源码简体");
            params.add("简体");
            params.add("英文");
            params.add("繁体");
            params.add("说明");
            params.add("KEYNAME");
            params.add("长度");
            params.add("文件类型");
            params.add("o");
            ExcelUtil_Zrg.createHead(wb3, "这个是一个标题", params);
            ExcelUtil_Zrg.createDate(wb3, lists2);

            // ExcelUtil_Zrg.removeRow(wb,0,3);

//            ExcelUtil_Zrg.insertBlankRow(wb3, 0, 3, new ArrayList<>());
//            String[] sheetNames = {"sheet1", "sheet2"};
//            ExcelUtil_Zrg.rebuildWorkbook(wb3, 2, sheetNames);
            wb3.write(fileOut);
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSQ(){
        Set<String> set = new HashSet<>();
        File file = new File("C:\\Users\\zhouruigang\\Desktop\\国际化_grmMobile_gen.sql");
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                String [] sts = tempString.split("\\(");
                String ss = sts[sts.length-1];
                String [] sts1 = ss.split("\\,");
                // 显示行号
                set.add(sts1[0]);
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        if (set.size()>0)
        {
            String sql = "DELETE FROM ECP_LAN_BASEDICT WHERE ZH_CHNAME IN ("
                    ;
           for (String it:set){

               sql+=" "+it+" ,";

           }


            sql+=" )";

            System.out.println(sql);

        }

    }








    /**
     *
     */
    @Test
    public void testTextOp(){
        String ssZ ="蒙DC3877 保险单号:1201405072020000459YD等,代收车船税:RMB1800.0,滞纳金:RMB0.0,税款属期:2020年01月至2020年12月,含保费合计:RMB2704.0";
        int szl = ssZ.length();

        Map hashMap = new HashMap();

        hashMap.put("jbr","1085219");

        JSONObject ibo = new JSONObject();
        ibo.put("jbr","1085219");

        String sss = ibo.toJSONString();

        String ss = hashMap.toString();
        System.out.println(ss);

        final JSONObject otherParam = new JSONObject();
        otherParam.put("inputersceneList", "xxx");

        otherParam.containsKey("inputersceneList");


        String jbrid = "jbr";
        final String[] jbrs = jbrid.split("\\.");

        int lt =jbrs.length;
        if (jbrs.length > 1) {
            jbrid = jbrs[jbrs.length-1];
        }




    }





    @Test
    public void testDoTextOp(){

//
//
//        try{
////            fr = new FileReader("C:\\Users\\zhouruigang\\Desktop\\file1.txt");
//            InputStream inputStream = new FileInputStream("C:\\Users\\zhouruigang\\Desktop\\file1.txt");
//            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
//            BufferedReader read = new BufferedReader(isr);
////            bufferedreader = new BufferedReader(fr);
//            String line = null;
//            while(true){
//                line = read.readLine();
//                if(line == null){
//                    break;
//                }
//
//                if (line.contains(".js") || line.contains(".html") )
//                 System.out.println(line);
//            }
//        }catch(Exception e){
//            System.out.println(e);
//        }finally{
//            try{
//                bufferedreader.close();
//                fr.close();
//            }catch(Exception e){
//                System.out.println(e);
//            }
//        }
        }

    }

