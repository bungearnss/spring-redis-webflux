package com.spring.redis_webflux.performance.service;

import com.spring.redis_webflux.performance.model.Product;
import com.spring.redis_webflux.performance.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceV1 {

    @Autowired
    private ProductRepository productRepository;

    public Mono<Product> getProduct(int id) {
        return this.productRepository.findById(id);
    }

    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        return this.productRepository.findById(id)
                .flatMap(p -> productMono.doOnNext(pr -> pr.setId(id)))
                .flatMap(this.productRepository::save);
    }
}
