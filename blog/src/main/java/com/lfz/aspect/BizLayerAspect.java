package com.lfz.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 统一业务接口调用的监控, 日志, 业务异常处理
 */
@Slf4j
@Aspect
@Component
public class BizLayerAspect {

    @Around("@annotation(bizLayer)")
    public Object process(ProceedingJoinPoint pjp, BizLayer bizLayer) throws Throwable {
        log.info("aop start");
        Object obj = pjp.proceed();
        log.info("aop end");
        return obj;
    }
}
