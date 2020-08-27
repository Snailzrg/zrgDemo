package common.util.ImgUtil;

import java.awt.*;

/**
 * @auther zhouruigang
 * @date 2018/12/13 17:02
 */
public class ToolKitDemo {
/**
 * ToolKit  ToolKit是一个抽象类,ToolKit作为AWT工具箱,提供了GUI最底层的Java访问,例如从系统获取图像、获取屏幕分辨率,获取屏幕色彩模型、全屏的时候获得屏幕大小等Toolkit是个非常有用类的,提供许多修改窗口默认行为的方法。使用Toolkit更改Java应用程序标题栏默认图标:Toolkittk=Toolkit.getDefaultToolkit();Imageimage=tk.createImage("image.gif");/*image.
 */
    /***
     * Image image = Toolkit.getDefaultToolkit().getImage(String filename);
     * Image image = Toolkit.getDefaultToolkit().getImage(URL url);
     * Image image = Toolkit.getDefaultToolkit().createImage(byte[] imageData);
     */

    //  Toolkit toolkit= Toolkit.getDefaultToolkit();
    //         toolkit.getImage("C:/Users/zhouruigang/Desktop/img.png");

    Image image = Toolkit.getDefaultToolkit().getImage("C:/Users/zhouruigang/Desktop/img.png");

   // String file = "C:/Users/zhouruigang/Desktop/img2.png";
}
