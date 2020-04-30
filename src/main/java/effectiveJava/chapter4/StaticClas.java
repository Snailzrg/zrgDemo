package effectiveJava.chapter4;


/**
 * 静态成员类
 * @author zhouruigang
 * 2019/8/15 17:01
 */
public class StaticClas {

     public StaticClas(){
         new StaticA().init();
         new StaticB().init();

     }
     
     static{
    	 
    	 System.out.println("init......");
     }

    /**
     * 静态成员类
     */
    static class  StaticA{
        void init(){
            System.out.println(" StaticA init....."+ new FuHe().hashCode());
        }
    }

    /**
     * 非静态成员类
     */
    private class StaticB{
        void init(){
            System.out.println(" StaticB init....."+ new FuHe().hashCode());

        }
    }


}
