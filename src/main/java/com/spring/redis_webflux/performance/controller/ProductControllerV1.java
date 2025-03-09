package com.spring.redis_webflux.performance.controller;

import com.spring.redis_webflux.performance.model.Product;
import com.spring.redis_webflux.performance.service.ProductServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product/v1")
public class ProductControllerV1 {

    @Autowired
    private ProductServiceV1 productServiceV1;

    @GetMapping("{id}")
    public Mono<Product> getProduct(@PathVariable int id) {
        return this.productServiceV1.getProduct(id);
    }

    @PutMapping("{id}")
    public Mono<Product> updateProduct(@PathVariable int id, @RequestBody Mono<Product> productMono) {
        return this.productServiceV1.updateProduct(id, productMono);
    }
}
