package ru.job4j.collection;

import java.util.*;

public class SimpleArray<T> implements Iterable<T> {
    private Object[] container;
    private int modCount = 0;
    private int size = 0;

    public SimpleArray() {
        this.container = new Object[2];
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        return (T) container[index];
    }

    public void add(T model) {
        modCount++;
        if (size == container.length) {
            container = Arrays.copyOf(container, size + size);
        }
        container[size] = (T) model;
        size++;
    }

    public boolean contains(T model) {
        if (size == 0) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (container[i] == model) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int expectedModCount = modCount;
            int indexIt = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return size > indexIt;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) container[indexIt++];
            }
        };
    }

    public static void main(String[] args) {
        SimpleArray<Integer> si = new SimpleArray<>();
        si.add(1);
        si.add(null);
        si.add(2);
        Iterator<Integer> it = si.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
