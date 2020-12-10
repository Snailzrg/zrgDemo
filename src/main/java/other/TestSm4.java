package other;
/**
 * @author snail
 * 2020/11/26 16:25
 */
public class TestSm4 {

    public static void main(String[] args) {
        try {
            System.out.println("开始****************************");
            String json = "{\"name\":\"测试SM4加密解密\",\"描述\":\"CSDN哈哈哈\"}";
            System.out.println("加密前："+json);
            //自定义的32位16进制秘钥
            String key = "86C63180C2806ED1F47B859DE501215B";
            String cipher = Sm4Util.encryptEcb(key,json);//sm4加密
            System.out.println("加密后："+cipher);
            System.out.println("校验："+Sm4Util.verifyEcb(key,cipher,json));//校验加密前后是否为同一数据
            json = Sm4Util.decryptEcb(key,cipher);//解密
            System.out.println("解密后："+json);
            System.out.println("结束****************************");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
