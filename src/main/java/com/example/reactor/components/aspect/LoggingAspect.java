package com.example.reactor.components.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Pointcut("execution(* com.example.angula.controller.AngulaRestController.*(..))")
    public void angulaRestController() {}

    @Before("@within(org.springframework.stereotype.Controller)")
    public void logControllers() {
        System.out.println("---------------------------\nAspect logging to controllers\n---------------------------");
    }

    @Before("execution(* com.example.reactor.service.SweetService.getSweet(..))")
    public void logSweetService() {
        System.out.println("---------------------------\nAspect logging to sweet service\n---------------------------");
    }

    // @Around Wraps the methpod and is responsible for execution of method
    // and returning the result processed by the method
    @Around("execution(* com.example.angula.controller.UserControlller.*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("---------------------------\nAspect logging to before user controller\n---------------------------");
        Object result = joinPoint.proceed();
        System.out.println("---------------------------\nAspect logging to after user controller\n---------------------------");
        return result;
    }

    @After("angulaRestController()")
    public void logtodo() {
        System.out.println("---------------------------\nAspect logging to todo controller\n---------------------------");
    }

    @AfterReturning(pointcut = "angulaRestController()", returning = "result")
    public void logtodo(Object result) {
        System.out.println("---------------------------\nAspect logging to todo controller returned "+ result +"\n---------------------------");
    }

    @AfterThrowing(pointcut = "angulaRestController()", throwing = "exception")
    public void logtodo(Exception exception) {
        System.out.println("---------------------------\nAspect logging to todo controller threw exception "+ exception +"\n---------------------------");
    }
    
}
