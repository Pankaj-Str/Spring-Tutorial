### "Hello, World!" without involving web components or Spring Boot. This example demonstrates a basic Spring application using the core Spring framework.

Step 1: Set up your project structure

Create a new directory for your project and navigate to it in your terminal. Then, create the following directory structure:

```
hello-spring
└── src
    └── main
        └── java
            └── com
                └── example
                    └── HelloSpringApp.java
```

Step 2: Write the code

Create the `HelloSpringApp.java` file in the `com.example` package:

```java
package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloSpringApp {

    public static void main(String[] args) {
        // Create an application context using annotation-based configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Retrieve the HelloWorld bean from the context
        HelloWorld helloWorld = context.getBean(HelloWorld.class);

        // Call the printMessage method to print "Hello, World!"
        helloWorld.printMessage();
    }

}
```

Create another Java file named `AppConfig.java` in the same package to define the bean:

```java
package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public HelloWorld helloWorld() {
        return new HelloWorld();
    }

}
```

Create a Java class named `HelloWorld.java` in the same package to define the bean implementation:

```java
package com.example;

public class HelloWorld {

    public void printMessage() {
        System.out.println("Hello, World!");
    }

}
```

Step 3: Build and Run

Compile the code using your preferred Java compiler or IDE. After compiling, run the `HelloSpringApp` class. It will create a Spring application context, retrieve the `HelloWorld` bean from the context, and call its `printMessage()` method, which will print "Hello, World!" to the console.

Please note that in a more complex Spring application, you would likely have a more intricate configuration and use case. However, this example demonstrates the basics of how to set up a Spring application context and retrieve and use beans from it.
