package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BackwardArrayIt implements Iterator<Integer> {
    private final int[] data;
    private int point;

    public BackwardArrayIt(int[] data) {
        this.data = data;
        this.point = data.length;
    }

    public boolean hasNext() {
        return point > 0;
    }

    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return data[--point];
    }
}
