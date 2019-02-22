package com.oreilly.cloud.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:sobngwi@gmail.com">Alain SOBNGWI</a>
 */
@Aspect
@Component
public class LoggingRequest {
    Logger log = Logger.getLogger(LoggingRequest.class.getName());


    @Before("execution(* com.oreilly.cloud.*.*.*(..))")
    public void logBefore(JoinPoint joinPoint){
        log.info(String.format("Incoming Request on method %s : %s",
                joinPoint.getSignature(),
                Arrays.toString(joinPoint.getArgs())));
    }
/*org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;*/
@Before("execution(* org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter.*(..))")
public void logBeforeWebSecurityConfigurerAdapter(JoinPoint joinPoint){
    log.info(String.format("Incoming Request on method %s : %s",
            joinPoint.getSignature(),
            Arrays.toString(joinPoint.getArgs())));
}
   /* @AfterReturning(pointcut =
            "execution(* com.in28minutes.rest.webservices.restfulwebservices.user.UserResource.*(..))"
            , returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        log.info("After Method " + joinPoint.getSignature().getName());
        log.info(String.format("Result is  :%s", result));
    }*/

  @AfterThrowing(pointcut =
           "execution(* com.oreilly.cloud.*.*.*(..))",
           throwing = "error")
   public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error){
       log.info(String.format("Method Signature: %s : %s ",
               joinPoint.getSignature(),
               Arrays.toString(joinPoint.getArgs())));
       log.info(String.format("Exception: %s", error));
   }

    @Around(value = "execution(* com.oreilly.cloud.*.*.*(..))")
    public Object computeDuration(ProceedingJoinPoint joinPoint) throws Throwable {

       LocalTime now =  LocalTime.now();
       Object result = joinPoint.proceed();
       LocalTime after = LocalTime.now();
       log.info(String.format("Result Object [%s]", result));
       log.info(String.format("Execution Duration %s : %s is : %s ms",
               joinPoint.getSignature(),
               Arrays.toString(joinPoint.getArgs()),
               Duration.between(now, after).toMillis()));
       return result;
    }

  /*  @Before("execution(* org.springframework.boot.*.*(..))")
    public void logBeforeSpring(JoinPoint joinPoint){
        log.info(String.format("Incoming Request on method %s : %s",
                joinPoint.getSignature(),
                Arrays.toString(joinPoint.getArgs())));
    }*/
}
