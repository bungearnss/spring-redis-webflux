package com.spring.redis_webflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class GeoLocation {

    private double longitude;
    private double latitude;
}
