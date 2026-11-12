package com.site.hospital_management.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation used as an AOP Pointcut target.
 * Any method annotated with @Loggable will be intercepted
 * by the LoggingAspect via the @annotation() PCD.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
}

