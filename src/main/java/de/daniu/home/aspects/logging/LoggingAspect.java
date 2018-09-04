package de.daniu.home.aspects.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Configuration
public class LoggingAspect {
    @Pointcut("@target(org.springframework.stereotype.Service)")
    public void serviceMethods() {}

    @Before("serviceMethods() && within(de.daniu.home..*)")
    public void before(JoinPoint joinPoint){
        //Advice
        Object target = joinPoint.getTarget();
        Logger logger = LoggerFactory.getLogger(target.getClass());
        String methodName = joinPoint.getSignature().getName();
        String argString = Arrays.deepToString(joinPoint.getArgs());

        logger.info("Calling {}#{}({})", target.getClass().getSimpleName(), methodName, argString);
    }
}
