package com.bullet.metricscloud.publisher;

import com.bullet.metricscloud.domain.Category;
import com.bullet.metricscloud.domain.ErrorMessage;
import io.vavr.Lazy;
import io.vavr.Tuple2;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ErrorScenario {
    private final List<ErrorCondition> errorConditions;
    private final SplittableRandom random;

    public ErrorMessage generateErrorMessage() {
        return new ErrorMessage(
                Stream
                        .of(Category.values())
                        .map(this::createTuple)
                        .collect(Collectors.toList())
        );
    }

    private Tuple2<Category, String> createTuple(Category category) {
        return errorConditions
                .stream()
                .filter(e -> e.getCategory().equals(category))
                .findFirst()
                .map(e -> new Tuple2<>(category, getValueWithBias(e.getCategory(), e.getProbability(), e.getLikelyIndex())))
                .orElseGet(() -> new Tuple2<>(category, getRandomValue(category)));
    }

    private String getRandomValue(Category category) {
        int index = random.nextInt(0, Category.valueMap.get(category).size());

        return Category.valueMap.get(category).get(index);
    }

    private String getValueWithBias(Category category, Integer probability, int likelyIndex) {
        Lazy<List<Integer>> potentiallyAvailableIndices = Lazy.of(() -> IntStream
                .range(0, Category.valueMap.get(category).size())
                .filter(i -> i != likelyIndex)
                .boxed()
                .collect(Collectors.toList()));

        int randomValue = random.nextInt(1, 101);

        if (randomValue <= probability) {
            String value = Category.valueMap.get(category).get(likelyIndex);

            return value;
        } else {
            List<Integer> availableIndices = potentiallyAvailableIndices.get();
            int index = random.nextInt(0, availableIndices.size());

            String value = Category.valueMap.get(category).get(availableIndices.get(index));

            return value;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class ErrorCondition {
        private final Category category;
        private final Integer probability;
        private final Integer likelyIndex;
    }
}
