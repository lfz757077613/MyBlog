package com.lfz.aspect;




import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务层的拦截注解
 * 处理日志记录，监控，以及统一的异常处理
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BizLayer {
    /** 是否记录请求参数 */
    boolean recordParam() default true;

    /** 是否记录请求结果 */
    boolean recordResult() default true;

}
