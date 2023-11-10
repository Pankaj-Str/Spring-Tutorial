package codeswithpankaj.com;
import org.springframework.stereotype.Service;

@Service
public class MyService {
	
	    public String greet(String name) {
	        return "Hello, " + name;
	    }

	    public int add(int a, int b) {
	        return a + b;
	    }

}
