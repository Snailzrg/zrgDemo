package effectiveJava.chapter5;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhouruigang
 * 2019/8/16 17:19
 */
public class Demo {


    public static Set unionSet(Set S1,Set S2){
        Set result = new HashSet(S1);
        result.addAll(S2);
        return result;

    }
}
