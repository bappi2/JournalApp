package com.mmeftauddin.journalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableMongoAuditing
@SpringBootApplication(scanBasePackages = {
        "com.mmeftauddin.journalapp",
        "com.mmeftauddin.common"
})
//@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {

        SpringApplication.run(JournalApplication.class, args);
    }

}

// PlatformTransactionManager --> interface
// MongoTransactionManager --> implemetation