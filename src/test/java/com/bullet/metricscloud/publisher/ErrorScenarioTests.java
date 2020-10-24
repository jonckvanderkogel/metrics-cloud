package com.bullet.metricscloud.publisher;

import com.bullet.metricscloud.domain.Category;
import com.bullet.metricscloud.domain.ErrorMessage;
import com.bullet.metricscloud.publisher.ErrorScenario.ErrorCondition;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorScenarioTests {
    @Test
    public void shouldGenerateErrorMessage() {
        ErrorScenario scenario = new ErrorScenario(
                List.of(
                        new ErrorCondition(Category.COUNTRY, 80, 3),
                        new ErrorCondition(Category.URL, 80, 2)
                ),
                new SplittableRandom(1000L));

        ErrorMessage message = scenario.generateErrorMessage();

        assertEquals("Austria", message.getHeaders().get(0)._2);
        assertEquals("/loans", message.getHeaders().get(4)._2);
    }

    @Test
    public void shouldGenerateTheCorrectPercentageInAScenario() {
        ErrorScenario scenario = new ErrorScenario(
                List.of(
                        new ErrorCondition(Category.COUNTRY, 80, 3)
                ),
                new SplittableRandom(1L));

        long messagesFromAustria = IntStream
                .rangeClosed(1, 100000)
                .boxed()
                .map(ignored -> scenario.generateErrorMessage())
                .filter(e -> e.getHeaders().get(0)._2.equals("Austria"))
                .count();

        // we now expect approximately 80% of the ErrorMessages to come from Austria
        assertEquals(0.8, Math.round(((double) messagesFromAustria /100000L) * 100.0)/100.0);
    }
}
