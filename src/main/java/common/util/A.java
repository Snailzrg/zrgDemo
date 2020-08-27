package common.util;

/**
 * @author snail
 * 2020/8/19 16:37
 */
public class A {

    public static void main(String[] args) {
        String str = "";
//        Long longStr = new Long(str.replaceAll("[^\\d]+", ""));
        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
       String longStr = str.replaceAll(regEx, "");
        String longStr2=  str.replaceAll(longStr,"");
        System.out.println("字符串=========：" + longStr);
        System.out.println("字符串=======2==：" + longStr2);



    }
}
