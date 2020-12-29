package cn.fenqing.aop.annotation.annotation;

import cn.fenqing.aop.annotation.constant.HandlerType;

import java.lang.annotation.*;


/**
 * @author fenqing
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PointcutHandler {

    /**
     * 标识
     * @return
     */
    String flag();

    /**
     * 类型
     * @return
     */
    HandlerType type() default HandlerType.AFTER;

    boolean async() default false;

}
