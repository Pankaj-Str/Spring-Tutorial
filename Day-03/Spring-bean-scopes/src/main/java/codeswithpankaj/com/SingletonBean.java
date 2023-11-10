package codeswithpankaj.com;

import org.springframework.stereotype.Component;

@Component
public class SingletonBean {

	Clients clients = new Clients("suraj",22,"Surat");
    
	public SingletonBean() {
		System.out.println("SingletonBean instance created.");
		System.out.println("name : "+clients.getName());
		System.out.println("Age : "+clients.getAge());
		System.out.println("City : "+clients.getCity());
		
	}
}
