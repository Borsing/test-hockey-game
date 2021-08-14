package com.maplr.testhockeygame.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspectConfiguration {

    /**
     * Get logger of the current joinPoint
     */
    private Logger logger(final JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    /**
     * Log Request and response from controller layer
     */
    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        var logger = logger(joinPoint);
        var methodRequested = joinPoint.getSignature().getName();
        var methodArgs = Arrays.toString(joinPoint.getArgs());

        logger.info("Request on method {} with args {}", methodRequested, methodArgs);
        Object result = joinPoint.proceed();
        logger.info("Response {} on request {}", result, methodRequested);
        return result;
    }
}
