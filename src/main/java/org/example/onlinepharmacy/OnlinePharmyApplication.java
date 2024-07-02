package org.example.onlinepharmacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class OnlinePharmyApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinePharmyApplication.class, args);
    }

}
