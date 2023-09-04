# How to insert data into a MySQL table using Spring Data JPA.
including all the necessary steps:

Step 1: **Set up Your Project**

Create a new Spring Boot project using your preferred development environment (e.g., Spring Initializr or your IDE).

![Screenshot 2023-09-04 100736](https://github.com/Pankaj-Str/Spring-Tutorial/assets/36913690/06f73722-59ef-4e5e-a880-b2f517875ad9)


Step 2: **Configure Your Database**

In the `src/main/resources/application.properties` file, configure your MySQL database connection properties:

![image](https://github.com/Pankaj-Str/Spring-Tutorial/assets/36913690/c6284969-cbb9-4dba-9c3a-8c2f05198e8b)

```yaml
SpringJDBCMysql\src\main\resources\application.properties
```
```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/cwp
spring.datasource.username=root
spring.datasource.password=admin
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

```

Step 3: **Create an Entity Class**

Create an entity class representing the data you want to insert into the database. For example, let's say you have a "Employee" entity:

```yaml
path = SpringJDBCMysql\src\main\java\in\p4n\Employee.java
```

```java
package in.p4n;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee {

   @Id
   private Integer empId;
   private String empName;
   private Double empSal;
   private String empDept;

   public Employee() {

   }

   public Employee(Integer empId, String empName, Double empSal, String empDept) {
      super();
      this.empId = empId;
      this.empName = empName;
      this.empSal = empSal;
      this.empDept = empDept;
   }

   public Integer getEmpId() {
      return empId;
   }
   public void setEmpId(Integer empId) {
      this.empId = empId;
   }
   public String getEmpName() {
      return empName;
   }
   public void setEmpName(String empName) {
      this.empName = empName;
   }
   public Double getEmpSal() {
      return empSal;
   }
   public void setEmpSal(Double empSal) {
      this.empSal = empSal;
   }
   public String getEmpDept() {
      return empDept;
   }
   public void setEmpDept(String empDept) {
      this.empDept = empDept;
   }

}
```

Step 4: **Create a Repository Interface**

Create a repository interface for your entity by extending `JpaRepository`:

```java
public interface UserRepository extends JpaRepository<User, Long> {
}
```

Step 5: **Create a Service Class**

Create a service class that uses the repository to insert data:

```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User insertUser(User user) {
        return userRepository.save(user);
    }
}
```

Step 6: **Create a Main Application Class**

Create a main application class with a `main` method to run your Spring Boot application:

```java
@SpringBootApplication
public class YourApplicationName {

    public static void main(String[] args) {
        SpringApplication.run(YourApplicationName.class, args);
    }
}
```

Step 7: **Insert Data in Main Method**

You can insert data into the MySQL table when your application starts. Modify the `main` method in your main application class as follows:

```java
@SpringBootApplication
public class YourApplicationName {

    public static void main(String[] args) {
        SpringApplication.run(YourApplicationName.class, args);

        // Insert data
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");

        ApplicationContext context = SpringApplication.run(YourApplicationName.class, args);
        UserService userService = context.getBean(UserService.class);
        User savedUser = userService.insertUser(user);

        System.out.println("User saved with ID: " + savedUser.getId());
    }
}
```

Step 8: **Run Your Application**

Start your Spring Boot application.

When you run your application, it will insert the user data into the "user" table in the MySQL database.

Make sure you have the required dependencies in your `pom.xml` (if using Maven) or `build.gradle` (if using Gradle) to enable Spring Data JPA and Spring Boot.

This complete example covers all the steps necessary to insert data into a MySQL table using Spring Data JPA.
