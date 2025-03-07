package com.paysyslabs.employment_management.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.paysyslabs.employment_management.service.*.*(..)) || execution(* com.paysyslabs.employment_management.controller.*.*(..))")
    public void applicationMethods() {}

    @Before("applicationMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(value = "applicationMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Exiting: {} with result: {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "applicationMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        logger.error("Exception in: {} with message: {}", joinPoint.getSignature(), exception.getMessage());
    }
}
