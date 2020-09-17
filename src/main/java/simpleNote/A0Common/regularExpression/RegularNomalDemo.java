package simpleNote.A0Common.regularExpression;

import java.util.regex.Pattern;

/**
 * 正则表达式 -常见
 * @author zhouruigang
 * 2020/4/29 10:22
 */
public class RegularNomalDemo {

    //身份证Id
    private static String simplePeopleId="/^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$/";

    //Id生成规则
    private static String simpleGenId="";

    //手机号码
    private static  String simpleMobile ="^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,1,2,5-9])|(177))\\d{8}$";

    //邮箱
    private static  String simpleEmail ="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    //密码复杂度  大小写+数字和字符 不低于8位  --不包含历史密码--
    //@see https://blog.csdn.net/aihaqi2765/article/details/101840106
    private static  String simplePassWord ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}";

    //URL
    private static  String  simpleInternetURL="^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";

   // private static  String  simpleInternetURLMS ="(ht|f)tp(s?)://[0-9a-zA-Z]([-.\w]*[0-9a-zA-Z])*(:(0-9)*)*(//?)([a-zA-Z0-9\-\.\?\,\'\/\\\+&amp;%\$#_]*)?";

    //HTML标签
    private static  String  simpleHTMLString = "";

    //日期 yyyy-MM-dd
    private static String simpleDate_Time = "/^[1-2][0-9][0-9][0-9]-[0-1]{0,1}[0-9]-[0-3]{0,1}[0-9]$/";


    /**
     * Java中正则表达式(regex)匹配多行(Pattern.MULTILINE和Pattern.DOTALL模式) Java中正则表达式怎样匹配换行符(\r\n,\n),从而实现多行匹配
     * 正则表达式中出现了^或者$, 默认只会匹配第一行. 设置了Pattern.MULTILINE模式,会匹配所有行。
     *
     */
    public static void main(String[] args) {

//        System.out.println(Pattern.matches(simplePassWord, "1234Ac.x"));

        // "^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$"

        System.out.println(Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,13}$", "1234007x"));


        System.out.println(Pattern.matches(simplePassWord, "123X+07x"));


        String wm ="^.*(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])[a-zA-Z0-9@#$%^&+=]*$";

        System.out.println(Pattern.matches(wm, "123X=07x"));

    String xs="/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*,\\.])[0-9a-zA-Z!@#$%^&*,\\.]{8,12}$/";
        System.out.println(Pattern.matches(xs, "123X@07x"));

        String paDATE ="^\\d{4}-[0-12]-\\d{1,2}";

        System.out.println("--->"+Pattern.matches(paDATE, "2020-12-5"));


        //(.|\n)
    }


}
