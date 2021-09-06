package ru.job4j.iterator;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;

public class ListUtilsTest {

    @Test
    public void whenAddBefore() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 1, 2);
        assertThat(Arrays.asList(1, 2, 3), Is.is(input));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddBeforeWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 3, 2);
    }

    @Test
    public void whenAddAfter() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addAfter(input, 0, 2);
        assertThat(Arrays.asList(1, 2, 3), Is.is(input));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddAfterWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addAfter(input, 3, 2);
    }

    @Test
    public void whenAddAfterLast() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2));
        ListUtils.addAfter(input, 2, 3);
        assertThat(Arrays.asList(0, 1, 2, 3), Is.is(input));
    }

    @Test
    public void whenRemoveIf() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.removeIf(input, (Integer i) -> i == 2);
        assertThat(Arrays.asList(1, 3), Is.is(input));
    }

    @Test
    public void whenRemoveIfEmptyList() {
        List<Integer> input = new ArrayList<>(Arrays.asList());
        ListUtils.removeIf(input, (Integer i) -> i == 2);
        assertThat(Arrays.asList(), Is.is(input));
    }

    @Test
    public void whenRemoveIfWithInvalidPredicate() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.removeIf(input, (Integer i) -> i == 10);
        assertThat(Arrays.asList(1, 2, 3), Is.is(input));
    }

    @Test
    public void whenRemoveIfAllWithPredicate() {
        List<Integer> input = new ArrayList<>(Arrays.asList(2, 4, 8));
        ListUtils.removeIf(input, (Integer i) -> i % 2 == 0);
        assertThat(Arrays.asList(), Is.is(input));
    }

    @Test
    public void whenReplaceIF() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.replaceIf(input, (Integer i) -> i == 2, 10);
        assertThat(Arrays.asList(1, 10, 3), Is.is(input));
    }

    @Test
    public void whenReplaceIfWithInvalidPredicate() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.replaceIf(input, (Integer i) -> i == 5, 10);
        assertThat(Arrays.asList(1, 2, 3), Is.is(input));
    }

    @Test
    public void whenReplaceIfAllElements() {
        List<Integer> input = new ArrayList<>(Arrays.asList(2, 4, 8));
        ListUtils.replaceIf(input, (Integer i) -> i % 2 == 0, 10);
        assertThat(Arrays.asList(10, 10, 10), Is.is(input));
    }

    @Test
    public void whenRemoveAll() {
        List<Integer> input = new ArrayList<>(Arrays.asList(2, 4, 8));
        List<Integer> elements = new ArrayList<>(Arrays.asList(4, 8, 2));
        ListUtils.removeAll(input, elements);
        assertThat(Arrays.asList(), Is.is(input));
    }

    @Test
    public void whenRemoveAllIfNotEquals() {
        List<Integer> input = new ArrayList<>(Arrays.asList(2, 4, 8));
        List<Integer> elements = new ArrayList<>(Arrays.asList(1, 0, 3));
        ListUtils.removeAll(input, elements);
        assertThat(Arrays.asList(2, 4, 8), Is.is(input));
    }

    @Test
    public void whenRemoveAllIfListEmpty() {
        List<Integer> input = new ArrayList<>(Arrays.asList());
        List<Integer> elements = new ArrayList<>(Arrays.asList(1, 0, 3));
        ListUtils.removeAll(input, elements);
        assertThat(Arrays.asList(), Is.is(input));
    }
}