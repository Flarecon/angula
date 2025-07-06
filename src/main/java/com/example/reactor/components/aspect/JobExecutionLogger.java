package com.example.reactor.components.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.example.angula.annotation.JobExecutor;

@Aspect
@Component
public class JobExecutionLogger {

    @Pointcut("execution(public * *(..)) && @within(com.example.angula.annotation.JobExecutor)")
    public void publicMethodInJobExecutor(){}

    @Before("publicMethodInJobExecutor()")
    public void logBeforeExecution(JoinPoint joinPoint){
        Class<?> targetClass = joinPoint.getTarget().getClass();
        JobExecutor annotation = targetClass.getAnnotation(JobExecutor.class);
        boolean log = annotation.log();

        if(log){
            String format = annotation.format();
            String methodName = joinPoint.getSignature().getName();
            String className = targetClass.getSimpleName();
            
            String logMessage = format
                                .replace("{class}", className)
                                .replace("{method}", methodName)
                                .replace("{timestamp}", LocalDateTime.now().toString());
            System.out.println(logMessage);
        }
    }
}
