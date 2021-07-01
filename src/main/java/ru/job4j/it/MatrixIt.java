package ru.job4j.it;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIt implements Iterator<Integer> {
    private final int[][] data;
    private int row = 0;
    private int column = 0;

    public MatrixIt(int[][] data) {
        this.data = data;
    }

    private boolean checkNext() {
        for (int i = row; i < data.length; i++) {
            if (data[i].length != 0) {
                row = i;
                return true;
            }
        }
        return false;
    }

    public boolean hasNext() {
        return row < data.length && column < data[row].length  || checkNext();
    }

    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int value = data[row][column];
        column++;
        while (row < data.length && column >= data[row].length) {
            column = 0;
            row++;
        }
        return value;
    }

    @Override
    public String toString() {
        for (int i : data[row]) {
            return String.valueOf(i);
        }
        return null;
    }
}
