package org.bfsi.user;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class UserServiceApplication {

	private static final Logger LOGGER = LogManager.getLogger(UserServiceApplication.class);
    public static void main(String[] args){
        SpringApplication.run(UserServiceApplication.class, args);
        LOGGER.info("Info level log message");
        LOGGER.debug("Debug level log message");
        LOGGER.error("Error level log message");
    }
}
