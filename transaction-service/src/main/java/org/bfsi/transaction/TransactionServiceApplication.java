package org.bfsi.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class TransactionServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(TransactionServiceApplication.class, args);

    }
}
