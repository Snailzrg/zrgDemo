package util.pdfUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * @author snail
 * 2020/8/3 14:05
 */
public class DoPdf {

    public static void main(String[] args) throws IOException {

        String htmlStr = "\ttable ,table tr td {border:1px dashed #000 ; border-collapse:collapse}   " + ".title_td{width:95px;}      " + " 厦门航空有限公司本部      " + "  通用报销单（标准）        " + "编号：1870            " + "    业务信息        " + "    经办部门    厦航本部_计划财务部    币种    RMB   " + " 合同编号    MFMMCG160625        申请总额    999   " + " 扣回预付款    0    其他扣款/冲销借款    0       " + " 预留质保金    0    附件张数(A4纸)    1    专票张数   " + " 1        预算信息    " + "        申请理由    11       " + " 业务类型    办公用纸采购支出    预算号    办公用纸采购支出20171020    " + "申请金额            收款信息 " + "           支付方式    转账－外单位     " + "   收款人    厦门市美利捷科技有限公司    收款人帐号    厦门市美利捷科技有限公司    支付金额   " + " 888        审核记录     " + "       时间    步骤名称    操作者    角色 " + "   处理意见        2017-12-15 17:04:00    新增    卢翔云" + "    财务共享中心.总账管理岗    同意        2017-12-14 08:52:16   " + " 新增    刘雪琴    财务共享中心.制证会计    同意     " + "   2017-12-14 08:47:01    新增    陈海容    财务共享中心.资金出纳  " + "  同意        2017-12-14 08:44:43    新增    赖淑华 " + "   财务共享中心.复核会计    同意        2017-12-14 08:42:55 " + "   新增    刘雪琴    财务共享中心.初审会计    同意      " + "  2017-12-14 08:33:46    新增    冯雁凌    预算责任中心负责人（本部_计划财务部）   " + " 同意        2017-12-14 08:29:48    新增    冯雁凌  " + "  经办人    请审批        会计分录 " + "           账期    2017-12    核算单位    计财部（小）    汇率" + "    1        账务科目    借方金额(原币)    贷方金额(原币)        " + "厦航.不分明细.管理费用-其他.其他营运费-其他费用.不分明细.不分明细.不分明细.共享中心.不分明细    0    888        " + "厦航.不分明细.银行存款-人民币.建设银行-重庆渝北801（收入）.不分明细.不分明细.不分明细.不分明细.不分明细    888    0  ";
        File file = File.createTempFile("pdfHtml", ".txt");
        if (file.exists()) {

            System.out.println("-----temp====");
        } else {

            System.out.println("123");

        }

    }

}
