package com.craft;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Address {

    @Value("Addis")
    private String city;

    @Value("Ethiopia")
    private String country;
}