package com.bullet.metricscloud.publisher;

import com.bullet.metricscloud.domain.ErrorMessage;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Function;

@RequiredArgsConstructor
public class MessagePublisher {
    private final Function<Long, ErrorScenario> errorScenarioFun;

    public Flux<ErrorMessage> generateErrorMessages() {
        return Flux.interval(Duration.ofMillis(50))
                .map(counter -> errorScenarioFun.apply(counter).generateErrorMessage())
                .onBackpressureBuffer(1000);
    }
}
