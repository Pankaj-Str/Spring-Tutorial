# Spring Bean Life Cycle

## 1. What is the Spring Bean Life Cycle?

In Java, we usually create objects using the `new` keyword.

```
Student student = new Student();
```

But in Spring Framework, the Spring container can create and manage objects for us. These managed objects are called Spring Beans.

The Bean Life Cycle describes the different stages a bean goes through, from creation to destruction.

1\. Bean Instantiation

Spring creates the object.

2\. Dependency Injection

Spring provides required dependencies and properties.

3\. Initialization

Spring completes bean setup and initialization callbacks run.

4\. Ready for Use

Your application uses the bean.

5\. Destruction

Spring runs destruction callbacks when the container shuts down or the bean is destroyed.

Note: This is a simplified learning diagram. Spring has additional lifecycle steps, including bean post-processors. Destruction callbacks are normally managed for singleton beans; prototype beans require special cleanup handling.

## 2. What tools do we need?

For this beginner example, we will use:

- Java JDK 17 or later
- IntelliJ IDEA, Eclipse, or another Java IDE
- Maven for dependency management
- Spring Framework 6
- XML configuration to understand the traditional Spring container

We will create a simple Java application, not a Spring Boot application, so you can focus on the core Spring Bean Life Cycle.

## 3. Step 0 – Install Java and create the project

First, check whether Java is installed on your computer.

Open Terminal or Command Prompt and run:

```
java -version
```

You should see a Java version such as `17` or higher. If Java is not installed, download it from [Eclipse Temurin](https://adoptium.net/).

Next, create a Maven project in IntelliJ IDEA or Eclipse.

Use these project settings:

| Setting          | Value                 |
| ---------------- | --------------------- |
| Project name     | `SpringBeanLifeCycle` |
| Language         | Java                  |
| Build tool       | Maven                 |
| JDK              | 17                    |
| Spring Framework | 6.2.x                 |
| Packaging        | JAR                   |

## 4. Step 1 – Create the project folder structure

Create the following files and folders exactly as shown.

```
SpringBeanLifeCycle/
│
├── pom.xml
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── cwp/
        │           ├── Student.java
        │           ├── MyBeanPostProcessor.java
        │           └── MainApp.java
        │
        └── resources/
            └── beans.xml
```

We will use these files:

- `pom.xml` – adds Spring dependencies.
- `Student.java` – defines our Spring bean.
- `MyBeanPostProcessor.java` – observes the bean before and after initialization.
- `beans.xml` – configures the bean.
- `MainApp.java` – starts the Spring container and uses the bean.

## 5. Step 2 – Configure Maven dependencies

Open `pom.xml` and write the complete code below.

```

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.cwp</groupId>
    <artifactId>SpringBeanLifeCycle</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.release>17</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>6.2.12</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.14.1</version>
            </plugin>
        </plugins>
    </build>

</project>

```

Explanation:

- `groupId` identifies the project group.
- `artifactId` is the project name.
- `maven.compiler.release` specifies Java 17.
- `spring-context` provides the Spring application context and bean lifecycle management.
- Maven downloads the required Spring libraries automatically.

Save the file and click Load Maven Changes or Reload Project in your IDE.

## 6. Step 3 – Create the Student bean

Create `Student.java` inside `src/main/java/com/cwp/`.

This class will demonstrate object creation, dependency/property setup, initialization, and destruction.

```

package com.cwp;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.DisposableBean;

public class Student implements BeanNameAware,
        InitializingBean, DisposableBean {

    private String name;
    private int age;

    // Constructor: called when Spring creates the object
    public Student() {
        System.out.println("1. Student object created");
    }

    // Setter methods: Spring uses these to inject properties
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // BeanNameAware callback
    @Override
    public void setBeanName(String beanName) {
        System.out.println("2. Bean name: " + beanName);
    }

    // InitializingBean callback
    @Override
    public void afterPropertiesSet() {
        System.out.println("5. afterPropertiesSet() called");
    }

    // Custom initialization method
    public void init() {
        System.out.println("6. Custom init() method called");
    }

    // Business method
    public void display() {
        System.out.println("Student Name: " + name);
        System.out.println("Student Age: " + age);
    }

    // DisposableBean callback
    @Override
    public void destroy() {
        System.out.println("9. DisposableBean destroy() called");
    }

    // Custom destruction method
    public void cleanup() {
        System.out.println("10. Custom cleanup() method called");
    }
}

```

### Understand the code

1\. Constructor

```
public Student() {
    System.out.println("1. Student object created");
}
```

Spring creates the object by calling its constructor.

2\. Setter methods

```
public void setName(String name) {
    this.name = name;
}
```

Spring calls these methods to set the student's name and age using our XML configuration.

3\. `BeanNameAware`

```
public void setBeanName(String beanName)
```

Spring calls this method to tell the bean its configured name.

4\. `InitializingBean`

```
public void afterPropertiesSet()
```

Spring calls this after the bean's properties have been set and the relevant aware callbacks have run.

5\. Custom initialization

```
public void init()
```

We will configure Spring to call this method after `afterPropertiesSet()`.

6\. Business method

```
public void display()
```

This is the method we will call to display the student's details.

7\. Destruction methods

```
public void destroy()
public void cleanup()
```

Spring will call these methods during the application's orderly shutdown.

Spring supports these lifecycle callbacks through its bean container.&#x20;




## 7. Step 4 – Create a BeanPostProcessor

A `BeanPostProcessor` allows us to execute code before and after a bean's initialization callbacks.

Create `MyBeanPostProcessor.java` in the same package.

```

package com.cwp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(
            Object bean, String beanName) throws BeansException {

        if (beanName.equals("student")) {
            System.out.println(
                "3. BeanPostProcessor: Before initialization");
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(
            Object bean, String beanName) throws BeansException {

        if (beanName.equals("student")) {
            System.out.println(
                "7. BeanPostProcessor: After initialization");
        }

        return bean;
    }
}

```

### What do these two methods do?

| Method                              | Purpose                                          |
| ----------------------------------- | ------------------------------------------------ |
| `postProcessBeforeInitialization()` | Runs before the bean's initialization callbacks. |
| `postProcessAfterInitialization()`  | Runs after the bean's initialization callbacks.  |

The processor must return the bean, or a suitable replacement, so that Spring can continue working with it. Spring uses post-processors for features such as wrapping beans with proxies.

## 8. Step 5 – Configure the bean in XML

Open `src/main/resources/beans.xml`.

```

<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Register the BeanPostProcessor -->
    <bean id="myBeanPostProcessor"
          class="com.cwp.MyBeanPostProcessor"/>

    <!-- Configure the Student bean -->
    <bean id="student"
          class="com.cwp.Student"
          init-method="init"
          destroy-method="cleanup">

        <property name="name" value="Pankaj"/>
        <property name="age" value="25"/>

    </bean>

</beans>

```

### Understand the XML configuration

Bean ID

```
<bean id="student" class="com.cwp.Student">
```

The bean's name is `student`, and its Java class is `com.cwp.Student`.

Property injection

```
<property name="name" value="Pankaj"/>
<property name="age" value="25"/>
```

Spring sets these values by calling `setName()` and `setAge()`.

Initialization method

```
init-method="init"
```

Spring calls the custom `init()` method during bean initialization.

Destruction method

```
destroy-method="cleanup"
```

Spring calls `cleanup()` when the application context is closed normally.

Spring officially supports these XML lifecycle attributes and the `InitializingBean` and `DisposableBean` interfaces.&#x20;





## 9. Step 6 – Create the Main class

Create `MainApp.java` in the same package.

```

package com.cwp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

    public static void main(String[] args) {

        System.out.println("Application starting...");

        // Step 1: Load Spring configuration
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("beans.xml");

        System.out.println("Spring container is ready.");

        // Step 2: Get the Spring-managed bean
        Student student = context.getBean("student", Student.class);

        // Step 3: Use the bean
        System.out.println("\nStudent Details:");
        student.display();

        // Step 4: Close the Spring container
        System.out.println("\nClosing Spring container...");
        context.close();

        System.out.println("Application finished.");
    }
}

```

### Understand the Main class

1. `ClassPathXmlApplicationContext` loads `beans.xml` and creates the configured singleton beans.
2. `getBean()` retrieves the Spring-managed `Student` object.
3. `display()` prints the student's details.
4. `context.close()` shuts down the container and triggers the configured destruction callbacks.

We explicitly close the context so that you can observe the destruction phase without relying on IDE shutdown behavior.

## 10. Step 7 – Run the complete program

Now run `MainApp.java`.

In IntelliJ IDEA or Eclipse:

1. Make sure Maven has downloaded the dependencies.
2. Open `MainApp.java`.
3. Right-click and select Run `MainApp.main()`.
4. Check the Console output.

### Expected output

```
Application starting...
1. Student object created
2. Bean name: student
3. BeanPostProcessor: Before initialization
5. afterPropertiesSet() called
6. Custom init() method called
7. BeanPostProcessor: After initialization
Spring container is ready.

Student Details:
Student Name: Pankaj
Student Age: 25

Closing Spring container...
9. DisposableBean destroy() called
10. Custom cleanup() method called
Application finished.
```

The numbers in the output are labels from the example code; they are not Spring's official lifecycle step numbers.

## 11. Step 8 – Understand the complete execution order

Let's understand what happens internally when you run the program.

<img width="701" height="643" alt="Screenshot 2026-10-09 at 1 52 10 PM" src="https://github.com/user-attachments/assets/25328ce8-2e2c-4353-a0b4-ec618eacf85c" />



This is the lifecycle order demonstrated by our example. The actual Spring lifecycle includes additional callbacks when you use other interfaces or annotations.&#x20;




## 12. Step 9 – Learn the important lifecycle methods

| Method                              | When does it run?                                    |
| ----------------------------------- | ---------------------------------------------------- |
| Constructor                         | When Spring creates the bean object                  |
| `setBeanName()`                     | When Spring supplies the bean's configured name      |
| `postProcessBeforeInitialization()` | Before initialization callbacks                      |
| `@PostConstruct`                    | During initialization, before `afterPropertiesSet()` |
| `afterPropertiesSet()`              | After properties have been set                       |
| Custom `init()`                     | After the preceding initialization callbacks         |
| `postProcessAfterInitialization()`  | After initialization callbacks                       |
| `@PreDestroy`                       | During destruction, before `destroy()`               |
| `destroy()`                         | When the container destroys the singleton bean       |
| Custom `cleanup()`                  | After the `DisposableBean.destroy()` callback        |

The annotation-based callbacks, `@PostConstruct` and `@PreDestroy`, are also common in modern Spring applications. They are alternatives to, or can be combined with, the other lifecycle mechanisms.&#x20;




## 13. Step 10 – What happens if we do not close the Spring container?

Consider this line:

```
context.close();
```

It tells Spring that the application context is closing. Spring can then call the destruction callbacks of managed singleton beans.

If you remove that line, your program might finish without displaying:

```
9. DisposableBean destroy() called
10. Custom cleanup() method called
```

For a standalone Java application, you can also register a shutdown hook:

```
context.registerShutdownHook();
```

Call this after creating the context if you want the JVM to request a graceful context shutdown when it exits. Do not rely on the shutdown hook for deterministic cleanup during normal program execution; explicitly closing the context is useful for this tutorial.&#x20;




## 14. Step 11 – Practice questions for beginners

1\. What is a Spring Bean?

A Java object managed by Spring

A database table

A Java package

2\. Which method runs after the bean's properties have been set?

cleanup()

afterPropertiesSet()

main()

3\. Which XML attribute configures a custom initialization method?

start-method

init-method

create-method

4\. Which method is called when context.close() destroys this singleton bean?

display()

setName()

destroy()

Reset quiz

## 15. Final summary

You have now created a complete beginner Spring project demonstrating the Bean Life Cycle.

Remember these three important points:

1. Initialization: Spring creates the bean, injects properties, runs initialization callbacks, and finishes post-processing.
2. Usage: Your application accesses the initialized bean through the Spring container.
3. Destruction: Spring invokes registered cleanup callbacks when the application context closes normally.

For further reading, see the official [Spring Framework – Customizing the Nature of a Bean](https://docs.spring.io/spring-framework/reference/6.2/core/beans/factory-nature.html) documentation.
