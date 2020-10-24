package com.bullet.metricscloud.websocket;

import com.bullet.metricscloud.domain.Category;
import com.bullet.metricscloud.domain.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ErrorMessageHandler implements WebSocketHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Flux<ErrorMessage> errorMessageFlux;

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession
                .send(errorMessageFlux
                        .map(message -> {
                            ErrorMessageDTO dto = new ErrorMessageDTO("ErrorMessage", convertTuplesToMap(message.getHeaders()));
                            try {
                                return objectMapper.writeValueAsString(dto);
                            } catch (JsonProcessingException e) {
                                return "Error while serializing to JSON";
                            }
                        })
                        .map(webSocketSession::textMessage))
                .and(webSocketSession
                        .receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .log()
                );
    }

    private Map<String, String> convertTuplesToMap(List<Tuple2<Category,String>> tuples) {
        return tuples
                .stream()
                .collect(Collectors.toMap(t -> t._1.toString(), t -> t._2));
    }
}
