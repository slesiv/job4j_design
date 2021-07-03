package ru.job4j.generics;

import java.util.*;

public class SimpleArray<T> implements Iterable<T> {
    private Object[] simpleArray;
    private int size = 0;

    public SimpleArray(int initialCapacity) {
        this.simpleArray = new Object[initialCapacity];
    }

    public boolean add(T model) {
        if (size >= simpleArray.length) {
            return false;
        }
        simpleArray[size] = model;
        size++;
        return true;
    }

    public T set(int index, T model) {
        Objects.checkIndex(index, size);
        T oldValue = (T) simpleArray[index];
        simpleArray[index] = model;
        return oldValue;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);

        return (T) simpleArray[index];
    }

    public T remove(int index) {
        Objects.checkIndex(index, size);
        final Object[] newSimpleArray = simpleArray;
        T oldValue = (T) simpleArray[index];

        final int newSize = size - 1;
        if (newSize  > index) {
            System.arraycopy(newSimpleArray, index + 1, simpleArray, index, newSize - index);
        }
        size = newSize;
        simpleArray[size] = null;
        return oldValue;
    }

    @Override
    public Iterator<T> iterator() {
         return new Iterator<T>() {
             private int cursor = 0;

             @Override
             public boolean hasNext() {
                 return cursor < size && size != 0;
             }

             @Override
             public T next() {
                 return (T) simpleArray[cursor++];
             }
         };
    }
}
