package other;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zhouruigang
 * 2020/5/9 14:20
 */
public class Ademo {
    private static String [] dayData = {"货币资金","内部市场","资金池体系","资金运作拓展表","货币资金趋势曲线","归集资金结构"};
    public static void main(String[] args) {

        List<String> demoList = Arrays.asList(dayData);
        String  ss = "货币资金";

//方法 一
        System.out.println(System.currentTimeMillis());

//方法 二
                System.out.println(Calendar.getInstance().getTimeInMillis());

//方法 三
        System.out.println(new Date().getTime());




    }
}
