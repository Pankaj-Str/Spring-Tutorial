# Spring - Bean Scopes Example

In Spring, bean scopes define the lifecycle and visibility of beans within the container. Here are some common bean scopes in Spring:

1. **Singleton Scope (default):** Spring creates a single instance of the bean and reuses it whenever the bean is requested. It is the default scope for Spring beans.

2. **Prototype Scope:** Spring creates a new instance of the bean each time it is requested. This is useful when you want to create a new object every time the bean is used.

3. **Request Scope:** A new bean instance is created for each HTTP request when used in a web application. This scope is only valid in the context of a web-aware Spring ApplicationContext.

4. **Session Scope:** Similar to request scope, but a new bean instance is created for each HTTP session.

5. **Global Session Scope:** Used in a web application with a global session. It creates a single instance of the bean for the entire web application's lifecycle.

Here's an example that demonstrates these scopes using the Singleton and Prototype scopes. For this example, I'll focus on the Singleton and Prototype scopes.

Step 1: Set up your project structure

Create a new directory for your project and navigate to it in your terminal. Then, create the following directory structure:

```
bean-scopes-example
└── src
    └── main
        └── java
            └── com
                └── example
                    ├── BeanScopesApp.java
                    ├── AppConfig.java
                    ├── SingletonBean.java
                    └── PrototypeBean.java
```

Step 2: Write the code

Create the `BeanScopesApp.java` file in the `com.example` package:

```java
package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScopesApp {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        SingletonBean singleton1 = context.getBean(SingletonBean.class);
        SingletonBean singleton2 = context.getBean(SingletonBean.class);

        System.out.println("Are singleton beans the same? " + (singleton1 == singleton2));

        PrototypeBean prototype1 = context.getBean(PrototypeBean.class);
        PrototypeBean prototype2 = context.getBean(PrototypeBean.class);

        System.out.println("Are prototype beans the same? " + (prototype1 == prototype2));
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

Create the `SingletonBean.java` file in the same package:

```java
package com.example;

import org.springframework.stereotype.Component;

@Component
public class SingletonBean {
    public SingletonBean() {
        System.out.println("SingletonBean instance created.");
    }
}
```

Create the `PrototypeBean.java` file in the same package:

```java
package com.example;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PrototypeBean {
    public PrototypeBean() {
        System.out.println("PrototypeBean instance created.");
    }
}
```

Step 3: Build and Run

Compile the code using your preferred Java compiler or IDE. After compiling, run the `BeanScopesApp` class. It will create instances of the `SingletonBean` and `PrototypeBean` beans and demonstrate the differences between their scopes.

In the output, you'll see that the Singleton beans are the same instance, while the Prototype beans are different instances. This showcases the behavior of these two different bean scopes.
