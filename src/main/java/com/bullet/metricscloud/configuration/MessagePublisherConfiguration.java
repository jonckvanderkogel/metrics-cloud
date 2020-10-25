package com.bullet.metricscloud.configuration;

import com.bullet.metricscloud.domain.Category;
import com.bullet.metricscloud.domain.ErrorMessage;
import com.bullet.metricscloud.publisher.ErrorScenario;
import com.bullet.metricscloud.publisher.MessagePublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.SplittableRandom;
import java.util.function.Function;

@Configuration
public class MessagePublisherConfiguration {
    @Bean
    public Flux<ErrorMessage> errorMessageFlux() {
        return new MessagePublisher(getErrorScenarioFun()).generateErrorMessages();
    }

    private List<ErrorScenario> getErrorScenarios() {
        var random = new SplittableRandom();
        // Austria and /loans endpoint
        ErrorScenario scenario1 = new ErrorScenario(
                List.of(
                        new ErrorScenario.ErrorCondition(Category.COUNTRY, 100, 3),
                        new ErrorScenario.ErrorCondition(Category.URL, 100, 2)
                ),
                random);

        // iOS and version 2.2
        ErrorScenario scenario2 = new ErrorScenario(
                List.of(
                        new ErrorScenario.ErrorCondition(Category.OS, 100, 1),
                        new ErrorScenario.ErrorCondition(Category.APP_VERSION, 100, 10)
                ),
                random);

        // T-Mobile
        ErrorScenario scenario3 = new ErrorScenario(
                List.of(
                        new ErrorScenario.ErrorCondition(Category.MOBILE_PROVIDER, 100, 2)
                ),
                random);

        // iOS, version 2.3 and /creditcards endpoint
        ErrorScenario scenario4 = new ErrorScenario(
                List.of(
                        new ErrorScenario.ErrorCondition(Category.OS, 100, 1),
                        new ErrorScenario.ErrorCondition(Category.APP_VERSION, 100, 11),
                        new ErrorScenario.ErrorCondition(Category.URL, 100, 7)
                ),
                random);

        return List.of(scenario1, scenario2, scenario3, scenario4);
    }

    private Function<Long, ErrorScenario> getErrorScenarioFun() {
        var errorScenarios = getErrorScenarios();
        long seriesValue = 250;
        return (l) -> errorScenarios.get(Long.valueOf(((l % (seriesValue * errorScenarios.size()))/seriesValue)).intValue());
    }
}
