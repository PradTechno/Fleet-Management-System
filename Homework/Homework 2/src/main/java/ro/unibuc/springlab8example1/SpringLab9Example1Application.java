package ro.unibuc.springlab8example1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SpringLab9Example1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringLab9Example1Application.class, args);
    }

}
