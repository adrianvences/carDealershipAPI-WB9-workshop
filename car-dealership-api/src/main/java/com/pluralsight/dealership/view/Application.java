package com.pluralsight.dealership.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

// @Component needed so that this class is managed as a spring bean
@Component
// implemented CommandLineRunner so it Overrides run method to allow code in this PSWM.
public class Application implements CommandLineRunner {

    @Autowired
    DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        new UserInterface(dataSource).display();
    }
}
