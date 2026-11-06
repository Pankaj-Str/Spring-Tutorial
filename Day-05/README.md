# Spring JDBC Tutorial

Java + Spring JDBC + MySQL


Project: Student Management System

Technologies:

- Java 17
- Spring Framework 6
- Spring JDBC
- JDBC
- MySQL 8
- Maven
- Eclipse IDE

## 1. What is Spring JDBC?

Spring JDBC is a module of the Spring Framework that simplifies database connectivity and database operations in Java applications.

In traditional JDBC, developers need to write repetitive code for:

1. Loading the database driver.
2. Creating a database connection.
3. Creating a PreparedStatement.
4. Executing SQL queries.
5. Processing ResultSet.
6. Closing database resources.
7. Handling SQL exceptions.

Spring JDBC simplifies these operations using a class called `JdbcTemplate`.

### Traditional JDBC vs Spring JDBC

| Traditional JDBC           | Spring JDBC                      |
| -------------------------- | -------------------------------- |
| Manual connection handling | Simplified connection management |
| Manual resource closing    | Automatic resource cleanup       |
| More boilerplate code      | Less boilerplate code            |
| SQLException handling      | Spring DataAccessException       |
| Manual query execution     | JdbcTemplate methods             |

## 2. Why Do We Use Spring JDBC?

Spring JDBC provides several advantages:

- Reduces repetitive JDBC code.
- Simplifies database connectivity.
- Provides automatic resource management.
- Supports PreparedStatement.
- Provides exception translation.
- Supports transaction management.
- Integrates with Spring dependency injection.

## 3. Understanding Spring JDBC Architecture

Our application will follow this architecture:

```
        Java Application
               |
               |
           Main Class
               |
               |
           DAO Layer
               |
               |
          JdbcTemplate
               |
               |
           DataSource
               |
               |
          JDBC Driver
               |
               |
          MySQL Database
```

### Explanation

| Component    | Responsibility                |
| ------------ | ----------------------------- |
| Main Class   | Executes application          |
| DAO          | Performs database operations  |
| JdbcTemplate | Executes SQL queries          |
| DataSource   | Provides database connections |
| JDBC Driver  | Communicates with MySQL       |
| MySQL        | Stores application data       |

## 4. Prerequisites – Install Required Software

Before developing the application, install the following software.

| Software        | Version                   | Purpose               |
| --------------- | ------------------------- | --------------------- |
| JDK             | 17 or above               | Java development      |
| Eclipse IDE     | Latest compatible release | Java coding           |
| MySQL Server    | 8.x                       | Database              |
| MySQL Workbench | Compatible version        | Database management   |
| Maven           | 3.x                       | Dependency management |

Download links:

- [Java JDK](https://www.oracle.com/java/technologies/downloads/?utm_source)
- [Eclipse IDE](https://www.eclipse.org/downloads/?utm_source)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/?utm_source)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/?utm_source)
- [Apache Maven](https://maven.apache.org/download.cgi?utm_source)

## 5. Verify Java Installation

Open Command Prompt.

Execute:

```
java -version
```

Expected output (version may differ):

```
java version "17.0.x"
```

Check Java compiler:

```
javac -version
```

Check Maven:

```
mvn -version
```

If these commands work, your development environment is ready.

## 6. Create Database in MySQL

Now we will create a database for our Student Management System.

### Step 1: Open MySQL Workbench

1. Open MySQL Workbench.
2. Connect to your MySQL server.
3. Open a new SQL query tab.

### Step 2: Create Database

Execute:

```
CREATE DATABASE spring_jdbc_db;
```

### Step 3: Select Database

```
USE spring_jdbc_db;
```

### Step 4: Create Student Table

```
CREATE TABLE students (

    id INT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL,

    email VARCHAR(100) UNIQUE NOT NULL,

    course VARCHAR(100) NOT NULL,

    fees DOUBLE NOT NULL

);
```

### Step 5: Verify Table

```
DESC students;
```

### Step 6: Insert Sample Records

```
INSERT INTO students
(name, email, course, fees)
VALUES
('Pankaj', 'pankaj@gmail.com', 'Java', 15000),
('Rahul', 'rahul@gmail.com', 'Python', 12000),
('Amit', 'amit@gmail.com', 'Spring Boot', 18000);
```

### Step 7: Display Records

```
SELECT * FROM students;
```

Expected records:

| id | name   | email             | course      | fees  |
| -- | ------ | ----------------- | ----------- | ----- |
| 1  | Pankaj | pankaj\@gmail.com | Java        | 15000 |
| 2  | Rahul  | rahul\@gmail.com  | Python      | 12000 |
| 3  | Amit   | amit\@gmail.com   | Spring Boot | 18000 |

Your database is ready.

## 7. Create Maven Project in Eclipse IDE

Now we will create our Spring JDBC application.

### Step 1: Open Eclipse

Launch Eclipse IDE and select your workspace.

### Step 2: Create New Maven Project

Navigate to:

```
File
  |
  New
  |
  Maven Project
```

### Step 3: Select Project Location

Select:

`Create a simple project (skip archetype selection)`

Click Next.

### Step 4: Enter Project Details

| Field       | Value             |
| ----------- | ----------------- |
| Group Id    | com.cwpc          |
| Artifact Id | SpringJDBCExample |
| Version     | 1.0-SNAPSHOT      |
| Packaging   | jar               |

Click Finish.

Your Maven project is now created.

## 8. Understand Maven Project Structure

The initial project will contain `pom.xml`.

We will create the following structure:

```
SpringJDBCExample
│
├── src/main/java
│   │
│   └── com.cwpc
│       │
│       ├── config
│       │   └── AppConfig.java
│       │
│       ├── model
│       │   └── Student.java
│       │
│       ├── dao
│       │   └── StudentDao.java
│       │
│       └── main
│           └── MainApp.java
│
├── src/main/resources
│
├── Maven Dependencies
│
└── pom.xml
```

### Create Packages in Eclipse

1. Right-click `src/main/java`.
2. Select New → Package.
3. Enter `com.cwpc.config`.
4. Click Finish.
5. Repeat for the other three packages.

## 9. Configure Spring JDBC Dependencies

Open the `pom.xml` file.

Replace its content with:

```

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.cwpc</groupId>
    <artifactId>SpringJDBCExample</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.release>17</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <spring.version>6.2.8</spring.version>
    </properties>

    <dependencies>

        <!-- Spring Context -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- Spring JDBC -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- MySQL JDBC Driver -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.3.0</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.14.0</version>
            </plugin>
        </plugins>
    </build>

</project>

```

### Understanding Maven Dependencies

1\. Spring Context

Provides the Spring IoC container and dependency injection functionality.

2\. Spring JDBC

Provides JdbcTemplate and database integration features.

3\. MySQL Connector/J

Provides the JDBC driver required to connect Java applications with MySQL.

### Download Dependencies

After saving `pom.xml`:

1. Right-click the project.
2. Select Maven.
3. Select Update Project.
4. Select your project.
5. Click OK.

Wait until Maven downloads all dependencies.

## 10. Configure Database Connectivity

Now we will connect our Java application with MySQL.

For this example, we will use Java-based Spring configuration.

Create a class:

`AppConfig.java`

Package:

`com.cwpc.config`

Add the following code:

```

package com.cwpc.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.cwpc.dao.StudentDao;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource ds =
                new DriverManagerDataSource();

        ds.setDriverClassName(
            "com.mysql.cj.jdbc.Driver"
        );

        ds.setUrl(
            "jdbc:mysql://localhost:3306/spring_jdbc_db"
        );

        ds.setUsername("root");

        ds.setPassword("your_password");

        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(
            DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }

    @Bean
    public StudentDao studentDao(
            JdbcTemplate jdbcTemplate) {

        return new StudentDao(jdbcTemplate);
    }
}

```

Replace `your_password` with your actual MySQL password.

### Understanding Annotations

@Configuration

Marks the class as a Spring configuration class.

@Bean

Registers the returned object as a Spring-managed bean.

DataSource

Provides database connection details.

DriverManagerDataSource

Creates JDBC connections using the configured credentials. It is useful for a beginner example; production systems should generally use a connection pool.

JdbcTemplate

Executes SQL queries and handles repetitive JDBC resource-management operations.

## 11. Create Student Model Class

Now create a Java class to represent student information.

Create:

`Student.java`

Package:

`com.cwpc.model`

```

package com.cwpc.model;

public class Student {

    private int id;
    private String name;
    private String email;
    private String course;
    private double fees;

    // Default Constructor
    public Student() {
    }

    // Parameterized Constructor
    public Student(String name, String email,
                   String course, double fees) {

        this.name = name;
        this.email = email;
        this.course = course;
        this.fees = fees;
    }

    // Getter and Setter Methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {

        return "Student {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", course='" + course + '\'' +
                ", fees=" + fees +
                '}';
    }
}

```

### Understanding Student Class

| Variable | Data Type | Purpose       |
| -------- | --------- | ------------- |
| id       | int       | Student ID    |
| name     | String    | Student name  |
| email    | String    | Student email |
| course   | String    | Course name   |
| fees     | double    | Course fees   |

We use encapsulation to protect the data using private variables and public getter/setter methods.

## 12. Create Student DAO Class

DAO stands for Data Access Object.

DAO is a design pattern used to separate database-related operations from the main application logic.

In our application, `StudentDao` will communicate with the MySQL database using JdbcTemplate.

Create:

`StudentDao.java`

Package:

`com.cwpc.dao`

### Step 1: Import Required Classes

```
package com.cwpc.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cwpc.model.Student;
```

### Step 2: Create StudentDao Class

```
public class StudentDao {

    private JdbcTemplate jdbcTemplate;

    public StudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
```

Explanation:

- `StudentDao` handles database operations.
- `JdbcTemplate` executes SQL queries.
- Constructor injection provides the JdbcTemplate object.

### Step 3: INSERT Operation – Save Student

Now we will insert a new student record into MySQL.

Add this method inside `StudentDao`.

```
public int saveStudent(Student student) {

    String sql = """
        INSERT INTO students
        (name, email, course, fees)
        VALUES (?, ?, ?, ?)
        """;

    return jdbcTemplate.update(
        sql,
        student.getName(),
        student.getEmail(),
        student.getCourse(),
        student.getFees()
    );
}
```

Explanation:

| Code        | Description                 |
| ----------- | --------------------------- |
| INSERT INTO | Inserts a new record        |
| VALUES      | Provides values for columns |
| ?           | Parameter placeholder       |
| update()    | Executes INSERT statement   |

Example:

```
Student student = new Student(
    "Pankaj",
    "pankaj@gmail.com",
    "Java",
    15000
);

studentDao.saveStudent(student);
```

### Step 4: SELECT Operation – Display All Students

Now we will retrieve all student records from MySQL.

Add this method:

```
public List<Student> getAllStudents() {

    String sql = "SELECT * FROM students";

    return jdbcTemplate.query(sql, (rs, rowNum) -> {

        Student student = new Student();

        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setCourse(rs.getString("course"));
        student.setFees(rs.getDouble("fees"));

        return student;
    });
}
```

Understanding RowMapper:

RowMapper converts database records into Java objects.

| Method      | Purpose               |
| ----------- | --------------------- |
| getInt()    | Retrieves integer     |
| getString() | Retrieves String      |
| getDouble() | Retrieves double      |
| query()     | Executes SELECT query |

Example:

```
List<Student> students = studentDao.getAllStudents();

for (Student s : students) {
    System.out.println(s);
}
```

### Step 5: SELECT BY ID – Search Student

Now we will search for a particular student using the student ID.

Add this method:

```
public Student getStudentById(int id) {

    String sql = "SELECT * FROM students WHERE id = ?";

    List<Student> students = jdbcTemplate.query(
        sql,
        (rs, rowNum) -> {

            Student student = new Student();

            student.setId(rs.getInt("id"));
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setCourse(rs.getString("course"));
            student.setFees(rs.getDouble("fees"));

            return student;
        },
        id
    );

    return students.isEmpty() ? null : students.get(0);
}
```

Explanation:

- `WHERE id = ?` searches for a particular student.
- `query()` executes the SELECT query.
- The RowMapper converts the database result into a Student object.
- If no student exists, the method returns `null`.

Example:

```
Student student = studentDao.getStudentById(1);

System.out.println(student);
```

### Step 6: UPDATE Operation – Update Student

Now we will update existing student details.

Add this method:

```
public int updateStudent(Student student) {

    String sql = """
        UPDATE students
        SET name = ?,
            email = ?,
            course = ?,
            fees = ?
        WHERE id = ?
        """;

    return jdbcTemplate.update(
        sql,
        student.getName(),
        student.getEmail(),
        student.getCourse(),
        student.getFees(),
        student.getId()
    );
}
```

Explanation:

| SQL Keyword | Purpose                   |
| ----------- | ------------------------- |
| UPDATE      | Modifies existing records |
| SET         | Specifies new values      |
| WHERE       | Identifies the record     |

Example:

```
Student student = studentDao.getStudentById(1);

if (student != null) {

    student.setName("Pankaj Chouhan");
    student.setCourse("Advanced Java");
    student.setFees(25000);

    studentDao.updateStudent(student);
}
```

### Step 7: DELETE Operation – Delete Student

Now we will delete a student using their ID.

Add this method:

```
public int deleteStudent(int id) {

    String sql = "DELETE FROM students WHERE id = ?";

    return jdbcTemplate.update(sql, id);
}
```

Explanation:

- DELETE removes records from the database.
- WHERE identifies which record to delete.
- `update()` returns the number of affected rows.

Example:

```
int result = studentDao.deleteStudent(1);

if (result > 0) {
    System.out.println("Student Deleted Successfully!");
}
```

### Step 8: Add Count Students Method

Let's create another method to count the total number of students.

```
public int getStudentCount() {

    String sql = "SELECT COUNT(*) FROM students";

    return jdbcTemplate.queryForObject(
        sql,
        Integer.class
    );
}
```

Explanation:

`queryForObject()` is used when we expect a single result.

Here, we retrieve the total number of students as an Integer.

### Complete StudentDao.java

Now combine all methods into one complete class.

```

package com.cwpc.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.cwpc.model.Student;

public class StudentDao {

    private JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public StudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // INSERT
    public int saveStudent(Student student) {

        String sql = """
            INSERT INTO students
            (name, email, course, fees)
            VALUES (?, ?, ?, ?)
            """;

        return jdbcTemplate.update(
            sql,
            student.getName(),
            student.getEmail(),
            student.getCourse(),
            student.getFees()
        );
    }

    // SELECT ALL
    public List<Student> getAllStudents() {

        String sql = "SELECT * FROM students";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Student student = new Student();

            student.setId(rs.getInt("id"));
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setCourse(rs.getString("course"));
            student.setFees(rs.getDouble("fees"));

            return student;
        });
    }

    // SELECT BY ID
    public Student getStudentById(int id) {

        String sql = "SELECT * FROM students WHERE id = ?";

        List<Student> students = jdbcTemplate.query(
            sql,
            (rs, rowNum) -> {

                Student student = new Student();

                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setCourse(rs.getString("course"));
                student.setFees(rs.getDouble("fees"));

                return student;
            },
            id
        );

        return students.isEmpty() ? null : students.get(0);
    }

    // UPDATE
    public int updateStudent(Student student) {

        String sql = """
            UPDATE students
            SET name = ?, email = ?, course = ?, fees = ?
            WHERE id = ?
            """;

        return jdbcTemplate.update(
            sql,
            student.getName(),
            student.getEmail(),
            student.getCourse(),
            student.getFees(),
            student.getId()
        );
    }

    // DELETE
    public int deleteStudent(int id) {

        String sql = "DELETE FROM students WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }

    // COUNT
    public int getStudentCount() {

        String sql = "SELECT COUNT(*) FROM students";

        return jdbcTemplate.queryForObject(
            sql,
            Integer.class
        );
    }
}

```

## 13. Register StudentDao in Spring Configuration

We have already registered the StudentDao bean in `AppConfig.java`.

Let's understand the configuration:

```
@Bean
public StudentDao studentDao(JdbcTemplate jdbcTemplate) {

    return new StudentDao(jdbcTemplate);
}
```

Spring automatically provides the JdbcTemplate bean to the StudentDao constructor.

This is called Constructor Dependency Injection.

Our Spring container manages the following objects:

| Bean         | Responsibility      |
| ------------ | ------------------- |
| DataSource   | Database connection |
| JdbcTemplate | SQL execution       |
| StudentDao   | Database operations |

## 14. Create Main Application Class

Now we will create the main application class to execute all CRUD operations.

Create:

`MainApp.java`

Package:

`com.cwpc.main`

### Step 1: Import Required Classes

```
package com.cwpc.main;

import java.util.List;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cwpc.config.AppConfig;
import com.cwpc.dao.StudentDao;
import com.cwpc.model.Student;
```

### Step 2: Create Main Class

```
public class MainApp {

    public static void main(String[] args) {

    }
}
```

### Step 3: Initialize Spring Container

Inside the main method:

```
AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);
```

Explanation:

- Creates the Spring IoC container.
- Loads configuration from AppConfig.
- Creates and manages registered beans.

### Step 4: Get StudentDao Bean

```
StudentDao studentDao =
        context.getBean(StudentDao.class);
```

Spring provides the StudentDao object.

### Step 5: Create Scanner Object

```
Scanner sc = new Scanner(System.in);
```

Scanner accepts user input from the console.

### Step 6: Create Menu

We will create a menu-driven application.

```
System.out.println("1. Insert Student");
System.out.println("2. Display All Students");
System.out.println("3. Search Student");
System.out.println("4. Update Student");
System.out.println("5. Delete Student");
System.out.println("6. Count Students");
System.out.println("0. Exit");
```

### Step 7: Implement Switch Case

Now we will execute operations based on user selection.

```

package com.cwpc.main;

import java.util.List;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cwpc.config.AppConfig;
import com.cwpc.dao.StudentDao;
import com.cwpc.model.Student;

public class MainApp {

    public static void main(String[] args) {

        try (
            AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

            Scanner sc = new Scanner(System.in)
        ) {

            StudentDao studentDao =
                    context.getBean(StudentDao.class);

            int choice;

            do {

                System.out.println("\n================================");
                System.out.println("   STUDENT MANAGEMENT SYSTEM");
                System.out.println("================================");

                System.out.println("1. Insert Student");
                System.out.println("2. Display All Students");
                System.out.println("3. Search Student By ID");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Count Students");
                System.out.println("0. Exit");

                System.out.println("================================");

                System.out.print("Enter Your Choice: ");

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Course: ");
                        String course = sc.nextLine();

                        System.out.print("Enter Fees: ");
                        double fees =
                            Double.parseDouble(sc.nextLine());

                        Student student = new Student(
                            name,
                            email,
                            course,
                            fees
                        );

                        int insertResult =
                            studentDao.saveStudent(student);

                        if (insertResult > 0) {
                            System.out.println(
                                "Student Inserted Successfully!"
                            );
                        }

                        break;

                    case 2:

                        List<Student> students =
                            studentDao.getAllStudents();

                        if (students.isEmpty()) {

                            System.out.println("No Students Found!");

                        } else {

                            for (Student s : students) {
                                System.out.println(s);
                            }
                        }

                        break;

                    case 3:

                        System.out.print("Enter Student ID: ");

                        int searchId =
                            Integer.parseInt(sc.nextLine());

                        Student found =
                            studentDao.getStudentById(searchId);

                        if (found != null) {
                            System.out.println(found);
                        } else {
                            System.out.println("Student Not Found!");
                        }

                        break;

                    case 4:

                        System.out.print("Enter Student ID: ");

                        int updateId =
                            Integer.parseInt(sc.nextLine());

                        Student existing =
                            studentDao.getStudentById(updateId);

                        if (existing == null) {

                            System.out.println("Student Not Found!");

                        } else {

                            System.out.print("Enter New Name: ");
                            existing.setName(sc.nextLine());

                            System.out.print("Enter New Email: ");
                            existing.setEmail(sc.nextLine());

                            System.out.print("Enter New Course: ");
                            existing.setCourse(sc.nextLine());

                            System.out.print("Enter New Fees: ");

                            existing.setFees(
                                Double.parseDouble(sc.nextLine())
                            );

                            int result =
                                studentDao.updateStudent(existing);

                            if (result > 0) {
                                System.out.println(
                                    "Student Updated Successfully!"
                                );
                            }
                        }

                        break;

                    case 5:

                        System.out.print("Enter Student ID: ");

                        int deleteId =
                            Integer.parseInt(sc.nextLine());

                        int deleteResult =
                            studentDao.deleteStudent(deleteId);

                        if (deleteResult > 0) {
                            System.out.println(
                                "Student Deleted Successfully!"
                            );
                        } else {
                            System.out.println("Student Not Found!");
                        }

                        break;

                    case 6:

                        int count = studentDao.getStudentCount();

                        System.out.println(
                            "Total Students: " + count
                        );

                        break;

                    case 0:

                        System.out.println(
                            "Thank You! Application Closed."
                        );

                        break;

                    default:

                        System.out.println("Invalid Choice!");

                }

            } while (choice != 0);

        } catch (NumberFormatException e) {

            System.out.println(
                "Invalid numeric input: " + e.getMessage()
            );

        } catch (Exception e) {

            System.out.println(
                "Application Error: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}

```

This is our complete menu-driven application.

## 15. Final Project Structure

After completing all steps, your Eclipse project should look like this:

```
SpringJDBCExample
│
├── src/main/java
│   │
│   └── com.cwpc
│       │
│       ├── config
│       │   └── AppConfig.java
│       │
│       ├── model
│       │   └── Student.java
│       │
│       ├── dao
│       │   └── StudentDao.java
│       │
│       └── main
│           └── MainApp.java
│
├── src/main/resources
│
├── Maven Dependencies
│
└── pom.xml
```

### Verify Every Java File

| File            | Responsibility                    |
| --------------- | --------------------------------- |
| AppConfig.java  | Database and Spring configuration |
| Student.java    | Student model                     |
| StudentDao.java | Database CRUD operations          |
| MainApp.java    | Main application and menu         |

## 16. Execute the Application in Eclipse

Now let's execute our application.

### Step 1: Start MySQL Server

Make sure MySQL Server is running.

### Step 2: Verify Database

Execute:

```
USE spring_jdbc_db;

SELECT * FROM students;
```

### Step 3: Check Database Password

Open `AppConfig.java`.

Verify:

```
ds.setUsername("root");
ds.setPassword("your_password");
```

Replace the password with your actual MySQL password.

### Step 4: Update Maven Project

1. Right-click `SpringJDBCExample`.
2. Select Maven.
3. Select Update Project.
4. Select Force Update if dependencies need refreshing.
5. Click OK.

### Step 5: Run MainApp.java

1. Open `MainApp.java`.
2. Right-click inside the editor.
3. Select Run As.
4. Select Java Application.

The application will execute in the Eclipse Console.

## 17. Test Complete Application

Let's test each operation individually.

### Test 1: Insert Student

Console input:

```
================================
   STUDENT MANAGEMENT SYSTEM
================================
1. Insert Student
2. Display All Students
3. Search Student By ID
4. Update Student
5. Delete Student
6. Count Students
0. Exit
================================

Enter Your Choice: 1

Enter Name: Priya
Enter Email: priya@gmail.com
Enter Course: Python
Enter Fees: 15000

Student Inserted Successfully!
```

Verify in MySQL:

```
SELECT * FROM students;
```

### Test 2: Display All Students

Input:

```
Enter Your Choice: 2
```

Expected output (illustrative):

```
Student {
id=1,
name='Pankaj',
email='pankaj@gmail.com',
course='Java',
fees=15000.0
}

Student {
id=2,
name='Rahul',
email='rahul@gmail.com',
course='Python',
fees=12000.0
}

Student {
id=3,
name='Amit',
email='amit@gmail.com',
course='Spring Boot',
fees=18000.0
}

Student {
id=4,
name='Priya',
email='priya@gmail.com',
course='Python',
fees=15000.0
}
```

### Test 3: Search Student by ID

Input:

```
Enter Your Choice: 3

Enter Student ID: 1
```

Output:

```
Student {
id=1,
name='Pankaj',
email='pankaj@gmail.com',
course='Java',
fees=15000.0
}
```

### Test 4: Update Student

Input:

```
Enter Your Choice: 4

Enter Student ID: 1

Enter New Name: Pankaj Chouhan
Enter New Email: pankaj.chouhan@gmail.com
Enter New Course: Advanced Java
Enter New Fees: 25000
```

Output:

```
Student Updated Successfully!
```

Verify:

```
SELECT * FROM students WHERE id = 1;
```

### Test 5: Delete Student

Input:

```
Enter Your Choice: 5

Enter Student ID: 4
```

Output:

```
Student Deleted Successfully!
```

Verify:

```
SELECT * FROM students;
```

### Test 6: Count Total Students

Input:

```
Enter Your Choice: 6
```

Output:

```
Total Students: 3
```

### Test 7: Exit Application

```
Enter Your Choice: 0

Thank You! Application Closed.
```

## 18. Understanding Important JdbcTemplate Methods

Spring JDBC provides several methods for database operations.

| Method             | Purpose                            |
| ------------------ | ---------------------------------- |
| `update()`         | INSERT, UPDATE, DELETE             |
| `query()`          | Retrieve multiple records          |
| `queryForObject()` | Retrieve a single value or object  |
| `execute()`        | Execute SQL statements             |
| `batchUpdate()`    | Execute multiple update statements |

### Example 1: queryForObject()

Find the total number of students.

```
public int getStudentCount() {

    String sql = "SELECT COUNT(*) FROM students";

    return jdbcTemplate.queryForObject(
        sql,
        Integer.class
    );
}
```

### Example 2: Find Maximum Course Fees

```
public double getMaximumFees() {

    String sql = "SELECT MAX(fees) FROM students";

    return jdbcTemplate.queryForObject(
        sql,
        Double.class
    );
}
```

### Example 3: Find Minimum Course Fees

```
public double getMinimumFees() {

    String sql = "SELECT MIN(fees) FROM students";

    return jdbcTemplate.queryForObject(
        sql,
        Double.class
    );
}
```

### Example 4: Search Students by Course

```
public List<Student> getStudentsByCourse(String course) {

    String sql = "SELECT * FROM students WHERE course = ?";

    return jdbcTemplate.query(
        sql,
        (rs, rowNum) -> {

            Student student = new Student();

            student.setId(rs.getInt("id"));
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setCourse(rs.getString("course"));
            student.setFees(rs.getDouble("fees"));

            return student;
        },
        course
    );
}
```

Usage:

```
List<Student> students =
        studentDao.getStudentsByCourse("Java");

students.forEach(System.out::println);
```

### Example 5: Find Students by Fees

```
public List<Student> getStudentsByFees(double fees) {

    String sql = "SELECT * FROM students WHERE fees > ?";

    return jdbcTemplate.query(
        sql,
        (rs, rowNum) -> {

            Student s = new Student();

            s.setId(rs.getInt("id"));
            s.setName(rs.getString("name"));
            s.setEmail(rs.getString("email"));
            s.setCourse(rs.getString("course"));
            s.setFees(rs.getDouble("fees"));

            return s;
        },
        fees
    );
}
```

Usage:

```
List<Student> students =
        studentDao.getStudentsByFees(15000);

students.forEach(System.out::println);
```

## 19. What Happens Internally When JdbcTemplate Executes?

Consider this statement:

```
jdbcTemplate.update(
    "INSERT INTO students(name,email,course,fees) VALUES(?,?,?,?)",
    "Pankaj",
    "pankaj@gmail.com",
    "Java",
    15000
);
```

Spring JDBC internally performs the following operations:

1. Obtains a database connection from DataSource.
2. Creates a PreparedStatement.
3. Sets the parameter values.
4. Executes the SQL statement.
5. Processes the result.
6. Releases JDBC resources.

### Why Do We Use PreparedStatement?

PreparedStatement allows us to execute parameterized SQL statements.

Example:

```
String sql = "SELECT * FROM students WHERE id = ?";

jdbcTemplate.query(sql, rowMapper, 1);
```

Avoid constructing SQL by directly concatenating user input:

```
// Avoid this approach
String sql =
    "SELECT * FROM students WHERE name = '" + name + "'";
```

Parameterized queries help prevent SQL injection.

## 20. Spring JDBC Exception Handling

Spring JDBC provides a hierarchy of database access exceptions.

The base exception is:

`DataAccessException`

Some commonly encountered exceptions:

| Exception                          | Meaning                          |
| ---------------------------------- | -------------------------------- |
| DuplicateKeyException              | Duplicate unique or primary key  |
| EmptyResultDataAccessException     | No result where one was expected |
| DataIntegrityViolationException    | Database constraint violation    |
| BadSqlGrammarException             | Invalid SQL syntax               |
| DataAccessResourceFailureException | Database resource failure        |

Example:

```
import org.springframework.dao.DataAccessException;

try {

    int result = studentDao.saveStudent(student);

    System.out.println("Student Inserted!");

} catch (DataAccessException e) {

    System.out.println("Database Error: " + e.getMessage());
}
```

## 21. Spring JDBC Transaction Management

Transactions are important when multiple database operations must succeed or fail together.

For example, suppose we want to:

1. Insert a student.
2. Insert a payment record.

If the second operation fails, we may want to roll back the first operation too.

Spring supports transaction management using `@Transactional`.

### Step 1: Add Transaction Management Dependency

Spring JDBC and Spring Context already provide the required transaction infrastructure for this example.

### Step 2: Enable Transaction Management

Inside `AppConfig.java`, add:

```
import org.springframework.transaction.annotation.EnableTransactionManagement;
```

Add this annotation above the configuration class:

```
@Configuration
@EnableTransactionManagement
public class AppConfig {
```

### Step 3: Create Transaction Manager Bean

Add this bean inside `AppConfig`:

```
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Bean
public PlatformTransactionManager transactionManager(
        DataSource dataSource) {

    return new DataSourceTransactionManager(dataSource);
}
```

### Step 4: Use @Transactional

Create a service class and annotate a method:

```
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Transactional
    public void addStudent(Student student) {

        studentDao.saveStudent(student);

        // Other database operations can be performed here.
    }
}
```

Important: Register `StudentService` as a Spring bean, either through component scanning or an explicit `@Bean` method. Also, transactional methods should be invoked through the Spring-managed proxy. By default, Spring rolls back on unchecked exceptions and errors, not every checked exception.

## 22. Common Spring JDBC Errors and Solutions

| Error                       | Reason                               | Solution                           |
| --------------------------- | ------------------------------------ | ---------------------------------- |
| No suitable driver          | JDBC driver missing                  | Add MySQL dependency               |
| Access denied for user      | Incorrect credentials                | Verify MySQL username and password |
| Unknown database            | Database missing                     | Create database                    |
| Table doesn't exist         | Table not created                    | Create students table              |
| Communications link failure | MySQL server unavailable             | Start MySQL                        |
| Connection refused          | Incorrect port or server unavailable | Verify port 3306                   |
| ClassNotFoundException      | Missing dependency                   | Update Maven project               |
| Duplicate entry             | Duplicate email                      | Use a unique email                 |
| Bad SQL grammar             | Incorrect SQL                        | Verify SQL statement               |

### Eclipse Maven Troubleshooting

If dependencies are not downloaded:

1. Right-click project.
2. Select Maven.
3. Select Update Project.
4. Check Force Update of Snapshots/Releases.
5. Click OK.

### Important Database Connection Check

Verify the following configuration:

```
ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

ds.setUrl(
    "jdbc:mysql://localhost:3306/spring_jdbc_db"
);

ds.setUsername("root");

ds.setPassword("your_password");
```

If your MySQL server uses a different port, update the JDBC URL accordingly.

## 23. Important Spring JDBC Interview Questions

Q1. What is Spring JDBC?

Spring JDBC is a module of the Spring Framework that simplifies JDBC database operations using JdbcTemplate.

Q2. What is JdbcTemplate?

JdbcTemplate is a Spring class that simplifies SQL execution, connection management and JDBC resource cleanup.

Q3. What is DataSource?

DataSource is a Java interface that provides database connections.

Q4. What is RowMapper?

RowMapper is an interface used to map each row of a ResultSet into a Java object.

Q5. What is the difference between JDBC and Spring JDBC?

Traditional JDBC requires more manual connection and resource management. Spring JDBC simplifies these operations through JdbcTemplate.

Q6. What is the difference between update() and query()?

- `update()` executes INSERT, UPDATE and DELETE statements.
- `query()` retrieves multiple records.

Q7. What is queryForObject()?

It executes a query that is expected to return one result, such as a count or a single database value.

Q8. What is dependency injection?

Dependency Injection is a design approach in which an object receives its required dependencies from an external container.

Q9. Does Spring JDBC support transactions?

Yes. Spring provides transaction management through PlatformTransactionManager and @Transactional.

Q10. What is PreparedStatement?

PreparedStatement is used to execute parameterized SQL statements.

Q11. What is DAO?

DAO stands for Data Access Object. It separates database access logic from other application logic.

Q12. What is the difference between Spring JDBC and Hibernate?

| Spring JDBC                      | Hibernate                     |
| -------------------------------- | ----------------------------- |
| SQL-based database access        | ORM-based database access     |
| Developer writes SQL             | ORM generates much of the SQL |
| JdbcTemplate                     | Session / EntityManager       |
| RowMapper                        | Entity mapping                |
| Lightweight database abstraction | Full ORM framework            |

## 24. Student Practice Assignments

After completing the Student Management System, students should try these practical assignments.

### Assignment 1: Employee Management System

Create an Employee Management application using Spring JDBC.

Database Table: employees

| Column     | Data Type |
| ---------- | --------- |
| id         | INT       |
| name       | VARCHAR   |
| email      | VARCHAR   |
| department | VARCHAR   |
| salary     | DOUBLE    |

Tasks:

- Insert employee.
- Display all employees.
- Search employee by ID.
- Update employee salary.
- Delete employee.
- Count employees.

### Assignment 2: Book Management System

Create a Book Management application.

Database Table: books

| Column   | Data Type |
| -------- | --------- |
| id       | INT       |
| title    | VARCHAR   |
| author   | VARCHAR   |
| category | VARCHAR   |
| price    | DOUBLE    |

Tasks:

- Add new book.
- Display all books.
- Search book by ID.
- Search books by category.
- Update book price.
- Delete book.

### Assignment 3: Course Management System

Create a Course Management application.

Database Table: courses

| Column       | Data Type |
| ------------ | --------- |
| id           | INT       |
| course_name  | VARCHAR   |
| duration     | VARCHAR   |
| fees         | DOUBLE    |
| trainer_name | VARCHAR   |

Tasks:

- Add course.
- Display courses.
- Search course.
- Update course.
- Delete course.

## 25. Spring JDBC Complete Learning Roadmap

After completing this beginner project, follow this learning sequence:

| Level        | Topics                      |
| ------------ | --------------------------- |
| Beginner     | Introduction to Spring JDBC |
| Beginner     | JdbcTemplate                |
| Beginner     | DataSource                  |
| Beginner     | Spring Configuration        |
| Intermediate | RowMapper                   |
| Intermediate | queryForObject()            |
| Intermediate | NamedParameterJdbcTemplate  |
| Intermediate | BeanPropertyRowMapper       |
| Intermediate | Batch Operations            |
| Advanced     | Transaction Management      |
| Advanced     | Exception Handling          |
| Advanced     | Connection Pooling          |
| Project      | Employee Management System  |
| Project      | Student Management System   |

## 26. Final Project Completion Checklist

Use this checklist while developing the application in Eclipse.

Your project progress

# 0%

0 of 22 steps completed

Install JDK and Eclipse IDE

Install MySQL Server and Workbench

Create spring_jdbc_db database

Create students table

Insert sample records

Create Maven project in Eclipse

Configure pom.xml dependencies

Create config package

Create model package

Create dao package

Create main package

Configure DataSource

Configure JdbcTemplate

Create Student model class

Create StudentDao class

Implement INSERT operation

Implement SELECT operation

Implement UPDATE operation

Implement DELETE operation

Create MainApp class

Run application in Eclipse

Test all CRUD operations

## 27. Conclusion

In this complete Spring JDBC tutorial, we learned:

1. Introduction to Spring JDBC.
2. Spring JDBC architecture.
3. Maven project creation in Eclipse.
4. Spring JDBC dependency configuration.
5. MySQL database connectivity.
6. JdbcTemplate configuration.
7. Model and DAO creation.
8. Complete CRUD operations.
9. RowMapper implementation.
10. Exception handling.
11. Transaction management.
12. Complete Student Management System.

We successfully designed a beginner-level database application using Java, Spring JDBC, Maven, Eclipse IDE and MySQL.

Recommended next tutorial: Spring JDBC with NamedParameterJdbcTemplate – Complete Step-by-Step CRUD Example.
