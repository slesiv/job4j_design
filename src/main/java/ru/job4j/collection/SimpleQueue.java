package ru.job4j.collection;

import java.util.NoSuchElementException;

public class SimpleQueue<T> {
    private final SimpleStack<T> in = new SimpleStack<>();
    private final SimpleStack<T> out = new SimpleStack<>();

    public T poll() {
        T value = null;
        if (out.isEmpty()) {
            if (in.isEmpty()) {
                throw new NoSuchElementException();
            }
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }
        value = out.pop();
        return value;
    }

    public void push(T value) {
        in.push(value);
    }
}
