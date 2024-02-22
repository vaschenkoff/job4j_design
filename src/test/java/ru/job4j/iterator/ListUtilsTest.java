package ru.job4j.iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenRemoveIf() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
        ListUtils.removeIf(input, x -> x % 2 == 0);
        assertThat(input).hasSize(2).containsSequence(1, 3);
    }

    @Test
    void whenRemoveIf2() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
        ListUtils.removeIf(input, x -> x % 3 == 0);
        assertThat(input).hasSize(2).containsSequence(1, 2);
    }

    @Test
    void whenReplaceIf() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
        ListUtils.replaceIf(input, x -> x % 2 == 0, 5);
        assertThat(input).hasSize(3).containsSequence(1, 5, 3);
    }

    @Test
    void whenReplaceIf2() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
        ListUtils.replaceIf(input, x -> x % 3 == 0, 7);
        assertThat(input).hasSize(3).containsSequence(1, 2, 7);
    }

    @Test
    void whenRemoveAll1() {
        List<Integer> el = List.of(1, 3);
        ListUtils.removeAll(input, el);
        assertThat(input).isEmpty();
    }

    @Test
    void whenRemoveAll2() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
        ListUtils.addAfter(input, 2, 4);
        assertThat(input).hasSize(4).containsSequence(1, 2, 3, 4);
        List<Integer> el = List.of(2, 3);
        ListUtils.removeAll(input, el);
        assertThat(input).hasSize(2).containsSequence(1, 4);
    }
 }