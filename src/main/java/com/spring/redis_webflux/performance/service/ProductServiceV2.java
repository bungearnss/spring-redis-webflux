package com.spring.redis_webflux.performance.service;

import com.spring.redis_webflux.performance.model.Product;
import com.spring.redis_webflux.performance.service.util.CacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceV2 {

    @Autowired
    private CacheTemplate<Integer, Product> cacheTemplate;

    @Autowired
    private ProductVisitService visitService;

    // GET
    public Mono<Product> getProduct(int id) {
        return this.cacheTemplate.get(id)
                .doFirst(() -> this.visitService.addVisit(id));
    }

    // PUT
    public Mono<Product> updateProduct(int id, Mono<Product> productMono) {
        return productMono
                .flatMap(p -> this.cacheTemplate.update(id, p));
    }

    // DELETE
    public Mono<Void> deleteProduct(int id) {
        return this.cacheTemplate.delete(id);
    }
}
