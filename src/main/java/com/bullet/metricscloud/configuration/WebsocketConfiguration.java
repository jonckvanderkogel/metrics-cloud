package com.bullet.metricscloud.configuration;

import com.bullet.metricscloud.domain.ErrorMessage;
import com.bullet.metricscloud.websocket.ErrorMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebsocketConfiguration {

    @Bean
    public HandlerMapping webSocketHandlerMapping(@Autowired Flux<ErrorMessage> errorMessageFlux) {
        Map<String, WebSocketHandler> urlMap = new HashMap<>();
        urlMap.put("/websocket", new ErrorMessageHandler(errorMessageFlux));

        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(1);
        handlerMapping.setUrlMap(urlMap);
        return handlerMapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
