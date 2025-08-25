/*
 * Custom Annotation By Angula Project
 */

package com.example.angula.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Indicates that an annotated class is a "JobExecutor", originally defined by Domain-Driven
 * Design (Evans, 2025) as "an operation offered as an interface that stands alone in the
 * model, with no encapsulated state."
 *
 * <p>May also indicate that a class is a "Scheduled Jobs Executor"
 * This annotation is only for Angula project
 * and individual teams may narrow their semantics and use as appropriate.
 * 
 * <p>
 * 
 * <p>Usage: 
 * <pre>
 *     {@literal @}JobExecutor
 *     public class MyJobExecutor {
 *         ...
 *     }
 * </pre>
 * </p>
 * 
 * @author Alok Gupta
 * @since now
 * @see Component
 * @see Scheduled
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface JobExecutor {

    String format() default "Running: {class}.{method}() at {timestamp}";
    boolean log() default true;
}

