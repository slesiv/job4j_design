package ru.job4j.collection.map;

import org.junit.Test;
import ru.job4j.map.SimpleMap;
import org.hamcrest.core.Is;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import static org.junit.Assert.*;

public class SimpleMapTest {

    @Test
    public void whenPutAndGet() {
        SimpleMap<Integer, Integer> smInt = new SimpleMap<>();
        for (int i = 1; i < 8; i++) {
            smInt.put(i, i);
        }
        assertThat(smInt.get(7), Is.is(7));
    }

    @Test
    public void whenPutStringAndGet() {
        SimpleMap<String, String> smStr = new SimpleMap<>();
        for (int i = 1; i < 8; i++) {
            smStr.put("key: " + i, "value: " + i);
        }
        assertThat(smStr.get("key: 6"), Is.is("value: 6"));
    }

    @Test
    public void whenRemoveAndGet() {
        SimpleMap<String, String> smStr = new SimpleMap<>();
        for (int i = 1; i < 8; i++) {
            smStr.put("key: " + i, "value: " + i);
        }
        smStr.remove("key: 1");
        assertNull(smStr.get("key: 1"));
    }

    @Test
    public void whenRemoveAndGetThatFalse() {
        SimpleMap<Integer, Integer> smInt = new SimpleMap<>();
        for (int i = 1; i < 8; i++) {
            smInt.put(i, i);
        }
        assertThat(smInt.remove(0), Is.is(Boolean.FALSE));
    }

    @Test
    public void whenCreateIterator() {
        SimpleMap<Integer, Integer> smInt = new SimpleMap<>();
        for (int i = 1; i < 8; i++) {
            smInt.put(i, i);
        }

        Iterator<Integer> iterator = smInt.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertThat(count, Is.is(7));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenCreateIteratorAndModification() {
        SimpleMap<Integer, Integer> smInt = new SimpleMap<>();
        Iterator<Integer> iterator = smInt.iterator();

        for (int i = 1; i < 8; i++) {
            smInt.put(i, i);
        }

        assertThat(iterator.hasNext(), Is.is(Boolean.TRUE));
        assertThat(iterator.next(), Is.is(1));
        smInt.put(8, 8);
        assertThat(iterator.hasNext(), Is.is(Boolean.TRUE));
    }
}
