package com.spring.redis_webflux.performance.service.util;

import com.spring.redis_webflux.performance.model.Product;
import com.spring.redis_webflux.performance.repository.ProductRepository;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

//@Service
public class ProductCacheTemplate extends CacheTemplate<Integer, Product> {

    @Autowired
    private ProductRepository productRepository;
    private RMapReactive<Integer, Product> map;

    public ProductCacheTemplate(RedissonReactiveClient client) {
        this.map = client.getMap("product", new TypedJsonJacksonCodec(Integer.class, Product.class));
    }

    @Override
    protected Mono<Product> getFromSource(Integer id) {
        return this.productRepository.findById(id);
    }

    @Override
    protected Mono<Product> getFromCache(Integer id) {
        return this.map.get(id);
    }

    @Override
    protected Mono<Product> updateSource(Integer id, Product product) {
        return this.productRepository.findById(id)
                .doOnNext(p -> product.setId(id))
                .flatMap(p -> this.productRepository.save(product));
    }

    @Override
    protected Mono<Product> updateCache(Integer id, Product product) {
        return this.map.fastPut(id, product).thenReturn(product);
    }

    @Override
    protected Mono<Void> deleteFromSource(Integer id) {
        return this.productRepository.deleteById(id);
    }

    @Override
    protected Mono<Void> deleteFromCache(Integer id) {
        return this.map.fastRemove(id).then();
    }
}
