package zrg.play;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author zhouruigang
 * 2020/4/26 16:18
 */
public class Test0426 {

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();

        System.out.println(now);

        String _dateString = now.toString();
        String sss = _dateString.replaceAll("-","");
        System.out.println(_dateString);
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");






    }
}
