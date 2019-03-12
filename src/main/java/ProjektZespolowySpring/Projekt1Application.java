package ProjektZespolowySpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@ImportResource("classpath:security.xml")
public class Projekt1Application {

    public static void main(String[] args) {
        SpringApplication.run(Projekt1Application.class, args);
    }

}
