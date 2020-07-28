package other;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhouruigang
 * 2020/5/8 9:39
 */
public class StringDo {

    private static String[] specialChar = new String[]{"-", "--", "0", "0.0", ""};
    private static String[] gridTableName = new String[]{"资金运作拓展表", "账户数量分布明细表", "账户分布明细表-直属单位", "资金备付明细表", "归集资金分布明细表",
            "不可归集资金分布明细表", "预算执行明细表", "带息负债明细表", "内部融资单位分布", "境内融资明细分布", "境内融资到期分布明细-月度", "境内融资到期分布明细-年度",
            "境外融资到期分布明细-月度", "境外融资到期分布明细-年度"};

    public static void main(String[] args) {

        //   6,国网天津电力,0,,,0,0,0300,9999FY02
        String ss = "6,国网天津电力,0,,,0,0,0300,9999FY02";

        boolean flag = false;
        String text = specialCharProcess("58,四、集团账户,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,58,", "内部融资单位分布");

        text = text + " ";
        String[] textArray = text.split(",");
        Map map = new LinkedHashMap();
        for (int i = 0, length = textArray.length; i < length; i++) {
            // 因为text文本最后加了一个空格，所以数组最后一个值去掉加的空格
            if (i == length - 1) {
                textArray[i] = textArray[i].substring(0, textArray[i].length() - 1);
            }
            map.put("field" + (i + 1), textArray[i]);
            if (Arrays.asList(gridTableName).contains("内部融资单位分布")) {
//                if ((i > 1 && i <= length - 3) && (!StringUtils.isNotBlank(textArray[i])
//                        || !Arrays.asList(specialChar).contains(textArray[i]))) {
//                    result = true;
//                }
                if (i > 1 && i <= length - 3) {
                    if (!StringUtils.isNotBlank(textArray[i])) {
                        System.out.println("I:" + i + "--isNotBlank");
                    }
                    if (!Arrays.asList(specialChar).contains(textArray[i])) {
                        System.out.println("I:" + i + "--contains");
                    }
                    //---------------------------------------------//
                    if ((!StringUtils.isNotBlank(textArray[i]))
                            || !Arrays.asList(specialChar).contains(textArray[i])) {
                        System.out.println("-------XXXX----+"+i+"--"+textArray[i]);
                        flag = true;
                    }
                }
            }
        }
        System.out.println(flag);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static String specialCharProcess(String text, String region) {
        StringBuilder sbValue = new StringBuilder();
        if (StringUtils.isNotBlank(text)) {
            // 防止都是逗号的情况,无法正常分割
            text = text + " ";
            String[] textArray = text.split(",");
            for (int i = 0, length = textArray.length; i < length; i++) {
                // 因为text文本最后加了一个空格，所以数组最后一个值去掉加的空格
                if (i == length - 1) {
                    textArray[i] = textArray[i].substring(0, textArray[i].length() - 1);
                }
                if (i == 0) {
                    if (!StringUtils.isNotBlank(textArray[i]) || Arrays.asList(specialChar).contains(textArray[i])) {
                        sbValue.append("0");
                    } else {
                        sbValue.append(textArray[i]);
                    }
                } else {

                    if (Arrays.asList(gridTableName).contains(region) && (i > 1 && i <= length - 3)
                            && (!StringUtils.isNotBlank(textArray[i])
                            || Arrays.asList(specialChar).contains(textArray[i]))) {
                        sbValue.append(",0");
                    } else {
                        sbValue.append("," + textArray[i]);
                    }
                }

            }

        }
        return sbValue.toString();
    }

}



