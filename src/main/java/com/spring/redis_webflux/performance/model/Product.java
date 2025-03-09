package com.spring.redis_webflux.performance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("product")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private Integer id;
    private String description;
    private double price;
}
