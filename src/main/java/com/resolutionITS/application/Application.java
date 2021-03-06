package com.resolutionITS.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Author: Jordan Lee
 * Application: Resolution-ITS is a user-friendly enterprise IT service management
 * platform
 * <p>
 * Date: 00/00/0000
 * <p>
 * To see more of the author's work, please visit https://github.com/jordanmlee
 * <p>
 * The Application class is contains the main method for running the application
 */

@SpringBootApplication
@EnableJpaRepositories
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    // demoing user add repo
//    @Bean
//    public CommandLineRunner demo (UserRepository repository) {
//        return(args -> {
//            repository.save(new Users2("Jacky"));
//        });
//    }


}

