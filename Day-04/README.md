# Aspect-Oriented Programming (AOP) with Spring

## 1. What is Aspect-Oriented Programming (AOP)?

Aspect-Oriented Programming (AOP) is a programming technique that separates common functionality from the main business logic.

Suppose you are developing a banking application. You have multiple methods:

- `deposit()` — deposit money
- `withdraw()` — withdraw money
- `checkBalance()` — check account balance

You want to print a log message whenever any of these methods executes.

Without AOP, you might write logging code inside every method. This creates duplicate code.

With AOP, you can write the logging code once and apply it to multiple methods automatically.

## Without AOP vs. With AOP

#### Without AOP

Logging code is repeated in every business method.

#### With AOP

Logging is written once in an aspect and applied to selected methods.

Common use cases of Spring AOP include:

- Logging
- Performance monitoring
- Security checks
- Transaction management
- Exception monitoring
- Auditing

## 2. Understand the important AOP terms

| Term          | Meaning                                                 | Example                           |
| ------------- | ------------------------------------------------------- | --------------------------------- |
| Aspect        | A class containing cross-cutting logic                  | `LoggingAspect`                   |
| Advice        | Code executed at a particular point                     | Print a log before a method       |
| Join point    | A point during program execution where advice can apply | A method execution                |
| Pointcut      | An expression that selects methods                      | Select all service methods        |
| Target object | The actual object whose method is being intercepted     | `StudentService`                  |
| Proxy         | An object Spring uses to intercept method calls         | A proxy around `StudentService`   |
| Weaving       | Connecting the aspect with the target behavior          | Applying logging to service calls |

In Spring AOP, method executions on Spring-managed beans are the main join points you work with.

### Types of advice

- `@Before` — executes before the target method.
- `@After` — executes after the target method finishes, whether it returns normally or throws an exception.
- `@AfterReturning` — executes when the target method returns successfully.
- `@AfterThrowing` — executes when the target method throws an exception.
- `@Around` — surrounds the target method and can control whether and when it executes.

We will implement these concepts step by step.

## 3. Create a Spring Boot project

We will create a project named `spring-aop-demo` using Java, Maven, and Spring Boot.

### Step 1: Open Spring Initializr

Open the official website:

[Spring Initializr — Create Spring Boot Project](https://start.spring.io)

Select these settings:

| Setting     | Value                  |
| ----------- | ---------------------- |
| Project     | Maven                  |
| Language    | Java                   |
| Spring Boot | A stable 3.5.x release |
| Group       | `com.example`          |
| Artifact    | `spring-aop-demo`      |
| Name        | `spring-aop-demo`      |
| Packaging   | Jar                    |
| Java        | 17                     |
| Dependency  | Spring AOP             |

Click Generate, download the ZIP file, and extract it.

<img width="2878" height="2082" alt="web-selected" src="https://github.com/user-attachments/assets/30abe841-74f4-482c-8cf2-7dd5953d0374" />


[docs.spring.io](https://docs.spring.io/initializr/docs/0.9.2/reference/html)

If Spring Initializr no longer offers the selected version, choose a compatible stable Spring Boot release available there. The code below uses Java 17 and Spring Boot 3.x conventions.

### Step 2: Open the project in your IDE

Open the extracted folder in IntelliJ IDEA, Eclipse, or VS Code with Java and Maven support.

Your project should contain a structure similar to this:

#### spring-aop-demo

`src/main/java/com/example/aop`

`SpringAopDemoApplication.java`

`service/`

`aspect/`

`src/main/resources/`

`pom.xml`

We will add the service and aspect classes ourselves.

## 4. Configure the Maven dependency

### Step 3: Check `pom.xml`

Open `pom.xml`. Make sure your project includes the AOP starter dependency. If you created the project with the Spring AOP dependency selected, it should already be present.

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

If you want to use the following complete Maven configuration, replace your `pom.xml` with this version:

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="
         http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.example</groupId>
    <artifactId>spring-aop-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <name>spring-aop-demo</name>
    <description>Spring AOP step-by-step example</description>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

Important: Maven downloads the required AOP libraries through this dependency. You do not need to download AspectJ separately for this example. The Spring AOP starter provides the necessary dependencies.


Wait until Maven finishes downloading dependencies before continuing.

## 5. Create the main Spring Boot class

### Step 4: Create `SpringAopDemoApplication.java`

Location:

`src/main/java/com/example/aop/SpringAopDemoApplication.java`

Write this code:

```
package com.example.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAopDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            SpringAopDemoApplication.class, args
        );
    }
}
```

Explanation:

- `@SpringBootApplication` configures the application and enables component scanning from the package containing this class and its subpackages.
- `SpringApplication.run()` starts the Spring application.
- We will place our service and aspect classes under `com.example.aop`, so Spring can discover them.

The Spring Boot AOP starter automatically configures Spring AOP for this application. You generally do not need to add `@EnableAspectJAutoProxy` separately.

## 6. Create the service class

### Step 5: Create `StudentService.java`

Create a package named `service` under `com.example.aop`.

File location:

`src/main/java/com/example/aop/service/StudentService.java`

```
package com.example.aop.service;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

    public String getStudentDetails() {

        System.out.println(
            "Business Logic: Fetching student details..."
        );

        return "Student Name: Pankaj";
    }

    public void performTask() {

        System.out.println(
            "Business Logic: Performing a task..."
        );
    }

    public void generateError() {

        System.out.println(
            "Business Logic: About to generate an error..."
        );

        throw new RuntimeException(
            "Something went wrong!"
        );
    }
}
```

### Understand this code

`@Service` tells Spring to register `StudentService` as a managed bean.

We have three methods:

| Method                | Purpose                                         |
| --------------------- | ----------------------------------------------- |
| `getStudentDetails()` | Returns student details                         |
| `performTask()`       | Demonstrates a normal method call               |
| `generateError()`     | Throws an exception to demonstrate error advice |

The code inside these methods is called business logic.

Notice that we have not added any logging aspect code to the service class. This is the key idea behind AOP: the service focuses on its business responsibilities, while the aspect handles cross-cutting functionality.

## 7. Create the Aspect class

### Step 6: Create `LoggingAspect.java`

Create another package named `aspect` under `com.example.aop`.

File location:

`src/main/java/com/example/aop/aspect/LoggingAspect.java`

Add the following code:

```
package com.example.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // 1. Before Advice
    @Before("execution(* com.example.aop.service.*.*(..))")
    public void logBefore() {

        System.out.println(
            "[BEFORE] Method execution is starting."
        );
    }

    // 2. After Returning Advice
    @AfterReturning(
        pointcut = "execution(* com.example.aop.service.*.*(..))",
        returning = "result"
    )
    public void logAfterReturning(Object result) {

        System.out.println(
            "[AFTER RETURNING] Result: " + result
        );
    }

    // 3. After Throwing Advice
    @AfterThrowing(
        pointcut = "execution(* com.example.aop.service.*.*(..))",
        throwing = "exception"
    )
    public void logAfterThrowing(Throwable exception) {

        System.out.println(
            "[AFTER THROWING] Error: "
            + exception.getMessage()
        );
    }

    // 4. After Advice
    @After("execution(* com.example.aop.service.*.*(..))")
    public void logAfter() {

        System.out.println(
            "[AFTER] Method execution has finished."
        );
    }

    // 5. Around Advice
    @Around("execution(* com.example.aop.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint)
            throws Throwable {

        System.out.println(
            "[AROUND] Before: "
            + joinPoint.getSignature().getName()
        );

        long startTime = System.nanoTime();

        try {
            // Execute the original business method
            Object result = joinPoint.proceed();

            return result;

        } finally {
            long endTime = System.nanoTime();

            System.out.println(
                "[AROUND] Execution time: "
                + (endTime - startTime)
                + " nanoseconds"
            );
        }
    }
}
```

### Understand each part

### 1\. `@Aspect`

```
@Aspect
```

Marks the class as an aspect. It tells Spring that this class contains advice and pointcut declarations.

### 2\. `@Component`

```
@Component
```

Registers the aspect as a Spring-managed bean. `@Aspect` alone does not make the class discoverable through component scanning.

Spring Documentation

### 3\. The pointcut expression

```
execution(* com.example.aop.service.*.*(..))
```

This expression selects method executions in classes directly inside `com.example.aop.service`.

Breakdown:

| Expression                | Meaning                          |
| ------------------------- | -------------------------------- |
| `execution`               | Match method executions          |
| First `*`                 | Any return type                  |
| `com.example.aop.service` | The service package              |
| Second `*`                | Any class in that package        |
| Third `*`                 | Any method name                  |
| `(..)`                    | Any number and type of arguments |

The same pointcut is used for all five advice types in this demonstration. This means that calling any of our three service methods triggers the matching advice.

### 4\. `joinPoint.proceed()`

```
Object result = joinPoint.proceed();
```

This is essential in our `@Around` advice. It allows the original business method to execute. Without calling `proceed()`, the original method would not run.

The `finally` block measures elapsed time even if the method throws an exception



## 8. Run the application automatically

### Step 7: Update the main class

We now need to call the service methods so we can see AOP working.

Replace the contents of `SpringAopDemoApplication.java` with this complete version:

```
package com.example.aop;

import com.example.aop.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAopDemoApplication
        implements CommandLineRunner {

    private final StudentService studentService;

    public SpringAopDemoApplication(
            StudentService studentService) {

        this.studentService = studentService;
    }

    public static void main(String[] args) {

        SpringApplication.run(
            SpringAopDemoApplication.class, args
        );
    }

    @Override
    public void run(String... args) {

        System.out.println("\n--- Example 1: Successful Method ---");

        String student = studentService.getStudentDetails();

        System.out.println("Main Result: " + student);

        System.out.println("\n--- Example 2: Normal Void Method ---");

        studentService.performTask();

        System.out.println("\n--- Example 3: Exception Method ---");

        try {
            studentService.generateError();
        } catch (RuntimeException exception) {

            System.out.println(
                "Main caught exception: "
                + exception.getMessage()
            );
        }
    }
}
```

### Why use `CommandLineRunner`?

`CommandLineRunner` allows us to execute code after Spring Boot has started.

We inject `StudentService` through its constructor, and Spring supplies the managed service instance. Calls through this Spring-managed bean can pass through the AOP proxy.

This is important because Spring AOP generally intercepts calls made through the proxy; direct calls to a method from within the same object do not normally pass through that proxy.

## 9. Final project structure

Check that your project now looks like this:

spring-aop-demo

`src/main/java/com/example/aop/`

`SpringAopDemoApplication.java`

`service/`

`StudentService.java`

`aspect/`

`LoggingAspect.java`

`src/main/resources/`

`pom.xml`

## 10. Run the Spring Boot application

### Step 8: Execute the application

- Option A — IntelliJ IDEA or Eclipse

Open `SpringAopDemoApplication.java` and run its `main()` method.

- Option B — Terminal

Open a terminal in the root project folder and run:

For Windows:

```
mvnw.cmd spring-boot:run
```

For macOS or Linux:

```
./mvnw spring-boot:run
```

If you have Maven installed globally, you can also run:

```
mvn spring-boot:run
```

Wait for Spring Boot to finish starting.

## 11. Understand the output

The exact ordering of advice messages can vary because we have applied multiple advice types to the same method. The following is an illustrative output showing the important behavior:

```
--- Example 1: Successful Method ---

[AROUND] Before: getStudentDetails
[BEFORE] Method execution is starting.
Business Logic: Fetching student details...
[AFTER RETURNING] Result: Student Name: Pankaj
[AFTER] Method execution has finished.
[AROUND] Execution time: 125000 nanoseconds
Main Result: Student Name: Pankaj

--- Example 2: Normal Void Method ---

[AROUND] Before: performTask
[BEFORE] Method execution is starting.
Business Logic: Performing a task...
[AFTER RETURNING] Result: null
[AFTER] Method execution has finished.
[AROUND] Execution time: 95000 nanoseconds

--- Example 3: Exception Method ---

[AROUND] Before: generateError
[BEFORE] Method execution is starting.
Business Logic: About to generate an error...
[AFTER THROWING] Error: Something went wrong!
[AFTER] Method execution has finished.
[AROUND] Execution time: 80000 nanoseconds
Main caught exception: Something went wrong!
```

The timing values are examples; your actual output will be different. Also, because the around advice and the other advice types overlap, the precise order of the inner advice messages is not guaranteed by this example.

### What happened?

## Successful method call

1. Spring intercepts the service call through its proxy.
2. `@Around` starts measuring execution time.
3. `@Before` prints a message before business logic executes.
4. `getStudentDetails()` executes and returns student details.
5. `@AfterReturning` receives the successful result.
6. `@After` runs, and `@Around` finishes measuring time.

For the exception method, `@AfterReturning` does not run because the method throws an exception. Instead, `@AfterThrowing` handles the exceptional exit, and `@After` still runs.

The exception is then caught by the `try-catch` block in the main application.

## 12. Learn the five advice types individually

Here is a quick revision of the five annotations used in the example.

### 1. `@Before`

Runs before the target method.

```
@Before("execution(* com.example.aop.service.*.*(..))")
public void before() {
    System.out.println("Before method");
}
```

### 2. `@AfterReturning`

Runs only after a successful return.

```
@AfterReturning(
    pointcut = "execution(* com.example.aop.service.*.*(..))",
    returning = "result"
)
public void afterReturning(Object result) {
    System.out.println(result);
}
```

### 3. `@AfterThrowing`

Runs when the target method throws an exception.

```
@AfterThrowing(
    pointcut = "execution(* com.example.aop.service.*.*(..))",
    throwing = "exception"
)
public void afterThrowing(Throwable exception) {
    System.out.println(exception.getMessage());
}
```

### 4. `@After`

Runs after the method exits, whether normally or exceptionally.

```
@After("execution(* com.example.aop.service.*.*(..))")
public void after() {
    System.out.println("Method finished");
}
```

### 5. `@Around`

Runs before and after the target invocation and can control its execution.

```
@Around("execution(* com.example.aop.service.*.*(..))")
public Object around(ProceedingJoinPoint joinPoint)
        throws Throwable {
    System.out.println("Before");
    try {
        return joinPoint.proceed();
    } finally {
        System.out.println("After");
    }
}
```

## 13. Common errors and their solutions

| Error                                         | Solution                                                                                |
| --------------------------------------------- | --------------------------------------------------------------------------------------- |
| `package org.aspectj.lang does not exist`     | Verify `spring-boot-starter-aop` is present and Maven dependencies have loaded.         |
| Aspect code never executes                    | Check that `LoggingAspect` has both `@Aspect` and `@Component`.                         |
| Service bean cannot be found                  | Keep the service under `com.example.aop.service`, beneath the main application package. |
| Pointcut does not match                       | Check the package and method pattern in `execution(...)`.                               |
| Business method does not execute              | Check that `@Around` calls `joinPoint.proceed()`.                                       |
| Advice does not run for a self-invoked method | Call the method through a Spring-managed bean rather than using `this.methodName()`.    |





For further study, refer to the official Spring Framework AOP documentation: [Aspect-Oriented Programming with Spring](https://docs.spring.io/spring/reference/core/aop.html).

Next recommended practice: Add a `PaymentService` class and use AOP to log every payment operation without changing the business logic. This will help you understand how AOP is useful in real-world applications.
