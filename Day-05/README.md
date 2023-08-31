# Spring JDBC - Create Query

In Spring JDBC, you can create and execute SQL queries using the `JdbcTemplate` class. This class provides a higher-level abstraction for working with JDBC and makes it easier to interact with databases. Here's how you can create and execute queries using Spring JDBC:

1. **Configure DataSource:**

   First, you need to configure a `DataSource` bean in your Spring configuration. This DataSource will provide the database connection to the `JdbcTemplate`.

   ```java
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.jdbc.datasource.DriverManagerDataSource;

   @Configuration
   public class DataSourceConfig {

       @Bean
       public DataSource dataSource() {
           DriverManagerDataSource dataSource = new DriverManagerDataSource();
           dataSource.setDriverClassName("your.database.driver.class");
           dataSource.setUrl("jdbc:your-database-url");
           dataSource.setUsername("your-username");
           dataSource.setPassword("your-password");
           return dataSource;
       }
   }
   ```

2. **Create JdbcTemplate Bean:**

   Next, create a `JdbcTemplate` bean that will be used to execute SQL queries.

   ```java
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.jdbc.core.JdbcTemplate;

   @Configuration
   public class JdbcTemplateConfig {

       @Bean
       public JdbcTemplate jdbcTemplate(DataSource dataSource) {
           return new JdbcTemplate(dataSource);
       }
   }
   ```

3. **Write and Execute Query:**

   Now you can use the `JdbcTemplate` to execute SQL queries. Here's an example of how to create and execute a simple query:

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

   In this example, the `jdbcTemplate.update()` method is used to execute an SQL update query to insert a new user into the database.

Remember to replace placeholders like `your.database.driver.class`, `jdbc:your-database-url`, `your-username`, and `your-password` with actual values according to your database configuration.

Spring JDBC provides various methods to execute queries, query for single results, query for lists of results, and more. You can refer to the Spring documentation for more advanced query options and error handling strategies.
