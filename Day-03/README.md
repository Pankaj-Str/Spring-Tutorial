# Spring - Bean Life Cycle

In Spring Framework, the lifecycle of a bean refers to the series of steps a bean goes through from its instantiation to its destruction. This lifecycle consists of several phases, during which different methods of the bean can be invoked. Here's an overview of the bean lifecycle phases:

1. **Instantiation**: This is the phase where the bean instance is created. This can be done using constructors or factory methods.

2. **Population of Properties**: Once the bean instance is created, its properties are populated. This can be done using setter methods or directly injecting values.

3. **Initialization**: After the properties are populated, the bean's initialization methods are called. This is where you can perform any necessary setup or initialization tasks.

4. **Custom Initialization**: This phase allows you to execute custom initialization logic using your own methods.

5. **Bean Ready for Use**: At this point, the bean is fully initialized and ready to be used by the application.

6. **Destruction**: When the application is shutting down or the bean is no longer needed, the bean's destruction methods are called. This is where you can perform cleanup tasks.

Let's go through an example to illustrate the bean lifecycle using Spring:

Suppose we have a simple `Person` class with a `name` property:

```java
public class Person {
    private String name;

    public Person() {
        System.out.println("Person: Constructor");
    }

    public void setName(String name) {
        System.out.println("Person: Setting name");
        this.name = name;
    }

    public void init() {
        System.out.println("Person: Custom init method");
    }

    public void destroy() {
        System.out.println("Person: Destroy method");
    }

    @Override
    public String toString() {
        return "Person [name=" + name + "]";
    }
}
```

Now, let's define a Spring configuration XML where we declare the `Person` bean:

```xml
<bean id="personBean" class="com.example.Person" init-method="init" destroy-method="destroy">
    <property name="name" value="John Doe"/>
</bean>
```

In this configuration, we're specifying the bean class, the custom initialization and destruction methods, and setting the `name` property.

When the Spring container starts up, it will follow these steps for the bean lifecycle:

1. The constructor of the `Person` bean is called.
2. The `name` property is set using the setter method.
3. The custom `init` method is called.
4. The bean is now fully initialized and ready to be used.
5. When the Spring container is shutting down, the `destroy` method is called.

You'll see corresponding console outputs at each phase of the bean's lifecycle.

Remember that with modern Spring versions and Java configurations, you can achieve the same lifecycle management using annotations (`@PostConstruct` and `@PreDestroy`) and Java-based configurations instead of XML.
