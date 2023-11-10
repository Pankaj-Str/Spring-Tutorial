# Aspect Oriented Programming with Spring

Aspect-Oriented Programming (AOP) is a programming paradigm that allows you to modularize cross-cutting concerns (such as logging, security, and transactions) in your application. Spring Framework provides support for AOP, allowing you to separate these concerns from your core application logic. A common use case for AOP is implementing logging throughout an application.

Here's an example of how to use AOP in Spring to implement logging using AspectJ annotations:

```yaml
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjtools -->
		<dependency>
		    <groupId>org.aspectj</groupId>
		    <artifactId>aspectjtools</artifactId>
		    <version>1.6.2</version>
		</dependency>

```

1. **Create Logging Aspect:**

Create an aspect class that defines the logging behavior. This class will be responsible for intercepting methods and performing the logging.

```java
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before " + joinPoint.getSignature().getName() + " method");
    }

    @AfterReturning(
        pointcut = "execution(* com.example.service.*.*(..))",
        returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("After " + joinPoint.getSignature().getName() + " method");
        System.out.println("Returned value: " + result);
    }
}
```

In this example, the `@Aspect` annotation marks the class as an aspect. The `@Before` and `@AfterReturning` annotations define the advice that gets executed before and after the methods in the specified pointcut expression.

2. **Create a Service:**

Create a simple service class that will have methods where logging will be applied.

```java
import org.springframework.stereotype.Service;

@Service
public class MyService {

    public String greet(String name) {
        return "Hello, " + name;
    }

    public int add(int a, int b) {
        return a + b;
    }
}
```

3. **Spring Configuration:**

Configure Spring to enable component scanning and load the aspect.

```java
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.example")
@EnableAspectJAutoProxy
public class AppConfig {
}
```

4. **Main Application:**

Create a main class to run the application.

```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(AppConfig.class);

        MyService myService = context.getBean(MyService.class);
        System.out.println(myService.greet("Alice"));
        System.out.println(myService.add(5, 3));

        context.close();
    }
}
```

In this example, the AOP aspect is applied to methods in the `MyService` class. The aspect intercepts method calls before and after execution and performs logging.

When you run the `MainApp`, you should see log output before and after each method call in the `MyService` class, as defined in the `LoggingAspect` class.

Remember that this is a simplified example to demonstrate AOP with Spring. In real-world applications, AOP can be used for more complex scenarios and cross-cutting concerns.
