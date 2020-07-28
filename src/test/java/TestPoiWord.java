import com.alibaba.fastjson.JSONObject;
import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 *     POI在读写word docx文件时是通过xwpf模块来进行的，其核心是XWPFDocument。一个XWPFDocument代表一个docx文档，其可以用来读docx文档，也可以用来写docx文档。XWPFDocument中主要包含下面这几种对象：
 *  XWPFParagraph：代表一个段落。
 *  XWPFRun：代表具有相同属性的一段文本。
 *  XWPFTable：代表一个表格。
 *  XWPFTableRow：表格的一行。
 *  XWPFTableCell：表格对应的一个单元格。
 * @author zhouruigang
 * 2019/1/9 15:36
 */
public class TestPoiWord {

    /**
     * 通过XWPFWordExtractor访问XWPFDocument的内容
     * @throws Exception
     */
    @Test
    public void testReadByExtractor() throws Exception {
        InputStream is = new FileInputStream("C:\\Users\\zhouruigang\\Desktop\\new.docx");
        XWPFDocument doc = new XWPFDocument(is);
        XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
        String text = extractor.getText();
        System.out.println(text);
        CoreProperties coreProps = extractor.getCoreProperties();
        this.printCoreProperties(coreProps);
        this.close(is);
    }

    /**
     * 输出CoreProperties信息
     * @param coreProps
     */
    private void printCoreProperties(CoreProperties coreProps) {
        System.out.println(coreProps.getCategory());   //分类
        System.out.println(coreProps.getCreator()); //创建者
        System.out.println(coreProps.getCreated()); //创建时间
        System.out.println(coreProps.getTitle());   //标题
    }

    /**
     * 关闭输入流
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }







    @Test
    public void testString(){


        String  str ="{\"BuyerInfoCheck\":\"\",\"buyer\":\"\",\"buyerTaxId\":\"\",\"checkCode\":\"\",\"civilFund\":\"\",\"createTime\":\"\",\"creator\":\"\",\"details\":\"\",\"dwdh\":\"\",\"electronicdoc\":\"\",\"gid\":\"\",\"hasAddedIntoBill\":\"\",\"invoiceCheck\":\"\",\"invoiceCode\":\"\",\"invoiceDate\":\"\",\"invoiceNumber\":\"\",\"invoiceTypeCode\":\"10101\",\"invoiceTypeName\":\"\",\"isReadOnly\":\"\",\"isSelected\":\"\",\"kindName\":\"\",\"modelState\":\"\",\"modifier\":\"\",\"pretaxAmount\":\"\",\"region\":\"\",\"seller\":\"\",\"sellerBank\":\"\",\"sellerTaxId\":\"\",\"status\":\"\",\"tax\":\"\",\"taxCheckDetail\":\"\",\"total\":\"\",\"yhdm\":\"\",\"ywid\":\"\",\"ywkey\":\"\"}";

        JSONObject job = JSONObject.parseObject(str);

        Set<String> ks =  job.keySet();


        System.out.println(ks.size());






        String yy= "[com.ygsoft.ecp.core.framework.model.ItemCondition@7c0405, {\"conditionField\":\"XB\",\"jcls\":\"com.ygsoft.ecp.core.framework.model.ItemCondition\",\"operator\":\"6\",\"value\":\"00000001\"}]";



              String txt = "{\n" +
                      "    \"cacheKey\": 394762394,\n" +
                      "    \"completeBZ\": false,\n" +
                      "    \"customFilter\": \"\",\n" +
                      "    \"dxSelectorFilter\": [\n" +
                      "        {\n" +
                      "            \"GROUP\": \"1\",\n" +
                      "            \"conditionField\": \"dxmc\",\n" +
                      "            \"modelState\": \"1\",\n" +
                      "            \"operator\": \"6\",\n" +
                      "            \"value\": \"部\",\n" +
                      "            \"__dynamic\": 1\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"GROUP\": \"1\",\n" +
                      "            \"conditionField\": \"dxmc\",\n" +
                      "            \"modelState\": \"1\",\n" +
                      "            \"operator\": \"6\",\n" +
                      "            \"value\": \"委员会\",\n" +
                      "            \"__dynamic\": 1\n" +
                      "        },\n" +
                      "        {\n" +
                      "            \"GROUP\": \"2\",\n" +
                      "            \"conditionField\": \"dxmc\",\n" +
                      "            \"modelState\": \"1\",\n" +
                      "            \"operator\": \"6\",\n" +
                      "            \"value\": \"测试\",\n" +
                      "            \"__dynamic\": 1\n" +
                      "        }\n" +
                      "    ],\n" +
                      "    \"dxlxid\": \"5001\",\n" +
                      "    \"dxtyBZ\": false,\n" +
                      "    \"effectBZ\": true,\n" +
                      "    \"filterContext\": \"\",\n" +
                      "    \"inputerName\": \"\",\n" +
                      "    \"islogindwfilter\": true,\n" +
                      "    \"resultFields\": \"\",\n" +
                      "    \"viewFields\": [],\n" +
                      "    \"selDxids\": \"\",\n" +
                      "    \"yhdm\": \"1000004\"\n" +
                      "}";

        System.out.println(txt);

    }





}
