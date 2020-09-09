package other;

import common.util.Patterns;
import common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 处理md文件中 网路资源图片
 * @author snail
 * 2020/8/28 3:40
 */
public class DoMdToBase64 {
    //中断文件操作的
    List<String> errorMsg = new ArrayList<>();
    public static void main(String[] args) {

        String cxt ="![img](https://mmbiz.qpic.cn/mmbiz_png/8KKrHK5ic6XBEWaiblGb3iaicgTuUPXoIgy8fQBiaQW6AV7eRvMOnJhANpoDCHOxKy0yIWFl9ZsxLfLK01foVZc99mw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)";

//
//        可以看到GeneratedMethodAccessor1的classLoader为DelegatingClassLoader，其parent为AppClassLoader。
//
//        ![img](https://mmbiz.qpic.cn/mmbiz_png/8KKrHK5ic6XBEWaiblGb3iaicgTuUPXoIgy81ic0jb7iaHAUJeNIgdTsMw3lb8W7bZTaNbEDL5sh05ZeYqGnoPU2eeTg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

        Matcher matcher = Patterns.WEB_URL.matcher(cxt);
        if (matcher.find()){
            System.out.println(matcher.group());
        }

    }

    private static  boolean isImgUrl(String text){
        boolean flag =false;
        if (StringUtil.isNotEmpty(text)&&text.contains("image") && text.endsWith(")")){
          flag = false;

            String[]  tmp = text.trim().split("(");

          System.out.println(tmp[0]);
        }
        return flag;
    }
}
