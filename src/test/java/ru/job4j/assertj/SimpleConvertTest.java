package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleConvertTest {
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> list = List.of("name", "hello", "first", "second", "third");
        assertThat(list).hasSize(5)
                .contains("name")
                .contains("hello", Index.atIndex(1))
                .containsAnyOf("hello", "sun", "moon")
                .doesNotContain("moon", "sun");
    }

    @Test
    void checkSet() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Set<String> set = Set.of("one", "two", "sun", "moon", "seven", "five");
        assertThat(set).hasSize(6)
                .contains("two")
                .containsOnly("one", "two", "sun", "moon", "seven", "five")
                .doesNotContain("eleven", "earth")
                .containsAnyOf("sun", "moon", "earth", "jupiter");
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = Map.of("one", 1, "sun", 2, "earth", 3, "moon", 4);
        assertThat(map).hasSize(4)
                .containsKeys("one", "sun", "earth")
                .containsValues(4, 2, 1, 3)
                .doesNotContainKey("three")
                .doesNotContainValue(6)
                .containsEntry("earth", 3);
    }
}