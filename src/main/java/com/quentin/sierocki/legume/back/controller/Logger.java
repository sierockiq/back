package com.quentin.sierocki.legume.back.controller;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Logger {
    @AfterThrowing(pointcut = "execution(* com.quentin.sierocki.legume.back.controller..*.*(..))", throwing = "ex")
    public void logError(ControllerException ex) {
        System.out.println(ex.getPathMethod()+ ex.getMessage());
    }

    
}