package com.site.hospital_management.Aspect;

import com.site.hospital_management.annotation.Loggable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * LoggingAspect — demonstrates:
 *
 *  1.  @annotation() PCD — matches methods annotated with a custom annotation (@Loggable)
 *  2.  @Order(1)         — this aspect fires BEFORE MyAspect (@Order(2))
 *  3.  Multiple aspects  — Spring applies all matching aspects in order
 *
 * Any service method annotated with @Loggable will be intercepted here.
 */
@Aspect
@Component
@Order(1)   // Runs FIRST among all aspects (lower number = higher priority)
public class LoggingAspect {

    /**
     * Pointcut using @annotation() PCD:
     * Matches any method annotated with @Loggable, regardless of class/package.
     */
    @Pointcut("@annotation(com.site.hospital_management.annotation.Loggable)")
    public void loggableMethods() {}

    /**
     * @Before on @annotation() pointcut:
     * Logs method name and arguments for any @Loggable method.
     */
    @Before("loggableMethods()")
    public void logBeforeLoggableMethod(JoinPoint joinPoint) {
        System.out.println("[LoggingAspect @Before @Loggable] Calling: "
                + joinPoint.getSignature().toShortString()
                + " | args: " + Arrays.toString(joinPoint.getArgs()));
    }
}

