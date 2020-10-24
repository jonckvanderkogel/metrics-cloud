package com.bullet.metricscloud.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import io.vavr.Tuple2;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@RequiredArgsConstructor
public class ErrorMessage {
    private final List<Tuple2<Category,String>> headers;
}
