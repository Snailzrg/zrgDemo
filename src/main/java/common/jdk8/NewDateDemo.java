package common.jdk8;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author zhouruigang
 * 2020/4/30 11:03
 */
public class NewDateDemo {

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
