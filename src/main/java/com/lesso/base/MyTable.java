package com.lesso.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解映射
 *
 * @author will_awoke
 * @version
 * @see MyTable
 * @since
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTable {
    String name() default "";
    String value() default "";
    boolean defaultDBValue() default false;
}
