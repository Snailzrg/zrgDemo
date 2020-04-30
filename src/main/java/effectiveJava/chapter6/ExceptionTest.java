package effectiveJava.chapter6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 针对只有抛出特殊异常才成功的测试添加支持
 * @author zhouruigang
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {

//	Class<? extends Throwable> value();
	
	//注解中的多元素参数
	Class<? extends Throwable> [] value();
}
