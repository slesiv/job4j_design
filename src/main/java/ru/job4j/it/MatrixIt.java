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

    public boolean hasNext() {
        while (row < data.length && column >= data[row].length) {
            column = 0;
            row++;
        }
        return row < data.length && column < data[row].length;
    }

    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int value = data[row][column];
        column++;
        return value;
    }
}
