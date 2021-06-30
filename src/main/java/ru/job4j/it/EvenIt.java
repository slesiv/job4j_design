package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenIt implements Iterator<Integer> {
    private int[] data;
    private int index = 0;

    public EvenIt(int[] data) {
        this.data = data;
    }

    private boolean parityCheck() {
        for (int i = index; i < data.length; i++) {
            if (data[i] % 2 == 0) {
                index = i;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasNext() {
        return parityCheck();
    }

    @Override
    public Integer next() {
        if (parityCheck()) {
            return data[index++];
        }
        throw new NoSuchElementException();
    }
}
