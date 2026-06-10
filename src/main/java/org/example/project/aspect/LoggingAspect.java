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

    // Log thời gian thực hiện cho tất cả controller methods
    @Around("execution(* org.example.project.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        log.info("[START] {} - Time: {}", methodName, LocalDateTime.now());

        Object result;
        try {
            result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("[END] {} - Execution time: {} ms - Time: {}", methodName, executionTime, LocalDateTime.now());
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("[ERROR] {} - Execution time: {} ms - Error: {}", methodName, executionTime, e.getMessage());
            throw e;
        }
    }

    // Audit Log đặc biệt cho chuyển tiền (theo yêu cầu SRS)
    @Around("execution(* org.example.project.controller.TransactionController.transfer(..))")
    public Object auditTransfer(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[AUDIT] Bắt đầu giao dịch chuyển tiền lúc: {}", LocalDateTime.now());

        Object result = joinPoint.proceed();

        log.info("[AUDIT] Giao dịch chuyển tiền HOÀN TẤT thành công lúc: {}", LocalDateTime.now());
        return result;
    }
}