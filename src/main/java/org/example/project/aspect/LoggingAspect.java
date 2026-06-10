package org.example.project.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // Log thời gian thực hiện cho tất cả controller
    @Around("execution(* org.example.project.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();

        log.info("[START] {}", methodName);

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - startTime;
        log.info("[END] {} - Execution time: {} ms", methodName, executionTime);

        return result;
    }

    // Audit Log cho chuyển tiền
    @Around("execution(* org.example.project.controller.TransactionController.transfer(..))")
    public Object auditTransfer(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[AUDIT] Chuyển tiền bắt đầu lúc: {}", LocalDateTime.now());

        Object result = joinPoint.proceed();

        log.info("[AUDIT] Chuyển tiền HOÀN TẤT lúc: {}", LocalDateTime.now());
        return result;
    }
}