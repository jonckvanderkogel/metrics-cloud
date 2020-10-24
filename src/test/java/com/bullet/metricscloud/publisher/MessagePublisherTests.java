package com.bullet.metricscloud.publisher;

import com.bullet.metricscloud.domain.Category;
import com.bullet.metricscloud.domain.ErrorMessage;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.SplittableRandom;
import java.util.function.Function;
import java.util.function.Predicate;

public class MessagePublisherTests {
    @Test
    public void shouldPublishMessage() {
        ErrorScenario scenario = new ErrorScenario(
                List.of(
                        new ErrorScenario.ErrorCondition(Category.COUNTRY, 80, 3),
                        new ErrorScenario.ErrorCondition(Category.URL, 80, 2)
                ),
                new SplittableRandom(1000L));

        Function<Long, ErrorScenario> errorScenarioFun = (l) -> scenario;

        MessagePublisher publisher = new MessagePublisher(errorScenarioFun);

        Predicate<ErrorMessage> messagePredicate = (m) -> m.getHeaders().get(0)._2.equals("Austria");

        StepVerifier
                .create(publisher.generateErrorMessages())
                .expectNextMatches(messagePredicate)
                .thenCancel()
                .verify();
    }
}
