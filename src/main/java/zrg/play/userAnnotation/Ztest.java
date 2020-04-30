package zrg.play.userAnnotation;

import java.lang.annotation.*;

/**
 * 元注解（注解的注解）的含义
 * Retention、运行
 *          按生命周期来划分可分为3类：
 *      1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；【Override】
 *      2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
 *      3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
 *      这3个生命周期分别对应于：Java源文件(.java文件) ---> .class文件 ---> 内存中的字节码。
 * Target、
 * Document、
 * Inherited、
 */

@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Ztest {

    String value() default "测试";
}
