package codeswithpankaj.com;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* codeswithpankaj.com.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
       // System.out.println("Before " + joinPoint.getSignature().getName() + " method");
    }

    @AfterReturning(
        //pointcut = "execution(* codeswithpankaj.com.service.*.*(..))",
        returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("After " + joinPoint.getSignature().getName() + " method");
        System.out.println("Returned value: " + result);
    }
}