package other;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * @author zhouruigang
 * 2020/5/8 15:09
 */
public class TestDemo {

    public static void main(String[] args) {

        String ss = "20200508";
        LocalDate localDate = LocalDate.now();

        System.out.println(localDate.getMonthValue());
        System.out.println(localDate.getYear());
        // 当前月 取上月最后一天  历史月 取最后一天

        int year = Integer.parseInt(ss.substring(0,4));
        int  month = Integer.parseInt(ss.substring(4,6));

        System.out.println("year"+year+"month"+month);

        if (year == localDate.getYear() && month== localDate.getMonthValue()){
            LocalDate today = LocalDate.now().minusMonths(1);
            System.out.println("-----");
            LocalDate lastDay =today.with(TemporalAdjusters.lastDayOfMonth());
            System.out.println(lastDay);
        }else{
            LocalDate lastDay =localDate.with(TemporalAdjusters.lastDayOfMonth());
           //shang
            System.out.println(lastDay);

        }

//
//        LocalDate today = LocalDate.now().minusMonths(1);
//        //本月的第一天
//        LocalDate firstday = LocalDate.of(today.getYear(),today.getMonth(),1);
//        //本月的最后一天
//        LocalDate lastDay =today.with(TemporalAdjusters.lastDayOfMonth());
//
//        System.out.println(firstday.getMonth().getValue()+"月");
//        System.out.println("最后一天："+lastDay.getDayOfMonth());
//        System.out.println("-------------------");



    }

}
