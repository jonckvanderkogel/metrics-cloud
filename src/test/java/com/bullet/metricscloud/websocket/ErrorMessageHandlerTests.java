package com.bullet.metricscloud.websocket;

import com.bullet.metricscloud.domain.Category;
import com.bullet.metricscloud.domain.ErrorMessage;
import io.vavr.Tuple2;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bullet.metricscloud.domain.Category.*;
import static org.mockito.Mockito.*;

public class ErrorMessageHandlerTests {
    @Test
    public void websocketShouldGetMessageWhenFluxEmits() {
        Flux<ErrorMessage> errorMessageFlux = Flux
                .fromIterable(List.of(
                        new ErrorMessage(createTuples()))
                );
        ErrorMessageHandler errorMessageHandler = new ErrorMessageHandler(errorMessageFlux);

        WebSocketSession webSocketSessionMock = mock(WebSocketSession.class);
        WebSocketMessage webSocketMessageMock = mock(WebSocketMessage.class);
        when(webSocketMessageMock.getPayloadAsText()).thenReturn("Foo");
        Flux<WebSocketMessage> webSocketMessageFlux = Flux
                .fromIterable(List.of(
                        webSocketMessageMock
                ));
        when(webSocketSessionMock.receive()).thenReturn(webSocketMessageFlux);
        when(webSocketSessionMock.send(any())).thenReturn(Mono.empty());

        errorMessageHandler.handle(webSocketSessionMock);

        verify(webSocketSessionMock, times(1)).send(any(Flux.class));
    }

    private List<Tuple2<Category, String>> createTuples() {
        return Stream
                .of(Category.values())
                .map(category -> new Tuple2<>(category, valueMap.get(category).get(0)))
                .collect(Collectors.toList());
    }
}
