package io.github.wdpm.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author evan
 * @date 2020/4/23
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {

    /**
     * @return injected class type
     */
    Class<?> value() default Class.class;

    /**
     * @return bean name
     */
    String name() default "";

}
