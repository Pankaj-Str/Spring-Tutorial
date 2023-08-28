# Spring - Bean Life Cycle

The lifecycle of a bean refers to the sequence of steps that a bean goes through from its instantiation to its eventual disposal. Spring provides hooks and callback methods that allow you to interact with a bean's lifecycle at various stages. Here's an overview of the bean lifecycle stages and the corresponding callback methods:

1. **Instantiation**: The bean is created, and its constructor is called.

2. **Population of Properties**: Setter methods and dependency injections are performed on the bean.

3. **Initialization**: Any custom initialization logic can be performed here. Spring provides two main callback methods for initialization:

   - `@PostConstruct`: Annotated method that is invoked after the bean has been fully constructed and all properties are set.
   - `InitializingBean` interface: Implementing the `afterPropertiesSet` method allows you to define initialization logic.

4. **In Use**: The bean is ready to be used by other beans or components.

5. **Destruction**: The bean is no longer needed, and it's about to be removed from the container. Spring provides two main callback methods for destruction:

   - `@PreDestroy`: Annotated method that is invoked before the bean is destroyed.
   - `DisposableBean` interface: Implementing the `destroy` method allows you to define destruction logic.

Here's an example that demonstrates the bean lifecycle callbacks:

Step 1: Set up your project structure

Create a new directory for your project and navigate to it in your terminal. Then, create the following directory structure:

```
bean-lifecycle-example
└── src
    └── main
        └── java
            └── com
                └── example
                    ├── BeanLifecycleApp.java
                    └── MyBean.java
```

Step 2: Write the code

Create the `BeanLifecycleApp.java` file in the `com.example` package:

```java
package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanLifecycleApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        MyBean myBean = context.getBean(MyBean.class);
        System.out.println("Using MyBean...");

        context.close();
    }

}
```

Create the `AppConfig.java` file in the same package:

```java
package com.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}
```

Create the `MyBean.java` file in the same package:

```java
package com.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class MyBean {

    @PostConstruct
    public void init() {
        System.out.println("Initializing MyBean...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroying MyBean...");
    }
}
```

Step 3: Build and Run

Compile the code using your preferred Java compiler or IDE. After compiling, run the `BeanLifecycleApp` class. It will create an instance of the `MyBean` bean. You'll see the output messages from the `@PostConstruct` and `@PreDestroy` methods indicating the initialization and destruction stages of the bean's lifecycle.

In this example, the `MyBean` class is annotated with `@Component`, which makes it a Spring-managed bean. The `@PostConstruct` and `@PreDestroy` annotations mark methods to be invoked during the corresponding lifecycle stages. When the Spring context is closed, the `@PreDestroy` method of the bean is called.

Keep in mind that Spring also supports the `InitializingBean` and `DisposableBean` interfaces for defining initialization and destruction methods, respectively.
