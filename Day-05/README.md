# Spring JDBC - Create Query

Certainly! Here's a complete example of using Spring JDBC to create and execute a query in a Spring Boot application:

1. **Create a Spring Boot Project:**

   Start by creating a new Spring Boot project using the Spring Initializr or your preferred method.

2. **Configure Dependencies:**

   Make sure you have the necessary dependencies in your `pom.xml` or `build.gradle` file:

   For Maven:

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-jdbc</artifactId>
   </dependency>
   <dependency>
       <groupId>com.h2database</groupId>
       <artifactId>h2</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

   For Gradle:

   ```groovy
   implementation 'org.springframework.boot:spring-boot-starter-jdbc'
   runtimeOnly 'com.h2database:h2'
   ```

3. **Create Configuration:**

   Create a configuration class to set up the `DataSource` and `JdbcTemplate`.

   ```java
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.jdbc.core.JdbcTemplate;
   import org.springframework.jdbc.datasource.DriverManagerDataSource;

   @Configuration
   public class DataSourceConfig {

       @Bean
       public DataSource dataSource() {
           DriverManagerDataSource dataSource = new DriverManagerDataSource();
           dataSource.setDriverClassName("org.h2.Driver");
           dataSource.setUrl("jdbc:h2:mem:testdb");
           dataSource.setUsername("sa");
           dataSource.setPassword("");
           return dataSource;
       }

       @Bean
       public JdbcTemplate jdbcTemplate(DataSource dataSource) {
           return new JdbcTemplate(dataSource);
       }
   }
   ```

4. **Create a Repository:**

   Create a repository class to interact with the database.

   ```java
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.jdbc.core.JdbcTemplate;
   import org.springframework.stereotype.Repository;

   @Repository
   public class UserRepository {

       private final JdbcTemplate jdbcTemplate;

       @Autowired
       public UserRepository(JdbcTemplate jdbcTemplate) {
           this.jdbcTemplate = jdbcTemplate;
       }

       public void createUser(String username, String email) {
           String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
           jdbcTemplate.update(sql, username, email);
       }
   }
   ```

5. **Run Application:**

   You can now create a main Spring Boot application class to run your application.

   ```java
   import org.springframework.boot.SpringApplication;
   import org.springframework.boot.autoconfigure.SpringBootApplication;

   @SpringBootApplication
   public class SpringJdbcExampleApplication {

       public static void main(String[] args) {
           SpringApplication.run(SpringJdbcExampleApplication.class, args);
       }
   }
   ```

6. **Verify Results:**

   You can write a simple test or use a controller to trigger the creation of a user and verify it in the H2 in-memory database.

Remember that this example uses the H2 in-memory database for simplicity. In a real-world scenario, you would configure the DataSource with the appropriate settings for your chosen database.

This example demonstrates how to create and execute a query using Spring JDBC in a Spring Boot application. You can extend this example to include more advanced query operations and error handling as needed.
