package com.bullet.metricscloud.websocket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ErrorMessageDTO {
    private final String type;
    private final Map<String, String> headers;
}
