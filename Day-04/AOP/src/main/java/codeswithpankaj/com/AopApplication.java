package codeswithpankaj.com;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class AopApplication {

	public static void main(String[] args) {
		  AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

	        MyService myService = context.getBean(MyService.class);
	        System.out.println(myService.greet("p4n"));
	        System.out.println(myService.add(5, 3));

	        context.close();
	}

}
