package com.craft;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.craft")
public class AppConfig {

    @Bean
    public Subject math() {
        return new Subject("Maths 101");
    }

    @Bean
    public Subject java() {
        return new Subject("Java EE");
    }
}