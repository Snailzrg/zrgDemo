package simpleNote.A0Common.regularExpression;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式 - 数字相关
 * @author zhouruigang
 * 2020/4/29 10:22
 */
public class RegularNumberDemo {
    //只能输入数字
    private static String REG_NUMBER_ONLY="^[0-9]*$";
    //整数或者 两位小数
    private static String REG_NUMBER="^[0-9]+\\.{0,1}[0-9]{0,2}$";
    // n位数字
    private static String REG_NUMBER_N="^\\d{4}$";
    // 匹配 前面的 3次或者多次
    private static String REG_NUMBER_N2="^[0-9]{3,}";

    private static  String REG_NUMBER_FLOAT="^\\-?[0-9]{1,}\\.?[0-9]{1,}$";

    /**
     * ^  匹配输入字符串开始的位置。如果设置了 RegExp 对象的 Multiline 属性，^ 还会与 \n 或 \r 之后的位置匹配。
     * $  匹配输入字符串结尾的位置。如果设置了 RegExp 对象的 Multiline 属性，$ 还会与 \n 或 \r 之前的位置匹配。
     * \b 匹配一个单词边界，即字与空格间的位置。
     * \B 非单词边界匹配。
     * @param args
     */
    public static void main(String[] args) {

        String simple1="123456.85";
        boolean flag = Pattern.matches(REG_NUMBER, simple1);
        System.out.println(flag);

        System.out.print("-----REG_NUMBER_ONLY :");
        Pattern pattern=Pattern.compile(REG_NUMBER_ONLY);
        Matcher matcher=pattern.matcher(simple1);
        boolean x= matcher.matches();
        System.out.println(x);

        System.out.print("-----REG_NUMBER_N :");
        System.out.println(Pattern.matches(REG_NUMBER_N, "1235"));


        System.out.print("-----REG_NUMBER_N2 :");
        System.out.println(Pattern.matches(REG_NUMBER_N2, "1235"));


        System.out.print("-----REG_NUMBER_FLOAT :");
        System.out.println(Pattern.matches(REG_NUMBER_FLOAT, "1235.9"));

        /**
         * 在调用时候 时不时报错
         * *** java.lang.instrument ASSERTION FAILED ***: "error == JVMTI_ERROR_NONE" at Reentrancy.c line: 161
         */


        String content = new String(simple1);
        Pattern pattern1 = Pattern.compile("^[0-9]+$", Pattern.MULTILINE);
        Matcher matcher1 = pattern1.matcher(content);
        ArrayList<String> matchList = new ArrayList<String>();

        while (matcher.find()) {
            matchList.add(matcher.group());
        }

        System.out.println(matchList);

    }


}
