package com.yrgo.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class PerformanceTimingAdvice {

    //before
    public Object performTimingMeasurement(ProceedingJoinPoint method)
            throws Throwable {

        long startTime = System.nanoTime();

        try {
            //proceed to target
            Object value = method.proceed();
            return value;
        } finally {
            //after
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            String methodName = method.getSignature().getName();
            String className = method.getTarget().getClass().getName();
            System.out.println("Time taken for the method " +
                    methodName + "from the class " + className + " took " + timeTaken
                    / 1000000 + "ms");
        }
    }
}
