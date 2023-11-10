package codeswithpankaj.com;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PrototypeBean {
	
	public PrototypeBean() {
		
		System.out.println("PrototypeBean instance created.");
		
	}
	
}
