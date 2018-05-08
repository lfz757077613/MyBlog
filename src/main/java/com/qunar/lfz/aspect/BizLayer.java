//package com.qunar.lfz.aspect;
//
//
//
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
///**
// * 业务层的拦截注解
// * 处理日志记录，监控，以及统一的异常处理
// */
//@Target({ElementType.METHOD})
//@Retention(RetentionPolicy.RUNTIME)
//public @interface BizLayer {
//
//    /** 当前业务的名称，没有时，使用 "类名_方法名" */
//    String value() default "";
//
//    /** 监控的前缀, 为空时,使用业务的名称作为前缀 */
//    String monitorPrefix() default "";
//
//    /** traceId 的前缀, 为空时, 使用业务名称的前缀 */
//    String traceIdPrefix() default "";
//
//    /** 日志的级别, 默认支持 info 级别的日志 */
//    int logLevel() default LogLevel.INFO;
//
//    /** 是否记录请求参数 */
//    boolean recordParam() default true;
//
//    /** 是否记录请求结果 */
//    boolean recordResult() default true;
//
//}
