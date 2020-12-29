package cn.fenqing.aop.annotation.annotation;

import java.lang.annotation.*;


/**
 * @author fenqing
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PointcutFlag {

    /**
     * 标识
     * @return
     */
    String flag();

}
