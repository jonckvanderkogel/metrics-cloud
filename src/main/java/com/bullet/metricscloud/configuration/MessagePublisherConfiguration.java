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
        ErrorScenario scenario1 = new ErrorScenario(
                List.of(
                        new ErrorScenario.ErrorCondition(Category.COUNTRY, 80, 3),
                        new ErrorScenario.ErrorCondition(Category.URL, 80, 2)
                ),
                random);

        ErrorScenario scenario2 = new ErrorScenario(
                List.of(
                        new ErrorScenario.ErrorCondition(Category.OS, 90, 1),
                        new ErrorScenario.ErrorCondition(Category.USER_TYPE, 90, 0)
                ),
                random);

        ErrorScenario scenario3 = new ErrorScenario(
                List.of(
                        new ErrorScenario.ErrorCondition(Category.MOBILE_PROVIDER, 75, 2)
                ),
                random);

        ErrorScenario scenario4 = new ErrorScenario(
                List.of(
                        new ErrorScenario.ErrorCondition(Category.OS, 90, 0),
                        new ErrorScenario.ErrorCondition(Category.APP_VERSION, 90, 11),
                        new ErrorScenario.ErrorCondition(Category.URL, 90, 7)
                ),
                random);

        return List.of(scenario1, scenario2, scenario3, scenario4);
    }

    private Function<Long, ErrorScenario> getErrorScenarioFun() {
        var errorScenarios = getErrorScenarios();
        long seriesValue = 10;
        return (l) -> errorScenarios.get(Long.valueOf(((l % (seriesValue * errorScenarios.size()))/seriesValue)).intValue());
    }
}
