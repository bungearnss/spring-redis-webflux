package com.spring.redis_webflux.performance.repository;

import com.spring.redis_webflux.performance.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
}
