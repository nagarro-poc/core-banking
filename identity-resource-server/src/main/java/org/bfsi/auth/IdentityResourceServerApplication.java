package org.bfsi.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class IdentityResourceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdentityResourceServerApplication.class, args);
    }
}
