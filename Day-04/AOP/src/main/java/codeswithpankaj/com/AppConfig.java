package codeswithpankaj.com;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "codeswithpankaj.com")
@EnableAspectJAutoProxy
public class AppConfig {

}
