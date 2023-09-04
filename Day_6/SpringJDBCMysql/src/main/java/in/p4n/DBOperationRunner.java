package in.p4n;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBOperationRunner implements CommandLineRunner {

     @Autowired
     EmployeeRepository eRepo;

     @Override
     public void run(String... args) throws Exception {

           eRepo.saveAll(Arrays.asList(

                      new Employee(1001,"James",2599.5,"HR"),
                      new Employee(1002,"Elizabeth",2999.0,"Admin"),
                      new Employee(1003,"Robert",2699.5,"Testing"),
                      new Employee(1004,"Victoria",3000.5,"Development"),
                      new Employee(1005,"David",2650.5,"QA"),
                      new Employee(1006,"Isabel",2590.0,"Support"),
                      new Employee(1007,"Michael",3599.75,"Development"),
                      new Employee(1008,"Maria",2499.0,"Finance"),
                      new Employee(1009,"Thomas",2799.25,"HR"),
                      new Employee(1010,"Maria",2899.5,"Development"),
        		   	  new Employee(1011,"Omji",28993.5,"IOS Development"))

            ); 

          System.out.println("----------All Data saved into Database----------------------");
          }

}