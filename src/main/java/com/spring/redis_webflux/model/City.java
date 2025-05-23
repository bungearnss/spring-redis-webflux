package com.spring.redis_webflux.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class City {

    private String zip;
    private String city;
    private String stateName;
    private int temperature;
}
