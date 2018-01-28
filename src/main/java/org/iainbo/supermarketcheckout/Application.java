package org.iainbo.supermarketcheckout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("org.iainbo")
@EntityScan(basePackages = {"org.iainbo.supermarketcheckout.entities"})
@EnableJpaRepositories(basePackages = {"org.iainbo.supermarketcheckout.repositories"})
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
