package com.spring.redis_webflux.chat.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RListReactive;
import org.redisson.api.RTopicReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
@Slf4j
public class ChatRoomService implements WebSocketHandler {

    @Autowired
    private RedissonReactiveClient client;

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        String room = getChatRoomName(webSocketSession);
        RTopicReactive topic = this.client.getTopic(room, StringCodec.INSTANCE);
        RListReactive<String> list = this.client.getList("history:" + room, StringCodec.INSTANCE);
        // subscribe
        webSocketSession.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(msg -> list.add(msg).then(topic.publish(msg)))
                .doOnError(System.out::println)
                .doFinally(s -> log.info("Subscriber finally {}", s))
                .subscribe();

        // publisher
        Flux<WebSocketMessage> flux = topic.getMessages(String.class)
                .startWith(list.iterator())
                .map(webSocketSession::textMessage)
                .doOnError(System.out::println)
                .doFinally(s -> log.info("publisher finally {}", s));

        return webSocketSession.send(flux);
    }

    private String getChatRoomName(WebSocketSession socketSession) {
        URI uri = socketSession.getHandshakeInfo().getUri();
        return UriComponentsBuilder.fromUri(uri)
                .build()
                .getQueryParams()
                .toSingleValueMap()
                .getOrDefault("room", "default");
    }

}
