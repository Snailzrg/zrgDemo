package demo.regularExpression;

import java.util.regex.Pattern;

/**
 * 正则表达式 -字符串相关
 * @author zhouruigang
 * 2020/4/29 10:22
 */
public class RegularStringDemo {

    // * 表示 匹配前面的子表达式零次或多次
    private static String REG_1 ="runox*b";

    private static String REG_2 ="[a-z]runox[x]{0,2}";

    private static String REG_2_1 ="[runox[x]{0,2}]{1,2}";

    private static  String REG_3 ="\\bhiXX\\b.*\\bLucy\\b";

    private static String reg_TT2="集团*账户";

    private static String reg_TT="^.*集团.*账户$";


    public static void main(String[] args) {
//        System.out.print("-----REG_1 :");
//        System.out.println(Pattern.matches(REG_1, "runoxxb"));
//
//        System.out.print("-----REG_2 :");
//        System.out.println(Pattern.matches(REG_2, "vrunox"));
//
////        Regex.Replace()
//
//        String ss =  "runoxxrunoxx".replaceFirst(REG_2_1,"-");
//        System.out.println(ss);
//
//        System.out.println(Pattern.matches(REG_3, "bhiXXLucy"));
//
//        System.out.print("-----er:");
//        System.out.println(Pattern.matches("\\w*", "xx"));


        //20200511

        System.out.print("-----reg_TT:");
        System.out.println(Pattern.matches(reg_TT, "58,四、集团账户,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,58,"));


//        [^/]+(?!.*exe)

        String sssreg = "/([^.]+)$/";

        String ss = "DX.DX.DX.DX.dxmc";

        String [] AA =  ss.split("\\.");

        if (AA.length>0){

            System.out.println(AA[AA.length-1]);


        }


        System.out.println(Pattern.matches(sssreg, ss));


        System.out.println(ss.matches(sssreg)
        );
    }


}
