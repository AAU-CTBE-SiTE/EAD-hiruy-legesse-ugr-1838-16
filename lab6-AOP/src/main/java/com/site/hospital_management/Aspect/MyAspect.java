package com.site.hospital_management.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * MyAspect — demonstrates all core Spring AOP concepts:
 *
 *  1.  @Aspect + @Component          — declaring an aspect
 *  2.  @Pointcut                     — reusable pointcut expressions
 *  3.  Pointcut Designators (PCDs):
 *        execution()                 — matches method signatures
 *        within()                    — matches types in a package
 *  4.  @Before                       — runs before the matched method
 *  5.  @After                        — runs after (finally — always runs)
 *  6.  @AfterReturning (returning=)  — runs only on successful return; binds return value
 *  7.  @AfterThrowing  (throwing=)   — runs only when exception is thrown; binds exception
 *  8.  @Around                       — wraps method; full control via ProceedingJoinPoint
 *  9.  JoinPoint API                 — access method name, args, target, signature
 * 10.  @Order                        — controls aspect execution order when multiple aspects apply
 */
@Aspect
@Component
@Order(2)   // This aspect runs second (LoggingAspect has @Order(1))
public class MyAspect {

    // =========================================================================
    // POINTCUT DEFINITIONS
    // =========================================================================

    /**
     * Pointcut 1 — execution() PCD:
     * Matches any method in any class inside the controller package.
     */
    @Pointcut("execution(* com.site.hospital_management.controller.*.*(..))")
    public void controllerMethods() {}

    /**
     * Pointcut 2 — execution() PCD:
     * Matches any method in any class inside the service package.
     */
    @Pointcut("execution(* com.site.hospital_management.service.*.*(..))")
    public void serviceMethods() {}

    /**
     * Pointcut 3 — within() PCD:
     * Matches ALL methods within the entire hospital_management package tree.
     */
    @Pointcut("within(com.site.hospital_management..*)")
    public void withinApplication() {}

    /**
     * Pointcut 4 — Composed pointcut (||):
     * Matches controller OR service methods.
     */
    @Pointcut("controllerMethods() || serviceMethods()")
    public void applicationMethods() {}

    // =========================================================================
    // ADVICE TYPES
    // =========================================================================

    /**
     * @Before — runs BEFORE the method executes.
     * Demonstrates JoinPoint API: method name + arguments.
     */
    @Before("controllerMethods()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className  = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args     = joinPoint.getArgs();

        System.out.println("[@Before] About to execute: " + className + "." + methodName
                + "() | args: " + Arrays.toString(args));
    }

    /**
     * @After — runs AFTER the method (like finally — always, success or exception).
     * Demonstrates post-method cleanup / audit logging.
     */
    @After("controllerMethods()")
    public void afterControllerMethod(JoinPoint joinPoint) {
        System.out.println("[@After]  Finished executing: "
                + joinPoint.getSignature().toShortString());
    }

    /**
     * @AfterReturning — runs ONLY when the method returns successfully.
     * 'returning' attribute binds the actual return value to 'result'.
     */
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void afterServiceReturning(JoinPoint joinPoint, Object result) {
        System.out.println("[@AfterReturning] " + joinPoint.getSignature().getName()
                + "() returned: " + result);
    }

    /**
     * @AfterThrowing — runs ONLY when the method throws an exception.
     * 'throwing' attribute binds the exception to 'ex'.
     */
    @AfterThrowing(pointcut = "applicationMethods()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        System.out.println("[@AfterThrowing] Exception in "
                + joinPoint.getSignature().toShortString()
                + " | Message: " + ex.getMessage());
    }

    /**
     * @Around — wraps the entire method execution.
     * Demonstrates: timing, modify args, skip execution, modify return value.
     * ProceedingJoinPoint.proceed() actually calls the real method.
     */
    @Around("serviceMethods()")
    public Object aroundServiceMethod(ProceedingJoinPoint pjp) throws Throwable {
        String method = pjp.getSignature().toShortString();
        long start = System.currentTimeMillis();

        System.out.println("[@Around] START — " + method);
        try {
            Object result = pjp.proceed();          // ← invoke real method
            long elapsed = System.currentTimeMillis() - start;
            System.out.println("[@Around] END   — " + method
                    + " | took " + elapsed + " ms");
            return result;
        } catch (Throwable ex) {
            System.out.println("[@Around] EXCEPTION in " + method
                    + " → " + ex.getMessage());
            throw ex;   // re-throw so @AfterThrowing also fires
        }
    }
}
