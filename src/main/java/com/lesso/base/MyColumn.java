package com.lesso.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解映射
 *
 * @author will_awoke
 * @version
 * @see MyColumn
 * @since
 */
@Target(java.lang.annotation.ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyColumn {
    String name() default "";
    Class type() default Object.class;
}
