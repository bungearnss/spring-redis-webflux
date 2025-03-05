package com.spring.redis_webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.redisson.api.RAtomicLongReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@Slf4j
class RedisWebfluxApplicationTests {

    @Autowired
    private ReactiveStringRedisTemplate template;

    @Autowired
    private RedissonReactiveClient client;

    @RepeatedTest(3)
    void springDataRedisTest() {
        ReactiveValueOperations<String, String> valueOperations = this.template.opsForValue();
        long before = System.currentTimeMillis();
        Mono<Void> mono = Flux.range(1, 50_00)
                .flatMap(i -> valueOperations.increment("user:1:visit"))
                .then();

        StepVerifier.create(mono)
                .verifyComplete();
        long after = System.currentTimeMillis();
        log.info("DataRedis | after - before : {} ms", (after - before));
    }

    @RepeatedTest(3)
    void redissonTest(){
        RAtomicLongReactive atomicLong = this.client.getAtomicLong("user:2:visit");
        long before = System.currentTimeMillis();
        Mono<Void> mono = Flux.range(1, 500_000)
                .flatMap(i -> atomicLong.incrementAndGet())
                .then();
        StepVerifier.create(mono)
                .verifyComplete();
        long after = System.currentTimeMillis();
        log.info("Redisson | after - before : {} ms", (after - before));
    }
}
