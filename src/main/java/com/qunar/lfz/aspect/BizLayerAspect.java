//package com.qunar.lfz.aspect;
//
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.MDC;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * 统一业务接口调用的监控, 日志, 业务异常处理
// */
//@Aspect
//@Slf4j(topic = "com.qunar.train.bypass.biz")
//public class BizLayerAspect {
//
//    @Around("@annotation(bizLayer)")
//    public Object process(ProceedingJoinPoint pjp, BizLayer bizLayer) throws Throwable {
//        long start = System.currentTimeMillis();
//        InvokeContext invokeContext = parseInvokeContext(pjp, bizLayer);
//        boolean createTraceId = false;
//        try {
//            createTraceId = setTraceId(bizLayer, invokeContext.getInvokeInterfaceName());
//            Object obj = pjp.proceed();
//            long waste = System.currentTimeMillis() - start;
//            if (LogLevel.isEnable(LogLevel.INFO, bizLayer.logLevel())) {
//                log.info("invoke Method {} #### Args {} #### Result {} #### waste {}ms",
//                        invokeContext.getInvokeInterfaceName(),
//                        invokeContext.isRecordParam() ? jsonUtils.toJson(pjp.getArgs()) : "--",
//                        invokeContext.isRecordResult() ? jsonUtils.toJson(obj) : "--",
//                        waste);
//            }
//            QMonitor.recordOne(invokeContext.getMonitorKey() + MonitorConstants.MONITOR_SUCCESS, waste); // record success waste
//            return obj;
//        } catch (ConstraintViolationException e) { // param invalid
//            String errorMsg = getConstraintViolationMsg(e);
//            BizCommonException bizException = new BizCommonException(errorMsg, BizErrorCodeEnum.PARAM_INVALID);
//            return errorRecordAndReturn(invokeContext, MonitorConstants.MONITOR_PARMA_ERROR, bizException, null);
//        } catch (BizCommonException e) { // biz error
//            return errorRecordAndReturn(invokeContext, MonitorConstants.LOG_KEY_JOINT + e.getErrorCode() + MonitorConstants.MONITOR_ERROR,
//                    e, e);
//        } catch (Throwable t) { // system error
//            BizCommonException bizException = new BizCommonException(BizErrorCodeEnum.SYSTEM_ERROR);
//            return errorRecordAndReturn(invokeContext, MonitorConstants.MONITOR_SYS_ERROR, bizException, t);
//        } finally {
//            if (createTraceId) { // remove added traceId
//                MDC.remove(MonitorConstants.TRACE_ID);
//            }
//            QMonitor.recordOne(invokeContext.getMonitorKey() + MonitorConstants.MONITOR_TOTAL);
//        }
//    }
//
//    private InvokeContext parseInvokeContext(ProceedingJoinPoint pjp, BizLayer bizLayer) {
//        Class<?> returnType = getReturnType(pjp);
//        String invokeInterfaceName  = getInvokeInterfaceName(pjp, bizLayer);
//        String monitorKey = getMonitorKey(bizLayer, invokeInterfaceName);
//        InvokeContext invokeContext = new InvokeContext();
//        invokeContext.setReturnType(returnType);
//        invokeContext.setArgs(pjp.getArgs());
//        invokeContext.setInvokeInterfaceName(invokeInterfaceName);
//        invokeContext.setMonitorKey(monitorKey);
//        invokeContext.setReqLogLevel(bizLayer.logLevel());
//        invokeContext.setRecordParam(bizLayer.recordParam());
//        invokeContext.setRecordResult(bizLayer.recordResult());
//        // 判断 returnType 是否是 ResponseObj 的类型或父类型
//        invokeContext.setCanReturnResponseObj(returnType.isAssignableFrom(ResponseObj.class));
//        return invokeContext;
//    }
//
//    private Class<?> getReturnType(ProceedingJoinPoint pjp) {
//        Signature signature = pjp.getSignature();
//        Class<?> returnType = ((MethodSignature) signature).getReturnType();
//        return returnType;
//    }
//
//    private String getInvokeInterfaceName(ProceedingJoinPoint pjp, BizLayer bizLayer) {
//        String interfaceName = bizLayer.value();
//        if (StringUtils.isBlank(interfaceName)) {
//            interfaceName = pjp.getTarget().getClass().getSimpleName()
//                    + MonitorConstants.LOG_KEY_JOINT
//                    + pjp.getSignature().getName().replace(".", MonitorConstants.LOG_KEY_JOINT);
//        }
//        return StringUtils.stripEnd(interfaceName, MonitorConstants.LOG_KEY_JOINT).toLowerCase();
//    }
//
//    private String getMonitorKey(BizLayer bizLayer, String invokeInterfaceName) {
//        return StringUtils.isBlank(bizLayer.monitorPrefix()) ? invokeInterfaceName :
//                StringUtils.stripEnd(bizLayer.monitorPrefix(), MonitorConstants.LOG_KEY_JOINT);
//    }
//
//    private boolean setTraceId(BizLayer bizLayer, String invokeInterfaceName) {
//        String value = MDC.get(MonitorConstants.TRACE_ID);
//        if (StringUtils.isEmpty(value)) {
//            String traceIdPrefix = bizLayer.traceIdPrefix();
//            if (StringUtils.isBlank(traceIdPrefix)) {
//                traceIdPrefix = invokeInterfaceName;
//            }
//            String traceId = StringUtils.stripEnd(traceIdPrefix, MonitorConstants.LOG_KEY_JOINT)
//                    + MonitorConstants.LOG_KEY_JOINT + System.currentTimeMillis();
//            MDC.put(MonitorConstants.TRACE_ID, traceId);
//            return true;
//        }
//        return false;
//    }
//
//    private String getConstraintViolationMsg(ConstraintViolationException e) {
//        if (e == null || e.getConstraintViolations() == null) {
//            return "null";
//        }
//        Set<String> distinctMsgSet = new HashSet<>();
//        StringBuilder buf = new StringBuilder();
//        for (ConstraintViolation cv : e.getConstraintViolations()) {
//            String message = cv.getMessage();
//            if (distinctMsgSet.add(message)) {
//                buf.append(message).append(";");
//            }
//        }
//        return buf.length() > 0 ? buf.substring(0, buf.length() - 1) : "null";
//    }
//
//    private ResponseObj errorRecordAndReturn(InvokeContext invokeContext, String errorAppendix,
//                                             BizCommonException bizException,
//                                             Throwable rawException) throws Throwable {
//        if (LogLevel.isEnable(LogLevel.ERROR, invokeContext.getReqLogLevel())) {
//            if (rawException != null) {
//                log.error("invoke Method error {} #### Args {} ####  errorMsg {}", invokeContext.getInvokeInterfaceName(),
//                        invokeContext.isRecordParam() ? jsonUtils.toJson(invokeContext.getArgs()) : "--",
//                        bizException.getMessage(), rawException);
//            } else {
//                log.error("invoke Method error {} #### Args {} ####  errorMsg {}", invokeContext.getInvokeInterfaceName(),
//                        invokeContext.isRecordParam() ? jsonUtils.toJson(invokeContext.getArgs()) : "--",
//                        bizException.getMessage());
//            }
//        }
//        QMonitor.recordOne(invokeContext.getMonitorKey() + errorAppendix);
//        if (!invokeContext.isCanReturnResponseObj()) {
//            throw bizException;
//        }
//        return ResponseObj.newFailResp(bizException.getErrorCode(), bizException.getMessage());
//    }
//
//    @Setter
//    @Getter
//    private static class InvokeContext {
//        Class<?> returnType;
//        Object[] args;
//        String invokeInterfaceName; // 不带 "_" 后缀
//        String monitorKey; // 不带 "_" 后缀
//        boolean canReturnResponseObj = false;
//        int reqLogLevel;
//        boolean recordParam = true;
//        boolean recordResult = true;
//    }
//
//}
